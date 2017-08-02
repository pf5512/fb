package com.footballer.rest.api.v1.domain;

import java.util.List;

/**
 * Updated by justin on 2015.05.18
 */
public class FootballPlayer {

    private User user;
    private List<FootballTeam> teams;
    private String friendStatusOfAccount;
    private boolean isTeamMemberOfAccount;
    private List<Long> joinedAccountTeamIds;  //球员已加入的，属于用户的球队 ids
    
    public FootballPlayer() {

    }

    public FootballPlayer(User user, List<FootballTeam> teams) {
        this.user = user;
        this.teams = teams;
    }
    
    public FootballPlayer(User user, List<FootballTeam> teams, String friendStatusOfAccount) {
        this.user = user;
        this.teams = teams;
        this.friendStatusOfAccount = friendStatusOfAccount;
    }
    
    public FootballPlayer(User user, List<FootballTeam> teams, String friendStatusOfAccount, boolean isTeamMemberOfAccount, List<Long> joinedAccountTeamIds) {
        this.user = user;
        this.teams = teams;
        this.friendStatusOfAccount = friendStatusOfAccount;
        this.isTeamMemberOfAccount = isTeamMemberOfAccount;
        this.joinedAccountTeamIds = joinedAccountTeamIds;
    }
    
    public static FootballPlayer build(Long playerId, String playerName, String imgUrl) {
    	User user = new User();
    	user.setPlayerId(playerId);
    	user.setPlayerNickName(playerName);
    	user.setPeopleImgUrl(imgUrl);
    	
    	FootballPlayer player = new FootballPlayer();
    	player.setUser(user);
    	
    	return player;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<FootballTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<FootballTeam> teams) {
        this.teams = teams;
    }

	public boolean isTeamMemberOfAccount() {
		return isTeamMemberOfAccount;
	}

	public void setTeamMemberOfAccount(boolean isTeamMemberOfAccount) {
		this.isTeamMemberOfAccount = isTeamMemberOfAccount;
	}

	public List<Long> getJoinedAccountTeamIds() {
		return joinedAccountTeamIds;
	}

	public void setJoinedAccountTeamIds(List<Long> joinedAccountTeamIds) {
		this.joinedAccountTeamIds = joinedAccountTeamIds;
	}

	public String getFriendStatusOfAccount() {
		return friendStatusOfAccount;
	}

	public void setFriendStatusOfAccount(
			String friendStatusOfAccount) {
		this.friendStatusOfAccount = friendStatusOfAccount;
	}
}
