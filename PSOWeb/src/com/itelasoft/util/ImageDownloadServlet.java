/**
 * 
 */
package com.itelasoft.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itelasoft.pso.beans.FileData;
import com.itelasoft.pso.services.IServiceLocator;
import com.itelasoft.pso.services.ServiceLocator;

public class ImageDownloadServlet extends HttpServlet {

	protected IServiceLocator serviceLocator = ServiceLocator
			.getServiceLocator();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String which = req.getParameter("p");
		String type = req.getParameter("type");
		FileData data = null;
		if (which != null && !which.isEmpty()) {
			data = serviceLocator.getFileDataService().retrieveEager(
					Long.valueOf(which));
		}
		if (data != null) {
			resp.setContentType(data.getContentType());
			 resp.addHeader("Pragma", "no-cache");
		        resp.addHeader("Cache-Control", "no-cache");
		        // Stronger according to blog comment below that references HTTP spec
		        resp.addHeader("Cache-Control", "no-store");
		        resp.addHeader("Cache-Control", "must-revalidate");
			if (type != null && type.equals("F"))
				resp.getOutputStream().write(data.getBlobFileData().getData());
			else
				resp.getOutputStream().write(
						data.getBlobFileData().getThumbnail());
		} else {
			URL url = new URL(req
					.getRequestURL()
					.replace(req.getRequestURL().lastIndexOf("/") + 1,
							req.getRequestURL().length(), "images/no_logo.jpg")
					.toString());
			URLConnection uc = url.openConnection();
			BufferedInputStream in = new BufferedInputStream(

			uc.getInputStream());
			resp.setContentType("image/jpeg");
			byte[] buf = new byte[10000];
			int read = 0;
			while ((read = in.read(buf)) >= 0)
				resp.getOutputStream().write(buf, 0, read);
			in.close();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
}
