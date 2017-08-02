package com.footballer.filter.context;

import com.footballer.rest.api.v1.vo.Account;
import com.footballer.rest.api.v1.vo.enumType.AppTokenType;
import com.footballer.rest.api.v2.vo.WechatConfig;

/**
 * Created by ian on 7/27/14.
 */
public class ApplicationContext {

    public ApplicationContext (Account account) {
        this.account = account;
    }

    private Account account;
    
    private AppTokenType appTokenType;
    
    private WechatConfig wechatConfig;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

	public AppTokenType getAppTokenType() {
		return appTokenType;
	}

	public void setAppTokenType(AppTokenType appTokenType) {
		this.appTokenType = appTokenType;
	}

	public WechatConfig getWechatConfig() {
		return wechatConfig;
	}

	public void setWechatConfig(WechatConfig wechatConfig) {
		this.wechatConfig = wechatConfig;
	}
}
