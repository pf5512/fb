package com.footballer.rest.api.v2.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v1.dao.ArenaDao;
import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.dao.FieldDao;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.vo.Arena;
import com.footballer.rest.api.v1.vo.Event;
import com.footballer.rest.api.v1.vo.Field;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.ArenaUser;
import com.footballer.rest.api.v2.vo.Account;
import com.footballer.rest.api.v2.vo.ArenaMember;
import com.footballer.rest.api.v2.vo.ArenaMemberDiscount;
import com.footballer.rest.api.v2.vo.FieldChargeStandard;
import com.footballer.rest.api.v2.vo.MemberShip;
import com.footballer.util.ObjectUtil;

@Service
public class CommonManager {

	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	@Autowired
	public ArenaDao arenaDao;
	
	@Autowired
	public FieldDao fieldDao;
	
	@Autowired
	public PlayerDao playerDao;
	
	@Autowired
	public EventDao eventDao;
	
	public boolean checkArenaExist(Long arenaId){
		Arena e = arenaDao.findById(Arena.class, arenaId);
		if(ObjectUtil.isNotNull(e)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkOpenIdExist(String openId){
		@SuppressWarnings("unchecked")
		List<Account> accountList = (List<Account>) mybatisBaseDao.selectList("getAccountByOpenId", openId);
		if(ObjectUtil.isNotNull(accountList) && accountList.size() >0){ 
			return true;
		}else{ 
			return false;
		}
	}
	
	public boolean checkFieldExist(Long fieldId){
		Field field = fieldDao.findById(Field.class, fieldId);
		if(ObjectUtil.isNotNull(field)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkEventAndPlayerExist(Long eventId, Long playerId){
		Player p = playerDao.findById(Player.class, playerId);
		if(checkEventExist(eventId) && ObjectUtil.isNotNull(p) ){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkPlayerExist(Long playerId){
		Player p = playerDao.findById(Player.class, playerId);
		if(ObjectUtil.isNotNull(p)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkEventExist(Long eventId){
		Event e = eventDao.findById(Event.class, eventId);
		if(ObjectUtil.isNotNull(e)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkArenaMemeberExist(Long arenaMemberId){
		ArenaMember am = (ArenaMember) mybatisBaseDao.selectOne("getArenaMemberById", arenaMemberId);
		if(ObjectUtil.isNotNull(am)){ 
			return true;
		}else{ 
			return false;
		}
	}
	
	public boolean checkArenaMemeberExist(Long arenaId,Long playerId){
		ArenaMember am = new ArenaMember();
		am.setArenaId(arenaId);
		am.setPlayerId(playerId);
		
		ArenaMember existArenaMember = (ArenaMember) mybatisBaseDao.selectOne("getArenaMemberByArenaIdAndPlayerId", am);
		if(ObjectUtil.isNotNull(existArenaMember)){ 
			return true;
		}else{ 
			return false;
		}
	}
	
	public boolean checkArenaUserExist(Long arenaId,Long playerId){
		ArenaUser au =new ArenaUser();
		au.setArenaId(arenaId);
		au.setPlayerId(playerId);
		
		ArenaUser existArenaUser = (ArenaUser) mybatisBaseDao.selectOne("getArenaUserByArenaIdAndPlayerId", au);
		if(ObjectUtil.isNotNull(existArenaUser)){ 
			return true;
		}else{ 
			return false;
		}
	}
	
	public boolean checkArenaMemberLevelExist(Long arenaId, Integer level){
		ArenaMemberDiscount amd = new ArenaMemberDiscount();
		amd.setArenaId(arenaId);
		amd.setLevel(level);
		ArenaMemberDiscount newAmd = (ArenaMemberDiscount) mybatisBaseDao.selectOne("getArenaMemberLevel", amd);
		if(ObjectUtil.isNotNull(newAmd)){ 
			return true;
		}else{ 
			return false;
		}
	}
	
	
	public boolean checkArenaMemberDiscountExist(Long arenaMemberDiscountId){
		ArenaMemberDiscount amd = (ArenaMemberDiscount) mybatisBaseDao.selectOne("getArenaMemberDiscountById", arenaMemberDiscountId);
		if(ObjectUtil.isNotNull(amd)){ 
			return true;
		}else{ 
			return false;
		}
	}
	
	public boolean checkArenaFieldStandardExist(Long id){
		FieldChargeStandard fcs = (FieldChargeStandard) mybatisBaseDao.selectOne("checkFieldChargeStandardExist", id);
		if(ObjectUtil.isNotNull(fcs)){ 
			return true;
		}else{ 
			return false;
		}
	}
	
	public Account checkCellPhoneExist(Long cellPhone){
		return (Account) mybatisBaseDao.selectOne("getAccountBycellPhone", cellPhone);
	} 
	
	public boolean checkWeChatNoExist(String weChatNo){
		@SuppressWarnings("unchecked")
		List<MemberShip> ms = (List<MemberShip>) mybatisBaseDao.selectList("getMemberShipByWeChatNo", weChatNo);
		if(ObjectUtil.isNotNull(ms) && ms.size()>0){ 
			return true;
		}else{ 
			return false;
		}
	}
	
	public FieldChargeStandard findFieldByFieldChargeStandardById(Long id){
		return (FieldChargeStandard) mybatisBaseDao.selectOne("getFieldChargeStandardById", id);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getArenaFieldStartTimeListByArenaId(Long arenaId){
		return (List<String>) mybatisBaseDao.selectList("getArenaFieldStartTimeListByArenaId", arenaId);
	}
	
	
}