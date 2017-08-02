package com.footballer.rest.api.v2.restfulService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v2.manager.UserShareRecordManager;
import com.footballer.rest.api.v2.vo.UserShareRecord;
import com.footballer.util.JacksonUtil;

@Path("/mobile/v2/userShare-service")
public class UserShareService {
	
	private static Logger logger = LoggerFactory.getLogger(UserShareService.class);
	
	@Autowired
	private UserShareRecordManager usrMgr;
	
	@POST
	@Path("/saveUserShareRecord/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveUserShareRecord(UserShareRecord usr){
		JsonResponse jp = new JsonResponse();
		try{
			usrMgr.saveUserShareRecord(usr);
			jp.setStatus(JsonResponse.SUCCESS);
			return jp;
		}catch(Exception e){
			logger.error("saveUserComments error,param:"+JacksonUtil.toJson(usr),e);
			jp.setStatus(JsonResponse.ERROR);
			jp.setMessage(e.getMessage());
			return jp;
		}
	}
	
}
