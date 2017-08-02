package com.footballer.rest.api.v2.restfulService;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import jersey.repackaged.com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.request.TournamentMatchDataStatisticsRequest;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.response.ListResponse;
import com.footballer.rest.api.v2.Response.GetTournamentVideoListResponse;
import com.footballer.rest.api.v2.Response.JsonListResonse;
import com.footballer.rest.api.v2.Response.TournamentResponse;
import com.footballer.rest.api.v2.domain.PagingInfo;
import com.footballer.rest.api.v2.manager.CommonManager;
import com.footballer.rest.api.v2.manager.MomentManager;
import com.footballer.rest.api.v2.manager.SupportManager;
import com.footballer.rest.api.v2.manager.TournamentManager;
import com.footballer.rest.api.v2.manager.UserCommentsManager;
import com.footballer.rest.api.v2.manager.UserShareRecordManager;
import com.footballer.rest.api.v2.request.DeleteTournamentTeamByIdRequest;
import com.footballer.rest.api.v2.request.GetThumbnailByUrlRequest;
import com.footballer.rest.api.v2.request.GetTournamentVideoListRequest;
import com.footballer.rest.api.v2.request.ShareRequest;
import com.footballer.rest.api.v2.vo.Moment;
import com.footballer.rest.api.v2.vo.Team;
import com.footballer.rest.api.v2.vo.Tournament;
import com.footballer.rest.api.v2.vo.TournamentMatch;
import com.footballer.rest.api.v2.vo.TournamentMatchSchedule;
import com.footballer.rest.api.v2.vo.Video;
import com.footballer.util.ObjectUtil;
import com.utils.restful.YoukuClient;

@Path("/mobile/v2/tournament-service")
public class TournamentService {
	
	@Autowired
	private TournamentManager tMgr;
	
	@Autowired
	private UserCommentsManager umMgr;
	
	@Autowired
	private UserShareRecordManager usrMgr;
	
	@Autowired
	private SupportManager sMgr;
	
	@Autowired
	private CommonManager cMgr;
	
	@Autowired
	private MomentManager mMgr;

	//获取所有赛事列表
	@GET
	@Path("/getAllTournamentList/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getAllTournamentList(@PathParam("playerId") Long playerId) {
		TournamentResponse response = new TournamentResponse();
		
		response.setTournamentsList(tMgr.getAllTournamentList(playerId));
		response.setStatus(JsonResponse.SUCCESS);
		return response;
	}
	
	
	
	//获取所有赛事列表，足球，篮球
	@GET
	@Path("/getAllSportsTournament/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getAllSportsTournament() {
		TournamentResponse response = new TournamentResponse();

		response.setTournamentsList(tMgr.getAllSportsTournament());
		response.setStatus(JsonResponse.SUCCESS);
		return response;
	}
	
	
	//获取球员的所有赛事列表
	@GET
	@Path("/getTournamentList/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentList(@PathParam("playerId") Long playerId) {
		TournamentResponse response = new TournamentResponse();

		if (ObjectUtil.isNotNull(playerId) && cMgr.checkPlayerExist(playerId)) {
			
			response.setTournamentsList(tMgr.getTournamentList(playerId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	
	// 获取某个赛事基本信息
	@GET 
	@Path("/getTournamentBaseInfo/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentBaseInfo(@PathParam("tournamentId") Long tournamentId) {
			TournamentResponse response = new TournamentResponse();

			if (ObjectUtil.isNotNull(tournamentId)) {
				response.setTournamentBaseInfo(tMgr.getTournamentBaseInfoById(tournamentId));
				response.setStatus(JsonResponse.SUCCESS);
				return response;
			} else {
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("parameters can not null");
				return response;
			}
	}

	//完整赛程
	@GET
	@Path("/getTournamentFullCalendarInfo/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentFullCalendarInfo(@PathParam("tournamentId") Long tournamentId) {
		TournamentResponse response = new TournamentResponse();

		if (ObjectUtil.isNotNull(tournamentId)) {
			response.setTournamentMatchList(tMgr.getTournamentFullCalendarInfoById(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//篮球 获取完全赛程
	@GET
	@Path("/getBasketBallTournamentFullCalendarInfo/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getBasketBallTournamentFullCalendarInfo(@PathParam("tournamentId") Long tournamentId) {
		TournamentResponse response = new TournamentResponse();

		if (ObjectUtil.isNotNull(tournamentId)) {
			response.setTournamentMatchList(tMgr.getBasketBallTournamentFullCalendarInfoById(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	//赛事所有参赛球队
	@GET
	@Path("/getTournamentTeamsInfo/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentTeamsInfo(@PathParam("tournamentId") Long tournamentId) {
		TournamentResponse response = new TournamentResponse();

		if (ObjectUtil.isNotNull(tournamentId)) {
			response.setTournamentTeamBaseInfoList(tMgr.getTournamentTeamsInfoById(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	@GET
	@Path("/getTournamentMatchListByTournamentId/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public JsonListResonse<TournamentMatch> getTournamentMatchListByTournamentId(
			@PathParam("tournamentId") Long tournamentId) {
		
		JsonListResonse<TournamentMatch> response = new JsonListResonse<TournamentMatch>();

		if (ObjectUtil.isNotNull(tournamentId)) {
			response.setList(tMgr.getTournamentMatchListByTournamentId(tournamentId));
			return response;
		} else {
			return response;
		}
	}
	
	//小组积分榜
	@GET
	@Path("/getTournamentGourpStandings/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentGourpStandings(@PathParam("tournamentId") Long tournamentId) {
		TournamentResponse response = new TournamentResponse();

		if (ObjectUtil.isNotNull(tournamentId)) {
			response.setTeamStandingList(tMgr.getTournamentGourpStandings(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//篮球赛事积分榜
	@GET
	@Path("/getBasketBallTournamentGourpStandings/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getBasketBallTournamentGourpStandings(@PathParam("tournamentId") Long tournamentId) {
		TournamentResponse response = new TournamentResponse();

		if (ObjectUtil.isNotNull(tournamentId)) {
			response.setTeamStandingList(tMgr.getBasketBallTournamentGourpStandings(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//球员个人数据项(累计)总排名
	@GET
	@Path("/getTournamentPlayerStatisticRanking/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentPlayerStatisticRanking(@PathParam("tournamentId") Long tournamentId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) ) {
			response.setPlayerMatchResultList(tMgr.getTournamentPlayerStatisticRankingById(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//篮球 球员个人数据项(累计)总排名
	@GET
	@Path("/getBasketBallTournamentPlayerStatisticRanking/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getBasketBallTournamentPlayerStatisticRanking(@PathParam("tournamentId") Long tournamentId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) ) {
			response.setBasketBallPlayerMatchResultList(tMgr.getBasketBallTournamentPlayerStatisticRankingById(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//球队单项数据（累计）总排名
	@GET
	@Path("/getTournamentTeamStatisticRanking/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentTeamStatisticRanking(@PathParam("tournamentId") Long tournamentId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) ) {
			response.setTeamMatchResultList(tMgr.getTournamentTeamStatisticRankingById(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	
	//篮球  球队单项数据（累计）总排名
	@GET
	@Path("/getBasketBallTournamentTeamStatisticRanking/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getBasketBallTournamentTeamStatisticRanking(@PathParam("tournamentId") Long tournamentId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) ) {
			response.setBasketBallTeamMatchResultList(tMgr.getBasketBallTournamentTeamStatisticRankingById(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	
	
	//获取指定球队的杯赛赛程
	@GET
	@Path("/getTeamTournamentCalendar/{tournamentId}/{teamId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTeamTournamentCalendar(@PathParam("tournamentId") Long tournamentId, @PathParam("teamId") Long teamId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(teamId) ) {
			response.setTournamentMatchList(tMgr.getTeamTournamentCalendar(tournamentId,teamId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	
	//获取指定球队的杯赛赛程
	@GET
	@Path("/getBasketBallTeamTournamentCalendar/{tournamentId}/{teamId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getBasketBallTeamTournamentCalendar(@PathParam("tournamentId") Long tournamentId, @PathParam("teamId") Long teamId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(teamId) ) {
			response.setTournamentMatchList(tMgr.getBasketBallTeamTournamentCalendar(tournamentId,teamId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	//获取指定球队的完成的比赛结果
	@GET
	@Path("/getTeamTournamentMatchResults/{tournamentId}/{matchId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTeamTournamentMatchResults(@PathParam("tournamentId") Long tournamentId, @PathParam("matchId") Long matchId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(matchId) ) {
			
			//根据matchId 获取 2个球队的teamId
			List<Long> teamIds = tMgr.getTournamentMatchTwoTeamID(matchId);
			Long hostTeamId =teamIds.get(0);
			Long guestTeamId = teamIds.get(1);

			if(ObjectUtil.isNotNull(hostTeamId) && ObjectUtil.isNotNull(guestTeamId)){
				List<TournamentMatch> tournamentMatchList  = Lists.newArrayList();
				tournamentMatchList.addAll(tMgr.getTeamTournamentMatchResults(tournamentId,hostTeamId));
				tournamentMatchList.addAll(tMgr.getTeamTournamentMatchResults(tournamentId,guestTeamId));
				response.setTournamentMatchList(tournamentMatchList);
				response.setStatus(JsonResponse.SUCCESS);
			}else{
				response.setStatus(JsonResponse.ERROR);
			}
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	
	//获取某个球队的所有球员名单（头像，名字，号码，位置，身高，体重，年龄，mvp,team_mvp）
	@GET
	@Path("/getTeamTournamentPlayerInfos/{tournamentId}/{teamId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTeamTournamentPlayerInfos(@PathParam("tournamentId") Long tournamentId, @PathParam("teamId") Long teamId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(teamId) ) {
			response.setTournamentTeamPlayerInfoList(tMgr.getTeamTournamentPlayerInfos(tournamentId,teamId));
			response.setTournamentTeamInfo(tMgr.getTournamentTeamBaseInfo(tournamentId,teamId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}

	// <篮球> 获取某个球队的球队和所有球员名单（头像，名字，号码，位置）
	@GET
	@Path("/getBasketballTeamTournamentPlayerInfos/{tournamentId}/{teamId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getBasketballTeamTournamentPlayerInfos(@PathParam("tournamentId") Long tournamentId,@PathParam("teamId") Long teamId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(teamId)) {
			response.setTournamentTeamPlayerInfoList(tMgr.getBasketballTeamPlayerInfos(teamId));
			response.setTournamentTeamInfo(tMgr.getBasketballTeamBaseInfo(teamId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	
	//获取某场比赛的2支球队所有球员名单（头像，名字，号码，位置，mvp,team_mvp）
	@GET
	@Path("/getTournamentMatchTeamPlayerInfos/{tournamentId}/{matchId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentMatchTeamPlayerInfos(@PathParam("tournamentId") Long tournamentId, @PathParam("matchId") Long matchId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(matchId) ) {
			
			//根据matchId 获取 2个球队的teamId
			List<Long> teamIds = tMgr.getTournamentMatchTwoTeamID(matchId);
			Long hostTeamId =teamIds.get(0);
			Long guestTeamId = teamIds.get(1);
			
			response.setHostTeamPlayerInfoList(tMgr.getTeamPlayerInfos(hostTeamId));
			response.setGuestTeamPlayerInfoList(tMgr.getTeamPlayerInfos(guestTeamId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	//某场比赛两队数据汇总 （从球员结果 取总和）
	@GET
	@Path("/getTournamentMatchTeamInfo/{tournamentMatchId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentMatchTeamInfo(@PathParam("tournamentMatchId") Long tournamentMatchId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentMatchId) ) {
			response.setTeamMatchResultList(tMgr.getTournamentMatchTeamInfo(tournamentMatchId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	
	//某场比赛两队球队的数据汇总v2 （直接从球队结果拿取）
	@GET
	@Path("/getTournamentMatchTeamResultInfo/{tournamentMatchId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentMatchTeamResultInfo(@PathParam("tournamentMatchId") Long tournamentMatchId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentMatchId) ) {
			response.setTeamMatchResultList(tMgr.getTournamentMatchTeamResultInfo(tournamentMatchId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	
	//篮球 某场比赛两队数据汇总
	@GET
	@Path("/getBasketBallTournamentMatchTeamInfo/{tournamentMatchId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getBasketBallTournamentMatchTeamInfo(@PathParam("tournamentMatchId") Long tournamentMatchId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentMatchId) ) {
			response.setBasketBallTeamMatchResultList(tMgr.getBasketBallTournamentMatchTeamInfo(tournamentMatchId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	
	//某场比赛两队所有球员的详细数据统计
	//（同时用于，展示本场比赛的 mvp 和 两队队内mvp）
	@GET
	@Path("/getTournamentMatchPlayerInfo/{tournamentMatchId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentMatchPlayerInfo(@PathParam("tournamentMatchId") Long tournamentMatchId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentMatchId) ) {
			response.setPlayerMatchResultList(tMgr.getTournamentMatchPlayerInfo(tournamentMatchId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//篮球 某场比赛两队所有球员的详细数据统计
	//（同时用于，展示本场比赛的 mvp 和 两队队内mvp）
	@GET
	@Path("/getBasketBallTournamentMatchPlayerInfo/{tournamentMatchId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getBasketBallTournamentMatchPlayerInfo(@PathParam("tournamentMatchId") Long tournamentMatchId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentMatchId) ) {
			response.setBasketBallPlayerMatchResultList(tMgr.getBasketBallTournamentMatchPlayerInfo(tournamentMatchId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	/**
	 * 增加比赛视频信息
	 * @param request
	 * @return
	 */
	@POST
	@Path("/saveVideoInfo/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveVideoInfo(Video video) {
		JsonResponse response = new JsonResponse();
		int count = 0;
		
		if (video.getId() != null) {
			count = tMgr.updateVideo(video);
		} else {
			count = tMgr.insertVideo(video);
		}
		
		if(count > 0){
			response.setStatus(JsonResponse.SUCCESS);
		}else{
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("保存比赛视频相关信息失败");
		}
		return response;
	}
	
	@POST
	@Path("/createTournament/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse createTournament(Tournament tournament) {
		
		JsonResponse response = new JsonResponse();
		
		if (tournament.getStartDt().after(tournament.getEndDt())) {
			response.setMessage("报名开始时间不能晚于报名结束时间!");
			response.setStatus(JsonResponse.ERROR);
		}
		
		if (tournament.getRegisterStartDateTime().after(
				tournament.getRegisterEndDateTime())) {
			response.setMessage("注册开始时间不能晚于注册结束时间!");
			response.setStatus(JsonResponse.ERROR);
		}
		
		if (tournament.getStartDt()
				.after(tournament.getRegisterStartDateTime())) {
			response.setMessage("报名开始时间不能晚于注册开始时间!");
			response.setStatus(JsonResponse.ERROR);
		}		
		
		this.tMgr.saveTournament(tournament);
		response.setStatus(JsonResponse.SUCCESS);
		return response;
	}
	
	//人工统计页面-录入球队,球员各项数据统计
	@POST
	@Path("/saveTournamentMatchDataStatistics/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveTournamentMatchDataStatistics(TournamentMatchDataStatisticsRequest request) {
		JsonResponse response = new JsonResponse(); 
		if(tMgr.saveTournamentMatchDataStatistics(request)){
			response.setStatus(JsonResponse.SUCCESS);
		}else{
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("传入数据：球队或球员数量为0");
		}
		return response;
	} 
	
	//统计页面-只录入球员各项数据统计
	//1.直接删除已存在的当场比赛数据 用新得 覆盖
	//2.并不计算比赛结果
	@POST
	@Path("/saveTournamentMatchPlayerDataStatistics/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveTournamentMatchPlayerDataStatistics(TournamentMatchDataStatisticsRequest request) {
		JsonResponse response = new JsonResponse(); 
		if(tMgr.saveTournamentMatchPlayerDataStatistics(request)){
			response.setStatus(JsonResponse.SUCCESS);
			response.setMessage("球员数据录入成功");
		}else{
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("传入数据：球队或球员数量为0");
		}
		return response;
	} 	
	
	//【篮球】 excel文件解析后 -录入球队,球员各项数据统计
	@POST
	@Path("/saveBasketBallTournamentMatchDataStatistics/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveBasketBallTournamentMatchDataStatistics(TournamentMatchDataStatisticsRequest request) {
		JsonResponse response = new JsonResponse();
		if (tMgr.saveBasketBallTournamentMatchDataStatistics(request)) {
			response.setStatus(JsonResponse.SUCCESS);
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("传入数据：球队或球员数量为0");
		}
		return response;
	}	
	
	//某场比赛视频内容，评论 点赞,分享记录等信息
	@POST
	@Path("/getTournamentMatchVideoRelatedInfo/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentMatchVideoRelatedInfo(ShareRequest sReq) {
		TournamentResponse response = new TournamentResponse();
		Long tournamentMatchId = sReq.getMatchId();
		
		if (ObjectUtil.isNotNull(tournamentMatchId) ) {
			Video video = tMgr.getTournamentMatchVideoInfo(tournamentMatchId);
			response.setVideoInfo(video);
			if(ObjectUtil.isNotNull(video)){
				response.setCommentsList(umMgr.getVideoUserCommentsList(video.getId()));
				response.setSupportList(sMgr.getVideoUserSupportList(video.getId()));
				response.setShareRecordList(usrMgr.getUserShareRecordByVideoId(video.getId()));
			}
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
				response.setStatus(JsonResponse.ERROR);				
				response.setMessage("parameters can not null");
				return response;
		}
	}
	
	/**
	 * 更新视频的缩略图
	 * 
	 * @return 成功更新的数量
	 */
	@GET
	@Path("/updateVideoThumbnail/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public JsonResponse updateVideoThumbnail() {
		int updatedCount = tMgr.updateVideoThumbnail();
		JsonResponse response = new JsonResponse();
		response.setMessage("update video count: " + updatedCount);
		response.setStatus(JsonResponse.SUCCESS);
		return response;
	}
	
	
	@POST
	@Path("/getThumbnailByUrl/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getThumbnailByUrl(GetThumbnailByUrlRequest request) {		
		return YoukuClient.getVideoThumbnail(request.getUrl());
	}
	
	// 加载某个视频内容，评论 点赞,分享记录等信息
	@GET
	@Path("/getVideoRelatedInfo/{videoId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getVideoRelatedInfo(@PathParam("videoId") Long videoId) {
		TournamentResponse response = new TournamentResponse();

		if (ObjectUtil.isNotNull(videoId)) {
			Video video = tMgr.getVideoInfoById(videoId);
			response.setVideoInfo(video);
			response.setCommentsList(umMgr.getVideoUserCommentsList(videoId));
			response.setSupportList(sMgr.getVideoUserSupportList(videoId));
			response.setShareRecordList(usrMgr.getUserShareRecordByVideoId(videoId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("videoId can not null");
			return response;
		}
	}
	
	// 加载某个分享的动态，评论 点赞,分享记录等信息
	@GET
	@Path("/getMomentRelatedInfo/{momentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getMomentRelatedInfo(@PathParam("momentId") Long momentId) {
		TournamentResponse response = new TournamentResponse();

		if (ObjectUtil.isNotNull(momentId)) {
			Moment moment = tMgr.getMomentInfoById(momentId);
			
			response.setMomentInfo(moment);
			response.setCommentsList(umMgr.getMomentCommentsList(momentId));
			response.setSupportList(sMgr.getMomentSupportList(momentId));
			response.setShareRecordList(usrMgr.getUserShareRecordByMomentId(momentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("videoId can not null");
			return response;
		}
	}
	
	// 获取联赛视频基本信息列表
	@GET
	@Path("/getVideoInfoByTournamentId/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public GetTournamentVideoListResponse getVideoInfoByTournamentId(
			@PathParam("tournamentId") Long tournamentId) {
		GetTournamentVideoListResponse response = new GetTournamentVideoListResponse();
		if (ObjectUtil.isNotNull(tournamentId)) {
			List<Video> videos = tMgr.getVideoInfoByTournamentId(tournamentId);
			response.setVideos(videos);
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//获取联赛视频基本信息列表
	@POST
	@Path("/getTournamentVideoList/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public GetTournamentVideoListResponse getTournamentVideoList(GetTournamentVideoListRequest req) {
		GetTournamentVideoListResponse response = new GetTournamentVideoListResponse();
		Long tournamentId = req.getTournamentId();
		
		if (ObjectUtil.isNotNull(tournamentId) ) {
			List<Video> videos = tMgr.getTournamentVideoList(tournamentId);
			response.setVideos(videos);
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
				response.setStatus(JsonResponse.ERROR);				
				response.setMessage("parameters can not null");
				return response;
		}
	}
	
	// 获取指定球队的指定联赛视频基本信息列表
	@GET
	@Path("/getTeamTournamentVideoList/{teamId}/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public GetTournamentVideoListResponse getTeamTournamentVideoList(@PathParam("teamId") Long teamId,@PathParam("tournamentId") Long tournamentId) {
		GetTournamentVideoListResponse response = new GetTournamentVideoListResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(teamId)) {
			List<Video> videos = tMgr.getTeamTournamentVideoList(teamId,tournamentId);
			response.setVideos(videos);
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	
	//[TeamDashboard]获取球队所在赛事所有数据项列表 及 总数据和排名
	@GET
	@Path("/getTeamTournamentStats/{tournamentId}/{teamId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTeamTournamentStats(@PathParam("tournamentId") Long tournamentId,@PathParam("teamId") Long teamId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(teamId)) {
			
			response.setTeamAllTournamentMatchResultList(tMgr.getTeamTournamentStats(tournamentId,teamId));
			response.setTeamMatchResultList(tMgr.getTournamentTeamStatisticRankingById(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//篮球 [TeamDashboard]获取球队所在赛事所有数据项列表 及 总数据和排名
	@GET
	@Path("/loadBasketBallTeamTournamentStats/{tournamentId}/{teamId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse loadBasketBallTeamTournamentStats(@PathParam("tournamentId") Long tournamentId,@PathParam("teamId") Long teamId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(teamId)) {
			
			response.setBasketBallTeamAllTournamentMatchResultList(tMgr.getBasketBallTeamTournamentStats(tournamentId,teamId));
			response.setBasketBallTeamMatchResultList(tMgr.getBasketBallTournamentTeamStatisticRankingById(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	

	//[PlayerDashboard]获取球员所在赛事所有数据项列表 及 总数据和排名
	@GET
	@Path("/getPlayerTournamentStats/{tournamentId}/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getPlayerTournamentStats(@PathParam("tournamentId") Long tournamentId,@PathParam("playerId") Long playerId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(playerId)) {
			
			response.setPlayerMatchResultList(tMgr.getPlayerTournamentTotalStats(tournamentId));
			response.setPlayerAllTournamentMatchResultList(tMgr.getPlayerAllTournamentMatchResultList(tournamentId,playerId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//篮球 [PlayerDashboard]获取球员所在赛事所有数据项列表 及 总数据和排名
	@GET
	@Path("/loadBasketBallPlayerTournamentStats/{tournamentId}/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse loadBasketBallPlayerTournamentStats(@PathParam("tournamentId") Long tournamentId,@PathParam("playerId") Long playerId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(playerId)) {
			
			response.setBasketBallPlayerMatchResultList(tMgr.getBasketBallPlayerTournamentTotalStats(tournamentId));
			response.setBasketBallPlayerAllTournamentMatchResultList(tMgr.getBasketBallPlayerAllTournamentMatchResultList(tournamentId,playerId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}	

	// [bbtournamentStats-Table] 获取当前赛事所有球队的对阵胜负情况
	@GET
	@Path("/getTournamentMatchWinLoseList/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentMatchWinLoseList(@PathParam("tournamentId") Long tournamentId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId)) {

			
			response.setBasketBallTeamMatchResultList(tMgr.getTournamentMatchWinLoseList(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}	
	
	
	
	//[PlayerDashboard]获取球员所在赛事的基本属性和信息
	@GET
	@Path("/getPlayerTournamentBaseInfo/{tournamentId}/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getPlayerTournamentBaseInfo(@PathParam("tournamentId") Long tournamentId,@PathParam("playerId") Long playerId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(playerId)) {
			
			response.setTournamentPlayerBaseInfo(tMgr.getTournamentPlayerBaseInfo(tournamentId,playerId));
			response.setTournamentRegisteredPlayer(tMgr.checkPlayerisTournamentRegisteredPlayer(tournamentId, playerId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	
	//[TeamDashboard] 检查当前球员是否已经注册了当前赛事
	@GET
	@Path("/checkPlayerisTournamentRegisteredPlayer/{tournamentId}/{playerId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse checkPlayerisTournamentRegisteredPlayer(@PathParam("tournamentId") Long tournamentId,@PathParam("playerId") Long playerId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId) && ObjectUtil.isNotNull(playerId)) {
			
			response.setTournamentRegisteredPlayer(tMgr.checkPlayerisTournamentRegisteredPlayer(tournamentId, playerId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//【用户分享数据保存】 
	@POST
	@Path("/saveMoments/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse saveMoments(Moment moment) {
		JsonResponse response = new JsonResponse();
		if (mMgr.saveMoment(moment)) {
			response.setStatus(JsonResponse.SUCCESS);
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("Moment分享失败");
		}
		return response;
	}		
	
	
	//加载赛事动态-分页
	@POST
	@Path("/getTournamentPagingTimeLines/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getTournamentPagingTimeLines(PagingInfo pageInfo) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(pageInfo.getTournamentId())) {
			response.setTournamentTimeLineList(tMgr.getTournamentPagingTimeLines(pageInfo));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	@POST
	@Path("/registerTournementByTeam/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse registerTournementByTeam(Team team) {
		JsonResponse response = new JsonResponse();
		if (team.getId() == null) {
			tMgr.createTeam(team);
		}
		tMgr.createTournamentTeam(team);
		response.setStatus(JsonResponse.SUCCESS);
		return response;
	}
	
	@POST
	@Path("/deleteTournamentTeamById/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse deleteTournamentTeamById(DeleteTournamentTeamByIdRequest request) {
		JsonResponse response = new JsonResponse();
		tMgr.deleteTournamentTeamById(request.getTournamentId(), request.getTeamIdList());
		response.setStatus(JsonResponse.SUCCESS);
		return response;
	}
	
	
	//加载【个人】赛事动态-分页
	@POST
	@Path("/getPlayerTournamentPagingTimeLines/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public TournamentResponse getPlayerTournamentPagingTimeLines(PagingInfo pageInfo) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(pageInfo.getTournamentId()) && ObjectUtil.isNotNull(pageInfo.getPlayerId())) {
			response.setTournamentTimeLineList(tMgr.getPlayerTournamentPagingTimeLines(pageInfo));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	//上传 录入赛事比分 
	// 如果相关赛事比分已经存在 会直接删除之前存在得球队赛事信息，并插入新得赛事比分信息
	// 无记录则直接插入数据
	@POST
	@Path("/saveMatchScoresResult/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public JsonResponse saveMatchScoresResult(TournamentMatchDataStatisticsRequest request) {
		JsonResponse response = new JsonResponse(); 
		
		if(request.getMatchIdsList().size()>0 && request.getImportTeamDataList().size()>0){
			//先移除与当前赛事冲突得数据
			tMgr.removeMatchesResultByIds(request);
			if(tMgr.saveMatchTeamResult(request)){
				response.setStatus(JsonResponse.SUCCESS);
				response.setMessage("比分数据：录入成功");
			}else{
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("传入数据：未传入正确得比赛比分数据");
			}
		}
		else{
			response.setStatus(JsonResponse.NODATA);
			response.setMessage("未传入有效数据：略过");
		}
		return response;
	} 
	
	
	//获取赛事得 数据统计级别及相关数据项
	@GET
	@Path("/getTournamentStatsContent/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public TournamentResponse getTournamentStatsContent(@PathParam("tournamentId") Long tournamentId) {
		TournamentResponse response = new TournamentResponse();
		if (ObjectUtil.isNotNull(tournamentId)) {
			
			response.setStatsContent(tMgr.getTournamentStatsContent(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);				
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	/**
	 * 创建赛程
	 * 
	 * 赛程类型:
	 *   1: 单循环
	 *   2: 双循环
	 *   3: 杯赛
	 * 
	 * @param request
	 * 
	 */
	@POST
	@Path("/createTournamentSchedule/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse createTournamentSchedule(TournamentMatchSchedule request) {
		JsonResponse response = new JsonResponse(); 
		
		// 检查tournament的状态
		Tournament tournament = tMgr.getTournamentBaseInfoById(request.getTournamentId());
		String status = tournament.getTournamentStatus();
		
		// 1 : 可报名, 审核通过而且比赛未开赛
		if ("1".equals(status)) {
			response.setStatus(JsonResponse.SUCCESS);
			tMgr.createMatchScedule(request);
		} else {
			response.setStatus(JsonResponse.ERROR);
			if ("0".equals(status)) {
				response.setMessage("赛事未审核通过");
			} else if ("2".equals(status)) {
				response.setMessage("赛事已开赛");
			} else if ("3".equals("")) {
				response.setMessage("赛事已结束");
			}
		}
		
		return response;
	}
	
	@POST
	@Path("/findMatchScheduleByTournamentId/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse findMatchScheduleByTournamentId(Long tournamentId) {
		ListResponse<TournamentMatchSchedule> response = new ListResponse<TournamentMatchSchedule>(); 
		List<TournamentMatchSchedule> list = tMgr.findMatchScheduleByTournamentId(tournamentId);
		response.setList(list);
		return response;
	}
	
	@POST
	@Path("/getTournamentMatchListByMatchScheduleId/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public JsonResponse getTournamentMatchListByMatchScheduleId(TournamentMatch request) {
		ListResponse<TournamentMatch> response = new ListResponse<TournamentMatch>(); 
		List<TournamentMatch> list = tMgr.getTournamentMatchListByMatchScheduleId(request.getTournamentId(), request.getMatchScheduleId());
		response.setList(list);
		return response;
	}
	
}
