package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.SpecialNeed;
import com.itelasoft.pso.beans.Student;

public class SpecialNeedDao extends GenericDao<SpecialNeed, Long> implements
		ISpecialNeedDao {

	public List<SpecialNeed> listAvailableByNameNStudent(Long studentId,
			String sNeedName) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select sn from SpecialNeed as sn, Student as stu where (stu.id = ? and lower(sn.name) like ?) and sn not in elements(stu.specialNeeds)");
		query.setParameter(0, studentId);
		query.setParameter(1, "%" + sNeedName.toLowerCase() + "%");
		@SuppressWarnings("unchecked")
		List<SpecialNeed> list = query.list();
		return list;
	}

	public List<SpecialNeed> listAvailableByStudent(Long studentId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select sn from SpecialNeed as sn, Student as stu where stu.id = ? and sn not in elements(stu.specialNeeds)");
		query.setParameter(0, studentId);
		@SuppressWarnings("unchecked")
		List<SpecialNeed> list = query.list();
		return list;
	}

	public List<SpecialNeed> listSpecialNeedsByStudent(Long studentId) {
		Session session = getCurrentSession();
		Criteria c = session.createCriteria(Student.class)
				.add(Restrictions.eq("id", studentId))
				.setFetchMode("specialNeeds", FetchMode.JOIN);
		Student student = (Student) c.uniqueResult();
		return student.getSpecialNeeds();
	}

	public SpecialNeed retrieveByStudent(Long studentId, Long sNeedId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select sn from SpecialNeed as sn, Student as stu where stu.id = ? and (sn.id = ? and sn not in elements(stu.specialNeeds))");
		query.setParameter(0, studentId);
		query.setParameter(1, sNeedId);
		return (SpecialNeed) query.uniqueResult();
	}
}
