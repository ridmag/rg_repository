package com.itelasoft.sample.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.web.UIModel;

public class StudentReport extends UIModel {
	private List<Student> students;
	private String pageTitle = "Student Details";

	public void build(ServletOutputStream outputStream) {
		this.students = serviceLocator.getStudentService().findAll();
		if (students != null && !students.isEmpty()) {
			TextColumnBuilder<String> idColumn = col.column("Student Id", "id",
					type.stringType());
			TextColumnBuilder<String> mdsidColumn = col.column("MDSID",
					"mdsid", type.stringType());
			TextColumnBuilder<String> cisidColumn = col.column("CISID",
					"cisid", type.stringType());
			TextColumnBuilder<String> nameColumn = col.column("Name", "name",
					type.stringType());
			TextColumnBuilder<String> dobColumn = col.column("Date of Birth",
					"dob", type.stringType());
			TextColumnBuilder<String> genderColumn = col.column("Gender",
					"gender", type.stringType());
			TextColumnBuilder<String> contactNumberColumn = col.column(
					"Contact Number", "contactNumber", type.stringType());

			try {
				report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(com.itelasoft.pso.web.reports.Templates.createTitleComponent(pageTitle))
						.columns(idColumn, mdsidColumn, cisidColumn,
								nameColumn, dobColumn, genderColumn,
								contactNumberColumn)
								.pageFooter(Templates.footerComponent)
						.setDataSource(createDataSource()).toPdf(outputStream);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private JRDataSource createDataSource() {
		DataSource dataSrc = new DataSource("id", "mdsid", "cisid", "name",
				"dob", "gender", "contactNumber");
		for (Student sd : students) {
			dataSrc.add(sd.getId().toString(), sd.getMdsid(), sd.getCisid(), sd
					.getContact().getName(), sd.getDob().toString(), sd.getGender(), sd
					.getContact().getContactNumber().toString());
		}
		return dataSrc;
	}
}
