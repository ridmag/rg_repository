package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.NdisAncillaryCost;
import com.itelasoft.pso.beans.NdisContribution;

public interface INdisContributionDao extends IGenericDao<NdisContribution, Long> {

	public Double getTotalDayProgramFund(Long studentId);

	public Double getTotalIndProgramFund(Long studentId);

	public Double getTotalTransProgramFund(Long studentId);
	
	public List<NdisContribution> listNdisContributionByStudent(Long id);

}