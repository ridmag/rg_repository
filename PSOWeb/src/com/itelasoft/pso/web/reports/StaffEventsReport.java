package com.itelasoft.pso.web.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.web.UIModel;

@ManagedBean(name = "staffEvents")
@SessionScoped
public class StaffEventsReport extends UIModel {
	private List<StaffEvent> staffEvents;
	private List<StaffMember> staffList;
	private StaffMember staffMember;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Date sDate, eDate;
	private List<SelectItem> staffSelectItemList = initStaffList();

	public List<SelectItem> initStaffList() {
		staffList = new ArrayList<StaffMember>();
		staffList = serviceLocator.getStaffMemberService().listActiveStaffMembers();
		staffSelectItemList = new ArrayList<SelectItem>();
		if (staffList != null && !staffList.isEmpty()) {
			staffSelectItemList.add(new SelectItem("All", "All Staff Members"));
			for (StaffMember member : staffList) {
				staffSelectItemList.add(new SelectItem(member.getStaffId(), member.getContact().getName()));
			}
		} else {
			staffSelectItemList.add(new SelectItem("", "No Staff Available"));
		}
		return staffSelectItemList;
	}

	public void build(ServletOutputStream outputStream, String startDate, String endDate, String staffId)
			throws DRException {
		List<StaffEvent> tmpStaffList = new ArrayList<StaffEvent>();
		this.sDate = conevertDate(startDate);
		this.eDate = conevertDate(endDate);
		staffEvents = new ArrayList<StaffEvent>();
		if (staffId.equals("All")) {
			List<StaffMember> members = serviceLocator.getStaffMemberService().listAll();
			
			for (StaffMember staffMember : members) {
				this.staffMember = staffMember;
				List<StaffEvent> tempSEvent = new ArrayList<StaffEvent>();
				tmpStaffList = serviceLocator.getStaffEventService()
						.listByStaffNDatesWithAttended(staffMember.getStaffId(), sDate, eDate);
				tempSEvent = calTimeGap(tmpStaffList);
				for (StaffEvent sEvent : tempSEvent) {
					staffEvents.add(sEvent);
				}
			}
		} else {
			staffMember = serviceLocator.getStaffMemberService().searchByStaffId(staffId);
			tmpStaffList = serviceLocator.getStaffEventService().listByStaffNDatesWithAttended(staffMember.getStaffId(),
					sDate, eDate);
			staffEvents = calTimeGap(tmpStaffList);
		}
		createReport(outputStream, staffMember);
	}

	private JasperReportBuilder createReport(ServletOutputStream outputStream, StaffMember member) {
		JasperReportBuilder reportBuilder = new JasperReportBuilder();
		if (staffEvents != null && !staffEvents.isEmpty()) {
			StyleBuilder bold = stl.style().bold();
			StyleBuilder boldWithCenter = stl.style(bold).setHorizontalAlignment(HorizontalAlignment.CENTER);
			StyleBuilder columnStyle = stl.style(boldWithCenter).setBorder(stl.pen1Point())
					.setBackgroundColor(Color.LIGHT_GRAY);
			StyleBuilder titleStyle = stl.style(boldWithCenter).setVerticalAlignment(VerticalAlignment.MIDDLE)
					.setFontSize(15);

			TextColumnBuilder<String> staffColumn = col.column("Staff Name", "staff", type.stringType())
					.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(stl.pen1Point()))
					.setFixedWidth(130).setPrintRepeatedDetailValues(false);
			/*TextColumnBuilder<String> programColumn = col.column("Cost Center", "program", type.stringType())
					.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.LEFT).setBorder(stl.pen1Point()))
					.setFixedWidth(130);*/
			TextColumnBuilder<String> groupColumn = col.column("Program", "group", type.stringType())
					.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(stl.pen1Point()))
					.setFixedWidth(130);
			TextColumnBuilder<String> eventColumn = col.column("Event Date", "event", type.stringType())
					.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(stl.pen1Point()));
			TextColumnBuilder<String> rStartTimeColumn = col
					.column("Roster Start Time", "rStartTime", type.stringType())
					.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(stl.pen1Point()));
			TextColumnBuilder<String> rEndTimeColumn = col.column("Roster End Time", "rEndTime", type.stringType())
					.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(stl.pen1Point()));
			TextColumnBuilder<String> aStartTimeColumn = col
					.column("Actual Start Time", "aStartTime", type.stringType())
					.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(stl.pen1Point()));
			TextColumnBuilder<String> aEndTimeColumn = col.column("Actual End Time", "aEndTime", type.stringType())
					.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(stl.pen1Point()));
			TextColumnBuilder<String> timeDifColumn = col.column("Time Differance", "timeDif", type.stringType())
					.setStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER).setBorder(stl.pen1Point()));

			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				reportBuilder = report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setColumnTitleStyle(columnStyle)
						.setColumnStyle(stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER))
						.highlightDetailOddRows()
						.columns(staffColumn, groupColumn, eventColumn, rStartTimeColumn, rEndTimeColumn,
								aStartTimeColumn, aEndTimeColumn, timeDifColumn)
						.title(cmp.horizontalList()
								.add(cmp.image(Templates.class.getResource("images/dynamicreports.png"))
										.setFixedDimension(100, 100),
								cmp.text("Exceeded Actual times Report").setStyle(titleStyle)
										.setHorizontalAlignment(HorizontalAlignment.LEFT))
								.newRow()
								.add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point()))
										.setFixedHeight(10)))
						.pageFooter(cmp.pageXofY().setStyle(boldWithCenter)).setDataSource(createDataSource())
						.toPdf(byteArrayOutputStream);
				outputStream.write(byteArrayOutputStream.toByteArray());
			} catch (DRException e) {
				showError("Error occured.. Report not created..");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				reportBuilder = report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(Templates.createTitleComponent("No Records Found..")).toPdf(byteArrayOutputStream);
				outputStream.write(byteArrayOutputStream.toByteArray());
			} catch (DRException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return reportBuilder;
	}

	private JRDataSource createDataSource() {
		DataSource dataSrc = new DataSource("staff", "group", "event", "rStartTime", "rEndTime",
				"aStartTime", "aEndTime", "timeDif");

		if (staffEvents != null && !staffEvents.isEmpty()) {
			for (StaffEvent event : staffEvents) {
				String staffName = event.getStaffMember().getContact().getName();
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				String eventDate = date.format(event.getProgramEvent().getEventDate());
				SimpleDateFormat formatter = new SimpleDateFormat("hh.mm.ss a");
				String rStart = formatter.format(event.getProgramEvent().getGroup().getStartTime());
				String rEnd = formatter.format(event.getProgramEvent().getGroup().getEndTime());
				String aStart = formatter.format(event.getStartTime());
				String aEnd = formatter.format(event.getEndTime());
				String timeDif = Double.toString(calTimeGapInEvent(event));

				dataSrc.add(staffName,
						event.getProgramEvent().getGroup().getName(), eventDate, rStart, rEnd, aStart, aEnd, timeDif);
			}
		}
		return dataSrc;
	}

	public List<StaffEvent> calTimeGap(List<StaffEvent> events) {
		List<StaffEvent> list = new ArrayList<StaffEvent>();
		for (StaffEvent event : events) {
			Calendar rStart1 = new GregorianCalendar();
			Calendar rEnd1 = new GregorianCalendar();
			Calendar aStart1 = new GregorianCalendar();
			Calendar aEnd1 = new GregorianCalendar();
			/*rStart1.setTime(event.getProgramEvent().getGroup().getStartTime());
			rEnd1.setTime(event.getProgramEvent().getGroup().getEndTime());*/
			rStart1.setTime(event.getRosterStartTime());
			rEnd1.setTime(event.getRosterEndTime());
			//System.out.println((event.getStartTime()==null)? "Null":"Not Null");
			if(event.getStartTime()!=null){
			aStart1.setTime(event.getStartTime());
			}/*
			else{
				if(event.isAttended()){
				System.out.println("Program id " +event.getProgramEvent().getId() + "date " + event.getProgramEvent().getEventDate());
				}
			}*/
			if(event.getEndTime()!=null){
			aEnd1.setTime(event.getEndTime());
			}/*else{
				if(event.isAttended()){
				System.out.println("Program id " +event.getProgramEvent().getId() + "date " + event.getProgramEvent().getEventDate());
				}
			}*/

			long rTimeDif = rEnd1.getTimeInMillis() - rStart1.getTimeInMillis();
			long aTimeDif = aEnd1.getTimeInMillis() - aStart1.getTimeInMillis();
			if (rTimeDif < aTimeDif) {
				list.add(event);
			}
		}
		return list;
	}

	public double calTimeGapInEvent(StaffEvent event) {
		Calendar rStart1 = new GregorianCalendar();
		Calendar rEnd1 = new GregorianCalendar();
		Calendar aStart1 = new GregorianCalendar();
		Calendar aEnd1 = new GregorianCalendar();

		rStart1.setTime(event.getProgramEvent().getGroup().getStartTime());
		rEnd1.setTime(event.getProgramEvent().getGroup().getEndTime());
		aStart1.setTime(event.getStartTime());
		aEnd1.setTime(event.getEndTime());

		long rTimeDif = rEnd1.getTimeInMillis() - rStart1.getTimeInMillis();
		long aTimeDif = aEnd1.getTimeInMillis() - aStart1.getTimeInMillis();

		double dif = aTimeDif - rTimeDif;
		double hours = dif / 3600000;

		return hours;
	}

	private Date conevertDate(String dateString) {
		Date date = null;
		try {
			if (dateString != null && !dateString.isEmpty())
				date = dateFormat.parse(dateString);
			else
				date = null;
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return date;
	}

	public StaffMember getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public List<StaffEvent> getStaffEvents() {
		return staffEvents;
	}

	public void setStaffEvents(List<StaffEvent> saffEvents) {
		this.staffEvents = saffEvents;
	}

	public Date getStartDate() {
		return sDate;
	}

	public void setStartDate(Date sDate) {
		this.sDate = sDate;
	}

	public Date getEndDate() {
		return eDate;
	}

	public void setEndDate(Date eDate) {
		this.eDate = eDate;
	}

	public List<SelectItem> getStaffSelectItemList() {
		return staffSelectItemList;
	}

	public void setStaffSelectItemList(List<SelectItem> staffSelectItemList) {
		this.staffSelectItemList = staffSelectItemList;
	}

	public List<StaffMember> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<StaffMember> staffList) {
		this.staffList = staffList;
	}
}
