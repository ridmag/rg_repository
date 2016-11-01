package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.StudentFundingSource;

public interface IStudentFundingSourceService extends
		IGenericService<StudentFundingSource, Long> {

	public List<StudentFundingSource> listStudentFundingSourcesByStudent(
			Long studentId);

	public StudentFundingSource getRelatedStudentFundingSource(Long studentId,
			Date eventDate);

	public double getUsedHoursForTheFortnight(Long studentId, Date fromDate,
			Date toDate);
}

/*
 * <bean id="fundingSourceService"
 * class="com.itelasoft.pso.services.FundingSourceService"> <property name="dao"
 * ref="fundingSourceDao" /> </bean> public IFundingSourceService
 * getFundingSourceService(){ return
 * (IFundingSourceService)context.getBean("fundingSourceService"); }
 */