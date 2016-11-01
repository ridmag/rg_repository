package com.itelasoft.pso.web.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.column.ComponentColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.StudentGroup;
import com.itelasoft.pso.web.UIModel;

public class StudentTimeTable extends UIModel {

	private HashMap<String, List<ProgramEvent>> eventsMap;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma");
	private Long studentId;
	private int maxEventCount;
	private StyleBuilder tmpTitleStyle = stl.style()
			.setLeftBorder(stl.pen1Point()).setTopBorder(stl.pen1Point())
			.setBottomBorder(stl.pen1Point())
			.setBackgroundColor(Color.LIGHT_GRAY);
	private StyleBuilder tmpColStyle = stl.style()
			.setForegroudColor(Color.white)
			.setLeftBorder(stl.pen1Point().setLineColor(Color.BLACK))
			.setTopBorder(stl.pen1Point().setLineColor(Color.BLACK))
			.setBottomBorder(stl.pen1Point().setLineColor(Color.BLACK));
	private StyleBuilder vListTitleStyle = stl.style()
			.setRightBorder(stl.pen1Point()).setTopBorder(stl.pen1Point())
			.setBottomBorder(stl.pen1Point())
			.setBackgroundColor(Color.LIGHT_GRAY)
			.setHorizontalAlignment(HorizontalAlignment.CENTER);
	private StyleBuilder vListColStyle = stl.style()
			.setRightBorder(stl.pen1Point()).setTopBorder(stl.pen1Point())
			.setBottomBorder(stl.pen1Point())
			.setHorizontalAlignment(HorizontalAlignment.CENTER);

	class GroupName extends AbstractSimpleExpression<String> {
		private static final long serialVersionUID = 1L;
		private String day;

		public String evaluate(ReportParameters reportParameters) {
			List<ProgramEvent> events = reportParameters.getValue(day);
			if (events != null && !events.isEmpty())
				return events.get(0).getGroup().getName();
			return "Home";
		}

		public void setDay(String day) {
			this.day = day;
		}
	}

	class EventString extends AbstractSimpleExpression<String> {
		private static final long serialVersionUID = 1L;
		private String day;

		public String evaluate(ReportParameters reportParameters) {
			List<ProgramEvent> events = reportParameters.getValue(day);
			return generateStringList(events.get(0), studentId);
		}

		public void setDay(String day) {
			this.day = day;
		}
	}

	class GroupImage extends AbstractSimpleExpression<InputStream> {
		private static final long serialVersionUID = 1L;
		private String day;

		public InputStream evaluate(ReportParameters reportParameters) {
			List<ProgramEvent> events = reportParameters.getValue(day);
			if (events != null && !events.isEmpty()) {
				if (events.get(0).getGroup().getPhoto() != null) {
					return new ByteArrayInputStream(events.get(0).getGroup()
							.getPhoto().getBlobFileData().getThumbnail());
				}
				return Templates.class.getResourceAsStream("no_logo.jpg");
			}
			return Templates.class.getResourceAsStream("home.png");
		}

		public void setDay(String day) {
			this.day = day;
		}
	}

	public void build(ServletOutputStream outputStream, Long studentId,
			String studentName, String fromDate, String toDate) {
		Date startDate = convertDate(fromDate);
		Date endDate = convertDate(toDate);
		this.studentId = studentId;
		List<ProgramEvent> eventList = serviceLocator.getProgramEventService()
				.listForTimeTable(startDate, endDate, studentId);
		if (eventList != null && !eventList.isEmpty()) {
			String reportHeader = "Weekly Program of " + studentName + " ["
					+ fromDate + " - " + toDate + "]";
			eventsMap = new HashMap<String, List<ProgramEvent>>();
			int tmpEventCount = maxEventCount = 0;
			for (ProgramEvent event : eventList) {
				if (eventsMap
						.containsKey(dayFormat.format(event.getEventDate()))) {
					eventsMap.get(dayFormat.format(event.getEventDate())).add(
							event);
					tmpEventCount++;
				} else {
					tmpEventCount = 1;
					eventsMap.put(dayFormat.format(event.getEventDate()),
							new ArrayList<ProgramEvent>());
					eventsMap.get(dayFormat.format(event.getEventDate())).add(
							event);
					if (maxEventCount < tmpEventCount)
						maxEventCount = tmpEventCount;
				}
			}

			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				report().setPageFormat(PageType.A4, PageOrientation.LANDSCAPE)
						.setTemplate(Templates.reportTemplate)
						.title(Templates.createTitleComponent(reportHeader))
						.setHighlightDetailEvenRows(false)
						.setHighlightDetailOddRows(false)
						.pageHeader(
								cmp.text(
										"Note: If you are not coming in to your program please ring Wyong Diary on 43 53 3858 to let us know\n")
										.setHorizontalAlignment(
												HorizontalAlignment.CENTER))
						.columns(getTmpColumn("Monday"), getColumn("Monday"),
								getTmpColumn("Tuesday"), getColumn("Tuesday"),
								getTmpColumn("Wednesday"),
								getColumn("Wednesday"),
								getTmpColumn("Thursday"),
								getColumn("Thursday"), getTmpColumn("Friday"),
								getColumn("Friday"))
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

	@SuppressWarnings("rawtypes")
	private TextColumnBuilder<List> getTmpColumn(String day) {
		TextColumnBuilder<List> tmpColumn = col
				.column("", day, type.listType()).setTitleStyle(tmpTitleStyle)
				.setStyle(tmpColStyle).setFixedWidth(1)
				.setStretchWithOverflow(false);
		return tmpColumn;
	}

	private ComponentColumnBuilder getColumn(String day) {
		VerticalListBuilder vList = cmp.verticalList(
				cmp.text(getGroupName(day))
						.setStyle(stl.style().bold().underline().setPadding(5))
						.setHorizontalAlignment(HorizontalAlignment.CENTER),
				cmp.text(getEventString(day))
						.setStyle(
								stl.style()
										.setAlignment(HorizontalAlignment.LEFT,
												VerticalAlignment.TOP)
										.setPadding(5)).setFixedHeight(250),
				cmp.image(getGroupImage(day))
						.setStyle(
								stl.style()
										.setPadding(10)
										.setAlignment(
												HorizontalAlignment.CENTER,
												VerticalAlignment.MIDDLE)
										.setTopBorder(stl.pen1Point()))
						.setHeight(153));
		ComponentColumnBuilder column = col.componentColumn(vList)
				.setTitle(day).setTitleStyle(vListTitleStyle)
				.setStyle(vListColStyle);
		return column;
	}

	private GroupName getGroupName(String day) {
		GroupName gn = new GroupName();
		gn.setDay(day);
		return gn;
	}

	private EventString getEventString(String day) {
		EventString es = new EventString();
		es.setDay(day);
		return es;
	}

	private GroupImage getGroupImage(String day) {
		GroupImage gi = new GroupImage();
		gi.setDay(day);
		return gi;
	}

	public InputStream getInputStream(StudentGroup group) {
		ByteArrayInputStream stream;
		if (group.getPhoto() != null) {
			stream = new ByteArrayInputStream(group.getPhoto().getBlobFileData().getData());
		} else {
			return Templates.class.getResourceAsStream("no_logo.jpg");
		}
		return stream;
	}

	private String generateStringList(ProgramEvent event, Long studentId) {
		String events = "";
		events = events + "Where: " + event.getLocation().getName() + "\n"
				+ "When: " + timeFormat.format(event.getStartTime()) + " - "
				+ timeFormat.format(event.getEndTime()) + "\n" + "Cost: $"
				+ event.getChargeAmount() + "\n";
		List<ProgramEvent> transports = serviceLocator.getProgramEventService()
				.listTransportForStudentPrograms(event.getEventDate(),
						studentId, event.getLocation().getId());
		if ((transports != null) && (!transports.isEmpty())) {
			events = events + "Transport: Options \n";
			for (ProgramEvent eve : transports) {
				GroupedStudent gstu = serviceLocator.getGroupedStudentService()
						.retrieveGroupedStudent(eve.getGroup().getId(),
								studentId);
				if (gstu != null)
					events = events + "From: " + gstu.getPickup().getName()
							+ "\n" + "To: " + gstu.getDropoff().getName()
							+ "\n";
				events = events + "Time: "
						+ timeFormat.format(eve.getStartTime()) + " - "
						+ timeFormat.format(eve.getEndTime()) + "\n";
				events = events + "Cost: $" + eve.getChargeAmount() + "\n";
			}
		} else {
			events = events + "Transport: Family \nCost: N/A\n";
		}
		events = events + "Bring: \n";
		return events;
	}

	private JRDataSource createDataSource() {
		DataSource dataSrc = new DataSource("Monday", "Tuesday", "Wednesday",
				"Thursday", "Friday");
		for (int i = 0; i < maxEventCount; i++) {
			dataSrc.add(getEventData("Monday", i), getEventData("Tuesday", i),
					getEventData("Wednesday", i), getEventData("Thursday", i),
					getEventData("Friday", i));
		}
		return dataSrc;
	}

	private List<ProgramEvent> getEventData(String day, int count) {
		List<ProgramEvent> list = eventsMap.get(day);
		if (list != null && !list.isEmpty()) {
			if (list.size() > count) {
				List<ProgramEvent> returnList = new ArrayList<ProgramEvent>();
				returnList.add(list.get(count));
				return returnList;
			}
		}
		return null;
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
