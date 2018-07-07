package com.estore.web;

import java.io.IOException;
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
import com.estore.utils.FillDataBean;
import com.estore.utils.Page;

public class CategoryServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String op = request.getParameter("op");
		if("addCategory".equals(op)){
			addCategory(request,response);
		}else if("findAllCategory".equals(op)){
			findAllCategory(request,response);
		}else if("updateCategory".equals(op)){
			updateCategory(request,response);
		}else if("deleteOne".equals(op)){
			deleteOne(request,response);
		}else if("deleteMulti".equals(op)){
			deleteMulti(request,response);
		}else if("findCategory".equals(op)){
			findCategory(request,response);
		}else if("findCategoryByUpdate".equals(op)){
			findCategoryByUpdate(request,response);
		}
		
	}

	private void findCategoryByUpdate(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categories = categoryService.findAllCategory();
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(request.getParameter("pid"));
		
		request.setAttribute("categories", categories);
		request.setAttribute("product", product);
		request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request, response);
		return;
	}

	private void findCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categories = categoryService.findAllCategory();
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/admin/product/addProduct.jsp").forward(request, response);
		return;	
	}

	private void deleteMulti(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String[] cids = request.getParameterValues("cid");
		if(cids == null || cids.length == 0){
			response.sendRedirect(request.getContextPath()+"/CategoryServlet?op=findCategory&num=1");
			return ;
		}
		CategoryService categoryService = new CategoryServiceImpl();
		for(int i = 0; i < cids.length; i++){
			categoryService.deleteCategory(Integer.parseInt(cids[i]));
		}
		response.sendRedirect(request.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");
	}


	private void deleteOne(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String cid = request.getParameter("cid");
		if(cid == null || "".equals(cid.trim())){
			return ;
		}else{
			CategoryService categoryService = new CategoryServiceImpl();
			try {
				boolean result = categoryService.deleteCategory(Integer.parseInt(cid));
				if(result){
					response.sendRedirect(request.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");
				}else{
					return ;
				}
			} catch (RuntimeException e) {
				// TODO: handle exception
				//java.sql.SQLException: Cannot delete or update a parent row: a foreign key constraint fails
				response.getWriter().println("删除失败，该类型下还有商品，请先删除所有该类型的商品！！");
				//response.setHeader("refresh", "3;url= "+request.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");

			}
			
		}
	}


	private void updateCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		if(cid == null || cid.trim().length()==0){
			response.sendRedirect(request.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");
		}
		
		
		if(cname == null || "".equals(cname.trim())){
			request.setAttribute("msg", "分类名不能为空");
			request.getRequestDispatcher("/updateCategory.jsp").forward(request, response);
			return ;
		}
		Category category = FillDataBean.fillData(Category.class, request);
		
		CategoryService categoryService = new CategoryServiceImpl();
		boolean result = categoryService.updateCategory(category);
		if(result){
			response.sendRedirect(request.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");
			return ;
		}else{
			request.setAttribute("cname", cname);
			request.getRequestDispatcher("/updateCategory.jsp").forward(request, response);
		}
	}


	private void findAllCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		CategoryService categoryService = new CategoryServiceImpl();
		String num = request.getParameter("num");
		Page categories = categoryService.findPageRecodes(num);
		request.setAttribute("page", categories);
		request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request, response);
	}


	private void addCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		String cname = request.getParameter("cname");
		
		if(cname == null || "".equals(cname.trim())){
			request.setAttribute("msg", "分类名不能为空");
			request.getRequestDispatcher("/admin/category/addCategory.jsp").forward(request, response);
			return;
		}
		Category category = FillDataBean.fillData(Category.class, request);
		CategoryService categoryService = new CategoryServiceImpl();
		boolean result = categoryService.addCategory(category);
		if(result){
			response.sendRedirect(request.getContextPath()+"/CategoryServlet?op=findAllCategory&num=1");
			return ;
		}else{
			request.setAttribute("cname", cname);
			request.getRequestDispatcher("/addCategory.jsp").forward(request, response);
		}
		
		
	}

}
