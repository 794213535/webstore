package com.estore.test;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.estore.dao.OrderDao;
import com.estore.dao.impl.OrderDaoImpl;
import com.estore.domain.Order;

public class TestOrderDao {
	
	private OrderDao orderDao = null;
	
	@Before
	public void setup() {
		orderDao = new OrderDaoImpl();
	}
	
	@Test
	public void test_placeOrder() {//下单
		Order order = new Order();
		order.setOid("0002");
		order.setMoney(55.5);
		order.setRecipients("tom");
		order.setTel("119");
		order.setAddress("警察局");
		order.setState(2);
		order.setOrdertime(new Date());
		order.setUid(1);
		int i = orderDao.placeOrder(order);
		if (i == 1) {
			System.out.println("下单成功！");
		} else {
			System.out.println("下单失败。");
		}
	}
	
	@Test
	public void test_updateOrderStateByOid() {//根据订单号修改订单状态
		String oid = "0001";
		int state = 1;
		int i = orderDao.updateOrderStateByOid(oid, state);
		if (i == 1) {
			System.out.println("订单状态修改成功！");
		} else {
			System.out.println("订单状态修改失败。");
		}
	}
	
	@Test
	public void test_findOrderById() {//根据订单号查询订单
		String oid = "0001";
		Order findOrderById = orderDao.findOrderById(oid);
		System.out.println(findOrderById);
	}

	@Test
	public void test_findOrderByUserId() {//关联查询 根据订单项得到订单
//		List<Order> orders = orderDao.findOrderByUserId(0, 1);
//		System.out.println(orders.get(0));
	}
	
	@Test
	public void test_findOrderByUserId2() {//多一个uid参数
//		List<Order> orders = orderDao.findOrderByUserId(0, 1, 1);
//		System.out.println(orders.get(0));
	}
	
	@Test
	public void test_findAllOrder() {//查询所有订单
		List<Order> findAllOrder = orderDao.findAllOrder();
		System.out.println(findAllOrder);
	}

}
