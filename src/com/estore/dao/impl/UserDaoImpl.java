package com.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.estore.dao.UserDao;
import com.estore.domain.Admin;
import com.estore.domain.User;
import com.estore.utils.C3P0Util;

public class UserDaoImpl implements UserDao {

	public User findUserByUsernameAndPassword(String username,String password) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select * from user where username=? and password=?";
			User user = qr.query(sql, new BeanHandler<User>(User.class), username,password);
			
			System.out.println("UserDaoImpl.findUserByUsernameAndPassword():" +user);
			if(user != null){
				return user;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean saveUser(User user) {
		
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "insert into user(username,nickname,password,email,birthday,updatetime) values(?,?,?,?,?,?)";
			int update = qr.update(sql, user.getUsername(),user.getNickname(),user.getPassword(),user.getEmail(),user.getBirthday(),user.getUpdatetime());
			if(update > 0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean updateUser(User user) {
		
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "update user set nickname=?,password=?,email=?,birthday=? where uid=?";
			int update = qr.update(sql,user.getNickname(),user.getPassword(),user.getEmail(),user.getBirthday(),
					user.getUid());
			if(update > 0){
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select * from user";
			List<User> users = qr.query(sql, new BeanListHandler<User>(User.class));
			return users;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int findRecordCount() {
		// TODO Auto-generated method stub
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select count(*) count from user";
			List query = qr.query(sql, new ColumnListHandler("count"));
			return Integer.parseInt(query.get(0).toString());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<User> findPageRecords(int startIndex, int pageSize) {
		// TODO Auto-generated method stub
		try {
			String sql = "select * from user limit ?,?";
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			List<User> users = qr.query(sql, new BeanListHandler<User>(User.class), startIndex,pageSize);
			return users;
		} catch (SQLException e) {
			throw new RuntimeException(e+"...查询失败");
		}
	}

	
	
}
