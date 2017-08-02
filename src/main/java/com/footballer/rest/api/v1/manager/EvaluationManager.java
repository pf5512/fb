package com.footballer.rest.api.v1.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v1.dao.EvaluationAllCountDao;
import com.footballer.rest.api.v1.dao.EvaluationDao;
import com.footballer.rest.api.v1.dao.EvaluationMatchCountDao;
import com.footballer.rest.api.v1.vo.PlayerMutualEvaluation;
import com.footballer.rest.api.v1.vo.PlayerMutualEvaluationAllCount;
import com.footballer.rest.api.v1.vo.PlayerMutualEvaluationMatchCount;

@Service
public class EvaluationManager {
	@Autowired
	public EvaluationAllCountDao evaluationAllCountDao;
	
	@Autowired
	public EvaluationMatchCountDao evaluationMatchCountDao;
	
	@Autowired
	public EvaluationDao EvaluationDao;
	
	public boolean savePlayerMutualEvaluation(PlayerMutualEvaluation pme){
		if(EvaluationDao.savePlayerMutualEvaluation(pme)){
			PlayerMutualEvaluationAllCount pmeac = new PlayerMutualEvaluationAllCount();
			PlayerMutualEvaluationMatchCount pmemc = new PlayerMutualEvaluationMatchCount();
			
			pmeac.setNumber(1);
			pmeac.setPlayerId(pme.getPlayerId());
			pmeac.setPlayerSkillType(pme.getPlayerSkillType());
			
			pmemc.setNumber(1);
			pmemc.setPlayerId(pme.getPlayerId());
			pmemc.setPlayerSkillType(pme.getPlayerSkillType());
			pmemc.setEventId(pme.getEventId());
		}
		
		return true;
	}
}
