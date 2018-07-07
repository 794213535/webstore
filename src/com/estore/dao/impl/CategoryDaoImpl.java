package com.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.estore.dao.CategoryDao;
import com.estore.domain.Category;
import com.estore.utils.C3P0Util;

public class CategoryDaoImpl implements CategoryDao {

	public int addCategory(Category category) {
		
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "insert into category(cname) values(?)";
			int update = qr.update(sql, category.getCname());
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int deleteCategory(int cid) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "delete from category where cid=?";
			int update = qr.update(sql, cid);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int updateCategory(Category category) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "update category set cname=? where cid=?";
			int update = qr.update(sql, category.getCname(),category.getCid());
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Category> findAllCategory() {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select * from category";
			List<Category> categories = qr.query(sql, new BeanListHandler<Category>(Category.class));
			return categories;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Category> findPageRecords(int startIndex, int recordCount) {
		try {
			String sql = "select cid,cname from category limit ?,?";
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			List<Category> categories = qr.query(sql, new BeanListHandler<Category>(Category.class), startIndex,recordCount);
			return categories;
		} catch (SQLException e) {
			throw new RuntimeException(e+"...查询失败");
		}
	}

	public int findRecordCount() {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select count(cid) count from category";
			List query = qr.query(sql,new ColumnListHandler("count"));
			return Integer.parseInt(query.get(0).toString());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
