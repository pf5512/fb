package com.footballer.rest.api.v1.domain;

import java.util.List;

import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;

public class PlayerRecognisedRecord {

	private PlayerSkills playerSkillType;
	private int recogniseTime;
	private List<PlayerBaseInfo> recognisedOfPlayerList;
	
	public PlayerSkills getPlayerSkillType() {
		return playerSkillType;
	}
	public void setPlayerSkillType(PlayerSkills playerSkillType) {
		this.playerSkillType = playerSkillType;
	}
	public int getRecogniseTime() {
		return recogniseTime;
	}
	public void setRecogniseTime(int recogniseTime) {
		this.recogniseTime = recogniseTime;
	}
	public List<PlayerBaseInfo> getRecognisedOfPlayerList() {
		return recognisedOfPlayerList;
	}
	public void setRecognisedOfPlayerList(
			List<PlayerBaseInfo> recognisedOfPlayerList) {
		this.recognisedOfPlayerList = recognisedOfPlayerList;
	}
}
