package com.footballer.rest.api.v2.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.vo.PlayerMatchRelationship;
@Service
public class PlayerMatchRelationshipManager {
	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	
	public void doMatchRelationships(List<PlayerMatchRelationship> pmrs){
		PlayerMatchRelationship dbPmr = null;
		Map<Long,Integer> map = new HashMap<Long,Integer>();
		for(PlayerMatchRelationship pmr:pmrs){
			dbPmr = (PlayerMatchRelationship)mybatisBaseDao.selectOne("findPlayerMatchRelationshipByPlayerIdAndRelatedPlayerId", pmr);
			if(dbPmr == null){
				mybatisBaseDao.insert("savePlayerMatchRelationship", pmr);
			}
		}
		
	}
	
	public PlayerMatchRelationship countPlayerMatchRelationshipByPlayerId(Long playerId){
		PlayerMatchRelationship dbPmr = null;
		dbPmr = (PlayerMatchRelationship)mybatisBaseDao.selectOne("countPlayerMatchRelationshipByPlayerId", playerId);
		return dbPmr;
	}
		
}
