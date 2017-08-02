package com.footballer.rest.api.v2.vo;

import java.util.Date;

import com.footballer.rest.api.v2.vo.enumType.VideoType;
import com.footballer.util.DateUtil;

public class Moment {

	private Long id;
	private Long playerId;
	private String playerName;
	private String playerLogo;
	private Long tournamentId;
	private Long tournamentMatchId;
	private String content;
	private String type;
	private String url;
	private Date date;
	private String displayLongDate;
	
	private Integer commentCount;
	private Integer supportCount;
	private Integer shareCount;
	private Integer watchCount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}
	public Long getTournamentMatchId() {
		return tournamentMatchId;
	}
	public void setTournamentMatchId(Long tournamentMatchId) {
		this.tournamentMatchId = tournamentMatchId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Integer getSupportCount() {
		return supportCount;
	}
	public void setSupportCount(Integer supportCount) {
		this.supportCount = supportCount;
	}
	public Integer getShareCount() {
		return shareCount;
	}
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	public Integer getWatchCount() {
		return watchCount;
	}
	public void setWatchCount(Integer watchCount) {
		this.watchCount = watchCount;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getDisplayLongDate() {
		return displayLongDate;
	}
	public void setDisplayLongDate(String displayLongDate) {
		this.displayLongDate = displayLongDate;
	}
//	public Date getDate() {
//		return DateUtil.parseLongDate(displayLongDate);
//	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPlayerLogo() {
		return playerLogo;
	}
	public void setPlayerLogo(String playerLogo) {
		this.playerLogo = playerLogo;
	}
	
}
