package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.StudentFundingSource;

/**
 * Dao class for FundingSourceStudentXRef.
 */
public interface IStudentFundingSourceDao extends
		IGenericDao<StudentFundingSource, Long> {

	public List<StudentFundingSource> listByStudent(Long studentId);

	public StudentFundingSource getRelatedStudentFundingSource(Long studentId,
			Date eventDate);

	public double getUsedHoursForTheFortnight(Long studentId, Date fromDate,
			Date toDate);
}
/*
 * <bean id="fundingSourceStudentXRefDao"
 * class="com.itelasoft.pso.dao.FundingSourceStudentXRefDao"> <property
 * name="sessionFactory" ref="mySessionFactory" /> </bean>
 */