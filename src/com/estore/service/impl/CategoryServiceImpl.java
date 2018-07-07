package com.estore.service.impl;

import java.util.List;

import com.estore.dao.CategoryDao;
import com.estore.dao.impl.CategoryDaoImpl;
import com.estore.domain.Category;
import com.estore.service.CategoryService;
import com.estore.utils.Page;
import com.estore.utils.TransactionManager;

public class CategoryServiceImpl implements CategoryService {

	private CategoryDao categoryDao = new CategoryDaoImpl();
	
	public boolean addCategory(Category category) {
		if(category == null){
			throw new IllegalArgumentException("category is null...");
		}
		TransactionManager.startTransaction();
		int addCategory = categoryDao.addCategory(category);
		if(addCategory > 0){
			TransactionManager.commit();
			TransactionManager.release();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public boolean deleteCategory(int cid) {
		TransactionManager.startTransaction();
		int deleteCategory = categoryDao.deleteCategory(cid);
		if(deleteCategory > 0){
			TransactionManager.commit();
			TransactionManager.release();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public boolean updateCategory(Category category) {
		if(category == null){
			throw new IllegalArgumentException("category is null...");
		}
		TransactionManager.startTransaction();
		int updateCategory = categoryDao.updateCategory(category);
		if(updateCategory > 0){
			TransactionManager.commit();
			TransactionManager.release();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public List<Category> findAllCategory() {
		List<Category> categories = categoryDao.findAllCategory();
		return categories;
	}
	
	public Page findPageRecodes(String num){
		int pageNum = 1;
		if(num != null){
			pageNum = Integer.parseInt(num);
		}
		int totalRecordNum = categoryDao.findRecordCount();
		Page page = new Page(pageNum,totalRecordNum,5);
		List<Category> records = categoryDao.findPageRecords(page.getStartIndex(), page.getPageSize());
		page.setRecords(records);
		return page;
		
	}
}
