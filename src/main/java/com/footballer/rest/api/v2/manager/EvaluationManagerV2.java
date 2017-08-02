package com.footballer.rest.api.v2.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;
import com.footballer.rest.api.v2.Response.DashboardResponse;
import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.vo.Dashboard;
import com.footballer.rest.api.v2.vo.Player;
import com.footballer.rest.api.v2.vo.PlayerMutualEvaluationAllCount;
import com.footballer.rest.api.v2.vo.PlayerMutualEvaluationMatchCount;
import com.footballer.util.StringUtil;
import com.google.common.collect.Lists;

@Component("evaluationManagerV2")
public class EvaluationManagerV2 {
	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	public PlayerMutualEvaluationMatchCount findPlayerMutualEvaluationMatchCountByIdAndProperty(Long playerId,Long eventId,PlayerSkills playerSkillType){
		PlayerMutualEvaluationMatchCount pmemc = new PlayerMutualEvaluationMatchCount();
		pmemc.setPlayerId(playerId);
		pmemc.setEventId(eventId);
		pmemc.setPlayerSkillType(playerSkillType);
		pmemc = (PlayerMutualEvaluationMatchCount)mybatisBaseDao.selectOne("findPlayerMutualEvaluationMatchCountByIdAndProperty", pmemc);
		return pmemc;
	}
	
	public PlayerMutualEvaluationAllCount findPlayerMutualEvaluationAllCountByIdAndProperty(Long playerId,PlayerSkills playerSkillType){
		PlayerMutualEvaluationAllCount pmeac = new PlayerMutualEvaluationAllCount();
		pmeac.setPlayerId(playerId);
		pmeac.setPlayerSkillType(playerSkillType);
		pmeac = (PlayerMutualEvaluationAllCount)mybatisBaseDao.selectOne("findPlayerMutualEvaluationAllCountByIdAndProperty", pmeac);
		return pmeac;
	}
	
	public PlayerMutualEvaluationAllCount findMaxProperty(Long playerId){
		return (PlayerMutualEvaluationAllCount)mybatisBaseDao.selectOne("findMaxProperty", playerId);
	}
	
	@SuppressWarnings("unchecked")
	public List<PlayerMutualEvaluationAllCount> findListByPlayerIdDesc(Long playerId){
		return (List<PlayerMutualEvaluationAllCount>)mybatisBaseDao.selectList("findPlayerMutualEvaluationAllCountByIdDesc", playerId);
	}
	
	@SuppressWarnings("unchecked")
	public DashboardResponse findDashboard(Long eventId){
		DashboardResponse response = new DashboardResponse();
		 
		List<Player> playerList = (List<Player>)mybatisBaseDao.selectList("findPlayerMutualEvaluationMatchSumDesc", eventId);
		List<Dashboard> boardList = Lists.newArrayList();
		Dashboard board = null;
		for(Player player:playerList){
			board = new Dashboard();
			checkNullPlayer(player);
			board.setPlayer(player);
			boardList.add(board);
		}
		
		List<PlayerMutualEvaluationMatchCount> mapList = (List<PlayerMutualEvaluationMatchCount>)mybatisBaseDao.selectList("findPlayerMutualEvaluationMatchCountByEventIdAndPlayerIdDesc", eventId);
		Map<Long,List<PlayerMutualEvaluationMatchCount>> resultMap= new HashMap<Long,List<PlayerMutualEvaluationMatchCount>>();
		List<PlayerMutualEvaluationMatchCount> tmpList = null;
		for(PlayerMutualEvaluationMatchCount pmeac:mapList){
			tmpList = resultMap.get(pmeac.getPlayerId());
			if(tmpList == null){
				tmpList = Lists.newArrayList();
				resultMap.put(pmeac.getPlayerId(), tmpList);
			}
			tmpList.add(pmeac);
		}
		
		for(Dashboard db:boardList){
			db.setEvaluationList(resultMap.get(db.getPlayer().getId()));
		}
    	
		response.setEvaluations(boardList);
		return response;
	}

	private void checkNullPlayer(Player player) {
		if(StringUtil.isEmpty(player.getNickName())){
			player.setNickName("");
		}
		if(StringUtil.isEmpty(player.getImageUrl())){
			player.setImageUrl("");
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Player> findRecognisedPlayersByEventIdAndPlayerIdAndProperty(Long eventId,Long playerId,String playerSkill){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("eventId", eventId);
		map.put("playerId", playerId);
		map.put("playerSkill", playerSkill);
		List<Player> resultList = (List<Player>)mybatisBaseDao.selectList("findRecognisedPlayersByEventIdAndPlayerIdAndProperty", map);
		for(Player p: resultList){
			checkNullPlayer(p);
		}
		return resultList;
		
	}
	
//	public List<PlayerRecognisedRecord> findPlayerRecognisedRecords(Long playerId,Long eventId){
//		
//	}
	
	
	
	
}
