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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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

import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.web.UIModel;
import com.itelasoft.util.SortObjectByName;

public class AnniversaryReport extends UIModel {

	private List<StaffMember> staffList;

	public void build(ServletOutputStream outputStream, int month, int year) {
		staffList = serviceLocator.getReportService().listStaffByJoinedDate(
				month);
		if (staffList != null && !staffList.isEmpty()) {
			Collections.sort(staffList, new SortObjectByName());
			StyleBuilder bold = stl.style().bold();
			StyleBuilder boldWithCenter = stl.style(bold)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			StyleBuilder columnStyle = stl.style(boldWithCenter)
					.setBorder(stl.pen1Point())
					.setBackgroundColor(Color.LIGHT_GRAY);
			StyleBuilder titleStyle = stl.style(boldWithCenter)
					.setVerticalAlignment(VerticalAlignment.MIDDLE)
					.setFontSize(15);

			TextColumnBuilder<String> nameColumn = col.column("Name", "name",
					type.stringType()).setStyle(
					stl.style()
							.setHorizontalAlignment(HorizontalAlignment.LEFT));
			TextColumnBuilder<String> dateColumn = col.column("Joined Date",
					"date", type.stringType());
			TextColumnBuilder<String> typeColumn = col.column("Staff Type",
					"type", type.stringType());
			TextColumnBuilder<Integer> yearsColumn = col.column("No of Years",
					"years", type.integerType());

			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				report().setColumnTitleStyle(columnStyle)
						.setColumnStyle(
								stl.style().setHorizontalAlignment(
										HorizontalAlignment.CENTER))
						.highlightDetailOddRows()
						.columns(nameColumn, typeColumn, dateColumn,
								yearsColumn)
						.title(cmp
								.horizontalList()
								.add(cmp.image(
										Templates.class
												.getResource("images/dynamicreports.png"))
										.setFixedDimension(80, 80),
										cmp.text("Staff Anniversary Report")
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
						.setDataSource(createDataSource(year))
						.toPdf(byteArrayOutputStream);
				outputStream.write(byteArrayOutputStream.toByteArray());
			} catch (DRException e) {
				showError("Error occured.. Report not created..");
			} catch (IOException e) {
				// TODO Auto-generated catch block
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

	private JRDataSource createDataSource(int year) {
		Date anivDate = convertDate(year);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(anivDate);
		DataSource dataSrc = new DataSource("name", "type", "date", "years");
		if (staffList != null && !staffList.isEmpty()) {
			for (StaffMember staff : staffList) {
				if (staff.getJoinedDate() != null) {
					cal.setTime(staff.getJoinedDate());
					int joinedYear = cal.get(Calendar.YEAR);
					dataSrc.add(staff.getContact().getName(), staff.getType()
							.getName(),
							dateFormat.format(staff.getJoinedDate()), year
									- joinedYear);
				}
			}
		} else
			dataSrc.add("", "", "", null);
		return dataSrc;
	}

	private Date convertDate(int year) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String dateString = year + "/12/31";
		try {
			if (dateString != null && !dateString.isEmpty())
				date = dateFormat.parse(dateString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return date;
	}

}
