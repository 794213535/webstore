package com.estore.service.impl;

import java.util.List;

import com.estore.dao.OrderItemDao;
import com.estore.dao.impl.OrderItemDaoImpl;
import com.estore.domain.OrderItem;
import com.estore.service.OrderItemService;
import com.estore.utils.TransactionManager;

public class OrderItemServiceImpl implements OrderItemService {

	private OrderItemDao orderItemDao = new OrderItemDaoImpl();
	
	public boolean addOrderItem(OrderItem orderItem) {
		TransactionManager.startTransaction();
		int placeOrder = orderItemDao.saveOrderItem(orderItem);
		if(placeOrder > 0){
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public List<OrderItem> findOrderItemsByOrderid(String oid) {
		// TODO Auto-generated method stub
		return orderItemDao.findOrderItemsByOrderid(oid);
		
		
	}

}
