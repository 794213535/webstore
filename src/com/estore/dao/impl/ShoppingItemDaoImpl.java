package com.estore.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.estore.dao.ShoppingItemDao;
import com.estore.utils.C3P0Util;

public class ShoppingItemDaoImpl implements ShoppingItemDao {

	public int addShoppingItem(String pid, int sid, int snum) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "insert into shoppingitem(pid,sid,snum) values(?,?,?)";
			int update = qr.update(sql,pid,sid,snum);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int deleteShoppingItem(int itemid) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "delete from shoppingitem where itemid=?";
			int update = qr.update(sql,itemid);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
