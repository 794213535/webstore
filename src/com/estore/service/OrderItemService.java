package com.estore.service;

import java.util.List;

import com.estore.domain.OrderItem;

public interface OrderItemService {

	boolean addOrderItem(OrderItem orderItem);
	
	public List<OrderItem> findOrderItemsByOrderid(String oid) ;

	
}
