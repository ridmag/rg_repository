package com.itelasoft.pso.services;

import java.util.Date;
import com.itelasoft.pso.beans.HoursUtilizedReport;
import com.itelasoft.pso.dao.IHoursUtilizedReportDao;

public class HoursUtilizedReportService extends
		GenericService<HoursUtilizedReport, Long> implements
		IHoursUtilizedReportService {
	private IHoursUtilizedReportDao utilizedReportDao;

	public void setUtilizedReportDao(IHoursUtilizedReportDao utilizedReportDao) {
		this.utilizedReportDao = utilizedReportDao;
	}

	public IHoursUtilizedReportDao getUtilizedReportDao() {
		return utilizedReportDao;
	}

	public HoursUtilizedReport retrieveUtilizationReport(Date fromDate,
			Date toDate) {
		return this.utilizedReportDao.retrieveUtilizationReport(fromDate,
				toDate);
	}
}
