package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.SpecialNeed;

public interface ISpecialNeedService extends IGenericService<SpecialNeed, Long> {

	public List<SpecialNeed> listAvailableByNameNStudent(Long studentId,
			String sNeedName);

	public List<SpecialNeed> listAvailableByStudent(Long studentId);

	public List<SpecialNeed> listSpecialNeedsByStudent(Long studentId);

	public SpecialNeed retrieveByStudent(Long studentId, Long sNeedId);

	public SpecialNeed retrieveEager(Long id);
}
