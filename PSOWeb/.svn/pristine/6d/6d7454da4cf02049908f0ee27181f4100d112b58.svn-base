package com.itelasoft.pso.web;

import java.io.IOException;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.icesoft.faces.context.Resource;
import com.itelasoft.pdf.FopEngine;
import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.web.jsf.PdfResource;

@ManagedBean
@SessionScoped
public class PrintModel extends UIModel {
	private String popupName;
	private Resource pdfTestResource;
	private Resource pdfAttendance;
	private Resource pdfAttendanceList;
	private Resource pdfInvoice;
	private Resource pdfInvoiceList;
	private Resource pdfUtilized;
	private Resource pdfTransport;
	private Resource pdfMailLables;
	private boolean printPopupVisible;
	private boolean confirmationPopupVisible;
	private boolean visibleDyanamicPopup;

	public PrintModel() {
		try {
			// FacesContext fc = FacesContext.getCurrentInstance();
			// ExternalContext ec = fc.getExternalContext();
			pdfTestResource = new PdfResource("test", null);
			pdfAttendance = new PdfResource("Attendance",
					Util.getManagedBean("programManagerModel"));
			pdfAttendanceList = new PdfResource("AttendanceList",
					Util.getManagedBean("eventManagerModel"));
			pdfInvoice = new PdfResource("Invoice",
					Util.getManagedBean("invoiceManagerModel"));
			pdfInvoiceList = new PdfResource("InvoiceList",
					Util.getManagedBean("invoiceManagerModel"));
			pdfTransport = new PdfResource("Transport",
					Util.getManagedBean("transportManagerModel"));
			pdfUtilized = new PdfResource("Utilized",
					Util.getManagedBean("reportManagerModel"));
			pdfMailLables= new PdfResource("MailLables",
					Util.getManagedBean("studentManagerModel"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showPdf() {
		FacesContext faces = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) faces
				.getExternalContext().getResponse();
		response.setContentType("application/pdf");
		FopEngine thisEngine = new FopEngine();
		byte[] pdf = thisEngine.pdfAttendance("Attendance",
				Util.getManagedBean("programManagerModel"));
		response.setContentLength(pdf.length);
		response.setHeader("Content-disposition", "inline; filename=\""
				+ "testFile" + "\"");
		try {
			ServletOutputStream out;
			out = response.getOutputStream();
			out.write(pdf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		faces.responseComplete();
	}

	public void togglePrintPopup(ActionEvent actionEvent) {
		if (confirmationPopupVisible)
			confirmationPopup();
		if (printPopupVisible) {
			String type = actionEvent.getComponent().getAttributes()
					.get("confirmation").toString();
			if (type.equals("programAttendance")
					|| type.equals("transportAttendance")
					|| type.equals("attendanceList")) {
				confirmationPopup();
			}
		} else {
			String printType = (String) actionEvent.getComponent()
					.getAttributes().get("target");
			if (printType != null && printType.equals("attendanceList")) {
				EventManagerModel emm = (EventManagerModel) Util
						.getManagedBean("eventManagerModel");
				for (ProgramEvent pro : emm.getSelectedEventsMap().values()) {
					if (pro.getGeneratedDate() == null) {
						showError("All selected event(s) should have an attendance sheet generated..");
						return;
					}
				}
			}
			if (printType != null && printType.equals("invoiceList")) {
				InvoiceManagerModel imm = (InvoiceManagerModel) Util
						.getManagedBean("invoiceManagerModel");
				for (Invoice inv : imm.getSelectedInvMap().values()) {
					if (inv.getId() == null) {
						showError("Only generated statements can be printed.");
						return;
					}
				}
			}
		}
		setPrintPopupVisible(!printPopupVisible);
	}

	public void toggleDyanamicPopup(ActionEvent actionEvent) {
		openDyanmicPopup();
	}

	public void openDyanmicPopup() {
		if (visibleDyanamicPopup)
			visibleDyanamicPopup = false;
		else
			visibleDyanamicPopup = true;
	}

	public void savePrint(ActionEvent actionEvent) {
		String type = actionEvent.getComponent().getAttributes()
				.get("confirmation").toString();
		if (type.equals("attendanceList")) {
			EventManagerModel emm = (EventManagerModel) Util
					.getManagedBean("eventManagerModel");
			for (ProgramEvent pro : emm.getSelectedEventsMap().values()) {
				pro.setPrintedDate(new Date());
				serviceLocator.getProgramEventService().update(pro);
			}
		}
		if (type.equals("programAttendance")) {
			ProgramManagerModel pmm = (ProgramManagerModel) Util
					.getManagedBean("programManagerModel");
			ProgramEvent pro = pmm.getProEvent();
			pro.setPrintedDate(new Date());
			serviceLocator.getProgramEventService().update(pro);
		}
		if (type.equals("transportAttendance")) {
			TransportManager tmm = (TransportManager) Util
					.getManagedBean("transportManagerModel");
			ProgramEvent pro = tmm.getProEvent();
			pro.setPrintedDate(new Date());
			serviceLocator.getProgramEventService().update(pro);
		}
		showInfo("Printed date saved successfully..");
		confirmationPopup();
	}

	/*
	 * public void printConfirmation(List<ProgramEvent> peList){
	 * for(ProgramEvent pro : peList){ pro.setPrintedDate(new Date());
	 * serviceLocator.getProgramEventService().update(pro); }
	 * confirmationPopup(); }
	 */

	public void confirmationPopup() {
		setConfirmationPopupVisible(!confirmationPopupVisible);
	}

	public int getRandomNo() {
		return (int) Math.floor(Math.random() * 1000.0);
	}

	public Date getLastModified() {
		return new Date();
	}

	public void setPopupName(String popupName) {
		this.popupName = popupName;
	}

	public String getPopupName() {
		return popupName;
	}

	public boolean isPrintPopupVisible() {
		return printPopupVisible;
	}

	public void setPrintPopupVisible(boolean printPopupVisible) {
		this.printPopupVisible = printPopupVisible;
	}

	public void setPdfTestResource(Resource pdfTestResource) {
		this.pdfTestResource = pdfTestResource;
	}

	public Resource getPdfTestResource() {
		return pdfTestResource;
	}

	public void setPdfAttendance(Resource pdfAttendance) {
		this.pdfAttendance = pdfAttendance;
	}

	public Resource getPdfAttendance() {
		return pdfAttendance;
	}

	public void setPdfInvoice(Resource pdfInvoice) {
		this.pdfInvoice = pdfInvoice;
	}

	public Resource getPdfInvoice() {
		return pdfInvoice;
	}

	public void setPdfAttendanceList(Resource pdfAttendanceList) {
		this.pdfAttendanceList = pdfAttendanceList;
	}

	public Resource getPdfAttendanceList() {
		return pdfAttendanceList;
	}

	public void setConfirmationPopupVisible(boolean visible) {
		this.confirmationPopupVisible = visible;
	}

	public boolean isConfirmationPopupVisible() {
		return confirmationPopupVisible;
	}

	public void setPdfTransport(Resource pdfTransport) {
		this.pdfTransport = pdfTransport;
	}

	public Resource getPdfTransport() {
		return pdfTransport;
	}

	public boolean isVisibleDyanamicPopup() {
		return visibleDyanamicPopup;
	}

	public void setVisibleDyanamicPopup(boolean visibleDyanamicPopup) {
		this.visibleDyanamicPopup = visibleDyanamicPopup;
	}

	public void setPdfUtilized(Resource pdfUtilized) {
		this.pdfUtilized = pdfUtilized;
	}

	public Resource getPdfUtilized() {
		return pdfUtilized;
	}

	public void setPdfInvoiceList(Resource pdfInvoiceList) {
		this.pdfInvoiceList = pdfInvoiceList;
	}

	public Resource getPdfInvoiceList() {
		return pdfInvoiceList;
	}

	public void setPdfMailLables(Resource pdfMailLables) {
		this.pdfMailLables = pdfMailLables;
	}

	public Resource getPdfMailLables() {
		return pdfMailLables;
	}

}
