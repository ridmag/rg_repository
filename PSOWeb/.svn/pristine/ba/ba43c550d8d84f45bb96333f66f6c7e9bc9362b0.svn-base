package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.StaffCheckRecord;
import com.itelasoft.pso.beans.StudentConsent;

public class StudentConsentDao extends GenericDao<StudentConsent, Long>
		implements IStudentConsentDao {

	public List<StudentConsent> listConsentsByStudent(Long studentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentConsent.class);
		criteria = criteria.createCriteria("student").add(
				Restrictions.eq("id", studentId));
		@SuppressWarnings("unchecked")
		List<StudentConsent> list = criteria.list();
		return list;
	}

	public boolean checkStudentConsentAvailability(Long studentId,
			Long consentId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentConsent.class)
				.createAlias("student", "student")
				.createAlias("consent", "consent");
		criteria = criteria.add(Restrictions.eq("consentGiven", true))
				.add(Restrictions.eq("student.id", studentId))
				.add(Restrictions.eq("consent.id", consentId));
		@SuppressWarnings("unchecked")
		List<StaffCheckRecord> list = criteria.list();
		if (list == null || list.isEmpty())
			return true;
		return false;
	}
}
