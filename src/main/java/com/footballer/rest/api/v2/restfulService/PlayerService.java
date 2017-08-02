package com.footballer.rest.api.v2.restfulService;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.footballer.rest.api.v1.domain.PlayerWithTeamsInfo;
import com.footballer.rest.api.v1.manager.UserManager;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.response.LoginResponse;
import com.footballer.rest.api.v2.vo.Arena;
import com.footballer.rest.api.v2.vo.Tournament;
import com.footballer.rest.api.v2.vo.enumType.UserType;
import com.footballer.rest.api.v2.Response.PlayerResponse;
import com.footballer.rest.api.v2.manager.CommonManager;
import com.footballer.rest.api.v2.manager.PlayerAccountManager;
import com.footballer.rest.api.v2.request.PlayerAccountRequest;
import com.footballer.rest.api.v2.request.WeChatUserRequest;
import com.footballer.rest.api.v2.vo.Account;
import com.footballer.util.ObjectUtil;
import com.footballer.util.PropertiesUtil;
import com.footballer.util.StringUtil;
import com.google.common.collect.Lists;


@Path("/mobile/v2/player-service")
public class PlayerService {
	
	private static Logger logger = LoggerFactory.getLogger(PlayerService.class);
	
	////identity_appId_apptoken_4_backend_api
	private static final String IAA= "/e30ea6d0-5273-4ba2-9cf7-155af789418c/100000/e7afdd55291a3e3796a6d3d83e70d6dd"; 
	
	private static final String URL_API_VERVION = "/footballer/api/mobile/v2/"; 

	//private static String Url = "http://footballer.cc";	
	private static String Url = PropertiesUtil.getDomain();
	
	//url_call_prefix
	private static final String URLPRE = Url + URL_API_VERVION;
	
	@Autowired
	private CommonManager cMgr;
	
	@Autowired
	private PlayerAccountManager pMgr;
	
	@Autowired
	public UserManager userManager;

	@Autowired
	public NotificationManager notificationManager;
	
	@Autowired
	public PlayerAccountManager playerMgr;

	// 根据 微信的openId 来为当前微信用户创建Player账户
	@POST
	@Path("/createPlayerAccountByOpenId/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Long createPlayerAccountByOpenId(WeChatUserRequest request) {
		String openId = request.getOpenId();
		String name = request.getName();
		String weChatPicUrl = request.getWeChatPicUrl();
		Long arenaId = request.getArenaId();
		//Long cellPhone = request.getCellphone();
		//String weChatNo = request.getWechatNo();

		if (ObjectUtil.isNotNull(arenaId) && ObjectUtil.isNotNull(openId)
				&& ObjectUtil.isNotNull(name) && ObjectUtil.isNotNull(weChatPicUrl)) {
			if (!cMgr.checkArenaExist(arenaId)) {
				throw new RuntimeException("当前场馆不存在");
			}
			if (cMgr.checkOpenIdExist(openId)) {
				Account account = pMgr.getAccountByOpenId(openId);
				if (account != null) {
					if (StringUtil.isEmpty(account.getWeChatPicUrl())) {
						pMgr.updateWechatImageUrlById(account.getId(), weChatPicUrl);
					}
					return account.getId();
				} else {
					throw new RuntimeException("Can not find account info by open id:" + openId);
				}
			}else{
				//（微信昵称可能存在大量特殊字符）清除所有特殊字符
				name = StringUtil.StringFilter(name);
				
				Long accountId = pMgr.registerWeiChatAccount(arenaId, name, openId, null, null, weChatPicUrl);
				logger.debug("为微信用户创建账户成功");
				
				//记录当前微信用户与场馆的 关系
				playerMgr.saveArenaUser(accountId, arenaId, UserType.WECHAT, "", null);
				return accountId;
			}
		} else {
			throw new RuntimeException("parameters can not null");
		}
	}
	
	@POST
	@Path("/getPlayerAccountByOpenId/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public PlayerResponse getPlayerAccountByOpenId(WeChatUserRequest request) {
		PlayerResponse response = new PlayerResponse();

		String openId = request.getOpenId();

		if (ObjectUtil.isNotNull(openId)) {

			if (cMgr.checkOpenIdExist(openId)) {
				
				response.setAccount(pMgr.getAccountByOpenId(openId));
				response.setStatus(JsonResponse.SUCCESS);
				return response;
			}else{
				response.setMessage("不存在当前openId");
				response.setStatus(JsonResponse.ERROR);
				return response;
			}
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	@GET
	@Path("/getArenaPlayerList/{arenaId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public PlayerResponse getArenaPlayerList(@PathParam("arenaId") Long arenaId) {
		PlayerResponse response = new PlayerResponse();

		if (ObjectUtil.isNotNull(arenaId)) {

			if (cMgr.checkArenaExist(arenaId)) {
				
				response.setArenaUsers(pMgr.getArenaUsersInfo(arenaId));
				response.setStatus(JsonResponse.SUCCESS);
				return response;
			}else{
				response.setMessage("不存在当前场馆");
				response.setStatus(JsonResponse.ERROR);
				return response;
			}
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}

	//用户更改密码 （2016.03.31）
	@POST
	@Path("/updateAccountPWD/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public JsonResponse updateAccountPWD(PlayerAccountRequest request) {
		JsonResponse response = new JsonResponse();
		Long playerAccountId = request.getPlayerAccountId();
		String newPwd = request.getNewPwd(); 
		
		if(ObjectUtil.isNotNull(playerAccountId) && ObjectUtil.isNotNull(newPwd)){
			
			if (cMgr.checkPlayerExist(playerAccountId)) {
				
				pMgr.updateAccountPWDById(playerAccountId, newPwd);
				response.setStatus(JsonResponse.SUCCESS);
				return response;
			}else{
				response.setMessage("不存在当前用户");
				response.setStatus(JsonResponse.ERROR);
				return response;
			}
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//用户未登录，找回密码 （2016.04.15）
	//update 移除 identity （2016.04.17）
	@POST
	@Path("/findAccountPWD/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public JsonResponse findAccountPWD(PlayerAccountRequest request) {
		JsonResponse response = new JsonResponse();
		Long cell = request.getCell();
		String newPwd = request.getNewPwd(); 
		
		if(ObjectUtil.isNotNull(cell) && ObjectUtil.isNotNull(newPwd)){
			
			Long playerAccountId = pMgr.findPlayerAccountIdByCellPhone(cell);
			pMgr.updateAccountPWDById(playerAccountId, newPwd);
			response.setStatus(JsonResponse.SUCCESS);
			return response;
			
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//后台场馆用户登录
	@POST
    @Path("/arenaOwnerLogin/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponse arenaOwnerLogin(PlayerAccountRequest request) {

		String accountName = request.getAccountName();
		String pwd = request.getPwd();
		
        LoginResponse response = new LoginResponse();
        List<Arena> arenas = Lists.newArrayList();

        if(ObjectUtil.isNotNull(accountName) && ObjectUtil.isNotNull(pwd)){
        	
        	arenas = pMgr.findArenasOfOwnerByNameAndPwd(accountName,pwd);
            if(arenas.size() >0){
            	response.setPlayerAccountId(pMgr.findPlayerAccountIdByCellPhone(Long.parseLong(accountName)));
            	response.setUrlPre(URLPRE);
            	response.setIaa(IAA);
            	response.setStatus(JsonResponse.SUCCESS);
                response.setArenas(arenas);
            }else{
            	response.setStatus(JsonResponse.NODATA);
            }
        }else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("parameters can not null");
        }
        
        return response;
    }
	

	
	//根据微信用户openid判断是否为场馆admin
	@GET
	@Path("/getArenaOwnerByIdAndOpenId/{arenaId}/{openId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public PlayerResponse getArenaOwnerByIdAndOpenId(@PathParam("arenaId") Long arenaId,@PathParam("openId") String openId) {
		PlayerResponse response = new PlayerResponse();

		if (ObjectUtil.isNotNull(arenaId) && ObjectUtil.isNotNull(openId) && !"".equals(openId.trim()) && openId.trim().length() >0 ) {

			if (cMgr.checkArenaExist(arenaId)) {
				
				response.setArenaOwner(pMgr.getArenaOwnerByIdAndOpenId(arenaId, openId));
				response.setStatus(JsonResponse.SUCCESS);
				return response;
			}else{
				response.setMessage("不存在当前场馆");
				response.setStatus(JsonResponse.ERROR);
				return response;
			}
		} else {
			response.setArenaOwner(false);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	
	//后台-赛事管理用户登录
	@POST
    @Path("/tournamentOwnerLogin/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponse tournamentOwnerLogin(PlayerAccountRequest request) {

		String accountName = request.getAccountName();
		String pwd = request.getPwd();
		
        LoginResponse response = new LoginResponse();
        List<Tournament> tournaments = Lists.newArrayList();

        if(ObjectUtil.isNotNull(accountName) && ObjectUtil.isNotNull(pwd)){
        	
        	tournaments = pMgr.findTournamentsOfOwnerByNameAndPwd(accountName,pwd);
            if(tournaments.size() >0){
            	response.setPlayerAccountId(pMgr.findPlayerAccountIdByCellPhone(Long.parseLong(accountName)));
            	response.setUrlPre(URLPRE);
            	response.setIaa(IAA);
            	response.setStatus(JsonResponse.SUCCESS);
                response.setTournaments(tournaments);
            }else{
            	response.setStatus(JsonResponse.NODATA);
            }
        }else{
        	response.setStatus(JsonResponse.ERROR);
        	response.setMessage("parameters can not null");
        }
        
        return response;
    }	
	
	
	
	//篮球[personalCenter]获取球员和所在的球队基本信息
	@GET
	@Path("/getPlayerAndBasketBallTeamsInfo/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public PlayerResponse getPlayerAndBasketBallTeamsInfo(@PathParam("playerId") Long playerId) {
		PlayerResponse response = new PlayerResponse();

		if (ObjectUtil.isNotNull(playerId)) {
			response.setPlayerWithTeamsList(pMgr.getPlayerAndBasketBallTeamsInfo(playerId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setArenaOwner(false);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	
	//足球 [personalCenter]获取球员和所在的球队基本信息
	@GET
	@Path("/getPlayerAndFootballTeamsInfo/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public PlayerResponse getPlayerAndFootballTeamsInfo(@PathParam("playerId") Long playerId) {
		PlayerResponse response = new PlayerResponse();

		if (ObjectUtil.isNotNull(playerId)) {
			response.setPlayerWithTeamsList(pMgr.getPlayerAndFootballTeamsInfo(playerId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setArenaOwner(false);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	
	@POST
	@Path("/updatePlayerAndTeamInfo/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public JsonResponse updatePlayerAndTeamInfo(PlayerWithTeamsInfo playerWithTeamsInfo) {
		JsonResponse response = new JsonResponse();

		System.out.println("当前用户设置的基本信息-头像： " + playerWithTeamsInfo.getPlayerLogo());
		System.out.println("当前用户设置的基本信息-名字： " + playerWithTeamsInfo.getPlayerName());
		System.out.println("当前用户设置的基本信息-生日： " + playerWithTeamsInfo.getPlayerBirth());
		System.out.println("当前用户设置的基本信息-身高： " + playerWithTeamsInfo.getPlayerHeight());
		System.out.println("当前用户设置的基本信息-体重： " + playerWithTeamsInfo.getPlayerWeight());
//		System.out.println("当前用户设置的基本信息： 号码" + playerWithTeamsInfo.getPlayerLogo());
//		System.out.println("当前用户设置的基本信息： 位置" + playerWithTeamsInfo.getPlayerLogo());
		
		if (ObjectUtil.isNotNull(playerWithTeamsInfo.getPlayerId())) {
			
			pMgr.updatePlayerNameAndImgById(playerWithTeamsInfo.getPlayerId(), playerWithTeamsInfo.getPlayerName(), playerWithTeamsInfo.getPlayerLogo());
			pMgr.updatePlayerPropertyById(playerWithTeamsInfo.getPlayerId(), playerWithTeamsInfo.getPlayerBirth(), playerWithTeamsInfo.getPlayerHeight(), playerWithTeamsInfo.getPlayerWeight());
			
			if(playerWithTeamsInfo.getPlayerTeamsInfo() != null && playerWithTeamsInfo.getPlayerTeamsInfo().size()>0){
				pMgr.updatePlayerTeamsInfoByPlayerTeamsInfo(playerWithTeamsInfo.getPlayerTeamsInfo());
			}

			response.setMessage("update info success");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("playerId can not null");
			return response;
		}
	}
	
	
	@POST
	@Path("/updatePlayerImgById/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public JsonResponse updatePlayerImgById(PlayerWithTeamsInfo playerWithTeamsInfo) {
		JsonResponse response = new JsonResponse();

		System.out.println("当前用户设置的基本信息-头像： " + playerWithTeamsInfo.getPlayerLogo());
		
		if (ObjectUtil.isNotNull(playerWithTeamsInfo.getPlayerId())) {
			
			pMgr.updatePlayerImgById(playerWithTeamsInfo.getPlayerId(), playerWithTeamsInfo.getPlayerLogo());
			response.setMessage("update info success");
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("playerId can not null");
			return response;
		}
	}
}
