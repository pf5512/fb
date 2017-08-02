package com.footballer.rest.api.v2.restfulService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v2.manager.UserCommentsManager;
import com.footballer.rest.api.v2.vo.UserComments;
import com.footballer.util.JacksonUtil;

@Path("/mobile/v2/userComments-service")
public class UserCommentsService {
	
	private static Logger logger = LoggerFactory.getLogger(UserCommentsService.class);
	
	@Autowired
	private UserCommentsManager userCommentsManager;
	
	@POST
	@Path("/saveUserComments/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveUserComments(UserComments uc){
		JsonResponse jp = new JsonResponse();
		try{
			userCommentsManager.saveUserComments(uc);
			jp.setStatus(JsonResponse.SUCCESS);
			return jp;
		}catch(Exception e){
			logger.error("saveUserComments error,param:"+JacksonUtil.toJson(uc),e);
			jp.setStatus(JsonResponse.ERROR);
			jp.setMessage(e.getMessage());
			return jp;
		}
	}
	
	
	@POST
	@Path("/saveUserCommentsToVideoItem/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveUserCommentsToVideoItem(UserComments uc){
		JsonResponse jp = new JsonResponse();
		try{
			userCommentsManager.saveUserCommentsToVideoItem(uc);
			jp.setStatus(JsonResponse.SUCCESS);
			return jp;
		}catch(Exception e){
			logger.error("saveUserComments error,param:"+JacksonUtil.toJson(uc),e);
			jp.setStatus(JsonResponse.ERROR);
			jp.setMessage(e.getMessage());
			return jp;
		}
	}
	
	@POST
	@Path("/saveUserCommentsToMomentItem/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveUserCommentsToMomentItem(UserComments uc){
		JsonResponse jp = new JsonResponse();
		try{
			userCommentsManager.saveUserCommentsToMomentItem(uc);
			jp.setStatus(JsonResponse.SUCCESS);
			return jp;
		}catch(Exception e){
			logger.error("saveUserComments error,param:"+JacksonUtil.toJson(uc),e);
			jp.setStatus(JsonResponse.ERROR);
			jp.setMessage(e.getMessage());
			return jp;
		}
	}
	
}
