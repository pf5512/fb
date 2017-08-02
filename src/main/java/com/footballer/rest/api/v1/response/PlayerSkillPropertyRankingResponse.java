package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v1.domain.PlayerSkillPropertyRanking;
import com.footballer.rest.api.v1.domain.PlayerSkillPropertyTeamRanking;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;

public class PlayerSkillPropertyRankingResponse extends JsonResponse {

	private String playerSkillType;
	private List<PlayerSkillPropertyRanking> friendsRankingList;
	private List<PlayerSkillPropertyTeamRanking> teamMembersRankingList;
	
	public String getPlayerSkillType() {
		return playerSkillType;
	}
	public void setPlayerSkillType(String playerSkillType) {
		this.playerSkillType = playerSkillType;
	}
	public List<PlayerSkillPropertyRanking> getFriendsRankingList() {
		return friendsRankingList;
	}
	public void setFriendsRankingList(
			List<PlayerSkillPropertyRanking> friendsRankingList) {
		this.friendsRankingList = friendsRankingList;
	}
	public List<PlayerSkillPropertyTeamRanking> getTeamMembersRankingList() {
		return teamMembersRankingList;
	}
	public void setTeamMembersRankingList(
			List<PlayerSkillPropertyTeamRanking> teamMembersRankingList) {
		this.teamMembersRankingList = teamMembersRankingList;
	}
	
	
}
