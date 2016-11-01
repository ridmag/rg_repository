/**
 * 
 */
package com.itelasoft.util;

import java.io.IOException;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itelasoft.pso.beans.FileData;

/**
 * @author vajira
 *
 */
public class ImageUploadServlet extends HttpServlet {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("servlet:"+req.getParameter("param1"));
		FileData fileData = new FileData();
		Base64Decode decode = new Base64Decode();
		fileData.getBlobFileData().setData(decode.decode(req.getParameter("param1").getBytes()));
		
		super.doPost(req, resp);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
}
