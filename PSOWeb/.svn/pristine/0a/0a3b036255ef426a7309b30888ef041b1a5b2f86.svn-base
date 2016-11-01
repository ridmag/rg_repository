package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.NdisAncillaryCost;
import com.itelasoft.pso.beans.NdisContribution;
import com.itelasoft.pso.dao.INdisContributionDao;

public class NdisContributionService extends GenericService<NdisContribution, Long>
		implements INdisContributionService {

	private INdisContributionDao ndisContributionDao;

	public void setNdisContributionDao(INdisContributionDao ndisContributionDao) {
		this.ndisContributionDao = ndisContributionDao;
	}

	public Double getTotalDayProgramFund(Long studentId) {
		return ndisContributionDao.getTotalDayProgramFund(studentId);
	}

	public Double getTotalIndProgramFund(Long studentId) {
		return ndisContributionDao.getTotalIndProgramFund(studentId);
	}

	public Double getTotalTransProgramFund(Long studentId) {
		return ndisContributionDao.getTotalTransProgramFund(studentId);
	}
	
	@Override
	public List<NdisContribution> listNdisContributionByStudent(Long id) {
		return ndisContributionDao.listNdisContributionByStudent(id);
	}

}