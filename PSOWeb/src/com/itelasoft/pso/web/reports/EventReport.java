package com.itelasoft.pso.web.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.expression.DRIValueFormatter;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.StudentEvent;
import com.itelasoft.pso.beans.Transaction;
import com.itelasoft.pso.web.UIModel;

public class EventReport extends UIModel {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private List<ProgramEvent> programEvents;
	private String reportTitle = "";

	public void build(ServletOutputStream outputStream, Long selectedProgramId,
			Long selectedGroupId, List<String> selectedStatuses,
			String fromDate, String toDate) {
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
		reportTitle = "Events Report [" + fromDate + " - " + toDate + "]";
		Date startDate = convertDate(fromDate);
		Date endDate = convertDate(toDate);
		programEvents = serviceLocator.getProgramEventService().listByCriteria(
				selectedProgramId, programTypes, selectedGroupId, startDate,
				endDate, null, null);
		if (programEvents != null && !programEvents.isEmpty()) {
			TextColumnBuilder<String> dayColumn = col
					.column("Event Date", "date", type.stringType())
					.setPrintRepeatedDetailValues(false)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			TextColumnBuilder<String> programColumn = col.column("Program",
					"program", type.stringType()).setFixedWidth(130);
			TextColumnBuilder<String> groupColumn = col.column("Group Name",
					"group", type.stringType()).setFixedWidth(130);
			TextColumnBuilder<String> startTimeColumn = col.column(
					"Start Time", "startTime", type.stringType())
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			TextColumnBuilder<String> endTimeColumn = col.column("End Time",
					"endTime", type.stringType()).setHorizontalAlignment(
					HorizontalAlignment.CENTER);
			TextColumnBuilder<String> generatedDateColumn = col.column(
					"Generated Date", "generated", type.stringType())
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			TextColumnBuilder<String> printedDateColumn = col.column(
					"Printed Date", "printed", type.stringType())
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			TextColumnBuilder<String> completedDateColumn = col.column(
					"Completed Date", "completed", type.stringType())
					.setHorizontalAlignment(HorizontalAlignment.CENTER);
			TextColumnBuilder<String> statusColumn = col.column("Status",
					"status", type.stringType()).setHorizontalAlignment(
					HorizontalAlignment.CENTER);
			TextColumnBuilder<BigDecimal> bankedColumn = col.column(
					"Banked Amount", "banked", type.bigDecimalType())
					.setHorizontalAlignment(HorizontalAlignment.CENTER);

			bankedColumn
					.setValueFormatter(new DRIValueFormatter<String, BigDecimal>() {
						private static final long serialVersionUID = 1L;

						@Override
						public String format(BigDecimal banked,
								ReportParameters arg1) {
							if (!banked.toString().equals("0")) {
								return "$"
										+ banked.setScale(2,
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
				report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(Templates.createTitleComponent(reportTitle))
						.columns(dayColumn, programColumn, groupColumn,
								startTimeColumn, endTimeColumn,
								generatedDateColumn, printedDateColumn,
								completedDateColumn, statusColumn, bankedColumn)
						.pageFooter(Templates.footerComponent)
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
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		DataSource dataSrc = new DataSource("date", "program", "group",
				"startTime", "endTime", "generated", "printed", "completed",
				"status", "banked");
		for (ProgramEvent event : programEvents) {
			String status = "";
			if (event.getStatus().equals("pending"))
				status = "Generated";
			if (event.getStatus().equals("finished"))
				status = "Completed";
			if (event.getStatus().equals("banked"))
				status = "Banked";
			String startTime = timeFormat.format(event.getStartTime());
			String endTime = timeFormat.format(event.getEndTime());
			dataSrc.add(
					getDateString(event.getEventDate()),
					event.getProgram().getName(),
					event.getGroup().getName(),
					startTime,
					endTime,
					getDateString(event.getGeneratedDate()),
					getDateString(event.getPrintedDate()),
					getDateString(event.getCompletedDate()),
					status,
					calculateBanked(serviceLocator.getStudentEventService()
							.listByProEventId(event.getId()), event.getStatus()));
		}
		return dataSrc;
	}

	private BigDecimal calculateBanked(List<StudentEvent> studentEventList,
			String eventStatus) {
		double bankedAmount = 0;
		if (eventStatus.equals("Banked")) {
			for (StudentEvent event : studentEventList) {
				for (Transaction tx : event.getPaymentTxs()) {
					if (!tx.getPaymentMethod().equals("EFT"))
						bankedAmount = bankedAmount + tx.getAmount();
				}
			}
		}
		return new BigDecimal(bankedAmount);
	}

	private String getDateString(Date date) {
		if (date != null) {
			return dateFormat.format(date);
		}
		return "-";
	}

	private Date convertDate(String dateString) {
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
