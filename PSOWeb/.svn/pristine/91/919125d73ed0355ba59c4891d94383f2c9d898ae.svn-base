package com.itelasoft.pso.dao.test;

import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.dao.DataAccessObject;
import com.itelasoft.pso.dao.ITextSearchDao;

public class TextSearchDaoTest extends TestCase {
	private ApplicationContext context;
	private ITextSearchDao textSearchDao;

	protected void setUp() throws Exception {
		super.setUp();
		context = new ClassPathXmlApplicationContext(new String[] {
				"dao-config.xml" });
		textSearchDao = (ITextSearchDao) context.getBean("textSearchDao");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSearch() {
		List ret = textSearchDao.searchContacts("kate");
		assertTrue(ret.size() > 0);
	}

	public void testSearchStudents360() {
		java.util.Calendar calendar = GregorianCalendar.getInstance();
		System.out.println(calendar.getTimeInMillis());
		List<Student> ret = textSearchDao.searchStudents360("Kate", "Active");
		System.out.println(calendar.getTimeInMillis());
		assertTrue(ret.size() > 0);
		
		ret = textSearchDao.searchStudents360("Austin", "Active");
		System.out.println(calendar.getTimeInMillis());
		assertTrue(ret.size() == 0);
		assertTrue(ret.size() > 0);
		ret = textSearchDao.searchStudents360("Wheelchair Mitchell","Active");
		System.out.println(calendar.getTimeInMillis());

		for (Student s : ret) {
			System.out.println(s.getId() + ":" + s.getContact().getName());
		}
		assertTrue(ret.size() > 0);
	}
	
	public void testSearchStudents() {
		java.util.Calendar calendar = GregorianCalendar.getInstance();
		List<Student> ret = textSearchDao.searchStudentsByNameNId("austin", "Inactive");
		assertTrue(ret.size() > 0);
		ret = textSearchDao.searchStudentsByNameNId("allen", "Active");
		assertTrue(ret.size() > 0);
		ret = textSearchDao.searchStudentsByNameNId("austin", null);
		assertTrue(ret.size() > 0);
		ret = textSearchDao.searchStudentsByNameNId("allen", null);
		assertTrue(ret.size() > 0);
		ret = textSearchDao.searchStudentsByNameNId(null, null);
		assertTrue(ret.size() > 0);
		ret = textSearchDao.searchStudentsByNameNId("austin", "active");
		assertTrue(ret.size() == 0);
		ret = textSearchDao.searchStudentsByNameNId("allen", "Inactive");
		assertTrue(ret.size() == 0);
		
		
		
	}
	public void testSearchStaff() {
		//java.util.Calendar calendar = GregorianCalendar.getInstance();
		List<StaffMember> ret = textSearchDao.searchStaffByNameNId(null, "Curren");
		assertTrue(ret.size() > 0);
		ret = textSearchDao.searchStaffByNameNId("Michelle", "Exited");
		assertTrue(ret.size() == 0);
		ret = textSearchDao.searchStaffByNameNId("Michelle", "Current");
		assertTrue(ret.size() > 0);
		/*ret = textSearchDao.searchStudentsByNameNId("allen", null);
		assertTrue(ret.size() > 0);
		ret = textSearchDao.searchStudentsByNameNId(null, null);
		assertTrue(ret.size() > 0);
		ret = textSearchDao.searchStudentsByNameNId("austin", "active");
		assertTrue(ret.size() == 0);
		ret = textSearchDao.searchStudentsByNameNId("allen", "Inactive");
		assertTrue(ret.size() == 0);*/
		
		
		
	}

	public void buildIndex() throws InterruptedException {
		java.util.Calendar calendar = GregorianCalendar.getInstance();
		System.out.println(calendar.getTimeInMillis());

		Session session = ((DataAccessObject) textSearchDao).openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.createIndexer().start();
		System.out.println(calendar.getTimeInMillis());

	}
}
