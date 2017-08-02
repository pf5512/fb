package com.footballer.rest.api.v2.manager;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.PlayerSupport;
import com.footballer.rest.api.v2.request.SupprotRequest;
import com.footballer.rest.api.v2.vo.Support;
import com.footballer.rest.api.v2.vo.enumType.SupportType;
import com.footballer.util.ObjectUtil;

@Service
public class UserSupportsManager {

	Logger logger = LoggerFactory.getLogger(UserSupportsManager.class);
	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	 
	public void addSupportToVideoItem(Support s){
		
		//check current support eixst or not
		PlayerSupport vs = findVideoSupportByVideoIdAndPlayerId(s);
		if(ObjectUtil.isNotNull(vs) && !vs.getStatus()){ //exist and status=0 then do update the status=1
			toggleUserSupportStatus(vs.getSupportId());
		}else if(ObjectUtil.isNotNull(vs) && vs.getStatus()){  //exist and status=1 then do nothing
			//do nothing (generally this will not happen. just in case)
		}else{  //not exist then add new one.
			//s.setCommentId(null);
			s.setDate(new Date());
			s.setStatus(true);
			s.setType(SupportType.AGREE);
			mybatisBaseDao.insertReturnWithParameter("saveUserSupports", s);
			mybatisBaseDao.insert("saveUserVideoSupports", s);
		}	
	}
	
	public void addSupportToMomentItem(Support s){
		
		PlayerSupport vs = findMomentSupportByMomentIdAndPlayerId(s);
		if(ObjectUtil.isNotNull(vs) && !vs.getStatus()){ //exist and status=0 then do update the status=1
			toggleUserSupportStatus(vs.getSupportId());
		}else if(ObjectUtil.isNotNull(vs) && vs.getStatus()){  //exist and status=1 then do nothing
			//do nothing (generally this will not happen. just in case)
		}else{  //not exist then add new one.

			s.setDate(new Date());
			s.setStatus(true);
			s.setType(SupportType.AGREE);
			mybatisBaseDao.insertReturnWithParameter("saveUserSupports", s);
			mybatisBaseDao.insert("saveUserMomentSupports", s);
		}	
	}
	
	public void saveUserSupportToCommentItem(Support s){
		s.setVideoId(null);
		s.setDate(new Date());
		s.setStatus(true);
		s.setType(SupportType.AGREE);
		mybatisBaseDao.insert("saveUserSupports", s);
	}
	
	public void toggleUserSupportStatus(Long id){
		mybatisBaseDao.update("toggleUserSupportStatus", id);
	}
	
	public void removeUserSupportFromVideoItem(SupprotRequest req){
		mybatisBaseDao.update("removeUserSupportFromVideoItem", req);
	}
	
	public void removeUserSupportFromMomentItem(SupprotRequest req){
		mybatisBaseDao.update("removeUserSupportFromMomentItem", req);
	}
	
	public PlayerSupport findVideoSupportByVideoIdAndPlayerId(Support s){
		return (PlayerSupport) mybatisBaseDao.selectOne("findVideoSupportByVideoIdAndPlayerId", s);
	}
	
	public PlayerSupport findMomentSupportByMomentIdAndPlayerId(Support s){
		return (PlayerSupport) mybatisBaseDao.selectOne("findMomentSupportByMomentIdAndPlayerId", s);
	} 
}
