package com.footballer.rest.api.v1.request;

import java.util.List;

import com.footballer.rest.api.v1.domain.FootballPlayerBalance;
import com.footballer.rest.api.v1.domain.FootballPlayerTeamActivity;

public class ConfirmEventRequest {
	private Long eventId;
	private List<FootballPlayerBalance> balances;
	private List<FootballPlayerTeamActivity> activities;
	
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public List<FootballPlayerBalance> getBalances() {
		return balances;
	}
	public void setBalances(List<FootballPlayerBalance> balances) {
		this.balances = balances;
	}
	public List<FootballPlayerTeamActivity> getActivities() {
		return activities;
	}
	public void setActivities(List<FootballPlayerTeamActivity> activities) {
		this.activities = activities;
	}
	
}
