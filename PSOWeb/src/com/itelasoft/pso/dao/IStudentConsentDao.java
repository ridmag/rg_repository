package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.StudentConsent;

public interface IStudentConsentDao extends IGenericDao<StudentConsent, Long> {

	public List<StudentConsent> listConsentsByStudent(Long studentId);

	public boolean checkStudentConsentAvailability(Long studentId,
			Long consentId);

}
