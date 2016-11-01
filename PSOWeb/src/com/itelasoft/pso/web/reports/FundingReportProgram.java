package com.itelasoft.pso.web.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.ctab;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.dynamicreports.jasper.builder.export.ExporterBuilders;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.web.UIModel;

public class FundingReportProgram extends UIModel {
	List<Object[]> records = new ArrayList<Object[]>();

	public void build(ServletOutputStream outputStream) {
		records = serviceLocator.getReportService().getFundingRecords();
		if (records != null && !records.isEmpty()) {
			CrosstabRowGroupBuilder<String> program = ctab.rowGroup("program",
					String.class);
			CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("group",
					String.class).setShowTotal(false);
			CrosstabColumnGroupBuilder<String> columnGroup = ctab.columnGroup(
					"body", String.class);
			CrosstabBuilder crosstab = ctab
					.crosstab()
					.headerCell(
							cmp.text("Cost Center & Program / Funding Source")
									.setStyle(Templates.boldCenteredStyle))
					.rowGroups(program, rowGroup)
					.columnGroups(columnGroup)
					.measures(
							ctab.measure("Amount ($)", "amount",
									BigDecimal.class, Calculation.SUM)
									.setHorizontalAlignment(
											HorizontalAlignment.CENTER));

			try {
				JasperHtmlExporterBuilder builder = new ExporterBuilders()
						.htmlExporter(outputStream);
				builder.setUsingImagesToAlign(false);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(Templates
								.createTitleComponent("All Not-Banked Fundings Report as at "
										+ dateFormat.format(new Date())))
						.summary(crosstab)
						.pageFooter(Templates.footerComponent)
						.setDataSource(createDataSource()).toPdf(outputStream);
			} catch (DRException e) {
				e.printStackTrace();
			}
		} else {
			try {
				report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(Templates
								.createTitleComponent("No Records Found.."))
						.toPdf(outputStream);
			} catch (DRException e) {
				e.printStackTrace();
			}
		}
	}

	public JRDataSource createDataSource() {
		DataSource dataSource = new DataSource("program", "group", "body",
				"amount");
		if (records != null && !records.isEmpty()) {
			for (Object[] record : records) {
				dataSource.add(record[0], record[1], record[2], new BigDecimal(
						(Double) record[3]));
			}
		}
		return dataSource;
	}

}
