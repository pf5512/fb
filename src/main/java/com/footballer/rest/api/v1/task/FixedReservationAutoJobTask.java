package com.footballer.rest.api.v1.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.filter.AppContextHolder;
import com.footballer.filter.context.ApplicationContext;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v2.manager.FieldReservationManager;
import com.footballer.rest.api.v2.manager.MemberShipManager;
import com.footballer.rest.api.v2.request.FieldReservationRequest;
import com.footballer.rest.api.v2.result.AutomationJobFieldReservationResult;
import com.footballer.rest.api.v2.result.MemberShipResult;
import com.footballer.rest.api.v2.vo.FieldReservation;
import com.footballer.rest.api.v2.vo.enumType.BookStatus;
import com.footballer.rest.api.v2.vo.enumType.PaymentStatus;
import com.footballer.util.DateUtil;

import jersey.repackaged.com.google.common.collect.Lists;



public class FixedReservationAutoJobTask {

	@Autowired
	private FieldReservationManager frManager;
	
	@Autowired
	private MemberShipManager msManager;
	
	public void automationReservation() throws Exception{

		 System.out.println("================> 启动 自动预定固定场次任务 =================>");
		 
		//因为是服务器直接call，这里设置为默认 account id =0
		 Account account = new Account();
		 account.setId(0L);
		 AppContextHolder.setContext(new ApplicationContext(account));
		 
		 
		 List<AutomationJobFieldReservationResult> list = frManager.getAllAutomationReservationActiveJobList();
		 
		 List<FieldReservation> frList = Lists.newArrayList();
		 for(AutomationJobFieldReservationResult result:list){

			 //根据参数计算出一周后的某一天得日期
			 //Date useDate = DateUtil.getNextWeekDay(DateUtil.getCurrentDate(), result.getDayOfWeek());
			 
			 //根据参数计算出两周后的某一天得日期
			 Date useDate = DateUtil.getNextSecondWeekDay(DateUtil.getCurrentDate(), result.getDayOfWeek());
			 
			 //检查当前场次和使用日期是否已经被预定了，如果是 则进入下一次循环
			 if(frManager.checkFieldRevsersionExist(result.getFieldChargeStandardId(), DateUtil.formatShortDate(useDate))){
				 continue;
			 }
			 
			 FieldReservation fr = new FieldReservation();

			 fr.setFieldId(result.getFieldId());
			 fr.setType(result.getType());
			 fr.setFieldChargeStandardId(result.getFieldChargeStandardId());
			 fr.setPlayerId(result.getPlayerId());
			 fr.setBookStatus(BookStatus.BOOK);
			 
			 
			 
     		 fr.setUseDate(useDate);
     		 
			 fr.setBookDate(DateUtil.getCurrentDate());
			 
			 MemberShipResult msr = msManager.getPlayerMemeberShipInfo(result.getArenaId(), result.getPlayerId());
			 if(msr.isMemberOfArena()){ //会员预定后，自动扣钱和记录
				 
				 FieldReservationRequest req = new FieldReservationRequest();
				 req.setArenaId(result.getArenaId());
				 req.setBookStatus(BookStatus.BOOK);
				 req.setDayOfWeek(result.getDayOfWeek());
				 req.setFcsId(result.getFieldChargeStandardId());
				 req.setFieldId(result.getFieldId());
				 req.setMemberId(msr.getArenaMember().getMemberId());
				 req.setOperateType(result.getType());
				 //req.setPaymentStatus(PaymentStatus.PAID);
				 req.setPlayerId(result.getPlayerId());
				 req.setType(result.getType());
				 req.setUseDate(DateUtil.formatShortDate(useDate));
				 req.setVideoStatus(result.getVideoStatus().getIndex());
				 
				//这里会根据类型 自动设置支付状态, 并完成预定！
				 frManager.memberReserveField(req);
				 
			 }else{
				 //非会员直接默认未支付
				 fr.setPaymentStatus(PaymentStatus.ADMINORDERUNPAID);
				 fr.setVideoStatus(result.getVideoStatus());
				 fr.setCreateBy(DateUtil.formatOldLongDateChina(DateUtil.getCurrentDate())+"<=>系统自动化任务");
				 fr.setCreateDt(DateUtil.getCurrentDate());
				 frList.add(fr);
			 }
			 
			 
		 }
		 
		 //一次性录入非会员预定
         frManager.insertBatchAutomationFieldReservations(frList);
         
         System.out.println("================> 结束 自动预定固定场次任务 =================>");
	 }
}
