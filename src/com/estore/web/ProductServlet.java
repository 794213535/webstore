package com.estore.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estore.domain.Product;
import com.estore.service.CategoryService;
import com.estore.service.ProductService;
import com.estore.service.impl.CategoryServiceImpl;
import com.estore.service.impl.ProductServiceImpl;
import com.estore.utils.FillDataBean;
import com.estore.utils.Page;

public class ProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		
		if ("findAllProduct".equals(op)) {
			findAllProduct(request, response);
		} else if ("updateProduct".equals(op)) {
			updateProduct(request, response);
		} else if ("deleteOne".equals(op)) {
			deleteOne(request, response);
		} else if ("deleteMulti".equals(op)) {
			deleteMulti(request, response);
		} else if("findProductById".equals(op)){
			findProductById(request,response);
		} else if("byCid".equals(op)){
			findProductByPid(request,response);
		} else if("findProByName".equals(op)){
			findProByName(request,response);
		}
	}

	private void findProByName(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		request.removeAttribute("products");
		String pname = request.getParameter("pname");
		if(pname == null){
			request.getRequestDispatcher("/products.jsp").forward(request, response);
			return ;
		}
		ProductService productService = new ProductServiceImpl();
		List<Product> products = productService.findProductByPname(pname);
		request.setAttribute("products", products);
		request.getRequestDispatcher("/products.jsp").forward(request, response);
	}

	private void findProductByPid(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.removeAttribute("products");
		String cidStr = request.getParameter("cid");
		int cid = Integer.parseInt(cidStr.trim());
		ProductService productService = new ProductServiceImpl();
		List<Product> products = productService.findProductByCid(cid);
		request.setAttribute("products", products);
		request.getRequestDispatcher("/products.jsp").forward(request, response);
	}

	private void findProductById(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/productdetail.jsp").forward(request, response);
		return ;
	}

	private void deleteMulti(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String[] pids = request.getParameterValues("pid");
		if (pids == null || pids.length == 0) {
			response.getWriter().write("商品删除失败");
			response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/ProductServlet?op=findAllProduct&num=1");
			return;
		}
		ProductService productService = new ProductServiceImpl();
		for (int i = 0; i < pids.length; i++) {
			productService.deleteProduct(pids[i]);
		}
		response.getWriter().write("商品删除成功<br/>");
		response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/ProductServlet?op=findAllProduct&num=1");
	}

	private void deleteOne(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		ProductService productService = new ProductServiceImpl();
		boolean delete = productService.deleteProduct(pid);
		if (delete) {
			//删除成功
			response.getWriter().write("商品删除成功<br/>");
			response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/ProductServlet?op=findAllProduct&num=1");
			return;
		} else {
			response.sendRedirect(request.getContextPath()+"/admin/product/productList.jsp");
			return;
		}
	}

	private void updateProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Product product = FillDataBean.fillData(Product.class, request);
		ProductService productService = new ProductServiceImpl();
		boolean update = productService.updateProduct(product);
		if (update) {
			//更新成功
			response.sendRedirect(request.getContextPath()+"/ProductServlet?op=findAllProduct&num=1");
			return;
		} else {
			//更新失败
			response.sendRedirect(request.getContextPath()+"/CategoryServlet?op=findCategoryByUpdate");
			return;
		}
	}

	private void findAllProduct(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ProductService productService = new ProductServiceImpl();
		String num = request.getParameter("num");
		Page products = productService.findPageRecodes(num);
		request.setAttribute("page", products);
		request.getRequestDispatcher("/admin/product/productList.jsp").forward(request, response);
		return ;
	}

}
