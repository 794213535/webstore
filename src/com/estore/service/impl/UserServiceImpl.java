package com.estore.service.impl;

import java.util.List;

import com.estore.dao.UserDao;
import com.estore.dao.impl.UserDaoImpl;
import com.estore.domain.Admin;
import com.estore.domain.User;
import com.estore.service.UserService;
import com.estore.utils.Page;
import com.estore.utils.TransactionManager;

public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDaoImpl();
	
	public User login(String username, String password) {
		
		User user = userDao.findUserByUsernameAndPassword(username, password);
		
		return user;
	}

	public boolean regist(User user) {
		if(user == null){
			throw new IllegalArgumentException("用户为空");
		}
		TransactionManager.startTransaction();
		userDao.saveUser(user);
		TransactionManager.commit();
		TransactionManager.release();
		return true;
	}

	public boolean updateUserMsg(User user) {
		if(user == null){
			throw new IllegalArgumentException("用户为空");
		}
		TransactionManager.startTransaction();
		userDao.updateUser(user);
		TransactionManager.commit();
		TransactionManager.release();
		return true;
	}
	
	public List<User> findAllUser() {
		List<User> users = userDao.findAllUser();
		return users;
	}

	public Page findPageRecodes(String num) {
		// TODO Auto-generated method stub
		int pageNum = 1;
		if(num != null){
			pageNum = Integer.parseInt(num);
		}
		int totalRecordNum = userDao.findRecordCount();
		Page page = new Page(pageNum,totalRecordNum,3);
		List<User> records = userDao.findPageRecords(page.getStartIndex(), page.getPageSize());
		page.setRecords(records);
		return page;
	}

}
