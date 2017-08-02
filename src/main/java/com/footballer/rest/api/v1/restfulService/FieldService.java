package com.footballer.rest.api.v1.restfulService;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.dao.FieldDao;
import com.footballer.rest.api.v1.response.ArenaResponse;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.vo.Field;
import com.footballer.util.JacksonUtil;

@Path("/mobile/v1/field-service")
public class FieldService {

	private static Logger logger = LoggerFactory.getLogger(FieldService.class);
	
	@Resource
	public FieldDao fieldDao;

	@POST
	@Path("/create/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArenaResponse create(Field field) {

		ArenaResponse response = new ArenaResponse();
		try{
			fieldDao.save(field);
		}catch(Exception e){
			logger.error("create field error,param:"+JacksonUtil.toJson(field),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
		response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("save field success");
		return response;
	}
	

	@PUT
	@Path("/update/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArenaResponse update(Field field) {

		ArenaResponse response = new ArenaResponse();
		try{
			fieldDao.update(field);
		}catch(Exception e){
			logger.error("create field error,param:"+JacksonUtil.toJson(field),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
		response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("save Field success");
		return response;
	}
	
//	@GET
//	@Path("/findById/{id}/{appId}/{apptoken}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public ArenaResponse findFieldById(@PathParam("id") String id) {
//
//		ArenaResponse response = new ArenaResponse();
//
//		if (ObjectUtil.isNull(id)) {
//			response.setStatus(JsonResponse.ERROR);
//			response.setMessage("Field id can not be empty");
//			return response;
//		}
//
//		Field field = fieldDao.findFieldById(id);
//		response.setStatus(JsonResponse.SUCCESS);
//		response.setField(field);
//		
//		return response;
//	}
//	
//	@GET
//	@Path("/findByName/{name}/{appId}/{apptoken}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public ArenaResponse findFieldByName(@PathParam("name") String name) {
//
//		ArenaResponse response = new ArenaResponse();
//
//		if (!StringUtils.hasText(name)) {
//			response.setStatus(JsonResponse.ERROR);
//			response.setMessage("Field name can not be empty");
//			return response;
//		}
//
//		List<Field> fieldlist = new ArrayList<Field>();
//		fieldlist = fieldDao.findFieldsByName(name);
//		response.setStatus(JsonResponse.SUCCESS);
//		response.setFieldList(fieldlist);
//		
//		return response;
//	}
	
//	@GET
//	@Path("/findFieldsByTeamId/{teamId}/{appId}/{apptoken}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public FieldResponse findFieldsByTeamId(@PathParam("teamId") String teamId) {
//
//		FieldResponse response = new FieldResponse();
//
//		if (!StringUtils.hasText(teamId)) {
//			response.setStatus(JsonResponse.ERROR);
//			response.setMessage("Team id can not be empty");
//			return response;
//		}
//
//		List<Field> fieldlist = new ArrayList<Field>();
//		fieldlist = teamfieldDao.findFieldsByTeamId(teamId);
//		response.setStatus(JsonResponse.SUCCESS);
//		response.setFieldList(fieldlist);
//		
//		return response;
//	}
	
}
