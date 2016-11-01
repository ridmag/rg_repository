package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.FundingSource;

/**
 * Dao class for FundingSource.
 */
public interface IFundingSourceDao extends IGenericDao<FundingSource, Long> {

	public boolean validateFundingId(Long fundingId, String code);

	public List<FundingSource> listAvailableByStudent(Long studentId);

	public List<FundingSource> listActiveFundingSources();

}
/*
 * <bean id="fundingSourceDao" class="com.itelasoft.pso.dao.FundingSourceDao">
 * <property name="sessionFactory" ref="mySessionFactory" /> </bean>
 */