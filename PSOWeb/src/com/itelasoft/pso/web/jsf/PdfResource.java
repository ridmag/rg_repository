package com.itelasoft.pso.web.jsf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import com.icesoft.faces.context.Resource;
import com.itelasoft.pdf.FopEngine;

public class PdfResource implements Resource, Serializable {
	
	private static final long serialVersionUID = 5038267519042645029L;
	private String resourceName;
	private FopEngine thisEngine = new FopEngine();
	private Object bean;
	//private ExternalContext extContext;
	//public String sURL;

	public PdfResource(String resourceName, Object bean) {
		this.bean = bean;
		this.resourceName = resourceName;
		//this.extContext = ec;
	}

	/**
	 * This intermediate step of reading in the files from the JAR, into a byte
	 * array, and then serving the Resource from the ByteArrayInputStream, is
	 * not strictly necessary, but serves to illustrate that the Resource
	 * content need not come from an actual file, but can come from any source,
	 * and also be dynamically generated. In most cases, applications need not
	 * provide their own concrete implementations of Resource, but can instead
	 * simply make use of com.icesoft.faces.context.ByteArrayResource,
	 * com.icesoft.faces.context.FileResource,
	 * com.icesoft.faces.context.JarResource.
	 */
	
	public InputStream open() throws IOException {
		try {
			//HttpServletRequest thisRequest = (HttpServletRequest) extContext.getRequest();
			//sURL = "http://localhost:8080/PSOWeb";
			if(resourceName.equals("test"))
				return new ByteArrayInputStream(thisEngine.pdfTest("test", bean));
			if(resourceName.equals("Attendance"))
				return new ByteArrayInputStream(thisEngine.pdfAttendance("Attendance", bean));
			if(resourceName.equals("AttendanceList"))
				return new ByteArrayInputStream(thisEngine.pdfAttendance("AttendanceList", bean));
			if(resourceName.equals("Invoice"))
				return new ByteArrayInputStream(thisEngine.pdfInvoice("Invoice", bean));
			if(resourceName.equals("InvoiceList"))
				return new ByteArrayInputStream(thisEngine.pdfInvoice("InvoiceList", bean));
			if(resourceName.equals("Transport"))
				return new ByteArrayInputStream(thisEngine.pdfAttendance("Transport", bean));
			if(resourceName.equals("Utilized"))
				return new ByteArrayInputStream(thisEngine.pdfUtilized("Utilized", bean));
			if(resourceName.equals("MailLables"))
				return new ByteArrayInputStream(thisEngine.pdfMailLables("MailLables", bean));
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error " + e);
		}
		return null;
	}

	public String calculateDigest() {
		Date date = new Date();
		return date.toString();
	}

	public Date lastModified() {
		return new Date();
	}

	public void withOptions(Options arg0) throws IOException {
		arg0.setExpiresBy(new Date(new Date().getTime() - (60 * 30 * 1000)));
	}	

}