package com.footballer.rest.api.v1.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.domain.PlayerBaseInfo;
import com.footballer.rest.api.v1.domain.PlayerRecognisedRecord;
import com.footballer.rest.api.v1.domain.RecogniseOtherPlayerRecord;
import com.footballer.rest.api.v1.exception.CopyException;
import com.footballer.rest.api.v1.sqlResultBean.PlayerRecognisedInfo;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.PlayerMutualEvaluation;
import com.footballer.rest.api.v1.vo.PlayerMutualEvaluationAllCount;
import com.footballer.rest.api.v1.vo.PlayerMutualEvaluationMatchCount;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;
import com.footballer.util.CopyUtil;
import com.footballer.util.ObjectUtil;
import com.google.common.collect.Lists;

/**
 * Created by sam
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
public class EvaluationMatchCountDao extends BaseDao {
	
	
    public PlayerMutualEvaluationMatchCount getPlayerEvaluation(Long id) {
        return  get(PlayerMutualEvaluationMatchCount.class, id);
    }	
    
    public PlayerMutualEvaluationMatchCount loadPlayerEvaluationById(Long id){
    	return load(PlayerMutualEvaluationMatchCount.class, id);
    }
    
    public boolean  savePlayerMutualEvaluationMatchCount(Long playerId,PlayerSkills playerSkillType,Integer number,Long eventId){
    	PlayerMutualEvaluationMatchCount pmem = new PlayerMutualEvaluationMatchCount();
    	pmem.setPlayerId(playerId);
    	pmem.setPlayerSkillType(playerSkillType);
    	pmem.setNumber(number);
    	pmem.setEventId(eventId);
    	return this.savePlayerMutualEvaluationMatch(pmem);
    	
    }
	
    
    public boolean  savePlayerMutualEvaluationMatch(PlayerMutualEvaluationMatchCount pmem){
		if(pmem.getEventId() != null && pmem.getPlayerId()!=null &&  pmem.getPlayerSkillType()!=null){
			PlayerMutualEvaluationMatchCount p = checkPlayerMutualEvaluationNotExist(pmem);
			if(ObjectUtil.isNull(p)){
				pmem.init();
			    save(pmem);
			    return true;
			}else{
				this.increaseNumber(pmem);
				return true;
			}
		}else{
			return false;
		}
    }
    
  //获取球员在某个技术指标的总共被认可的次数
  	public Integer findPlayerOneSkillRecognisedMatchNumber(String skillType, Long playerId,Long eventId){
  		
  		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerMutualEvaluationMatchCount.class);
      	criteria.add(Restrictions.eq("playerId",playerId))
      				.add(Restrictions.eq("eventId",eventId))
  					.add(Restrictions.eq("playerSkillType",  skillType));
      	PlayerMutualEvaluationMatchCount pmeac = (PlayerMutualEvaluationMatchCount)criteria.uniqueResult();
  		return pmeac.getNumber();
  	}
    
    public void increaseNumber(PlayerMutualEvaluationMatchCount pmem){
    	String sql = "update player_mutual_evaluation_match_count t set t.number = t.number+1 where t.event_id = :eventId and t.player_id = :playerId and  t.property_type = :playerSkillType ";
    	Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
    	query.setParameter("playerId", pmem.getPlayerId())
    		.setParameter("eventId", pmem.getEventId())
    		.setParameter("playerSkillType", pmem.getPlayerSkillType().name());
    	query.executeUpdate();
    }
    
    
    public void  decreaseNumber(Long playerId,PlayerSkills playerSkillType,Integer number,Long eventId){
    	PlayerMutualEvaluationMatchCount pmem = new PlayerMutualEvaluationMatchCount();
    	pmem.setPlayerId(playerId);
    	pmem.setPlayerSkillType(playerSkillType);
    	pmem.setNumber(number);
    	pmem.setEventId(eventId);
    	this.decreaseNumber(pmem);
    	
    }
    
    public void decreaseNumber(PlayerMutualEvaluationMatchCount pmem){
    	String sql = "update player_mutual_evaluation_match_count t set t.number = t.number-1 where t.event_id = :eventId and t.player_id = :playerId and t.property_type = :playerSkillType and t.number >= 1 ";
    	Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
    	query.setParameter("playerId", pmem.getPlayerId())
		.setParameter("eventId", pmem.getEventId())
		.setParameter("playerSkillType",  pmem.getPlayerSkillType().name());
    	query.executeUpdate();
    }
    
    public PlayerMutualEvaluationMatchCount checkPlayerMutualEvaluationNotExist(PlayerMutualEvaluationMatchCount pme){
    	
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerMutualEvaluationMatchCount.class);
    	criteria.add(Restrictions.eq("eventId", pme.getEventId()))
					.add(Restrictions.eq("playerId", pme.getPlayerId()))
					.add(Restrictions.eq("playerSkillType", pme.getPlayerSkillType()));
    	
    	PlayerMutualEvaluationMatchCount p = 	(PlayerMutualEvaluationMatchCount) criteria.uniqueResult();
    	return  p;
    }
}
