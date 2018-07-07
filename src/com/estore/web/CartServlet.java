package com.estore.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estore.domain.ShoppingCar;
import com.estore.domain.User;
import com.estore.service.CartService;
import com.estore.service.ShoppingItemService;
import com.estore.service.impl.CartServiceImpl;
import com.estore.service.impl.ShoppingItemServiceImpl;

public class CartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("op");
		if("addCart".equals(op)){
			addCart(request,response);
		}else if("delItem".equals(op)){
			delItem(request,response);
		}else if("findCart".equals(op)){
			findCart(request,response);
		}
	}

	private void findCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		CartService cartService = new CartServiceImpl();
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			response.sendRedirect(request.getContextPath()+"/user/login.jsp");
			return ;
		}
		ShoppingCar shoppingCar = cartService.findCart(user.getUid());
		request.getSession().setAttribute("shoppingCar", shoppingCar);
		response.sendRedirect(request.getContextPath()+"/shoppingcart.jsp");
	}

	private void delItem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		String itemid = request.getParameter("itemid");
		String uid = request.getParameter("uid");
		ShoppingItemService shoppingItemService = new ShoppingItemServiceImpl();
		boolean result = shoppingItemService.deleteShoppingItem(Integer.parseInt(itemid));
		if(result){
			CartService cartService = new CartServiceImpl();
			ShoppingCar shoppingCar = cartService.findCart(Integer.parseInt(uid));
			request.getSession().setAttribute("shoppingCar", shoppingCar);
			request.getRequestDispatcher("/shoppingcart.jsp").forward(request, response);
		}
	}

	private void addCart(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		String uid = request.getParameter("uid");
		String pid = request.getParameter("pid");
		String snumS = request.getParameter("snum");
		int snum = 1;
		if(snumS == null){
			snum = 1;
		}else{
			snum = Integer.parseInt(snumS);
		}
		CartService cartService = new CartServiceImpl();
		ShoppingCar shoppingCar = cartService.findCart(Integer.parseInt(uid));
		if(shoppingCar == null){
			boolean addCart = cartService.addCart(Integer.parseInt(uid));
			if(addCart){
				shoppingCar = cartService.findCart(Integer.parseInt(uid));
				boolean result = addItemToCart(pid,shoppingCar.getSid(),snum);
				if(result){
					shoppingCar = cartService.findCart(Integer.parseInt(uid));
					request.getSession().setAttribute("shoppingCar", shoppingCar);
					request.getRequestDispatcher("/shoppingcart.jsp").forward(request, response);
				}
				return ;
			}else{
				response.sendRedirect(request.getContextPath()+"/MainServlet");
			}
		}
		boolean result = addItemToCart(pid,shoppingCar.getSid(),snum);
		if(result){
			shoppingCar = cartService.findCart(Integer.parseInt(uid));
			request.getSession().setAttribute("shoppingCar", shoppingCar);
			request.getRequestDispatcher("/shoppingcart.jsp").forward(request, response);
		}
	}
	
	private boolean addItemToCart(String pid,int sid,int snum){
		ShoppingItemService shoppingItemService = new ShoppingItemServiceImpl();
		boolean result = shoppingItemService.addShoppingItem(pid, sid, snum);
		if(result){
			return true;
		}
		return false;
	}

}
