package com.footballer.rest.api.v2.restfulService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v2.manager.SystemManager;
import com.footballer.rest.api.v2.vo.AccessStats;
import com.footballer.util.JacksonUtil;

@Path("/mobile/v2/system-service")
public class SystemService {
	
	private static Logger logger = LoggerFactory.getLogger(SystemService.class);
	
	@Autowired
	private SystemManager sMgr;
	
	@POST
	@Path("/saveAccessRecord/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveAccessRecord(AccessStats as){
		JsonResponse jp = new JsonResponse();
		try{
			sMgr.saveAccessRecord(as);
			jp.setStatus(JsonResponse.SUCCESS);
			return jp;
		}catch(Exception e){
			logger.error("saveUserComments error,param:"+JacksonUtil.toJson(as),e);
			jp.setStatus(JsonResponse.ERROR);
			jp.setMessage(e.getMessage());
			return jp;
		}
	}
	
}
