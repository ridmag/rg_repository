package com.itelasoft.pso.web.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.concatenatedReport;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.web.UIModel;

public class RosterReport extends UIModel {

	private List<Program> programs;
	private List<Date> dates;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat dateHeaderFormat = new SimpleDateFormat(
			"dd/MM/yyyy - EEEE");
	private Calendar calendar = Calendar.getInstance();
	private TextColumnBuilder<String>[] events;

	public void build(ServletOutputStream outputStream, String startDate,
			String endDate) {
		Date fromDate = convertDate(startDate);
		Date toDate = convertDate(endDate);
		programs = serviceLocator.getProgramService().createRoster(fromDate,
				toDate, "");
		if (programs != null && !programs.isEmpty()) {
			generateDates(fromDate, toDate);
			List<JasperReportBuilder> list = new ArrayList<JasperReportBuilder>();
			try {
				for (Program program : programs) {
					list.add(createReport(program, startDate, endDate));
				}
				JasperReportBuilder[] reports = new JasperReportBuilder[list
						.size()];
				for (int i = 0; i < list.size(); i++) {
					reports[i] = list.get(i);
				}
				concatenatedReport().concatenate(reports).toPdf(outputStream);
			} catch (DRException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public JasperReportBuilder createReport(Program program, String startDate,
			String endDate) throws DRException {
		// TextColumnBuilder<String> programs = col.column("Programs / Dates",
		// "programs", type.stringType()).setStyle(
		// DynamicReports.stl.style().setLeftPadding(2)
		// .setBorder(DynamicReports.stl.pen1Point()));
		events = new TextColumnBuilder[dates.size()];
		int i = 0;
		for (Date date : dates) {
			events[i] = col.column(dateHeaderFormat.format(date),
					dateHeaderFormat.format(date), type.stringType()).setStyle(
					DynamicReports.stl.style().setFontSize(8)
							.setBorder(DynamicReports.stl.pen1Point()));
			i++;
		}

		StyleBuilder bold = stl.style().bold().setFontSize(14);
		VerticalListBuilder verticalHeaders = cmp.verticalList(
				cmp.horizontalList(cmp
						.text("Roster Report")
						.setStyle(
								stl.style().bold().setFontSize(24)
										.setPadding(10))
						.setHorizontalAlignment(HorizontalAlignment.CENTER)),
				cmp.horizontalList(cmp.text("Period").setFixedWidth(180)
						.setStyle(bold), cmp.text(":").setFixedWidth(10)
						.setStyle(bold), cmp.text(startDate + " - " + endDate)
						.setFixedWidth(200).setStyle(bold)),
				cmp.horizontalList(cmp.text("Program name").setFixedWidth(180)
						.setStyle(bold), cmp.text(":").setFixedWidth(10)
						.setStyle(bold), cmp.text(program.getName())
						.setFixedWidth(200).setStyle(bold)),
				cmp.horizontalList(cmp.text("")));
		JasperReportBuilder report = report()
				.setTemplate(Templates.reportTemplate).title(verticalHeaders)
				.addColumn(events)
				// addColumn(programs)
				// .pageFooter(Templates.footerComponent)
				.setDataSource(createDataSource(program));
		if (events.length == 5)
			report.setPageFormat(PageType.A4, PageOrientation.PORTRAIT);
		else
			report.setPageFormat(PageType.A3, PageOrientation.LANDSCAPE);
		return report;
	}

	public JRDataSource createDataSource(Program pro) {
		ArrayList<String> list = new ArrayList<String>();
		// list.add("programs");
		for (int i = 0; i < events.length; i++) {
			list.add(events[i].getColumn().getName());
		}
		DataSource dataSrc = new DataSource(
				list.toArray(new String[list.size()]));
		// for (Program pro : programs) {
		dataSrc.add(getEvents(pro));
		// }
		return dataSrc;
	}

	private Date convertDate(String dateString) {
		Date date = null;
		try {
			if (dateString != null && !dateString.isEmpty())
				date = dateFormat.parse(dateString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return date;
	}

	private void generateDates(Date fromDate, Date toDate) {
		dates = new ArrayList<Date>();
		Date rosterDate = fromDate;
		calendar.setTime(fromDate);
		do {
			/*if (calendar.get(Calendar.DAY_OF_WEEK) == 1
					|| calendar.get(Calendar.DAY_OF_WEEK) == 7) {
				calendar.add(Calendar.DATE, 1);
				rosterDate = calendar.getTime();
				continue;
			}*/
			dates.add(calendar.getTime());
			calendar.add(Calendar.DATE, 1);
			rosterDate = calendar.getTime();
		} while (rosterDate.equals(toDate) || rosterDate.before(toDate));
	}

	private Object[] getEvents(Program program) {
		Object[] values = new Object[events.length]; // + 1];
		// values[0] = program.getName();
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma");
		int i = 0;
		for (TextColumnBuilder<String> cell : events) {
			String dateString = cell.getColumn().getName();
			String eventList = "";
			for (ProgramEvent pe : program.getProgramEvents()) {
				String eventString = dateHeaderFormat.format(pe.getEventDate());
				if (dateString.equals(eventString)) {
					if (eventList.contains("Event Details")) {
						if (events.length == 5) {
							eventList = eventList + "_________________________"
									+ "\n";
						} else {
							eventList = eventList
									+ "__________________________" + "\n";
						}
					}
					eventList = eventList + "\n Event Details \n";
					if (!pe.getProgram().getType().getName()
							.equals("Transport")) {
						eventList = eventList + "    " + "Location:" + " "
								+ pe.getLocation().getName() + "\n";
					}
					eventList = eventList + "    " + "Group:" + " "
							+ pe.getGroup().getName() + "\n" + "    " + "Time:"
							+ " " + timeFormat.format(pe.getStartTime()) + " "
							+ "-" + " " + timeFormat.format(pe.getEndTime())
							+ "\n" + "    " + "Status:" + " " + pe.getStatus()
							+ "\n";
					if (pe.getProgram().getType().getName().equals("Transport")) {
						if (pe.getVehicle() != null) {
							eventList = eventList + "\n Vehicle Details \n";
							eventList = eventList + "    " + "Name:" + " "
									+ pe.getVehicle().getName() + "\n" + "    "
									+ "Registratin Id:" + " "
									+ pe.getVehicle().getRegistrationId()
									+ "\n";
							if (!pe.getStatus().equals("finished")) {
								eventList = eventList + "    " + "Status:"
										+ " "
										+ pe.getVehicle().getAvailability()
										+ "\n";
							}
						} else {
							eventList = eventList
									+ "\n Vehicle - Not Assigned\n";
						}
					}
					if ((pe.getStaffEvents() != null)
							&& (!pe.getStaffEvents().isEmpty())) {
						eventList = eventList + "\n Staff Members \n";
						for (StaffEvent se : pe.getStaffEvents()) {
							String staffName = se.getStaffMember().getContact()
									.getName();
							if (pe.getStatus().equals("finished")) {
								eventList = eventList + "    " + staffName
										+ "\n";
							} else {
								eventList = eventList + "    " + staffName
										+ "\n" + "                       - "
										+ se.getStaffStatus() + "\n";
							}
						}
					} else {
						eventList = eventList
								+ "\nStaff Members - Not Assigned\n";
					}
				}
			}
			values[i] = eventList;
			i++;
		}
		return values;
	}
}
