package com.footballer.rest.api.v2.restfulService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v2.manager.UserSupportsManager;
import com.footballer.rest.api.v2.request.BaseIdRequest;
import com.footballer.rest.api.v2.request.SupprotRequest;
import com.footballer.rest.api.v2.vo.Support;
import com.footballer.util.JacksonUtil;
import com.footballer.util.ObjectUtil;

@Path("/mobile/v2/userSupports-service")
public class UserSupportsService {

	private static Logger logger = LoggerFactory.getLogger(UserSupportsService.class);

	@Autowired
	private UserSupportsManager usMgr;

	@POST
	@Path("/addSupportToVideoItem/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse addSupportToVideoItem(Support s) {
		JsonResponse jr = new JsonResponse();
		if (ObjectUtil.isNull(s) || ObjectUtil.isNull(s.getPlayerId()) || ObjectUtil.isNull(s.getVideoId())) {
			jr.setStatus(JsonResponse.ERROR);
			jr.setMessage("parameters get is null value");
			return jr;
		}

		usMgr.addSupportToVideoItem(s);
		jr.setStatus(JsonResponse.SUCCESS);
		return jr;
	}
	
	@POST
	@Path("/addSupportToMomentItem/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse addSupportToMomentItem(Support s) {
		JsonResponse jr = new JsonResponse();
		if (ObjectUtil.isNull(s) || ObjectUtil.isNull(s.getPlayerId()) || ObjectUtil.isNull(s.getMomentId())) {
			jr.setStatus(JsonResponse.ERROR);
			jr.setMessage("parameters get is null value");
			return jr;
		}

		usMgr.addSupportToMomentItem(s);
		jr.setStatus(JsonResponse.SUCCESS);
		return jr;
	}
	

	@POST
	@Path("/saveUserSupportToCommentItem/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveUserSupportToCommentItem(Support s) {
		JsonResponse jr = new JsonResponse();
		if (ObjectUtil.isNull(s) || ObjectUtil.isNull(s.getPlayerId()) || ObjectUtil.isNull(s.getCommentId())) {
			jr.setStatus(JsonResponse.ERROR);
			jr.setMessage("parameters get is null value");
			return jr;
		}

		usMgr.saveUserSupportToCommentItem(s);
		jr.setStatus(JsonResponse.SUCCESS);
		return jr;
	}
	
	@POST
	@Path("/removeUserSupportFromVideoItem/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse removeUserSupportFromVideoItem(SupprotRequest req) {
		JsonResponse jr = new JsonResponse();
		Long playerId = req.getPlayerId();
		Long videoId = req.getVideoId();
				
		if (ObjectUtil.isNull(videoId) || ObjectUtil.isNull(playerId)) {
			logger.error("removeUserSupportFromVideoItem error,param:"+JacksonUtil.toJson(req));
			jr.setStatus(JsonResponse.ERROR);
			jr.setMessage("params is null ");
			return jr;
		}
		usMgr.removeUserSupportFromVideoItem(req);
		jr.setStatus(JsonResponse.SUCCESS);
		return jr;
	}
	
	@POST
	@Path("/removeUserSupportFromMomentItem/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse removeUserSupportFromMomentItem(SupprotRequest req) {
		JsonResponse jr = new JsonResponse();
		Long playerId = req.getPlayerId();
		Long momentId = req.getMomentId();
				
		if (ObjectUtil.isNull(momentId) || ObjectUtil.isNull(playerId)) {
			logger.error("removeUserSupportFromMomentItem error,param:"+JacksonUtil.toJson(req));
			jr.setStatus(JsonResponse.ERROR);
			jr.setMessage("params is null ");
			return jr;
		}
		usMgr.removeUserSupportFromMomentItem(req);
		jr.setStatus(JsonResponse.SUCCESS);
		return jr;
	}
	
	@POST
	@Path("/toggleUserSupportStatus/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse toggleUserSupportStatus(BaseIdRequest req) {
		JsonResponse jr = new JsonResponse();
		Long supportId = req.getId();
		if (ObjectUtil.isNull(supportId) || supportId.toString() == "") {
			jr.setStatus(JsonResponse.ERROR);
			jr.setMessage("supportId is null ");
			return jr;
		}

		usMgr.toggleUserSupportStatus(supportId);
		jr.setStatus(JsonResponse.SUCCESS);
		return jr;
	}
}
