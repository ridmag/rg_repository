package com.itelasoft.pso.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.itelasoft.pso.beans.Guardian;
import com.itelasoft.pso.dao.IGuardianDao;

public class GuardianService implements IGuardianService {

	private IGuardianDao guardianDao = null;

	public void setGuardianDao(IGuardianDao guardianDao) {
		this.guardianDao = guardianDao;
	}

	public Guardian retrive(Long studentId, Long contactId) {
		return this.guardianDao.retrive(studentId, contactId);
	}

	public Guardian create(Guardian guardian)
			throws DataIntegrityViolationException {
		return this.guardianDao.save(guardian);
	}

	public Guardian update(Guardian guardian) {
		return this.guardianDao.update(guardian);
	}

	public int delete(Guardian guardian) {
		return this.guardianDao.delete(guardian);
	}

	public List<Guardian> listByStudent(Long studentId) {
		return guardianDao.listByStudent(studentId);
	}
}
