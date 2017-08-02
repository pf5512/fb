package com.footballer.rest.api.v1.response;

import java.util.List;

import com.footballer.rest.api.v2.domain.Sponsor;

public class SponsorResponse extends JsonResponse {

	private List<Sponsor> sponsorsOfTournamentTeamsList;
	private List<Sponsor> sponsorsOfTournamentList;
	
	
	
	
	
	public List<Sponsor> getSponsorsOfTournamentTeamsList() {
		return sponsorsOfTournamentTeamsList;
	}
	public void setSponsorsOfTournamentTeamsList(List<Sponsor> sponsorsOfTournamentTeamsList) {
		this.sponsorsOfTournamentTeamsList = sponsorsOfTournamentTeamsList;
	}
	public List<Sponsor> getSponsorsOfTournamentList() {
		return sponsorsOfTournamentList;
	}
	public void setSponsorsOfTournamentList(List<Sponsor> sponsorsOfTournamentList) {
		this.sponsorsOfTournamentList = sponsorsOfTournamentList;
	}
}
