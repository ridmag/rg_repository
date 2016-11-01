package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.itelasoft.pso.beans.Contact;

public class ContactDao extends DataAccessObject implements IContactDao {

	// Basic data Services

	public Contact retrive(long id) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		Query query = session.createQuery(
				"from Contact as contact where contact.id = ?").setParameter(0,
				id);
		Contact contact = (Contact) query.uniqueResult();
		// //session.getTransaction().commit();
		return contact;
	}

	public Contact save(Contact contact) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		session.save(contact);
		// //session.getTransaction().commit();
		return contact;
	}

	public Contact update(Contact contact) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		session.update(contact);
		// //session.getTransaction().commit();
		return contact;
	}

	public int delete(long id) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		Query query = session.createQuery(
				"delete from Contact as contact where contact.id = ?")
				.setParameter(0, id);
		int row = query.executeUpdate();
		// //session.getTransaction().commit();
		return row;
	}

	// Extended data Services

	public List<Contact> listByName(String name) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		Query query = session
				.createQuery("from Contact as contact where lower(contact.firstName) like ? or lower(contact.lastName) like ? order by contact.firstName, contact.lastName");
		query.setParameter(0, "%" + name.toLowerCase() + "%");
		query.setParameter(1, "%" + name.toLowerCase() + "%");
		@SuppressWarnings("unchecked")
		List<Contact> list = query.list();
		// //session.getTransaction().commit();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public Contact retrieveGuardian(Long studentId, Long guardianId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select gua from Contact as gua, Student as stu where stu.id = ? and (gua.id = ? and gua in elements(stu.guardians))");
		if (guardianId == null)
			guardianId = -1L;
		query.setParameter(0, studentId);
		query.setParameter(1, guardianId);
		Contact contact = (Contact) query.uniqueResult();
		return contact;
	}

	public List<String> listStudentNames() {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select concat(c.title, ' ',c.firstName, ' ', c.lastName) as fullname from Contact c where  (c.firstName is not null) or (c.lastName is not null)");
		@SuppressWarnings("unchecked")
		List<String> name = query.list();
		if (name != null && !name.isEmpty()) {
			return name;
		}
		return null;
	}
}
