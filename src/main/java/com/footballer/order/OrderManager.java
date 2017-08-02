package com.footballer.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.footballer.order.wechat.BattleOrder;
import com.footballer.order.wechat.FullFieldOrder;
import com.footballer.order.wechat.PositiveBattleOrder;

public class OrderManager {
	
	public static Order createBattleOrder(BigDecimal price) {
		Order order = new BattleOrder(price);
		order.buildOrderLines();
		return order;
	}

	
	public static Order createPositiveOrder(BigDecimal price) {
		Order order = new PositiveBattleOrder(price);
		order.buildOrderLines();
		return order;
	}
	
	public static Order createFullFieldOrder(BigDecimal price) {
		Order order = new FullFieldOrder(price);
		order.buildOrderLines();
		return order;
	}
	
	public static void priceOrder(Order order) {
		if (order != null) {
			List<OrderLine> lines = order.getOrderLines();
			if (!CollectionUtils.isEmpty(lines)) {
				BigDecimal price = BigDecimal.ZERO;
				
				for(OrderLine orderLine : lines) {
					if (orderLine != null) {
						if (OrderLineType.DEBIT.equals(orderLine.getType())) {
							price = price.add(orderLine.getFee().abs());
						} else if (OrderLineType.CREDIT.equals(orderLine.getType())) {
							price = price.subtract(orderLine.getFee().abs());
						}
					}
				}
				
				if (price.compareTo(BigDecimal.ZERO) < 0) {
					throw new RuntimeException("系统价格不能低于零");
				}
				
				order.setFinalPrice(price);
				
				order.buildWechatFinalPrice().buildDisplayFinalPrice();
			}
		}
	}
}
