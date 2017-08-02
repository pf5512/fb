package com.footballer.rest.api.v2.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.footballer.rest.api.v2.vo.WechatConfig;

@JsonInclude(Include.NON_NULL)
public class WechatConfigResponse extends JsonResponse{
	
	private WechatConfig wechatConfig;

	public WechatConfig getWechatConfig() {
		return wechatConfig;
	}

	public void setWechatConfig(WechatConfig wechatConfig) {
		this.wechatConfig = wechatConfig;
	}

}
