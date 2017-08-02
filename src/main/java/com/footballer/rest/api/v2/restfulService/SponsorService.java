package com.footballer.rest.api.v2.restfulService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v1.response.SponsorResponse;
import com.footballer.rest.api.v2.manager.SponsorManager;
import com.footballer.util.ObjectUtil;

@Path("/mobile/v2/sponsor-service")
public class SponsorService {
	
	@Autowired
	private SponsorManager sMgr;
	
	@GET
	@Path("/getAllSponsorsBytournamentId/{tournamentId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public SponsorResponse getTournamentSponsorsBytournamentId(@PathParam("tournamentId") Long tournamentId){
		SponsorResponse response = new SponsorResponse();
			
		if(ObjectUtil.isNotNull(tournamentId)){
			response.setSponsorsOfTournamentList(sMgr.getSponsorsOfTournamnetByTournamentId(tournamentId));
			response.setSponsorsOfTournamentTeamsList(sMgr.getSponsorsOfTournamentTeamsByTournamentId(tournamentId));
			response.setStatus(JsonResponse.SUCCESS);
		}else{
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("paramters is null");
		}
		return response;	
	}
}
