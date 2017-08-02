package com.footballer.rest.api.v2.manager;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.dao.ArenaDao;
import com.footballer.rest.api.v1.dao.FieldDao;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.dao.TeamDao;
import com.footballer.rest.api.v1.vo.Field;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.Team;
import com.footballer.rest.api.v1.vo.enumType.FeeType;
import com.footballer.rest.api.v1.vo.enumType.PayMethod;
import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.AllArenaUserReservationStatistics;
import com.footballer.rest.api.v2.domain.ArenaMemberInfo;
import com.footballer.rest.api.v2.domain.FieldReservationDetail;
import com.footballer.rest.api.v2.request.FieldReservationRequest;
import com.footballer.rest.api.v2.result.ArenasFieldsReservationResult;
import com.footballer.rest.api.v2.result.AutomationJobFieldReservationResult;
import com.footballer.rest.api.v2.result.MemberShipResult;
import com.footballer.rest.api.v2.result.PlayerFieldReservationResult;
import com.footballer.rest.api.v2.vo.Account;
import com.footballer.rest.api.v2.vo.ArenaMember;
import com.footballer.rest.api.v2.vo.ArenaMemberBalanceLine;
import com.footballer.rest.api.v2.vo.AutomationJobFieldReservation;
import com.footballer.rest.api.v2.vo.FieldChargeStandard;
import com.footballer.rest.api.v2.vo.FieldReservation;
import com.footballer.rest.api.v2.vo.enumType.AutomationJobStatus;
import com.footballer.rest.api.v2.vo.enumType.BookStatus;
import com.footballer.rest.api.v2.vo.enumType.PaymentStatus;
import com.footballer.rest.api.v2.vo.enumType.VideoStatus;
import com.footballer.util.CommonUtil;
import com.footballer.util.DBUtil;
import com.footballer.util.DateUtil;
import com.footballer.util.ObjectUtil;
import com.google.common.collect.Lists;

@Service
public class FieldReservationManager {

	@Autowired
	public MybatisBaseDao mybatisBaseDao;

	@Autowired
	private CommonManager cMgr;

	@Autowired
	private ArenaMemberManager amMgr;

	@Autowired
	private ArenaFieldManager afMgr;

	@Autowired
	private PlayerAccountManager pMgr;
	
	@Autowired
	private MemberShipManager msMgr;

	@Autowired
	public ArenaDao arenaDao;

	@Autowired
	public PlayerDao playerDao;

	@Autowired
	public TeamDao teamDao;

	@Autowired
	public FieldDao fieldDao;

	// 获取 指定场馆和具体哪天该场馆所有场地的 预定情况
	@SuppressWarnings("unchecked")
	public List<ArenasFieldsReservationResult> getArenasFieldsReservationsByArenaIdAndUseDate(Long arenaId,
			String useDate) {
		FieldReservation fr = new FieldReservation();
		fr.setArenaId(arenaId);
		fr.setUseDate(DateUtil.parseShortDate(useDate));
		List<ArenasFieldsReservationResult> list = (List<ArenasFieldsReservationResult>) mybatisBaseDao
				.selectList("getArenasFieldsReservationsByArenaIdAndUseDate", fr);

		for (ArenasFieldsReservationResult f : list) {

			if ("fullField".equals(f.getReservationType())) {

				List<Team> teams = teamDao.findTeamByActivePlayer(Long.parseLong(f.getPlayerId()));
				String teamNames = "";
				for (Team t : teams) {
					teamNames += teamNames + t.getName() + " ";
				}
				f.setTeamName(teamNames);
				//Long playerCellPhone = pMgr.findPlayerCellById(Long.parseLong(f.getPlayerId()));
				Account account = pMgr.getAccountById(Long.parseLong(f.getPlayerId()));
				f.setHostCell(account.getCellphone());
				f.setWeChatPicUrl(account.getWeChatPicUrl());

			} else if ("battle".equals(f.getReservationType())) {
				List<Team> teams = teamDao.findTeamByActivePlayer(Long.parseLong(f.getPlayerId()));
				String teamNames = "", guestTeamName = "";
				for (Team t : teams) {
					teamNames += teamNames + t.getName() + " ";
				}
				f.setTeamName(teamNames);
				Long playerCellPhone = pMgr.findPlayerCellById(Long.parseLong(f.getPlayerId()));
				f.setHostCell(playerCellPhone);
				Account account = pMgr.getAccountById(Long.parseLong(f.getPlayerId()));
				f.setWeChatPicUrl(account.getWeChatPicUrl());

				Player guestPlayer = playerDao.findJustPlayer(f.getGuestPlayerId());
				if (ObjectUtil.isNotNull(guestPlayer)) {
					List<Team> guestTeams = teamDao.findTeamByActivePlayer(f.getGuestPlayerId());
					for (Team t : guestTeams) {
						guestTeamName += guestTeamName + t.getName() + " ";
					}
					f.setGuestPlayerName(guestPlayer.getNickName());
					f.setGuestTeamName(guestTeamName);
					
					account = pMgr.getAccountById(f.getGuestPlayerId());
					//f.setHostCell(account.getCellphone());
					f.setGuestWeChatPicUrl(account.getWeChatPicUrl());

					Long guestCellPhone = pMgr.findPlayerCellById(f.getGuestPlayerId());
					f.setGuestCell(guestCellPhone);
				}

			} else if ("singleFly".equals(f.getReservationType())) {
				// f.setPlayerIdList(playerIdList);
				// f.setPlayerNameList(playerNameList);
			} else {

			}
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ArenasFieldsReservationResult> getArenaReservationByCurrentWeek(Long arenaId){
		
		List<ArenasFieldsReservationResult> list = (List<ArenasFieldsReservationResult>) mybatisBaseDao.selectList("getArenaReservationByCurrentWeek", arenaId);

		for (ArenasFieldsReservationResult f : list) {

			if ("fullField".equals(f.getReservationType())) {

				List<Team> teams = teamDao.findTeamByActivePlayer(Long.parseLong(f.getPlayerId()));
				String teamNames = "";
				for (Team t : teams) {
					teamNames += teamNames + t.getName() + " ";
				}
				f.setTeamName(teamNames);
				Long playerCellPhone = pMgr.findPlayerCellById(Long.parseLong(f.getPlayerId()));
				f.setHostCell(playerCellPhone);

			} else if ("battle".equals(f.getReservationType())) {
				List<Team> teams = teamDao.findTeamByActivePlayer(Long.parseLong(f.getPlayerId()));
				String teamNames = "", guestTeamName = "";
				for (Team t : teams) {
					teamNames += teamNames + t.getName() + " ";
				}
				f.setTeamName(teamNames);
				Long playerCellPhone = pMgr.findPlayerCellById(Long.parseLong(f.getPlayerId()));
				f.setHostCell(playerCellPhone);

				Player guestPlayer = playerDao.findJustPlayer(f.getGuestPlayerId());
				if (ObjectUtil.isNotNull(guestPlayer)) {
					List<Team> guestTeams = teamDao.findTeamByActivePlayer(f.getGuestPlayerId());
					for (Team t : guestTeams) {
						guestTeamName += guestTeamName + t.getName() + " ";
					}
					f.setGuestPlayerName(guestPlayer.getNickName());
					f.setGuestTeamName(guestTeamName);

					Long guestCellPhone = pMgr.findPlayerCellById(f.getGuestPlayerId());
					f.setGuestCell(guestCellPhone);
				}

			} else if ("singleFly".equals(f.getReservationType())) {
				// f.setPlayerIdList(playerIdList);
				// f.setPlayerNameList(playerNameList);
			} else {

			}
		}
			
		return list; 
	}
	
	public List<String> getArenaReservationStatisticsByYear(Long arenaId,Integer year){
		
		//数据准备
		List<String> dataList = Lists.newArrayList();
		//月度跨度
		for (int i = Calendar.JANUARY; i <= Calendar.DECEMBER; i++) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, i);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date startDate = cal.getTime();// 当前月第一天

			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date endDate = cal.getTime();// 当前月最后一天

			String times = (i+1)+"月-"+ getArenaReservationStatisticsByDate(arenaId,DateUtil.formatShortDate(startDate), DateUtil.formatShortDate(endDate));
			//1月-36-24-7-3-2
			dataList.add(times);
		}
		dataList.add("-");
		//季度跨度
		for (int i = Calendar.JANUARY, j=1; i < Calendar.DECEMBER; i+=3,j++) {
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, i);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			Date startDate = cal.getTime();// 当前月第一天

			cal.add(Calendar.MONTH, 3);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date endDate = cal.getTime();// 当前月最后一天

			String times = j+"季度-"+ getArenaReservationStatisticsByDate(arenaId,DateUtil.formatShortDate(startDate), DateUtil.formatShortDate(endDate));
			//1季度-36-24-7-3-2
			dataList.add(times);
		}
		//后台不做装配 前台处理
		
		return dataList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ArenaMemberInfo> getArenaUsersFieldReservationStatisticsByDate(Long arenaId, String startDate, String endDate){
		
		Map<String, Object> params = DBUtil.createParameterMapNotThrowException(
                new String[]{"arenaId","startDate", "endDate"},
                new Object[]{arenaId.toString(), startDate, endDate},
                "getArenaUsersFieldReservationStatisticsByDate");
		
		return (List<ArenaMemberInfo>) mybatisBaseDao.selectList("getArenaUsersFieldReservationStatisticsByDate", params);
	}
	
	
	public AllArenaUserReservationStatistics getArenaAllUsersReservationStatisticsByDate(Long arenaId, String startDate, String endDate){
		
		Map<String, Object> params = DBUtil.createParameterMapNotThrowException(
                new String[]{"arenaId","startDate", "endDate"},
                new Object[]{arenaId.toString(), startDate, endDate},
                "getArenaAllUsersReservationStatisticsByDate");
		
		return (AllArenaUserReservationStatistics) mybatisBaseDao.selectOne("getArenaAllUsersReservationStatisticsByDate", params);
	}
	
	public String getArenaReservationStatisticsByDate(Long arenaId,String startDate, String endDate){
		
		Map<String, Object> params = DBUtil.createParameterMapNotThrowException(
                new String[]{"arenaId","startDate", "endDate","times"},
                new Object[]{arenaId.toString(), startDate, endDate,""},
                "getArenaReservationStatisticsByDate");
		
		return (String) mybatisBaseDao.selectOne("getArenaReservationStatisticsByDate", params);
	}

	public FieldReservation getFieldReservationById(Long id) {
		return (FieldReservation) mybatisBaseDao.selectOne("getFieldReservationById", id);
	}

	// 更新当前预定记录的 状态
	public void updateFieldReservationStatus(Long fieldReservationId, BookStatus bookStatus, PaymentStatus paymentStatus,Long guestPlayerId,
			PaymentStatus guestPaymentStatus) {
		FieldReservation fr = new FieldReservation();

		fr.setId(fieldReservationId);
		fr.setBookStatus(bookStatus);
		if(ObjectUtil.isNull(guestPlayerId)){
			fr.setPaymentStatus(paymentStatus);
		}else{
			fr.setPaymentStatus(paymentStatus);
			fr.setGuestPaymentStatus(guestPaymentStatus);
		}
		fr.setUpdateDt(DateUtil.getCurrentDate());

		mybatisBaseDao.update("updateFieldReservationStatus", fr);
	}

	// 检查 当前预定是否已经存在
	public boolean checkFieldRevsersionExist(Long fieldReservationId) {
		FieldReservation fR = (FieldReservation) mybatisBaseDao.selectOne("getFieldReservationById",
				fieldReservationId);
		if (ObjectUtil.isNotNull(fR)) {
			return true;
		} else {
			return false;
		}
	}

	// 检查 当前预定是否已经存在
	public boolean checkFieldRevsersionExist(Long fieldChargeStandardId, String useDate) {
		FieldReservation fr = new FieldReservation();
		fr.setFieldChargeStandardId(fieldChargeStandardId);
		fr.setUseDate(DateUtil.parseShortDate(useDate));
		@SuppressWarnings("unchecked")
		List<FieldReservation> queryList = (List<FieldReservation>) mybatisBaseDao
				.selectList("checkFieldRevsersionExist", fr);
		if (queryList.size() != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// 检查 当前预定是否已经存在
	public boolean checkFieldRevsersionExist(Long fieldReservationId, Long playerId) {
		FieldReservation fr = new FieldReservation();
		fr.setId(fieldReservationId);
		fr.setPlayerId(playerId);
		FieldReservation queryFr = (FieldReservation) mybatisBaseDao
				.selectOne("checkFieldRevsersionExistByIdAndPlayerId", fr);
		if (ObjectUtil.isNull(queryFr)) {
			return false;
		} else {
			return true;
		}
	}

	// 付费会员完成预定场次并自动记录和扣款
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void memberReserveField(FieldReservationRequest request) {
		// Long fieldReservationId =
		// Long.parseLong(String.valueOf(reserveField(request, BookStatus.BOOK,
		// PaymentStatus.PAID).getId()));
		FieldChargeStandard fcs = afMgr.getFieldChargeStandardById(request.getFcsId());
		// 先预定场次后，再会员自动扣款
		if (request.getType() == "fullField" || "fullField".equals(request.getType())) {
			Long fieldReservationId = Long
					.parseLong(String.valueOf(reserveField(request, BookStatus.BOOK, PaymentStatus.PAID).getId()));
			amMgr.manageArenaMemberFunds(request.getArenaId(), request.getMemberId(), fcs.getPrice().negate(),
					fieldReservationId);
		} else if (request.getType() == "battle" || "battle".equals(request.getType())) {
			// 约战 只收一半的钱
			Long fieldReservationId = Long.parseLong(
					String.valueOf(reserveField(request, BookStatus.BOOK, PaymentStatus.YUEZHANPAID).getId()));
			amMgr.manageArenaMemberFunds(request.getArenaId(), request.getMemberId(),
					fcs.getPrice().divide(new BigDecimal(2)).negate(), fieldReservationId);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public FieldReservation reserveField(FieldReservationRequest request, BookStatus bookStatus,
			PaymentStatus paymentStatus) {
		FieldReservation fr = new FieldReservation();

		FieldChargeStandard fcs = cMgr.findFieldByFieldChargeStandardById(request.getFcsId());
		fr.setFieldId(fcs.getFieldId());
		fr.setFieldChargeStandardId(request.getFcsId());
		fr.setPlayerId(request.getPlayerId());
		fr.setType(request.getType());
		fr.setBookStatus(bookStatus);
		fr.setUseDate(DateUtil.parseShortDate(request.getUseDate()));
		fr.setBookDate(DateUtil.getCurrentDate());
		fr.setPaymentStatus(paymentStatus);
		fr.setVideoStatus(VideoStatus.getValue(request.getVideoStatus()));
		CommonUtil.addAuditableAttributes(fr);
		
		return (FieldReservation) mybatisBaseDao.insertReturnWithParameter("saveFieldReservation", fr);
	}

	@SuppressWarnings("unchecked")
	public List<PlayerFieldReservationResult> getPlayerReservationList(Long playerId) {
		return (List<PlayerFieldReservationResult>) mybatisBaseDao.selectList("getPlayerReservationList", playerId);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void cancelReservation(Long fieldReservationId, Date cancelDate) {
		FieldReservation fr = new FieldReservation();

		fr.setId(fieldReservationId);
		fr.setCancelDate(cancelDate);
		fr.setUpdateBy(AppContextHolder.getContext().getAccount().getId().toString());
		fr.setUpdateDt(cancelDate);

		mybatisBaseDao.update("cancelReservation", fr);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void adminCancelReservation(Long fieldReservationId, Date cancelDate) {
		FieldReservation fr = new FieldReservation();

		fr.setId(fieldReservationId);
		fr.setCancelDate(cancelDate);
		fr.setUpdateBy("管理员操作：取消预定");
		fr.setUpdateDt(cancelDate);

		mybatisBaseDao.update("cancelReservation", fr);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void deductAndRefund(Long arenaId, Long playerId, Long fieldReservationId, Date cancelDate) {
		ArenaMember am = amMgr.getArenaMemberByArenaIdAndPlayerId(arenaId, playerId);
		Long arenaMemberId = am.getId();

		// 根据id 拿到本次预定时所扣去的费用金额，全额退款
		fieldFullRefund(fieldReservationId, arenaMemberId);
		// 根据取消时间和使用时间相差数，判断罚款情况（<24h 则按照场地设置扣去一定金额）
		cancelDeduct(cancelDate, fieldReservationId, arenaMemberId);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void memberRefund(Long arenaId, Long playerId, Long fieldReservationId, Date cancelDate) {
		ArenaMember am = amMgr.getArenaMemberByArenaIdAndPlayerId(arenaId, playerId);
		Long arenaMemberId = am.getId();

		// 根据id 拿到本次预定时所扣去的费用金额，根据折扣退款
		resevationRefund(fieldReservationId, arenaMemberId);

		// 暂时不扣钱 注释掉根据取消时间罚款
		// 根据取消时间和使用时间相差数，判断罚款情况（<24h 则按照场地设置扣去一定金额）
		// cancelDeduct(cancelDate, fieldReservationId, arenaMemberId);
	}

	@Deprecated
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void fieldFullRefund(Long fieldReservationId, Long arenaMemberId) {
		ArenaMemberBalanceLine ambl = amMgr.getArenaMemberBalanceLineByfieldReservationIdAndArenaMemberId(fieldReservationId,arenaMemberId).get(0);
		BigDecimal price = ambl.getFee().abs(); // 正值 退款
		String comments = "取消预定，全额退款";

		// 先记录 balance line
		amMgr.saveArenaMemberLine(arenaMemberId, FeeType.DEBIT, PayMethod.PRESTORE, price, comments,
				fieldReservationId);
		// 再更新 余额
		amMgr.updateArenaMemberBalance(arenaMemberId, price);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void resevationRefund(Long fieldReservationId, Long arenaMemberId) {
		ArenaMemberBalanceLine ambl = amMgr.getArenaMemberBalanceLineByfieldReservationIdAndArenaMemberId(fieldReservationId,arenaMemberId).get(0);
		BigDecimal price = ambl.getFee().abs(); // 正值 退款
		String comments ="会员取消预定,自动根据折扣后的价格退款："+ price ;
		// 先记录 balance line
		amMgr.saveArenaMemberLine(arenaMemberId, FeeType.DEBIT, PayMethod.PRESTORE, price, comments, fieldReservationId);
		// 再更新 余额
		amMgr.updateArenaMemberBalance(arenaMemberId, price);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void cancelDeduct(Date cancelDate, Long fieldReservationId, Long arenaMemberId) {

		// 根据使用时间和取消时间差 判断是否存在取消耽误费
		FieldReservation fr = getFieldReservationById(fieldReservationId);
		FieldChargeStandard fcs = cMgr.findFieldByFieldChargeStandardById(fr.getFieldChargeStandardId());
		String useDateTimeString = DateUtil.formatShortDate(fr.getUseDate()) + " "
				+ DateUtil.formatShortTime(fcs.getStartTime());

		Date useDateTime = DateUtil.parseLongDate(useDateTimeString);
		long hours = DateUtil.getBetweenHoursOfTwoDates(useDateTime, cancelDate);

		// 未在比赛开始前 24小时 完成取消操作 需要扣去一定的费用
		if (hours >= 0 && hours < 24) {
			// 若存在 获取 对应场地取消预定设置的标准扣费

			Field field = fieldDao.findFieldById(fr.getFieldId().toString());
			if (ObjectUtil.isNotNull(field) && ObjectUtil.isNotNull(field.getCancelReservationFee())) { // 场馆已经设置了
																										// 取消扣款金额，才完成扣款
				BigDecimal price = field.getCancelReservationFee().negate(); // 负值
																				// 扣款

				String comments = "取消预定，扣除耽误费";
				// 先记录 balance line
				amMgr.saveArenaMemberLine(arenaMemberId, FeeType.CREDIT, PayMethod.PRESTORE, price, comments,
						fieldReservationId);
				// 再更新 余额
				amMgr.updateArenaMemberBalance(arenaMemberId, price);
			}

		}
		// 不存在 结束
	}

	public FieldReservationDetail getArenasFieldsReservationDetail(Long fieldReservationId) {
		return (FieldReservationDetail) mybatisBaseDao.selectOne("getArenasFieldsReservationDetail",
				fieldReservationId);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void acceptBattleInfo(String operateType, Long fieldReservationId, Long guestPlayerId, PaymentStatus paymentStatus) {
		FieldReservation fr = new FieldReservation();

		fr.setId(fieldReservationId);
		fr.setGuestPlayerId(guestPlayerId);
		fr.setGuestPaymentStatus(paymentStatus);
		fr.setUpdateBy(operateType+ "操作：新增应战方信息");
		fr.setUpdateDt(new Date());
		mybatisBaseDao.update("acceptBattleInfo", fr);
	}
	
	//移除一支约战球队（会员完成自动退费一半的费用）
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void removeTeamOfBattle(FieldReservationRequest req) {
		
		FieldReservationDetail frd = getArenasFieldsReservationDetail(req.getFieldReservationId());
		
		FieldReservation fr = new FieldReservation();
		fr.setId(req.getFieldReservationId());
		fr.setUpdateDt(new Date());

		if(req.getRemovePlayerId().toString().equals(req.getGuestPlayerId().toString())){
			//移除客队
			fr.setGuestPlayerId(null);
			fr.setGuestPaymentStatus(PaymentStatus.UNPAID);
			fr.setUpdateBy("管理员操作：移除约战客队");
			mybatisBaseDao.update("removeGuestTeamOfBattle", fr);
			
			MemberShipResult msResult = msMgr.getPlayerMemeberShipInfo(req.getArenaId(),req.getRemovePlayerId());
	        if(ObjectUtil.isNull(msResult.getArenaMember())){  
	            //非会员用户
	        }else{   
	            //会员用户 退款
	        	memberRefund(req.getArenaId(), req.getGuestPlayerId(), req.getFieldReservationId(),null);
	        }
		}
		else if(req.getRemovePlayerId().toString().equals(req.getPlayerId().toString())){
			//移除主队
			int guestPaymentStatusNo = Integer.parseInt(frd.getGuestPaymentStatus()); 
			if(guestPaymentStatusNo == 3 || guestPaymentStatusNo == 6){
				guestPaymentStatusNo--;  //客队转换为主队时  对应的 支付状态 由  应战已付 转换为 约战已付
			} 
			fr.setPaymentStatus(PaymentStatus.getValue(guestPaymentStatusNo));
			fr.setPlayerId(req.getGuestPlayerId());
			fr.setGuestPlayerId(null);
			fr.setGuestPaymentStatus(PaymentStatus.UNPAID);
			fr.setUpdateBy("管理员操作：移除约战主队,并将客队调整为主队");
			mybatisBaseDao.update("removeHostTeamOfBattle", fr);
			
			MemberShipResult msResult = msMgr.getPlayerMemeberShipInfo(req.getArenaId(),req.getPlayerId());
	        if(ObjectUtil.isNull(msResult.getArenaMember())){  
	            //非会员用户
	        }else{   
	            //会员用户 退款
	        	memberRefund(req.getArenaId(), req.getPlayerId(), req.getFieldReservationId(),null);
	        }
		}
	}

	// 应战方 信息 更新 （会员完成自动扣一半的费用）
	public void acceptBattleFieldReservation(FieldReservationRequest request, PaymentStatus paymentStatus) {

		acceptBattleInfo(request.getOperateType(), request.getFieldReservationId(), request.getGuestPlayerId(), paymentStatus);

		if (ObjectUtil.isNotNull(request.getMemberId())) {
			// 会员用户自动扣取场地费一半的资金
			FieldChargeStandard fcs = afMgr.getFieldChargeStandardById(request.getFcsId());
			amMgr.manageArenaMemberFunds(request.getArenaId(), request.getMemberId(),
					fcs.getPrice().divide(new BigDecimal(2)).negate(), request.getFieldReservationId());
		}
	}
	
	public void createAutoReservationJob(FieldReservationRequest request){
		AutomationJobFieldReservation ajfr = new AutomationJobFieldReservation();
		ajfr.setPlayerId(request.getPlayerId());
		ajfr.setType(request.getType());
		ajfr.setArenaId(request.getArenaId());
		ajfr.setFieldId(request.getFieldId());
		ajfr.setFieldChargeStandardId(request.getFcsId());
		ajfr.setDayOfWeek(request.getDayOfWeek());
		ajfr.setBookStatus(request.getBookStatus());
		ajfr.setPaymentStatus(request.getPaymentStatus());
		ajfr.setVideoStatus(VideoStatus.getValue(request.getVideoStatus()));
		ajfr.setJobStatus(AutomationJobStatus.ACTIVE);
		ajfr.setCreateDt(DateUtil.getCurrentDate());
		ajfr.setCreateBy(AppContextHolder.getContext().getAccount().getId().toString());
		
		mybatisBaseDao.insert("createAutoReservationJob", ajfr);
	}
	
	@SuppressWarnings("unchecked")
	public List<AutomationJobFieldReservationResult> getAutomationReservationJobList(Long arenaId){
		return (List<AutomationJobFieldReservationResult>) mybatisBaseDao.selectList("getAutomationReservationJobList", arenaId);
	}
	
	@SuppressWarnings("unchecked")
	public List<AutomationJobFieldReservationResult> getAllAutomationReservationActiveJobList(){
		return (List<AutomationJobFieldReservationResult>) mybatisBaseDao.selectList("getAllAutomationReservationActiveJobList", null);
	}
	
	public void deleteAutoReservationJobById(Long autoJobId){
		mybatisBaseDao.delete("deleteAutoReservationJobById", autoJobId);
	}
	
	public void updateAutoReservationJobStatusById(Long autoJobId, AutomationJobStatus status){
		AutomationJobFieldReservation ajfr = new AutomationJobFieldReservation();
		ajfr.setId(autoJobId);
		ajfr.setJobStatus(status);
		mybatisBaseDao.update("updateAutoReservationJobStatusById", ajfr);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void insertBatchAutomationFieldReservations(List<FieldReservation> list) {
		mybatisBaseDao.insert("insertBatchAutomationFieldReservations", list);
	}
}
