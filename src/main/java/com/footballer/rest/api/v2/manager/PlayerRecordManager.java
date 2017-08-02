package com.footballer.rest.api.v2.manager;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v1.vo.enumType.EventType;
import com.footballer.rest.api.v2.Response.PlayerRecordResponse;
import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.vo.PlayerMatchRelationship;
import com.footballer.rest.api.v2.vo.PlayerRecord;
import com.footballer.util.DateUtil;

/**   
 *
 * @Description: PlayerRecordvo
 * @date 2015-09-05 17:53:07
 * @version V1.0   
 *
 */
@Service
public class PlayerRecordManager {
	
	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	@Autowired
	public PlayerMatchRelationshipManager playerMatchRelationshipManager;
	
	
	
	@SuppressWarnings("unchecked")
	public List<PlayerRecord> findPlayerRecordByYear(Long id){
		Integer year = DateUtil.getCurrentYear(); 
		PlayerRecord pr = new PlayerRecord();
		pr.setPlayerId(id);
		pr.setYears(year);
		List<PlayerRecord> list = (List<PlayerRecord>)mybatisBaseDao.selectList("findPlayerRecordYear", pr);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<PlayerRecord> findPlayerRecordByTotal(Long id){
		List<PlayerRecord> list = (List<PlayerRecord>)mybatisBaseDao.selectList("findPlayerRecordTotal", id);
		return list;
	}
	
	public PlayerRecordResponse getPlayerRecordMain(Long id){
		List<PlayerRecord> totalList = this.findPlayerRecordByTotal(id);
		List<PlayerRecord> yearList = this.findPlayerRecordByYear(id);
		
		PlayerRecordResponse response = new PlayerRecordResponse();
		
		PlayerRecord playerRecordTotals = new PlayerRecord();
		PlayerRecord playerFootballGameTotalRecord = new PlayerRecord();
		PlayerRecord playerFootballGameYearRecord  = new PlayerRecord();
		PlayerRecord playerFootballMatchTotalRecord = new PlayerRecord();
		PlayerRecord playerFootballMatchYearRecord = new PlayerRecord();
		
		response.setPlayerRecordTotals(playerRecordTotals);
		response.setPlayerFootballGameTotalRecord(playerFootballGameTotalRecord);
		response.setPlayerFootballGameYearRecord(playerFootballGameYearRecord);
		response.setPlayerFootballMatchTotalRecord(playerFootballMatchTotalRecord);
		response.setPlayerFootballMatchYearRecord(playerFootballMatchYearRecord);
		
		PlayerMatchRelationship pmr = (PlayerMatchRelationship)playerMatchRelationshipManager.countPlayerMatchRelationshipByPlayerId(id);
		playerRecordTotals.setDistinctPlayerCount(pmr.getDistinctPlayerCount());
		
//		response.setPlayerRecordTotals(totalList);
//		response.setPlayerRecordYears(yearList);
		
		
		if(totalList != null){
			for(PlayerRecord pr:totalList){
				sumPlayerRecord(playerRecordTotals, pr);
				if(EventType.footballGame == pr.getEventType()){
					sumPlayerRecord(playerFootballGameTotalRecord, pr);
				}else if(EventType.footballMatch == pr.getEventType()){
					sumPlayerRecord(playerFootballMatchTotalRecord, pr);
				}else if(EventType.watchFootballMatch == pr.getEventType()){
					//暂时
				}
			}
		}
		
		int year = DateUtil.getCurrentYear();
		playerFootballGameYearRecord.setYears(year);
		if(yearList != null){
			for(PlayerRecord pr:yearList){
				if(EventType.footballGame == pr.getEventType()){
					if(year == pr.getYears()){
						sumPlayerRecord(playerFootballGameYearRecord, pr);
					}
				}else if(EventType.footballMatch == pr.getEventType()){
					if(year == pr.getYears()){
						sumPlayerRecord(playerFootballMatchYearRecord, pr);
					}
				}else if(EventType.watchFootballMatch == pr.getEventType()){
					//暂时
				}
			}
		}
		
		
		return response;
	}

	private void sumPlayerRecord(PlayerRecord sum,
			PlayerRecord pr) {
		sum.setMatches(sum.getMatches()+pr.getMatches());
		sum.setHours(sum.getHours()+pr.getHours());
		sum.setMinutes(sum.getMinutes()+pr.getMinutes());
		sum.setPlayerCount(sum.getPlayerCount()+pr.getPlayerCount());
	}
	
}
