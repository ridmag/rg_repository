package com.itelasoft.pso.web.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.expression.DRIValueFormatter;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.web.UIModel;
import com.itelasoft.util.SortObjectByName;

public class OutstandingInvoiceReport extends UIModel {
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private List<Invoice> invoices;

	public void build(ServletOutputStream outputStream, String startDate,
			String endDate) {
		Date start = conevertDate(startDate);
		Date end = conevertDate(endDate);
		invoices = serviceLocator.getReportService().listOutstandingInvoices(
				start, end);
		if (invoices != null && !invoices.isEmpty()) {
			Collections.sort(invoices, new SortObjectByName());
			StyleBuilder bold = stl.style().bold();
			StyleBuilder boldWithCenter = stl.style(bold)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			StyleBuilder columnStyle = stl.style(boldWithCenter)
					.setBorder(stl.pen1Point())
					.setBackgroundColor(Color.LIGHT_GRAY);
			StyleBuilder titleStyle = stl.style(boldWithCenter)
					.setVerticalAlignment(VerticalAlignment.MIDDLE)
					.setFontSize(15);

			TextColumnBuilder<String> invId = col.column("Statement Id", "id",
					type.stringType());
			TextColumnBuilder<String> stuName = col.column("Statement To",
					"name", type.stringType()).setStyle(
					stl.style()
							.setHorizontalAlignment(HorizontalAlignment.LEFT));
			TextColumnBuilder<String> dateColumn = col.column("Date", "date",
					type.stringType());
			TextColumnBuilder<BigDecimal> toalColumn = col
					.column("Total ($)", "total", type.bigDecimalType())
					.setHorizontalAlignment(HorizontalAlignment.RIGHT)
					.setValueFormatter(
							new DRIValueFormatter<String, BigDecimal>() {
								private static final long serialVersionUID = 1L;

								@Override
								public String format(BigDecimal banked,
										ReportParameters arg1) {
									if (!banked.toString().equals("0")) {
										return "$"
												+ banked.setScale(
														2,
														BigDecimal.ROUND_HALF_UP);
									} else
										return "-";
								}

								@Override
								public Class<String> getValueClass() {
									return String.class;
								}
							});
			try {
				report().setColumnTitleStyle(columnStyle)
						.setColumnStyle(
								stl.style().setHorizontalAlignment(
										HorizontalAlignment.CENTER))
						.highlightDetailOddRows()
						.columns(invId, stuName, dateColumn, toalColumn)
						.title(cmp
								.horizontalList()
								.add(cmp.image(
										Templates.class
												.getResource("images/dynamicreports.png"))
										.setFixedDimension(80, 80),
										cmp.text(
												"Outstanding Activity Statements")
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
				showError("Error occured.. Report not created..");
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
		DataSource dataSrc = new DataSource("id", "name", "date", "total");
		if (invoices != null && !invoices.isEmpty()) {
			for (Invoice inv : invoices) {
				if (inv != null) {
					if (inv.getStudent() != null) {
						dataSrc.add(inv.getId().toString(), inv.getStudent()
								.getContact().getName(),
								dateFormat.format(inv.getInvoiceDate()),
								new BigDecimal(inv.getTotal()));
					}
				}
			}
		}
		return dataSrc;
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
}
