package com.estore.domain;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable{

	private int cid;
	private String cname;
	
	private List<Product> products;

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Category [cid=" + cid + ", cname=" + cname + ", products="
				+ products + "]";
	}

	
	
	
}
