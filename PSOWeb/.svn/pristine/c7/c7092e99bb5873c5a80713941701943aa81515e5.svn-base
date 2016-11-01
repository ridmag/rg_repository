package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.FundingSource;

public interface IFundingSourceService extends
		IGenericService<FundingSource, Long> {

	public boolean validateFundingId(Long fundingId, String code);

	public List<FundingSource> listAvailableByStudent(Long studentId);

	public List<FundingSource> listActiveFundingSources();

}

/*
 * <bean id="fundingSourceService"
 * class="com.itelasoft.pso.services.FundingSourceService"> <property name="dao"
 * ref="fundingSourceDao" /> </bean> public IFundingSourceService
 * getFundingSourceService(){ return
 * (IFundingSourceService)context.getBean("fundingSourceService"); }
 */