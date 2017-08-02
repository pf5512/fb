package com.footballer.rest.api.v2.Response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v2.domain.AllArenaUserReservationStatistics;
import com.footballer.rest.api.v2.domain.ArenaMemberInfo;
import com.footballer.rest.api.v2.domain.FieldReservationDetail;
import com.footballer.rest.api.v2.result.ArenasFieldsReservationResult;
import com.footballer.rest.api.v2.result.AutomationJobFieldReservationResult;
import com.footballer.rest.api.v2.result.PlayerFieldReservationResult;

@JsonInclude(Include.NON_NULL) 
public class FieldReservationResponse  extends JsonResponse {
	
	private List<ArenasFieldsReservationResult> arenasFieldsReservation;
	private List<PlayerFieldReservationResult> unUsedPlayerFieldReservationList;
	private List<PlayerFieldReservationResult> expiredPlayerFieldReservationList;
	private List<PlayerFieldReservationResult> playerReservationList;
	private FieldReservationDetail fieldReservationDetail;
	private List<String> statisticsList;
	private List<String> currentWeeksDates;
	private List<ArenaMemberInfo> arenaUserReservationStatisticsList;
	
	private List<AutomationJobFieldReservationResult> automationReservationJobList;
	
	private AllArenaUserReservationStatistics allArenaUserReservationStatistics;
	
	public List<ArenasFieldsReservationResult> getArenasFieldsReservation() {
		return arenasFieldsReservation;
	}

	public void setArenasFieldsReservation(List<ArenasFieldsReservationResult> arenasFieldsReservation) {
		this.arenasFieldsReservation = arenasFieldsReservation;
	}

	public List<PlayerFieldReservationResult> getUnUsedPlayerFieldReservationList() {
		return unUsedPlayerFieldReservationList;
	}

	public void setUnUsedPlayerFieldReservationList(List<PlayerFieldReservationResult> unUsedPlayerFieldReservationList) {
		this.unUsedPlayerFieldReservationList = unUsedPlayerFieldReservationList;
	}

	public List<PlayerFieldReservationResult> getExpiredPlayerFieldReservationList() {
		return expiredPlayerFieldReservationList;
	}

	public void setExpiredPlayerFieldReservationList(List<PlayerFieldReservationResult> expiredPlayerFieldReservationList) {
		this.expiredPlayerFieldReservationList = expiredPlayerFieldReservationList;
	}

	public FieldReservationDetail getFieldReservationDetail() {
		return fieldReservationDetail;
	}

	public void setFieldReservationDetail(FieldReservationDetail fieldReservationDetail) {
		this.fieldReservationDetail = fieldReservationDetail;
	}

	public List<PlayerFieldReservationResult> getPlayerReservationList() {
		return playerReservationList;
	}

	public void setPlayerReservationList(
			List<PlayerFieldReservationResult> playerReservationList) {
		this.playerReservationList = playerReservationList;
	}

	public List<String> getStatisticsList() {
		return statisticsList;
	}

	public void setStatisticsList(List<String> statisticsList) {
		this.statisticsList = statisticsList;
	}

	public List<String> getCurrentWeeksDates() {
		return currentWeeksDates;
	}

	public void setCurrentWeeksDates(List<String> currentWeeksDates) {
		this.currentWeeksDates = currentWeeksDates;
	}

	public List<ArenaMemberInfo> getArenaUserReservationStatisticsList() {
		return arenaUserReservationStatisticsList;
	}

	public void setArenaUserReservationStatisticsList(List<ArenaMemberInfo> arenaUserReservationStatisticsList) {
		this.arenaUserReservationStatisticsList = arenaUserReservationStatisticsList;
	}

	public List<AutomationJobFieldReservationResult> getAutomationReservationJobList() {
		return automationReservationJobList;
	}

	public void setAutomationReservationJobList(List<AutomationJobFieldReservationResult> automationReservationJobList) {
		this.automationReservationJobList = automationReservationJobList;
	}

	public AllArenaUserReservationStatistics getAllArenaUserReservationStatistics() {
		return allArenaUserReservationStatistics;
	}

	public void setAllArenaUserReservationStatistics(AllArenaUserReservationStatistics allArenaUserReservationStatistics) {
		this.allArenaUserReservationStatistics = allArenaUserReservationStatistics;
	}

}
