package com.itelasoft.util;

import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ApplicationUtil {

	public static String getContextUrl(){
		FacesContext faces= (FacesContext) FacesContext.getCurrentInstance();
		HttpServletRequestWrapper var = new HttpServletRequestWrapper((HttpServletRequest)faces
		.getExternalContext().getRequest());
		System.out.println("request###:"+faces.getExternalContext().getRequestScheme()+"://"+var.getHeader("host")+faces.getExternalContext().getRequestContextPath());
		return faces.getExternalContext().getRequestScheme()+"://"+var.getHeader("host")+faces.getExternalContext().getRequestContextPath();
		
	}
}
