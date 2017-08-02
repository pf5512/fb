package com.footballer.rest.api.v2.manager;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.AvailableFiled;
import com.footballer.rest.api.v2.domain.BattleActiveCustomersInfo;
import com.footballer.rest.api.v2.domain.BattleWishInfo;
import com.footballer.rest.api.v2.domain.FieldReservationDetail;
import com.footballer.rest.api.v2.request.AvaliableFieldRequest;
import com.footballer.rest.api.v2.request.BattleRequest;
import com.footballer.rest.api.v2.request.FieldReservationRequest;
import com.footballer.rest.api.v2.request.battleReservationRequest;
import com.footballer.rest.api.v2.result.MemberShipResult;
import com.footballer.rest.api.v2.vo.BattleWish;
import com.footballer.rest.api.v2.vo.FieldChargeStandard;
import com.footballer.rest.api.v2.vo.FieldReservation;
import com.footballer.rest.api.v2.vo.enumType.PaymentStatus;
import com.footballer.rest.api.v2.vo.enumType.VideoStatus;
import com.footballer.util.CommonUtil;
import com.footballer.util.DateUtil;
import com.footballer.util.ObjectUtil;
import com.google.common.collect.Lists;



@Service
public class BattleManager {

	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	@Autowired
	private MemberShipManager msMgr;
	
	@Autowired
	private ArenaFieldManager afMgr;
	
	@Autowired
	private ArenaMemberManager amMgr;
	
	@Autowired
	public CommonManager cMgr;
	
	@SuppressWarnings("unchecked")
	public List<BattleWishInfo> findWeChatBattleWishsOfNext7Days(Long arenaId){
		List<BattleWishInfo>  battleWishInfoList = Lists.newArrayList();
		List<BattleWishInfo>  existBattleWishInfoList = Lists.newArrayList();
		//微信收集的未来7天的约站信息
		List<BattleWishInfo> weChatBattleWishInfoList = (List<BattleWishInfo>) mybatisBaseDao.selectList("findWeChatBattleWishsOfNext7Days", arenaId);
		//后台收集的未来7天的约站信息
		List<BattleWishInfo> backendAvailableBattleInfoList = (List<BattleWishInfo>) mybatisBaseDao.selectList("findBackendAvailableBattleOfNext7Days", arenaId);
		
		existBattleWishInfoList.addAll(weChatBattleWishInfoList);
		existBattleWishInfoList.addAll(backendAvailableBattleInfoList);

		battleWishInfoList.addAll(existBattleWishInfoList);
		
		List<String> currentWeekDaysStartTimeList = Lists.newArrayList();
		//获取当天日期和 未来7天日期
		List<String> currentWeekDays = DateUtil.currentWeekDays();
		//场馆标准开始时间
		List<String> arenaFieldStartTimeList =  cMgr.getArenaFieldStartTimeListByArenaId(arenaId);
		//拼凑展示时间
		for(String date: currentWeekDays){
			for(String startTime: arenaFieldStartTimeList){
				currentWeekDaysStartTimeList.add(date+' '+startTime);
			}
		}
		
		//然后根据日期循环, 筛选出来已经有记录的约战意愿
		for(BattleWishInfo bwi: existBattleWishInfoList){
			if(currentWeekDaysStartTimeList.contains(DateUtil.formatLongDate(bwi.getDate()))){
				currentWeekDaysStartTimeList.remove(DateUtil.formatLongDate(bwi.getDate()));	
			}
		}

		//根据筛选出来得日期 创建空得数据模板便于前台显示
		for(String s_date: currentWeekDaysStartTimeList){
			BattleWishInfo bwi = new BattleWishInfo();
			bwi.setDate(DateUtil.parseLongDate(s_date));
			battleWishInfoList.add(bwi);
		}
	
		Collections.sort(battleWishInfoList,new Comparator<BattleWishInfo>(){  
            @Override  
            public int compare(BattleWishInfo b1,BattleWishInfo b2) {  
                return b1.getDate().compareTo(b2.getDate());  
            }  
        });  
				
		return battleWishInfoList;
	}
	
	@SuppressWarnings("unchecked")
	public List<BattleActiveCustomersInfo> findBattleActiveCustomersOrderByTimes(Long arenaId){
		return (List<BattleActiveCustomersInfo>) mybatisBaseDao.selectList("findBattleActiveCustomersOrderByTimes", arenaId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public Long addWeChatBattleWish(BattleRequest request){
		BattleWish bw = new BattleWish();
		bw.setArenaId(request.getArenaId());
		bw.setPlayerId(request.getPlayerId());
		bw.setTeamId(request.getTeamId());
		bw.setDate(DateUtil.parseLongDate(request.getDate()));
		
		BattleWish battleWish = (BattleWish) mybatisBaseDao.insertReturnWithParameter("addWeChatBattleWish", bw);
		return battleWish.getId();
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void removeBattleWishById(Long battleWishId){
		mybatisBaseDao.delete("removeBattleWishById", battleWishId);
	}
	
	public List<AvailableFiled> getAvaliableFieldsOfDay(AvaliableFieldRequest request){
		List<AvailableFiled> afList= Lists.newArrayList();
		//被拖动选择的2只球队的playerId
		String playerId = request.getPlayerId().toString();
		String targetPlayerId = request.getTargetPlayerId().toString();
		
		//1.当前场次场地 已预订的情况
		List<FieldReservationDetail> arenaFieldsReservationDetailList = (List<FieldReservationDetail>) mybatisBaseDao.selectList("getArenaFieldsReservationDetailOnSpecifyTime", request);
		
		//2.具体时段和场馆信息条件下的 场地信息
		List<FieldReservationDetail> arenaFCSInfoList = (List<FieldReservationDetail>) mybatisBaseDao.selectList("getArenaFieldChargeStandardInfoOnSpecifyTime", request);

		for(FieldReservationDetail bookedFieldDetail:arenaFieldsReservationDetailList){
			if("fullField".equals(bookedFieldDetail.getType())){//整场已预订,则排除可用
				for(int i=0;i<arenaFCSInfoList.size();i++){
					if(arenaFCSInfoList.get(i).getFieldChargeStandardId().toString().equals(bookedFieldDetail.getFieldChargeStandardId().toString())){
						arenaFCSInfoList.remove(i);
						break;
					}
				}
			}else if("battle".equals(bookedFieldDetail.getType())){//约战已预订
				//查看当前预订中是否有本次拖动的2支球队之一
				
				if(bookedFieldDetail.getGuestPlayerId() ==null && bookedFieldDetail.getPlayerId().toString().equals(playerId)){
					//可用
				}else if(bookedFieldDetail.getGuestPlayerId() ==null && bookedFieldDetail.getPlayerId().toString().equals(targetPlayerId) ){
					//可用
				}else{
					//与本次操作球队都不同 则当前场次不可用
					for(int i=0;i<arenaFCSInfoList.size();i++){
						if(arenaFCSInfoList.get(i).getFieldChargeStandardId().toString().equals(bookedFieldDetail.getFieldChargeStandardId().toString())){
							arenaFCSInfoList.remove(i);
							break;
						}
					}
				}
			}
		}
		
		for(FieldReservationDetail avaliableField:arenaFCSInfoList)
		{
			AvailableFiled af = new AvailableFiled();
			af.setFcsId(avaliableField.getFieldChargeStandardId());
			af.setFieldId(avaliableField.getFieldId());
			af.setFieldName(avaliableField.getFieldName());
			afList.add(af);
		}	
		return afList;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void arenaOwnerAddBattleReservation(battleReservationRequest req){
		
		FieldReservation fr = new FieldReservation();

		fr.setFieldId(req.getFieldId());
		fr.setFieldChargeStandardId(req.getFcsId());
		fr.setPlayerId(req.getPlayerId());
		fr.setUseDate(DateUtil.parseShortDate(req.getUseDate()));
		fr.setGuestPlayerId(req.getGuestPlayerId());
		
		//约战方  会员身份检查 是则自动扣款和记录
		MemberShipResult msResult = msMgr.getPlayerMemeberShipInfo(req.getArenaId(),req.getPlayerId());
		if(msResult.isMemberOfArena()){  
			fr.setPaymentStatus(PaymentStatus.YUEZHANPAID);
		}else{
			fr.setPaymentStatus(PaymentStatus.ADMINORDERUNPAID);
		}
		        
		//应战方  会员身份检查
		MemberShipResult msGuestResult = msMgr.getPlayerMemeberShipInfo(req.getArenaId(),req.getGuestPlayerId());
		if(msGuestResult.isMemberOfArena()){  
			fr.setGuestPaymentStatus(PaymentStatus.YINGZHANPAID);
		}else{
			fr.setGuestPaymentStatus(PaymentStatus.ADMINORDERUNPAID);
		}
		CommonUtil.addAuditableAttributes(fr);
		
		//先完成预定，然后根据身份扣款
		FieldReservation newFieldReservation = (FieldReservation) mybatisBaseDao.insertReturnWithParameter("arenaOwnerAddBattleReservation", fr);
		Long newFieldReservationId = newFieldReservation.getId();

		//约战方  会员身份检查 是则自动扣款和记录
        if(ObjectUtil.isNotNull(msResult.getArenaMember())){  
        	// 会员用户自动扣取场地费一半的资金
        	FieldChargeStandard fcs = afMgr.getFieldChargeStandardById(req.getFcsId());
        	amMgr.manageArenaMemberFunds(req.getArenaId(), msResult.getArenaMember().getMemberId(),fcs.getPrice().divide(new BigDecimal(2)).negate(), newFieldReservationId);
        }        
        
        //应战方  会员身份检查
        if(ObjectUtil.isNotNull(msGuestResult.getArenaMember())){  
        	// 会员用户自动扣取场地费一半的资金
        	FieldChargeStandard fcs = afMgr.getFieldChargeStandardById(req.getFcsId());
        	amMgr.manageArenaMemberFunds(req.getArenaId(), msGuestResult.getArenaMember().getMemberId(),fcs.getPrice().divide(new BigDecimal(2)).negate(), newFieldReservationId);
        }
	}
}
