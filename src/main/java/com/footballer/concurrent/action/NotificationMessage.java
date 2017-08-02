package com.footballer.concurrent.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;


public final class NotificationMessage {
	
	public static final String APPROVED = "APPROVED";
	public static final String REJECTED = "REJECTED";
	
	private static final String BEFORE_GAME_SIGN_MESSAGE = "您已完成赛前到场签到";
	private static final String AFTER_GAME_SIGN_ESSAGE = "您已完成赛后到场签到";
	
	private static final String BEFORE_GAME_SIGN_TYPE = "赛前签到";
	private static final String AFTER_GAME_SIGN_TYPE = "赛后签到";
	private static final String FRIEND_INVITATION_TYPE = "好友邀请";
	private static final String FRIEND_INVITATION_COMPLETE_TYPE = "好友邀请完成";
	private static final String JOIN_TEAM_TYPE = "邀请入队";
	private static final String JOIN_TEAM_COMPLETED_TYPE = "邀请入队完成";
	private static final String APPLY_TEAM_INVITATION_TYPE = "申请入队";
	private static final String APPLY_TEAM_INVITATION_COMPLETED_TYPE = "申请入队完成";
	
	private NotificationMessage() {}
	
	public static void main(String[] args) {
		System.out.println(getAfterGameSignMessageMap(1L, 2L, "test"));
	} 
	
	/**
	 * 赛前签到
	 * @param eventId
	 * @param playerId
	 * @return
	 */
	public static Map<String, Object> getBeforeGameSignMessage(Long eventId, Long playerId) {
		String message = String.format(
				"{target: eventDetail, eventId: %s, playerIdTo: %s, content: %s, type: %s}",
				eventId, playerId, BEFORE_GAME_SIGN_MESSAGE, BEFORE_GAME_SIGN_TYPE);
		return toMap(message);
	}
		
	/**
	 * 赛后签到
	 * @param eventId
	 * @param playerId
	 * @param content
	 * @return
	 */
	public static Map<String, Object> getAfterGameSignMessageMap(Long eventId, Long playerId,
			String content) {
		String string = String.format(
				"{target: eventDetail, eventId: %s, playerIdTo: %s, content: %s, type: %s}",
				eventId, playerId, AFTER_GAME_SIGN_ESSAGE, AFTER_GAME_SIGN_TYPE);			
		return toMap(string);
	}
	
	private static Map<String, Object> toMap(String message) {
		Map<String, Object> map = new HashMap<>();
		
		JSONObject json = new JSONObject(message);
		@SuppressWarnings("unchecked")
		Set<String> keys = json.keySet();
		for (String key : keys) {
			map.put(key, json.get(key));
		}		
		return map;
	}
	
	/**
	 * 好友邀请
	 * @param playerIdFrom
	 * @param playerIdTo
	 * @return
	 */
	public static Map<String, Object> getFriendInvitationMessage(Long playerIdFrom,
			Long playerIdTo, String content) {
		String message = String
				.format("{target:friendRequest, playerIdFrom: %s, playerIdTo: %s, content: %s, type: %s}",
						playerIdFrom, playerIdTo, content, FRIEND_INVITATION_TYPE);
		return toMap(message);
	}
	
	/**
	 * 好友邀请操作完成
	 * @param status
	 * @param playerIdFrom
	 * @param playerIdTo
	 * @param content
	 * @return
	 */
	public static Map<String, Object> getFriendInvitationCompleted(String status, Long playerIdFrom,
			Long playerIdTo, String content) {
		String message = String
				.format("{target: friendRequestDone, status: %s, playerIdFrom: %s, playerIdTo: %s, content: %s, type: %s}",
						status, playerIdFrom, playerIdTo, content, FRIEND_INVITATION_COMPLETE_TYPE);
		return toMap(message);
	}
	
	/**
	 * 入队邀请
	 * @param teamId
	 * @param playerIdFrom
	 * @param playerIdTo
	 * @param content
	 * @return
	 */
	public static Map<String, Object> getJoinTeamInvitation(Long teamId, Long playerIdFrom,
			Long playerIdTo, String content) {
		String message = String
				.format("{target:teamInvitation, teamId: %s, playerIdFrom: 123, playerIdTo: 456, content: %s, type: %s}",
						teamId, playerIdFrom, playerIdTo, content, JOIN_TEAM_TYPE);
		return toMap(message);
	}
	
	/**
	 * 入队邀请操作完成
	 * @param status
	 * @param playerIdFrom
	 * @param playerIdTo
	 * @param content
	 * @return
	 */
	public static Map<String, Object> getJoinTeamInvitationCompleted(String status, Long teamId,
			Long playerIdTo, String content) {
		String message = String
				.format("{target: teamInvitationDone, status: %s, teamId: %s, playerIdTo: %s, content: %s, type: %s}",
						status, teamId, playerIdTo, content, JOIN_TEAM_COMPLETED_TYPE);
		return toMap(message);
	}
	
	/**
	 * 入队申请
	 * @param teamId
	 * @param playerIdFrom
	 * @param playerIdTo
	 * @param content
	 * @return
	 */
	public static Map<String, Object> getApplyTeamInvitation(Long teamId, Long playerIdTo, String content) {
		String message = String
				.format("{target:teamApplication, teamId: %s, playerIdTo: %s, content: %s, type: %s}",
						teamId, playerIdTo, content, APPLY_TEAM_INVITATION_TYPE);
		return toMap(message);
	}
	
	/**
	 * 入队申请操作完成
	 * @param status
	 * @param playerIdFrom
	 * @param playerIdTo
	 * @param content
	 * @return
	 */
	public static Map<String, Object> getApplyTeamInvitationCompleted(String status, Long teamId,
			Long playerIdTo, String content) {
		String message =String
				.format("{target: teamApplicationDone, status: %s, teamId: %s, playerIdTo: %s, content: %s, type: %s}",
						status, teamId, playerIdTo, content, APPLY_TEAM_INVITATION_COMPLETED_TYPE);
		return toMap(message);
	}
	
}
