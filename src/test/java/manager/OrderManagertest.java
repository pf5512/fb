package manager;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.footballer.order.Order;
import com.footballer.order.OrderLineType;
import com.footballer.order.OrderManager;
import com.google.gson.Gson;


public class OrderManagertest {
		
	@Test
	public void testFullFieldOrder() {
		BigDecimal systemCountPrice = BigDecimal.valueOf(300);
		Order order = OrderManager.createFullFieldOrder(systemCountPrice);
		OrderManager.priceOrder(order);
		Assert.assertEquals(BigDecimal.valueOf(280), order.getFinalPrice());
		Assert.assertEquals(BigDecimal.valueOf(280*100).toString(), order.getWechatFinalPrice());
		Assert.assertEquals(BigDecimal.valueOf(280).toString(), order.getDisplayFinalPrice());
		
		Assert.assertEquals(systemCountPrice, order.getOrderLines().get(0).getFee());
		Assert.assertEquals(OrderLineType.DEBIT, order.getOrderLines().get(0).getType());
		Assert.assertEquals("原价", order.getOrderLines().get(0).getDescription());
		
		Assert.assertEquals(BigDecimal.valueOf(20), order.getOrderLines().get(1).getFee());
		Assert.assertEquals(OrderLineType.CREDIT, order.getOrderLines().get(1).getType());
		Assert.assertEquals("微信订场优惠", order.getOrderLines().get(1).getDescription());
		
		System.out.println(new Gson().toJson(order));
	}
	
	@Test
	public void testBattleFieldOrder() {
		BigDecimal systemCountPrice = BigDecimal.valueOf(300);
		Order order = OrderManager.createBattleOrder(systemCountPrice);
		OrderManager.priceOrder(order);
		
		int expectPrice = 130;
		
		Assert.assertEquals(BigDecimal.valueOf(expectPrice), order.getFinalPrice());
		Assert.assertEquals(BigDecimal.valueOf(expectPrice*100).toString(), order.getWechatFinalPrice());
		Assert.assertEquals(BigDecimal.valueOf(expectPrice).toString(), order.getDisplayFinalPrice());
		
		Assert.assertEquals(2, order.getOrderLines().size());
		
		Assert.assertEquals(systemCountPrice.divide(BigDecimal.valueOf(2)), order.getOrderLines().get(0).getFee());
		Assert.assertEquals(OrderLineType.DEBIT, order.getOrderLines().get(0).getType());
		Assert.assertEquals("原价", order.getOrderLines().get(0).getDescription());
		
		Assert.assertEquals(BigDecimal.valueOf(20), order.getOrderLines().get(1).getFee());
		Assert.assertEquals(OrderLineType.CREDIT, order.getOrderLines().get(1).getType());
		Assert.assertEquals("微信订场优惠", order.getOrderLines().get(1).getDescription());
	}
	
	@Test
	public void testPositiveBattleFieldOrder() {
		BigDecimal systemCountPrice = BigDecimal.valueOf(300);
		Order order = OrderManager.createPositiveOrder(systemCountPrice);
		OrderManager.priceOrder(order);
		
		int expectPrice = 110;
		
		Assert.assertEquals(BigDecimal.valueOf(expectPrice), order.getFinalPrice());
		Assert.assertEquals(BigDecimal.valueOf(expectPrice*100).toString(), order.getWechatFinalPrice());
		Assert.assertEquals(BigDecimal.valueOf(expectPrice).toString(), order.getDisplayFinalPrice());
		
		Assert.assertEquals(3, order.getOrderLines().size());
		
		Assert.assertEquals(systemCountPrice.divide(BigDecimal.valueOf(2)), order.getOrderLines().get(0).getFee());
		Assert.assertEquals(OrderLineType.DEBIT, order.getOrderLines().get(0).getType());
		Assert.assertEquals("原价", order.getOrderLines().get(0).getDescription());
		
		Assert.assertEquals(BigDecimal.valueOf(20), order.getOrderLines().get(1).getFee());
		Assert.assertEquals(OrderLineType.CREDIT, order.getOrderLines().get(1).getType());
		Assert.assertEquals("微信订场优惠", order.getOrderLines().get(1).getDescription());
		
		Assert.assertEquals(BigDecimal.valueOf(20), order.getOrderLines().get(2).getFee());
		Assert.assertEquals(OrderLineType.CREDIT, order.getOrderLines().get(2).getType());
		Assert.assertEquals("主动约战优惠", order.getOrderLines().get(2).getDescription());
	}
}
