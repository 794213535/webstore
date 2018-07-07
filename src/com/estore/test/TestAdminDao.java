package com.estore.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.estore.dao.AdminDao;
import com.estore.dao.impl.AdminDaoImpl;
import com.estore.domain.Admin;

public class TestAdminDao {
	
	private AdminDao adminDao;
	
	@Before
	public void setup() {
		adminDao = new AdminDaoImpl();
	}
	
	@Test
	public void test_findAdminByUsernameAndPassword() {
		Admin findAdminByUsernameAndPassword = adminDao.findAdminByUsernameAndPassword("tom1", "cat");
		System.out.println(findAdminByUsernameAndPassword);
	}
	
	@Test
	public void test_saveAdmin() {
		Admin admin = new Admin();
		admin.setUsername("first");
		admin.setPassword("first");
		System.out.println("save:" + adminDao.saveAdmin(admin));
	}
	
	@Test
	public void test_updateAdmin() {
		Admin admin = new Admin();
		admin.setAid(2);
		admin.setUsername("test");
		admin.setPassword("test11");
		System.out.println("update:" + adminDao.updateAdminByAid(admin));
	}
	
	@Test
	public void test_findAllAdmin() {
		List<Admin> admins = adminDao.findAllAdmin();
		System.out.println(admins);
	}
	
	@Test
	public void test_findPageRecords() {
		List<Admin> admins = adminDao.findPageRecords(2, 1);
		System.out.println(admins);
	}
	
	@Test
	public void test_findRecordCount() {
		int counts = adminDao.findRecordCount();
		System.out.println(counts);
	}

}
