package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Contact;

public interface IContactService {

	public Contact retrive(Long id);

	public Contact save(Contact contact);

	public Contact update(Contact contact);

	public int delete(long id);

	public List<Contact> listByName(String name);

	public List<String> listAllStudentNames();

	public Contact retrieveGuardian(Long studentId, Long guardianId);
}
