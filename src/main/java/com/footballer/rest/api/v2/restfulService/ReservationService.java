package com.footballer.rest.api.v2.restfulService;

import java.util.Date;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.notification.NotificationManager;
import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v2.Response.FieldReservationResponse;
import com.footballer.rest.api.v2.Response.UnPaidReserveFieldResponse;
import com.footballer.rest.api.v2.Response.WechatConfigResponse;
import com.footballer.rest.api.v2.domain.FieldReservationDetail;
import com.footballer.rest.api.v2.manager.ArenaMemberManager;
import com.footballer.rest.api.v2.manager.CommonManager;
import com.footballer.rest.api.v2.manager.FieldReservationManager;
import com.footballer.rest.api.v2.manager.MemberShipManager;
import com.footballer.rest.api.v2.manager.PlayerAccountManager;
import com.footballer.rest.api.v2.request.FieldReservationRequest;
import com.footballer.rest.api.v2.request.GetPlayerReservationListRequest;
import com.footballer.rest.api.v2.request.RemoveGuestBattle;
import com.footballer.rest.api.v2.request.WechatConfigRequest;
import com.footballer.rest.api.v2.result.MemberShipResult;
import com.footballer.rest.api.v2.result.PlayerFieldReservationResult;
import com.footballer.rest.api.v2.vo.Account;
import com.footballer.rest.api.v2.vo.FieldReservation;
import com.footballer.rest.api.v2.vo.WechatConfig;
import com.footballer.rest.api.v2.vo.enumType.AutomationJobStatus;
import com.footballer.rest.api.v2.vo.enumType.BookStatus;
import com.footballer.rest.api.v2.vo.enumType.PaymentStatus;
import com.footballer.util.DateUtil;
import com.footballer.util.ObjectUtil;
import com.footballer.util.StringUtil;
import com.google.common.collect.Lists;


@Path("/mobile/v2/reservation-service")
public class ReservationService {
	
	private static Logger logger = LoggerFactory.getLogger(ReservationService.class);
	
	@Autowired
	private FieldReservationManager frManager;
	
	@Autowired
	private CommonManager cMgr;
	
	@Autowired
	public NotificationManager notificationManager;
	
	@Autowired
	public MemberShipManager msMgr;
	
	@Autowired
	private PlayerAccountManager pMgr;
	
	@Autowired
	private ArenaMemberManager amMgr;
	
	@Autowired
	public PlayerDao playerDao;
	
	
	
	//获取 指定场馆和具体哪天该场馆所有场地的 预定情况
    @GET
    @Path("/getArenaReservationByDate/{arenaId}/{useDate}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public FieldReservationResponse getArenaReservationByDate(@PathParam("arenaId") Long arenaId, 
    														  @PathParam("useDate") String useDate) {
    	FieldReservationResponse response = new FieldReservationResponse();
    	
    	if (StringUtil.isEmpty(useDate)) {
    		useDate = DateUtil.getCurrentDateString();
    	}
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId) 
    			&& ObjectUtil.isNotNull(useDate)) {
    		WechatConfig wechatConfig = amMgr.getWechatConfigByArenaId(arenaId);
    		response.setArenasFieldsReservation(frManager.getArenasFieldsReservationsByArenaIdAndUseDate(arenaId, useDate));
    		response.setCurrentWeeksDates(DateUtil.currentWeekDaysByNumber(wechatConfig.getOrderAheadWeekNumber()));    		
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaId or useDate can not null or arena not exist");
    		return response;
    	}
    }
    
    //获取 指定场馆 当前一周 该场馆所有场地的 预定情况
    @GET
    @Path("/getArenaReservationByCurrentWeek/{arenaId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public FieldReservationResponse getArenaReservationByCurrentWeek(@PathParam("arenaId") Long arenaId) {
    	FieldReservationResponse response = new FieldReservationResponse();
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {
    		response.setArenasFieldsReservation(frManager.getArenaReservationByCurrentWeek(arenaId));
    		response.setCurrentWeeksDates(DateUtil.currentWeekDays());
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaId can not null or arena not exist");
    		return response;
    	}
    }
    
    //获取 指定场馆 所有场地的 具体时间段内的预定情况 (详细数据统计)
    @GET
    @Path("/getArenaReservationStatisticsByDate/{arenaId}/{year}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public FieldReservationResponse getArenaReservationStatisticsByDate(@PathParam("arenaId") Long arenaId, 
    														  @PathParam("year") Integer year) {
    	FieldReservationResponse response = new FieldReservationResponse(); 
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId) 
    			&& ObjectUtil.isNotNull(year)) {
    		
    		response.setStatisticsList(frManager.getArenaReservationStatisticsByYear(arenaId, year));
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaId or year can not null or arena not exist");
    		return response;
    	}
    }
    
    //获取 指定场馆 所有用户 详细订场数据统计
    @GET
    @Path("/getArenaUsersFieldReservationStatisticsByDate/{arenaId}/{startDate}/{endDate}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public FieldReservationResponse getArenaUsersFieldReservationStatisticsByDate(@PathParam("arenaId") Long arenaId, 
    														  @PathParam("startDate") String startDate,
    														  @PathParam("endDate") String endDate) {
    	FieldReservationResponse response = new FieldReservationResponse(); 
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId) 
    			&& ObjectUtil.isNotNull(startDate) &&  ObjectUtil.isNotNull(endDate)) {
    		
    		response.setArenaUserReservationStatisticsList(frManager.getArenaUsersFieldReservationStatisticsByDate(arenaId, startDate,endDate));
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaId or Date can not null or arena not exist");
    		return response;
    	}
    }
    
    //获取 指定场馆 所有用户类型 （后台，微信，app） 订场总数据
    
    @GET
    @Path("/getArenaAllUsersReservationStatisticsByDate/{arenaId}/{startDate}/{endDate}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public FieldReservationResponse getArenaAllUsersReservationStatisticsByDate(@PathParam("arenaId") Long arenaId, 
    														  @PathParam("startDate") String startDate,
    														  @PathParam("endDate") String endDate) {
    	FieldReservationResponse response = new FieldReservationResponse(); 
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId) 
    			&& ObjectUtil.isNotNull(startDate) &&  ObjectUtil.isNotNull(endDate)) {
    		
    		response.setAllArenaUserReservationStatistics(frManager.getArenaAllUsersReservationStatisticsByDate(arenaId, startDate,endDate));
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaId or Date can not null or arena not exist");
    		return response;
    	}
    }
    
  //获取 球员当前的预定列表（分未过期和已过期2个列表）
    @POST
    @Path("/getPlayerReservationList/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public FieldReservationResponse getPlayerReservationList(GetPlayerReservationListRequest request) {
    	FieldReservationResponse response = new FieldReservationResponse(); 
    	
    	String arenaMemberId = request.getArenaMemberId();
    	String openId = request.getOpenId();
    	Long playerId = null;
    	
    	
    	if(ObjectUtil.isNotNull(openId)){
    		Account account = pMgr.getAccountByOpenId(openId);
            playerId = account.getId();
    	}else if(ObjectUtil.isNotNull(arenaMemberId)){
    		//根据场地会员来获取预定情况
    		playerId = amMgr.getPlayerIdByArenaMemberId(arenaMemberId);
    	}else{
    		//非会员用户 获取预定
    		playerId = Long.parseLong(request.getPlayerId());
    	}
    	
    	if(ObjectUtil.isNotNull(playerId)) {
    		List<PlayerFieldReservationResult> list = Lists.newArrayList();
    		List<PlayerFieldReservationResult> unUsedList = Lists.newArrayList();
    		List<PlayerFieldReservationResult> expireList = Lists.newArrayList();
    		
    		list = frManager.getPlayerReservationList(playerId);
    		for(PlayerFieldReservationResult pfrr: list){
    			if(DateUtil.getCurrentDate().getTime() >DateUtil.parseShortDate(pfrr.getUseDate()).getTime()){
    				expireList.add(pfrr);
    			}else{
    				unUsedList.add(pfrr);
    			}
    		}
    		
    		response.setPlayerReservationList(list);
    		response.setExpiredPlayerFieldReservationList(expireList);
    		response.setUnUsedPlayerFieldReservationList(unUsedList);
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
    		
    		response.setStatus(JsonResponse.ERROR);
        	response.setMessage("playerId can not null or not exist");
    		return response;
    	}
    }

    
    //非会员预定 已经完成了支付 改变之前的预定状态
    @POST
    @Path("/updatePaidReserveField/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public JsonResponse updatePaidReserveField(FieldReservationRequest request) throws DomainNotFoundException {
        JsonResponse response = new JsonResponse(); 
        Long fieldReservationId = request.getFieldReservationId();
        String operateType = request.getOperateType();
        
        if(ObjectUtil.isNotNull(fieldReservationId)){
        	FieldReservation fr = frManager.getFieldReservationById(fieldReservationId);
        	
            //检查当前场次预定是否存在
            if(ObjectUtil.isNotNull(fr)){
                
            	//APP 
            	if("app".equals(operateType)){
            		if("fullField".equals(fr.getType())){
                        frManager.updateFieldReservationStatus(fieldReservationId, BookStatus.BOOK, PaymentStatus.APPPAID, null, null);
                	}
    				if("battle".equals(fr.getType()))
    				{ 
    					if(ObjectUtil.isNull(fr.getGuestPlayerId())){
    						//约战
    						frManager.updateFieldReservationStatus(fieldReservationId, BookStatus.BOOK, PaymentStatus.APPYUEZHANPAID, null, null);
    					}else{
    						//应战
    						frManager.updateFieldReservationStatus(fieldReservationId, BookStatus.BOOK, fr.getPaymentStatus(), fr.getGuestPlayerId(), PaymentStatus.APPYINGZHANPAID);
    					}	
    				}
            	}else{//weChat
            		if("fullField".equals(fr.getType())){
                        frManager.updateFieldReservationStatus(fieldReservationId, BookStatus.BOOK, PaymentStatus.WECHATPAID, null, null);
                	}
    				if("battle".equals(fr.getType()))
    				{ 
    					if(ObjectUtil.isNull(fr.getGuestPlayerId())){
    						//约战
    						frManager.updateFieldReservationStatus(fieldReservationId, BookStatus.BOOK, PaymentStatus.YUEZHANWECHATPAID, null, null);
    					}else{
    						//应战
    						frManager.updateFieldReservationStatus(fieldReservationId, BookStatus.BOOK, fr.getPaymentStatus(), fr.getGuestPlayerId(), PaymentStatus.YINGZHANWECHATPAID);
    					}	
    				}
            	}
            	
                response.setStatus(JsonResponse.SUCCESS);
                response.setMessage("支付完成后，预定场次生效");
                return response;
            }else{
                //不存在当前预定信息
                response.setStatus(JsonResponse.ERROR);
                response.setMessage("不存在当前预定信息");
            }
        }else{
            response.setStatus(JsonResponse.ERROR);
            response.setMessage("parameters contains null");
            return response;
        }
        return response;
    }
    
    
    // 会员 or 非会员 取消预定 具体场地的场次
    /**
     * 管理员可以设定属于场馆的的取消后扣款固定金额，当天内取消的按指定金额扣取金额。
     * 会员：标记为取消，根据取消时间，自动完成退款和扣款，并做记录。
     * 非会员：标记为取消，不做金额操作，管理员手动操作对应退款和扣款。
     */
    @POST
    @Path("/cancelReservation/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public JsonResponse cancelReservation(FieldReservationRequest request) throws DomainNotFoundException {
        JsonResponse response = new JsonResponse(); 
        Long arenaId = request.getArenaId();
        Long fieldReservationId = request.getFieldReservationId();
        Long playerId = request.getPlayerId();
        Long memberShipId = request.getMemberId();

        
        if(ObjectUtil.isNotNull(arenaId) && ObjectUtil.isNotNull(fieldReservationId) && ObjectUtil.isNotNull(playerId)){
            
            //1.检查当前用户是否预订了该场次
            if(frManager.checkFieldRevsersionExist(fieldReservationId, playerId)){
                
                //2.标记为取消预订
                Date cancelDate = DateUtil.getCurrentDate();
                frManager.cancelReservation(fieldReservationId, cancelDate);
                
                if(ObjectUtil.isNull(memberShipId)){  
                    //非会员用户
                    //3.通知场馆方退款， 换了方案，web前端自动轮训 查看 取消情况
                }else{   
                    //会员用户 按照会员折扣退款
                	frManager.memberRefund(arenaId, playerId, fieldReservationId, cancelDate);
                	
                	//3.根据退款时间，相应扣款和退款，并做完整的数据记录 ---暂时不做扣款
                    //frManager.deductAndRefund(arenaId,playerId,fieldReservationId, cancelDate);
                }
                response.setStatus(JsonResponse.SUCCESS);
                response.setMessage("取消成功");
                
            }else{
                response.setStatus(JsonResponse.ERROR);
                response.setMessage("并不存在当前预订信息");
                return response;
            }
            
        }else{
            response.setStatus(JsonResponse.ERROR);
            response.setMessage("parameters contains null");
            return response;
        }
        return response;
    }
    
    
    @GET
    @Path("/getReservationDetail/{fieldReservationId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public FieldReservationResponse getReservationDetail(@PathParam("fieldReservationId") Long fieldReservationId){
        FieldReservationResponse response = new FieldReservationResponse(); 
        
        if(ObjectUtil.isNotNull(fieldReservationId) && frManager.checkFieldRevsersionExist(fieldReservationId)){
            
            response.setFieldReservationDetail(frManager.getArenasFieldsReservationDetail(fieldReservationId));
            response.setStatus(JsonResponse.SUCCESS);
        }else{
            response.setStatus(JsonResponse.ERROR);
            response.setMessage("parameters contains null or not exist");
        }
        return response;
    }
    
    

//    /**
//     * 场馆人员代理会员 预定 具体场地的场次
//     * 会员 预定场次并完成自动扣款
//     * 非会员  预定场次 不自动扣款 但记录 支付状态为 管理员代理预定未支付
//     * @param request
//     * @return
//     * @throws DomainNotFoundException
//     */
//    @POST
//    @Path("/adminOperateReserveField/{identity}/{appId}/{apptoken}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
//    public JsonResponse adminOperateReserveField(FieldReservationRequest request) throws DomainNotFoundException {
//        JsonResponse response = new JsonResponse(); 
//        Long arenaId = request.getArenaId();
//        Long fieldChargeStandardId = request.getFcsId();
//        String type = request.getType();
//        Long playerId = request.getPlayerId();
//        String useDate = request.getUseDate();
//        
//        if(ObjectUtil.isNotNull(playerId) && ObjectUtil.isNotNull(arenaId) && ObjectUtil.isNotNull(fieldChargeStandardId)
//                && ObjectUtil.isNotNull(useDate) && ObjectUtil.isNotNull(type)){
//            
//            if(!cMgr.checkPlayerExist(playerId)){
//                response.setStatus(JsonResponse.ERROR);
//                response.setMessage("playerId not exist");
//                return response;
//            }
//            if(!cMgr.checkArenaExist(arenaId)){
//                response.setStatus(JsonResponse.ERROR);
//                response.setMessage("arenaId not eixst");
//                return response;
//            }
//            
//            //检查当前场次是否被预定
//            if(frManager.checkFieldRevsersionExist(fieldChargeStandardId, useDate)){
//                //已经被预定了，请选择其他场次
//                response.setStatus(JsonResponse.ERROR);
//                response.setMessage("抱歉，当前场次已经被预定，请选择其他场次");
//                return response;
//            }else{
//                MemberShipResult msResult = msMgr.getPlayerMemeberShipInfo(arenaId,playerId);
//                //完成预定
//                if(msResult.isMemberOfArena()){
//                    //当前球员是球场注册付费会员，则完成预定并从充值账户扣款
//                    request.setMemberId(msResult.getArenaMember().getMemberId());
//                    frManager.memberReserveField(request);
//                    response.setStatus(JsonResponse.SUCCESS);
//                    response.setMessage("预定成功，并完成自动扣款");
//                    
//                }else{
//                    //当前球员非球场会员，则只完成预定，并记录为管理员代预定未付款状态
//                    frManager.reserveField(request, BookStatus.BOOK, PaymentStatus.ADMINORDERUNPAID);
//                    response.setStatus(JsonResponse.SUCCESS);
//                    response.setMessage("非会员场次预定记录成功");
//                }
//            }
//        }else{
//            response.setStatus(JsonResponse.ERROR);
//            response.setMessage("parameters can not null");
//        }
//        return response;
//    }
    
    
    //场馆人员 取消预定 具体场地的场次
    /**
     * 管理员可以设定属于场馆的的取消后扣款固定金额，当天内取消的按指定金额扣取金额。
     * 会员：标记为取消，根据取消时间，自动完成退款和扣款，并做记录。
     * 非会员：标记为取消，不做金额操作，管理员手动操作对应退款和扣款。
     */
    @POST
    @Path("/adminCancelReservation/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public JsonResponse adminCancelReservation(FieldReservationRequest request) throws DomainNotFoundException {
        JsonResponse response = new JsonResponse(); 
        Long arenaId = request.getArenaId();
        Long fieldReservationId = request.getFieldReservationId();
        Long playerId = request.getPlayerId();
        Long guestPlayerId = request.getGuestPlayerId();
        
        if(ObjectUtil.isNotNull(arenaId) && ObjectUtil.isNotNull(fieldReservationId) && ObjectUtil.isNotNull(playerId)){
            
            //1.检查当前用户是否预订了该场次
            if(frManager.checkFieldRevsersionExist(fieldReservationId, playerId)){
                
            	Date cancelDate = DateUtil.getCurrentDate();
            	
                //约战方
                MemberShipResult msResult = msMgr.getPlayerMemeberShipInfo(arenaId,playerId);
                if(ObjectUtil.isNull(msResult.getArenaMember())){  
                    //非会员用户
                	
                }else{   
                    //会员用户
                    //根据退款时间，相应扣款和退款，并做完整的数据记录
                	frManager.memberRefund(arenaId,playerId,fieldReservationId, cancelDate);
                }
                
                //应战方
                if(ObjectUtil.isNotNull(guestPlayerId)){    
                    MemberShipResult gmsResult = msMgr.getPlayerMemeberShipInfo(arenaId,guestPlayerId);
                    if(ObjectUtil.isNull(gmsResult.getArenaMember())){  
                        //非会员用户
                    }else{   
                        //会员用户
                        //根据退款时间，相应扣款和退款，并做完整的数据记录
                        frManager.memberRefund(arenaId,guestPlayerId,fieldReservationId, cancelDate);
                    }
                }
                
                //2.标记为取消预订
                frManager.adminCancelReservation(fieldReservationId, cancelDate);
                
                response.setStatus(JsonResponse.SUCCESS);
                response.setMessage("取消成功");
            }else{
                response.setStatus(JsonResponse.ERROR);
                response.setMessage("并不存在当前预订信息");
                return response;
            }
            
        }else{
            response.setStatus(JsonResponse.ERROR);
            response.setMessage("parameters contains null");
            return response;
        }
        return response;
    }
    
    
//    //场馆人员 约战-应战方应战  具体场地的场次
//    /**
//     * 管理员操作 应战方 应战
//     * 会员：自动完成扣款，并做记录。
//     * 非会员：不做金额操作
//     */
//    @POST
//    @Path("/adminOperateAcceptBattle/{identity}/{appId}/{apptoken}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
//    public JsonResponse adminOperateAcceptBattle(FieldReservationRequest request) throws DomainNotFoundException {
//        JsonResponse response = new JsonResponse(); 
//        Long arenaId = request.getArenaId();
//        Long fieldReservationId = request.getFieldReservationId();
//        Long guestPlayerId = request.getGuestPlayerId();
//        
//        if(ObjectUtil.isNotNull(arenaId) && ObjectUtil.isNotNull(fieldReservationId) && ObjectUtil.isNotNull(guestPlayerId)){
//            
//            //1.检查是否是已预订了的场次
//            if(frManager.checkFieldRevsersionExist(fieldReservationId)){
//            	FieldReservationDetail frd = frManager.getArenasFieldsReservationDetail(fieldReservationId);
//            	request.setFcsId(frd.getFieldChargeStandardId());
//                MemberShipResult msResult = msMgr.getPlayerMemeberShipInfo(arenaId,guestPlayerId);
//                if(ObjectUtil.isNull(msResult.getArenaMember())){  
//                    //非会员用户
//                	frManager.acceptBattleFieldReservation(request, PaymentStatus.ADMINORDERUNPAID);
//                	
//                }else{   
//                    //会员用户
//                	request.setMemberId(msResult.getArenaMember().getMemberId());
//                	frManager.acceptBattleFieldReservation(request, PaymentStatus.YINGZHANPAID);
//                }
//                response.setStatus(JsonResponse.SUCCESS);
//                response.setMessage("应战成功");
//            }else{
//                response.setStatus(JsonResponse.ERROR);
//                response.setMessage("并不存在当前预订信息");
//                return response;
//            }
//            
//        }else{
//            response.setStatus(JsonResponse.ERROR);
//            response.setMessage("parameters contains null");
//            return response;
//        }
//        return response;
//    }
    
    //场馆人员 约战-移除约战的一支球队
    /**
     * 管理员操作 移除约战的一支球队
     * 会员：被移除后 自动退款
     * 非会员：直接移除
     * 如果被移除球队是之前的订场球队，那么剩下的另一只球队被设置成当前订场球队（客队=>主队）
     */
    @POST
    @Path("/adminRemoveBattleTeam/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public JsonResponse adminRemoveBattleTeam(FieldReservationRequest request) throws DomainNotFoundException {
        JsonResponse response = new JsonResponse(); 
        Long arenaId = request.getArenaId();
        Long fieldReservationId = request.getFieldReservationId();
        Long playerId = request.getPlayerId();
        Long guestPlayerId = request.getGuestPlayerId();
        Long removePlayerId = request.getRemovePlayerId();
        
        if(ObjectUtil.isNotNull(arenaId) && ObjectUtil.isNotNull(fieldReservationId) &&
           ObjectUtil.isNotNull(playerId) && ObjectUtil.isNotNull(guestPlayerId)	
        		&& ObjectUtil.isNotNull(removePlayerId)){
            
            //1.检查当前用户是否预订了该场次
            if(frManager.checkFieldRevsersionExist(fieldReservationId)){
            	
                    //移除
                    frManager.removeTeamOfBattle(request);
                    response.setStatus(JsonResponse.SUCCESS);
                    response.setMessage("移除球队成功");
           }else{
                response.setStatus(JsonResponse.ERROR);
                response.setMessage("并不存在当前预订信息");
                return response;
            }
            
        }else{
            response.setStatus(JsonResponse.ERROR);
            response.setMessage("parameters contains null");
            return response;
        }
        return response;
    }
    
    @POST
    @Path("/removeWechatGuestBattle/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse removeWechatGuestBattle(RemoveGuestBattle request) throws DomainNotFoundException {
    	FieldReservationDetail reservation = frManager.getArenasFieldsReservationDetail(request.getFieldReservationId());
    	Long hostPlayerId = reservation.getPlayerId();
    	Long arenaId = 52L;
    	Long guestPlayerId = request.getGuestPlayerId();
    	Long reservationId = request.getFieldReservationId();
    	Long removePlayerId = guestPlayerId;
    
    	
    	FieldReservationRequest fieldReservationRequest = new FieldReservationRequest();
    	fieldReservationRequest.setArenaId(arenaId);
    	fieldReservationRequest.setFieldReservationId(reservationId);
    	fieldReservationRequest.setPlayerId(hostPlayerId);
    	fieldReservationRequest.setGuestPlayerId(guestPlayerId);
    	fieldReservationRequest.setRemovePlayerId(removePlayerId);
    	
    	return adminRemoveBattleTeam(fieldReservationRequest);
    }
    
    //约战-应战方应战  具体场地的场次
    /**
     * 场馆后台/微信用户/app 应战方 应战
     * 会员：自动完成扣款，并做记录。
     * 非会员：记录预定，未支付
     */
    @POST
    @Path("/acceptBattle/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public JsonResponse acceptBattle(FieldReservationRequest request) throws DomainNotFoundException {
        JsonResponse response = new JsonResponse(); 
        Long arenaId = request.getArenaId();
        Long fieldReservationId = request.getFieldReservationId();
        Long guestPlayerId = request.getGuestPlayerId();
        String operateType = request.getOperateType();
        
        if(ObjectUtil.isNotNull(arenaId) && ObjectUtil.isNotNull(fieldReservationId) 
        	&& ObjectUtil.isNotNull(guestPlayerId) && ObjectUtil.isNotNull(operateType)){
        	
        	//1.检查是否是已预订了的场次
            if(frManager.checkFieldRevsersionExist(fieldReservationId)){
            	FieldReservationDetail frd = frManager.getArenasFieldsReservationDetail(fieldReservationId);
            	request.setFcsId(frd.getFieldChargeStandardId());
                MemberShipResult msResult = msMgr.getPlayerMemeberShipInfo(arenaId,guestPlayerId);
                if(ObjectUtil.isNull(msResult.getArenaMember())){  
                    
                	//非会员用户
                	if("admin".equals(operateType)){//场馆后台操作
                		frManager.acceptBattleFieldReservation(request, PaymentStatus.ADMINORDERUNPAID);
                	}
                	if("weChat".equals(operateType)){//用户通过微信操作
                		frManager.acceptBattleInfo(operateType,request.getFieldReservationId(), request.getGuestPlayerId(),PaymentStatus.UNPAID);
                	}
                	if("app".equals(operateType)){//用户通过app操作
                		frManager.acceptBattleInfo(operateType,request.getFieldReservationId(), request.getGuestPlayerId(),PaymentStatus.UNPAID);
                	}
                }else{   
                    //会员用户
                	request.setMemberId(msResult.getArenaMember().getMemberId());
                	
                	if("admin".equals(operateType)){//场馆后台操作
                		frManager.acceptBattleFieldReservation(request, PaymentStatus.YINGZHANPAID);
                	}
                	if("weChat".equals(operateType)){//用户通过微信操作
                		frManager.acceptBattleFieldReservation(request, PaymentStatus.YINGZHANWECHATPAID);
                	}
                	if("app".equals(operateType)){//用户通过app操作
                		frManager.acceptBattleFieldReservation(request, PaymentStatus.APPYINGZHANPAID);
                	}
                }
                response.setStatus(JsonResponse.SUCCESS);
                response.setMessage("应战成功");
            }else{
                response.setStatus(JsonResponse.ERROR);
                response.setMessage("并不存在当前预订信息");
                return response;
            }
            
        }else{
            response.setStatus(JsonResponse.ERROR);
            response.setMessage("parameters contains null");
            return response;
        }
        return response;
    }
    
    /**
     * 场馆后台/微信用户/app 预定 具体场地的场次
     * 会员 预定场次并完成自动扣款
     * 非会员  预定场次 不自动扣款 但记录 支付状态为 管理员代理预定未支付
     */
    @POST
    @Path("/reserveField/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public UnPaidReserveFieldResponse reserveField(FieldReservationRequest request) throws DomainNotFoundException {
    	UnPaidReserveFieldResponse response = new UnPaidReserveFieldResponse(); 
        Long arenaId = request.getArenaId();
        Long fieldChargeStandardId = request.getFcsId();
        String type = request.getType();
        Long playerId = request.getPlayerId();
        String useDate = request.getUseDate();
        String operateType = request.getOperateType();
        
        if(ObjectUtil.isNotNull(playerId) && ObjectUtil.isNotNull(arenaId) && ObjectUtil.isNotNull(fieldChargeStandardId)
                && ObjectUtil.isNotNull(useDate) && ObjectUtil.isNotNull(type) && ObjectUtil.isNotNull(operateType)){
            
            if(!cMgr.checkPlayerExist(playerId)){
                response.setStatus(JsonResponse.ERROR);
                response.setMessage("playerId not exist");
                return response;
            }
            if(!cMgr.checkArenaExist(arenaId)){
                response.setStatus(JsonResponse.ERROR);
                response.setMessage("arenaId not eixst");
                return response;
            }
            
            //检查当前场次是否被预定
            if(frManager.checkFieldRevsersionExist(fieldChargeStandardId, useDate)){
                //已经被预定了，请选择其他场次
                response.setStatus(JsonResponse.ERROR);
                response.setMessage("抱歉，当前场次已经被预定，请选择其他场次");
                return response;
            }else{
            	//完成预定
            	MemberShipResult msResult = msMgr.getPlayerMemeberShipInfo(arenaId,playerId);
                
                if(msResult.isMemberOfArena()){
                	
                	//当前球员是球场注册付费会员，则完成预定并从充值账户扣款
                    request.setMemberId(msResult.getArenaMember().getMemberId());
                    frManager.memberReserveField(request);
                    response.setStatus(JsonResponse.SUCCESS);
                    if("admin".equals(operateType)){
                        response.setMessage("会员用户，场馆操作-预定成功，并完成自动扣款");
                    }
                    if("weChat".equals(operateType)){
                        response.setMessage("会员用户-微信预定成功，并完成自动扣款");
                    }
                    if("app".equals(operateType)){
                    	response.setMessage("会员用户-APP预定成功，并完成自动扣款");
                    }
                }else{
                	if("admin".equals(operateType)){
                		//当前球员非球场会员，则只完成预定，并记录为管理员代预定未付款状态
                        frManager.reserveField(request, BookStatus.BOOK, PaymentStatus.ADMINORDERUNPAID);
                        response.setStatus(JsonResponse.SUCCESS);
                        response.setMessage("非会员-场馆操作-场次预定记录成功");
                	}
                	if("weChat".equals(operateType)){
                		//完成预定
                    	FieldReservation fieldReservation = frManager.reserveField(request, BookStatus.UNBOOK, PaymentStatus.UNPAID);
                    	response.setFieldReservationId(fieldReservation.getId());
                        response.setStatus(JsonResponse.SUCCESS);
                        response.setMessage("非会员-微信用户-场次预定记录成功");
                    }
                    if("app".equals(operateType)){
                    	//完成预定
                    	FieldReservation fieldReservation = frManager.reserveField(request, BookStatus.UNBOOK, PaymentStatus.UNPAID);
                    	response.setFieldReservationId(fieldReservation.getId());
                        response.setStatus(JsonResponse.SUCCESS);
                        response.setMessage("非会员-微信用户-场次预定记录成功");
                    }
                    
                }
            }
        }else{
            response.setStatus(JsonResponse.ERROR);
            response.setMessage("parameters can not null");
        }
        return response;
    }
    
    
    /**
     * 场馆后台创建长期自动预定
     * 2016.04.10
     * @throws Exception 
     */
    @POST
    @Path("/createAutoReservationJob/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public JsonResponse createAutoReservationJob(FieldReservationRequest request) throws Exception {
    	JsonResponse response = new JsonResponse(); 
        
    	Long arenaId = request.getArenaId();
        Long fieldChargeStandardId = request.getFcsId();
        Long playerId = request.getPlayerId();
        Long fieldId = request.getFieldId();
        Integer dayOfWeek = request.getDayOfWeek();
        String type = request.getType();
        Integer videoStatus = request.getVideoStatus();
        
        if(ObjectUtil.isNotNull(playerId) && ObjectUtil.isNotNull(arenaId)
        		&& ObjectUtil.isNotNull(fieldChargeStandardId)
                && ObjectUtil.isNotNull(dayOfWeek) && ObjectUtil.isNotNull(fieldId)
                && ObjectUtil.isNotNull(type) && ObjectUtil.isNotNull(videoStatus)){
            
            if(!cMgr.checkPlayerExist(playerId)){
                response.setStatus(JsonResponse.ERROR);
                response.setMessage("playerId not exist");
                return response;
            }
            if(!cMgr.checkArenaExist(arenaId)){
                response.setStatus(JsonResponse.ERROR);
                response.setMessage("arenaId not eixst");
                return response;
            }
            
            
            //创建长期自动化订场任务
            request.setType(type);
            //根据参数day(一周的第几天)来设置使用日期
            request.setBookStatus(BookStatus.BOOK);
            request.setPaymentStatus(PaymentStatus.ADMINORDERUNPAID);
            request.setVideoStatus(videoStatus);
            frManager.createAutoReservationJob(request);
            
            
//            String useDate = DateUtil.formatShortDate(DateUtil.getNextWeekDay(DateUtil.getCurrentDate(), dayOfWeek));
//            request.setUseDate(useDate);
//            request.setVideoStatus(0);
//            frManager.reserveField(request, BookStatus.BOOK, PaymentStatus.ADMINORDERUNPAID);
            
        }else{
            response.setStatus(JsonResponse.ERROR);
            response.setMessage("parameters can not null");
        }
        return response;
    }
    
    
    /**
     * 场馆后台-获取创建长期自动预定列表
     * 2016.04.11
     * @throws Exception 
     */
    @GET
    @Path("/getAutomationReservationJobList/{arenaId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public FieldReservationResponse getAutomationReservationJobList(@PathParam("arenaId") Long arenaId){
        FieldReservationResponse response = new FieldReservationResponse(); 
        
        if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)){
            
            response.setAutomationReservationJobList(frManager.getAutomationReservationJobList(arenaId));
            response.setStatus(JsonResponse.SUCCESS);
        }else{
            response.setStatus(JsonResponse.ERROR);
            response.setMessage("parameters contains null or not exist");
        }
        return response;
    }
    
    
    /**
     * 场馆后台-删除创建长期自动预定项
     * 2016.04.11
     * @throws Exception 
     */
    @DELETE
	@Path("/deleteAutoReservationJobById/{autoJobId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse deleteAutoReservationJobById(@PathParam("autoJobId") Long autoJobId) {
		JsonResponse response = new JsonResponse();

		if (ObjectUtil.isNotNull(autoJobId)) {

			frManager.deleteAutoReservationJobById(autoJobId);
			response.setMessage("删除 自动化预定场次任务 成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("autoJobId can not null or not exist");
			return response;
		}
	}
    
    /**
     * 场馆后台-暂停 创建长期自动预定项
     * 2016.06.03
     * @throws Exception 
     */
    @POST
	@Path("/suspendAutoReservationJobById/{autoJobId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse suspendAutoReservationJobById(@PathParam("autoJobId") Long autoJobId) {
		JsonResponse response = new JsonResponse();

		if (ObjectUtil.isNotNull(autoJobId)) {
			frManager.updateAutoReservationJobStatusById(autoJobId, AutomationJobStatus.INACTIVE);
			response.setMessage("暂停 自动化预定场次任务 成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("autoJobId can not null or not exist");
			return response;
		}
	}
    /**
     * 场馆后台-恢复创建长期自动预定项
     * 2016.06.03
     * @throws Exception 
     */
    @POST
	@Path("/resumeAutoReservationJobById/{autoJobId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse resumeAutoReservationJobById(@PathParam("autoJobId") Long autoJobId) {
		JsonResponse response = new JsonResponse();

		if (ObjectUtil.isNotNull(autoJobId)) {

			frManager.updateAutoReservationJobStatusById(autoJobId, AutomationJobStatus.ACTIVE);
			response.setMessage("恢复 自动化预定场次任务 成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("autoJobId can not null or not exist");
			return response;
		}
	}
    
    /**
     * 获取微信配置
     * @param request
     * @return
     */
    @POST
	@Path("/findWechatConfigByArenaId/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public WechatConfigResponse findWechatConfigByArenaId(WechatConfigRequest request) {
    	WechatConfigResponse response = new WechatConfigResponse();
    	WechatConfig wechatConfig = amMgr.getWechatConfigByArenaId(request.getArenaId());
    	response.setWechatConfig(wechatConfig);
    	return response;
    }
    
    /**
     * 获取微信配置
     * @param request
     * @return
     */
    @GET
	@Path("/getWechatConfigByArenaId/{arenaId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public WechatConfigResponse getWechatConfigByArenaId(@PathParam("arenaId") Long arenaId) {
    	WechatConfigResponse response = new WechatConfigResponse();
    	WechatConfig wechatConfig = amMgr.getWechatConfigByArenaId(arenaId);
    	response.setWechatConfig(wechatConfig);
    	response.setStatus(JsonResponse.SUCCESS);
    	return response;
    }
    
}