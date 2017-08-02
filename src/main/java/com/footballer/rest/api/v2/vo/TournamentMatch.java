package com.footballer.rest.api.v2.vo;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.footballer.util.DateUtil;

public class TournamentMatch {

	private Long id;
	private Long tournamentId;
	private Date time;
	private String displayTime;
	private String fieldName;
	private String typeName;
	private String hostTeamLabel;
	private Long hostTeamId;
	private String hostTeamName;
	private String hostTeamLogo;
	private String guestTeamLabel;
	private Long guestTeamId;
	private String guestTeamName;
	private String guestTeamLogo;
	private String result;
	private String videoUrl;
	private String name;
	private String displayName;
	
	private Long matchScheduleId;
	private Integer round;
	
	public String getDisplayName() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(name);
		if (!StringUtils.isEmpty(hostTeamLabel)) {
			buffer.append(" ");
			buffer.append(hostTeamLabel);
		}
		if (!StringUtils.isEmpty(guestTeamLabel)) {
			buffer.append(" vs ");
			buffer.append(guestTeamLabel);
		}
		return buffer.toString();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getHostTeamLabel() {
		return hostTeamLabel;
	}
	public void setHostTeamLabel(String hostTeamLabel) {
		this.hostTeamLabel = hostTeamLabel;
	}
	public Long getHostTeamId() {
		return hostTeamId;
	}
	public void setHostTeamId(Long hostTeamId) {
		this.hostTeamId = hostTeamId;
	}
	public String getHostTeamName() {
		return hostTeamName;
	}
	public void setHostTeamName(String hostTeamName) {
		this.hostTeamName = hostTeamName;
	}
	public String getHostTeamLogo() {
		return hostTeamLogo;
	}
	public void setHostTeamLogo(String hostTeamLogo) {
		this.hostTeamLogo = hostTeamLogo;
	}
	public String getGuestTeamLabel() {
		return guestTeamLabel;
	}
	public void setGuestTeamLabel(String guestTeamLabel) {
		this.guestTeamLabel = guestTeamLabel;
	}
	public Long getGuestTeamId() {
		return guestTeamId;
	}
	public void setGuestTeamId(Long guestTeamId) {
		this.guestTeamId = guestTeamId;
	}
	public String getGuestTeamName() {
		return guestTeamName;
	}
	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}
	public String getGuestTeamLogo() {
		return guestTeamLogo;
	}
	public void setGuestTeamLogo(String guestTeamLogo) {
		this.guestTeamLogo = guestTeamLogo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getDisplayTime() {
		return DateUtil.formatLongDate(time);
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public Long getTournamentId() {
		return tournamentId;
	}
	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}
	public Long getMatchScheduleId() {
		return matchScheduleId;
	}
	public void setMatchScheduleId(Long matchScheduleId) {
		this.matchScheduleId = matchScheduleId;
	}
	public Integer getRound() {
		return round;
	}
	public void setRound(Integer round) {
		this.round = round;
	}
	
}
