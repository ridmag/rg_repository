package com.itelasoft.pso.web.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.web.UIModel;

public class StudentEventsReport extends UIModel {
	private List<Object[]> studentEvents = new ArrayList<Object[]>();

	public void build(ServletOutputStream outputStream, String startDate,
			String endDate) {
		studentEvents = serviceLocator.getReportService()
				.listStudentsWithEvents(startDate, endDate);
		if (studentEvents != null && !studentEvents.isEmpty()) {
			StyleBuilder bold = stl.style().bold();
			StyleBuilder boldWithCenter = stl.style(bold)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			StyleBuilder columnStyle = stl.style(boldWithCenter)
					.setBorder(stl.pen1Point())
					.setBackgroundColor(Color.LIGHT_GRAY);
			StyleBuilder titleStyle = stl.style(boldWithCenter)
					.setVerticalAlignment(VerticalAlignment.MIDDLE)
					.setFontSize(15);

			TextColumnBuilder<String> studentColumn = col
					.column("Student Name", "student", type.stringType())
					.setStyle(
							stl.style().setHorizontalAlignment(
									HorizontalAlignment.LEFT))
					.setFixedWidth(130).setPrintRepeatedDetailValues(false);
			TextColumnBuilder<String> programColumn = col.column(
					"Cost Center", "program", type.stringType()).setStyle(
					stl.style()
							.setHorizontalAlignment(HorizontalAlignment.LEFT));
			TextColumnBuilder<String> groupColumn = col
					.column("Program", "group", type.stringType())
					.setStyle(
							stl.style().setHorizontalAlignment(
									HorizontalAlignment.LEFT))
					.setFixedWidth(130);
			TextColumnBuilder<String> eventColumn = col.column("Event Name",
					"event", type.stringType()).setStyle(
					stl.style()
							.setHorizontalAlignment(HorizontalAlignment.LEFT));

			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				report().setColumnTitleStyle(columnStyle)
						.setColumnStyle(
								stl.style().setHorizontalAlignment(
										HorizontalAlignment.CENTER))
						.highlightDetailOddRows()
						.columns(studentColumn, programColumn, groupColumn,
								eventColumn)
						.title(cmp
								.horizontalList()
								.add(cmp.image(
										Templates.class
												.getResource("images/dynamicreports.png"))
										.setFixedDimension(80, 80),
										cmp.text("Student Events")
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
						.setDataSource(createDataSource())
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
		DataSource dataSrc = new DataSource("student", "program", "group",
				"event");

		if (studentEvents != null && !studentEvents.isEmpty()) {
			for (Object[] event : studentEvents) {
				String studentName = event[0] + " " + event[1] + " " + event[2];
				String eventName = event[5] + " " + event[3];
				dataSrc.add(studentName, event[4], event[3], eventName);
			}
		}
		return dataSrc;
	}
}
