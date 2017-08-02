package com.footballer.rest.api.v1.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.dao.InvitationDao;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.dao.TeamDao;
import com.footballer.rest.api.v1.dao.UserDao;
import com.footballer.rest.api.v1.domain.ClientTeam;
import com.footballer.rest.api.v1.domain.FootballPlayer;
import com.footballer.rest.api.v1.domain.FootballTeam;
import com.footballer.rest.api.v1.domain.PlayerTeamSummary;
import com.footballer.rest.api.v1.domain.PlayerWithStatus;
import com.footballer.rest.api.v1.domain.User;
import com.footballer.rest.api.v1.domain.UserWithStatus;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.response.ClientPlayerBalanceLine;
import com.footballer.rest.api.v1.result.PlayerActiviyWithChargeResult;
import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.Player;
import com.footballer.rest.api.v1.vo.PlayerBalanceLine;
import com.footballer.rest.api.v1.vo.PlayerDepositAccount;
import com.footballer.rest.api.v1.vo.PlayerPlayerInvitation;
import com.footballer.rest.api.v1.vo.PlayerProperty;
import com.footballer.rest.api.v1.vo.Team;
import com.footballer.rest.api.v1.vo.enumType.FeeType;
import com.footballer.rest.api.v1.vo.enumType.InvitationResponseType;
import com.footballer.rest.api.v1.vo.enumType.PayMethod;
import com.footballer.rest.api.v1.vo.enumType.PlayerTeamActivityType;
import com.footballer.rest.api.v2.manager.PlayerBalanceLineManager;
import com.footballer.rest.api.v2.result.PlayerFeeOfEventResult;
import com.footballer.util.ObjectUtil;
import com.footballer.util.SecurityUtils;
import com.footballer.util.StringUtil;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * Created by ian on 8/21/14.
 */

@Service
public class UserManager {

    @Autowired
    private UserDao userDao;
    
	@Autowired
	public PlayerDao playerDao;
    
    @Autowired
    private TeamDao teamDao;
    
    @Autowired
    private InvitationDao invitationDao;
    
    @Autowired
    private PlayerBalanceLineManager pblMgr;
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public User createUser(String name, String password)
        throws Exception {

        User user = new User();

        if (!StringUtils.hasText(name)) {
            throw new Exception("用户名不能为空!");
        }

        List<Account> accounts = userDao.findUsersByName(name);
        if (!CollectionUtils.isEmpty(accounts)) {
            throw new Exception("用户名: " + name + ", 已经被注册。");
        }

        Account account = new Account(name, SecurityUtils.md5(password));
        // TODO: enum status
        account.setStatus("active");

        Long accountId = userDao.save(account);
        
        // create a default player
        Player player = new Player();
        player.setAccount(account);
        player.setNickName(name);
        
        userDao.createUserPlayer(player);
        
        user.setAccountId(accountId);
        user.setAccountStatus(account.getStatus());
        user.setAccountIdentity(account.getUserToken().getIdentity());

        return user;
    }

    public User findUserByNameAndPassword(String name, String password)
        throws DomainNotFoundException{
        List<Account> accounts = userDao.findUsersByNameAndPassword(name, SecurityUtils.md5(password));

        if (CollectionUtils.isEmpty(accounts)) {
            throw new DomainNotFoundException("找不到账户信息");
        }

        Account account = accounts.get(0);

        Player player = userDao.findById(Player.class, account.getId());

        User user = buildUser(account, player);

        return user;
    }

    public static User buildUser(Account account, Player player) {
        User user = new User();

        // build user
        if(account != null) {
        	user.setAccountId(account.getId());
            user.setAccountStatus(account.getStatus());
            user.setAccountIdentity(account.getUserToken().getIdentity());
        }
        

        if (player != null) {
            user.setPlayerId(player.getId());
            user.setPlayerNickName(player.getNickName());
            //user.setIsLeft(player.getIsLeft());
            user.setPeopleImgUrl(player.getImageUrl());
            
            if (null != player.getPlayerProperty()) {
            	PlayerProperty pp = player.getPlayerProperty();
            	user.setBirth(pp.getBirth());
                user.setHeavyFoot(pp.getHeavyFoot());
                user.setHeight(pp.getHeight());
                user.setWeight(pp.getWeight());
            }
            
            if (null != player.getAddress()) {
            	user.setPeopleAddress(player.getAddress().getName());
                user.setPeopleAddressId(player.getAddress().getId());
                user.setAddressLatitude(player.getAddress().getLatitude());
                user.setAddressLongitude(player.getAddress().getLongitude());
            }            
        }

        return user;
    }

    public List<FootballTeam> findTeamsByPlayerId(Long playerId)
        throws DomainNotFoundException{
        Player player = playerDao.findPlayer(playerId);

        if (null == player) {
            throw new DomainNotFoundException("找不到球员信息");
        }

        List<Team> teams = teamDao.findTeamByActivePlayer(playerId);

        if (CollectionUtils.isEmpty(teams)) {
            throw new DomainNotFoundException("找不到球队信息");
        }

        List<FootballTeam> footballTeamList = new ArrayList<>();
        
        List<FootballTeam> ownerTeams = new ArrayList<>();
        List<FootballTeam> joinedTeams = new ArrayList<>();

        for (Team team : teams) {
            FootballTeam footballTeam = new FootballTeam();
            ClientTeam clientTeam = new ClientTeam();
            BeanUtils.copyProperties(team, clientTeam, new String[]{"fields", "players"});
            footballTeam.setTeam(clientTeam);
            
            if (playerId.equals(clientTeam.getCaptainUserId())) {
            	ownerTeams.add(footballTeam);
            } else {
            	joinedTeams.add(footballTeam);
            }
        }
        
        Collections.sort(ownerTeams);
        Collections.sort(joinedTeams);
        
        footballTeamList.addAll(ownerTeams);
        footballTeamList.addAll(joinedTeams);

        return footballTeamList;
    }

    public FootballPlayer findFootballPlayerById(Long playerId) {
        
    	Account account = userDao.findById(Account.class, playerId);
        Player player = userDao.findById(Player.class, account.getId());

        User user = buildUser(account, player);
        
        List<FootballTeam> teams = null;
		try {
			teams = findTeamsByPlayerId(player.getId());
		} catch (DomainNotFoundException e) {
			e.printStackTrace();
		}
		
		Account currentAccount = AppContextHolder.getContext().getAccount();
		Long curretUserId = currentAccount.getId();
		String friendStatusOfAccount = InvitationResponseType.OWNER.toString() ;
		if(!curretUserId.equals(playerId)){
			boolean isTeamMemberOfAccunt = false;
			List<Long> joinedAccountTeamIds =Lists.newArrayList();
			
			PlayerPlayerInvitation ppi = invitationDao.findPlayerTOPlayer(curretUserId, playerId);
			if(ObjectUtil.isNotNull(ppi))
			{
				String status = ppi.getStatus().toString();
				if(status.equals("ACCEPT")){
					friendStatusOfAccount = "friends";
				}else if(status.equals("APPLY"))
				{ 
					if(ppi.getFromPlayerId().equals(curretUserId)){
						friendStatusOfAccount = "you-invited-no-reply";
					}else{
						friendStatusOfAccount = "he-invited-no-reply";
					}
				}else if(status.equals("REFUSE"))
				{	
					if(ppi.getFromPlayerId().equals(curretUserId)){
						friendStatusOfAccount = "you-invited-refuse";
					}else{
						friendStatusOfAccount = "he-invited-refuse";
					}	
				}
			}else {
				friendStatusOfAccount = "not-friend";
			}
			
			List<Team> teamsOfAccount = teamDao.findTeamsAsCaptainByPlayerId(currentAccount.getId());
			for(Team t: teamsOfAccount){
				if(teamDao.isPlayerInTeam(t.getId(),playerId)){
					joinedAccountTeamIds.add(t.getId());
				}
			}
			if(joinedAccountTeamIds.size()>0){
				isTeamMemberOfAccunt = true;
			}
			return new FootballPlayer(user, teams, friendStatusOfAccount, isTeamMemberOfAccunt , joinedAccountTeamIds);
		}else{
			friendStatusOfAccount = "yourself";
			return new FootballPlayer(user, teams, friendStatusOfAccount);
		}
    }
    
    public PlayerProperty findPlayerPropertyByPlayerId(Long playerId) {
    	PlayerProperty playerProperty = playerDao.findPlayerProperty(playerId);
    	return playerProperty;
    }
    
    //TODO 需要 优化  做大了 一次性拿所有得playerlist 是很耗时间的    
    /**
     * 获取当前用户的通讯录列表中，未注册的APP的手机号列表
     * @param cellphones   
     * @param cellPhoneList  当前用户通讯录列表
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public List<String> findCurrentUsersAddressListsUnRegisterUserList(String[] cellphones,List<String> cellPhoneList)
    {
    	List<String> userCellPhoneList = userDao.getAllUsersCellPhones();
    	for(String cell : userCellPhoneList)
    	{
    		if(StringUtil.isHave(cellphones, cell)){
			cellPhoneList.remove(cell);
		}
		}
		return cellPhoneList;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public List<UserWithStatus> findCurrentUsersAddressListFriends(List<String> cellPhoneList)
    {
    	Account currentAccount = AppContextHolder.getContext().getAccount();
    	List<UserWithStatus> UserWithStatusList = Lists.newArrayList();
    	UserWithStatusList = userDao.findCurrentUsersAddressListFriends(currentAccount.getId(),cellPhoneList);
		return UserWithStatusList;
    }
    
    /**
     * 更新单个球员的资金账户余额
     * @param playerId
     * @param teamId
     * @param feeType
     * @param payMethod
     * @param amount
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public PlayerDepositAccount updatePlayerBalances(Long playerId, Long teamId,
			FeeType feeType, PayMethod payMethod, BigDecimal amount, String comment, Long eventId) {
    	
    	if (FeeType.DEBIT.equals(feeType)) {
    		amount = amount.abs();
    	}
    	
    	if (FeeType.CREDIT.equals(feeType)) {
    		amount = BigDecimal.ZERO.subtract(amount);
    	}

		PlayerDepositAccount account = userDao.updatePlayerBalances(playerId, teamId, amount);
		
		if (null != account) {
			userDao.createPlayerBalancesLine(account, feeType, payMethod, amount.abs(), comment, eventId);
		}			
		
		return account;
    }
    
    /**
     * 获取球员的资金明细信息
     * 
     * @param playerId
     * @param teamId
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<PlayerBalanceLine> findPlayerBalanceLines(Long playerId, Long teamId) {
    	return userDao.findPlayerBalanceLines(playerId, teamId);
    }
    
    /**
     * 获取一场比赛所有人的缴费/欠费记录
     * 
     * @param Long eventId
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<ClientPlayerBalanceLine> findPlayerBalanceLines(Long eventId) {
    	
    	List<PlayerBalanceLine> lines = userDao.findPlayerBalanceLines(eventId);
    	List<ClientPlayerBalanceLine> clientLines = Lists.newArrayList();
    	
    	if (!CollectionUtils.isEmpty(lines)) {
    		clientLines = new ArrayList<>();
    		for (PlayerBalanceLine line : lines) {
    			if (FeeType.DEBIT.equals(line.getFeeType())) {
    				clientLines.add(new ClientPlayerBalanceLine(line));
    			}
    		}
    	}
    	return clientLines;
    }
    
    /**
     * 获取球员的资金明细信息
     * 
     * @param playerId
     * @param teamId
     */
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public List<PlayerBalanceLine> findPlayerBalanceLines(Long playerId, Long teamId, Long eventId) {
    	return userDao.findPlayerBalanceLines(playerId, teamId);
    }
    
    /**
     * 获取指定球员的所有球队的球员列表
     * @param playerId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public List<FootballTeam> findPlayerTeamAllPlayers(Long playerId) {
    	List<FootballTeam> teams = new ArrayList<>();
    	
    	List<PlayerTeamSummary> summaries = userDao.findPlayerTeamSummary(playerId);
    	
    	if (!CollectionUtils.isEmpty(summaries)) {
    		Multimap<Long, FootballPlayer> teamPlayerMap = LinkedHashMultimap.create();
    		Map<Long, PlayerTeamSummary> teamNameMap = new LinkedHashMap<Long, PlayerTeamSummary>();
    		
    		for (PlayerTeamSummary summary : summaries) {
    			Long teamId = summary.getTeamId();
    			teamPlayerMap.put(teamId,
						FootballPlayer.build(summary.getPlayerId(), summary.getPlayerName(), summary.getImageUrl()));
    			teamNameMap.put(teamId, summary);
    		}
    		
    		for (Map.Entry<Long, PlayerTeamSummary> entry : teamNameMap.entrySet()) {
    			PlayerTeamSummary summary = entry.getValue();
    			Long teamId = entry.getKey();
    			
    			FootballTeam team = buildFootballTeam(teamId, summary.getTeamCaptainId(), summary.getTeamName(), summary.getTeamLogo());
    			
    			Collection<FootballPlayer> players = teamPlayerMap.get(teamId);
    			team.setFootballPlayers((Set<FootballPlayer>) players);
    			
    			teams.add(team);
    		}
    	}
    	
    	return teams;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void updatePlayerTeamActivity(Long playerId,
			Long teamId, Long eventId, Integer delta, PlayerTeamActivityType type) {
    	
    	Account currentAccount = AppContextHolder.getContext().getAccount();
    	Long accountId = currentAccount.getId();
    	
    	Team team = teamDao.findById(Team.class, teamId);
    	
    	if (null != team) {
			if (accountId.equals(team.getCaptainUserId())
					|| accountId.equals(team.getViceCaptainUserId())) {
				teamDao.updatePlayerTeamSummaryByType(playerId, teamId, delta, type);
		    	teamDao.createPlayerTeamActivity(playerId, teamId, eventId, type);
			}
    	} else {
    		throw new RuntimeException("Can not find team by teamid:" + teamId);
    	}
    }
    
	private FootballTeam buildFootballTeam(Long teamId, Long captainUserId,String teamName, String teamLogo) {
		FootballTeam team = new FootballTeam();
		ClientTeam clientTeam = new ClientTeam();
		clientTeam.setId(teamId);
		clientTeam.setName(teamName);  
		clientTeam.setLogo(teamLogo);
		clientTeam.setCaptainUserId(captainUserId);
		team.setTeam(clientTeam);
		return team;
	}
	
    public List<PlayerActiviyWithChargeResult> findPlayerActivityAndChargeFee(List<PlayerWithStatus> playerStatusList,Long eventId) {
    	List<PlayerActiviyWithChargeResult> playerActivityAndChargeList = Lists.newArrayList();
    	List<PlayerFeeOfEventResult> playerFeeList = pblMgr.findAllPlayerChargeFeeOfEventByEventId(eventId);
    	
    	for(PlayerWithStatus playerWithStatus: playerStatusList){
    		PlayerActiviyWithChargeResult result = new PlayerActiviyWithChargeResult();
    		
    		result.setPlayerId(playerWithStatus.getId());
    		result.setPlayerName(playerWithStatus.getName());
    		result.setPlayerImg(playerWithStatus.getImageUrl());
    		result.setPlayerTeamActivityType(playerWithStatus.getPlayerTeamActivityType());
    		for(PlayerFeeOfEventResult playerFee: playerFeeList){
    			if(playerFee.getPlayerID().toString().equals(playerWithStatus.getId().toString())){
    				result.setChargeFee(playerFee.getFee());
    				break;
    			} 
    		}
    		playerActivityAndChargeList.add(result);
    	}
    	return playerActivityAndChargeList;
    }
}
