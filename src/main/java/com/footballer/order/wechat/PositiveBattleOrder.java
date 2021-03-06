package com.footballer.order.wechat;

import java.math.BigDecimal;

import com.footballer.order.Order;
import com.footballer.order.OrderLine;
import com.footballer.order.OrderLineType;

public class PositiveBattleOrder extends WechatOrder {
	
	/**
	 * 构造函数
	 * @param systemCountPrice 全场定价
	 */
	public PositiveBattleOrder(BigDecimal systemCountPrice) {
		super(systemCountPrice);
		setOrderType("HostOrder");
	}

	@Override
	protected Order buildOrderLines() {
		BigDecimal halfFieldPrice = getPrice().divide(BigDecimal.valueOf(2));
		//BigDecimal wechatDiscountPrice = BigDecimal.valueOf(20);
		
		OrderLine systemPriceline = new OrderLine(halfFieldPrice.abs(), OrderLineType.DEBIT, "原价");
		//OrderLine wechatDiscountPriceLine = new OrderLine(wechatDiscountPrice.abs(), OrderLineType.CREDIT, "微信订场优惠");
		//OrderLine positiveDiscountPriceLine = new OrderLine(wechatDiscountPrice.abs(), OrderLineType.CREDIT, "主动约战优惠");
		
		getOrderLines().add(systemPriceline);
		//getOrderLines().add(wechatDiscountPriceLine);
		//getOrderLines().add(positiveDiscountPriceLine);
		
		return this;
	}
}
