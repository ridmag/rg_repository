package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.dao.IContactDao;

public class ContactService implements IContactService {

	private IContactDao contactDao = null;

	public void setContactDao(IContactDao contactDao) {
		this.contactDao = contactDao;
	}

	public Contact retrive(Long id) {
		return this.contactDao.retrive(id);
	}

	public List<Contact> listByName(String name) {
		return this.contactDao.listByName(name);
	}

	public Contact save(Contact contact) {
		return this.contactDao.save(contact);
	}

	public Contact update(Contact contact) {
		return this.contactDao.update(contact);
	}

	public int delete(long id) {
		return this.contactDao.delete(id);
	}

	public Contact retrieveGuardian(Long studentId, Long guardianId) {
		return this.contactDao.retrieveGuardian(studentId, guardianId);
	}

	public List<String> listAllStudentNames() {
		return this.contactDao.listStudentNames();
	}
}
