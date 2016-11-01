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

import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.web.UIModel;

public class AbsenteeReportIndividual extends UIModel {

	private List<Object[]> absentees = new ArrayList<Object[]>();
	private List<Date> dates;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	// private SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");

	public void build(ServletOutputStream outputStream, String startDate,
			String endDate, Long studentId) {
		createDateList(conevertDate(startDate), conevertDate(endDate));
		absentees = serviceLocator.getReportService().getAbsentees(
				studentId.toString(), startDate, endDate);
		if (absentees != null && !absentees.isEmpty()) {
			Student student = serviceLocator.getStudentService().retrieve(
					studentId);
			CrosstabRowGroupBuilder<String> rowGroup = ctab.rowGroup("program",
					String.class).setTotalHeader("Total");
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
			CrosstabBuilder crosstab = ctab
					.crosstab()
					.headerCell(
							cmp.text("Program / Event Date").setStyle(
									Templates.boldCenteredStyle))
					.rowGroups(rowGroup).columnGroups(columnGroup)
					.measures(hoursMeasure);
			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(Templates
								.createTitleComponent("Absentee Report of "
										+ student.getContact().getName()))
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
								.createTitleComponent("No completed events found.."))
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
		DataSource dataSource = new DataSource("program", "date", "time");
		if (absentees != null && !absentees.isEmpty()) {
			for (Object[] absentee : absentees) {
				for (Date day : dates) {
					if (day.equals(absentee[1])) {
						BigDecimal timeDiff = new BigDecimal(
								(Double) absentee[2]);
						dataSource.add(absentee[0], day, timeDiff);
					} else {
						dataSource.add(absentee[0], day, new BigDecimal(0));
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
