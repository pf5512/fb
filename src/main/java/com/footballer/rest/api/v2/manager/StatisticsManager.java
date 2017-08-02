package com.footballer.rest.api.v2.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v1.domain.FootballPlayerTeamActivity;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.rest.api.v1.vo.PlayerMutualEvaluationAllCount;
import com.footballer.rest.api.v1.vo.enumType.EventType;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;
import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.vo.PlayerMatchRelationship;
import com.footballer.rest.api.v2.vo.PlayerMutualEvaluationMatchCount;
import com.footballer.rest.api.v2.vo.PlayerRecord;
import com.footballer.util.DateUtil;

@Service
public class StatisticsManager {

	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	 
	@Autowired
	public PlayerMatchRelationshipManager playerMatchRelationshipManager;
	
	public void createOrIncreaseEvaluation(long playerId ,long eventId, PlayerSkills playerSkillType){
		PlayerMutualEvaluationMatchCount pmemc = buildPlayerMutualEvaluationMatchCount(
				playerId, eventId,playerSkillType);
		if(mybatisBaseDao.selectOne("findPlayerMutualEvaluationAllCountByIdAndProperty", pmemc) == null){
			mybatisBaseDao.insert("savePlayerMutualEvaluationMatchCount", pmemc);
		}else{
			mybatisBaseDao.insert("increasePlayerMutualEvaluationMatchCount", pmemc);
		}
		
		PlayerMutualEvaluationAllCount pmeac = buildPlayerMutualEvaluationAllCount(playerId,playerSkillType);
		if(mybatisBaseDao.selectOne("findPlayerMutualEvaluationAllCountByIdAndProperty", pmeac) == null){
			mybatisBaseDao.insert("savePlayerMutualEvaluationAllCount", pmeac);
		}else{
			mybatisBaseDao.insert("increasePlayerMutualEvaluationAllCount", pmeac);
		}
	}

	private PlayerMutualEvaluationAllCount buildPlayerMutualEvaluationAllCount(
			long playerId, PlayerSkills playerSkillType) {
		PlayerMutualEvaluationAllCount pmeac = new PlayerMutualEvaluationAllCount();
		pmeac.setCreateDt(new Date());
		pmeac.setNumber(1);
		pmeac.setPlayerId(playerId);
		pmeac.setPlayerSkillType(playerSkillType);
		return pmeac;
	}

	private PlayerMutualEvaluationMatchCount buildPlayerMutualEvaluationMatchCount(
			long playerId, long eventId, PlayerSkills playerSkillType) {
		PlayerMutualEvaluationMatchCount pmemc = new PlayerMutualEvaluationMatchCount();
	
		pmemc.setPlayerId(playerId);
		pmemc.setPlayerSkillType(playerSkillType);
		pmemc.setCreateDt(new Date());
		pmemc.setNumber(1);
		pmemc.setEventId(eventId);
		return pmemc;
	}
	
	public void decreaseEvaluation(long playerId ,long eventId, PlayerSkills playerSkillType){
		PlayerMutualEvaluationMatchCount pmemc = buildPlayerMutualEvaluationMatchCount(
				playerId, eventId,playerSkillType);
		PlayerMutualEvaluationAllCount pmeac = buildPlayerMutualEvaluationAllCount(playerId,playerSkillType);
		mybatisBaseDao.insert("decreasePlayerMutualEvaluationMatchCount", pmemc);
		mybatisBaseDao.insert("decreasePlayerMutualEvaluationAllCount", pmeac);
	}
	
	@Async
	public void asyncBuildPlayerRecord(Collection<FootballPlayerTeamActivity> list,Event event){
	
		
		//维护基本信息，包括出场时间，交手过的球员人次
		Long minute = (event.getEnd_date().getTime() - event.getStart_date().getTime())/60000;
		double hour = Double.valueOf(String.valueOf(minute))/60D;
		int playerCount  = list.size() - 1;
		for(FootballPlayerTeamActivity activity:list){
			this.createOrIncreasePlayerRecord(activity.getPlayerId(), hour, playerCount, minute,event.getType());
		}
		
		//维护球员关系（去重）
		 this.doPlayerMatchRelationship(list);
	
	}
	
	public void doPlayerMatchRelationship(Collection<FootballPlayerTeamActivity> collection){
		List<PlayerMatchRelationship> list = new ArrayList<PlayerMatchRelationship>();
		PlayerMatchRelationship pmr = null;
		Map<Long,Integer> map = null;
		for(FootballPlayerTeamActivity fpta1:collection){
			for(FootballPlayerTeamActivity fpta2:collection){
				if(fpta1.getPlayerId() == fpta2.getPlayerId()){
					continue;
				}
				pmr = new PlayerMatchRelationship();
				list.add(pmr);
				pmr.setPlayerId(fpta1.getPlayerId());
				pmr.setRelatedPlayerId(fpta2.getPlayerId());
			}
		}
		 playerMatchRelationshipManager.doMatchRelationships(list);
	}
	
	public void createOrIncreasePlayerRecord(long playerId,double hour,int playerCount,long minute,EventType et){
		PlayerRecord pr = this.buildPalyerRecord(playerId, hour, playerCount,minute,et);
		pr.setUpdateDt(new Date());
		PlayerRecord dbPr = (PlayerRecord)mybatisBaseDao.selectOne("findPlayerRecordYear", pr);
		if(dbPr == null){
			mybatisBaseDao.insert("savePlayerRecord", pr);
		}else{
			mybatisBaseDao.update("increasePlayerRecordById", pr);
		}
	}

	private PlayerRecord buildPalyerRecord(long playerId, double hour, int playerCount,long minute,EventType et) {
		PlayerRecord pr = new PlayerRecord();
		pr.setPlayerId(playerId);
		pr.setHours(hour);
		pr.setPlayerCount(playerCount);
		pr.setMatches(1);
		pr.setMinutes(minute);
		pr.setYears(DateUtil.getCurrentYear());
		pr.setCreateDt(new Date());
		pr.setEventType(et);
		return pr;
	}
	
	public void decreasePlayerRecord(long playerId,double hour,long minute,int playerCount,EventType et){
		PlayerRecord pr = this.buildPalyerRecord(playerId, hour, playerCount,minute,et);
		mybatisBaseDao.update("decreasePlayerRecordById", pr);
	}
	
}
