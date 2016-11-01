package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.StudentFundingSource;
import com.itelasoft.pso.dao.IStudentFundingSourceDao;

public class StudentFundingSourceService extends
		GenericService<StudentFundingSource, Long> implements
		IStudentFundingSourceService {

	public List<StudentFundingSource> listStudentFundingSourcesByStudent(
			Long studentId) {
		return ((IStudentFundingSourceDao) dao).listByStudent(studentId);
	}

	public StudentFundingSource getRelatedStudentFundingSource(Long studentId,
			Date eventDate) {
		return ((IStudentFundingSourceDao) dao).getRelatedStudentFundingSource(
				studentId, eventDate);
	}

	public double getUsedHoursForTheFortnight(Long studentId, Date fromDate,
			Date toDate) {
		return ((IStudentFundingSourceDao) dao).getUsedHoursForTheFortnight(
				studentId, fromDate, toDate);
	}
}
