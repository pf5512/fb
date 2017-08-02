package com.footballer.rest.api.v1.restfulService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.dao.InvitationDao;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.dao.TeamDao;
import com.footballer.rest.api.v1.dao.TeamDao.TeamBasic;
import com.footballer.rest.api.v1.dao.UserDao;
import com.footballer.rest.api.v1.domain.ClientTeam;
import com.footballer.rest.api.v1.domain.FootballPlayer;
import com.footballer.rest.api.v1.domain.FootballTeam;
import com.footballer.rest.api.v1.domain.FootballTeamInfo;
import com.footballer.rest.api.v1.domain.PlayerBaseInfo;
import com.footballer.rest.api.v1.domain.PlayerTeamSummary;
import com.footballer.rest.api.v1.domain.User;
import com.footballer.rest.api.v1.exception.CopyException;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.manager.UserManager;
import com.footballer.rest.api.v1.request.CreateTeamByNamePlayerIdRequest;
import com.footballer.rest.api.v1.request.FindTeamNameByPlayerIdRequest;
import com.footballer.rest.api.v1.request.FindTeamsByCaptainUserIdRequest;
import com.footballer.rest.api.v1.response.DomainResponse;
import com.footballer.rest.api.v1.response.FindTeamsByCaptainUserIdResponse;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.response.ListResponse;
import com.footballer.rest.api.v1.response.TeamResponse;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.PlayerTeamActivity;
import com.footballer.rest.api.v1.vo.Team;
import com.footballer.rest.api.v1.vo.TeamPlayer;
import com.footballer.rest.api.v1.vo.TeamPlayer.TEAM_PLAYER_STATUS;
import com.footballer.util.JacksonUtil;
import com.footballer.util.ObjectUtil;

@Transactional
@Path("/mobile/v1/team-service")
public class TeamService {

	private static Logger logger = LoggerFactory.getLogger(TeamService.class);
	
	@Autowired
	public TeamDao teamDao;
	
	@Autowired
	public PlayerDao playerDao;
	
    @Autowired
    public UserManager userManager;	
    
    @Autowired
	public InvitationDao invitationDao;
    
    @Autowired
	public UserDao userDao;

	/**
	 * Create team
	 * 1. create team player relation with current account
	 * 2. send invitation to players if player id list present
	 * @param team
	 * @return
	 */
	@POST
	@Path("/create/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public TeamResponse create(Team team) {

		TeamResponse response = new TeamResponse();
		response.setTeam(team);
		if ((!StringUtils.hasText(team.getName()))
				&& !ObjectUtil.isNotNull(team.getCaptainUserId())) {
			logger.warn("createTeam: User name/captain can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("User name/captain can not be empty");
			return response;
		}

		try{
			Team savedTeam = teamDao.createNewTeam(team);
		
			userDao.createPlayerDepositAccount(team.getCaptainUserId(), savedTeam.getId());
			
			// TODO: move to team manager class
			if (!CollectionUtils.isEmpty(savedTeam.getPlayers())) {
				Set<Long> playerIds = new HashSet<>();
				
				for (Player player : savedTeam.getPlayers()) {
					if (!StringUtils.isEmpty(player.getId())) {
						playerIds.add(player.getId());
					}
				}
				invitationDao.TeamInvitePlayers(savedTeam.getId(), new ArrayList<Long>(playerIds));
			}
		}catch(Exception e){
			logger.error("create team error,param:"+JacksonUtil.toJson(team),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
		response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("save team success");
		return response;
	}
	
	@POST
	@Path("/createTeamByPlayer/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public TeamResponse createTeamByPlayer(Team team) {
		TeamResponse response = new TeamResponse();
		Team createdTeam = null;
		try{
			 createdTeam = teamDao.createTeamByPlayer(team);
		}catch(Exception e){
			logger.error("createTeamByPlayer team error,param:"+JacksonUtil.toJson(team),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
		response.setTeam(createdTeam);
		response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("save team success");
		return response;
	}
		
	@POST
	@Path("/update/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public TeamResponse update(Team team) {

		TeamResponse response = new TeamResponse();
		response.setTeam(team);
		try{
			teamDao.update(team);
		}catch(Exception e){
			logger.error("update team error,param:"+JacksonUtil.toJson(team),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
		response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("save team success");
		return response;
	}
	
	@POST
	@Path("/updateTeamInfo/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public TeamResponse updateTeamInfo(Team team) {

		TeamResponse response = new TeamResponse();
		response.setTeam(team);
		try{
			teamDao.updateTeamInfo(team);
		}catch(Exception e){
			logger.error("updateTeamInfo  error,param:"+JacksonUtil.toJson(team),e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
		response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("save team success");
		return response;
	}
			
	@GET
	@Path("/findTeamsByName/{name}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse findTeamsByName(@PathParam("name") String name) {

		DomainResponse<FootballPlayer> response = new DomainResponse<>();

		if (!StringUtils.hasText(name)) {
			logger.warn("Team name can not be empty");
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("Team name can not be empty");
			return response;
		}
		List<FootballTeam> footballTeamSet = new ArrayList<>();
		
		try{
			List<Team> teamList = teamDao.findTeamsByName(name);
			
	        for (Team team : teamList) {
	            FootballTeam footballTeam = new FootballTeam();
	            ClientTeam clientTeam = new ClientTeam();
	            BeanUtils.copyProperties(team, clientTeam, new String[]{"fields", "players"});
	            footballTeam.setTeam(clientTeam);
	            footballTeamSet.add(footballTeam);
	        }
		}catch(Exception e){
			logger.error("findTeamsByName  error,param:name:"+name,e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
        FootballPlayer footballPlayer = new FootballPlayer(null, footballTeamSet);
		
		response.setStatus(JsonResponse.SUCCESS);
		response.setDomain(footballPlayer);
		
		return response;
	}
	
	@GET
	@Path("/findAllTeams/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse findAllTeams() {

		DomainResponse<FootballPlayer> response = new DomainResponse<>();
		
		//暂时解决方式，场馆系统和app的所有球队 会引起场馆客户觉得 用户数据被利用，所以暂时隐藏
//		List<FootballTeam> footballTeams = new ArrayList<>();
//
//		try{
//			List<Team> teamList = teamDao.findTeamsByName(null);
//	        for (Team team : teamList) {
//	            FootballTeam footballTeam = new FootballTeam();
//	            ClientTeam clientTeam = new ClientTeam();
//	            BeanUtils.copyProperties(team, clientTeam, new String[]{"fields", "players"});
//	            footballTeam.setTeam(clientTeam);
//	            
//	            footballTeams.add(footballTeam);
//	        }
//		}catch(Exception e){
//			logger.error("findAllTeams  error",e);
//			response.setStatus(JsonResponse.ERROR);
//			response.setMessage(e.getMessage());
//			return response;
//		}
//        Collections.sort(footballTeams);
//		
//        FootballPlayer footballPlayer = new FootballPlayer(null, footballTeams);
//		
//		response.setStatus(JsonResponse.SUCCESS);
//		response.setDomain(footballPlayer);
		
		response.setStatus(JsonResponse.SUCCESS);
		response.setDomain(null);
		
		return response;
	}
	

    @GET
    @Path("/findTeamPlayers/{teamId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findTeamPlayers(@PathParam("teamId") Long teamId) {
        DomainResponse<FootballTeam> response = new DomainResponse<>();

        if (null == teamId) {
            response.setStatus(JsonResponse.ERROR);
            response.setMessage("Team id can not be empty");
            return response;
        }

        Team team = null;
        try{
	        team = teamDao.findTeamPlayers(teamId);
	
	        if (null == team) {
	        	logger.warn("Can not find team by team id:{}",teamId);
	            response.setStatus(JsonResponse.ERROR);
	            response.setMessage("Can not find team by team id" + teamId);
	            return response;
	        }
        }catch(Exception e){
			logger.error("findTeamPlayers  error,teamId:"+teamId,e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}

        // Convert team to football team
        FootballTeam footballTeam = new FootballTeam();
        ClientTeam clientTeam = new ClientTeam();
        BeanUtils.copyProperties(team, clientTeam, new String[]{"fields", "players"});
        footballTeam.setTeam(clientTeam);

        Set<Player> players = team.getPlayers();

        // Convert player to football players
        if (!CollectionUtils.isEmpty(players)) {
            Set<FootballPlayer> footballPlayers = new HashSet<>();
            Account account = AppContextHolder.getContext().getAccount();

            for (Player player : players) {
                User user = UserManager.buildUser(account, player);
                FootballPlayer footballPlayer = new FootballPlayer();
                footballPlayer.setUser(user);
                footballPlayers.add(footballPlayer);
            }

            footballTeam.setFootballPlayers(footballPlayers);
        }

        response.setStatus(JsonResponse.SUCCESS);
        response.setDomain(footballTeam);

        return response;
    }
    
    //查看球队dashboard 页面 API
    @GET
    @Path("/findTeamInfo/{teamId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findTeamInfo(@PathParam("teamId") Long teamId) {
    	DomainResponse<FootballTeamInfo> response = new DomainResponse<>();
    	FootballTeamInfo teamInfo = new FootballTeamInfo();
    	
    	@SuppressWarnings("unchecked")
		DomainResponse<FootballTeam> footballTeamresponse =   (DomainResponse<FootballTeam>) findTeamPlayers(teamId);
    	FootballTeam footballTeam = footballTeamresponse.getDomain();
    	try{
	    	if(ObjectUtil.isNotNull(footballTeam)){
	    		teamInfo.setTeam(footballTeam.getTeam());
	        	teamInfo.setFootballPlayers(footballTeam.getFootballPlayers());
	        	Account account = AppContextHolder.getContext().getAccount();
	        	String playerRelationshipWithTeam ="";
	        	if(teamDao.isPlayerInTeam(teamId, account.getId())){
	        		playerRelationshipWithTeam = "MEMBER";
	        	}else{
	        		playerRelationshipWithTeam = invitationDao.getTeamPlayerInvitationStatus(teamId, account.getId());
	        	}
	        	teamInfo.setCurrentUserRelationshipWithTeam(playerRelationshipWithTeam);
	        	//teamInfo.setApplicantPlayers(footballPlayers);
	        	response.setDomain(teamInfo);
	        	response.setStatus(JsonResponse.SUCCESS);
	    	}else{
	    		response.setDomain(null);
	        	response.setMessage("球队不存在，或者未找到");
	        	response.setStatus(JsonResponse.SUCCESS);
	    	}
    	}catch(Exception e){
			logger.error("findTeamInfo  error,teamId:"+teamId,e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
    	return response;
    }
    
    //进入球队dashboard里面的管理球队信息页面 API
    @GET
    @Path("/findTeamManagementInfo/{teamId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findTeamManagementInfo(@PathParam("teamId") Long teamId) {
    	DomainResponse<FootballTeamInfo> response = new DomainResponse<>();
    	FootballTeamInfo teamInfo = new FootballTeamInfo();
    	
    	@SuppressWarnings("unchecked")
		DomainResponse<FootballTeam> footballTeamresponse =   (DomainResponse<FootballTeam>) findTeamPlayers(teamId);
    	FootballTeam footballTeam = footballTeamresponse.getDomain();
    	Set<FootballPlayer> applicantFootballPlayers = new HashSet<>();
    	Set<FootballPlayer> invitedFootballPlayers = new HashSet<>();
    	try{
	    	List<Player> applicantPlayers = teamDao.findApplicantPlayers(teamId);
	    	List<Player> invitedPlayers = teamDao.findInvitedPlayers(teamId);
	    	
	    	Account account = AppContextHolder.getContext().getAccount();
	    	if (!CollectionUtils.isEmpty(applicantPlayers)) {
	            for (Player player : applicantPlayers) {
	                User user = UserManager.buildUser(account, player);
	                FootballPlayer footballPlayer = new FootballPlayer();
	                footballPlayer.setUser(user);
	                applicantFootballPlayers.add(footballPlayer);
	            }
	        }
	    	if (!CollectionUtils.isEmpty(invitedPlayers)) {
	            for (Player player : invitedPlayers) {
	                User user = UserManager.buildUser(account, player);
	                FootballPlayer footballPlayer = new FootballPlayer();
	                footballPlayer.setUser(user);
	                invitedFootballPlayers.add(footballPlayer);
	            }
	        }
	    	if(ObjectUtil.isNotNull(footballTeam)){
	    		teamInfo.setTeam(footballTeam.getTeam());
	        	teamInfo.setFootballPlayers(footballTeam.getFootballPlayers());
	        	teamInfo.setApplicantPlayers(applicantFootballPlayers);
	        	teamInfo.setInvitedPlayers(invitedFootballPlayers);
	        	
	        	response.setDomain(teamInfo);
	        	response.setStatus(JsonResponse.SUCCESS);
	    	}else{
	    		response.setDomain(null);
	        	response.setMessage("球队不存在，或者未找到");
	        	response.setStatus(JsonResponse.SUCCESS);
	    	}
	    	
    	}catch(Exception e){
			logger.error("findTeamManagementInfo  error,teamId:"+teamId,e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
    	return response;
    }
    
    @GET
    @Path("/findTeamsByPlayer/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findTeamsByPlayer() {
        DomainResponse<FootballPlayer> response = new DomainResponse<>();
        try {
        	Account account = AppContextHolder.getContext().getAccount();
            FootballPlayer footballplayer = userManager.findFootballPlayerById(account.getId());
            footballplayer.setUser(null);
            response.setDomain(footballplayer);
        } catch (RuntimeException e) {
        	logger.error("findTeamsByPlayer error",e);
            response.setStatus(JsonResponse.ERROR);
            response.setMessage(e.getMessage());
        }

        response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("find football player success");
        return response;
    }
    
    @POST
	@Path("/findTeamNameByPlayerId/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
    public String findTeamNameByPlayerId(FindTeamNameByPlayerIdRequest request) {
    	List<FootballTeam> teams = new ArrayList<FootballTeam>();
		try {
			teams = userManager.findTeamsByPlayerId(request.getPlayerId());
		} catch (DomainNotFoundException e) {
			logger.debug("There is no team found by player id:" + request.getPlayerId());
		}
    	
    	if (!CollectionUtils.isEmpty(teams)) {
    		return teams.get(teams.size() - 1).getTeam().getName();
    	}
    	
    	return "";
    }
    
    @POST
	@Path("/findTeamIdByPlayerId/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
    public String findTeamIdByPlayerId(FindTeamNameByPlayerIdRequest request) {
    	List<FootballTeam> teams = new ArrayList<FootballTeam>();
		try {
			teams = userManager.findTeamsByPlayerId(request.getPlayerId());
		} catch (DomainNotFoundException e) {
			logger.debug("There is no team found by player id:" + request.getPlayerId());
		}
    	
    	if (!CollectionUtils.isEmpty(teams)) {
    		return String.valueOf(teams.get(teams.size() - 1).getTeam().getId());
    	}
    	
    	return "";
    }
    
    @POST
	@Path("/createTeamByNamePlayerId/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
    public Long createTeamByNamePlayerId(CreateTeamByNamePlayerIdRequest request) {
    	Team team = new Team();
    	team.setName(request.getTeamName());
    	team.setCaptainUserId(request.getPlayerId());
    	team.setCreateBy("wechat");
    	team.setUpdateBy("wechat");
    	
    	Team savedTeam = teamDao.createNewTeam(team, request.getPlayerId());
    	
    	return savedTeam.getId();
    }
    
    @GET
    @Path("/findPlayerRelatedTeamsAndMembers/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findPlayerRelatedTeamsAndMembers() {
        DomainResponse<FootballPlayer> response = new DomainResponse<>();
        try {
        	Account account = AppContextHolder.getContext().getAccount();
            List<FootballTeam> teams = userManager.findTeamsByPlayerId(account.getId());
            for(FootballTeam footballTeam : teams){
            	@SuppressWarnings("unchecked")
				DomainResponse<FootballTeam> footballTeamResponse = 
            			(DomainResponse<FootballTeam>) findTeamPlayers(footballTeam.getTeam().getId());
            	footballTeam.setFootballPlayers(footballTeamResponse.getDomain().getFootballPlayers());
            }
            FootballPlayer footballplayer = new FootballPlayer(null, teams);
            List<PlayerBaseInfo> friendsList = playerDao.getPlayerFriendsBaseInfoList(account.getId());

            response.setList(friendsList);
            response.setDomain(footballplayer);
        } catch (RuntimeException | DomainNotFoundException e) {
        	logger.error("findPlayerRelatedTeamsAndMembers error",e);
            response.setStatus(JsonResponse.ERROR);
            response.setMessage(e.getMessage());
        } catch (CopyException e) {
			e.printStackTrace();
			response.setStatus(JsonResponse.ERROR);
            response.setMessage(e.getMessage());
		}

        response.setStatus(JsonResponse.SUCCESS);
        response.setMessage("find Player Related Teams And Members success And friendsList");
        return response;
    }
        
    /**
     * 获取球员所在球队的基本信息
     * 
     * @param teamId
     * @return
     */
    @GET
    @Path("/findPlayerTeamSummary/{teamId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findPlayerTeamSummary(@PathParam("teamId") Long teamId) {
    	ListResponse<PlayerTeamSummary> response = new ListResponse<>();
    	List<PlayerTeamSummary> list = null;
    	try{
    		list = teamDao.findPlayerTeamSummary(teamId);
    	}catch(Exception e){
			logger.error("findPlayerTeamSummary  error,teamId:"+teamId,e);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
    	}
    	response.setList(list);
    	response.setStatus(JsonResponse.SUCCESS);
    	response.setMessage("获取球员所在球队的基本信息成功");
    	return response;
    }
    
    
    /**
     * 获取球员在球队中的活动数据列表
     * @param teamId
     * @param playerId
     * @return
     */
    @GET
    @Path("/findPlayerTeamActivities/{teamId}/{playerId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findPlayerTeamActivities(@PathParam("teamId") Long teamId,
    		@PathParam("playerId") Long playerId) {
    	ListResponse<PlayerTeamActivity> response = new ListResponse<>();
    	List<PlayerTeamActivity> list = null;
    	try{
    		list = teamDao.findPlayerTeamActivities(playerId, teamId);
    	}catch(Exception e){
			logger.error("findPlayerTeamActivities  error",e);
			logger.error("findPlayerTeamActivities,param : teamId:{};playerId:{}",teamId,playerId);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
    	}
    	response.setList(list);
    	response.setStatus(JsonResponse.SUCCESS);
    	response.setMessage("获取球员在球队中的活动数据列表成功");
    	return response;
    }
    
    /**
     * 更新球员在球队的基本信息
     * @param teamPlayer
     * @return
     */
    @POST
	@Path("/updatePlayerTeamSummary/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse updatePlayerTeamSummary(TeamPlayer teamPlayer) {
		JsonResponse response = new JsonResponse();
		try{
			teamDao.updatePlayerTeamSummary(teamPlayer);
		}catch(Exception e){
			logger.error("updatePlayerTeamSummary  error",e);
			logger.error("updatePlayerTeamSummary  error,param:teamPlayer:{}",JacksonUtil.toJson(teamPlayer));
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
		}
		response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("更新球员在球队的基本信息成功");
		return response;
	}
    
    @POST
    @Path("/leaveTeam/{teamId}/{playerId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse leaveTeam(@PathParam("teamId") Long teamId,
    		@PathParam("playerId") Long playerId){
    	JsonResponse response = new JsonResponse();
    	try{
    		teamDao.updateTeamPlayerStatus(teamId, playerId, TEAM_PLAYER_STATUS.LEAVE);
    	}catch(Exception e){
			logger.error("leaveTeam  error",e);
			logger.error("leaveTeam error,param : teamId:{};playerId:{}",teamId,playerId);
			response.setStatus(JsonResponse.ERROR);
			response.setMessage(e.getMessage());
			return response;
	}
    	response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("更新球员在球队的基本信息成功");
		return response;
    } 
    
    
    @POST
	@Path("/findTeamsByCaptainUserId/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
    public JsonResponse findTeamsByCaptainUserId(FindTeamsByCaptainUserIdRequest request){
    	ListResponse<TeamBasic> response = new ListResponse<TeamDao.TeamBasic>();
    	List<TeamBasic> teams = teamDao.findTeamsByCaptainUserId(request.getCaptainUserId());
    	response.setList(teams);
    	response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("获取队长的所有球队成功!");
		return response;
    }
    
    
 /*   @POST
    @Path("/joinTeam/{teamId}/{playerId}/{identity}/{appId}/{apptoken}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse joinTeam(@PathParam("teamId") Long teamId,
    		@PathParam("playerId") Long playerId){
    	JsonResponse response = new JsonResponse();
    	teamDao.updateTeamPlayerStatus(teamId, playerId, TEAM_PLAYER_STATUS.ACTIVE);
    	response.setStatus(JsonResponse.SUCCESS);
		response.setMessage("更新球员在球队的基本信息成功");
		return response;
    }*/
    
}
