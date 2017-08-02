package com.footballer.order;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;

public abstract class Order {
	
	public Order(BigDecimal price) {
		this.price = price;
	}
	
	/**
	 * 订单类型
	 */
	private String orderType;
	
	/**
	 * 系统金额
	 */
	private BigDecimal price;
	
	/**
	 * 最终金额
	 */
	private BigDecimal finalPrice;
	
	/**
	 * 微信支付需要的最终价格的金额，finalPrice value(分) * 100 转化为元
	 */
	private String wechatFinalPrice;
	
	/**
	 * 最终显示价格，转换成对应的单元的金额, finalPrice
	 */
	private String displayFinalPrice;
	
	/**
	 * 价格明细
	 */
	private List<OrderLine> orderLines = Lists.newArrayList();
	
	protected abstract Order buildOrderLines();
	
	protected abstract Order buildWechatFinalPrice();
	
	protected abstract Order buildDisplayFinalPrice();

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public String getWechatFinalPrice() {
		return wechatFinalPrice;
	}

	public void setWechatFinalPrice(String wechatFinalPrice) {
		this.wechatFinalPrice = wechatFinalPrice;
	}

	public String getDisplayFinalPrice() {
		return displayFinalPrice;
	}

	public void setDisplayFinalPrice(String displayFinalPrice) {
		this.displayFinalPrice = displayFinalPrice;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
}
