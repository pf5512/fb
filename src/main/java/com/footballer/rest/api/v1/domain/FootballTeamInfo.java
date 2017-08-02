package com.footballer.rest.api.v1.domain;

import java.util.Set;

/**
 * Created by ian on 8/29/14.
 */
public class FootballTeamInfo {

    private ClientTeam team;
    private Set<FootballPlayer> footballPlayers;
    private Set<FootballPlayer> applicantPlayers;
    private Set<FootballPlayer> invitedPlayers;
    private String currentUserRelationshipWithTeam;
    
    //Todo
    //private MatchInfo matchRecord; 

    public ClientTeam getTeam() {
        return team;
    }

    public void setTeam(ClientTeam team) {
        this.team = team;
    }

    public Set<FootballPlayer> getFootballPlayers() {
        return footballPlayers;
    }

    public void setFootballPlayers(Set<FootballPlayer> footballPlayers) {
        this.footballPlayers = footballPlayers;
    }

	public Set<FootballPlayer> getApplicantPlayers() {
		return applicantPlayers;
	}

	public void setApplicantPlayers(Set<FootballPlayer> applicantPlayers) {
		this.applicantPlayers = applicantPlayers;
	}

	public Set<FootballPlayer> getInvitedPlayers() {
		return invitedPlayers;
	}

	public void setInvitedPlayers(Set<FootballPlayer> invitedPlayers) {
		this.invitedPlayers = invitedPlayers;
	}

	public String getCurrentUserRelationshipWithTeam() {
		return currentUserRelationshipWithTeam;
	}

	public void setCurrentUserRelationshipWithTeam(
			String currentUserRelationshipWithTeam) {
		this.currentUserRelationshipWithTeam = currentUserRelationshipWithTeam;
	}

}
