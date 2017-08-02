package com.footballer.rest.api.v2.client;

import javax.ws.rs.core.MediaType;

import com.footballer.rest.api.v2.Response.ArenaFieldResponse;
import com.footballer.rest.api.v2.Response.FieldReservationResponse;
import com.footballer.rest.api.v2.Response.PlayerResponse;
import com.footballer.rest.api.v2.Response.WechatConfigResponse;
import com.footballer.rest.api.v2.domain.FieldReservationDetail;
import com.footballer.rest.api.v2.vo.Account;
import com.footballer.rest.api.v2.vo.FieldChargeStandard;
import com.footballer.rest.api.v2.vo.WechatConfig;
import com.footballer.util.PropertiesUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ReserveServiceClient {
	
	//TODO: need to put domain into configuration
	private static final String DOMAIN = PropertiesUtil.getDomain()+ "/footballer/api/mobile";
	//private static final String DOMAIN = "http://localhost:8080/footballer/api/mobile";
	private static final String DOMAIN_V_1 = DOMAIN + "/v1";
	private static final String DOMAIN_V_2 = DOMAIN + "/v2";
	private static final String SUFFIX = "/"+PropertiesUtil.getSuffix();
	private static final String GET_RESERVATION_URL = DOMAIN_V_2 + "/reservation-service/getReservationDetail/?" + SUFFIX;
	private static final String GET_FIELD_URL = DOMAIN_V_2 + "/arenaField-service/getFieldStandardDetail/?" + SUFFIX;
	private static final String CREATE_ACCOUNT_BY_OPENID = DOMAIN_V_2 + "/player-service/createPlayerAccountByOpenId" + SUFFIX;
	private static final String GET_ACCOUNT_BY_OPENID = DOMAIN_V_2 + "/player-service/getPlayerAccountByOpenId" + SUFFIX;
	private static final String GET_WECHAT_CONFIG_BY_ARENA_ID = DOMAIN_V_2 + "/reservation-service/findWechatConfigByArenaId" + SUFFIX;
	private static final String FIND_TEAM_NAME_BY_PLAYER_ID = DOMAIN_V_1 + "/team-service/findTeamNameByPlayerId" + SUFFIX;
	private static final String FIND_TEAM_ID_BY_PLAYER_ID = DOMAIN_V_1 + "/team-service/findTeamIdByPlayerId" + SUFFIX;
	//private static final String FOOTBALLER_ARENA_ID = "52";
			
	public static String createPlayerAccountByOpenIdArenaId(String openId,
			String nickName, String imageUrl, String arenaId) {		
		return createPlayerAccountByOpenId(openId, arenaId, nickName, imageUrl);
	}
	
	public static String createPlayerAccountByOpenId(String openId,
			String arenaId, String nickName, String imageUrl) {
		String params = "{\"arenaId\":\""+ arenaId +"\","
			       + "\"name\":\""+nickName+"\","
			       + "\"weChatPicUrl\":\"" + imageUrl + "\","
			       + "\"openId\":\""+openId+"\"}";
		
		Client client = Client.create();
        WebResource webResource = client.resource(CREATE_ACCOUNT_BY_OPENID);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, params);
        
        String playerId = response.getEntity(String.class);
        return playerId;
	}
	
	public static Account getPlayerAccountByOpenid(String openid) {
		String params = "{\"openId\":\""+openid+"\"}";
		Client client = Client.create();
        WebResource webResource = client.resource(GET_ACCOUNT_BY_OPENID);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, params);
        
        PlayerResponse playerResponse = response.getEntity(PlayerResponse.class);
        Account account = playerResponse.getAccount();
        
		return account;
	}
	
	public static WechatConfig getWechatConfigByArenaId(String arenaId) {
		String params = "{\"arenaId\":\"" + arenaId + "\"}";
		Client client = Client.create();
        WebResource webResource = client.resource(GET_WECHAT_CONFIG_BY_ARENA_ID);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, params);
        
        WechatConfigResponse wechatConfigResponse = response.getEntity(WechatConfigResponse.class);
        WechatConfig wechatConfig = wechatConfigResponse.getWechatConfig();
        
		return wechatConfig;
	}
	
	public static String findTeamNameByPlayerId(Long playerId) {
		String params = "{\"playerId\":\"" + playerId + "\"}";
		Client client = Client.create();
		WebResource webResource = client.resource(FIND_TEAM_NAME_BY_PLAYER_ID);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, params);
		
		String teamName = response.getEntity(String.class);
		
		return teamName;
	}
	
	public static String findTeamIdByPlayerId(Long playerId) {
		String params = "{\"playerId\":\"" + playerId + "\"}";
		Client client = Client.create();
		WebResource webResource = client.resource(FIND_TEAM_ID_BY_PLAYER_ID);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, params);
		
		String teamId = response.getEntity(String.class);
		
		return teamId;
	}
	
	public static void main(String[] args) {
		String name = findTeamNameByPlayerId(125L);
		System.out.println(name);
	}
	
	public static FieldChargeStandard getFieldById(String fieldId) {
		WebResource r = buildWebResource(getFieldUrl(fieldId));
		ArenaFieldResponse response = r.get(ArenaFieldResponse.class);
		if (response != null) {
			return response.getFieldChargeStandard();
		}
		return new FieldChargeStandard();
	}
	
	public static FieldReservationDetail getReservationById(String fieldReserveId) {
		WebResource r = buildWebResource(getReserveUrl(fieldReserveId));
		FieldReservationResponse reservation = r.get(FieldReservationResponse.class);
		if (null != reservation) {
			return reservation.getFieldReservationDetail();
		}
		
		return new FieldReservationDetail();
	}
	
	private static WebResource buildWebResource(String url) {
		Client c = Client.create();
		WebResource r = c.resource(url);
		return r;
	}
	
	private static String getReserveUrl(String fieldReserveId) {
		return GET_RESERVATION_URL.replace("?", fieldReserveId);
	}
	
	private static String getFieldUrl(String fieldId) {
		return GET_FIELD_URL.replace("?", fieldId);
	}

}
