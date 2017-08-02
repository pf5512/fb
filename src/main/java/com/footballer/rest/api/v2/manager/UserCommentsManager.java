package com.footballer.rest.api.v2.manager;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.UserCommentsInfo;
import com.footballer.rest.api.v2.vo.Account;
import com.footballer.rest.api.v2.vo.UserComments;
import com.footballer.util.ObjectUtil;
import com.footballer.util.StringUtil;

@Service
public class UserCommentsManager {

	Logger logger = LoggerFactory.getLogger(UserCommentsManager.class);
	@Autowired
	public MybatisBaseDao mybatisBaseDao;
	 
	public void saveUserComments(UserComments uc){
		if(uc == null){
			return ;
		}
		if(StringUtil.isEmpty(uc.getComments()) || uc.getComments().length() >300){
			logger.warn("comments is valid");
			return ;
		}
		uc.setCommentsTime(new Date());
		mybatisBaseDao.insert("saveUserComments", uc);
	}
	
	public void saveUserCommentsToVideoItem(UserComments uc){
		if(uc == null){
			return ;
		}
		if(StringUtil.isEmpty(uc.getComments()) || ObjectUtil.isNull(uc.getVideoId()) || ObjectUtil.isNull(uc.getPlayerId())){
			logger.warn("comments is valid");
			return ;
		}
		uc.setCommentsTime(new Date());
		System.out.println("==> 新增【视频】评论 playerID: " + uc.getPlayerId() + "  videoId: " +uc.getVideoId()+"  comments: "+ uc.getComments());

		UserComments newUserComment = (UserComments) mybatisBaseDao.insertReturnWithParameter("saveUserComments", uc);
		uc.setId(newUserComment.getId());
		mybatisBaseDao.insert("saveUserCommentsToVideoItem", uc);
	}
	
	
	public void saveUserCommentsToMomentItem(UserComments uc){
		if(uc == null){
			return ;
		}
		if(StringUtil.isEmpty(uc.getComments()) || ObjectUtil.isNull(uc.getMomentId()) || ObjectUtil.isNull(uc.getPlayerId())){
			logger.warn("comments is valid");
			return ;
		}
		uc.setCommentsTime(new Date());
		System.out.println("==> 新增【动态】评论 playerID: " + uc.getPlayerId() + "  momentId: " +uc.getMomentId()+"  comments: "+ uc.getComments());

		UserComments newUserComment = (UserComments) mybatisBaseDao.insertReturnWithParameter("saveUserComments", uc);
		uc.setId(newUserComment.getId());
		mybatisBaseDao.insert("saveUserCommentsToMomentItem", uc);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserCommentsInfo> getVideoUserCommentsList(Long videoId){
		return (List<UserCommentsInfo>) mybatisBaseDao.selectList("getVideoUseCommentsList", videoId);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserCommentsInfo> getMomentCommentsList(Long momentId){
		return (List<UserCommentsInfo>) mybatisBaseDao.selectList("getMomentCommentsList", momentId);
	}	
	
}
