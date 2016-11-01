package com.itelasoft.pso.web.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.ctab;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import com.itelasoft.pso.beans.Transaction;
import com.itelasoft.pso.web.UIModel;

public class DailyReceipt extends UIModel {

	private List<Transaction> oldtrans;
	private List<Transaction> currentList;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public void build(ServletOutputStream outputStream, String transDate) {
		Date eventDate = conevertDate(transDate);
		oldtrans = serviceLocator.getReportService().getPreviousCollections(
				eventDate);
		currentList = serviceLocator.getReportService().getCurrentCollections(
				eventDate);
		if ((oldtrans != null && !oldtrans.isEmpty())
				|| (currentList != null && !currentList.isEmpty())) {
			CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("content",
					String.class).setHeaderWidth(200);
			CrosstabColumnGroupBuilder<String> columnGroup = ctab.columnGroup(
					"source", String.class);
			CrosstabBuilder crosstab = ctab
					.crosstab()
					.headerCell(
							cmp.text(dateFormat.format(eventDate))
									.setStyle(Templates.columnTitleStyle)
									.setHorizontalAlignment(
											HorizontalAlignment.CENTER))
					.addRowGroup(
							rowGroup.setTotalHeaderStyle(
									Templates.columnTitleStyle
											.setHorizontalAlignment(HorizontalAlignment.LEFT))
									.setHeaderHorizontalAlignment(
											HorizontalAlignment.LEFT))
					.columnGroups(columnGroup)
					.measures(
							ctab.measure("total", Double.class, Calculation.SUM)
									.setHorizontalAlignment(
											HorizontalAlignment.CENTER));
			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(Templates
								.createTitleComponent("Daily Receipt Report of "
										+ dateFormat.format(eventDate)))
						.summary(crosstab)
						.pageFooter(Templates.footerComponent)
						.setDataSource(createDataSource())
						.toPdf(byteArrayOutputStream);
				outputStream.write(byteArrayOutputStream.toByteArray());

			} catch (DRException e) {
				e.printStackTrace();
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
		DataSource dataSource = new DataSource("content", "source", "total");
		if (currentList != null && !currentList.isEmpty()) {
			for (Transaction trans : currentList) {
				dataSource.add("Collected for the Day ($)", trans
						.getStudentEvent().getStdFundingSrc().getFundingSrc()
						.getFundingCode(), trans.getAmount());
			}
		}
		if (oldtrans != null && !oldtrans.isEmpty()) {
			for (Transaction trans : oldtrans) {
				dataSource.add("Collected For Previous Invoices ($)", trans
						.getStudentEvent().getStdFundingSrc().getFundingSrc()
						.getFundingCode(), trans.getAmount());
			}
		}
		return dataSource;
	}

	private Date conevertDate(String dateString) {
		Date date = null;
		try {
			if (dateString != null && !dateString.isEmpty())
				date = dateFormat.parse(dateString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return date;
	}
}
