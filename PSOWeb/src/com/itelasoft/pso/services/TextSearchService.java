package com.itelasoft.pso.services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.dao.DataAccessObject;
import com.itelasoft.pso.dao.ITextSearchDao;

public class TextSearchService implements ITextSearchService {

	private ITextSearchDao textSearchDao = null;

	public void setTextSearchDao(ITextSearchDao textSearchDao) {
		this.textSearchDao = textSearchDao;
	}

	public List<Contact> searchContacts(String searchText) {
		return textSearchDao.searchContacts(searchText);
	}

	public List<Student> searchStudentsByNameNId(String searchText, String status) {
		return textSearchDao.searchStudentsByNameNId(searchText, status);
	}

	public List<Student> searchStudentsByNameNIdWithNdisNumber(String searchText, String status) {
		return textSearchDao.searchStudentsByNameNIdWithNdisNumber(searchText, status);
	}

	public List<StaffMember> searchStaffByNameNId(String searchText, String status) {
		return textSearchDao.searchStaffByNameNId(searchText, status);
	}

	public List<Student> searchStudents360(String searchText, String status) {

		return textSearchDao.searchStudents360(searchText, status);
	}

	@Override
	public boolean buildIndex() {
		Session session = ((DataAccessObject) textSearchDao).openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.createIndexer().start();
		return true;
	}

}
