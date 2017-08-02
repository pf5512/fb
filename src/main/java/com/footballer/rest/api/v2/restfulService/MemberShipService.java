package com.footballer.rest.api.v2.restfulService;

import java.math.BigDecimal;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import com.footballer.rest.api.v1.vo.Team;
import com.footballer.rest.api.v1.vo.enumType.FeeType;
import com.footballer.rest.api.v1.vo.enumType.PayMethod;
import com.footballer.rest.api.v2.Response.MemberShipResponse;
import com.footballer.rest.api.v2.manager.ArenaMemberManager;
import com.footballer.rest.api.v2.manager.CommonManager;
import com.footballer.rest.api.v2.manager.MemberShipManager;
import com.footballer.rest.api.v2.manager.PlayerAccountManager;
import com.footballer.rest.api.v2.request.ArenaMemberDiscountRequest;
import com.footballer.rest.api.v2.request.ArenaMemberRequest;
import com.footballer.rest.api.v2.result.MemberShipResult;
import com.footballer.rest.api.v2.vo.Account;
import com.footballer.rest.api.v2.vo.ArenaMemberDiscount;
import com.footballer.rest.api.v2.vo.enumType.UserType;
import com.footballer.util.DateUtil;
import com.footballer.util.ObjectUtil;


@Path("/mobile/v2/membership-service")
public class MemberShipService {
	
	private static Logger logger = LoggerFactory.getLogger(MemberShipService.class);
	
	@Autowired
	private MemberShipManager msManager;
	
	@Autowired
	private ArenaMemberManager amManager;
	
	@Autowired
	private CommonManager cMgr;
	
	@Autowired
	public NotificationManager notificationManager;
	
	@Autowired
	public PlayerAccountManager playerMgr;
	
	@Autowired
	public PlayerDao playerDao;
	
	//获取 当前用户的身份
    @GET
    @Path("/getPlayerMemeberShipInfo/{arenaId}/{playerId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public MemberShipResponse getPlayerMemeberShipInfo(@PathParam("arenaId") Long arenaId,@PathParam("playerId") Long playerId) {
    	MemberShipResponse response = new MemberShipResponse(); 
    	
    	if(ObjectUtil.isNotNull(playerId) && cMgr.checkArenaExist(arenaId)) {
    		
    		MemberShipResult msr = msManager.getPlayerMemeberShipInfo(arenaId,playerId);
    		response.setMemberShipResult(msr);
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaId or playerId can not null or arena not exist");
    		return response;
    	}
    }
    
	//获取 当前场馆的会员级别和折扣列表
    @GET
    @Path("/getArenaMemberDiscounts/{arenaId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public MemberShipResponse getArenaMemberDiscounts(@PathParam("arenaId") Long arenaId) {
    	MemberShipResponse response = new MemberShipResponse(); 
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {
    		
    		response.setArenaMemberDiscountList(msManager.getArenaMemberDiscountsByArenaId(arenaId));
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaId can not null or arena not exist");
    		return response;
    	}
    }

    //新建 场馆的会员等级，折扣，充值金额
    @POST
    @Path("/saveArenaMemberDiscount/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse saveArenaMemberDiscount(ArenaMemberDiscountRequest req) {
    	JsonResponse response = new JsonResponse(); 
    	Long arenaId = req.getArenaId();
    	Integer level = req.getLevel();
    	BigDecimal discount = req.getDiscount().abs();
    	BigDecimal fund = req.getFund().abs();
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)
    			&& ObjectUtil.isNotNull(level)
    			&& ObjectUtil.isNotNull(discount)
    			&& ObjectUtil.isNotNull(fund)) {
    		
    		msManager.saveArenaMemberDiscount(arenaId, level, discount, fund);
    		response.setMessage("新增会员级别和折扣信息 成功");
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaId can not null or arena not exist");
    		return response;
    	}
    }
    
    //修改 场馆的会员等级，折扣，充值金额
    @PUT
    @Path("/updateArenaMemberDiscount/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updateArenaMemberDiscount(ArenaMemberDiscountRequest req) {
    	JsonResponse response = new JsonResponse(); 
    	Long arenaMemberDiscountId = req.getArenaMemberDiscountId();
    	BigDecimal discount = req.getDiscount();
    	BigDecimal fund = req.getFund();
    	
    	if(ObjectUtil.isNotNull(arenaMemberDiscountId) && cMgr.checkArenaMemberDiscountExist(arenaMemberDiscountId)
    			&& ObjectUtil.isNotNull(discount)
    			&& ObjectUtil.isNotNull(fund)) {
    		
    		msManager.updateArenaMemberDiscount(arenaMemberDiscountId, discount.abs(), fund.abs());
    		response.setMessage("更新会员级别和折扣信息 成功");
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaMemberDiscountId or other parameter can not null or arenaMemberDiscount not exist");
    		return response;
    	}
    }
    
    //删除 场馆的会员等级，折扣，充值金额
    @DELETE
    @Path("/deleteArenaMemberDiscount/{arenaMemberDiscountId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse deleteArenaMemberDiscount(@PathParam("arenaMemberDiscountId") Long arenaMemberDiscountId) {
    	JsonResponse response = new JsonResponse(); 
    	
    	if(ObjectUtil.isNotNull(arenaMemberDiscountId) && cMgr.checkArenaMemberDiscountExist(arenaMemberDiscountId)) {
    		
    		msManager.deleteArenaMemberDiscount(arenaMemberDiscountId);
    		response.setMessage("删除 会员级别和折扣信息 成功");
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaMemberDiscountId can not null or arenaMemberDiscount not exist");
    		return response;
    	}
    }
    
    //获取 某个场馆所有得会员名单
    @GET
    @Path("/getArenaMemebersInfo/{arenaId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public MemberShipResponse getArenaMemebersInfo(@PathParam("arenaId") Long arenaId) {
    	MemberShipResponse response = new MemberShipResponse(); 
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {
    		response.setArenaMemberList(msManager.getArenaMemebersInfo(arenaId));
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaId can not null or arena not exist");
    		return response;
    	}
    }
    
    //获取 某个场馆所有<非>会员名单
    @GET
    @Path("/getArenaNonMemebersInfo/{arenaId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public MemberShipResponse getArenaNonMemebersInfo(@PathParam("arenaId") Long arenaId) {
    	MemberShipResponse response = new MemberShipResponse(); 
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {
    		response.setArenaNonMemberList(msManager.getArenaNonMemebersInfo(arenaId));
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaId can not null or arena not exist");
    		return response;
    	}
    }
    
    
    //获取 某个会员资金流水
    @GET
    @Path("/getArenaMemeberBalanceLines/{arenaMemberId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public MemberShipResponse getArenaMemeberBalanceLines(@PathParam("arenaMemberId") Long arenaMemberId) {
    	MemberShipResponse response = new MemberShipResponse(); 
    	
    	if(ObjectUtil.isNotNull(arenaMemberId) && cMgr.checkArenaMemeberExist(arenaMemberId)) {
    		response.setArenaMemberBalanceLinesList(msManager.getArenaMemeberBalanceLinesByArenaMemberId(arenaMemberId));
        	response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaMemberId can not ArenaMember or arena not exist");
    		return response;
    	}
    }
    
    //为会员 充值 或 扣款 
    @POST
    @Path("/manageArenaMemberFunds/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse manageArenaMemberFunds(ArenaMemberRequest request) {
    	JsonResponse response = new JsonResponse(); 
    	Long arenaMemberId = request.getArenaMemberId();
    	BigDecimal price = request.getPrice();
    	String comments = "";
    	FeeType feeType = FeeType.CREDIT;
    	PayMethod payMethod = PayMethod.CACHE;
    	
    	if(ObjectUtil.isNotNull(price) && price.signum() == -1 ){  //signum返回 的是 -1, 0, 1，分别表示 负数、零、正数
    		payMethod = PayMethod.PRESTORE;
    		comments = "后台管理员操作:扣款";
    	}else if(ObjectUtil.isNotNull(price) && price.signum() == 1){
    		feeType = FeeType.DEBIT;
    		comments = "后台管理员操作:充值";
    	}else{
    		//为 0  则不做操作 退出
    		response.setStatus(JsonResponse.ERROR);
        	response.setMessage("price can not be null or zero");
    		return response;
    	}
    	
    	if(ObjectUtil.isNotNull(arenaMemberId) && cMgr.checkArenaMemeberExist(arenaMemberId)) {
    		amManager.manageArenaMemberFunds(arenaMemberId, price, feeType, payMethod, comments);
    		response.setMessage("管理员操作成功: "+ comments);
    		response.setStatus(JsonResponse.SUCCESS);
    		return response;
    	}else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("arenaMemberId can not ArenaMember or arena not exist");
    		return response;
    	}
    }
    
    //录入付费会员账号信息
    @POST
    @Path("/addArenaMemeberInfo/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public JsonResponse addArenaMemeberInfo(ArenaMemberRequest request) throws DomainNotFoundException {
    	JsonResponse response = new JsonResponse(); 
    	Long arenaId = request.getArenaId();
    	String name = request.getName();
    	String teamName = request.getTeamName();
    	Long cellPhone = request.getCellphone();
    	String weChatNo = request.getWechatNo();
    	BigDecimal price = request.getPrice();
    	String comments;
    	if(ObjectUtil.isNotNull(request.getComments())){
    		comments = request.getComments().trim();
    	}else{
    		comments = null;
    	}
    	
    	
    	
    	if(ObjectUtil.isNotNull(price) && price.signum() == 1 //signum返回 的是 -1, 0, 1，分别表示 负数、零、正数 
    		&& ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)
    		&& ObjectUtil.isNotNull(name) && ObjectUtil.isNotNull(teamName)
    		&& ObjectUtil.isNotNull(cellPhone) 
    		//&& ObjectUtil.isNotNull(weChatNo)
    		){
    		
    		Account account =  cMgr.checkCellPhoneExist(cellPhone);
    		if(ObjectUtil.isNotNull(account)){
    			if(cMgr.checkArenaMemeberExist(arenaId, account.getId())){ 
    				//如果用户已在本同注册过，同时也在本场馆注册过，则提示当前手机号已经有用户注册
    				response.setMessage("管理员操作失败: 当前手机号:"+ cellPhone+ "已经有用户注册");
    	    		response.setStatus(JsonResponse.ERROR);
            		return response;
    			}else{
    				//如果用户已在本系统注册过，但是未在当前场馆注册，则直接用注册账号，这里创建新的场馆与会员的关系
    				amManager.addArenaMemeberInfo4ExistAccountWithDiffentArena(arenaId, account.getId(), name, teamName, cellPhone, weChatNo, price, comments);
    				response.setMessage("管理员操作成功: 录入新的付费会员(已在本系统注册用户)");
    	    		response.setStatus(JsonResponse.SUCCESS);
            		return response;
    			}
    		}else{
    			try {
    				amManager.addArenaMemeberInfo(arenaId, name, teamName,cellPhone, weChatNo, price, comments);
    				
    				response.setMessage("管理员操作成功: 录入新的付费会员");
    	    		response.setStatus(JsonResponse.SUCCESS);
    	    		return response;
    			} catch (DomainNotFoundException e) {
    				response.setStatus(JsonResponse.ERROR);
    	        	response.setMessage(e.getMessage());
    	    		return response;
    			}
    		}
    		
    	}else{
    		//为 0 或 -1  则表示 预存资金 小于0 或负数，数额有误 退出
    		response.setStatus(JsonResponse.ERROR);
        	response.setMessage("parameters can not null, price can not be negative or zero");
    		return response;
    	}
    }
    
    //录入《非》付费会员账号信息
    @POST
    @Path("/addArenaNonMemeberInfo/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse addArenaNonMemeberInfo(ArenaMemberRequest request) {
    	JsonResponse response = new JsonResponse(); 
    	Long arenaId = request.getArenaId();
    	String name = request.getName();
    	String teamName = request.getTeamName();
    	Long cellPhone = request.getCellphone();
    	String weChatNo = request.getWechatNo();
    	String comments = request.getComments();
    	BigDecimal customPrice = request.getCustomPrice(); 
    	
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)
    		&& ObjectUtil.isNotNull(name) && ObjectUtil.isNotNull(teamName)
    		&& ObjectUtil.isNotNull(cellPhone) 
    		//&& ObjectUtil.isNotNull(weChatNo)
    		){
    		
    		Account account = cMgr.checkCellPhoneExist(cellPhone);
    				
    		if(ObjectUtil.isNotNull(account)){ 
    			if(cMgr.checkArenaUserExist(arenaId, account.getId())){
    				//如果用户已在本系统注册过，同时也在本场馆注册过，则提示当前手机号已经有用户注册
    				response.setMessage("管理员操作失败: 当前手机号:"+ cellPhone+ "已经有用户注册");
    	    		response.setStatus(JsonResponse.ERROR);
    	    		return response;
    			}else{
    				//如果用户已在本系统注册过，但是未在当前场馆注册，则直接用注册账号，这里创建新的场馆与会员的关系
        			playerMgr.saveArenaUser(account.getId(), arenaId, UserType.BACKEND, comments, customPrice);
        			response.setMessage("管理员操作成功: 录入非付费会员(已在本系统注册用户)");
    	    		response.setStatus(JsonResponse.SUCCESS);
            		return response;
    			}
    			
    		}else{
    			try {
    				amManager.addArenaNonMemeberInfo(arenaId, name, teamName,cellPhone, weChatNo,comments, customPrice);
    				
    				response.setMessage("管理员操作成功: 录入非付费会员");
    	    		response.setStatus(JsonResponse.SUCCESS);
    	    		return response;
    			} catch (DomainNotFoundException e) {
    				response.setStatus(JsonResponse.ERROR);
    	        	response.setMessage(e.getMessage());
    	    		return response;
    			}
    		}
    		
    	}else{
    		response.setStatus(JsonResponse.ERROR);
        	response.setMessage("parameters can not null");
    		return response;
    	}
    }
    
    
    //会员 更新 重命名和更改级别
    @POST
    @Path("/updateArenaMemeberInfo/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updateArenaMemeberInfo(ArenaMemberRequest request) {
    	JsonResponse response = new JsonResponse(); 
    	Long arenaId = request.getArenaId();
    	Long arenaMemberId = request.getArenaMemberId();
    	String newName = request.getName();
    	Integer level  =  request.getLevel();
    	String comments = request.getComments();
    	BigDecimal customDiscount = request.getCustomDiscount();
    	
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)
    	   && ObjectUtil.isNotNull(arenaMemberId) && cMgr.checkArenaMemeberExist(arenaMemberId)
    	   && ObjectUtil.isNotNull(customDiscount)
    	   && ObjectUtil.isNotNull(newName) && ObjectUtil.isNotNull(level)){
    		
    		//折扣 为负数 或者 > 10 则报错
    		if(customDiscount.signum() == -1 || customDiscount.compareTo(new BigDecimal(10.00)) == 1){
    			response.setStatus(JsonResponse.ERROR);
            	response.setMessage("discount can not bigger than 10 or can not minus");
        		return response;
    		}
    		
    		amManager.updateArenaMemeberNameAndComments(arenaMemberId, newName, customDiscount, comments);
    		if(cMgr.checkArenaMemberLevelExist(arenaId,level)){
    			amManager.updateArenaMemeberLevel(arenaId, arenaMemberId, level);
    		}else{
    			response.setStatus(JsonResponse.ERROR);
            	response.setMessage("不存在当前会员级别");
    		}		
    		response.setMessage("管理员操作成功: 更新会员信息");
    	    response.setStatus(JsonResponse.SUCCESS);
    	    return response;
    	}else{
    		response.setStatus(JsonResponse.ERROR);
        	response.setMessage("parameters can not null or arenaMember not exist");
    		return response;
    	}
    }
    
    //会员 重命名
    @POST
    @Path("/updateArenaMemeberName/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updateArenaMemeberName(ArenaMemberRequest request) {
    	JsonResponse response = new JsonResponse(); 
    	Long arenaMemberId = request.getArenaMemberId();
    	String newName = request.getName();
    	
    	if(ObjectUtil.isNotNull(arenaMemberId) && cMgr.checkArenaMemeberExist(arenaMemberId)
    			&& ObjectUtil.isNotNull(newName)){
    		
    		amManager.updateArenaMemeberNameAndComments(arenaMemberId, newName, new BigDecimal(10),null);
    		
    		response.setMessage("管理员操作成功: 会员重命名");
    	    response.setStatus(JsonResponse.SUCCESS);
    	    return response;
    	}else{
    		response.setStatus(JsonResponse.ERROR);
        	response.setMessage("parameters can not null or arenaMember not exist");
    		return response;
    	}
    }
    
    
    //会员 修改会员等级
    @POST
    @Path("/updateArenaMemeberLevel/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updateArenaMemeberLevel(ArenaMemberRequest request) {
    	JsonResponse response = new JsonResponse(); 
    	Long arenaId = request.getArenaId();
    	Long arenaMemberId = request.getArenaMemberId();
    	Integer level  =  request.getLevel();
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)
    			&& ObjectUtil.isNotNull(arenaMemberId) && cMgr.checkArenaMemeberExist(arenaMemberId)
    			&& ObjectUtil.isNotNull(level)){
    		
    		if(cMgr.checkArenaMemberLevelExist(arenaId,level)){
    			amManager.updateArenaMemeberLevel(arenaId, arenaMemberId, level);
        		response.setMessage("管理员操作成功: 会员 重设会员级别");
        	    response.setStatus(JsonResponse.SUCCESS);
    		}else{
    			response.setStatus(JsonResponse.ERROR);
            	response.setMessage("不存在当前会员级别");
    		}
    		
    	    return response;
    	}else{
    		response.setStatus(JsonResponse.ERROR);
        	response.setMessage("parameters can not null or arenaMember not exist");
    		return response;
    	}
    }
    
    
    
  //会员 更新 重命名和更改级别
    @POST
    @Path("/updateArenaNonMemeberInfo/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updateArenaNonMemeberInfo(ArenaMemberRequest request) {
    	JsonResponse response = new JsonResponse(); 
    	Long arenaId = request.getArenaId();
    	Long playerId = request.getPlayerId();
    	String comments = request.getComments();
    	
    	
    	if(ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)
    			&& ObjectUtil.isNotNull(playerId)){
    		
    		amManager.updateArenaNonMemeberComments(arenaId, playerId,comments);
    		response.setMessage("管理员操作成功: 更新用户备注");
    	    response.setStatus(JsonResponse.SUCCESS);
    	    return response;
    	}else{
    		response.setStatus(JsonResponse.ERROR);
        	response.setMessage("parameters can not null or arena not exist");
    		return response;
    	}
    }
}
