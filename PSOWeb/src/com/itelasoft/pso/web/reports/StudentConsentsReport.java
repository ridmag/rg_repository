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

import net.sf.dynamicreports.report.builder.column.BooleanColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.BooleanComponentType;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.beans.Consent;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentConsent;
import com.itelasoft.pso.web.UIModel;
import com.itelasoft.util.SortObjectByName;

import java.util.Collections;

public class StudentConsentsReport extends UIModel {

	private BooleanColumnBuilder[] columns;
	private List<Student> students;
	private List<Consent> consents;

	public void build(ServletOutputStream outputStream, List<String> consentIds) {
		List<Long> ids = new ArrayList<Long>();
		for (String s : consentIds) {
			ids.add(Long.valueOf(s));
		}
		// students = serviceLocator.getReportService().listForNonConsentReport(
		// ids);
		students = serviceLocator.getStudentService().findAll();
		if (students != null && !students.isEmpty()) {
			Collections.sort(students, new SortObjectByName());
			StyleBuilder bold = stl.style().bold();
			StyleBuilder boldWithCenter = stl.style(bold)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			StyleBuilder columnStyle = stl.style(boldWithCenter)
					.setBorder(stl.pen1Point())
					.setBackgroundColor(Color.LIGHT_GRAY);
			StyleBuilder titleStyle = stl.style(boldWithCenter)
					.setVerticalAlignment(VerticalAlignment.MIDDLE)
					.setFontSize(15);

			TextColumnBuilder<Long> idColumn = col.column("Student ID", "id",
					type.longType()).setFixedWidth(40);
			TextColumnBuilder<String> nameColumn = col
					.column("Student Name", "name", type.stringType())
					.setStyle(
							stl.style().setHorizontalAlignment(
									HorizontalAlignment.LEFT))
					.setFixedWidth(130);
			consents = new ArrayList<Consent>();
			for (Long id : ids) {
				Consent con = serviceLocator.getConsentService().retrieve(id);
				consents.add(con);
			}
			columns = new BooleanColumnBuilder[consents.size()];
			int i = 0;
			for (Consent consent : consents) {
				columns[i] = col
						.booleanColumn(consent.getName(), consent.getName())
						.setComponentType(BooleanComponentType.IMAGE_STYLE_4)
						.setImageDimension(10, 10);
				i++;
			}
			try {
				report().setColumnTitleStyle(columnStyle)
						.setColumnStyle(
								stl.style().setHorizontalAlignment(
										HorizontalAlignment.CENTER))
						.highlightDetailOddRows()
						.addColumn(idColumn, nameColumn)
						.addColumn(columns)
						.title(cmp
								.horizontalList()
								.add(cmp.image(
										Templates.class
												.getResource("images/dynamicreports.png"))
										.setFixedDimension(80, 80),
										cmp.text("Students' Consents Report")
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
						.setDataSource(createDataSource()).toPdf(outputStream);
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
		ArrayList<String> list = new ArrayList<String>();
		list.add("id");
		list.add("name");
		for (int i = 0; i < columns.length; i++) {
			list.add(columns[i].getColumn().getName());
		}
		DataSource dataSrc = new DataSource(
				list.toArray(new String[list.size()]));
		for (Student student : students) {
			Object[] values = createValues(student);
			boolean allTrue = true;
			for (int i = 2; i < columns.length + 2; i++) {
				if ((Boolean) values[i] != true) {
					allTrue = false;
				}
			}
			if (!allTrue)
				dataSrc.add(values);
			// dataSrc.add(createValues(student));
		}
		return dataSrc;
	}

	private Object[] createValues(Student student) {
		Object[] values = new Object[columns.length + 2];
		values[0] = student.getId();
		values[1] = student.getContact().getName();
		int i = 2;
		for (BooleanColumnBuilder column : columns) {
			boolean consented = false;
			for (StudentConsent sc : student.getStudentConsents()) {
				if (column.getColumn().getName()
						.equals(sc.getConsent().getName()))
					consented = true;
			}
			values[i] = consented;
			i++;
		}
		return values;
	}
}
