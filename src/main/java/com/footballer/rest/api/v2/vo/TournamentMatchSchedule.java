package com.footballer.rest.api.v2.vo;

import com.footballer.rest.api.v2.vo.base.GenericVo;
import com.footballer.util.StringUtil;


public class TournamentMatchSchedule extends GenericVo{
	
	private Long tournamentId;
	private String matchScheduleType;
	private String leagueSetting;
	private String matchCupSetting;
	private String name;
	private String status;

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}

	public String getMatchScheduleType() {
		return matchScheduleType;
	}

	public void setMatchScheduleType(String matchScheduleType) {
		this.matchScheduleType = matchScheduleType;
	}

	public String getLeagueSetting() {
		return leagueSetting;
	}

	public void setLeagueSetting(String leagueSetting) {
		this.leagueSetting = leagueSetting;
	}

	public String getMatchCupSetting() {
		return matchCupSetting;
	}

	public void setMatchCupSetting(String matchCupSetting) {
		this.matchCupSetting = matchCupSetting;
	}

	public String getName() {
		String setting = StringUtil.isEmpty(leagueSetting) ? matchCupSetting
				: leagueSetting;
		return tournamentId + " - " + matchScheduleType + " - " + setting;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
