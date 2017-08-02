package com.footballer.rest.api.v2.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.Sponsor;

@Service
public class SponsorManager {
	
	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	@SuppressWarnings("unchecked")
	public List<Sponsor> getSponsorsOfTournamnetByTournamentId(Long tournamentId){
		return (List<Sponsor>) mybatisBaseDao.selectList("getSponsorsOfTournamnetByTournamentId", tournamentId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Sponsor> getSponsorsOfTournamentTeamsByTournamentId(Long tournamentId){
		return (List<Sponsor>) mybatisBaseDao.selectList("getSponsorsOfTournamentTeamsByTournamentId", tournamentId);
	}

}
