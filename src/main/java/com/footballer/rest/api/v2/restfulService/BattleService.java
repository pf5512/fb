package com.footballer.rest.api.v2.restfulService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.response.FieldResponse;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v2.Response.BattleResponse;
import com.footballer.rest.api.v2.manager.BattleManager;
import com.footballer.rest.api.v2.manager.CommonManager;
import com.footballer.rest.api.v2.request.AvaliableFieldRequest;
import com.footballer.rest.api.v2.request.BattleRequest;
import com.footballer.rest.api.v2.request.RemoveWeChatBattleWish;
import com.footballer.rest.api.v2.request.battleReservationRequest;
import com.footballer.util.ObjectUtil;

@Path("/mobile/v2/battle-service")
public class BattleService {

	@Autowired
	private BattleManager bwMgr;

	@Autowired
	private CommonManager cMgr;

	// 获取微信未来7天的登记约战的球队信息(包括当天)
	@GET
	@Path("/findWeChatBattleWishs/{arenaId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public BattleResponse findWeChatBattleWishs(@PathParam("arenaId") Long arenaId) {
		BattleResponse response = new BattleResponse();

		if (ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {

			response.setBattleWishList(bwMgr.findWeChatBattleWishsOfNext7Days(arenaId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}

	//获取当前球场约战按使用频率排序的常用客户名单列表
	@GET
	@Path("/findBattleActiveCustomersOrderByTimes/{arenaId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public BattleResponse findBattleActiveCustomersOrderByTimes(@PathParam("arenaId") Long arenaId) {
		BattleResponse response = new BattleResponse();

		if (ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {

			response.setBattleActiveCustomersList(bwMgr.findBattleActiveCustomersOrderByTimes(arenaId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}

	// 录入微信约战意愿
	@POST
	@Path("/addWeChatBattleWish/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public BattleResponse addWeChatBattleWish(BattleRequest request) {
		BattleResponse response = new BattleResponse();

		if (ObjectUtil.isNotNull(request) && ObjectUtil.isNotNull(request.getArenaId())
				&& ObjectUtil.isNotNull(request.getDate()) && ObjectUtil.isNotNull(request.getPlayerId())
				&& ObjectUtil.isNotNull(request.getTeamId())) {
			response.setBattleWishId(bwMgr.addWeChatBattleWish(request));
			response.setMessage("录入约球意愿成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameter can not null ");
			return response;
		}
	}

	// 移除微信约战意愿
	@POST
	@Path("/removeWeChatBattleWish/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse removeWeChatBattleWish(RemoveWeChatBattleWish request) {
		JsonResponse response = new JsonResponse();
		long battleWishId = request.getBattleWishId();
		if (ObjectUtil.isNotNull(battleWishId)) {

			bwMgr.removeBattleWishById(battleWishId);
			response.setMessage("删除 约战意愿 成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("battleWishId can not null or battleWishId not exist");
			return response;
		}

	}
	
	// 根据被拖动选择的 约战信息后，获取当天可用场次场地信息
	@POST
	@Path("/getAvaliableFieldsOfDay/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public FieldResponse getAvaliableFieldsOfDay(AvaliableFieldRequest request) {
		FieldResponse response = new FieldResponse();
		Long arenaId = request.getArenaId();
		String startTime = request.getStartTime();
		String useDate= request.getUseDate();
		
		if (ObjectUtil.isNotNull(arenaId)&& ObjectUtil.isNotNull(startTime)&& ObjectUtil.isNotNull(useDate)) {

			response.setAvaliableFieldsList(bwMgr.getAvaliableFieldsOfDay(request));
			response.setMessage("获取可用场地信息");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}

	}
	
	//完成被拖动选择的 2支约战球队的场地预定，并移除处于约战意愿中得记录(如果是已经创建的约战预约则直接加入应战球队信息)
	@POST
	@Path("/arenaOwnerAddBattleReservation/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public FieldResponse arenaOwnerAddBattleReservation(battleReservationRequest request) {
		FieldResponse response = new FieldResponse();
		Long arenaId = request.getArenaId();
		Long battleWishId = request.getBattleWishId();
		Long targetBattleWishId = request.getTargetBattleWishId();
		Long fieldId = request.getFieldId();
		Long fcsId = request.getFcsId();
		Long playerId = request.getPlayerId();
		Long guestPlayerId = request.getGuestPlayerId();
		String useDate= request.getUseDate();
		
		if (ObjectUtil.isNotNull(arenaId) && ObjectUtil.isNotNull(playerId) && ObjectUtil.isNotNull(guestPlayerId) &&
			ObjectUtil.isNotNull(fieldId) && ObjectUtil.isNotNull(fcsId) && ObjectUtil.isNotNull(useDate)) {

			//插入约战预定场地
			bwMgr.arenaOwnerAddBattleReservation(request);
			//根据约战双方检查 移除相应得约战意愿
			if(ObjectUtil.isNotNull(battleWishId)){
				bwMgr.removeBattleWishById(battleWishId);
			}
			if(ObjectUtil.isNotNull(targetBattleWishId)){
				bwMgr.removeBattleWishById(targetBattleWishId);
			}
			
			response.setMessage("完成约战后台预订，并移除相关意愿或记录");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}

	}
	
}
