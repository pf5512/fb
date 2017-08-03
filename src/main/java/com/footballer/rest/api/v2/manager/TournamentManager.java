package com.footballer.rest.api.v2.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.footballer.rest.api.v1.request.TournamentMatchDataStatisticsRequest;
import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.BasketBallMatchResult;
import com.footballer.rest.api.v2.domain.PagingInfo;
import com.footballer.rest.api.v2.domain.StatsContent;
import com.footballer.rest.api.v2.domain.TimeLine;
import com.footballer.rest.api.v2.manager.schdule.DoubleLeagueStrategy;
import com.footballer.rest.api.v2.manager.schdule.ScheduleManager;
import com.footballer.rest.api.v2.manager.schdule.ScheduleMatchItem;
import com.footballer.rest.api.v2.manager.schdule.ScheduleStrategy;
import com.footballer.rest.api.v2.manager.schdule.SingleLeagueStrgategy;
import com.footballer.rest.api.v2.vo.BasketBallPlayerMatchResult;
import com.footballer.rest.api.v2.vo.BasketBallTeamMatchResult;
import com.footballer.rest.api.v2.vo.TournamentMatchSchedule;
import com.footballer.rest.api.v2.vo.Moment;
import com.footballer.rest.api.v2.vo.PlayerMatchResult;
import com.footballer.rest.api.v2.vo.Team;
import com.footballer.rest.api.v2.vo.TeamMatchResult;
import com.footballer.rest.api.v2.vo.Tournament;
import com.footballer.rest.api.v2.vo.TournamentGourpTeamStanding;
import com.footballer.rest.api.v2.vo.TournamentMatch;
import com.footballer.rest.api.v2.vo.TournamentPlayerBaseInfo;
import com.footballer.rest.api.v2.vo.TournamentRemindInfo;
import com.footballer.rest.api.v2.vo.TournamentTeamBaseInfo;
import com.footballer.rest.api.v2.vo.TournamentTeamPlayerInfo;
import com.footballer.rest.api.v2.vo.Video;
import com.footballer.rest.api.v2.vo.enumType.TournamentType;
import com.footballer.util.DateUtil;
import com.footballer.util.ObjectUtil;
import com.footballer.util.StringUtil;
import com.google.common.collect.Lists;
import com.utils.restful.YoukuClient;


@Service
public class TournamentManager {

	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	@SuppressWarnings("unchecked")
	public List<Tournament> getAllTournamentList(Long playerId){
		return (List<Tournament>) mybatisBaseDao.selectList("getAllTournamentList", playerId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tournament> getAllSportsTournament(){
		return (List<Tournament>) mybatisBaseDao.selectList("getAllSportsTournament",null);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tournament> getTournamentList(Long playerId){
		return (List<Tournament>) mybatisBaseDao.selectList("getTournamentList", playerId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> getTournamentMatchTwoTeamID(Long matchId){
		return (List<Long>) mybatisBaseDao.selectList("getTournamentMatchTwoTeamID", matchId);
	}

	@SuppressWarnings("unchecked")
	public List<TournamentMatch> getTournamentFullCalendarInfoById(Long tournamentId) {
		return (List<TournamentMatch>) mybatisBaseDao.selectList("getTournamentFullCalendarInfoById", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<TournamentMatch> getBasketBallTournamentFullCalendarInfoById(Long tournamentId) {
		return (List<TournamentMatch>) mybatisBaseDao.selectList("getBasketBallTournamentFullCalendarInfoById", tournamentId);
	}
	
	public Tournament getTournamentBaseInfoById(Long tournamentId) {
		return (Tournament) mybatisBaseDao.selectOne("getTournamentBaseInfoById", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<TournamentTeamBaseInfo> getTournamentTeamsInfoById(Long tournamentId){
		return (List<TournamentTeamBaseInfo>) mybatisBaseDao.selectList("getTournamentTeamsInfoById", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<TournamentPlayerBaseInfo> getTournamentPlayersInfoByTournamentId(Long tournamentId){
		return (List<TournamentPlayerBaseInfo>) mybatisBaseDao.selectList("getTournamentPlayersInfoByTournamentId", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<TournamentGourpTeamStanding> getTournamentGourpStandings(Long tournamentId){
		TournamentTeamBaseInfo ttbi =new TournamentTeamBaseInfo();
		ttbi.setTournamentId(tournamentId);
		//ttbi.setGroupName(groupName);
		return (List<TournamentGourpTeamStanding>) mybatisBaseDao.selectList("getTournamentGourpStandings", ttbi);
	}
	
	@SuppressWarnings("unchecked")
	public List<TournamentGourpTeamStanding> getBasketBallTournamentGourpStandings(Long tournamentId){
		
		return (List<TournamentGourpTeamStanding>) mybatisBaseDao.selectList("getBasketBallTournamentGourpStandings", tournamentId);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PlayerMatchResult> getTournamentPlayerStatisticRankingById(Long tournamentId){
		return (List<PlayerMatchResult>) mybatisBaseDao.selectList("getTournamentPlayerStatisticRankingById", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<BasketBallPlayerMatchResult> getBasketBallTournamentPlayerStatisticRankingById(Long tournamentId){
		return (List<BasketBallPlayerMatchResult>) mybatisBaseDao.selectList("getBasketBallTournamentPlayerStatisticRankingById", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<TeamMatchResult> getTournamentTeamStatisticRankingById(Long tournamentId){
		return (List<TeamMatchResult>) mybatisBaseDao.selectList("getTournamentTeamStatisticRankingById", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<BasketBallTeamMatchResult> getBasketBallTournamentTeamStatisticRankingById(Long tournamentId){
		return (List<BasketBallTeamMatchResult>) mybatisBaseDao.selectList("getBasketBallTournamentTeamStatisticRankingById", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<PlayerMatchResult> getPlayerTournamentTotalStats(Long tournamentId){
		return (List<PlayerMatchResult>) mybatisBaseDao.selectList("getPlayerTournamentTotalStats", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<BasketBallPlayerMatchResult> getBasketBallPlayerTournamentTotalStats(Long tournamentId){
		return (List<BasketBallPlayerMatchResult>) mybatisBaseDao.selectList("getBasketBallPlayerTournamentTotalStats", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<TeamMatchResult> getTeamTournamentStats(Long tournamentId, Long teamId){
		TournamentTeamBaseInfo ttbi =new TournamentTeamBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setTeamId(teamId);
		return (List<TeamMatchResult>) mybatisBaseDao.selectList("getTeamTournamentStats", ttbi);
	}
	
	@SuppressWarnings("unchecked")
	public List<BasketBallTeamMatchResult> getBasketBallTeamTournamentStats(Long tournamentId, Long teamId){
		TournamentTeamBaseInfo ttbi =new TournamentTeamBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setTeamId(teamId);
		return (List<BasketBallTeamMatchResult>) mybatisBaseDao.selectList("getBasketBallTeamTournamentStats", ttbi);
	}
	
	@SuppressWarnings("unchecked")
	public List<PlayerMatchResult> getPlayerAllTournamentMatchResultList(Long tournamentId, Long playerId){
		TournamentTeamBaseInfo ttbi =new TournamentTeamBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setCaptainPlayerId(playerId);
		return (List<PlayerMatchResult>) mybatisBaseDao.selectList("getPlayerAllTournamentMatchResultList", ttbi);
	}
	
	@SuppressWarnings("unchecked")
	public List<BasketBallPlayerMatchResult> getBasketBallPlayerAllTournamentMatchResultList(Long tournamentId, Long playerId){
		TournamentTeamBaseInfo ttbi =new TournamentTeamBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setCaptainPlayerId(playerId);
		return (List<BasketBallPlayerMatchResult>) mybatisBaseDao.selectList("getBasketBallPlayerAllTournamentMatchResultList", ttbi);
	}
	
	public TournamentPlayerBaseInfo getTournamentPlayerBaseInfo(Long tournamentId, Long playerId){
		TournamentPlayerBaseInfo ttbi =new TournamentPlayerBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setPlayerId(playerId);
		return (TournamentPlayerBaseInfo) mybatisBaseDao.selectOne("getTournamentPlayerBaseInfo", ttbi);
	}
	
	public boolean checkPlayerisTournamentRegisteredPlayer(Long tournamentId, Long playerId){
		TournamentPlayerBaseInfo ttbi =new TournamentPlayerBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setPlayerId(playerId);
		Integer result = (Integer) mybatisBaseDao.selectOne("checkPlayerisTournamentRegisteredPlayer", ttbi);
		if(result >= 1){
			return true;
		}else{
			return false;
		}
	}
	
	public StatsContent getTournamentStatsContent(Long tournamentId){
		return (StatsContent) mybatisBaseDao.selectOne("getTournamentStatsContent", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<BasketBallTeamMatchResult> getTournamentMatchWinLoseList(Long tournamentId){
		return (List<BasketBallTeamMatchResult>) mybatisBaseDao.selectList("getTournamentMatchWinLoseList", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<TeamMatchResult> getTournamentMatchTeamInfo(Long tournamentMatchId){
		return (List<TeamMatchResult>) mybatisBaseDao.selectList("getTournamentMatchTeamInfo", tournamentMatchId);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TeamMatchResult> getTournamentMatchTeamResultInfo(Long tournamentMatchId){
		return (List<TeamMatchResult>) mybatisBaseDao.selectList("getTournamentMatchTeamResultInfo", tournamentMatchId);
	}
		
	@SuppressWarnings("unchecked")
	public List<BasketBallTeamMatchResult> getBasketBallTournamentMatchTeamInfo(Long tournamentMatchId){
		return (List<BasketBallTeamMatchResult>) mybatisBaseDao.selectList("getBasketBallTournamentMatchTeamInfo", tournamentMatchId);
	}
	
	@SuppressWarnings("unchecked")
	public List<PlayerMatchResult> getTournamentMatchPlayerInfo(Long tournamentMatchId){
		return (List<PlayerMatchResult>) mybatisBaseDao.selectList("getTournamentMatchPlayerInfo", tournamentMatchId);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<BasketBallPlayerMatchResult> getBasketBallTournamentMatchPlayerInfo(Long tournamentMatchId){
		return (List<BasketBallPlayerMatchResult>) mybatisBaseDao.selectList("getBasketBallTournamentMatchPlayerInfo", tournamentMatchId);
	}
	
	public Video getTournamentMatchVideoInfo(Long tournamentMatchId){
		return (Video) mybatisBaseDao.selectOne("getTournamentMatchVideoInfo", tournamentMatchId);
	}
	
	public Video getVideoInfoById(Long videoId){
		return (Video) mybatisBaseDao.selectOne("getVideoInfoById", videoId);
	}
	
	public Moment getMomentInfoById(Long momentId){
		return (Moment) mybatisBaseDao.selectOne("getMomentInfoById", momentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Video> getVideoInfoByTournamentId(Long tournament_id){
		return (List<Video>) mybatisBaseDao.selectList("getVideoInfoByTournamentId", tournament_id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Video> getTournamentVideoList(Long tournamentId){		
		return (List<Video>) mybatisBaseDao.selectList("getTournamentMatchVideoList", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Video> getTeamTournamentVideoList(Long teamId, Long tournamentId){	
		TournamentTeamBaseInfo ttbi =new TournamentTeamBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setTeamId(teamId);
		return (List<Video>) mybatisBaseDao.selectList("getTeamTournamentVideoList", ttbi);
	}
	
	@SuppressWarnings("unchecked")
	public List<Video> getMatchVideoListWithoutThumbnail(){		
		return (List<Video>) mybatisBaseDao.selectList("getMatchVideoListWithoutThumbnail", null);
	}
	
	@SuppressWarnings("unchecked")
	public List<TournamentMatch> getTournamentMatchListByTournamentId(Long tournamentId) {
		return (List<TournamentMatch>) mybatisBaseDao.selectList("getTournamentMatchListByTournamentId", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<TournamentMatch> getTournamentMatchListByMatchScheduleId(Long tournamentId, Long matchScheduleId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tournamentId", tournamentId);
		param.put("matchScheduleId", matchScheduleId);
		return (List<TournamentMatch>) mybatisBaseDao.selectList("getTournamentMatchListByMatchScheduleId", param);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public int updateBatchVideoThumbnails(List<Video> videos){
		return  mybatisBaseDao.update("updateBatchVideoThumbnails", videos);
	}
	
	public int updateVideoThumbnail(Video video){
		return  mybatisBaseDao.update("updateVideoThumbnail", video);
	}
	
	private String parseVideoId(String url) {
		if (url.indexOf(YoukuClient.PLAYER_VIDEO_URL) > -1) {
			return YoukuClient.parsePlayerVideoId(url);
		} else if (url.indexOf(YoukuClient.COMMON_VIDEO_URL) > -1) {
			return YoukuClient.parseCommonVideoId(url);
		}
		return null;
	}
	
	public int updateVideoThumbnail() {
		int updateCount = 0;
		List<Video> videoList = getMatchVideoListWithoutThumbnail();
		List<String> viedoIdList = new ArrayList<String>();
		List<Long> idList = new ArrayList<>();
		
		for (Video video : videoList) {
			String url = video.getUrl();
			String videoId = parseVideoId(url);
			if (StringUtil.isNotBlank(videoId)) {
				viedoIdList.add(videoId);
				idList.add(video.getId());
			}			
		}
		
		if (viedoIdList.size() > 0) {
			List<String> thumbnails = YoukuClient.batchVideoThumbnails(viedoIdList);
			
			for(int i=0; i<thumbnails.size(); i++) {
				Video video = new Video();
				video.setId(idList.get(i));
				video.setThumbnail(thumbnails.get(i));
				System.out.println("id:" + video.getId());
				System.out.println("thumbnail:" + video.getThumbnail());
				updateVideoThumbnail(video);
				updateCount++;
			}
		}
		
		return updateCount;
	}
	
	@SuppressWarnings("unchecked")
	public List<TournamentMatch> getTeamTournamentCalendar(Long tournamentId,Long teamId){
		TournamentTeamBaseInfo ttbi =new TournamentTeamBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setTeamId(teamId);
		return (List<TournamentMatch>) mybatisBaseDao.selectList("getTeamTournamentCalendar", ttbi);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TournamentMatch> getBasketBallTeamTournamentCalendar(Long tournamentId,Long teamId){
		TournamentTeamBaseInfo ttbi =new TournamentTeamBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setTeamId(teamId);
		return (List<TournamentMatch>) mybatisBaseDao.selectList("getBasketBallTeamTournamentCalendar", ttbi);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TournamentMatch> getTeamTournamentMatchResults(Long tournamentId,Long teamId){
		TournamentTeamBaseInfo ttbi =new TournamentTeamBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setTeamId(teamId);
		return (List<TournamentMatch>) mybatisBaseDao.selectList("getTeamTournamentMatchResults", ttbi);
	}
	
	@SuppressWarnings("unchecked")
	public List<TournamentTeamPlayerInfo> getTeamTournamentPlayerInfos(Long tournamentId,Long teamId){
		TournamentTeamBaseInfo ttbi =new TournamentTeamBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setTeamId(teamId);
		return (List<TournamentTeamPlayerInfo>) mybatisBaseDao.selectList("getTeamTournamentPlayerInfos", ttbi);
	}
	
	@SuppressWarnings("unchecked")
	public List<TournamentTeamPlayerInfo> getBasketballTeamPlayerInfos(Long teamId){
		return (List<TournamentTeamPlayerInfo>) mybatisBaseDao.selectList("getBasketballTeamPlayerInfos", teamId);
	}
	
//	@SuppressWarnings("unchecked")
//	public List<TimeLine> getTournamentAllTimeLines(Long tournamentId){
//		return (List<TimeLine>) mybatisBaseDao.selectList("getTournamentAllTimeLines", tournamentId);
//	}
	
	@SuppressWarnings("unchecked")
	public List<TimeLine> getTournamentPagingTimeLines(PagingInfo pagingInfo){
		if(pagingInfo.getPageSize()>0 && pagingInfo.getPageIndex()>=0){
			//sql 使用Limit 所以这里的 pageIndex 需要转化为 limit 对应的 起始位开始取
			pagingInfo.setPageIndex(pagingInfo.getPageIndex() * pagingInfo.getPageSize());
			return (List<TimeLine>) mybatisBaseDao.selectList("getTournamentPagingTimeLines", pagingInfo);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TimeLine> getPlayerTournamentPagingTimeLines(PagingInfo pagingInfo){
		//sql 使用Limit 所以这里的 pageIndex 需要转化为 limit 对应的 起始位开始取
		pagingInfo.setPageIndex(pagingInfo.getPageIndex() * pagingInfo.getPageSize());
		return (List<TimeLine>) mybatisBaseDao.selectList("getPlayerTournamentPagingTimeLines", pagingInfo);
	}
	
	public TournamentTeamBaseInfo getTournamentTeamBaseInfo(Long tournamentId,Long teamId){
		TournamentTeamBaseInfo ttbi =new TournamentTeamBaseInfo();
		ttbi.setTournamentId(tournamentId);
		ttbi.setTeamId(teamId);
		return (TournamentTeamBaseInfo) mybatisBaseDao.selectOne("getTournamentTeamBaseInfo", ttbi);
	}
	
	public TournamentTeamBaseInfo getBasketballTeamBaseInfo(Long teamId){
		return (TournamentTeamBaseInfo) mybatisBaseDao.selectOne("getBasketballTeamBaseInfo", teamId);
	}
	
	@SuppressWarnings("unchecked")
	public List<TournamentTeamPlayerInfo> getTeamPlayerInfos(Long teamId){
		return (List<TournamentTeamPlayerInfo>) mybatisBaseDao.selectList("getTeamPlayerInfos", teamId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public boolean saveTournamentMatchDataStatistics(TournamentMatchDataStatisticsRequest request){
		List<PlayerMatchResult> playerMatchResults = request.getImportPlayerDataList();
		List<TeamMatchResult> teamMatchResults = request.getImportTeamDataList();
		
		if(playerMatchResults.size() >0 && teamMatchResults.size()>0){
			mybatisBaseDao.insert("saveTournamentMatchPlayerStatistics", playerMatchResults);
			mybatisBaseDao.insert("saveTournamentMatchTeamStatistics", teamMatchResults);
			return true;
		}else{
			return false;
		}		
	}
	
	public int saveTournament(Tournament tournament) {	
		if (tournament.getId() == null) {
			if (tournament.getStatus() == null) {
				tournament.setStatus("0");
			}
			
			if (tournament.getArenaId() == null) {
				tournament.setArenaId(-1L);
			}
			
			if (tournament.getType() == null) {
				tournament.setType(TournamentType.LEAGUE);
			}
			
			return mybatisBaseDao.insert("insertTournament", tournament);
		} else {
			return mybatisBaseDao.update("updateTournament", tournament);
		}
		
	}
	
	public Long createTeam(Team team) {
		mybatisBaseDao.insert("insertTeam", team);
		return team.getId();
	}
	
	public int createTournamentTeam(Team team) {
		return mybatisBaseDao.insert("insertTournamentTeam", team);
	}
	
	/**
	 * 从参赛列表中，删除球队
	 * 
	 * 1. 权限检查，只有联赛的管理员才能删除
	 * 2. 开赛后可以删除
	 * 
	 * @param team
	 * @return
	 */
	public int deleteTournamentTeamById(Long tournamentId, List<Long> teamIdList) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tournamentId", tournamentId);
		param.put("teamIdList", teamIdList);
		return mybatisBaseDao.delete("deleteTournamentTeamById", param);
	}
	
	/**
	 * 获取赛事相关的所有赛程信息
	 * @param tournamentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TournamentMatchSchedule> findMatchScheduleByTournamentId(Long tournamentId) {
		return (List<TournamentMatchSchedule>) mybatisBaseDao.selectList(
				"selectMatchScheduleByTournamentId", tournamentId);
	}
	
	/**
	 * 创建赛程
	 * 
	 * 1. 创建赛程设置
	 * 2. 根据设置生成具体的赛程信息
	 * 
	 * @param matchSchedule
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void createMatchScedule(TournamentMatchSchedule matchSchedule) {
		
		List<ScheduleMatchItem> scheduleMatchList = new ArrayList<ScheduleMatchItem>();
		
		mybatisBaseDao.insert("insertTournamentMatchSchdule", matchSchedule);
		
		Long matchScheduleId = matchSchedule.getId();
		
		
		List<TournamentTeamBaseInfo> teamList = getTournamentTeamsInfoById(matchSchedule.getTournamentId());
		List<Long> teamIdList = new ArrayList<Long>();
		
		if (!teamList.isEmpty() && teamList.size() > 0) {
			for(TournamentTeamBaseInfo team : teamList) {
				teamIdList.add(team.getTeamId());
			}
		}
		
		// 联赛
		if ("league".equals(matchSchedule.getMatchScheduleType())) {
			ScheduleStrategy scheduleStrategy = "single".equals(matchSchedule
					.getLeagueSetting()) ? new SingleLeagueStrgategy()
					: new DoubleLeagueStrategy();
			scheduleMatchList = buildSchduleMatchList(teamList, teamIdList, scheduleStrategy);
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tournamentId", matchSchedule.getTournamentId());
		param.put("matchScheduleId", matchScheduleId);
		param.put("scheduleMatchList", scheduleMatchList);
		
		mybatisBaseDao.insert("insertTournamentMatchForSchedule", param);
	}

	private List<ScheduleMatchItem> buildSchduleMatchList(List<TournamentTeamBaseInfo> teamList,
			List<Long> teamIdList, ScheduleStrategy scheduleStrategy) {
		ScheduleManager scheduleManager = new ScheduleManager(scheduleStrategy);
		List<ScheduleMatchItem> scheduleMatchList = scheduleManager.create(teamIdList);
		
		if (!scheduleMatchList.isEmpty() && scheduleMatchList.size() > 0) {
			for (ScheduleMatchItem item : scheduleMatchList) {
				TournamentTeamBaseInfo hostTeam = getTournamentTeamBaseInfoByTeamId(item.getHostTeamId(), teamList);
				TournamentTeamBaseInfo guestTeam = getTournamentTeamBaseInfoByTeamId(item.getGuestTeamId(), teamList);
				
				item.setHostTeam(hostTeam);
				item.setGestTeam(guestTeam);
			}
		}
		
		return scheduleMatchList;
	}
	
	private TournamentTeamBaseInfo getTournamentTeamBaseInfoByTeamId(
			Long teamId, List<TournamentTeamBaseInfo> teamList) {
		TournamentTeamBaseInfo team = new TournamentTeamBaseInfo();
		for (TournamentTeamBaseInfo item : teamList) {
			if (teamId.equals(item.getTeamId())) {
				team = item;
				break;
			}
		}
		return team;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public boolean saveTournamentMatchPlayerDataStatistics(TournamentMatchDataStatisticsRequest request){
		List<PlayerMatchResult> playerMatchResults = request.getImportPlayerDataList();
		Long matchId = request.getMatchId();
//		List<TeamMatchResult> teamMatchResults = request.getImportTeamDataList();
		
		
		
		if(ObjectUtil.isNotNull(matchId) && playerMatchResults.size() >0){
			mybatisBaseDao.delete("deletePlayerMatchResultByMatchId", matchId);
			mybatisBaseDao.insert("saveTournamentMatchPlayerStatistics", playerMatchResults);
			//mybatisBaseDao.insert("saveTournamentMatchTeamStatistics", teamMatchResults);
			return true;
		}else{
			return false;
		}		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public boolean saveMatchTeamResult(TournamentMatchDataStatisticsRequest request){
		List<TeamMatchResult> teamMatchResults = request.getImportTeamDataList();
		if(mybatisBaseDao.insert("saveMatchTeamResult", teamMatchResults)>0){
			return true;
		}else{
			return false;
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public void removeMatchesResultByIds(TournamentMatchDataStatisticsRequest request){
		List<Long> matchIdsList = request.getMatchIdsList();
		mybatisBaseDao.delete("removeMatchesResultByIds", matchIdsList);
	}
		
	public int insertVideo(Video video) {
		Date date = DateUtil.parseLongDate(video.getFullDisplayDate());
		video.setDate(date);
		int count =   mybatisBaseDao.insert("insertVideo", video);
		if (count > 0 && video.getId() != null && StringUtils.isEmpty(video.getThumbnail())) {
			String thumbnailUrl = YoukuClient.getVideoThumbnail(video.getUrl());
			video.setThumbnail(thumbnailUrl);
			if (!StringUtils.isEmpty(thumbnailUrl)) {
				updateVideoThumbnail(video);
			}			
		}
		return count;
	}
	
	public int updateVideo(Video video) {
		Date date = DateUtil.parseLongDate(video.getFullDisplayDate());
		video.setDate(date);
		return  mybatisBaseDao.update("updateVideoInfo", video);
	}
	
	public int updateTournamentMatchById(TournamentMatch tournamentMatch) {
		return mybatisBaseDao.update("updateTournamentMatchById", tournamentMatch);
	}
	
	/**
	 * Insert or update match score
	 * @param list
	 * @param tournamentMatchId
	 */
	public void saveMatchResultScore(List<TeamMatchResult> list, Long tournamentMatchId) {
		Integer count = selectMatchResultByMatchId(tournamentMatchId);
		if (count >= 1) { // already inserted
			updateMatchTeamResultOneTeam(list);
		} else {
			insertMatchTeamResultTwoTeams(list);
		}
	}
	
	public Integer selectMatchResultByMatchId(Long tournamentMatchId) {
		return (Integer)mybatisBaseDao.selectOne("selectMatchResultByMatchId", tournamentMatchId);
	}
	
	public int insertMatchTeamResultTwoTeams(List<TeamMatchResult> list) {
		return mybatisBaseDao.insert("insertMatchTeamResultTwoTeams", list);
	}
	
	public void updateMatchTeamResultOneTeam(List<TeamMatchResult> list) {
		for (TeamMatchResult teamMatchResult : list) {
			mybatisBaseDao.update("updateMatchTeamResultOneTeam", teamMatchResult);
		}
	}
	
	public static void main(String[] args) {
		Video video = new Video();
		video.setTournamentId(1L);
		video.setTournamentMatchId(1L);
		video.setName("test");
		String date = "2016-03-21 19:30:00";
		video.setDisplayDate(date);
		video.setUrl("http://v.qq.com/x/page/z0189ulrjqj.html");
		video.setThumbnail("https://r1.ykimg.com/0542040856FCCA176A0A4504508AA9B3");		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
	public boolean saveBasketBallTournamentMatchDataStatistics(TournamentMatchDataStatisticsRequest request){
		Long tournamentId = request.getTournamentId();
		List<BasketBallMatchResult> basketBallMatchResults = request.getImportBasketBallMatchResultList();
		List<TournamentTeamBaseInfo> tournamentTeamList= getTournamentTeamsInfoById(tournamentId);
		List<TournamentPlayerBaseInfo> tournamentPlayerList = getTournamentPlayersInfoByTournamentId(tournamentId);
		
		List<BasketBallPlayerMatchResult> allPlayerMatchResultList = Lists.newArrayList();
		List<BasketBallTeamMatchResult> allTeamMatchResultList = Lists.newArrayList();
		
		//data prepare
		for(BasketBallMatchResult bbm : basketBallMatchResults){
			
			TournamentMatch tm =new TournamentMatch();
			tm.setTournamentId(tournamentId);
			tm.setHostTeamName(bbm.getTeamAName());
			//tm.setGuestTeamName(bbm.getTeamAName());
			
			if(bbm.getStrMatchTime().length()==16 ){ //"2017-04-22 17:30"
				tm.setTime(DateUtil.parseLongDateNoSecond(bbm.getStrMatchTime()));
			}else{//"2017-04-22 17:30:00"
				tm.setTime(DateUtil.parseLongDate(bbm.getStrMatchTime()));
			}
			Long tournamentMatchId = (Long) mybatisBaseDao.selectOne("getTournamentMatchIdByTimeAndTeamName",tm);
			bbm.setTournamentMatchId(tournamentMatchId);
			
			for(TournamentTeamBaseInfo ttb: tournamentTeamList){
				if(ttb.getTeamName().trim().equalsIgnoreCase(bbm.getTeamAName().trim())){
					bbm.setTeamAId(ttb.getTeamId());
					continue;
				}
				if(ttb.getTeamName().trim().equalsIgnoreCase(bbm.getTeamBName().trim())){
					bbm.setTeamBId(ttb.getTeamId());
				}
			}
			
			for(BasketBallTeamMatchResult bbt: bbm.getBbTeamMatchResultList()){
				bbt.setTournamentMatchId(tournamentMatchId);
				if(bbt.getTeamName().equalsIgnoreCase(bbm.getTeamAName())){
					bbt.setTeamId(bbm.getTeamAId());
				}
				if(bbt.getTeamName().equalsIgnoreCase(bbm.getTeamBName())){
					bbt.setTeamId(bbm.getTeamBId());
				}
			}
			
			for(BasketBallPlayerMatchResult bbp: bbm.getBbPlayerMatchResultList()){
				bbp.setTournamentMatchId(tournamentMatchId);
				if(bbp.getTeamName().equalsIgnoreCase(bbm.getTeamAName())){
					bbp.setTeamId(bbm.getTeamAId());
				}
				if(bbp.getTeamName().equalsIgnoreCase(bbm.getTeamBName())){
					bbp.setTeamId(bbm.getTeamBId());
				}
				
				for(TournamentPlayerBaseInfo player : tournamentPlayerList){
					if(player.getPlayerNumber() == Integer.parseInt(bbp.getPlayerNumber()) && player.getTeamId().intValue() == bbp.getTeamId().intValue()){
						bbp.setPlayerId(player.getPlayerId());
					}
				}
			}
		}
		
		//全部取出来 拉平放到2个List中 一次存入
		for(BasketBallMatchResult bbm : basketBallMatchResults){
			allPlayerMatchResultList.addAll(bbm.getBbPlayerMatchResultList());
			allTeamMatchResultList.addAll(bbm.getBbTeamMatchResultList());
		}
		
		
		if(basketBallMatchResults.size() >0){
			mybatisBaseDao.insert("saveBasketBallTournamentMatchPlayerStatistics", allPlayerMatchResultList);
			mybatisBaseDao.insert("saveBasketBallTournamentMatchTeamStatistics", allTeamMatchResultList);
			return true;
		}else{
			return false;
		}
	}
	
	//自动推送任务之 正式比赛赛前提醒 （2个小时前）
	@SuppressWarnings("unchecked")
	public List<TournamentRemindInfo> getTournamentRemindInfoListB4Match(){
		return (List<TournamentRemindInfo>) mybatisBaseDao.selectList("getTournamentRemindInfoListB4Match",null);
	}
	
	//自动推送任务之 正式比赛赛后 数据和视频排名查看提醒 （48小时后）
	@SuppressWarnings("unchecked")
	public List<TournamentRemindInfo> getTournamentRemindInfoListAfterMatch(){
		return (List<TournamentRemindInfo>) mybatisBaseDao.selectList("getTournamentRemindInfoListAfterMatch",null);
	}

}
