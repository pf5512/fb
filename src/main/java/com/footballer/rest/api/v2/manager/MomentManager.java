package com.footballer.rest.api.v2.manager;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.vo.Moment;
import com.footballer.util.DateUtil;
import com.footballer.util.ObjectUtil;
import com.footballer.util.StringUtil;

@Service
public class MomentManager {

	Logger logger = LoggerFactory.getLogger(MomentManager.class);
	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	public boolean saveMoment(Moment moment){
		if(moment == null 
		   || StringUtil.isEmpty(moment.getUrl()) || moment.getUrl() == "" || ObjectUtil.isNull(moment.getUrl()) 
		   || ObjectUtil.isNull(moment.getTournamentId()) 
		   || ObjectUtil.isNull(moment.getPlayerId()) )
		{
			logger.warn("moment parameter is valid");
			return false;
		}
		moment.setDate(DateUtil.getCurrentDate());
		//moment.setDisplayLongDate(DateUtil.formatLongDate(new Date()));
		mybatisBaseDao.insert("saveMoment", moment);
		System.out.print("==> 存入Moment内容: " +moment.getContent());
		System.out.print("==> 存入Momenturl: " +moment.getUrl());
		return true;
	}
}
