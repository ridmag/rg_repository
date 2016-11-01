/**
 * 
 */
package com.itelasoft.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.*;

import org.apache.axis2.databinding.types.xsd.Integer;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.icesoft.faces.component.ext.taglib.Util;
import com.itelasoft.pso.beans.Collection;
import com.itelasoft.pso.beans.HoursUtilizedReport;
import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.services.ServiceLocator;

/**
 * @author vajira
 * 
 */
public class VelocityBuilder {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
	private static DecimalFormat myFormatter = new DecimalFormat("###,##0.00");

	public enum TYPE {
		EMAIL, PDF
	};

	public static String createFromTemplate(String template,
			HashMap<String, Object> model) {
		VelocityEngine velocityEngine = ServiceLocator.getServiceLocator()
				.getVelocityEngine();
		String string = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, template, model);
		return string;
		// Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		// HTMLWorker htmlWorker = new HTMLWorker(document);

	}

	public static String attendance(HashMap<String,Object> eventsMap,
			boolean individual, Date periodEnd) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("proEvents", eventsMap);
		// hashMap.put("studentEvents", studentEvents);
		hashMap.put("dateFormat", dateFormat);
		hashMap.put("dateFormat2", new SimpleDateFormat("EEEE"));
		hashMap.put("timeFormat", timeFormat);
		hashMap.put("myFormatter", myFormatter);
		hashMap.put("type", individual ? "Y" : "");
		hashMap.put("periodEnd", periodEnd);
		hashMap.put("site-url",ApplicationUtil.getContextUrl());
		//hashMap.put(, value);
		String body;
		/*if (individual)
			body = createFromTemplate("/AttendanceInd.html", hashMap);
		else
		*/	body = createFromTemplate("/Attendance.html", hashMap);
		return body;
	}

	public static String invoices(List<Invoice> invoices) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		Calendar cal = new GregorianCalendar();
		for (Invoice inv : invoices) {
			cal.setTime(inv.getInvoiceDate());
			cal.add(Calendar.DAY_OF_MONTH, 30);
			inv.setDueDate(cal.getTime());
		}
		hashMap.put("invoices", invoices);
		hashMap.put("dateFormat", dateFormat);
		hashMap.put("myFormatter", myFormatter);
		String body = createFromTemplate("/InvoiceList.html", hashMap);
		return body;
	}

	public static String hoursUtilizedReport(HoursUtilizedReport report) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("report", report);
		hashMap.put("timeFormat", timeFormat);
		hashMap.put("dateFormat", dateFormat);
		String body = createFromTemplate("/HoursUtilizedReport.html", hashMap);
		return body;
	}

	public static String testTemplate() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("test", "helloooooo");
		String body = createFromTemplate("/test.html", hashMap);
		return body;
	}
	
	public static String MailLables(List<Student> students){
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		List<Student> studentArray=new ArrayList<Student>();
		List<ArrayList<Student>> studentArr=new ArrayList<ArrayList<Student>>();
		int count=0;
		for (Student stu : students) {
			studentArray.add(count,stu);
			count=count+1;
			if(count==3){
				List<Student> studentArrayCp=new ArrayList<Student>();
				studentArrayCp.addAll(studentArray);
				studentArr.add((ArrayList<Student>) studentArrayCp);
				studentArray.clear();
				count=0;
			}
		}
		if(count<3 && count>0){
			List<Student> studentArrayCp=new ArrayList<Student>();
			studentArrayCp.addAll(studentArray);
			studentArr.add((ArrayList<Student>) studentArrayCp);
			studentArray.clear();
		}
		hashMap.put("students", studentArr);
		String body = createFromTemplate("/studentMailLabels.html", hashMap);
		return body;
	}

}
