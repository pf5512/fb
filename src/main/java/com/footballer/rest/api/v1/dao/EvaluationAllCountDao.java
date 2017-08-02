package com.footballer.rest.api.v1.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.vo.PlayerMutualEvaluationAllCount;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;
import com.footballer.util.ObjectUtil;

/**
 * Created by sam 
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
public class EvaluationAllCountDao extends BaseDao {
	
	
    public PlayerMutualEvaluationAllCount getPlayerEvaluation(Long id) {
        return  get(PlayerMutualEvaluationAllCount.class, id);
    }	
    
    public PlayerMutualEvaluationAllCount loadPlayerEvaluationById(Long id){
    	return load(PlayerMutualEvaluationAllCount.class, id);
    }
    
    
    public boolean  savePlayerMutualEvaluationAllCount(Long playerId,PlayerSkills playerSkillType,Integer number){
    	PlayerMutualEvaluationAllCount pmeac = new PlayerMutualEvaluationAllCount();
    	pmeac.setPlayerId(playerId);
    	pmeac.setPlayerSkillType(playerSkillType);
    	pmeac.setNumber(number);
    	return this.savePlayerMutualEvaluationAllCount(pmeac);
    	
    }
	
    
    public boolean  savePlayerMutualEvaluationAllCount(PlayerMutualEvaluationAllCount pmeac){
		if( pmeac.getPlayerId()!=null &&  pmeac.getPlayerSkillType()!=null && pmeac.getNumber() !=null){
			PlayerMutualEvaluationAllCount p = checkPlayerMutualEvaluationNotExist(pmeac);
			if(ObjectUtil.isNull(p)){
//				pmeac.init(); 
			    save(pmeac);
			    return true;
			}else{
				this.increaseNumber(pmeac);
				return true;
			}
		}else{
			return false;
		}
    }
    
	//获取球员在某个技术指标的总共被认可的次数
	public Integer findPlayerOneSkillRecognisedTotalNumber(String skillType, Long playerId){
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerMutualEvaluationAllCount.class);
    	criteria.add(Restrictions.eq("playerId",playerId))
					.add(Restrictions.eq("playerSkillType", PlayerSkills.valueOf(PlayerSkills.class, skillType)));
    	PlayerMutualEvaluationAllCount pmeac = (PlayerMutualEvaluationAllCount)criteria.uniqueResult();
		return pmeac.getNumber();
	}
    
    public void increaseNumber(PlayerMutualEvaluationAllCount pmeac){
    	String sql = "update player_mutual_evaluation_all_count t set t.number = t.number+1 where  t.player_id = :playerId and t.property_type = :playerSkillType";
    	Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
    	query.setParameter("playerId", pmeac.getPlayerId())
    	.setParameter("playerSkillType", pmeac.getPlayerSkillType().name());
    	query.executeUpdate();
    }
    
    public void decreaseNumber(Long playerId,PlayerSkills playerSkillType,Integer number){
	   	PlayerMutualEvaluationAllCount pmeac = new PlayerMutualEvaluationAllCount();
		pmeac.setPlayerId(playerId);
		pmeac.setPlayerSkillType(playerSkillType);
		pmeac.setNumber(number);
		this.decreaseNumber(pmeac);
	
    }
    
    public void decreaseNumber(PlayerMutualEvaluationAllCount pmeac){
    	String sql = "update player_mutual_evaluation_all_count t set t.number = t.number-1 where  t.property_type = :playerSkillType and  t.player_id = :playerId and t.number >= 1 ";
    	Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
    	query.setParameter("playerId",  pmeac.getPlayerId())
    	.setParameter("playerSkillType", pmeac.getPlayerSkillType().name());
    	query.executeUpdate();
    }
    
    public PlayerMutualEvaluationAllCount checkPlayerMutualEvaluationNotExist(PlayerMutualEvaluationAllCount pmeac){
    	
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerMutualEvaluationAllCount.class);
    	criteria.add(Restrictions.eq("playerId", pmeac.getPlayerId()))
					.add(Restrictions.eq("playerSkillType", pmeac.getPlayerSkillType()));
    	
    	PlayerMutualEvaluationAllCount p = 	(PlayerMutualEvaluationAllCount) criteria.uniqueResult();
    	return  p;
    }
}
