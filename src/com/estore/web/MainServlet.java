package com.estore.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estore.domain.Category;
import com.estore.domain.Product;
import com.estore.service.CategoryService;
import com.estore.service.ProductService;
import com.estore.service.impl.CategoryServiceImpl;
import com.estore.service.impl.ProductServiceImpl;

public class MainServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("MainServlet.doPost() start from here!");

		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categories = categoryService.findAllCategory();
		request.getSession().setAttribute("categories", categories);
		
		ProductService productService = new ProductServiceImpl();
		List<Product> productTop = productService.selectTop(4);
		request.getSession().setAttribute("productTop", productTop);
		
		List<Product> hotProducts = productService.selectTop(6, 5);
		request.getSession().setAttribute("hotProducts", hotProducts);
		
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}

}
