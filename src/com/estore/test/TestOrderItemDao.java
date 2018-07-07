package com.estore.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.estore.dao.OrderItemDao;
import com.estore.dao.impl.OrderItemDaoImpl;
import com.estore.domain.Order;
import com.estore.domain.OrderItem;
import com.estore.domain.Product;

public class TestOrderItemDao {
	
	private OrderItemDao orderItemDao= null;
	
	@Before
	public void setup() {
		orderItemDao = new OrderItemDaoImpl();
	}
	
	@Test
	public void test_saveOrderItem() {//保存订单项
		OrderItem orderItem = new OrderItem();
		Order order = new Order();
		order.setOid("1");
		Product product = new Product();
		product.setPid("1002");
		
		orderItem.setOid(order.getOid());
		orderItem.setPid(product.getPid());
		orderItem.setBuynum(10);
		int i = orderItemDao.saveOrderItem(orderItem);
		if (i == 1) {
			System.out.println("订单项保存成功！");
		} else {
			System.out.println("订单项保存失败。");
		}
	}
	
	@Test
	public void test_deleteOrderItem() {//删除订单项
		int itemid = 1;
		int i = orderItemDao.deleteOrderItem(itemid);
		if (i == 1) {
			System.out.println("订单项删除成功！");
		} else {
			System.out.println("订单项删除失败。");
		}
	}
	
	@Test
	public void test_updateOrderItem() {//修改订单项
		OrderItem orderItem = new OrderItem();
		orderItem.setItemid(2);
		orderItem.setOid("1");
		orderItem.setPid("1002");
		orderItem.setBuynum(90);
		int i = orderItemDao.updateOrderItem(orderItem);
		if (i == 1) {
			System.out.println("订单项修改成功！");
		} else {
			System.out.println("订单项修改失败。");
		}
	}
	
	@Test
	public void test_findOrderItemByItemid() {//根据订单项id查询订单项
		OrderItem orderItem = orderItemDao.findOrderItemByItemid(2);
		System.out.println(orderItem);
	}
	
	@Test
	public void test_findAllOrderItem() {//查询所有商品
		List<OrderItem> orderItems = orderItemDao.findAllOrderItem();
		System.out.println(orderItems);
	}

}
