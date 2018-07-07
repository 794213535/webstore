package com.estore.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.estore.dao.ProductDao;
import com.estore.dao.impl.ProductDaoImpl;
import com.estore.domain.Product;

public class TestProductDao {
	
	private ProductDao productDao = null;
	
	@Before
	public void setUp(){
		productDao = new ProductDaoImpl();
	}
	
	@Test
	public void test_saveProduct() {//保存商品
		Product product = new Product();
		product.setPid("a00004");
		product.setPname("小熊饼干");
		product.setEstoreprice(2);
		product.setMarkprice(1.8);
		product.setPnum(90);
		product.setCid(1);
		product.setImgurl("e://abc");
		product.setDescription("多吃点");
		int i = productDao.saveProduct(product);
		if (i == 1) {
			System.out.println("商品保存成功！");
		} else {
			System.out.println("商品保存失败。");
		}
	}
	
	@Test
	public void test_deleteProduct() {//删除商品
//		Product product = new Product();
//		product.setPid("a00002");
//		//int i = productDao.deleteProduct(pid);
//		if (i == 1) {
//			System.out.println("商品删除成功！");
//		} else {
//			System.out.println("商品删除失败。");
//		}
	}
	
	@Test
	public void test_updateProduct() {//修改商品
		//pname=?,estoreprice=?,markprice=?,pnum=?,cid=?,imgurl=?,desc=?
		Product product = new Product();
		product.setPid("a00001");
		product.setPname("肉松饼11");
		product.setEstoreprice(2.2);
		product.setMarkprice(1.8);
		product.setPnum(100);
		product.setCid(1);
		product.setImgurl("C:");
		product.setDescription("好吃你就多吃点咯。");
		int i = productDao.updateProduct(product);
		if (i == 1) {
			System.out.println("商品修改成功！");
		} else {
			System.out.println("商品修改失败。");
		}
	}
	
	@Test
	public void test_findProductByPnameOrPid() {//根据商品名称或商品号查询商品
//		Product findProductByPnameOrPid = productDao.findProductByPnameOrPid("肉松饼11", "a00001");
//		System.out.println(findProductByPnameOrPid);
	}
	
	@Test
	public void test_findAllProduct() {//查询所有商品
		List<Product> findAllProduct = productDao.findAllProduct();
		System.out.println(findAllProduct);
	}

}
