package com.estore.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.estore.dao.UserDao;
import com.estore.dao.impl.UserDaoImpl;
import com.estore.domain.User;

public class TestUserDao {

	private UserDao userDao = null;
	
	
	@Before
	public void setUp(){
		userDao = new UserDaoImpl();
	}
	
	@Test
	public void testsaveUser(){
		
		User user = new User();
		user.setUsername("test");
		user.setNickname("test");
		user.setPassword("test");
		user.setEmail("test@test.com");
		user.setBirthday(new Date());
		user.setUpdatetime(new Date());
		userDao.saveUser(user);
		
	}
	
	@Test
	public void testfindUserByUsernameAndPassword(){
		
		User findUserByUsernameAndPassword = userDao.findUserByUsernameAndPassword("test", "test1");
		System.out.println(findUserByUsernameAndPassword);
		
	}
	
	@Test
	public void testupdateUser(){
		
		User findUserByUsernameAndPassword = userDao.findUserByUsernameAndPassword("test", "test");
		findUserByUsernameAndPassword.setNickname("test111");
		System.out.println(findUserByUsernameAndPassword);
		boolean updateUser = userDao.updateUser(findUserByUsernameAndPassword);
		System.out.println(updateUser);
		
	}
	
	
}
