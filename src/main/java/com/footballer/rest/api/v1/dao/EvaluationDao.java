package com.footballer.rest.api.v1.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
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
import com.footballer.rest.api.v1.vo.PlayerMutualEvaluationMatchCount;
import com.footballer.rest.api.v1.vo.enumType.PlayerSkills;
import com.footballer.util.CopyUtil;
import com.footballer.util.ObjectUtil;
import com.google.common.collect.Lists;

/**
 * Created by justin on 20150610
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
public class EvaluationDao extends BaseDao {
	
	@Autowired
	public PlayerDao playerDao;
	
	@Autowired
	public EvaluationAllCountDao evaluationAllCountDao;
	
	@Autowired
	public EvaluationMatchCountDao evaluationMatchCountDao;
	
	
	
    public PlayerMutualEvaluation getPlayerEvaluation(Long id) {
        return  get(PlayerMutualEvaluation.class, id);
    }	
    
    public PlayerMutualEvaluation loadPlayerEvaluationById(Long id){
    	return load(PlayerMutualEvaluation.class, id);
    }
	
    public boolean  savePlayerMutualEvaluation(PlayerMutualEvaluation pme){
		if(pme.getEventId() != null && pme.getPlayerId()!=null && pme.getRecognisePlayerId()!=null && pme.getPlayerSkillType()!=null){
			PlayerMutualEvaluation p = checkPlayerMutualEvaluationNotExist(pme);
			if(ObjectUtil.isNull(p)){
				pme.init();
			    save(pme);
			    evaluationAllCountDao.savePlayerMutualEvaluationAllCount(pme.getPlayerId(),pme.getPlayerSkillType(),1);
			    evaluationMatchCountDao.savePlayerMutualEvaluationMatchCount(pme.getPlayerId(),pme.getPlayerSkillType(),1,pme.getEventId());
			    return true;
			}else{
				delete(p);
				flush();
				System.out.println("移除认同");
				evaluationAllCountDao.decreaseNumber(pme.getPlayerId(),pme.getPlayerSkillType(),1);
			    evaluationMatchCountDao.decreaseNumber(pme.getPlayerId(),pme.getPlayerSkillType(),1,pme.getEventId());
				return true;
			}
		}else{
			return false;
		}
    }
    
    public PlayerMutualEvaluation checkPlayerMutualEvaluationNotExist(PlayerMutualEvaluation pme){
    	
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerMutualEvaluation.class);
    	criteria.add(Restrictions.eq("eventId", pme.getEventId()))
					.add(Restrictions.eq("playerId", pme.getPlayerId()))
					.add(Restrictions.eq("recognisePlayerId", pme.getRecognisePlayerId()))
					.add(Restrictions.eq("playerSkillType", pme.getPlayerSkillType()));
    	
    	PlayerMutualEvaluation p = 	(PlayerMutualEvaluation) criteria.uniqueResult();
    	return  p;
    }
    
    //获取 球员在一次赛事之后得到的认可
    public List<PlayerRecognisedRecord> getPlayerRecognisedRecords(Long eventId, Long playerId) throws CopyException{
    	List<PlayerRecognisedRecord> list= Lists.newArrayList();
    	
    	Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerMutualEvaluation.class);
    	criteria.add(Restrictions.eq("eventId",eventId))
					.add(Restrictions.eq("playerId", playerId));
    	@SuppressWarnings("unchecked")
		List<PlayerMutualEvaluation> pmeList = criteria.list();
    	
    	HashMap<PlayerSkills, List<PlayerBaseInfo>> map  = new HashMap<PlayerSkills, List<PlayerBaseInfo>>();
    	
    	for(PlayerMutualEvaluation pme: pmeList)
    	{
    		PlayerBaseInfo playerBaseInfo = new PlayerBaseInfo();
    		Player player = playerDao.findById(Player.class, pme.getRecognisePlayerId());
    	 	CopyUtil.copy(playerBaseInfo, player);
    	 	
    	 	if(map.containsKey(pme.getPlayerSkillType())){
    	 		map.get(pme.getPlayerSkillType()).add(playerBaseInfo);
    	 	}else{
    	 		List<PlayerBaseInfo>  playerList = Lists.newArrayList();
    	 		playerList.add(playerBaseInfo);
    	 		map.put(pme.getPlayerSkillType(), playerList);
    	 	}
    	}
    	
    	for(Entry<PlayerSkills, List<PlayerBaseInfo>> entry: map.entrySet()) {
    		 System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");
    		 PlayerRecognisedRecord rpr = new PlayerRecognisedRecord();
    		 rpr.setPlayerSkillType(entry.getKey());
    		 rpr.setRecogniseTime(entry.getValue().size());
    		 rpr.setRecognisedOfPlayerList(entry.getValue());
    		 
    		 list.add(rpr);
    	}
    	//sort by 认同次数排序
    	Collections.sort(list,new Comparator<PlayerRecognisedRecord>(){  
            @Override  
            public int compare(PlayerRecognisedRecord r1,PlayerRecognisedRecord r2) {  
                return r2.getRecognisedOfPlayerList().size() - r1.getRecognisedOfPlayerList().size();  
            }  
        });  
    	return list;
    }
    
    public List<PlayerRecognisedRecord> getPlayerRecognisedRecords_v2(Long eventId, Long playerId) throws CopyException {
    	List<PlayerRecognisedRecord> list = Lists.newArrayList();
    	String sql = "select fp.id,fp.nick_name as nickName , fp.imageUrl as imageUrl,"+
    				" 		pmem.property_type as playerSkillType,pmem.number as number "+
    				" from player_mutual_evaluation_match_count pmem, "+
    				" 	football_player fp ,player_mutual_evaluation pme "+
    				" where  pmem.player_id = pme.player_id and pmem.event_id = pme.event_id " +
    				" 	and pmem.property_type =pme.property_type "+
    				" 	and pme.recognise_player_id = fp.id "+
    				" 	and pmem.player_id =:palyerId and pmem.event_id =:eventId "+
    				" 	order by pmem.number desc,pmem.property_type ";
    	
    	Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
    			.addScalar("id",  LongType.INSTANCE)
				.addScalar("nickName", StringType.INSTANCE)
				.addScalar("imageUrl", StringType.INSTANCE)
				.addScalar("imageUrl", StringType.INSTANCE)
				.addScalar("playerSkillType", StringType.INSTANCE)
				.addScalar("number",IntegerType.INSTANCE)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
    			.setParameter("palyerId", playerId)
    			.setParameter("eventId", eventId);
    	@SuppressWarnings("unchecked")
    	List<Map<String,Object>> resultList= query.list();
    	PlayerRecognisedRecord prr = null;
    	PlayerBaseInfo pb = null;
    	String lastSkillType = null;
    	String skillType = null;
    	for(Map<String, Object> map : resultList){
    		skillType = (String)map.get("playerSkillType");
    		if( lastSkillType==null || !lastSkillType.equals(skillType)){
    			prr = new PlayerRecognisedRecord();
    			prr.setPlayerSkillType(PlayerSkills.valueOf(skillType));
        		prr.setRecogniseTime((Integer)map.get("number"));
        		
        		pb = buildPlayerBaseInfo(map);
        		
        		List<PlayerBaseInfo> pbList = Lists.newArrayList();
        		pbList.add(pb);
        		prr.setRecognisedOfPlayerList(pbList);
        		
        		list.add(prr);
    		}else{
    			pb = buildPlayerBaseInfo(map);
        		prr.getRecognisedOfPlayerList().add(pb);
    		}
    		lastSkillType = skillType;
    	}
    	
    	
    	
    	return list;
    }

	private PlayerBaseInfo buildPlayerBaseInfo(Map<String, Object> map) {
		PlayerBaseInfo pb;
		pb = new PlayerBaseInfo();
		pb.setId((Long)map.get("id"));
		pb.setImageUrl((String)map.get("imageUrl"));
		pb.setNickName((String)map.get("nickName"));
		return pb;
	}
	
	public List<PlayerRecognisedRecord> getPlayerAllRecognisedRecords_v2(Long playerId) throws CopyException {
		List<PlayerRecognisedRecord> list = Lists.newArrayList();
	 	String sql = "select fp.id,fp.nick_name as nickName , fp.imageUrl as imageUrl,"+
				" 		pmem.property_type as playerSkillType,pmem.number as number "+
				" from  (football_player fp left join player_mutual_evaluation pme on pme.recognise_player_id = fp.id) " +
				"		left join player_mutual_evaluation_match_count pmem on pmem.player_id = pme.player_id and pmem.property_type =pme.property_type "+
				" where fp.id =:palyerId  "+
				" 	order by pmem.number desc,pmem.property_type ";
	
	Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
			.addScalar("id",  LongType.INSTANCE)
			.addScalar("nickName", StringType.INSTANCE)
			.addScalar("imageUrl", StringType.INSTANCE)
			.addScalar("imageUrl", StringType.INSTANCE)
			.addScalar("playerSkillType", StringType.INSTANCE)
			.addScalar("number",IntegerType.INSTANCE)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.setParameter("palyerId", playerId);
		
		
		return list;
	}
    
    //获取球员 所有的被认可信息 并排序和显示认同人信息
	public List<PlayerRecognisedRecord> getPlayerAllRecognisedRecords(Long playerId) throws CopyException {
		List<PlayerRecognisedRecord> list = Lists.newArrayList();

		String sql = "SELECT pme.property_type skillType, "
						+ "fp.id playerId, "
						+ "fp.nick_name playerName,"
						+ "fp.imageUrl playerImgUrl "
						+ "FROM footballer.player_mutual_evaluation pme "
						+ "left join footballer.football_player fp on fp.id = pme.recognise_player_id "
						+ "where pme.player_id = :playerId ";
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("skillType",  StringType.INSTANCE)
				.addScalar("playerId", LongType.INSTANCE)
				.addScalar("playerName", StringType.INSTANCE)
				.addScalar("playerImgUrl", StringType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(PlayerRecognisedInfo.class))
				.setParameter("playerId", playerId);
		@SuppressWarnings("unchecked")
		List<PlayerRecognisedInfo> resultList = query.list();
    	
		HashMap<String, List<PlayerBaseInfo>> map  = new HashMap<String, List<PlayerBaseInfo>>();
		for (PlayerRecognisedInfo pri : resultList) 
		{
			PlayerBaseInfo playerBaseInfo = new PlayerBaseInfo();
			playerBaseInfo.setId(pri.getPlayerId());
			playerBaseInfo.setNickName(pri.getPlayerName());
			playerBaseInfo.setImageUrl(pri.getPlayerImgUrl());

			if (map.containsKey(pri.getSkillType())) {
				map.get(pri.getSkillType()).add(playerBaseInfo);
			} else {
				List<PlayerBaseInfo> playerList = Lists.newArrayList();
				playerList.add(playerBaseInfo);
				map.put(pri.getSkillType(), playerList);
			}
		}
		
		for (Entry<String, List<PlayerBaseInfo>> entry : map.entrySet()) 
		{
			System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");
			PlayerRecognisedRecord rpr = new PlayerRecognisedRecord();
			rpr.setPlayerSkillType(PlayerSkills.valueOf(entry.getKey()));
			rpr.setRecogniseTime(entry.getValue().size());
			rpr.setRecognisedOfPlayerList(entry.getValue());

			list.add(rpr);
		}
		// sort by 认同次数排序
		Collections.sort(list, new Comparator<PlayerRecognisedRecord>() {
			@Override
			public int compare(PlayerRecognisedRecord r1,
					PlayerRecognisedRecord r2) {
				return r2.getRecognisedOfPlayerList().size()
						- r1.getRecognisedOfPlayerList().size();
			}
		});
		return list;
	}
    
    
    //获取 球员在一次赛事之后发出的认可
	public List<RecogniseOtherPlayerRecord> getRecogniseOtherPlayerRecords(Long eventId, Long recognisePlayerId) throws CopyException{
		List<RecogniseOtherPlayerRecord> list= Lists.newArrayList();
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerMutualEvaluation.class);
    	criteria.add(Restrictions.eq("eventId",eventId))
					.add(Restrictions.eq("recognisePlayerId", recognisePlayerId));
    	@SuppressWarnings("unchecked")
		List<PlayerMutualEvaluation> pmeList = criteria.list();
    	
    	for(PlayerMutualEvaluation pme: pmeList)
    	{
    		RecogniseOtherPlayerRecord ropr = new RecogniseOtherPlayerRecord();
    		PlayerBaseInfo playerBaseInfo = new PlayerBaseInfo();
    		Player player = playerDao.findById(Player.class, pme.getPlayerId());
    	 	CopyUtil.copy(playerBaseInfo, player);
    		
    		ropr.setPlayerSkillType(pme.getPlayerSkillType());
    		ropr.setPlayer(playerBaseInfo);
    		ropr.setRecogniseTime(pme.getCreateDt());
    		list.add(ropr);
    	}
		return list;
	}

	
	//获取球员在某个技术指标的总共被认可的次数
	public Integer findPlayerOneSkillRecognisedTotalNumber(String skillType, Long playerId){
		
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(PlayerMutualEvaluation.class);
    	criteria.add(Restrictions.eq("playerId",playerId))
					.add(Restrictions.eq("playerSkillType", PlayerSkills.valueOf(PlayerSkills.class, skillType)));
    	criteria.setProjection(Projections.rowCount());  
        System.out.println(criteria.uniqueResult());
		return Integer.parseInt(criteria.uniqueResult().toString());
	}
	
	// 获取 球员发出的所有认可记录
	public List<RecogniseOtherPlayerRecord> getAllRecogniseOtherPlayerRecords(Long recognisePlayerId) throws CopyException {
		List<RecogniseOtherPlayerRecord> list = Lists.newArrayList();

		String sql = "SELECT pme.property_type skillType,"
				+ "fp.id playerId,"
				+ "fp.nick_name playerName,"
				+ "fp.imageUrl playerImgUrl,"
				+ "pme.create_dt recogniseDate "
				+ "FROM footballer.player_mutual_evaluation pme "
				+ "left join footballer.football_player fp on fp.id = pme.player_id "
				+ "where pme.recognise_player_id = :recognisePlayerId ";
		Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
				.addScalar("skillType",  StringType.INSTANCE)
				.addScalar("playerId", LongType.INSTANCE)
				.addScalar("playerName", StringType.INSTANCE)
				.addScalar("playerImgUrl", StringType.INSTANCE)
				.addScalar("recogniseDate", DateType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(PlayerRecognisedInfo.class))
				.setParameter("recognisePlayerId", recognisePlayerId);
		@SuppressWarnings("unchecked")
		List<PlayerRecognisedInfo> resultList = query.list();

		for (PlayerRecognisedInfo pri : resultList) {
			RecogniseOtherPlayerRecord ropr = new RecogniseOtherPlayerRecord();
			PlayerBaseInfo playerBaseInfo = new PlayerBaseInfo();
			playerBaseInfo.setId(pri.getPlayerId());
			playerBaseInfo.setNickName(pri.getPlayerName());
			playerBaseInfo.setImageUrl(pri.getPlayerImgUrl());

			ropr.setPlayerSkillType(PlayerSkills.valueOf(pri.getSkillType()));
			ropr.setPlayer(playerBaseInfo);
			ropr.setRecogniseTime(pri.getRecogniseDate());
			list.add(ropr);
		}
		return list;
	}
}
