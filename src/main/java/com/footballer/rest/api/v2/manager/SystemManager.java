package com.footballer.rest.api.v2.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.vo.AccessStats;
import com.footballer.util.ObjectUtil;
import com.footballer.util.StringUtil;

@Service
public class SystemManager {

	Logger logger = LoggerFactory.getLogger(SystemManager.class);
	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	public void saveAccessRecord(AccessStats as){
		if(as == null || StringUtil.isEmpty(as.getUrl()) || as.getUrl() == "" || ObjectUtil.isNull(as.getUrl()) ){
			logger.warn("AccessStats is valid");
			return ;
		}
		System.out.print("==> 访问URL: " +as.getUrl());
		
		AccessStats findResult = (AccessStats) mybatisBaseDao.selectOne("findAccessStatsByUrlAndDate", as);
		if(ObjectUtil.isNotNull(findResult)){
			mybatisBaseDao.update("autoIncrementAccessRecord", findResult.getId());
			System.out.println("=> 直接自增");
			return;
		}else{
			mybatisBaseDao.insert("saveAccessRecord", as);
			System.out.println("=> 新增一条记录");
		}
	}
//	
//	public String handleIdentityURL(String url){
//		//移除微信分享后微信端加入的参数链接 便于找到对应的分享记录和避免重复
//		url = StringUtil.replaceAll(url, "&from=singlemessage", "");
//		url = StringUtil.replaceAll(url, "&from=timeline", "");
//		url = StringUtil.replaceAll(url, "&isappinstalled=0", "");
//				
//		//移除不同得入口的访问参数
//		url = StringUtil.replaceAll(url, "type=app&", "");
//		url = StringUtil.replaceAll(url, "type=web&", "");
//		
//		return url;
//	}
}
