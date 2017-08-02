package com.footballer.rest.api.v1.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.footballer.rest.api.v1.vo.base.GenericVo;

@Entity
@Table(name = "player_friend")
public class PlayerFriend extends GenericVo {
	
	private Long playerId;
	private Long friendPlayerId;
	private String status;
	
	public PlayerFriend() {
		super();
	}
	
	@Column(name = "player_id", nullable = false)
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	
	@Column(name = "friend_player_id", nullable = false)
	public Long getFriendPlayerId() {
		return friendPlayerId;
	}

	public void setFriendPlayerId(Long friendPlayerId) {
		this.friendPlayerId = friendPlayerId;
	}
	
	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public enum PLAYER_STATUS {
		ACTIVE, DISABLE, INACTIVE, EXPIRE
	}


}
