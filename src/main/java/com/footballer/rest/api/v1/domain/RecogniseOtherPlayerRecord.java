package com.footballer.rest.api.v1.domain;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.footballer.rest.api.v1.adapter.LongDateAdapter;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;

public class RecogniseOtherPlayerRecord {

	private PlayerSkills playerSkillType;
	private PlayerBaseInfo player;
	private Date	 recogniseTime;
	
	public PlayerSkills getPlayerSkillType() {
		return playerSkillType;
	}
	public void setPlayerSkillType(PlayerSkills playerSkillType) {
		this.playerSkillType = playerSkillType;
	}
	public PlayerBaseInfo getPlayer() {
		return player;
	}
	public void setPlayer(PlayerBaseInfo player) {
		this.player = player;
	}
	
	@XmlJavaTypeAdapter(LongDateAdapter.class)
	public Date getRecogniseTime() {
		return recogniseTime;
	}
	public void setRecogniseTime(Date recogniseTime) {
		this.recogniseTime = recogniseTime;
	}
	

}
