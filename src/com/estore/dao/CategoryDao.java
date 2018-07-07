package com.estore.dao;

import java.util.List;

import com.estore.domain.Category;

public interface CategoryDao {

	/**
	 * 增加分类
	 * @param category
	 * @return
	 */
	int addCategory(Category category);
	
	/**
	 * 删除分类
	 * @param cid
	 * @return
	 */
	int deleteCategory(int cid);
	
	/**
	 * 修改分类
	 * @param category
	 * @return
	 */
	int updateCategory(Category category);
	
	/**
	 * 查找所有分类
	 * @return
	 */
	List<Category> findAllCategory();
	
	/**
	 * 查询指定页的记录
	 * @param startIndex 每页记录开始的索引值
	 * @param recordCount 总记录数
	 * @return
	 */
	List<Category> findPageRecords(int startIndex,int recordCount);
	
	int findRecordCount();
}

