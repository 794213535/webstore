package com.estore.service;

import java.util.List;

import com.estore.domain.Category;
import com.estore.utils.Page;

public interface CategoryService {

	/**
	 * 增加分类
	 * @param category
	 * @return
	 */
	boolean addCategory(Category category);
	
	/**
	 * 删除分类
	 * @param cid
	 * @return
	 */
	boolean deleteCategory(int cid);
	
	/**
	 * 修改分类
	 * @param category
	 * @return
	 */
	boolean updateCategory(Category category);
	
	/**
	 * 查询所有分类
	 * @return
	 */
	List<Category> findAllCategory();
	
	/**
	 * 分页查询
	 * @param num 页码字符串形式
	 * @return
	 */
	public Page findPageRecodes(String num);
	
}
