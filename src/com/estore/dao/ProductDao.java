package com.estore.dao;

import java.util.List;

import com.estore.domain.Product;

public interface ProductDao {

	/**
	 * 保存商品
	 * @param product
	 * @return
	 */
	public int saveProduct(Product product);
	
	/**
	 * 删除商品
	 * @param product
	 * @return
	 */
	public int deleteProduct(String pid);
	
	/**
	 * 修改商品
	 * @param product
	 * @return
	 */
	public int updateProduct(Product product);
	
	/**
	 * 根据商品名称查询商品
	 * @param pname
	 * @param pid
	 * @return
	 */
	public List<Product> findProductByPname(String pname);
	
	/**
	 * 根据商品号查询商品
	 * @param pid
	 * @return
	 */
	public Product findProductByPid(String pid);
	/**
	 * 查询所有商品
	 * @return
	 */
	public List<Product> findAllProduct();

	/**
	 * 查询记录数
	 * @return
	 */
	public int findRecordCount();

	/**
	 * 分页查询记录
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<Product> findPageRecords(int startIndex, int pageSize);

	/**
	 * 查询最前面count条记录
	 * @return
	 */
	public List<Product> findTop(int count);
	
	/**
	 * 查询从指定条数开始的count条记录
	 * @return
	 */
	public List<Product> findTop(int count,int start);
	
	public List<Product> findProductByCid(int cid);
	
	
}
