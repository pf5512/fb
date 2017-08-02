package com.footballer.rest.api.v2.restfulService;

import java.math.BigDecimal;
import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.notification.NotificationManager;
import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v2.Response.ArenaFieldResponse;
import com.footballer.rest.api.v2.manager.ArenaFieldManager;
import com.footballer.rest.api.v2.manager.CommonManager;
import com.footballer.rest.api.v2.request.FieldChargeStandardRequest;
import com.footballer.util.DateUtil;
import com.footballer.util.ObjectUtil;

@Path("/mobile/v2/arenaField-service")
public class ArenaFieldService {

	@Autowired
	private CommonManager cMgr;

	@Autowired
	private ArenaFieldManager afMgr;

	@Autowired
	public NotificationManager notificationManager;

	// 加载 场馆场次情况
	@GET
	@Path("/getArenaFieldsStandards/{arenaId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArenaFieldResponse getArenaFieldsStandards(@PathParam("arenaId") Long arenaId) {
		ArenaFieldResponse response = new ArenaFieldResponse();

		if (ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {

			response.setFieldChargeStandardList(afMgr.getArenaFieldChargeStandardsByArenaId(arenaId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("arenaId can not null or arena not exist");
			return response;
		}
	}

	// 加载 场次详细情况
	@GET
	@Path("/getFieldStandardDetail/{fieldChargeStandardId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArenaFieldResponse getFieldStandardDetail(@PathParam("fieldChargeStandardId") Long fieldChargeStandardId) {
		ArenaFieldResponse response = new ArenaFieldResponse();

		if (ObjectUtil.isNotNull(fieldChargeStandardId) && cMgr.checkArenaFieldStandardExist(fieldChargeStandardId)) {
			response.setFieldChargeStandard(afMgr.getFieldChargeStandardById(fieldChargeStandardId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("fieldChargeStandardId can not null or standard not exist");
			return response;
		}
	}

	// 新建 场馆场次情况
	@POST
	@Path("/saveArenaFieldStandard/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveArenaFieldStandard(FieldChargeStandardRequest fcs) {
		JsonResponse response = new JsonResponse();
		Long arenaId = fcs.getArenaId();
		Long fieldId = fcs.getFieldId();
		Date startTime = DateUtil.parseShortTime(fcs.getStartTime());
		Date endTime = DateUtil.parseShortTime(fcs.getEndTime());
		BigDecimal price = fcs.getPrice();
		BigDecimal barginPrice = fcs.getBarginPrice();

		if (ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId) && ObjectUtil.isNotNull(fieldId)
				&& cMgr.checkFieldExist(fieldId) && ObjectUtil.isNotNull(startTime) && ObjectUtil.isNotNull(endTime)
				&& ObjectUtil.isNotNull(price)) {

			afMgr.saveArenaFieldStandard(arenaId, fieldId, startTime, endTime, price, barginPrice);
			response.setMessage("新增场馆场次 成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null or arena not exist");
			return response;
		}
	}

	// 修改 场馆相同时段所有场地的场次情况
	@PUT
	@Path("/updateSameTimeShiftArenaFieldStandard/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse updateSameTimeShiftArenaFieldStandard(FieldChargeStandardRequest fcs) {
		JsonResponse response = new JsonResponse();
		Long arenaId = fcs.getArenaId();
		Long arenaFieldStandardId = fcs.getId();
		Date startTime = DateUtil.parseNoSecondTime(fcs.getStartTime());
		Date endTime = DateUtil.parseNoSecondTime(fcs.getEndTime());

		if (ObjectUtil.isNotNull(arenaId)&& cMgr.checkArenaExist(arenaId) 
			&& ObjectUtil.isNotNull(arenaFieldStandardId) && cMgr.checkArenaFieldStandardExist(arenaFieldStandardId)	
			&& ObjectUtil.isNotNull(startTime) && ObjectUtil.isNotNull(endTime)) {

			afMgr.updateSameTimeShiftArenaFieldStandard(arenaId, arenaFieldStandardId, startTime, endTime);
			response.setMessage("更新同时段场次情况 成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("ArenaId or other parameter can not null or Arena not exist");
			return response;
		}
	}
	
	//修改单独场地某个时间段得价格
	@PUT
	@Path("/updateArenaFieldStandardPriceByFCSId/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse updateArenaFieldStandardPriceByFCSId(FieldChargeStandardRequest fcs) {
		JsonResponse response = new JsonResponse();
		Long ArenaFieldStandardId = fcs.getId();
		BigDecimal price = fcs.getPrice();
		BigDecimal weekendPrice = fcs.getWeekendPrice();

		if (ObjectUtil.isNotNull(ArenaFieldStandardId) && cMgr.checkArenaFieldStandardExist(ArenaFieldStandardId) && ObjectUtil.isNotNull(price) && ObjectUtil.isNotNull(weekendPrice)) {

			afMgr.updateArenaFieldStandardPriceByFCSId(ArenaFieldStandardId, price, weekendPrice);
			response.setMessage("更新指定场次此价格 成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("ArenaFieldStandardId or other parameter can not null or ArenaFieldStandard not exist");
			return response;
		}
	}
	

	// 删除 场馆场次情况
	@DELETE
	@Path("/deleteArenaFieldStandard/{arenaFieldStandardId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse deleteArenaFieldStandard(@PathParam("arenaFieldStandardId") Long arenaFieldStandardId) {
		JsonResponse response = new JsonResponse();

		if (ObjectUtil.isNotNull(arenaFieldStandardId) && cMgr.checkArenaFieldStandardExist(arenaFieldStandardId)) {

			afMgr.deleteArenaFieldStandard(arenaFieldStandardId);
			response.setMessage("删除 场馆场次情况 成功");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("arenaFieldStandardId can not null or arenaFieldStandard not exist");
			return response;
		}
	}

}
