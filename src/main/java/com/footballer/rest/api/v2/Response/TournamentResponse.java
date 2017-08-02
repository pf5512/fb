package com.footballer.rest.api.v2.Response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v2.domain.TimeLine;
import com.footballer.rest.api.v2.domain.UserCommentsInfo;
import com.footballer.rest.api.v2.domain.UserShareInfo;
import com.footballer.rest.api.v2.domain.PlayerSupport;
import com.footballer.rest.api.v2.domain.StatsContent;
import com.footballer.rest.api.v2.vo.BasketBallPlayerMatchResult;
import com.footballer.rest.api.v2.vo.BasketBallTeamMatchResult;
import com.footballer.rest.api.v2.vo.Moment;
import com.footballer.rest.api.v2.vo.PlayerMatchResult;
import com.footballer.rest.api.v2.vo.TeamMatchResult;
import com.footballer.rest.api.v2.vo.Tournament;
import com.footballer.rest.api.v2.vo.TournamentGourpTeamStanding;
import com.footballer.rest.api.v2.vo.TournamentMatch;
import com.footballer.rest.api.v2.vo.TournamentPlayerBaseInfo;
import com.footballer.rest.api.v2.vo.TournamentTeamBaseInfo;
import com.footballer.rest.api.v2.vo.TournamentTeamPlayerInfo;
import com.footballer.rest.api.v2.vo.Video;

@JsonInclude(Include.NON_NULL) 
public class TournamentResponse  extends JsonResponse {

	private Tournament tournamentBaseInfo;
	private List<Tournament> tournamentsList;
	private List<TournamentMatch> tournamentMatchList;
	private TournamentPlayerBaseInfo tournamentPlayerBaseInfo;
	private List<TournamentTeamBaseInfo> tournamentTeamBaseInfoList;
	private List<TournamentGourpTeamStanding> teamStandingList;
	private List<PlayerMatchResult> playerMatchResultList;
	private List<PlayerMatchResult> playerAllTournamentMatchResultList;
	private List<TeamMatchResult> teamMatchResultList;
	private List<TeamMatchResult> teamAllTournamentMatchResultList;
	private List<TournamentTeamPlayerInfo> tournamentTeamPlayerInfoList;
	private List<TournamentTeamPlayerInfo> hostTeamPlayerInfoList;
	private List<TournamentTeamPlayerInfo> guestTeamPlayerInfoList;
	private TournamentTeamBaseInfo tournamentTeamInfo;
	private Video videoInfo;
	private Moment momentInfo;
	private List<UserCommentsInfo> commentsList;
	private List<PlayerSupport> supportList;
	private List<UserShareInfo> shareRecordList;
	private List<BasketBallPlayerMatchResult> basketBallPlayerMatchResultList;
	private List<BasketBallTeamMatchResult> basketBallTeamMatchResultList;
	private List<BasketBallPlayerMatchResult> basketBallPlayerAllTournamentMatchResultList;
	private List<BasketBallTeamMatchResult> basketBallTeamAllTournamentMatchResultList;
	private List<TimeLine> tournamentTimeLineList;
	
	private StatsContent statsContent;
	
	private boolean isTournamentRegisteredPlayer;
	
	public List<TournamentMatch> getTournamentMatchList() {
		return tournamentMatchList;
	}

	public void setTournamentMatchList(List<TournamentMatch> tournamentMatchList) {
		this.tournamentMatchList = tournamentMatchList;
	}

	public List<TournamentTeamBaseInfo> getTournamentTeamBaseInfoList() {
		return tournamentTeamBaseInfoList;
	}

	public void setTournamentTeamBaseInfoList(List<TournamentTeamBaseInfo> tournamentTeamBaseInfoList) {
		this.tournamentTeamBaseInfoList = tournamentTeamBaseInfoList;
	}

	public List<TournamentGourpTeamStanding> getTeamStandingList() {
		return teamStandingList;
	}

	public void setTeamStandingList(List<TournamentGourpTeamStanding> teamStandingList) {
		this.teamStandingList = teamStandingList;
	}

	public List<PlayerMatchResult> getPlayerMatchResultList() {
		return playerMatchResultList;
	}

	public void setPlayerMatchResultList(List<PlayerMatchResult> playerMatchResultList) {
		this.playerMatchResultList = playerMatchResultList;
	}

	public List<TournamentTeamPlayerInfo> getTournamentTeamPlayerInfoList() {
		return tournamentTeamPlayerInfoList;
	}

	public void setTournamentTeamPlayerInfoList(List<TournamentTeamPlayerInfo> tournamentTeamPlayerInfoList) {
		this.tournamentTeamPlayerInfoList = tournamentTeamPlayerInfoList;
	}

	public List<TeamMatchResult> getTeamMatchResultList() {
		return teamMatchResultList;
	}

	public void setTeamMatchResultList(List<TeamMatchResult> teamMatchResultList) {
		this.teamMatchResultList = teamMatchResultList;
	}

	public List<TournamentTeamPlayerInfo> getHostTeamPlayerInfoList() {
		return hostTeamPlayerInfoList;
	}

	public void setHostTeamPlayerInfoList(List<TournamentTeamPlayerInfo> hostTeamPlayerInfoList) {
		this.hostTeamPlayerInfoList = hostTeamPlayerInfoList;
	}

	public List<TournamentTeamPlayerInfo> getGuestTeamPlayerInfoList() {
		return guestTeamPlayerInfoList;
	}

	public void setGuestTeamPlayerInfoList(List<TournamentTeamPlayerInfo> guestTeamPlayerInfoList) {
		this.guestTeamPlayerInfoList = guestTeamPlayerInfoList;
	}

	public List<Tournament> getTournamentsList() {
		return tournamentsList;
	}

	public void setTournamentsList(List<Tournament> tournamentsList) {
		this.tournamentsList = tournamentsList;
	}

	public TournamentTeamBaseInfo getTournamentTeamInfo() {
		return tournamentTeamInfo;
	}

	public void setTournamentTeamInfo(TournamentTeamBaseInfo tournamentTeamInfo) {
		this.tournamentTeamInfo = tournamentTeamInfo;
	}

	public Video getVideoInfo() {
		return videoInfo;
	}

	public void setVideoInfo(Video videoInfo) {
		this.videoInfo = videoInfo;
	}

	public List<UserCommentsInfo> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<UserCommentsInfo> commentsList) {
		this.commentsList = commentsList;
	}

	public List<PlayerSupport> getSupportList() {
		return supportList;
	}

	public void setSupportList(List<PlayerSupport> supportList) {
		this.supportList = supportList;
	}

	public List<TeamMatchResult> getTeamAllTournamentMatchResultList() {
		return teamAllTournamentMatchResultList;
	}

	public void setTeamAllTournamentMatchResultList(List<TeamMatchResult> teamAllTournamentMatchResultList) {
		this.teamAllTournamentMatchResultList = teamAllTournamentMatchResultList;
	}

	public List<PlayerMatchResult> getPlayerAllTournamentMatchResultList() {
		return playerAllTournamentMatchResultList;
	}

	public void setPlayerAllTournamentMatchResultList(List<PlayerMatchResult> playerAllTournamentMatchResultList) {
		this.playerAllTournamentMatchResultList = playerAllTournamentMatchResultList;
	}

	public TournamentPlayerBaseInfo getTournamentPlayerBaseInfo() {
		return tournamentPlayerBaseInfo;
	}

	public void setTournamentPlayerBaseInfo(TournamentPlayerBaseInfo tournamentPlayerBaseInfo) {
		this.tournamentPlayerBaseInfo = tournamentPlayerBaseInfo;
	}

	public List<UserShareInfo> getShareRecordList() {
		return shareRecordList;
	}

	public void setShareRecordList(List<UserShareInfo> shareRecordList) {
		this.shareRecordList = shareRecordList;
	}

	public Tournament getTournamentBaseInfo() {
		return tournamentBaseInfo;
	}

	public void setTournamentBaseInfo(Tournament tournamentBaseInfo) {
		this.tournamentBaseInfo = tournamentBaseInfo;
	}

	public List<BasketBallPlayerMatchResult> getBasketBallPlayerMatchResultList() {
		return basketBallPlayerMatchResultList;
	}

	public void setBasketBallPlayerMatchResultList(List<BasketBallPlayerMatchResult> basketBallPlayerMatchResultList) {
		this.basketBallPlayerMatchResultList = basketBallPlayerMatchResultList;
	}

	public List<BasketBallTeamMatchResult> getBasketBallTeamMatchResultList() {
		return basketBallTeamMatchResultList;
	}

	public void setBasketBallTeamMatchResultList(List<BasketBallTeamMatchResult> basketBallTeamMatchResultList) {
		this.basketBallTeamMatchResultList = basketBallTeamMatchResultList;
	}

	public List<BasketBallTeamMatchResult> getBasketBallTeamAllTournamentMatchResultList() {
		return basketBallTeamAllTournamentMatchResultList;
	}

	public void setBasketBallTeamAllTournamentMatchResultList(
			List<BasketBallTeamMatchResult> basketBallTeamAllTournamentMatchResultList) {
		this.basketBallTeamAllTournamentMatchResultList = basketBallTeamAllTournamentMatchResultList;
	}

	public List<BasketBallPlayerMatchResult> getBasketBallPlayerAllTournamentMatchResultList() {
		return basketBallPlayerAllTournamentMatchResultList;
	}

	public void setBasketBallPlayerAllTournamentMatchResultList(
			List<BasketBallPlayerMatchResult> basketBallPlayerAllTournamentMatchResultList) {
		this.basketBallPlayerAllTournamentMatchResultList = basketBallPlayerAllTournamentMatchResultList;
	}

	public boolean isTournamentRegisteredPlayer() {
		return isTournamentRegisteredPlayer;
	}

	public void setTournamentRegisteredPlayer(boolean isTournamentRegisteredPlayer) {
		this.isTournamentRegisteredPlayer = isTournamentRegisteredPlayer;
	}

	public Moment getMomentInfo() {
		return momentInfo;
	}

	public void setMomentInfo(Moment momentInfo) {
		this.momentInfo = momentInfo;
	}

	public List<TimeLine> getTournamentTimeLineList() {
		return tournamentTimeLineList;
	}

	public void setTournamentTimeLineList(List<TimeLine> tournamentTimeLineList) {
		this.tournamentTimeLineList = tournamentTimeLineList;
	}

	public StatsContent getStatsContent() {
		return statsContent;
	}

	public void setStatsContent(StatsContent statsContent) {
		this.statsContent = statsContent;
	}

}
