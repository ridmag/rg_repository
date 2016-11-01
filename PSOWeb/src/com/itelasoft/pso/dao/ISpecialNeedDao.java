package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.SpecialNeed;

public interface ISpecialNeedDao extends IGenericDao<SpecialNeed, Long> {

	public List<SpecialNeed> listAvailableByNameNStudent(Long studentId,
			String sNeedName);

	public List<SpecialNeed> listAvailableByStudent(Long studentId);

	public List<SpecialNeed> listSpecialNeedsByStudent(Long studentId);

	public SpecialNeed retrieveByStudent(Long studentId, Long sNeedId);

}
