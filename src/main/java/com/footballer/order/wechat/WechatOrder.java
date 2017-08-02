package com.footballer.order.wechat;

import java.math.BigDecimal;

import com.footballer.order.Order;

public abstract class WechatOrder extends Order {
	
	public WechatOrder(BigDecimal systemCountPrice) {
		super(systemCountPrice);
	}

	@Override
	protected Order buildWechatFinalPrice() {
		BigDecimal fenToYuanUnit = BigDecimal.valueOf(100);		
		BigDecimal feeOfYuan = getFinalPrice().multiply(fenToYuanUnit);
		setWechatFinalPrice(String.valueOf(feeOfYuan.intValue()));
		return this;
	}
	
	protected Order buildDevWechatFinalPrice() {
		BigDecimal fenToYuanUnit = BigDecimal.valueOf(1);
		BigDecimal feeOfYuan = getFinalPrice().multiply(fenToYuanUnit);
		BigDecimal feeOfFen = feeOfYuan.divide(BigDecimal.valueOf(100));
		setWechatFinalPrice(String.valueOf(feeOfFen.intValue()));
		
		return this;
	}
	
	@Override
	protected Order buildDisplayFinalPrice() {
		setDisplayFinalPrice(String.valueOf(getFinalPrice().intValue()));
		
		return this;
	}

}
