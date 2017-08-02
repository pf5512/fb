package com.footballer.rest.api.v2.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v2.vo.WechatConfig;

@JsonInclude(Include.NON_NULL) 
public class WechatResponse  extends JsonResponse {

	private String accessToken;
	private String jsAPITicket;
	private String signature;
	private String errcode;
	private String code;
	private String openId;
	
	private Long playerId;
	private String nickname;   
	private String headimgurl;
	
	//跨服务号的 唯一识别id(存在系统有多个公众号注册的情况，就使用 unionid 来唯一识别用户)
	private String unionid; 
	
	private WechatConfig wechatConfig;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getJsAPITicket() {
		return jsAPITicket;
	}
	public void setJsAPITicket(String jsAPITicket) {
		this.jsAPITicket = jsAPITicket;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public WechatConfig getWechatConfig() {
		return wechatConfig;
	}
	public void setWechatConfig(WechatConfig wechatConfig) {
		this.wechatConfig = wechatConfig;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public Long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}
}
