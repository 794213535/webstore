package com.estore.service.impl;

import com.estore.dao.CartDao;
import com.estore.dao.impl.CartDaoImpl;
import com.estore.domain.ShoppingCar;
import com.estore.service.CartService;
import com.estore.utils.TransactionManager;

public class CartServiceImpl implements CartService{

	private CartDao cartDao = new CartDaoImpl();
	
	public ShoppingCar findCart(int uid) {
		ShoppingCar sc = cartDao.findCart(uid);
		return sc;
	}
	
	public boolean addCart(int uid) {
		TransactionManager.startTransaction();
		int saveCar = cartDao.saveCar(uid);
		if(saveCar > 0){
			TransactionManager.commit();
			TransactionManager.release();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	

}
