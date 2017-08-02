package com.footballer.rest.api.v2.manager;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.dao.TeamDao;
import com.footballer.rest.api.v1.dao.UserDao;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.vo.Team;
import com.footballer.rest.api.v1.vo.enumType.FeeType;
import com.footballer.rest.api.v1.vo.enumType.PayMethod;
import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.ArenaUser;
import com.footballer.rest.api.v2.vo.ArenaMember;
import com.footballer.rest.api.v2.vo.ArenaMemberBalanceLine;
import com.footballer.rest.api.v2.vo.ArenaMemberDiscount;
import com.footballer.rest.api.v2.vo.MemberShip;
import com.footballer.rest.api.v2.vo.WechatConfig;
import com.footballer.rest.api.v2.vo.enumType.UserType;
import com.footballer.util.CommonUtil;
import com.footballer.util.DateUtil;
import com.footballer.util.ObjectUtil;

@Service
public class ArenaMemberManager {

	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	@Autowired
	public UserDao userDao;
	
	@Autowired
	public TeamDao teamDao;
	
	@Autowired
	public PlayerAccountManager playerMgr;
	
	public Long getPlayerIdByArenaMemberId(String id){
		return (Long) mybatisBaseDao.selectOne("getPlayerIdByArenaMemberId", Long.parseLong(id));
	}
	
	public WechatConfig getWechatConfigByArenaId(Long arenaId) {
		return (WechatConfig) mybatisBaseDao.selectOne("getWechatConfigByArenaId", arenaId);
	}
	
	public ArenaMember getArenaMemberByArenaIdAndMemberId(Long arenaId, Long memberId){
		ArenaMember am = new ArenaMember();
		am.setArenaId(arenaId);
		am.setMemberShipId(memberId);
		return (ArenaMember) mybatisBaseDao.selectOne("getArenaMemberByArenaIdAndMemberShipId", am);
	}
	
	public ArenaMember getArenaMemberByArenaIdAndPlayerId(Long arenaId, Long playerId){
		ArenaMember am = new ArenaMember();
		am.setArenaId(arenaId);
		am.setPlayerId(playerId);
		return (ArenaMember) mybatisBaseDao.selectOne("getArenaMemberByArenaIdAndPlayerId", am);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void manageArenaMemberFunds(Long arenaId, Long memberShipId, BigDecimal price ,Long fieldReservationId){
		String comments ="";
		ArenaMember arenaMember = getArenaMemberByArenaIdAndMemberId(arenaId, memberShipId);
		Long arenaMemberId = arenaMember.getId();
		if(ObjectUtil.isNotNull(arenaMember.getCustomDiscount())){
			price =  arenaMember.getCustomDiscount().divide(new BigDecimal(10)).multiply(price).setScale(0, BigDecimal.ROUND_HALF_UP);
			comments = "会员预定成功,自动根据折扣("+ arenaMember.getCustomDiscount()+ ")后的价格扣款："+ price ;
		}else{
			//之前很多会员没有设置过值,则按照原价扣款
			comments = "会员预定成功,未设置折扣，按照原价扣款："+ price ;
		} 
		//先记录 balance line
		saveArenaMemberLine(arenaMemberId, FeeType.CREDIT, PayMethod.PRESTORE, price, comments, fieldReservationId);
		//再更新 余额
		updateArenaMemberBalance(arenaMemberId, price);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void manageArenaMemberFunds(Long arenaMemberId, BigDecimal price, FeeType feeType, PayMethod paymethod, String comments){
		//先记录 balance line
		saveArenaMemberLine(arenaMemberId, feeType, paymethod, price, comments, null);
		//再更新 余额
		updateArenaMemberBalance(arenaMemberId, price);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<ArenaMemberBalanceLine> getArenaMemberBalanceLineByfieldReservationIdAndArenaMemberId(Long fieldReservationId, Long arenaMemberId){
		ArenaMemberBalanceLine ambl = new ArenaMemberBalanceLine();
		ambl.setArenaMemberId(arenaMemberId);
		ambl.setFieldReservationId(fieldReservationId);
		return (List<ArenaMemberBalanceLine>) mybatisBaseDao.selectList("getArenaMemberBalanceLineByfieldReservationIdAndArenaMemberId", ambl);
	}
	
	@Deprecated
	public ArenaMemberDiscount getArenaMemberDiscountByArenaIdAndPrice(Long arenaId, BigDecimal fund){
		ArenaMemberDiscount amd = new ArenaMemberDiscount();
		amd.setArenaId(arenaId);
		amd.setFund(fund);
		return (ArenaMemberDiscount) mybatisBaseDao.selectOne("getArenaMemberDiscountByArenaIdAndFund", amd);
	}
	

	
	public ArenaMemberDiscount getArenaMemberDiscountByArenaIdAndLevel(Long arenaId, Integer level){
		ArenaMemberDiscount amd = new ArenaMemberDiscount();
		amd.setArenaId(arenaId);
		amd.setLevel(level);
		return (ArenaMemberDiscount) mybatisBaseDao.selectOne("getArenaMemberDiscountByArenaIdAndLevel", amd);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void addArenaMemeberInfo(Long arenaId, String name, String teamName, Long cellPhone, String weChatNo, BigDecimal price, String comments) throws DomainNotFoundException {
		//根据冲入的金额 来判断 属于什么样得会员级别和类型 获取 arenaMemberDiscountId
		ArenaMemberDiscount amd = getArenaMemberDiscountByArenaIdAndLevel(arenaId, 0);
		if(ObjectUtil.isNull(amd)){
			throw new DomainNotFoundException("请选择正确得场馆规定会员充值金额");
		}
		Long arenaMemberDiscountId = amd.getId();
		
		//自动通过 cellPhone 来创建球员账号 account and footballer_player. 并拿到 playerId.
		Long playerId = playerMgr.registerPremiumAccount(arenaId, name, cellPhone, weChatNo);
		
		//根据对面创建新的球队信息，并将当前player作为球队创建者
		Team team =new Team();
		team.setName(teamName);
		team.setCaptainUserId(playerId);
		team.setCreateBy("后台管理员录入");
		team.setCreateDt(DateUtil.getCurrentDate());
		Team savedTeam = teamDao.createNewTeam(team,playerId);
		//创建球员所在球队的资金账户
		userDao.createPlayerDepositAccount(team.getCaptainUserId(), savedTeam.getId());
		
		//获取 指定场地会员的最新会员号，并加1 ，作为新的会员号 memberShipNo
		Integer memberShipNo = getLatestArenaMemberNoByArenaId(arenaId)+ 1;
		
		//创建 memberShip account
		Long memberShipId = saveMemberShip(playerId, arenaMemberDiscountId, name);
		
		//创建 arenaMember account
		saveArenaMember(arenaId, memberShipId, memberShipNo, name, price, comments);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void addArenaMemeberInfo4ExistAccountWithDiffentArena(Long arenaId, Long playerId, String name, String teamName, Long cellPhone, String weChatNo, BigDecimal price, String comments) throws DomainNotFoundException {
		//根据充入的金额 来判断 属于什么样得会员级别和类型 获取 arenaMemberDiscountId
		//updated 默认级别0 
		ArenaMemberDiscount amd = getArenaMemberDiscountByArenaIdAndLevel(arenaId, 0);
		if(ObjectUtil.isNull(amd)){
			throw new DomainNotFoundException("请选择正确得场馆规定会员充值金额");
		}
		Long arenaMemberDiscountId = amd.getId();
		
		//获取 指定场地会员的最新会员号，并加1 ，作为新的会员号 memberShipNo
		Integer memberShipNo = getLatestArenaMemberNoByArenaId(arenaId)+ 1;
		
		//创建 memberShip account
		Long memberShipId = saveMemberShip(playerId, arenaMemberDiscountId, name);
		
		//创建 arenaMember account
		saveArenaMember(arenaId, memberShipId, memberShipNo, name, price, comments);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void addArenaNonMemeberInfo(Long arenaId, String name, String teamName, Long cellPhone, String weChatNo,String comments, BigDecimal customPrice) throws DomainNotFoundException {
		
		//自动通过 cellPhone 来创建球员账号 account and footballer_player. 并拿到 playerId.
		Long playerId = playerMgr.registerPremiumAccount(arenaId, name, cellPhone, weChatNo);
		
		//根据对面创建新的球队信息，并将当前player作为球队创建者
		Team team =new Team();
		team.setName(teamName);
		team.setCaptainUserId(playerId);
		team.setCreateBy("后台管理员录入");
		team.setCreateDt(DateUtil.getCurrentDate());
		Team savedTeam = teamDao.createNewTeam(team,playerId);
		//创建球员所在球队的资金账户
		userDao.createPlayerDepositAccount(team.getCaptainUserId(), savedTeam.getId());
		//创建用户和场馆的关系
		playerMgr.saveArenaUser(playerId, arenaId, UserType.BACKEND, comments, customPrice);
	}
	
	
	
	@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
	public Integer getLatestArenaMemberNoByArenaId(Long arenaId){
		Integer arenaMemberNo = (Integer) mybatisBaseDao.selectOne("getLatestArenaMemberNoByArenaId", arenaId); 
		if(ObjectUtil.isNotNull(arenaMemberNo)){
			return arenaMemberNo;
		}else{
			return 0;
		}
	}
	
	@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
	public Long saveMemberShip(Long playerId, Long arenaMemberDiscountId, String name){
		MemberShip ms =new MemberShip();
		CommonUtil.addAuditableAttributes(ms);
		ms.setPlayerId(playerId);
		ms.setArenaMemberDiscountId(arenaMemberDiscountId);
		ms.setName(name);
		//ms.setCellphone(cellPhone);
		//ms.setWechatNo(weChatNo);
		ms.setTotalBalance(BigDecimal.ZERO);
		MemberShip newMemberShip = (MemberShip) mybatisBaseDao.insertReturnWithParameter("saveMemberShip", ms);
		return newMemberShip.getId();
	}
	
	@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
	public void saveArenaMember(Long arenaId, Long memberShipId, Integer memberShipNo, String name,BigDecimal balance, String comments){
		ArenaMember am =new ArenaMember();
		CommonUtil.addAuditableAttributes(am);
		am.setArenaId(arenaId);
		am.setMemberShipId(memberShipId);
		am.setMemberShipNo(memberShipNo);
		am.setName(name);
		am.setBalance(balance);
		am.setComments(comments);
		mybatisBaseDao.insert("saveArenaMember", am);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void saveArenaMemberLine(Long arenaMemberId, FeeType feeType, PayMethod payMethod, BigDecimal fee, String comments, Long fieldReservationId){
		ArenaMemberBalanceLine ambl = new ArenaMemberBalanceLine();
		CommonUtil.addAuditableAttributes(ambl);
		ambl.setArenaMemberId(arenaMemberId);
		ambl.setFeeType(feeType);
		ambl.setPayMethod(payMethod);
		ambl.setFee(fee);
		ambl.setComment(comments);
		ambl.setFieldReservationId(fieldReservationId);
		mybatisBaseDao.insert("saveArenaMemberLine", ambl);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void updateArenaMemberBalance(Long arenaMemberId,BigDecimal balance ){
		ArenaMember am =new ArenaMember();
		am.setId(arenaMemberId);
		am.setBalance(balance);
		am.setUpdateBy("付费会员自动处理");
		am.setUpdateDt(DateUtil.getCurrentDate());
		mybatisBaseDao.update("updateArenaMemberBalance", am);
	}
	
//	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
//	public void updateArenaMemberBalance(Long arenaId, Long memberShipId, BigDecimal price ){
//		
//	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void updateArenaMemeberNameAndComments(Long arenaMemberId, String newName, BigDecimal customDiscount, String comments){
		ArenaMember am =new ArenaMember();
		am.setId(arenaMemberId);
		am.setName(newName);
		am.setCustomDiscount(customDiscount);
		am.setComments(comments);
		mybatisBaseDao.update("updateArenaMemeberNameAndComments", am);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void updateArenaNonMemeberComments(Long arenaId, Long playerId,String comments){
		ArenaUser au = new ArenaUser();
		au.setArenaId(arenaId);
		au.setPlayerId(playerId);
		au.setComments(comments);
		mybatisBaseDao.update("updateArenaNonMemeberComments", au);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void updateArenaMemeberLevel(Long arenaId,Long arenaMemberId, Integer level){
		ArenaMember am =new ArenaMember();
		am.setId(arenaMemberId);
		am.setArenaId(arenaId);
		am.setLevel(level);
		mybatisBaseDao.update("updateArenaMemeberLevel", am);
	}
}
