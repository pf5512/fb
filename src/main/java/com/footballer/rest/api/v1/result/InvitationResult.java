package com.footballer.rest.api.v1.result;

import com.footballer.rest.api.v1.domain.PlayerBaseInfo;
import com.footballer.rest.api.v1.domain.TeamBaseInfo;
import com.footballer.rest.api.v1.vo.PlayerPlayerInvitation;
import com.footballer.rest.api.v1.vo.TeamPlayerInvitation;
import com.footballer.rest.api.v1.vo.enumType.InvitationMessageType;

public class InvitationResult {

	private PlayerBaseInfo fromPlayer;
	private PlayerBaseInfo toPlayer;
	private TeamBaseInfo team;
	private PlayerPlayerInvitation playerPlayerInvitation;
	private TeamPlayerInvitation teamPlayerInvitation;
	private InvitationMessageType messageType;//消息的从属类型： 发送方/接收方
	
	public PlayerBaseInfo getFromPlayer() {
		return fromPlayer;
	}
	public void setFromPlayer(PlayerBaseInfo fromPlayer) {
		this.fromPlayer = fromPlayer;
	}
	public PlayerBaseInfo getToPlayer() {
		return toPlayer;
	}
	public void setToPlayer(PlayerBaseInfo toPlayer) {
		this.toPlayer = toPlayer;
	}
	public TeamBaseInfo getTeam() {
		return team;
	}
	public void setTeam(TeamBaseInfo team) {
		this.team = team;
	}
	public InvitationMessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(InvitationMessageType messageType) {
		this.messageType = messageType;
	}
	public PlayerPlayerInvitation getPlayerPlayerInvitation() {
		return playerPlayerInvitation;
	}
	public void setPlayerPlayerInvitation(
			PlayerPlayerInvitation playerPlayerInvitation) {
		this.playerPlayerInvitation = playerPlayerInvitation;
	}
	public TeamPlayerInvitation getTeamPlayerInvitation() {
		return teamPlayerInvitation;
	}
	public void setTeamPlayerInvitation(TeamPlayerInvitation teamPlayerInvitation) {
		this.teamPlayerInvitation = teamPlayerInvitation;
	}

	
}
