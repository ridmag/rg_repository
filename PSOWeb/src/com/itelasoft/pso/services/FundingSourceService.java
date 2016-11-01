package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.FundingSource;
import com.itelasoft.pso.dao.IFundingSourceDao;

public class FundingSourceService extends GenericService<FundingSource, Long>
		implements IFundingSourceService {

	private IFundingSourceDao fundingDao;

	public boolean validateFundingId(Long fundingId, String code) {
		return this.fundingDao.validateFundingId(fundingId, code);
	}

	public void setFundingDao(IFundingSourceDao fundingDao) {
		this.fundingDao = fundingDao;
	}

	public List<FundingSource> listAvailableByStudent(Long studentId) {
		return fundingDao.listAvailableByStudent(studentId);
	}

	public List<FundingSource> listActiveFundingSources() {
		return this.fundingDao.listActiveFundingSources();
	}

}
