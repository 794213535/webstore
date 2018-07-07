package com.estore.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.omg.PortableServer.POA;

import com.estore.dao.ProductDao;
import com.estore.domain.Category;
import com.estore.domain.Product;
import com.estore.utils.C3P0Util;

public class ProductDaoImpl implements ProductDao {

	public int saveProduct(Product product) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "insert into product(pid,pname,estoreprice,markprice,pnum,cid,imgurl,description) values(?,?,?,?,?,?,?,?)";
			int update = qr.update(sql, product.getPid(), product.getPname(), product.getEstoreprice(), product.getMarkprice(),
					product.getPnum(), product.getCid(), product.getImgurl(), product.getDescription());
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int deleteProduct(String pid) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "delete from product where pid=?";
			int update = qr.update(sql, pid);
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int updateProduct(Product product) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "update product set pname=?,estoreprice=?,markprice=?,pnum=?,cid=?,imgurl=?,description=? where pid=?";
			int update = qr.update(sql, product.getPname(), product.getEstoreprice(), product.getMarkprice(),
					product.getPnum(), product.getCid(), product.getImgurl(), product.getDescription(), product.getPid());
			return update;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findProductByPname(String pname) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select * from product where pname like ?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class), "%"+pname+"%");
			
			System.out.println("ProductDaoImpl.findProductByPname()" +products);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findAllProduct() {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select * from product";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class));
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Product findProductByPid(String pid) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select * from product where pid=?";
			Product product = qr.query(sql, new BeanHandler<Product>(Product.class), pid);
			return product;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int findRecordCount() {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select count(pid) count from product";
			List query = qr.query(sql,new ColumnListHandler("count"));
			return Integer.parseInt(query.get(0).toString());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findPageRecords(int startIndex, int recordCount) {
		try {
			String sql = "select * from product limit ?,?";
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class), startIndex,recordCount);
			if(products != null || products.size() > 0){
				for(int i = 0; i < products.size(); i++){
					sql = "select * from category where cid=?";
					int cid = products.get(i).getCid();
					Category category = qr.query(sql, new BeanHandler<Category>(Category.class),cid);
					products.get(i).setCategory(category);
				}
			}
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e+"...查询失败");
		}
	}

	public List<Product> findTop(int count) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select * from product limit ?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class),count);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findTop(int count, int start) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select * from product limit ?,?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class),start,count);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> findProductByCid(int cid) {
		try {
			QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
			String sql = "select * from product where cid=?";
			List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class),cid);
			return products;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
