package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataIntegrityViolationException;

import com.itelasoft.pso.beans.Guardian;

public class GuardianDao extends DataAccessObject implements IGuardianDao {

	// Basic data Services

	public Guardian retrive(Long studentId, Long contactId) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		Query query = session
				.createQuery("from Guardian as sg where sg.student.id = ? and sg.contact.id = ?");
		query.setParameter(0, studentId);
		query.setParameter(1, contactId);
		//session.getTransaction().commit();
		Guardian guardian = (Guardian) query.uniqueResult();
		return guardian;
	}
	
	public Guardian save(Guardian guardian)
			throws DataIntegrityViolationException {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		if (guardian.getContact().getId() == null) {
			session.save(guardian.getContact());
		} else {
			session.update(guardian.getContact());
		}
		if (guardian.getStudent().getId() == null) {
			session.save(guardian.getStudent());
		} else {
			session.update(guardian.getStudent());
		}
		session.save(guardian);
		// //session.getTransaction().commit();
		return guardian;
	}

	public Guardian update(Guardian guardian) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		session.update(guardian.getContact());
		session.update(guardian);
		// //session.getTransaction().commit();
		return guardian;
	}

	public int delete(Guardian guardian) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		Query query = session
				.createQuery(
						"delete from Guardian as sg where sg.student.id = ? and sg.contact.id = ?")
				.setParameter(0, guardian.getStudent().getId())
				.setParameter(1, guardian.getContact().getId());
		int row = query.executeUpdate();
		// //session.getTransaction().commit();
		return row;
	}

	// Extended data Services

	public List<Guardian> listByStudent(Long studentId) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		Query query = session
				.createQuery("from Guardian as sg where sg.student.id = ? order by sg.guardianId");
		query.setParameter(0, studentId);
		@SuppressWarnings("unchecked")
		List<Guardian> list = query.list();
		// //session.getTransaction().commit();
		return list;
	}

}
