package com.footballer.order;

import java.math.BigDecimal;

public class OrderLine {
	private BigDecimal fee;
	private OrderLineType type;
	private String description;

	public OrderLine(BigDecimal fee, OrderLineType type, String description) {
		this.fee = fee;
		this.type = type;
		this.description = description;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public OrderLineType getType() {
		return type;
	}

	public void setType(OrderLineType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
