package com.footballer.rest.api.v1.restfulService;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.dao.ArenaDao;
import com.footballer.rest.api.v1.dao.FieldDao;
import com.footballer.rest.api.v1.domain.ArenaField;
import com.footballer.rest.api.v1.response.ArenaResponse;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.vo.Arena;
import com.footballer.rest.api.v1.vo.Field;
import com.footballer.util.JacksonUtil;
import com.footballer.util.ObjectUtil;

@Path("/mobile/v1/arena-service")
public class ArenaService {

	private static Logger logger = LoggerFactory.getLogger(ArenaService.class);
	
	@Autowired
	public ArenaDao arenaDao;
	
	@Autowired
	public FieldDao fieldDao;

	@POST
	@Path("/create/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArenaResponse create(Arena arena) {

		ArenaResponse response = new ArenaResponse();
		try{
			arenaDao.save(arena);
			response.setStatus(JsonResponse.SUCCESS);
			response.setMessage("save arena success");
			return response;
		}catch(Exception e){
			logger.error("create arena error,param:"+JacksonUtil.toJson(arena),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	

	@PUT
	@Path("/update/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArenaResponse update(Arena arena) {

		ArenaResponse response = new ArenaResponse();
		try{
			arenaDao.update(arena);
			response.setStatus(JsonResponse.SUCCESS);
			response.setMessage("save Arena success");
			return response;
		}catch(Exception e){
			logger.error("update arena error,param:"+JacksonUtil.toJson(arena),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GET
	@Path("/findById/{id}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArenaResponse findArenaById(@PathParam("id") Long id) {

		ArenaResponse response = new ArenaResponse();

		if (ObjectUtil.isNull(id)) {
			logger.warn("Arena id can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("Arena id can not be empty");
			return response;
		}
		try{
			Arena arena = arenaDao.findById(Arena.class, id);
			List<Field> fieldList = arenaDao.findAllFieldsById(id);
			
			response.setStatus(JsonResponse.SUCCESS);
			response.setArena(arena);
			response.setFieldList(fieldList);
			
			return response;
		
		}catch(Exception e){
			logger.error("update arena error,param:id:"+id,e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	@GET
	@Path("/findAllArenaByName/{name}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArenaResponse findAllArenaByName(@PathParam("name") String name) {
		ArenaResponse response = new ArenaResponse();
		try{
			
			List<ArenaField> arenaFields = arenaDao.findAllArenasByName(name);
			response.setStatus(JsonResponse.SUCCESS);
			response.setArenaFields(arenaFields);
			return response;
		}catch(Exception e){
			logger.error("findAllArenaByName error,param:name:"+name,e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
	
	@GET
	@Path("/findAllArena/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArenaResponse findAllArena() {
		ArenaResponse response = new ArenaResponse();
		try{
			List<ArenaField> arenaFields = arenaDao.findAllArenas();
			response.setStatus(JsonResponse.SUCCESS);
			response.setArenaFields(arenaFields);
			return response;
		}catch(Exception e){
			logger.error("findAllArena error",e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
	}
	
}
