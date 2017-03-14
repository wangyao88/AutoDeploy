package com.cis.properties.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cis.deploy.manager.DeployManager;
import com.cis.properties.bean.Property;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class PropertyServlet extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if(method.equals("readContent")){
			readContent(request,response);
		}else if(method.equals("writeContent")){
			writeContent(request,response);
		}
	}

	private void writeContent(HttpServletRequest request,
			HttpServletResponse response) {
		Property property = null;
		OutputStreamWriter writerStream = null;
		try {
			property = (Property) request.getSession().getAttribute("property");
		    writerStream = new OutputStreamWriter(new FileOutputStream(new File(property.getFilePath())),"UTF-8");
			writerStream.write(request.getParameter("content"));
			writerStream.flush();
			//由本地放到指定位置
			DeployManager.getInstance().getTransferService().transferFileAfterUpdatedProperties(property);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (writerStream != null) {
				try {
					writerStream.close();
				} catch (IOException e) {
					writerStream = null;
				}
			}
		}
	}

	private void readContent(HttpServletRequest request,
			HttpServletResponse response) {
		Property property = new Property();
		try {
			BeanUtils.populate(property, request.getParameterMap());
			StringBuilder path = new StringBuilder();
			path.append(this.getServletConfig().getServletContext().getRealPath("/"));
			path.append("config/");
			path.append(property.getServer());
			path.append("/");
			path.append(property.getPath());
			path.append("/");
			path.append(property.getName());
			property.setFilePath(path.toString());
			String content = DeployManager.getInstance().getPropertiesService().readContents(path.toString());
			request.setAttribute("content",content);
			request.getSession().setAttribute("property", property);
			this.getServletContext().getRequestDispatcher("/properties.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
