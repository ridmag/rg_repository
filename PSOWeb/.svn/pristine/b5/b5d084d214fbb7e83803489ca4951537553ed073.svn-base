package com.itelasoft.pso.web.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.ctab;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.dynamicreports.jasper.builder.export.ExporterBuilders;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsExporterBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabMeasureBuilder;
import net.sf.dynamicreports.report.builder.crosstab.CrosstabRowGroupBuilder;
import net.sf.dynamicreports.report.constant.Calculation;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.expression.DRIValueFormatter;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.web.UIModel;

public class AbsenteeReportAll extends UIModel {
	private List<Object[]> absentees = new ArrayList<Object[]>();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	// private SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
	private List<Date> dates;

	public void build(ServletOutputStream outputStream, String startDate,
			String endDate) {
		if (startDate == null || endDate == null)
			return;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		JasperHtmlExporterBuilder builder = new ExporterBuilders()
				.htmlExporter(byteArrayOutputStream);
		builder.setUsingImagesToAlign(false);
		builder.setRemoveEmptySpaceBetweenRows(false);
		absentees = serviceLocator.getReportService().getGroupAbsentees(
				startDate, endDate);
		if (absentees != null && !absentees.isEmpty()) {
			createDateList(conevertDate(startDate), conevertDate(endDate));

			CrosstabRowGroupBuilder<String> rowGroupMDSID = ctab.rowGroup(
					"mdsid", String.class);

			CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("student",
					String.class).setShowTotal(false);
			CrosstabColumnGroupBuilder<Date> columnGroup = ctab.columnGroup(
					"date", Date.class);
			columnGroup.setHeaderValueFormatter(new DRIValueFormatter<String, Date>() {
				private static final long serialVersionUID = 1L;
				@Override
				public String format(Date arg0, ReportParameters arg1) {
					return dateFormat.format(arg0);
				}
				@Override
				public Class<String> getValueClass() {
					return String.class;
				}
			});
			CrosstabMeasureBuilder<BigDecimal> hoursMeasure = ctab.measure(
					"Time(hh:mm)", "time", BigDecimal.class, Calculation.SUM);

			hoursMeasure
					.setValueFormatter(new DRIValueFormatter<String, BigDecimal>() {
						private static final long serialVersionUID = 1L;

						@Override
						public String format(BigDecimal timeDiff,
								ReportParameters arg1) {
							if (!timeDiff.toString().equals("0")) {
								BigDecimal[] diff = timeDiff
										.divideAndRemainder(new BigDecimal(60));
								String time = (diff[0]
										.compareTo(BigDecimal.TEN) < 0 ? "0"
										: "")
										+ diff[0].toString()
										+ ":"
										+ (diff[1].compareTo(BigDecimal.TEN) < 0 ? "0"
												: "") + diff[1].toString();
								// return diff[0].toString() + ":" + diff[1];
								return time;
							} else
								return "-";
						}

						@Override
						public Class<String> getValueClass() {
							return String.class;
						}
					});

			hoursMeasure.setHorizontalAlignment(HorizontalAlignment.CENTER);

			// ConditionalStyleBuilder condition1 = stl
			// .conditionalStyle(
			// net.sf.dynamicreports.report.builder.DynamicReports.cnd
			// .equal(hoursMeasure,
			// (Number) new Integer(0)))
			// .setForegroudColor(Color.WHITE)
			// .setBorder(
			// new StyleBuilders().pen().setLineColor(Color.BLACK));
			// hoursMeasure.setStyle(stl.style().conditionalStyles(condition1));

			CrosstabBuilder crosstab = ctab
					.crosstab()
					.headerCell(
							cmp.text("Event Dates").setStyle(
									Templates.boldCenteredStyle),
							cmp.text("MDSID & Student Names").setStyle(
									Templates.boldCenteredStyle))
					.addRowGroup(rowGroupMDSID).addRowGroup(rowGroup)
					.columnGroups(columnGroup).measures(hoursMeasure);

			try {
				JasperXlsExporterBuilder exporterBuilder = new ExporterBuilders()
						.xlsExporter(byteArrayOutputStream);
				exporterBuilder.setCollapseRowSpan(true);

				report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(Templates
								.createTitleComponent("Absentee Report of Students"))
						.summary(crosstab)
						.pageFooter(Templates.footerComponent)
						.setDataSource(createDataSource())
						.toPdf(byteArrayOutputStream);
				;
			} catch (DRException e) {
				e.printStackTrace();
			}
		} else {

			try {
				report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(Templates
								.createTitleComponent("No completed events found.."))
						.toPdf(byteArrayOutputStream);
			} catch (DRException e) {
				e.printStackTrace();
			}
		}
		try {
			outputStream.write(byteArrayOutputStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private JRDataSource createDataSource() {
		DataSource dataSource = new DataSource("mdsid", "student", "date",
				"time");
		if (absentees != null && !absentees.isEmpty()) {
			for (Object[] absentee : absentees) {
				//String eventDate = dateFormat.format(absentee[5]);
				for (Date day : dates) {
					BigDecimal timeDiff = new BigDecimal((Double) absentee[6]);
					if ((Boolean) absentee[0]) {
						if (day.equals(absentee[5])) {
							dataSource.add(absentee[1], absentee[2] + " "
									+ absentee[3] + " " + absentee[4], day,
									timeDiff);
						} else {
							dataSource.add(absentee[1], absentee[2] + " "
									+ absentee[3] + " " + absentee[4], day,
									null);
						}
					} else {
						if (day.equals(absentee[5])) {
							dataSource.add("", absentee[2] + " " + absentee[3]
									+ " " + absentee[4], day, timeDiff);
						} else {
							dataSource.add("", absentee[2] + " " + absentee[3]
									+ " " + absentee[4], day, null);
						}
					}
				}
			}
		}
		return dataSource;
	}

	private Date conevertDate(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			if (dateString != null && !dateString.isEmpty())
				date = dateFormat.parse(dateString);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return date;
	}

	private void createDateList(Date start, Date end) {
		dates = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		Date incDate = start;
		calendar.setTime(end);
		calendar.add(Calendar.DATE, 1);
		end = calendar.getTime();
		calendar.setTime(start);
		while (incDate.before(end)) {
			// if (!dayFormat.format(incDate).equals("Saturday")
			// && !dayFormat.format(incDate).equals("Sunday"))
			dates.add(incDate);
			calendar.add(Calendar.DATE, 1);
			incDate = calendar.getTime();
		}
	}

}
