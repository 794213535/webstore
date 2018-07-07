package com.estore.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.estore.domain.Product;
import com.estore.service.ProductService;
import com.estore.service.impl.ProductServiceImpl;

public class AddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	private String processUplodForm(FileItem fileItem) {
		
		try {
			InputStream is = fileItem.getInputStream();
			String storeDirectory = getServletContext().getRealPath("/files");
			String name = fileItem.getName();
			
			if(name!=null){
				name = FilenameUtils.getName(name);
			}
			name = UUID.randomUUID().toString()+"_"+name;
			
			File file = new File(storeDirectory,name);
		    
			try {
				fileItem.write(file);
				return name;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	private String processFieldForm(FileItem fileItem) throws UnsupportedEncodingException {
		String name = fileItem.getFieldName();
		String value = fileItem.getString("UTF-8");
		return value;
	}
	
	/**
	 * 生成文件保存的目录，使用哈希码的方式计算目录
	 * @param storeDirectory 文件存储的根目录
	 * @param filename
	 * @return
	 */
	private String makeDir(String storeDirectory,String filename){
		
		int hashCode = filename.hashCode();
		int dir1 = hashCode&0xf0;
		int dir2 = dir1 >> 4;
		String directory = dir1 + File.separator + dir2;
		File file = new File(storeDirectory,directory);
		if(!file.exists()){
			file.mkdirs();
		}
		return storeDirectory + File.separator + directory;
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		addProduct(request, response);
		
	}


	private void addProduct(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		boolean multipartContent = ServletFileUpload.isMultipartContent(request);
		if(!multipartContent){
			throw new RuntimeException("your form's enctype attribute must be multipart/form-data");
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		List<String> param = new ArrayList<String>();
		List<FileItem> items = new ArrayList<FileItem>(0);
		
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (FileItem fileItem : items) {
			if(fileItem.isFormField()){
				String value = processFieldForm(fileItem);
				param.add(value);
			}else{
				String path = processUplodForm(fileItem);
				param.add(path);
			}
		}
		Product product = new Product();
		product.setCid(Integer.parseInt(param.get(0)));
		product.setPid(param.get(1));
		product.setPnum(Integer.parseInt(param.get(2)));
		product.setPname(param.get(3));
		product.setEstoreprice(Double.parseDouble(param.get(4)));
		product.setMarkprice(Double.parseDouble(param.get(5)));
		product.setImgurl(param.get(6));
		product.setDescription(param.get(7));
		
		ProductService productService = new ProductServiceImpl();
		boolean result = productService.addProduct(product);
		if(result){
			response.getWriter().write("商品添加成功<br/>");
			response.setHeader("Refresh", "1;URL="+request.getContextPath()+"/ProductServlet?op=findAllProduct&num=1");
		}else{
			response.sendRedirect(request.getContextPath()+"/admin/product/productList.jsp");
			return;
		}
	}

}

