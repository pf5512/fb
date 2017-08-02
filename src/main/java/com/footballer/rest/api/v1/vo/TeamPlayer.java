package com.footballer.rest.api.v1.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.footballer.rest.api.v1.vo.base.GenericVo;
import com.footballer.rest.api.v1.vo.enumType.PlayerPosition;

@Entity
@Table(name = "team_player")
public class TeamPlayer extends GenericVo {
	
	private Long teamId;
	private Long playerId;
	private String status;
	
	private Integer playerNumber;
	private PlayerPosition position;
	private Integer attendTotal;
	private Integer lateTotal;
	private Integer absenceTotal;
	
	private String name;
	
	public TeamPlayer() {
		super();
	}
	
	@Column(name = "team_id", nullable = false)
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	@Column(name = "player_id", nullable = false)
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
	
	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public enum TEAM_PLAYER_STATUS {
		ACTIVE, DISABLE, INACTIVE, EXPIRE,LEAVE
	}
	
	@Column(name = "player_number", nullable = true)
	public Integer getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(Integer playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	@Enumerated(value=EnumType.STRING)
	@Column(name = "position", nullable = true)	
	public PlayerPosition getPosition() {
		return position;
	}

	public void setPosition(PlayerPosition position) {
		this.position = position;
	}
	
	@Column(name = "attend_total", nullable = true)
	public Integer getAttendTotal() {
		return attendTotal;
	}

	public void setAttendTotal(Integer attendTotal) {
		this.attendTotal = attendTotal;
	}
	
	@Column(name = "late_total", nullable = true)
	public Integer getLateTotal() {
		return lateTotal;
	}

	public void setLateTotal(Integer lateTotal) {
		this.lateTotal = lateTotal;
	}
	
	@Column(name = "absence_total", nullable = true)
	public Integer getAbsenceTotal() {
		return absenceTotal;
	}

	public void setAbsenceTotal(Integer absenceTotal) {
		this.absenceTotal = absenceTotal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
