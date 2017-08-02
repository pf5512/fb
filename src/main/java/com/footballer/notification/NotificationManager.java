package com.footballer.notification;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.footballer.concurrent.action.NotificationFactory;
import com.footballer.concurrent.action.SendNotificationAction;
import com.footballer.filter.AppContextHolder;
import com.footballer.rest.api.v1.dao.EventDao;
import com.footballer.rest.api.v1.dao.PlayerDao;
import com.footballer.rest.api.v1.dao.UserDao;
import com.footballer.rest.api.v1.exception.DomainNotFoundException;
import com.footballer.rest.api.v1.vo.PlayerAppToken;
import com.footballer.util.ObjectUtil;

public class NotificationManager {
	
	@Autowired
	public EventDao eventDao;
	
	@Autowired
	public UserDao userDao;
	
	@Autowired
	public PlayerDao playerDao;
	
	private static ExecutorService executorService;
	
	{
        executorService = new ThreadPoolExecutor(5, 500,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r, ("Footballer-ConcurrentTaskExecutor-" + 1));
					}
        	
        });
    }
	
	public void sendSingleMessage(final String message, final Long playerId) throws DomainNotFoundException {
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					if(ObjectUtil.isNotNull(playerId)){						
						List<PlayerAppToken> playerAppTokens = playerDao.findPlayerAllTokens(playerId);
						
						if (!CollectionUtils.isEmpty(playerAppTokens)) {
							for (PlayerAppToken playerAppToken : playerAppTokens) {
								if(ObjectUtil.isNotNull(playerAppToken)) {
									NotificationFactory.send(playerAppToken, message);
								} else {
									throw new DomainNotFoundException("当前用户的DeviceToken不存在，或存在异常");
								}
							}
						}
					}
				} catch(Exception e) {
					System.out.println("NotificationManager send notification error:" + e.getMessage());
				}
			}
		});
	}
	
	public void sendSingleMessage(final Map<String, Object> message, final Long playerId)
			throws DomainNotFoundException {
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					if(ObjectUtil.isNotNull(playerId)){						
						List<PlayerAppToken> playerAppTokens = playerDao.findPlayerAllTokens(playerId);
						
						if (!CollectionUtils.isEmpty(playerAppTokens)) {
							for (PlayerAppToken playerAppToken : playerAppTokens) {
								if(ObjectUtil.isNotNull(playerAppToken)) {
									NotificationFactory.send(playerAppToken, message);
								} else {
									throw new DomainNotFoundException("当前用户的DeviceToken不存在，或存在异常");
								}
							}
						}
					}
				} catch(Exception e) {
					System.out.println("NotificationManager send notification error:" + e.getMessage());
				}
			}
		});
	}
		
	public int sendMessage(final String message, final List<Long> playerIds) {
		
		if (CollectionUtils.isEmpty(playerIds)) return 0;
		
		Set<Long> filteredPlayerIds = filterPlayerId(playerIds);
		
		final ForkJoinPool pool = new ForkJoinPool();
		final SendNotificationAction action = new SendNotificationAction(filteredPlayerIds, message, eventDao);
		
		pool.submit(action);
		
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("NotificationManager start to join task");
					action.join();
				} catch(Exception e) {
					System.out.println("NotificationManager send notification error:" + e.getMessage());
				} finally {
					pool.shutdown();
				}
			}
		});
		
        System.out.println("NotificationManager: send notification message done");
        
        return filteredPlayerIds.size();
	}
	
	private Set<Long> filterPlayerId(List<Long> playerIds) {
		if (!CollectionUtils.isEmpty(playerIds)) {
			Set<Long> filteredPlayerId = new HashSet<Long>(playerIds);
			Long currentPlayerId = AppContextHolder.getContext().getAccount().getId();
			
			if (filteredPlayerId.contains(currentPlayerId)) {
				filteredPlayerId.remove(currentPlayerId);
			}
			
			return filteredPlayerId;
		}
		
		return new HashSet<Long>();
	}
}
