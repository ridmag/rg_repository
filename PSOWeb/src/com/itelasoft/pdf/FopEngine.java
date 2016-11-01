package com.itelasoft.pdf;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.itelasoft.pso.beans.HoursUtilizedReport;
import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentGroup;
import com.itelasoft.pso.services.IServiceLocator;
import com.itelasoft.pso.services.ServiceLocator;
import com.itelasoft.pso.web.EventManagerModel;
import com.itelasoft.pso.web.InvoiceManagerModel;
import com.itelasoft.pso.web.ProgramManagerModel;
import com.itelasoft.pso.web.StudentManagerModel;
import com.itelasoft.pso.web.TransportManager;
import com.itelasoft.pso.web.reports.ReportManagerModel;
import com.itelasoft.util.SortEventsBySequence;
import com.itelasoft.util.SortObjectByName;
import com.itelasoft.util.SortProgramEventsByDate;
import com.itelasoft.util.VelocityBuilder;
import com.itelasoft.xslt.TransformerUtil;

public class FopEngine {
	protected IServiceLocator serviceLocator = ServiceLocator
			.getServiceLocator();

	public byte[] pdfTest(String sReportFileName, Object managedBean) {
		// this.sURL = sURL;
		// String invoiceType = sReportFileName;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String body = VelocityBuilder.testTemplate();
		InputStream xsltfile = getClass().getResourceAsStream(
				"/com/itelasoft/xslt/xhtml2fo.xsl");// new
		// FileInputStream("C:\\Users\\vajira\\workspace\\ITelaVet3\\src\\com\\itelasoft\\xslt\\xhtml2fo.xsl");
		TransformerUtil.transformToPdf(xsltfile, new StringReader(body),
				outputStream);
		return outputStream.toByteArray();
	}

	public byte[] pdfAttendance(String sReportFileName, Object managedBean) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		List<ProgramEvent> programEvents = new ArrayList<ProgramEvent>();
		boolean individual = false;
		Date periodEnd = null;
		HashMap<String, Object> finalMap = new HashMap<String, Object>();

		if (sReportFileName.equals("Attendance")) {
			ProgramManagerModel programManagerModel = (ProgramManagerModel) managedBean;
			ProgramEvent event = programManagerModel.getProEvent();
			// event.setStudentEvents(serviceLocator.getProgramEventService()
			// .listStudentEventsByEventId(event.getId()));
			programEvents.add(event);
			StudentGroup group = serviceLocator.getStudentGroupService()
					.retrieveWithData(event.getGroup().getId(),false);
			if (group.getProgram().getType().getName().equals("Individual"))
				finalMap.put("Individual", programEvents);
			else
				finalMap.put("Student", programEvents);

		} else if (sReportFileName.equals("Transport")) {
			TransportManager transportManager = (TransportManager) managedBean;
			ProgramEvent event = transportManager.getProEvent();
			ProgramEvent event2 = event.getReturnEvent();

			// event.setStudentEvents(serviceLocator.getProgramEventService()
			// .listStudentEventsByEventId(event.getId()));
			Collections.sort(event.getStudentEvents(),
					new SortEventsBySequence());
			programEvents.add(event);
			if (event2 != null && !event2.getStudentEvents().isEmpty()) {
				Collections.sort(event2.getStudentEvents(),
						new SortEventsBySequence());
				programEvents.add(event2);
			}
			finalMap.put("Student", programEvents);
		} else {
			EventManagerModel eventManagerModel = (EventManagerModel) managedBean;
			periodEnd = eventManagerModel.getToDate();
			individual = eventManagerModel.isIndividual();
			HashMap<Long, List<ProgramEvent>> indMap = new HashMap<Long, List<ProgramEvent>>();
			HashMap<Long, ProgramEvent> printList = new HashMap<Long, ProgramEvent>();
			// HashMap<Long, ProgramEvent> printListTransport = new
			// HashMap<Long, ProgramEvent>();
			/*
			 * SortedMap<Long, ProgramEvent> sortedMap = new TreeMap<Long,
			 * ProgramEvent>(); for(ProgramEvent programEvent
			 * :eventManagerModel.getSelectedEventsMap().values()){
			 * sortedMap.put(programEvent.getId(), programEvent); }
			 */
			List<ProgramEvent> repeatingList = new ArrayList<ProgramEvent>();
			java.util.Iterator<ProgramEvent> iterator = eventManagerModel
					.getSelectedEventsMap().values().iterator();
			while (iterator.hasNext()) {
				ProgramEvent tmpEvent = iterator.next();
				// System.out.println("aaaa-" + tmpEvent.getName());
				ProgramEvent event = serviceLocator.getProgramEventService()
						.retrieveWithData(tmpEvent.getId(), true, true);
				if (event.getProgram().getType().getName().equals("Individual")) {
					StudentGroup group = event.getGroup();
					List<ProgramEvent> events = null;
					if (indMap.containsKey(group.getId()))
						events = indMap.get(group.getId());
					else {
						events = new ArrayList<ProgramEvent>();
						indMap.put(group.getId(), events);
					}
					events.add(event);
				} else if (event.getProgram().getType().getName()
						.equals("Student") || event.getProgram().getType().getName().equals("Staff")) {
					printList.put(event.getId(), event);
					if(event.getStudentEvents() != null){
						Collections.sort(event.getStudentEvents(),
								new SortObjectByName());
					}
					if(event.getStaffEvents() != null){
						Collections.sort(event.getStaffEvents(),
								new SortObjectByName());
					}
					
				} else if (event.getProgram().getType().getName()
						.equals("Transport")) {
					if (event.getGroup().getParentGroup() == null) {
						ProgramEvent returnEvent = serviceLocator
								.getProgramEventService().getReturnEventWithData(
										event, true, true);
						if (returnEvent != null) {
							Collections.sort(returnEvent.getStudentEvents(),
									new SortEventsBySequence());
							repeatingList.add(returnEvent);
							event.setReturnEvent(returnEvent);
						}
					}
					/*if (event.getReturnEvent() != null) {
						event.setReturnEvent(serviceLocator
								.getProgramEventService().retrieveWithData(
										event.getReturnEvent().getId(), true,
										true));
						Collections
								.sort(event.getReturnEvent().getStudentEvents(),
										new SortEventsBySequence());
						repeatingList.add(event.getReturnEvent());

					}*/
					printList.put(event.getId(), event);
					Collections.sort(event.getStudentEvents(),
							new SortEventsBySequence());
				}
			}

			for (ProgramEvent pe : repeatingList) {
				if (printList.containsKey(pe.getId()))
					printList.remove(pe.getId());
			}

			List<ProgramEvent> finalList = new ArrayList<ProgramEvent>(
					printList.values());
			Collections.sort(finalList, new SortProgramEventsByDate());
			for (List<ProgramEvent> indList : indMap.values())
				Collections.sort(indList, new SortProgramEventsByDate());		
			finalMap.put("Student", finalList);
			finalMap.put("Individual", indMap);

		}
		/***************************
		 * if (individual) { SortedMap<String, ProgramEvent> sortedMap = new
		 * TreeMap<String, ProgramEvent>(
		 * eventManagerModel.getSelectedEventsMap()); for (ProgramEvent event :
		 * sortedMap.values()) { Student student =
		 * event.getStudentEvents().get(0) .getGroupedStudent().getStudent();
		 * List<ProgramEvent> events = null; if (indMap.containsKey(student))
		 * events = indMap.get(student); else { events = new
		 * ArrayList<ProgramEvent>(); indMap.put(student, events); }
		 * events.add(event); programEvents.addAll(sortedMap.values()); } } else
		 * { HashMap<Long, ProgramEvent> printList = new HashMap<Long,
		 * ProgramEvent>(); for (ProgramEvent event : eventManagerModel
		 * .getSelectedEventsMap().values()) { if
		 * (event.getProgram().getType().getName() .equals("Transport")) {
		 * event.setReturnEvent(serviceLocator
		 * .getProgramEventService().getReturnEvent(event)); }
		 * printList.put(event.getId(), event); } List<ProgramEvent>
		 * repeatingList = new ArrayList<ProgramEvent>(); for (ProgramEvent pe :
		 * printList.values()) { if (pe.getReturnEvent() != null &&
		 * printList.containsKey(pe.getReturnEvent() .getId()))
		 * repeatingList.add(pe.getReturnEvent()); } for (ProgramEvent pe :
		 * repeatingList) { printList.remove(pe.getId()); }
		 * programEvents.addAll(printList.values()); } } List<ProgramEvent>
		 * finalList = new ArrayList<ProgramEvent>(); for (ProgramEvent pe :
		 * programEvents) { ProgramEvent event =
		 * serviceLocator.getProgramEventService() .retrieveWithData(pe.getId(),
		 * true, true); if (pe.getReturnEvent() != null) {
		 * event.setReturnEvent(serviceLocator.getProgramEventService()
		 * .retrieveWithData(pe.getReturnEvent().getId(), true, true));
		 * Collections.sort(event.getReturnEvent().getStudentEvents(), new
		 * SortEventsBySequence()); } Collections.sort(event.getStudentEvents(),
		 * new SortEventsBySequence()); finalList.add(event); }
		 */
		String body = VelocityBuilder.attendance(finalMap, individual,
				periodEnd);
		InputStream xsltfile = getClass().getResourceAsStream(
				"/com/itelasoft/xslt/xhtml2fo.xsl");
		TransformerUtil.transformToPdf(xsltfile, new StringReader(body),
				outputStream);
		return outputStream.toByteArray();

	}

	public byte[] pdfInvoice(String sReportFileName, Object managedBean) {
		InvoiceManagerModel invoiceManagerModel = (InvoiceManagerModel) managedBean;
		List<Invoice> invoices = new ArrayList<Invoice>();
		if (sReportFileName.equals("Invoice")) {
			invoices.add(invoiceManagerModel.getInvoice());
		} else {
			List<Invoice> list = new ArrayList<Invoice>();
			list.addAll(invoiceManagerModel.getSelectedInvMap().values());
			for (Invoice inv : serviceLocator.getInvoiceService().setData(list)) {
				invoices.add(invoiceManagerModel.setEventItems(inv));
			}
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String body = VelocityBuilder.invoices(invoices);
		InputStream xsltfile = getClass().getResourceAsStream(
				"/com/itelasoft/xslt/xhtml2fo.xsl");
		TransformerUtil.transformToPdf(xsltfile, new StringReader(body),
				outputStream);
		return outputStream.toByteArray();
	}

	public byte[] pdfUtilized(String sReportFileName, Object managedBean) {
		ReportManagerModel reportManagerModel = (ReportManagerModel) managedBean;
		HoursUtilizedReport report = reportManagerModel
				.getHoursUtilizedReport();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String body = VelocityBuilder.hoursUtilizedReport(report);
		InputStream xsltfile = getClass().getResourceAsStream(
				"/com/itelasoft/xslt/xhtml2fo.xsl");
		TransformerUtil.transformToPdf(xsltfile, new StringReader(body),
				outputStream);
		return outputStream.toByteArray();
	}
	
	public byte[] pdfMailLables(String sReportFileName, Object managedBean) {
		StudentManagerModel studentManagerModel = (StudentManagerModel) managedBean;
		studentManagerModel.selectStudentsStatus();
		List<Student> students=studentManagerModel.getStudents();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String body = VelocityBuilder.MailLables(students);
		InputStream xsltfile = getClass().getResourceAsStream(
				"/com/itelasoft/xslt/xhtml2foMails.xsl");
		TransformerUtil.transformToPdf(xsltfile, new StringReader(body),
				outputStream);
		return outputStream.toByteArray();
	}

}
