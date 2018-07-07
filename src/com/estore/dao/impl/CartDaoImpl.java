package com.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.estore.dao.CartDao;
import com.estore.domain.Product;
import com.estore.domain.ShoppingCar;
import com.estore.domain.ShoppingItem;
import com.estore.utils.C3P0Util;

public class CartDaoImpl implements CartDao {

	public ShoppingCar findCart(int uid) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select * from shoppingcar where uid=?";
			ShoppingCar shoppingCar = qr.query(sql, new BeanHandler<ShoppingCar>(ShoppingCar.class), uid);
			if(shoppingCar != null){
				sql = "select * from shoppingItem where sid=?";
				List<ShoppingItem> shoppingItems = qr.query(sql, new BeanListHandler<ShoppingItem>(ShoppingItem.class), shoppingCar.getSid());
				for(int i  = 0; i < shoppingItems.size(); i++){
					sql = "select * from product where pid=?";
					Product product = qr.query(sql, new BeanHandler<Product>(Product.class), shoppingItems.get(i).getPid());
					shoppingItems.get(i).setProduct(product);
				}
				shoppingCar.setShoppingItems(shoppingItems);
			}
			return shoppingCar;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int saveCar(int uid) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "insert into shoppingcar(uid) values(?)";
			int update = qr.update(sql,uid);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
