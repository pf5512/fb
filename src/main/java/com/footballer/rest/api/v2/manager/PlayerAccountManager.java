package com.footballer.rest.api.v2.manager;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.footballer.rest.api.v1.dao.UserDao;
import com.footballer.rest.api.v1.domain.PlayerBaseInfo;
import com.footballer.rest.api.v1.vo.PlayerProperty;
import com.footballer.rest.api.v1.vo.TeamPlayer;
import com.footballer.rest.api.v2.dao.MybatisBaseDao;
import com.footballer.rest.api.v2.domain.ArenaMemberInfo;
import com.footballer.rest.api.v2.domain.ArenaUser;
import com.footballer.rest.api.v2.domain.PlayerWithTeamsInfo;
import com.footballer.rest.api.v2.vo.Account;
import com.footballer.rest.api.v2.vo.Arena;
import com.footballer.rest.api.v2.vo.ArenaOwner;
import com.footballer.rest.api.v2.vo.FootballPlayer;
import com.footballer.rest.api.v2.vo.Tournament;
import com.footballer.rest.api.v2.vo.UserToken;
import com.footballer.rest.api.v2.vo.enumType.UserType;
import com.footballer.util.CommonUtil;
import com.footballer.util.DateUtil;
import com.footballer.util.ObjectUtil;
import com.footballer.util.SecurityUtils;
import com.utils.MD5Util;

import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class PlayerAccountManager {

	@Autowired
	public MybatisBaseDao mybatisBaseDao;

	@Autowired
	public UserDao userDao;

	@Autowired
	public MemberShipManager msMgr;

	@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
	public Long registerPremiumAccount(Long arenaId, String name, Long cellPhone, String weChatNo) {

		String pwd="";
		
		// 后台管理员代付费用户创建账户 用户名手机号, 密码是 手机号后6位
		if(cellPhone.toString().length()>5){
			pwd = cellPhone.toString().substring(5);
		}else{
			pwd = "1";
		}
		pwd = MD5Util.MD5Encode(pwd, null);

		Long accountId = saveAccount(cellPhone.toString(), pwd, null, cellPhone, weChatNo, null);

		// 创建相关手机用户token数据
		String identity = UUID.randomUUID().toString();
		saveUserToken(accountId, identity);

		// 创建相关球员信息
		String imageUrl = "";
		String deviceToken = "";
		Long addressId = null;
		saveFootballPlayer(accountId, name, addressId, imageUrl, deviceToken);
		return accountId;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Long registerWeiChatAccount(Long arenaId, String name, String openId, Long cellPhone, String weChatNo,
			String weChatPicUrl) {

		// 用户名/密码都是微信用户昵称
		Long accountId = saveAccount(name, name, openId, cellPhone, weChatNo, weChatPicUrl);

		// 创建相关手机用户token数据
		String identity = UUID.randomUUID().toString();
		saveUserToken(accountId, identity);

		// 创建相关球员信息
		String imageUrl = "";
		String deviceToken = "";
		Long addressId = null;
		saveFootballPlayer(accountId, name, addressId, imageUrl, deviceToken);

		return accountId;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Long registerWeiChatAccount(String name, String openId, Long cellPhone, String weChatNo, String weChatPicUrl) {

		// 用户名/密码都是微信用户昵称
		Long accountId = saveAccount(name, name, openId, cellPhone, weChatNo, weChatPicUrl);

		// 创建相关手机用户token数据
		String identity = UUID.randomUUID().toString();
		saveUserToken(accountId, identity);

		// 创建相关球员信息
		String imageUrl = "";
		String deviceToken = "";
		Long addressId = null;
		saveFootballPlayer(accountId, name, addressId, imageUrl, deviceToken);

		return accountId;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Long registerWeiChatAccount(String name, String openId, String weChatPicUrl) {

		// 用户名/密码都是微信用户昵称
		Long accountId = saveAccount(name, name, openId, null, null, weChatPicUrl);

		// 创建相关手机用户token数据
		String identity = UUID.randomUUID().toString();
		saveUserToken(accountId, identity);

		// 创建相关球员信息
		String deviceToken = "";
		
		saveWXPlayer(accountId, name, weChatPicUrl, deviceToken);
		return accountId;
	}

	@Transactional(propagation = Propagation.NESTED, isolation = Isolation.READ_COMMITTED)
	public Long saveAccount(String name, String pwd, String openId, Long cellPhone, String weChatNo,
			String weChatPicUrl) {
		Account account = new Account();
		account.setName(name);
		account.setPassword(pwd);
		if (ObjectUtil.isNotNull(openId)) {
			account.setWeChatOpenId(openId);
		}
		if (ObjectUtil.isNotNull(cellPhone)) {
			account.setCellphone(cellPhone);
		}
		if (ObjectUtil.isNotNull(weChatNo)) {
			account.setWeChatNo(weChatNo);
		}
		if (ObjectUtil.isNotNull(weChatPicUrl)) {
			account.setWeChatPicUrl(weChatPicUrl);
		}
		account.setStatus("active");
		Account newAccount = (Account) mybatisBaseDao.insertReturnWithParameter("saveAccount", account);
		return newAccount.getId();
	}

	@Transactional(propagation = Propagation.NESTED, isolation = Isolation.READ_COMMITTED)
	public void saveUserToken(Long accountId, String identity) {
		UserToken userToken = new UserToken();
		userToken.setId(accountId);
		userToken.setIdentify(identity);
		mybatisBaseDao.insert("saveUserToken", userToken);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void saveFootballPlayer(Long accountId, String name, Long addressId, String imageUrl,
			String deviceToken) {
		FootballPlayer player = new FootballPlayer();
		player.setId(accountId);
		player.setNickName(name);
		player.setAddressId(addressId);
		player.setImageUrl(imageUrl);
		player.setDeviceToken(deviceToken);
		CommonUtil.addAuditableAttributes(player);
		mybatisBaseDao.insert("saveFootballPlayer", player);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void saveWXPlayer(Long accountId, String name, String imageUrl,String deviceToken) {
		FootballPlayer player = new FootballPlayer();
		player.setId(accountId);
		player.setNickName(name);
		player.setImageUrl(imageUrl);
		player.setDeviceToken(deviceToken);
		mybatisBaseDao.insert("saveFootballPlayer", player);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void saveArenaUser(Long accountId, Long arenaId, UserType userType, String comments,BigDecimal customPrice) {
		ArenaUser arenaUser = new ArenaUser();
		arenaUser.setPlayerId(accountId);
		arenaUser.setArenaId(arenaId);
		arenaUser.setUserType(userType);
		if(ObjectUtil.isNotNull(comments)){
			arenaUser.setComments(comments);
		}
		if(ObjectUtil.isNotNull(customPrice)){
			arenaUser.setCustomPrice(customPrice);
		}
		arenaUser.setCreateDt(DateUtil.getCurrentDate());
		mybatisBaseDao.insert("saveArenaUser", arenaUser);
	}
	

	public Account getAccountByOpenId(String openId) {
		return (Account) mybatisBaseDao.selectOne("getPlayerByOpenId", openId);
	}

	public Account getAccountById(Long id) {
		return (Account) mybatisBaseDao.selectOne("getAccountById", id);
	}

	public Long findPlayerCellById(Long playerId) {
		return (Long) mybatisBaseDao.selectOne("findPlayerCellById", playerId);
	}

	public List<ArenaUser> getArenaUsersInfo(Long arenaId) {
		List<ArenaUser> arenaUserList = Lists.newArrayList();
		List<ArenaMemberInfo> arenaMemberInfoList = msMgr.getArenaMemebersInfo(arenaId);
		List<ArenaMemberInfo> arenaNonMemberInfoList = msMgr.getArenaNonMemebersInfo(arenaId);

		for (ArenaMemberInfo am : arenaMemberInfoList) {
			ArenaUser au = new ArenaUser();
			au.setArenaId(am.getArenaId());
			au.setPlayerId(am.getPlayerId());
			au.setName(am.getName());
			au.setTeams(am.getTeams());
			au.setCellphone(am.getCellphone());
			au.setWechatNo(am.getWechatNo());
			au.setMember(true);
			au.setTotalCount(am.getTotalCount());
			arenaUserList.add(au);
		}
		for (ArenaMemberInfo anm : arenaNonMemberInfoList) {
			ArenaUser au = new ArenaUser();
			au.setArenaId(anm.getArenaId());
			au.setPlayerId(anm.getPlayerId());
			au.setName(anm.getName());
			au.setTeams(anm.getTeams());
			au.setCellphone(anm.getCellphone());
			au.setWechatNo(anm.getWechatNo());
			au.setMember(false);
			au.setTotalCount(anm.getTotalCount());
			arenaUserList.add(au);
		}

//		Collections.sort(arenaUserList, new Comparator<ArenaUser>() {
//			@Override
//			public int compare(ArenaUser a1, ArenaUser a2) {
//				// 中文排序
//				return Collator.getInstance(java.util.Locale.SIMPLIFIED_CHINESE).compare(a1.getName(), a2.getName());
//			}
//		});
		
		Collections.sort(arenaUserList, new Comparator<ArenaUser>() {
			@Override
			public int compare(ArenaUser a1, ArenaUser a2) {
				// 按订场次数排序
				return a2.getTotalCount()-a1.getTotalCount();
			}
		});

		return arenaUserList;
	}

	public void updateAccountPWDById(Long playerAccountId, String newPwd) {
		// md5 加密
		String MD5_newPwd = SecurityUtils.md5(newPwd);
		Account account = new Account();
		account.setId(playerAccountId);
		account.setPassword(MD5_newPwd);

		mybatisBaseDao.update("updateAccountPWDById", account);
	}

	public void updateWechatImageUrlById(Long accountId, String imageUrl) {
		Account account = new Account();
		account.setId(accountId);
		account.setWeChatPicUrl(imageUrl);

		mybatisBaseDao.update("updateWechatImageUrlById", account);
	}
	
	public void updatePlayerNameAndImgById(Long playerId, String playerName, String playerLogo){
		PlayerBaseInfo player = new PlayerBaseInfo();
		player.setId(playerId);
		player.setNickName(playerName);
		player.setImageUrl(playerLogo);
		mybatisBaseDao.update("updatePlayerNameAndImgById", player);
	}
	
	public void updatePlayerImgById(Long playerId, String playerLogo){
		PlayerBaseInfo player = new PlayerBaseInfo();
		player.setId(playerId);
		player.setImageUrl(playerLogo);
		mybatisBaseDao.update("updatePlayerImgById", player);
	}
	
	public void updatePlayerPropertyById(Long playerId, Date playerBirth, Integer playerHeight, Integer playerWeight){
		PlayerProperty pp = new PlayerProperty();
		pp.setId(playerId);
		pp.setBirth(playerBirth);
		pp.setHeight(playerHeight);
		pp.setWeight(playerWeight);
		
		PlayerProperty eixstPP = (PlayerProperty) mybatisBaseDao.selectOne("findPlayerPropertyByPlayerId", playerId);
		if(ObjectUtil.isNull(eixstPP)){
			pp.setCreateBy(playerId.toString());
			pp.setCreateDt(new Date());
			mybatisBaseDao.insert("savePlayerProperty", pp);
			System.out.println("PlayerAccountManager - updatePlayerPropertyById - 新增用户基本属性:  " +playerId +  " => birth " + playerBirth + "  height: "+ playerHeight + " weight: "+ playerWeight);
		}else{
			pp.setUpdateBy(playerId.toString());
			pp.setUpdateDt(new Date());
			System.out.println("PlayerAccountManager - updatePlayerPropertyById - 修改用户基本属性:  " +playerId +  " => birth " + playerBirth + "  height: "+ playerHeight + " weight: "+ playerWeight);
			mybatisBaseDao.update("updatePlayerPropertyById", pp);
		}
	}
	
	public void updatePlayerTeamsInfoByPlayerTeamsInfo(List<TeamPlayer> list){
		mybatisBaseDao.update("updatePlayerTeamsInfoByPlayerTeamsInfo", list);
	}

	public Long findPlayerAccountIdByCellPhone(Long cell) {
		return (Long) mybatisBaseDao.selectOne("findPlayerAccountIdByCellPhone", cell);
	}

	@SuppressWarnings("unchecked")
	public List<Arena> findArenasOfOwnerByNameAndPwd(String name, String password){
		Account account = new Account();
		account.setName(name);
		String MD5_newPwd = SecurityUtils.md5(password);
		account.setPassword(MD5_newPwd);
		return (List<Arena>) mybatisBaseDao.selectList("findArenasOfOwnerByNameAndPwd", account);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tournament> findTournamentsOfOwnerByNameAndPwd(String name, String password){
		Account account = new Account();
		account.setName(name);
		String MD5_newPwd = SecurityUtils.md5(password);
		account.setPassword(MD5_newPwd);
		return (List<Tournament>) mybatisBaseDao.selectList("findTournamentsOfOwnerByNameAndPwd", account);
	}
	
	public static void main(String[] args) {
		String pass = "1";
		System.out.println("pass:" + SecurityUtils.md5(pass));
	}
	
	public boolean getArenaOwnerByIdAndOpenId(Long arenaId, String openId){
		ArenaOwner ao = new ArenaOwner();
		ao.setArenaId(arenaId);
		ao.setOpenId(openId);
		
		ArenaOwner arenaOwner =(ArenaOwner) mybatisBaseDao.selectOne("findArenaOwnerByIdAndOpenId", ao);
		if(ObjectUtil.isNotNull(arenaOwner)){
			return true;
		}else{
			return false;	
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PlayerWithTeamsInfo> getPlayerAndBasketBallTeamsInfo(Long playerId){
		return (List<PlayerWithTeamsInfo>) mybatisBaseDao.selectList("getPlayerAndBasketBallTeamsInfo", playerId);
	}
	
	@SuppressWarnings("unchecked")
	public List<PlayerWithTeamsInfo> getPlayerAndFootballTeamsInfo(Long playerId){
		return (List<PlayerWithTeamsInfo>) mybatisBaseDao.selectList("getPlayerAndFootballTeamsInfo", playerId);
	}
}
