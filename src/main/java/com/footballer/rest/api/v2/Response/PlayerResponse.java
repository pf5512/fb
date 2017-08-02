package com.footballer.rest.api.v2.Response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v2.domain.ArenaUser;
import com.footballer.rest.api.v2.domain.PlayerWithTeamsInfo;
import com.footballer.rest.api.v2.vo.Account;
import com.footballer.rest.api.v2.vo.Player;

@JsonInclude(Include.NON_NULL) 
public class PlayerResponse  extends JsonResponse {

	private Player player;
	private Account account;
	private List<ArenaUser> arenaUsers;
	private boolean isArenaOwner;
	private List<PlayerWithTeamsInfo> playerWithTeamsList;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<ArenaUser> getArenaUsers() {
		return arenaUsers;
	}

	public void setArenaUsers(List<ArenaUser> arenaUsers) {
		this.arenaUsers = arenaUsers;
	}

	public boolean isArenaOwner() {
		return isArenaOwner;
	}

	public void setArenaOwner(boolean isArenaOwner) {
		this.isArenaOwner = isArenaOwner;
	}

	public List<PlayerWithTeamsInfo> getPlayerWithTeamsList() {
		return playerWithTeamsList;
	}

	public void setPlayerWithTeamsList(List<PlayerWithTeamsInfo> playerWithTeamsList) {
		this.playerWithTeamsList = playerWithTeamsList;
	}
	
}
