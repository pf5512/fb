package com.footballer.rest.api.v2.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.PlayerSupport;

@Service
public class SupportManager {

	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	 
	@SuppressWarnings("unchecked")
	public List<PlayerSupport> getVideoUserSupportList(Long videoId){
		return (List<PlayerSupport>) mybatisBaseDao.selectList("getVideoUserSupportList", videoId);
	}
	
	@SuppressWarnings("unchecked")
	public List<PlayerSupport> getMomentSupportList(Long momentId){
		return (List<PlayerSupport>) mybatisBaseDao.selectList("getMomentSupportList", momentId);
	}
	
}
