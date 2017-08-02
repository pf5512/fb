package com.footballer.rest.api.v2.domain;

import java.util.Date;

import com.footballer.util.DateUtil;

public class SoloWishInfo {
	
	private Long id;
	private Long arenaId;
	private Date date;
	private String displayDate;
	private String disPlayDateTime;
	private String week;
	private Long playerId;
	private String playerName;
	private String playerHeadIcon;
	private Long cellphone;
	private Integer useTimes;
	private Integer absentTimes;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPlayerHeadIcon() {
		return playerHeadIcon;
	}
	public void setPlayerHeadIcon(String playerHeadIcon) {
		this.playerHeadIcon = playerHeadIcon;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDisplayDate() {
		return DateUtil.formatShortDate(date);
	}
	public String getWeek() {
		return DateUtil.formatChinaWeekDate(date);
	}
	public String getDisPlayDateTime() {
		return DateUtil.formatNoSecondTime(date);
	}
	public Long getCellphone() {
		return cellphone;
	}
	public void setCellphone(Long cellphone) {
		this.cellphone = cellphone;
	}
	public Integer getUseTimes() {
		return useTimes;
	}
	public void setUseTimes(Integer useTimes) {
		this.useTimes = useTimes;
	}
	public Integer getAbsentTimes() {
		return absentTimes;
	}
	public void setAbsentTimes(Integer absentTimes) {
		this.absentTimes = absentTimes;
	}
	
}
