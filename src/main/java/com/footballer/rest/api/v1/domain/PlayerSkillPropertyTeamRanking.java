package com.footballer.rest.api.v1.domain;

import java.util.List;

public class PlayerSkillPropertyTeamRanking  {

	private Long teamId;
	private String teamName;
	private String teamIconUrl;
	private List<PlayerSkillPropertyRanking> teamMembersRankingList;
	
	
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamIconUrl() {
		return teamIconUrl;
	}
	public void setTeamIconUrl(String teamIconUrl) {
		this.teamIconUrl = teamIconUrl;
	}
	public List<PlayerSkillPropertyRanking> getTeamMembersRankingList() {
		return teamMembersRankingList;
	}
	public void setTeamMembersRankingList(
			List<PlayerSkillPropertyRanking> teamMembersRankingList) {
		this.teamMembersRankingList = teamMembersRankingList;
	}
}
