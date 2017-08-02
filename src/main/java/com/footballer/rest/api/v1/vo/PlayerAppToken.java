package com.footballer.rest.api.v1.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.footballer.rest.api.v1.vo.base.GenericVo;
import com.footballer.rest.api.v1.vo.enumType.AppTokenType;

@Entity
@Table(name="player_app_tokens")
public class PlayerAppToken extends GenericVo {
	
	private Long playerId;
	private String token;
	private AppTokenType type;
	
	@Column(name = "player_id", nullable = false)
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	
	@Column(name = "token", nullable = false)
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	@Enumerated(value=EnumType.STRING)
	@Column(name = "type", nullable = false)
	public AppTokenType getType() {
		return type;
	}
	public void setType(AppTokenType type) {
		this.type = type;
	}
}
