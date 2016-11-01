package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.StudentConsent;
import com.itelasoft.pso.dao.IStudentConsentDao;

public class StudentConsentService extends GenericService<StudentConsent, Long>
		implements IStudentConsentService {

	private IStudentConsentDao studentConsentDao;

	public void setStudentConsentDao(IStudentConsentDao studentConsentDao) {
		this.studentConsentDao = studentConsentDao;
	}

	public IStudentConsentDao getStudentConsentDao() {
		return studentConsentDao;
	}

	public List<StudentConsent> listConsentsByStudent(Long studentId) {
		return this.studentConsentDao.listConsentsByStudent(studentId);
	}

	public boolean checkStudentConsentAvailability(Long studentId,
			Long consentId) {
		return this.studentConsentDao.checkStudentConsentAvailability(
				studentId, consentId);
	}
}
