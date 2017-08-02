package com.footballer.rest.api.v2.manager;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.UserShareInfo;
import com.footballer.rest.api.v2.vo.UserComments;
import com.footballer.rest.api.v2.vo.UserShareRecord;
import com.footballer.rest.api.v2.vo.Video;
import com.footballer.util.ObjectUtil;
import com.footballer.util.StringUtil;

@Service
public class UserShareRecordManager {

	Logger logger = LoggerFactory.getLogger(UserShareRecordManager.class);
	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	
	@Autowired
	private TournamentManager tMgr;
	 
	public void saveUserShareRecord(UserShareRecord usr){
		if(usr == null){
			return ;
		}
		if(StringUtil.isEmpty(usr.getUrl()) || usr.getUrl() == "" || ObjectUtil.isNull(usr.getUrl()) ){
			logger.warn("UserShareRecord is valid");
			return ;
		}
		
		@SuppressWarnings("unchecked")
		List<UserShareInfo> result = (List<UserShareInfo>)mybatisBaseDao.selectList("findUserShareRecord", usr);
		if(ObjectUtil.isNotNull(result) && result.size() >0){
			//当前用户已经分享过该页面了，不再记录
			System.out.println("==> 当前用户已经分享过该页面了，不再记录");
			return;
		}else{
			//未分享过
			
			usr.setDate(new Date());
			usr.setUrl(handleIdentityURL(usr.getUrl()));
			
			//通过url 截取拿到 分享的 视频id,如果存在 则保持记录 
			String sMatchId = StringUtil.getURLParamterValue(usr.getUrl(), "matchId"); //获取matchId
			String sVideoId = StringUtil.getURLParamterValue(usr.getUrl(), "videoId");  //获取videoId
			String sMomentId = StringUtil.getURLParamterValue(usr.getUrl(), "momentId");  //获取momentId
			
			if(ObjectUtil.isNotNull(sMatchId)){
				Video video = tMgr.getTournamentMatchVideoInfo(Long.parseLong(sMatchId.trim()));
				if(ObjectUtil.isNotNull(video) && ObjectUtil.isNotNull(video.getId())){
					usr.setVideoId(video.getId());
					mybatisBaseDao.insert("saveUserShareRecord", usr);
					mybatisBaseDao.insert("saveUserShareVideosRecord", usr);
					System.out.println("==> 保存分享记录-当前分享的球员ID: "+ usr.getPlayerId() + "  ==> 视频ID: "+ usr.getVideoId()+ "  ==> Content: "+ usr.getContent() + "  ==> 页面URL: "+ usr.getUrl());
				}else{
					System.out.println("==> 未获取到videoId信息，提取分享信息失败");
					return;
				}
			}else if(ObjectUtil.isNotNull(sVideoId)){
				Long videoId = Long.parseLong(sVideoId);
				usr.setVideoId(videoId);
				mybatisBaseDao.insert("saveUserShareRecord", usr);
				mybatisBaseDao.insert("saveUserShareVideosRecord", usr);
				
				System.out.println("==> 保存分享记录-当前分享的球员ID: "+ usr.getPlayerId() + "  ==> 视频ID: "+ usr.getVideoId()+ "  ==> Content: "+ usr.getContent() + "  ==> 页面URL: "+ usr.getUrl());
			}else if(ObjectUtil.isNotNull(sMomentId)){
				Long momentId = Long.parseLong(sMomentId);
				usr.setMomentId(momentId);
				mybatisBaseDao.insert("saveUserShareRecord", usr);
				mybatisBaseDao.insert("saveUserShareMomentRecord", usr);
				
				System.out.println("==> 保存分享记录-当前分享的球员ID: "+ usr.getPlayerId() + "  ==> 动态ID: "+ usr.getMomentId()+ "  ==> Content: "+ usr.getContent() + "  ==> 页面URL: "+ usr.getUrl());
			}else{
				//非赛事集锦,非动态分享
				mybatisBaseDao.insert("saveUserShareRecord", usr);
				System.out.println("==> 保存分享记录-当前分享的球员ID: "+ usr.getPlayerId() + "  ==> Content: "+ usr.getContent() + "  ==> 页面URL: "+ usr.getUrl());
			}
			//System.out.println("==> User record saved Successfully !!!! + playerId: "+ usr.getPlayerId());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<UserShareInfo> getUserShareRecordByUrlAndContent(String url, String content){
		if(ObjectUtil.isNull(url) ||ObjectUtil.isNull(content)){
			logger.warn("UserShareRecord is valid");
			return null;
		}
		
		url = handleIdentityURL(url);
		System.out.println("==> 获取分享记录-当前分享的页面URL: "+ url);
		System.out.println("==> 获取分享记录-当前分享的Content: "+ content);
		
		UserShareRecord usr = new UserShareRecord();
		usr.setUrl(url);
		usr.setContent(content);
		return (List<UserShareInfo>) mybatisBaseDao.selectList("getUserShareRecordByUrlAndContent", usr);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserShareInfo> getUserShareRecordByVideoId(Long videoId){
		if(ObjectUtil.isNull(videoId)){
			logger.warn("video is valid");
			return null;
		}
		return (List<UserShareInfo>) mybatisBaseDao.selectList("getUserShareRecordByVideoId", videoId);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserShareInfo> getUserShareRecordByMomentId(Long momentId){
		if(ObjectUtil.isNull(momentId)){
			logger.warn("moment is valid");
			return null;
		}
		return (List<UserShareInfo>) mybatisBaseDao.selectList("getUserShareRecordByMomentId", momentId);
	}
	
	public String handleIdentityURL(String url){
		//移除微信分享后微信端加入的参数链接 便于找到对应的分享记录和避免重复
		url = StringUtil.replaceAll(url, "&from=singlemessage", "");
		url = StringUtil.replaceAll(url, "&from=timeline", "");
		url = StringUtil.replaceAll(url, "&isappinstalled=0", "");
				
		//移除不同得入口的访问参数
		url = StringUtil.replaceAll(url, "type=app&", "");
		url = StringUtil.replaceAll(url, "type=web&", "");
		
		return url;
	}
}
