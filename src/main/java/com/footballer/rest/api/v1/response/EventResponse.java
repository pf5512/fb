package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v1.domain.FootballTeam;
import com.footballer.rest.api.v1.domain.PlayerSkillStatistics;
import com.footballer.rest.api.v1.domain.PlayerWithStatus;
import com.footballer.rest.api.v1.result.EventResult;
import com.footballer.rest.api.v1.result.PlayerActiviyWithChargeResult;
import com.footballer.rest.api.v1.vo.enumType.EventType;
import com.footballer.rest.api.v2.result.EventCommentsResult;
import com.footballer.rest.api.v2.vo.Dashboard;

public class EventResponse extends JsonResponse {
	
	private EventType eventType;
	private EventResult eventResult;
	private List<EventResult> eventList;
	private List<PlayerWithStatus> playerList;
	private FootballTeam hostTeam;
	private FootballTeam guestTeam;
	private List<PlayerSkillStatistics> playerSkillStatisticsList;
	private List<PlayerActiviyWithChargeResult> playerActivityWithChargeFeeList;
	private List<Dashboard> playerEventEvaluationList;
	private List<EventCommentsResult> ecList;
	
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public List<EventResult> getEventList() {
		return eventList;
	}
	public void setEventList(List<EventResult> eventList) {
		this.eventList = eventList;
	}
	public List<PlayerWithStatus> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(List<PlayerWithStatus> playerList) {
		this.playerList = playerList;
	}
	public EventResult getEventResult() {
		return eventResult;
	}
	public void setEventResult(EventResult eventResult) {
		this.eventResult = eventResult;
	}
	public FootballTeam getHostTeam() {
		return hostTeam;
	}
	public void setHostTeam(FootballTeam hostTeam) {
		this.hostTeam = hostTeam;
	}
	public FootballTeam getGuestTeam() {
		return guestTeam;
	}
	public void setGuestTeam(FootballTeam guestTeam) {
		this.guestTeam = guestTeam;
	}
	public List<PlayerSkillStatistics> getPlayerSkillStatisticsList() {
		return playerSkillStatisticsList;
	}
	public void setPlayerSkillStatisticsList(List<PlayerSkillStatistics> playerSkillStatisticsList) {
		this.playerSkillStatisticsList = playerSkillStatisticsList;
	}
	public List<EventCommentsResult> getEcList() {
		return ecList;
	}
	public void setEcList(List<EventCommentsResult> ecList) {
		this.ecList = ecList;
	}
	public List<PlayerActiviyWithChargeResult> getPlayerActivityWithChargeFeeList() {
		return playerActivityWithChargeFeeList;
	}
	public void setPlayerActivityWithChargeFeeList(List<PlayerActiviyWithChargeResult> playerActivityWithChargeFeeList) {
		this.playerActivityWithChargeFeeList = playerActivityWithChargeFeeList;
	}
	public List<Dashboard> getPlayerEventEvaluationList() {
		return playerEventEvaluationList;
	}
	public void setPlayerEventEvaluationList(List<Dashboard> playerEventEvaluationList) {
		this.playerEventEvaluationList = playerEventEvaluationList;
	}
	
}
