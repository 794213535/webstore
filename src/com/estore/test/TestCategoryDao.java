package com.estore.test;

import org.junit.Test;

import com.estore.dao.CategoryDao;
import com.estore.dao.impl.CategoryDaoImpl;

public class TestCategoryDao {

	@Test
	public void test() {
		CategoryDao categoryDao = new CategoryDaoImpl();
		int findRecordCount = categoryDao.findRecordCount();
		System.out.println(findRecordCount);
	}
	
	

}
