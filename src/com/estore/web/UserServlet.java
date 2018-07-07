package com.estore.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import com.estore.domain.User;
import com.estore.service.AdminService;
import com.estore.service.UserService;
import com.estore.service.impl.AdminServiceImpl;
import com.estore.service.impl.UserServiceImpl;
import com.estore.utils.FillDataBean;
import com.estore.utils.Page;
import com.estore.web.bean.UserFormBean;

public class UserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		String uid = request.getParameter("uid");
		
		if ("regist".equals(op)) {
			regist(request, response);
		}else if("login".equals(op)){
			login(request,response);
		}else if("lgout".equals(op)){
			lgout(request,response);
		}else if("adduser".equals(op)){
			adduser(request,response);
		}
		else if("findAllUser".equals(op)){
			findAllUser(request,response);
		}
		else if(uid != null) {
			updatePersonalData(request, response);
		}
	}
	
	
	private void findAllUser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserService userService = new UserServiceImpl();
		String num = request.getParameter("num");
		Page users = userService.findPageRecodes(num);
		request.setAttribute("page", users);
		System.out.println("UserServlet.findAllUser()"+users);
		request.getRequestDispatcher("/admin/user/userList.jsp").forward(
				request, response);
	}

	private void adduser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserFormBean userFormBean = FillDataBean.fillData(UserFormBean.class, request);
		boolean validate = userFormBean.validate();
		if(!validate){
			System.out.println("UserServlet.adduser() validate fail!");
			request.setAttribute("msg", userFormBean);
			request.getRequestDispatcher("/admin/user/addUser.jsp").forward(request, response);
			return ;
		}
		
		User user = new User();
		try {
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.copyProperties(user, userFormBean);
			UserService userService = new UserServiceImpl();
			user.setUpdatetime(new Date());
			boolean regist = userService.regist(user);
			if(regist){
				response.getWriter().write("添加成功,1秒后跳");
				response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/user/userList.jsp");
				return;
			}else{
				response.getWriter().write("呃，添加失败,1秒后重新添加");
				response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/admin/user/addUser.jsp");
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void updatePersonalData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserFormBean userFormBean = FillDataBean.fillData(UserFormBean.class, request);
		User user = new User();
		try {
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.copyProperties(user, userFormBean);
			user.setUpdatetime(new Date());
			
			UserService userService = new UserServiceImpl();
			
			boolean update = userService.updateUserMsg(user);
			if (update) {
				response.getWriter().write("修改成功,1秒后跳到主页");
				response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/index.jsp");
				return;
			} else {
//				response.getWriter().write("嗯，个人资料修改失败了，请重新修改吧,1秒后回到个人资料页");
//				response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/user/personal.jsp");
				request.setAttribute("user", user);
				request.getRequestDispatcher("/user/personal.jsp").forward(request, response);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void lgout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		request.getSession().removeAttribute("user");
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(username == null || password == null){
			request.setAttribute("msg", "用户名或密码不能为空");
			request.getRequestDispatcher("/user/login.jsp").forward(request, response);
			return ;
		}
		
		UserService userService = new UserServiceImpl();
		User user = userService.login(username, password);
		 
		if(user != null){
			request.getSession().setAttribute("user", user);
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			System.out.println("UserServlet.login() OK" +user);
			return ;
		}else{
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.setAttribute("msg", "用户名或密码错误");

			request.getRequestDispatcher("/user/login.jsp").forward(request, response);
			return ;
		}
		
	}

	private void regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserFormBean userFormBean = FillDataBean.fillData(UserFormBean.class, request);
		boolean validate = userFormBean.validate();
		if(!validate){
			request.setAttribute("msg", userFormBean);
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
			return ;
		}
		
		User user = new User();
		try {
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.copyProperties(user, userFormBean);
			UserService userService = new UserServiceImpl();
			user.setUpdatetime(new Date());
			boolean regist = userService.regist(user);
			if(regist){
				response.getWriter().write("注册成功,1秒后跳到登录页");
				response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/user/login.jsp");
				return;
			}else{
				response.getWriter().write("呃，注册失败了，重新注册吧,1秒后回到注册页");
				response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/user/login.jsp");
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
