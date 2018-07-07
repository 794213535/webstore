package com.estore.dao;

public interface ShoppingItemDao {

	/**
	 * 添加商品到购物车项
	 * @param pid
	 * @param sid
	 * @param snum
	 * @return
	 */
	public int addShoppingItem(String pid,int sid,int snum);
	
	/**
	 * 从购物车删除某一项
	 * @param itemid
	 * @return
	 */
	public int deleteShoppingItem(int itemid);
	
}
