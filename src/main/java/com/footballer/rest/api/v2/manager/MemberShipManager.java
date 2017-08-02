package com.footballer.rest.api.v2.manager;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.dao.ArenaDao;
import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.ArenaMemberInfo;
import com.footballer.rest.api.v2.result.MemberShipResult;
import com.footballer.rest.api.v2.vo.ArenaMemberBalanceLine;
import com.footballer.rest.api.v2.vo.ArenaMemberDiscount;
import com.footballer.util.ObjectUtil;

@Service
public class MemberShipManager {

	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	@Autowired
	public ArenaDao arenaDao;
	
	public MemberShipResult getPlayerMemeberShipInfo(Long arenaId, Long playerId){
		MemberShipResult msResult = new MemberShipResult();
		ArenaMemberInfo am = new ArenaMemberInfo();
		am.setArenaId(arenaId);
		am.setPlayerId(playerId);
	
		ArenaMemberInfo arenaMember = (ArenaMemberInfo) mybatisBaseDao.selectOne("getPlayerMemeberShipInfo", am);
		if(ObjectUtil.isNull(arenaMember)){
			msResult.setMemberOfArena(false);
		}else{
			msResult.setMemberOfArena(true);
			msResult.setArenaMember(arenaMember);
		}
		return msResult;
	}
	
	@SuppressWarnings("unchecked")
	public List<ArenaMemberDiscount> getArenaMemberDiscountsByArenaId(Long arenaId){
		return (List<ArenaMemberDiscount>) mybatisBaseDao.selectList("getArenaMemberDiscountsByArenaId", arenaId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void saveArenaMemberDiscount(Long arenaId, Integer level, BigDecimal discount, BigDecimal fund){
		ArenaMemberDiscount amd = new ArenaMemberDiscount();
		amd.setArenaId(arenaId);
		amd.setLevel(level);
		amd.setDiscount(discount);
		amd.setFund(fund);
		
		mybatisBaseDao.insert("saveArenaMemberDiscount", amd);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void updateArenaMemberDiscount(Long arenaMemberDisocuntId, BigDecimal discount, BigDecimal fund){
		ArenaMemberDiscount amd = new ArenaMemberDiscount();
		amd.setId(arenaMemberDisocuntId);
		amd.setDiscount(discount);
		amd.setFund(fund);
		
		mybatisBaseDao.update("updateArenaMemberDiscount", amd);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void deleteArenaMemberDiscount(Long arenaMemberDisocuntId){
		mybatisBaseDao.delete("deleteArenaMemberDiscount", arenaMemberDisocuntId);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ArenaMemberInfo> getArenaMemebersInfo(Long arenaId){
		return (List<ArenaMemberInfo>) mybatisBaseDao.selectList("getArenaMemebersInfo", arenaId);
	}
	
	@SuppressWarnings("unchecked")
	public List<ArenaMemberInfo> getArenaNonMemebersInfo(Long arenaId){
		return (List<ArenaMemberInfo>) mybatisBaseDao.selectList("getArenaNonMemebersInfo", arenaId);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<ArenaMemberBalanceLine> getArenaMemeberBalanceLinesByArenaMemberId(Long id){
		return (List<ArenaMemberBalanceLine>) mybatisBaseDao.selectList("getArenaMemeberBalanceLinesByArenaMemberId", id);
	}
	
}
