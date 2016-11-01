package com.itelasoft.pso.dao.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.dao.ContactDao;

import junit.framework.TestCase;

public class ContactDaoTest extends TestCase {
	private ApplicationContext context;
	private ContactDao contactDao;

	protected void setUp() throws Exception {
		super.setUp();
		context = new ClassPathXmlApplicationContext(new String[] {
				"services-config.xml", "dao-config.xml" });
		contactDao = (ContactDao) context.getBean("contactDao");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testRetrive() {
		Contact contact = contactDao.retrive(1);
		assertEquals("Amy", contact.getFirstName());
	}

	public void testSave() {
		Contact contact = new Contact();
		contact.setTitle("Dr");
		contact.setFirstName("Test First Name");
		contact.setLastName("Test Last Name");
		contact.setMiddleNames("Test Middle Names");
		contact.setBusinessName("Test Business Name");
		contact.setStreetAddress("Test Street Address");
		contact.setCity("Test City");
		contact.setState("Test State");
		contact.setPostCode("Test Poset Code");
		contact.setCountry("Test Country");
		contact.setGeoCode("Test Geocode");
		contact.setMobilePhone("Test Mobile 001");
		contact.setHomePhone("Test Home 001");
		contact.setOfficePhone("Test Office 001");
		contact.setEmail("test@email.com");
		contact.setBusinessType("Test Business Type");
		contact.setSendNewsletter(true);
		contactDao.save(contact);
		// Retrieve already inserted row
		Contact contact_ref = contactDao.retrive(contact.getId());
		// Test
		assertEquals(contact.getFirstName(), contact_ref.getFirstName());
	}

	public void testUpdate() {
		Contact contact = contactDao.listByName("Test First Name").iterator()
				.next();
		contact.setFirstName("Test First Name - UPDATED");
		contactDao.update(contact);
		contact = contactDao.listByName("Test First Name - UPDATED").iterator()
				.next();
		// Test
		assertEquals("Test First Name - UPDATED", contact.getFirstName());
	}

	public void testListByName() {
		Contact contact = contactDao.listByName("Test First Name - UPDATED")
				.iterator().next();
		// Test
		assertEquals("Test First Name - UPDATED", contact.getFirstName());
	}

	public void testDelete() {
		Contact contact = contactDao.listByName("Test First Name - UPDATED")
				.iterator().next();
		int deletedRows = contactDao.delete(contact.getId());
		// Test
		assertEquals(1, deletedRows);
	}

}
