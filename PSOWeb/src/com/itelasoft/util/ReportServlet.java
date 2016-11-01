package com.itelasoft.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.itelasoft.pso.web.reports.AbsenteeReportAll;
import com.itelasoft.pso.web.reports.AbsenteeReportIndividual;
import com.itelasoft.pso.web.reports.AnniversaryReport;
import com.itelasoft.pso.web.reports.CollectionsReport;
import com.itelasoft.pso.web.reports.CriminalReport;
import com.itelasoft.pso.web.reports.DailyReceipt;
import com.itelasoft.pso.web.reports.EventReport;
import com.itelasoft.pso.web.reports.EventsCompletionReport;
import com.itelasoft.pso.web.reports.FundingReportProgram;
import com.itelasoft.pso.web.reports.OutstandingInvoiceReport;
import com.itelasoft.pso.web.reports.RosterReport;
import com.itelasoft.pso.web.reports.StaffEventsReport;
import com.itelasoft.pso.web.reports.StudentConsentsReport;
import com.itelasoft.pso.web.reports.StudentEventsReport;
import com.itelasoft.pso.web.reports.StudentTimeTable;
import com.itelasoft.pso.web.reports.TimeSheet;
import com.itelasoft.sample.reports.StudentReport;

/**
 * Servlet implementation class ReportServlet
 */
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String params = request.getParameter("params");
		// response.setContentType("application/docx");

		if (name.equals("dailyReceipt")) {
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				new DailyReceipt().build(response.getOutputStream(),
						(String) jsonObject.get("eventDate"));
			}
		} else if (name.equals("fundingReport")) {
			new FundingReportProgram().build(response.getOutputStream());
		} else if (name.equals("absenteeReportIndividual")) {
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				Long studentId = Long.valueOf((String) jsonObject
						.get("studentId"));
				new AbsenteeReportIndividual().build(
						response.getOutputStream(),
						(String) jsonObject.get("fromDate"),
						(String) jsonObject.get("toDate"), studentId);
			}
		} else if (name.equals("absenteeReportAll")) {
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				new AbsenteeReportAll().build(response.getOutputStream(),
						(String) jsonObject.get("startDate"),
						(String) jsonObject.get("endDate"));
			}
		} else if ("anniversaryReport".equals(name)) {
			int month = 0;
			int year = 0;
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				month = (Integer) jsonObject.get("month");
				year = (Integer) jsonObject.get("year");
			}
			new AnniversaryReport().build(response.getOutputStream(), month,
					year);
		} else if (name.equals("invoiceReport")) {
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				new OutstandingInvoiceReport().build(
						response.getOutputStream(),
						(String) jsonObject.get("startDate"),
						(String) jsonObject.get("endDate"));
			}
		} else if (name.equals("eventCompletion")) {
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				new EventsCompletionReport().build(response.getOutputStream(),
						(String) jsonObject.get("reportType"));
			}
		} else if (name.equals("consentReport")) {
			if (params != null && !params.isEmpty()) {
				List<String> consentIds = new ArrayList<String>();
				JSONObject jsonObject = JSONObject.fromObject(params);
				for (Object id : jsonObject.values()) {
					consentIds.add((String) id);
				}
				new StudentConsentsReport().build(response.getOutputStream(),
						consentIds);
			}
		} else if (name.equals("criminalReport")) {
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				new CriminalReport().build(response.getOutputStream(),
						(String) jsonObject.get("cutOffDate"));
			}
		} else if ("studentTimeTable".equals(name)) {
			JSONObject jsonObject = JSONObject.fromObject(params);
			if (params != null && !params.isEmpty()) {
				new StudentTimeTable().build(response.getOutputStream(),
						new Long((Integer) jsonObject.get("studentId")),
						(String) jsonObject.get("studentName"),
						(String) jsonObject.get("fromDate"),
						(String) jsonObject.get("toDate"));
			}
		} else if (name.equals("studentEventsReport")) {
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				new StudentEventsReport().build(response.getOutputStream(),
						(String) jsonObject.get("startDate"),
						(String) jsonObject.get("endDate"));
			}
		} else if (name.equals("eventReport")) {
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				new EventReport().build(response.getOutputStream(), new Long(
						(Integer) jsonObject.get("selectedProgramId")),
						new Long((Integer) jsonObject.get("selectedGroupId")),
						(List<String>) jsonObject.get("selectedStatuses"),
						(String) jsonObject.get("fromDate"),
						(String) jsonObject.get("toDate"));
			}
		} else if (name.equals("timeSheet")) {
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				try {
					new TimeSheet().build(response.getOutputStream(),
							(String) jsonObject.get("startDate"),
							(String) jsonObject.get("endDate"),
							(String) jsonObject.get("staffId"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   }
		}else if (name.equals("staffEventsReport")) {
				if (params != null && !params.isEmpty()) {
					JSONObject jsonObject = JSONObject.fromObject(params);
					try {
						new StaffEventsReport().build(response.getOutputStream(),
								(String) jsonObject.get("startDate"),
								(String) jsonObject.get("endDate"),
								(String) jsonObject.get("staffId"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		} else if (name.equals("rosterReport")) {
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				new RosterReport().build(response.getOutputStream(),
						(String) jsonObject.get("startDate"),
						(String) jsonObject.get("endDate"));
			}
		} else if (name.equals("collectionsReportIndividual")) {
			if (params != null && !params.isEmpty()) {
				JSONObject jsonObject = JSONObject.fromObject(params);
				new CollectionsReport().build(response.getOutputStream(),
						new Long((Integer) jsonObject.get("studentId")),
						new Long((Integer) jsonObject.get("collectionId")));
			}
		} else if (name.equals("student360View")) {
			if (params != null && !params.isEmpty()) {
				// JSONObject jsonObject = JSONObject.fromObject(params);
				// Long studentId = new Long((Integer)
				// jsonObject.get("studentId"));
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				response.sendRedirect("front/Student360View.jsf");
			}
		} else if (name.equals("sample")) {
			new StudentReport().build(response.getOutputStream());
		} else
			response.getWriter().print(
					"Report not supported:" + request.getParameter("params"));
	}
    
}
