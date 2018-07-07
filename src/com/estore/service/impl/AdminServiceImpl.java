package com.estore.service.impl;

import java.util.List;

import com.estore.dao.AdminDao;
import com.estore.dao.impl.AdminDaoImpl;
import com.estore.domain.Admin;
import com.estore.service.AdminService;
import com.estore.utils.Page;
import com.estore.utils.TransactionManager;

public class AdminServiceImpl implements AdminService {
	
	private AdminDao adminDao = new AdminDaoImpl();

	public Admin login(String username, String password) {
		Admin admin = adminDao.findAdminByUsernameAndPassword(username, password);
		return admin;
	}

	public boolean regist(Admin admin) {
		if (admin == null) {
			throw new IllegalArgumentException("admin is null...");
		}
		TransactionManager.startTransaction();
		boolean addAdmin = adminDao.saveAdmin(admin);
		if (addAdmin) {
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public boolean updateAdminMsg(Admin admin) {
		if (admin == null) {
			throw new IllegalArgumentException("admin is null...");
		}
		TransactionManager.startTransaction();
		boolean updateAdmin = adminDao.updateAdminByAid(admin);
		if (updateAdmin) {
			TransactionManager.commit();
			return true;
		}
		TransactionManager.rollback();
		TransactionManager.release();
		return false;
	}

	public List<Admin> findAllAdmin() {
		List<Admin> admins = adminDao.findAllAdmin();
		return admins;
	}

	public Page findPageRecodes(String num) {
		int pageNum = 1;
		if(num != null){
			pageNum = Integer.parseInt(num);
		}
		int totalRecordNum = adminDao.findRecordCount();
		Page page = new Page(pageNum,totalRecordNum,2);
		List<Admin> records = adminDao.findPageRecords(page.getStartIndex(), page.getPageSize());
		page.setRecords(records);
		return page;
	}

}
