package com.estore.dao;

import com.estore.domain.ShoppingCar;

public interface CartDao {

	ShoppingCar findCart(int uid);
	
	int saveCar(int uid);
	
	
	
}
