package com.footballer.rest.api.v2.restfulService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v2.Response.BattleResponse;
import com.footballer.rest.api.v2.Response.SoloResponse;
import com.footballer.rest.api.v2.manager.CommonManager;
import com.footballer.rest.api.v2.manager.SoloManager;
import com.footballer.rest.api.v2.request.WeChatSoloWishRequest;
import com.footballer.rest.api.v2.request.SoloRequest;
import com.footballer.util.ObjectUtil;

@Path("/mobile/v2/solo-service")
public class SoloService {

	@Autowired
	private SoloManager slMgr;

	@Autowired
	private CommonManager cMgr;

	// 获取微信未来7天的登记单飞的球员列表(包括当天)
	@GET
	@Path("/findWeChatSoloWishes/{arenaId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public SoloResponse findWeChatSoloWishes(@PathParam("arenaId") Long arenaId) {
		SoloResponse response = new SoloResponse();

		if (ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {

			response.setSoloWishList(slMgr.findSoloWishsOfNext7Days(arenaId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}

	// 录入微信单飞意愿
	@POST
	@Path("/addWeChatSoloWish/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public SoloResponse addWeChatSoloWish(SoloRequest request) {
		SoloResponse response = new SoloResponse();

		if (ObjectUtil.isNotNull(request) && ObjectUtil.isNotNull(request.getArenaId())
				&& ObjectUtil.isNotNull(request.getDate()) && ObjectUtil.isNotNull(request.getPlayerId()) ) {
			
			response.setSoloWishId(slMgr.addWeChatSoloWish(request));
			response.setMessage("录入约球意愿成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameter can not null ");
			return response;
		}
	}

	// 移除微信单飞意愿
	@POST
	@Path("/removeWeChatSoloWish/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse removeWeChatSoloWish(WeChatSoloWishRequest request) {
		JsonResponse response = new JsonResponse();
		long soloWishId = request.getSoloWishId();
		if (ObjectUtil.isNotNull(soloWishId)) {

			slMgr.removeSoloWishById(soloWishId);
			response.setMessage("删除 单飞意愿 成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("soloWishId can not null or battleWishId not exist");
			return response;
		}

	}
	
	// --------------------------------------   后台管理 ------------------------------------//
	
	//获取当前球场<单飞>按使用频率排序的常用客户名单列表
	@GET
	@Path("/findSoloActiveCustomersOrderByTimes/{arenaId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public SoloResponse findSoloActiveCustomersOrderByTimes(@PathParam("arenaId") Long arenaId) {
		SoloResponse response = new SoloResponse();

		if (ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {
			response.setSoloActiveCustomersList(slMgr.findSoloActiveCustomersOrderByTimes(arenaId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	@POST
	@Path("/updateSoloWishDate/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse updateSoloWishDate(SoloRequest request) {
		JsonResponse response = new JsonResponse();
		Long soloWishId = request.getSoloWishId();
		String date= request.getDate();
		
		if (ObjectUtil.isNotNull(soloWishId)&& ObjectUtil.isNotNull(date)) {

			if(slMgr.updateSoloWishDate(request)>0){
				response.setMessage("更新单飞报名成功");
				response.setStatus(JsonResponse.SUCCESS);
				return response;
			}else{
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("更新单飞失败");
				return response;
			}
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}

	}
	
	
	@POST
	@Path("/recordAbsentSoloWish/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse recordAbsentSoloWish(WeChatSoloWishRequest request) {
		JsonResponse response = new JsonResponse();
		long soloWishId = request.getSoloWishId();
		if (ObjectUtil.isNotNull(soloWishId)) {

			slMgr.recordAbsentSoloWishById(soloWishId);
			response.setMessage("记录 单飞缺席 成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("soloWishId can not null or soloWishId not exist");
			return response;
		}

	}
	
	//	// 根据被拖动选择的 约战信息后，获取当天可用场次场地信息
//	@POST
//	@Path("/getAvaliableFieldsOfDay/{identity}/{appId}/{apptoken}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public FieldResponse getAvaliableFieldsOfDay(AvaliableFieldRequest request) {
//		FieldResponse response = new FieldResponse();
//		Long arenaId = request.getArenaId();
//		String startTime = request.getStartTime();
//		String useDate= request.getUseDate();
//		
//		if (ObjectUtil.isNotNull(arenaId)&& ObjectUtil.isNotNull(startTime)&& ObjectUtil.isNotNull(useDate)) {
//
//			response.setAvaliableFieldsList(bwMgr.getAvaliableFieldsOfDay(request));
//			response.setMessage("获取可用场地信息");
//			response.setStatus(JsonResponse.SUCCESS);
//			return response;
//		} else {
//			response.setStatus(JsonResponse.ERROR);
//			response.setMessage("parameters can not null");
//			return response;
//		}
//
//	}
//	
//	//完成被拖动选择的 2支约战球队的场地预定，并移除处于约战意愿中得记录(如果是已经创建的约战预约则直接加入应战球队信息)
//	@POST
//	@Path("/arenaOwnerAddBattleReservation/{identity}/{appId}/{apptoken}")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
//	public FieldResponse arenaOwnerAddBattleReservation(battleReservationRequest request) {
//		FieldResponse response = new FieldResponse();
//		Long arenaId = request.getArenaId();
//		Long battleWishId = request.getBattleWishId();
//		Long targetBattleWishId = request.getTargetBattleWishId();
//		Long fieldId = request.getFieldId();
//		Long fcsId = request.getFcsId();
//		Long playerId = request.getPlayerId();
//		Long guestPlayerId = request.getGuestPlayerId();
//		String useDate= request.getUseDate();
//		
//		if (ObjectUtil.isNotNull(arenaId) && ObjectUtil.isNotNull(playerId) && ObjectUtil.isNotNull(guestPlayerId) &&
//			ObjectUtil.isNotNull(fieldId) && ObjectUtil.isNotNull(fcsId) && ObjectUtil.isNotNull(useDate)) {
//
//			//插入约战预定场地
//			bwMgr.arenaOwnerAddBattleReservation(request);
//			//根据约战双方检查 移除相应得约战意愿
//			if(ObjectUtil.isNotNull(battleWishId)){
//				bwMgr.removeBattleWishById(battleWishId);
//			}
//			if(ObjectUtil.isNotNull(targetBattleWishId)){
//				bwMgr.removeBattleWishById(targetBattleWishId);
//			}
//			
//			response.setMessage("完成约战后台预订，并移除相关意愿或记录");
//			response.setStatus(JsonResponse.SUCCESS);
//			return response;
//		} else {
//			response.setStatus(JsonResponse.ERROR);
//			response.setMessage("parameters can not null");
//			return response;
//		}
//
//	}
	
}
