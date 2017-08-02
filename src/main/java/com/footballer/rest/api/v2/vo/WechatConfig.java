package com.footballer.rest.api.v2.vo;

import com.footballer.rest.api.v2.vo.base.GenericVo;

public class WechatConfig extends GenericVo {
	
	private Long arenaId;
	
	private String appid;
	private String accessBackUrl;
	private String wechatAppSecret;
	private String spbillCreateIp;
	private String notifyUrl;
	private String tradeType;
	private String body;
	private String attach;
	private String partnerId;
	private String partnerAppSecret;
	
	private String arenaHeaderTitle;
	private String arenaLogoUrl;
	private String arenaFooterTitle;
	
	private Integer orderAheadWeekNumber;
	private String battleComments;
	
	public Long getArenaId() {
		return arenaId;
	}
	public void setArenaId(Long arenaId) {
		this.arenaId = arenaId;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAccessBackUrl() {
		return accessBackUrl;
	}
	public void setAccessBackUrl(String accessBackUrl) {
		this.accessBackUrl = accessBackUrl;
	}
	public String getWechatAppSecret() {
		return wechatAppSecret;
	}
	public void setWechatAppSecret(String wechatAppSecret) {
		this.wechatAppSecret = wechatAppSecret;
	}
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerAppSecret() {
		return partnerAppSecret;
	}
	public void setPartnerAppSecret(String partnerAppSecret) {
		this.partnerAppSecret = partnerAppSecret;
	}
	public String getArenaHeaderTitle() {
		return arenaHeaderTitle;
	}
	public void setArenaHeaderTitle(String arenaHeaderTitle) {
		this.arenaHeaderTitle = arenaHeaderTitle;
	}
	public String getArenaFooterTitle() {
		return arenaFooterTitle;
	}
	public void setArenaFooterTitle(String arenaFooterTitle) {
		this.arenaFooterTitle = arenaFooterTitle;
	}
	public Integer getOrderAheadWeekNumber() {
		return orderAheadWeekNumber;
	}
	public void setOrderAheadWeekNumber(Integer orderAheadWeekNumber) {
		this.orderAheadWeekNumber = orderAheadWeekNumber;
	}
	public String getBattleComments() {
		return battleComments;
	}
	public void setBattleComments(String battleComments) {
		this.battleComments = battleComments;
	}
	public String getArenaLogoUrl() {
		return arenaLogoUrl;
	}
	public void setArenaLogoUrl(String arenaLogoUrl) {
		this.arenaLogoUrl = arenaLogoUrl;
	}

}
