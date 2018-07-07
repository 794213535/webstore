package com.estore.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.estore.dao.ProductDao;
import com.estore.dao.impl.ProductDaoImpl;
import com.estore.domain.Product;
import com.estore.service.ProductService;
import com.estore.utils.Page;
import com.estore.utils.TransactionManager;

public class ProductServiceImpl implements ProductService {

	private ProductDao productDao = new ProductDaoImpl();
	
	public boolean addProduct(Product product) {
		TransactionManager.startTransaction();
		int saveProduct = productDao.saveProduct(product);
		if(saveProduct > 0){
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public boolean deleteProduct(String pid) {
		TransactionManager.startTransaction();
		int deleteProduct = productDao.deleteProduct(pid);
		if(deleteProduct > 0){
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public boolean updateProduct(Product product) {
		TransactionManager.startTransaction();
		int updateProduct = productDao.updateProduct(product);
		if(updateProduct > 0){
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public List<Product> findProductByPname(String pname) {
		List<Product> products = productDao.findProductByPname(pname);
		return products;
	}

	public List<Product> findAllProduct() {
		List<Product> products = productDao.findAllProduct();
		return products;
	}

	public Product findProductByPid(String pid) {
		Product product = productDao.findProductByPid(pid);
		return product;
	}

	public List<Product> findProductByPnameOrPid(String pidOrPname) {
		List<Product> products = new ArrayList<Product>();
		List<Product> findProductByPname = productDao.findProductByPname(pidOrPname);
		if(findProductByPname!=null){
			products.addAll(findProductByPname);
		}
		Product product = productDao.findProductByPid(pidOrPname);
		if(product != null){
			products.add(product);
		}
		if(products.size() > 0){
			return products;
		}
		return null;
	}
	
	public Page findPageRecodes(String num){
		int pageNum = 1;
		if(num != null){
			pageNum = Integer.parseInt(num);
		}
		int totalRecordNum = productDao.findRecordCount();
		Page page = new Page(pageNum,totalRecordNum,5);
		List<Product> records = productDao.findPageRecords(page.getStartIndex(), page.getPageSize());
		page.setRecords(records);
		return page;
		
	}

	public List<Product> selectTop(int count) {
		List<Product> products = productDao.findTop(count);
		return products;
	}

	public List<Product> selectTop(int count, int start) {
		List<Product> products = productDao.findTop(count,start);
		return products;
	}

	public List<Product> findProductByCid(int cid) {
		List<Product> products = productDao.findProductByCid(cid);
		return products;
	}
	
	

}
