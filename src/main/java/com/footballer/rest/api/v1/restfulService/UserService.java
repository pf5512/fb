package com.footballer.rest.api.v1.restfulService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.dao.UserDao;
import com.footballer.rest.api.v1.domain.FootballPlayer;
import com.footballer.rest.api.v1.domain.FootballPlayerBalance;
import com.footballer.rest.api.v1.domain.FootballPlayerTeamActivity;
import com.footballer.rest.api.v1.domain.FootballTeam;
import com.footballer.rest.api.v1.domain.PlayerBaseInfo;
import com.footballer.rest.api.v1.domain.User;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.manager.UserManager;
import com.footballer.rest.api.v1.request.GetAppUserRequest;
import com.footballer.rest.api.v1.request.GetPlayerDeviceTokenRequest;
import com.footballer.rest.api.v1.request.UpdateAccountCellPhoneRequest;
import com.footballer.rest.api.v1.response.ClientPlayerBalanceLine;
import com.footballer.rest.api.v1.response.DomainResponse;
import com.footballer.rest.api.v1.response.FindPlayerTeamAllPlayerAndFriendsResponse;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.response.ListResponse;
import com.footballer.rest.api.v1.response.LoginResponse;
import com.footballer.rest.api.v1.response.UserResponse;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.PlayerBalanceLine;
import com.footballer.rest.api.v1.vo.PlayerProperty;
import com.footballer.rest.api.v1.vo.enumType.FeeType;
import com.footballer.rest.api.v2.manager.EvaluationManagerV2;
import com.footballer.rest.api.v2.manager.PlayerAccountManager;
import com.footballer.rest.api.v2.vo.PlayerMutualEvaluationAllCount;
import com.footballer.util.CommonUtil;
import com.footballer.util.JacksonUtil;
import com.footballer.util.ObjectUtil;
import com.footballer.util.StringUtil;
import com.google.common.collect.Lists;

@Path("/mobile/v1/user-service")
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
    @Autowired
    public UserDao userDao;
    
    @Autowired
    public PlayerDao playerDao;

    @Autowired
    public UserManager userManager;
    
    @Autowired
	private EvaluationManagerV2 evaluationManagerV2;
    
    @Autowired
	private PlayerAccountManager pMgr;

    @GET
    @Path("/register/{name}/{password}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponse register(
            @PathParam("name") String name,
            @PathParam("password") String password) {
        LoginResponse response = new LoginResponse();
        User user;

        try {
            user = userManager.createUser(name, password);
        } catch (Exception e) {
        	logger.error("register name:{},password:{}",name,password);
        	logger.error("register error ",e);
            response.setStatus(JsonResponse.ERROR);
            response.setMessage(e.getMessage());
            return response;
        }

        response.setStatus(JsonResponse.SUCCESS);
        response.setUser(user);

        return response;
    }

    @GET
    @Path("/login/{name}/{password}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponse login(
            @PathParam("name") String name,
            @PathParam("password") String password) {

        LoginResponse response = new LoginResponse();
        User user;

        try {
            user = userManager.findUserByNameAndPassword(name, password);
        } catch (DomainNotFoundException e) {
        	logger.error("login error ",e);
            response.setStatus(JsonResponse.ERROR);
            response.setMessage(e.getMessage());
            return response;
        }

        response.setStatus(JsonResponse.SUCCESS);
        response.setUser(user);

        return response;
    }

    @POST
    @Path("/player/create/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse createUserPlayer(Player player) {
        JsonResponse response = new JsonResponse();
        player.setAccount(AppContextHolder.getContext().getAccount());
        PlayerProperty playerProperty = new PlayerProperty();
        CommonUtil.addAuditableAttributes(playerProperty);
        playerProperty.setId(Long.parseLong(playerProperty.getCreateBy()));
        player.setPlayerProperty(playerProperty);

        try {
            userDao.createUserPlayer(player);
        } catch (Exception e) {
        	logger.error("createUserPlayer error ,param:"+JacksonUtil.toJson(player) ,e);
            response.setStatus(JsonResponse.ERROR);
            response.setMessage(e.getMessage());
            return response;
        }

        response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("save user player success");
        return response;
    }

    @POST
    @Path("/player/update/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updateUserPlayer(Player player) {
        JsonResponse response = new JsonResponse();
        try {
        	userDao.updateUserPlayer(player);
        }catch (Exception e) {
        	logger.error("updateUserPlayer error ,param:"+JacksonUtil.toJson(player) ,e);
            response.setStatus(JsonResponse.ERROR);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("save user player success");
        return response;
    }
    
    @POST
    @Path("/player/updateAccountCellPhone/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updateAccountCellPhone(UpdateAccountCellPhoneRequest request) {
    	JsonResponse response = new JsonResponse();
    	String phone = request.getPhone();
    	String openid = request.getOpenid();
    	
    	com.footballer.rest.api.v2.vo.Account account = pMgr.getAccountByOpenId(openid);
    	
    	if (phone != null) {
    		boolean isPhoneNumber = isPhoneNumber(phone);
    		if (isPhoneNumber) {
    			userDao.updateAccountPhone(phone, account.getId());
    			response.setStatus(JsonResponse.SUCCESS);
        		response.setMessage("保存电话号码成功");
    		} else {
    			response.setStatus(JsonResponse.ERROR);
        		response.setMessage("请输入有效的电话号码");
    		}
    	} else {
    		response.setStatus(JsonResponse.ERROR);
    		response.setMessage("请输入电话号码");
    	}
    	return response;
    }

	public static boolean isPhoneNumber(String mobiles){  
		Pattern p = Pattern.compile("^1\\d{10}$");//Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
    	Matcher m = p.matcher(mobiles);  
    	return m.matches();  
    }	
	
    @POST
    @Path("/player/updatePlayerDeviceToken/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updatePlayerDeviceToken(GetPlayerDeviceTokenRequest request) {
        JsonResponse response = new JsonResponse();
        try {
        	userDao.updatePlayerDeviceToken(request.getDeviceToken());
        }catch (Exception e) {
        	logger.error("updatePlayerDeviceToken error,param:"+JacksonUtil.toJson(request) ,e);
            response.setStatus(JsonResponse.ERROR);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("save user player success");
        return response;
    }
    
    //获取 用户的手机通讯录中 已存在的我们的用户的信息 并返回 1和2个列表
    /**
     * 
     * @param request
     * @return  1. 已注册app但是还未与当前用户成为好友的 用户列表
     *          2. 未注册app的通讯录中得手机用户
     */
    @POST
    @Path("/getAppUserOfContacts/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse getAppUserOfContacts(GetAppUserRequest request) {
    	UserResponse response = new UserResponse();
        
    	String [] cellphones = request.getCellphoneList().split(",");
    	List<String> cellPhoneList = Lists.newArrayList();
    	for(String s: cellphones)
    	{
    		cellPhoneList.add(s);
    	}
    	try {
    		response.setRegisterUserList(userManager.findCurrentUsersAddressListFriends(cellPhoneList));
    		response.setUnRegisteredUserList(userManager.findCurrentUsersAddressListsUnRegisterUserList(cellphones,cellPhoneList));
    	}catch (Exception e) {
        	logger.error("getAppUserOfContacts error,param: "+JacksonUtil.toJson(request) ,e);
            response.setStatus(JsonResponse.ERROR);
            response.setMessage(e.getMessage());
            return response;
        }
    	response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("get user with status List success");
        return response;
    }
    
    //查看球员主页
    @GET
    @Path("/findPlayer/{playerId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findFootballPlayersById(@PathParam("playerId") Long playerId) {
        DomainResponse<FootballPlayer> response = new DomainResponse<>();
        try {
            FootballPlayer footballplayer = userManager.findFootballPlayerById(playerId);
            response.setDomain(footballplayer);
            List<PlayerMutualEvaluationAllCount> eList = (List<PlayerMutualEvaluationAllCount>)evaluationManagerV2.findListByPlayerIdDesc(playerId);
            response.setList(eList);
        } catch (RuntimeException e) {
        	logger.error("findFootballPlayersById error,param:playerId "+playerId ,e);
            response.setStatus(JsonResponse.ERROR);
            response.setMessage(e.getMessage());
            return response;
        }

        response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("find football player success");
        return response;
    }
    
    @GET
    @Path("/player/findPlayerProperty/{playerId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findPlayerPropertyByPlayerId(@PathParam("playerId") Long playerId) {
        DomainResponse<PlayerProperty> response = new DomainResponse<>();
        try {
        	PlayerProperty playerProperty = userManager.findPlayerPropertyByPlayerId(playerId);
        	//List<PlayerMutualEvaluationAllCount> eList = (List<PlayerMutualEvaluationAllCount>)evaluationManagerV2.findListByPlayerIdDesc(playerId);
            response.setDomain(playerProperty);
            //response.setList(eList);
        } catch (RuntimeException e) {
        	logger.error("findFootballPlayersById error,param:playerId: "+playerId ,e);
            response.setStatus(JsonResponse.ERROR);
            response.setMessage(e.getMessage());
            return response;
        }

        response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("获取球员详细技术信息成功");
        return response;
    }
    
    
    @POST
    @Path("/player/updatePlayerProperty/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updatePlayerProperty(PlayerProperty playerProperty) {
        JsonResponse response = new JsonResponse();
	    try{
	    	Account account = AppContextHolder.getContext().getAccount();
	        if(ObjectUtil.isNotNull(account)){
	        	 playerProperty.setAccount(account);
	             playerDao.updatePlayerProperty(playerProperty);
	             response.setStatus(JsonResponse.SUCCESS);
	             response.setMessage("更新球员详细技术信息成功");
	        }else{
	        	 response.setStatus(JsonResponse.ERROR);
	             response.setMessage("不存在这个球员账户");
	        }
	    }catch (Exception e) {
	    	logger.error("updatePlayerProperty error,param: "+JacksonUtil.toJson(playerProperty)  ,e);
	        response.setStatus(JsonResponse.ERROR);
	        response.setMessage(e.getMessage());
	        return response;
	    }
        return response;
    }
    
    /**
     * 扣款单个球员的资金账户余额, 在原来的数目下减少指定的数目
     * 
     * 如果是临时扣款的，没法关联eventId的，将eventId设置为-1,  然后加comment.
     * 
     * @param request
     * @return
     */
    @POST
    @Path("/player/chargePlayerBalances/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse chargePlayerBalances(FootballPlayerBalance request) {
    	JsonResponse response = new JsonResponse();
    	
    	if (null == request.getTeamId()) throw new IllegalArgumentException("Team id is null");
    	if (null == request.getPlayerId()) throw new IllegalArgumentException("Player id is null");
    	if (null == request.getPayMethod()) throw new IllegalArgumentException("Pay method is null");
    	if (null == request.getAmount()) throw new IllegalArgumentException("Amount is null");
    	if (null == request.getEventId()) throw new IllegalArgumentException("Event id is null");
    	 try{
    		 userManager.updatePlayerBalances(request.getPlayerId(),
				request.getTeamId(), FeeType.CREDIT,
				request.getPayMethod(), request.getAmount(),
				request.getComment(), request.getEventId());
    	 }catch (Exception e) {
 	    	logger.error("chargePlayerBalances error,param: "+JacksonUtil.toJson(request)  ,e);
 	        response.setStatus(JsonResponse.ERROR);
 	        response.setMessage(e.getMessage());
 	        return response;
 	    }
		response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("更新单个球员的资金账户余额成功");
    	return response;
    }
    
    /**
     * 充值单个球员的资金账户余额, 在原来的数目下增加指定的数目
     * @param request
     * @return
     */
    @POST
    @Path("/player/depositPlayerBalances/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse depositPlayerBalances(FootballPlayerBalance request) {
    	JsonResponse response = new JsonResponse();
    	
    	if (null == request.getTeamId()) throw new IllegalArgumentException("Team id is null");
    	if (null == request.getPlayerId()) throw new IllegalArgumentException("Player id is null");
    	if (null == request.getPayMethod()) throw new IllegalArgumentException("Pay method is null");
    	if (null == request.getAmount()) throw new IllegalArgumentException("Amount is null");
    	try{
    		userManager.updatePlayerBalances(request.getPlayerId(),
				request.getTeamId(), FeeType.DEBIT,
				request.getPayMethod(), request.getAmount(),
				request.getComment(), request.getEventId());
    	}catch (Exception e) {
 	    	logger.error("chargePlayerBalances error,param: "+JacksonUtil.toJson(request) ,e);
 	        response.setStatus(JsonResponse.ERROR);
 	        response.setMessage(e.getMessage());
 	        return response;
 	    }
		response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("更新单个球员的资金账户余额成功");
    	return response;
    }
    
    /**
     * 获取球员的资金明细信息
     * @param playerId
     * @param teamId
     * @return
     */
    @GET
    @Path("/player/getPlayerBalanceLines/{playerId}/{teamId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse getPlayerBalanceLines(@PathParam("playerId") Long playerId,
    		@PathParam("teamId") Long teamId) {
    	ListResponse<ClientPlayerBalanceLine> response = new ListResponse<>();
    	List<ClientPlayerBalanceLine> clientLines = null;
    	try{
	    	List<PlayerBalanceLine> lines = userManager.findPlayerBalanceLines(playerId, teamId);
	    	
	    	if (!CollectionUtils.isEmpty(lines)) {
	    		clientLines = new ArrayList<>();
	    		for (PlayerBalanceLine line : lines) {
	    			clientLines.add(new ClientPlayerBalanceLine(line));
	    		}
	    		Collections.sort(clientLines);
	    	}
	    	
	    	response.setStatus(JsonResponse.SUCCESS);
	        response.setMessage("获取球员的资金明细信息成功");
	    	response.setList(clientLines);
	    	return response;
    	}catch (Exception e) {
 	    	logger.error("getPlayerBalanceLines error " ,e);
 	    	logger.error("getPlayerBalanceLines error,param : teamId:{};playerId:{}",teamId,playerId);
 	        response.setStatus(JsonResponse.ERROR);
 	        response.setMessage(e.getMessage());
 	        return response;
 	    }
    }
    
    /**
     *  获取一场比赛所有人的缴费记录
     * @param playerId
     * @param teamId
     * @return
     */
    @GET
    @Path("/player/findPlayerDebitBalanceLines/{eventId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse getPlayerBalanceLines(@PathParam("eventId") Long eventId) {
    	ListResponse<ClientPlayerBalanceLine> response = new ListResponse<>();
    	try{
        	List<ClientPlayerBalanceLine> clientLines = userManager.findPlayerBalanceLines(eventId);
        	Collections.sort(clientLines);
        	response.setStatus(JsonResponse.SUCCESS);
            response.setMessage("获取一场比赛所有人的缴费记录信息成功");
        	response.setList(clientLines);
        	return response;
    	}catch (Exception e) {
 	    	logger.error("getPlayerBalanceLines error, param:eventId:"+eventId ,e);
 	        response.setStatus(JsonResponse.ERROR);
 	        response.setMessage(e.getMessage());
 	        return response;
 	    }
    	
    }
    
    /**
     * 获取指定球员的所有球队的球员列表和好友列表
     * @param playerId
     * @return
     */
    @GET
    @Path("/player/findPlayerTeamAllPlayersAndFriends/{playerId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findPlayerTeamAllPlayersAndFriends(@PathParam("playerId") Long playerId) {
		FindPlayerTeamAllPlayerAndFriendsResponse response = new FindPlayerTeamAllPlayerAndFriendsResponse();
    	List<FootballTeam> teams = userManager.findPlayerTeamAllPlayers(playerId);
    	List<PlayerBaseInfo> friends = null;
    	
    	try {
    		friends = playerDao.getPlayerFriendsBaseInfoList(playerId);
		} catch (Exception e) {
			logger.error("findPlayerTeamAllPlayersAndFriends error,param:playerId: "+playerId ,e);
			response.setStatus(JsonResponse.ERROR);
			return response;
		}
    	
    	response.setFriends(friends);
    	response.setTeams(teams);
    	
    	response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("获取指定球员的所有球队的球员列表成功");
    	return response;
    }
    
    /**
     * 更新球员赛事活动数据
     * @param request
     * @return
     */
    @POST
    @Path("/player/updatePlayerTeamActivity/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse updatePlayerTeamActivity(FootballPlayerTeamActivity request) {
    	JsonResponse response = new JsonResponse();
    	try{
    		userManager.updatePlayerTeamActivity(request.getPlayerId(),
				request.getTeamId(), request.getEventId(), request.getDelta(), request.getType());
    	} catch (Exception e) {
			logger.error("updatePlayerTeamActivity error ,param:"+JacksonUtil.toJson(request) ,e);
			response.setStatus(JsonResponse.ERROR);
			return response;
		}
		response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("更新球员赛事活动数据成功");
    	return response;
    }
    
    
    
    
    /**
     * 根据手机号查找已注册的用户信息，并返回和当前用户的关系
     * 
     */
    @GET
    @Path("/findUserAndRelationship/{criteria}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findUserAndRelationship(@PathParam("criteria") String criteria) {
    	UserResponse response = new UserResponse();
    	String _criteria = criteria.toString().trim(); 
    	
    	
    	try{
	    	if(_criteria.length() == 11 && StringUtil.isLong(_criteria)){
	    		//11位且全数字，则根据手机号码查找
				response.setUserWithStatus(userDao.findUserAndRelationshipByCellPhone(_criteria));
	    	}else{  
	    		//非手机号 则根据球员nickName 查找
	    		response.setRegisterUserList(userDao.findUserAndRelationshipByNickName(_criteria));	
	    	}
    	} catch (Exception e) {
			logger.error("findUserAndRelationship error ,param:criteria:"+criteria ,e);
			response.setStatus(JsonResponse.ERROR);
			return response;
		}
    	response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("获取数据成功");
    	return response;
    }

}