package com.itelasoft.pso.web.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.ExporterBuilders;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.web.UIModel;

public class EventsCompletionReport extends UIModel {

	private List<ProgramEvent> events;
	private String title;

	public void build(ServletOutputStream outputStream, String eventStatus) {
		List<ProgramType> programTypes = new ArrayList<ProgramType>();
		ProgramType proType = serviceLocator.getProgramTypeService()
				.retrieveByName("Student");
		if (proType != null)
			programTypes.add(proType);
		proType = serviceLocator.getProgramTypeService().retrieveByName(
				"Transport");
		if (proType != null)
			programTypes.add(proType);
		proType = serviceLocator.getProgramTypeService().retrieveByName(
				"Individual");
		if (proType != null)
			programTypes.add(proType);
		if (eventStatus.equals("all")) {
			title = "All";
		}
		if (eventStatus.equals("banked")) {
			title = "Banked";
		}
		if (eventStatus.equals("finished")) {
			title = "Completed";
		}
		if (eventStatus.equals("pending")) {
			title = "Incomplete";
		}
		List<String> statuses = new ArrayList<String>();
		if (!eventStatus.equals("all")) {
			statuses.add(eventStatus);
		}
		events = serviceLocator.getProgramEventService().listByCriteria(null,
				programTypes, null, null, null, statuses, null);
		if (events != null && !events.isEmpty()) {
			StyleBuilder bold = stl.style().bold();
			StyleBuilder boldWithCenter = stl.style(bold)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			StyleBuilder columnStyle = stl.style(boldWithCenter)
					.setBorder(stl.pen1Point())
					.setBackgroundColor(Color.LIGHT_GRAY);
			StyleBuilder titleStyle = stl.style(boldWithCenter)
					.setVerticalAlignment(VerticalAlignment.MIDDLE)
					.setFontSize(15);
			TextColumnBuilder<String> programColumn = col.column("Cost Center",
					"program", type.stringType()).setStyle(
					stl.style()
							.setHorizontalAlignment(HorizontalAlignment.LEFT));
			TextColumnBuilder<String> groupColumn = col.column("Program",
					"group", type.stringType()).setStyle(
					stl.style()
							.setHorizontalAlignment(HorizontalAlignment.LEFT));
			TextColumnBuilder<String> dateColumn = col.column("Event Date",
					"date", type.stringType()).setFixedWidth(60);
			TextColumnBuilder<String> staffColumn = col.column("Coordinator",
					"staff", type.stringType()).setStyle(
					stl.style()
							.setHorizontalAlignment(HorizontalAlignment.LEFT));
			TextColumnBuilder<String> startColumn = col.column("Start Time",
					"start", type.stringType()).setFixedWidth(60);
			TextColumnBuilder<String> endColumn = col.column("End Time", "end",
					type.stringType()).setFixedWidth(60);
			TextColumnBuilder<String> locColumn = col.column(
					"Location / Vehicle", "location", type.stringType())
					.setStyle(
							stl.style().setHorizontalAlignment(
									HorizontalAlignment.LEFT));
			try {
				JasperHtmlExporterBuilder builder = new ExporterBuilders()
						.htmlExporter(outputStream);
				builder.setUsingImagesToAlign(false);
				builder.setRemoveEmptySpaceBetweenRows(false);
				JasperReportBuilder report = report()
						.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setColumnTitleStyle(columnStyle)
						.setColumnStyle(
								stl.style().setHorizontalAlignment(
										HorizontalAlignment.CENTER))
						.highlightDetailOddRows()
						.title(cmp
								.horizontalList()
								.add(cmp.image(
										Templates.class
												.getResource("images/dynamicreports.png"))
										.setFixedDimension(80, 80),
										cmp.text(title + " Events Report")
												.setStyle(titleStyle)
												.setHorizontalAlignment(
														HorizontalAlignment.LEFT))
								.newRow()
								.add(cmp.filler()
										.setStyle(
												stl.style().setTopBorder(
														stl.pen2Point()))
										.setFixedHeight(10)))
						.pageFooter(cmp.pageXofY().setStyle(boldWithCenter))
						.setDataSource(createDataSource());

				if (title.equals("All")) {
					TextColumnBuilder<String> statusColumn = col.column(
							"Status", "status", type.stringType())
							.setFixedWidth(60);
					report.columns(dateColumn, programColumn, groupColumn,
							locColumn, staffColumn, startColumn, endColumn,
							statusColumn).toPdf(outputStream);
				} else {
					report.columns(dateColumn, programColumn, groupColumn,
							locColumn, staffColumn, startColumn, endColumn)
							.toPdf(outputStream);
				}
			} catch (DRException e) {
				e.printStackTrace();
			}
		} else {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(Templates
								.createTitleComponent("No Records Found.."))
						.toPdf(byteArrayOutputStream);
				outputStream.write(byteArrayOutputStream.toByteArray());
			} catch (DRException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private JRDataSource createDataSource() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat amPmTime = new SimpleDateFormat("hh:mm aaa");
		DataSource dataSrc;
		if (title.equals("All")) {
			dataSrc = new DataSource("date", "program", "group", "location",
					"staff", "start", "end", "status");
		} else {
			dataSrc = new DataSource("date", "program", "group", "location",
					"staff", "start", "end");
		}
		if (events != null && !events.isEmpty()) {
			for (ProgramEvent pro : events) {
				String locName = "";
				String staffName = "";
				if (pro.getLocation() != null) {
					locName = pro.getLocation().getName();
				}
				if (pro.getVehicle() != null) {
					locName = pro.getVehicle().getRegistrationId();
				}
				if (pro.getCoordinator() != null) {
					staffName = pro.getCoordinator().getContact().getName();
				} else {
					staffName = "Not Assigned";
				}
				if (title.equals("All")) {
					String status;
					if (pro.getStatus() != null
							&& pro.getStatus().equals("pending"))
						status = "Incomplete";
					else if (pro.getStatus() != null
							&& pro.getStatus().equals("finished"))
						status = "Completed";
					else if (pro.getStatus() != null
							&& pro.getStatus().equals("banked"))
						status = "Banked";
					else
						status = "";
					dataSrc.add(dateFormat.format(pro.getEventDate()), pro
							.getProgram().getName(), pro.getGroup().getName(),
							locName, staffName, amPmTime.format(pro
									.getStartTime()), amPmTime.format(pro
									.getEndTime()), status);
				} else {
					dataSrc.add(dateFormat.format(pro.getEventDate()), pro
							.getProgram().getName(), pro.getGroup().getName(),
							locName, staffName, amPmTime.format(pro
									.getStartTime()), amPmTime.format(pro
									.getEndTime()));
				}
			}
		}
		return dataSrc;
	}
}
