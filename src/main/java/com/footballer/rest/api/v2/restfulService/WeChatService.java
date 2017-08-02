package com.footballer.rest.api.v2.restfulService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.footballer.rest.api.v1.MediaType;
import com.footballer.rest.api.v1.response.JsonResponse;
import com.footballer.rest.api.v2.Response.WechatResponse;
import com.footballer.rest.api.v2.client.ReserveServiceClient;
import com.footballer.rest.api.v2.manager.CommonManager;
import com.footballer.rest.api.v2.manager.MomentManager;
import com.footballer.rest.api.v2.manager.PlayerAccountManager;
import com.footballer.rest.api.v2.request.QiNiuFetchRequest;
import com.footballer.rest.api.v2.request.WechatRequest;
import com.footballer.rest.api.v2.vo.Account;
import com.footballer.rest.api.v2.vo.Moment;
import com.footballer.rest.api.v2.vo.WechatConfig;
import com.footballer.rest.api.v2.vo.base.AufSports;
import com.footballer.util.HttpUtil;
import com.footballer.util.JacksonUtil;
import com.footballer.util.ObjectUtil;
import com.footballer.util.StringUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.utils.Sha1Util;

@Path("/mobile/v2/wechat-service")
public class WeChatService {

	@Autowired
	private CommonManager cMgr;
	
	@Autowired
	private PlayerAccountManager paMgr;
	
	@Autowired
	private MomentManager mMgr;
	
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

	// 获取微信访问token
	@GET
	@Path("/getWXAccessToken/{arenaId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public WechatResponse getWXAccessToken(@PathParam("arenaId") Long arenaId) {
		WechatResponse response = new WechatResponse();
		String APP_ID="", SECRET="";
		
		String redis_access_token_name = arenaId.toString() + "wx_access_token";
		
    	//redisTemplate.opsForValue().set("wx_access_token", "11", 1, TimeUnit.MINUTES);
    	String wx_access_token = redisTemplate.opsForValue().get(redis_access_token_name);
    	if (ObjectUtil.isNotNull(wx_access_token)) {
    		System.out.println("==> get access_token from redis:" + wx_access_token);
        	response.setAccessToken(wx_access_token);
    		response.setStatus(JsonResponse.SUCCESS);
        	return response;
    	}
		
		//if (ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {
		if (ObjectUtil.isNotNull(arenaId)) {

			if(arenaId == 0){// Auf专用, 无需读取场地id 暂时直接获取
				APP_ID = AufSports.getAppId();
				SECRET = AufSports.getAppSecret();
			}else{
				//获取微信配置根据场地id
				com.footballer.rest.api.v2.vo.WechatConfig wechatConfig = null;
				wechatConfig = ReserveServiceClient.getWechatConfigByArenaId(arenaId.toString());
				
				if (wechatConfig == null) {
					response.setStatus(JsonResponse.ERROR);
					response.setMessage("wechatConfig can not read correctlly");
					return response;
				}
				
				APP_ID = wechatConfig.getAppid();
				SECRET = wechatConfig.getWechatAppSecret();
			}
			
			String getWXAccessTokenurl  = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ APP_ID + "&secret=" + SECRET;
			
			System.out.println("==> getWXAccessTokenurl: " + getWXAccessTokenurl);
			
			try {
				Map<String, Object> jsonMap = JacksonUtil.jsonToMap(HttpUtil.httpClientGet(getWXAccessTokenurl));
				//System.out.println(jsonMap.get("expires_in"));
				System.out.println("==> get access_token:" +jsonMap.get("access_token"));
				
				if (jsonMap.get("errcode") != null)
                {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
					response.setStatus(JsonResponse.ERROR);
					response.setMessage("获取accessToken失败");
                }
                else
                {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
                	
                	redisTemplate.opsForValue().set(redis_access_token_name, jsonMap.get("access_token").toString(), 100, TimeUnit.MINUTES);
                	
                	response.setAccessToken(jsonMap.get("access_token").toString());
    				response.setStatus(JsonResponse.SUCCESS);
                }
				return response;
			} catch (Exception e) {
				System.out.println(e.toString());
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("出现异常，获取accessToken失败");
				return response;
			}
			
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}

	
	//获取微信 jsAPI_ticket
	@GET
	@Path("/getWXjsApiTicket/{accessToken}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public WechatResponse getWXjsApiTicket(@PathParam("accessToken") String accessToken) {
		WechatResponse response = new WechatResponse();

		if (ObjectUtil.isNotNull(accessToken) ) {

			String wechatTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ accessToken + "&type=jsapi";
			
			try {
				Map<String, Object> jsonMap = JacksonUtil.jsonToMap(HttpUtil.httpClientGet(wechatTicketUrl));
				if (Integer.parseInt(jsonMap.get("errcode").toString()) == 0 && ObjectUtil.isNotNull(jsonMap.get("ticket")))
                {
                	response.setJsAPITicket(jsonMap.get("ticket").toString());
                	response.setErrcode(jsonMap.get("errcode").toString());
    				response.setStatus(JsonResponse.SUCCESS);
                }else
                {
					response.setStatus(JsonResponse.ERROR);
					response.setMessage("获取ticket失败");
                }
				return response;
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("出现异常，获取ticket失败");
				return response;
			}
			
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("accessToken can not null");
			return response;
		}
	}	
	
	
	// 获取微信 signature
	@POST
	@Path("/getWXSignature/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public WechatResponse getWXSignature(WechatRequest req) {
		WechatResponse response = new WechatResponse();

		if (ObjectUtil.isNotNull(req.getJsApiTicket()) && ObjectUtil.isNotNull(req.getNoncestr()) && ObjectUtil.isNotNull(req.getTimestamp()) && ObjectUtil.isNotNull(req.getUrl())) {
			
			
			String string1 =  "jsapi_ticket="+req.getJsApiTicket()+
				      					 "&noncestr="+req.getNoncestr()+
				      					 "&timestamp="+req.getTimestamp()+
				      					 "&url="+ req.getUrl();
			
			String signature = Sha1Util.getSha1(string1);
			
			System.out.println("==> string1: "+ string1);
			System.out.println("==> signature: "+ signature); 
					
			response.setSignature(signature);
			response.setStatus(JsonResponse.SUCCESS); 
			return response;
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("params can not null");
			return response;
		}
	}
	
	
	
	@GET
	@Path("/getWechatConfig/{arenaId}/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public WechatResponse getWechatConfig(@PathParam("arenaId") Long arenaId) {
		WechatResponse response = new WechatResponse();

		if (ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {
			WechatConfig wechatConfig = ReserveServiceClient.getWechatConfigByArenaId(arenaId.toString());
			if (wechatConfig == null) {
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("wechatConfig can not read correctlly");
			}else{
				response.setWechatConfig(wechatConfig);
				response.setStatus(JsonResponse.SUCCESS);
			}
			return response;	
		} else {
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("parameters can not null");
			return response;
		}
	}
	
	
	//获取 用户网页授权登录的 accesstoken 和 openid 并提取当前用户信息
	@GET
	@Path("/getWechatOAuthAccessToken/{code}/")
	@Produces(MediaType.APPLICATION_JSON)
	public WechatResponse getWechatOAuthAccessToken(@PathParam("code") String code) {
		System.out.println("=>  Method:(getWechatOAuthAccessToken) code: "+ code);
		
		WechatResponse response = new WechatResponse();
		String appid = AufSports.getAppId();
		String appSecret = AufSports.getAppSecret();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ appid+ 
				     "&secret="+ appSecret+ 
				     "&code="+ code +
				     "&grant_type=authorization_code";
		
		try {
			Map<String, Object> jsonMap = JacksonUtil.jsonToMap(HttpUtil.httpClientGet(url));
			
			System.out.println("=>  jsonMap: "+ jsonMap.get("errcode") + "    ==> access_token: "+ jsonMap.get("access_token"));
			
			if (ObjectUtil.isNotNull(jsonMap.get("access_token")))
            {
				response.setOpenId(jsonMap.get("openid").toString());
				response.setAccessToken(jsonMap.get("access_token").toString());
				
				System.out.println("=>  openid: "+ response.getOpenId() +"  access_token: "+ response.getAccessToken());
				
				if(ObjectUtil.isNotNull(response.getAccessToken()) && ObjectUtil.isNotNull(response.getOpenId())){
					String getWXUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+response.getAccessToken()+
							                  "&openid="+ response.getOpenId() +
							                  "&lang=zh_CN"; 
					Map<String, Object> userJsonMap = JacksonUtil.jsonToMap(HttpUtil.httpClientGet(getWXUserInfoUrl));
					String openId = userJsonMap.get("openid").toString();
					if (ObjectUtil.isNotNull(openId))
		            {
//						response.setUnionid(userJsonMap.get("unionid").toString());
						Account a = paMgr.getAccountByOpenId(openId);
						if(ObjectUtil.isNotNull(a)){
							response.setPlayerId(a.getId());
							response.setNickname(a.getName());   
							response.setHeadimgurl(a.getWeChatPicUrl());
							response.setMessage("find exist player");
							System.out.println("=>  find exist player  => Id:"+ a.getId() + " Name: " + a.getName());
						}else{
							String name = StringUtil.StringFilter(userJsonMap.get("nickname").toString());
							String wechatImgUrl = userJsonMap.get("headimgurl").toString();
							Long playerId = paMgr.registerWeiChatAccount(name, openId, wechatImgUrl);
							response.setPlayerId(playerId);
							response.setNickname(name);
							response.setHeadimgurl(wechatImgUrl);
							response.setMessage("create a new player");
							System.out.println("=>  create a new player:  id: "+ playerId + " name: "+ name);
						}
		            }else{
		            	response.setStatus(JsonResponse.NODATA);
		            	response.setMessage("获取用户信息失败");
		            }			
				}
				
				response.setStatus(JsonResponse.SUCCESS);
            }else
            {
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("获取OAuth AccessToken失败");
            }
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(JsonResponse.ERROR);
			response.setMessage("出现异常，获取OAuth AccessToken失败");
			return response;
		}
	}
	
	// fetchWeixinImageToQiqiu
	@POST
	@Path("/fetchWeixinImageToQiqiu/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public WechatResponse fetchWeixinImageToQiqiu(QiNiuFetchRequest req) {
				
		WechatResponse response = new WechatResponse();
		
		//https://developer.qiniu.com/kodo/sdk/1239/java#rs-fetch
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone2());
		//...其他参数参考类注释
		String accessKey = "cR7-v6zB5KdzA4N4Au1lJwTlo5gRg0U7w7Z-H3r5";
		String secretKey = "7sTfPvkKjHJUojM1LCdzikOowYMfYM3slcsSfFj8";
		String bucket = "aufsports";
		String key =  java.util.UUID.randomUUID().toString();
		String qiniubucketURL = "http://oozwvcwr2.bkt.clouddn.com/";
		//String remoteSrcUrl = "http://devtools.qiniu.com/qiniu.png";
		String remoteSrcUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + req.getAccessToken() + "&media_id=" + req.getMediaId();
		Auth auth = Auth.create(accessKey, secretKey);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		
		System.out.println("MediaId is:" + req.getMediaId());
		
		//抓取网络资源到空间
		try {
		    FetchRet fetchRet = bucketManager.fetch(remoteSrcUrl, bucket, key);
		    
		    Moment moment = new Moment();
		    moment.setContent(req.getComment());
		    moment.setPlayerId(req.getPlayerId());
		    moment.setTournamentId(req.getTournamentId());
		    moment.setTournamentMatchId(req.getMatchId());
		    moment.setUrl(qiniubucketURL + fetchRet.key);
		    moment.setType("pic");
		    mMgr.saveMoment(moment);
		    
		    response.setMessage(qiniubucketURL + fetchRet.key);
		    System.out.println(fetchRet.hash);
		    System.out.println(fetchRet.key);
		    System.out.println(fetchRet.mimeType);
		    System.out.println(fetchRet.fsize);
		} catch (QiniuException ex) {
		    System.err.println(ex.response.toString());
		}
		
		response.setStatus(JsonResponse.SUCCESS);
		return response;
	}
	
	// fetchWeixinImageToQiqiu
	@POST
	@Path("/fetchWeixinImageToAliOSS/{identity}/{appId}/{apptoken}")
	@Produces(MediaType.APPLICATION_JSON)
	public WechatResponse fetchWeixinImageToAliOSS(QiNiuFetchRequest req) {
		WechatResponse response = new WechatResponse();
		
	    String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
	    String accessKeyId = "LTAILCqjNYF6g0lM";
	    String accessKeySecret = "0OTis6hLhARoJG7xZ6GDjWrb62FX6C";
	    String bucketName = "aufsports";
	    String key = java.util.UUID.randomUUID().toString();
		
		String remoteSrcUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + req.getAccessToken() + "&media_id=" + req.getMediaId();
		
		System.out.println("MediaId is:" + req.getMediaId());
		System.out.println("Weixin remoteSrcUrl is:" + remoteSrcUrl);

		byte[] imgBytes = getImageByteFromRemote(remoteSrcUrl);
		String ossImgURL = null;
		
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		try {
	        client.putObject(bucketName, key, new ByteArrayInputStream(imgBytes));
	        
	        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
	        //生成URL
	        URL url = client.generatePresignedUrl(bucketName, key, expiration);
	        if (url != null) {
	        	ossImgURL = url.toString();
	        }
		} catch (OSSException oe) {
			System.out.println("Error Message: " + oe.getMessage());
		} catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            client.shutdown();
        }
		
		//Save image data to DB
		try {		    
		    Moment moment = new Moment();
		    moment.setContent(req.getComment());
		    moment.setPlayerId(req.getPlayerId());
		    moment.setTournamentId(req.getTournamentId());
		    moment.setTournamentMatchId(req.getMatchId());
		    moment.setUrl(ossImgURL);
		    moment.setType("pic");
		    mMgr.saveMoment(moment);
		    
		    response.setMessage(ossImgURL);
		    System.out.println(ossImgURL);
		} catch (Exception ex) {
		    System.err.println(ex.toString());
		}
		
		response.setStatus(JsonResponse.SUCCESS);
		return response;
	}
	
	// 获取微信访问token
		@GET
		@Path("/getMatchArrange/{teams}/{identity}/{appId}/{apptoken}")
		@Produces(MediaType.APPLICATION_JSON)
		public WechatResponse getMatchArrange(@PathParam("teams") String teams) {
			WechatResponse response = new WechatResponse();
			String APP_ID="", SECRET="";
			
			
			
			
			Integer arenaId = 0;
			String redis_access_token_name = arenaId.toString() + "wx_access_token";
			
	    	//redisTemplate.opsForValue().set("wx_access_token", "11", 1, TimeUnit.MINUTES);
	    	String wx_access_token = redisTemplate.opsForValue().get(redis_access_token_name);
	    	if (ObjectUtil.isNotNull(wx_access_token)) {
	    		System.out.println("==> get access_token from redis:" + wx_access_token);
	        	response.setAccessToken(wx_access_token);
	    		response.setStatus(JsonResponse.SUCCESS);
	        	return response;
	    	}
			
			//if (ObjectUtil.isNotNull(arenaId) && cMgr.checkArenaExist(arenaId)) {
			if (ObjectUtil.isNotNull(arenaId)) {

				if(arenaId == 0){// Auf专用, 无需读取场地id 暂时直接获取
					APP_ID = AufSports.getAppId();
					SECRET = AufSports.getAppSecret();
				}else{
					//获取微信配置根据场地id
					com.footballer.rest.api.v2.vo.WechatConfig wechatConfig = null;
					wechatConfig = ReserveServiceClient.getWechatConfigByArenaId(arenaId.toString());
					
					if (wechatConfig == null) {
						response.setStatus(JsonResponse.ERROR);
						response.setMessage("wechatConfig can not read correctlly");
						return response;
					}
					
					APP_ID = wechatConfig.getAppid();
					SECRET = wechatConfig.getWechatAppSecret();
				}
				
				String getWXAccessTokenurl  = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
						+ APP_ID + "&secret=" + SECRET;
				
				System.out.println("==> getWXAccessTokenurl: " + getWXAccessTokenurl);
				
				try {
					Map<String, Object> jsonMap = JacksonUtil.jsonToMap(HttpUtil.httpClientGet(getWXAccessTokenurl));
					//System.out.println(jsonMap.get("expires_in"));
					System.out.println("==> get access_token:" +jsonMap.get("access_token"));
					
					if (jsonMap.get("errcode") != null)
	                {// 错误时微信会返回错误码等信息，{"errcode":40013,"errmsg":"invalid appid"}
						response.setStatus(JsonResponse.ERROR);
						response.setMessage("获取accessToken失败");
	                }
	                else
	                {// 正常情况下{"access_token":"ACCESS_TOKEN","expires_in":7200}
	                	
	                	redisTemplate.opsForValue().set(redis_access_token_name, jsonMap.get("access_token").toString(), 100, TimeUnit.MINUTES);
	                	
	                	response.setAccessToken(jsonMap.get("access_token").toString());
	    				response.setStatus(JsonResponse.SUCCESS);
	                }
					return response;
				} catch (Exception e) {
					System.out.println(e.toString());
					response.setStatus(JsonResponse.ERROR);
					response.setMessage("出现异常，获取accessToken失败");
					return response;
				}
				
			} else {
				response.setStatus(JsonResponse.ERROR);
				response.setMessage("parameters can not null");
				return response;
			}
		}
	
	//Download remote image
	private byte[] getImageByteFromRemote(String remoteURL) {
		URL url = null;
		try {
			url = new URL(remoteURL);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());        
	        ByteArrayOutputStream imgStream = new ByteArrayOutputStream();

	        byte[] buffer = new byte[1024];
	        int length;

	        while ((length = dataInputStream.read(buffer)) > 0) {  
	        	imgStream.write(buffer, 0, length);
	        }
	        
	        byte[] imgBytes = imgStream.toByteArray();
	        dataInputStream.close();
	        imgStream.close();
	        
	        return imgBytes;
		} catch (MalformedURLException e) {
			System.err.println(e.toString());
		} catch (IOException e) {
			System.err.println(e.toString());
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		BegerArrangement(19);
	}
	
	private static void BegerArrangement(int nAmount)
	{
		if (nAmount < 2 || nAmount > 90 )
			return;

		// 队伍数量
		int nFixAmount = nAmount;
		// 最后一支队伍的编号
		int nLastPlayerNo = nAmount;
		// 奇数队伍，补上一支虚拟的队伍，最后一支队伍的编号为0
		
		if ((nAmount % 2) !=0)
		{
			++nFixAmount;
			nLastPlayerNo = 0;
		}
		// 轮数
		int nMaxRound = nFixAmount-1;
		int nHalfAmount = nFixAmount / 2;

		// 移动的间隔
		int nStep = nFixAmount <= 4 ? 1 : (nFixAmount - 4) / 2 + 1;

		int nRound = 1;
		int nFirstPlayerPos = 1;
		int nLastPlayerPos = 1;
		
		int result[][] = new int[100][200];
		
		while (nRound <= nMaxRound)
		{
			// 每次最后一个玩家的位置需要左右对调
			nLastPlayerPos = nFixAmount + 1 - nLastPlayerPos;

			if (nRound == 1)
				nFirstPlayerPos = 1;
			else
				nFirstPlayerPos = (nFirstPlayerPos + nStep) % (nFixAmount - 1);

			if (nFirstPlayerPos == 0)
				nFirstPlayerPos = nFixAmount - 1;

			if (nFirstPlayerPos == nLastPlayerPos)
				nFirstPlayerPos = nFixAmount + 1 - nLastPlayerPos;

			for (int i = 1; i <= nHalfAmount; ++i)
			{
				int nPos[] = { i, nFixAmount - i + 1 };
				int nPlayer[] = { 0, 0 };
				for (int j = 0; j < 2; ++j)
				{
					if (nPos[j] == nLastPlayerPos)
						nPlayer[j] = nLastPlayerNo;
					else if (nPos[j] < nFirstPlayerPos)
						nPlayer[j] = nFixAmount - nFirstPlayerPos + nPos[j];
					else
						nPlayer[j] = nPos[j] - nFirstPlayerPos + 1;

					result[i-1][(nRound-1)*2+j] = nPlayer[j];
				}
			}

			++nRound;
		}

		for (int i = 1; i <= nMaxRound; ++i)
		{
			if( i == 1 )
				System.out.println("r: " + i);
			else
				System.out.println("r" + i);
		}
		
		System.out.println("\n");
		
		
		String[] resultAgenda = new String[nMaxRound];
		
		for (int j = 0; j < nMaxRound; ++j) {
			String tresult = "";
			for (int i = 0; i < nHalfAmount; ++i)
			{
				if(tresult=="") {
					tresult = result[i][j*2] + "-" + result[i][j*2+1];
				} else {
					tresult = tresult + "," + result[i][j*2] + "-" + result[i][j*2+1];
				}
				System.out.println( result[i][j*2] + "-" + result[i][j*2+1]);
			}
			resultAgenda[j] = tresult;
			System.out.println(tresult);
		}
		System.out.println("\n");
		
		for (int j = 0; j < nMaxRound; ++j) {
			System.out.println(resultAgenda[j]);
		}
		
		System.out.println("\n\n");
	}
}
