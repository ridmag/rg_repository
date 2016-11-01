package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.Student;

public interface ITextSearchService {

	public List<Contact> searchContacts(String searchText);

	public List<Student> searchStudentsByNameNId(String searchText, String status);

	public List<Student> searchStudentsByNameNIdWithNdisNumber(String searchText, String status);

	public List<Student> searchStudents360(String searchText, String status);

	public boolean buildIndex();

	public List<StaffMember> searchStaffByNameNId(String searchText, String status);
}
