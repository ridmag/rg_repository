package com.itelasoft.pso.services;

import java.util.List;

import org.hibernate.Hibernate;

import com.itelasoft.pso.beans.SpecialNeed;
import com.itelasoft.pso.dao.ISpecialNeedDao;

public class SpecialNeedService extends GenericService<SpecialNeed, Long>
		implements ISpecialNeedService {

	public ISpecialNeedDao specialNeedDao;

	public List<SpecialNeed> listAvailableByNameNStudent(Long studentId,
			String sNeedName) {
		return specialNeedDao.listAvailableByNameNStudent(studentId, sNeedName);
	}

	public List<SpecialNeed> listAvailableByStudent(Long studentId) {
		return specialNeedDao.listAvailableByStudent(studentId);
	}

	public List<SpecialNeed> listSpecialNeedsByStudent(Long studentId) {
		return specialNeedDao.listSpecialNeedsByStudent(studentId);
	}

	public SpecialNeed retrieveByStudent(Long studentId, Long sNeedId) {
		return specialNeedDao.retrieveByStudent(studentId, sNeedId);
	}

	public void setSpecialNeedDao(ISpecialNeedDao specialNeedDao) {
		this.specialNeedDao = specialNeedDao;
	}

	@Override
	public SpecialNeed retrieveEager(Long id) {
		SpecialNeed need = super.retrieve(id);
		if (need.getIcon() != null)
			Hibernate.initialize(need.getIcon().getBlobFileData());
		return need;
	}

	@Override
	public List<SpecialNeed> findAll() {
		List<SpecialNeed> needs = super.findAll();
		for (SpecialNeed need : needs) {
			if (need.getIcon() != null)
				Hibernate.initialize(need.getIcon().getBlobFileData());
		}
		return needs;
	}
}
