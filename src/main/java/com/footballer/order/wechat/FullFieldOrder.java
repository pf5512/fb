package com.footballer.order.wechat;

import java.math.BigDecimal;

import com.footballer.order.Order;
import com.footballer.order.OrderLine;
import com.footballer.order.OrderLineType;

public class FullFieldOrder extends WechatOrder {

	public FullFieldOrder(BigDecimal systemCountPrice) {
		super(systemCountPrice);
		setOrderType("微信预定全场");
	}

	@Override
	protected Order buildOrderLines() {
		//BigDecimal wechatDiscountPrice = BigDecimal.valueOf(20);
		
		OrderLine systemPriceline = new OrderLine(getPrice(), OrderLineType.DEBIT, "原价");
		//OrderLine wechatDiscountPriceLine = new OrderLine(wechatDiscountPrice, OrderLineType.CREDIT, "微信订场优惠");
		
		getOrderLines().add(systemPriceline);
		//getOrderLines().add(wechatDiscountPriceLine);
		
		return this;
	}
}
