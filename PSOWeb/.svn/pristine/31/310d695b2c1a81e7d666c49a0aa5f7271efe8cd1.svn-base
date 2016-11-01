package com.itelasoft.pso.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.regex.RegexQuery;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.Student;

public class TextSearchDao extends DataAccessObject implements ITextSearchDao {

	@Override
	public List<Contact> searchContacts(String searchString) {
		Session session = getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		Transaction tx = fullTextSession.beginTransaction();
		// create native Lucene query unsing the query DSL
		// alternatively you can write the Lucene query using the Lucene query
		// parser
		// or the Lucene programmatic API. The Hibernate Search DSL is
		// recommended though
		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Contact.class).get();
		org.apache.lucene.search.Query query = qb.keyword().onFields("title", "firstName", "lastName")
				.matching(searchString).createQuery();
		// wrap Lucene query in a org.hibernate.Query
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Contact.class);
		// execute search
		@SuppressWarnings("unchecked")
		List<Contact> result = hibQuery.list();
		tx.commit();
		session.close();
		return result;
	}

	public List<StaffMember> searchStaffByNameNId(String searchText, String status) {
		Session session = getCurrentSession();

		FullTextSession fullTextSession = Search.getFullTextSession(session);

		/**********************************/
		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(StaffMember.class).get();
		Query query = null, statusQuery = null;
		if (searchText != null && !searchText.isEmpty()) {
			/*
			 * query = qb .keyword() .onFields("id", "contact.title",
			 * "contact.firstName", "contact.lastName").matching(searchText)
			 * .createQuery();
			 */
			searchText = searchText.toLowerCase();
			if (searchText.matches("[0-9]+")) {
				query = qb.keyword().onFields("staffId").matching(searchText).createQuery();
			} else {
				searchText += ".*";
				RegexQuery regexQuery1 = new RegexQuery(new Term("contact.firstName", searchText));
				RegexQuery regexQuery2 = new RegexQuery(new Term("contact.lastName", searchText));
				query = qb.bool().should(regexQuery1).should(regexQuery2).createQuery();
			}
		}
		if (status != null && !status.isEmpty()) {
			statusQuery = qb.phrase().onField("status").sentence(status).createQuery();
		}
		if (query != null) {
			if (statusQuery != null) {
				query = qb.bool().must(query).must(statusQuery).createQuery();
			}
		} else if (statusQuery != null) {
			query = statusQuery;
		}
		if (query != null) {

			// regexQuery.setRegexImplementation(new
			// JavaUtilRegexCapabilities());
			org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, StaffMember.class);

			// execute search
			@SuppressWarnings("unchecked")
			List<StaffMember> result = hibQuery.list();
			return result;
		} else {
			return findByCriteriaStaff();
		}
	}

	public List<Student> searchStudentsByNameNId(String searchText, String status) {
		Session session = getCurrentSession();

		FullTextSession fullTextSession = Search.getFullTextSession(session);

		/**********************************/

		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Student.class).get();
		Query query = null, statusQuery = null;
		if (searchText != null && !searchText.isEmpty()) {
			/*
			 * query = qb .keyword() .onFields("id", "contact.title",
			 * "contact.firstName", "contact.lastName").matching(searchText)
			 * .createQuery();
			 */
			searchText = searchText.toLowerCase();
			if (searchText.matches("[0-9]+")) {
				query = qb.keyword().onFields("id").matching(searchText).createQuery();
			} else {
				searchText += ".*";
				RegexQuery regexQuery1 = new RegexQuery(new Term("contact.firstName", searchText));
				RegexQuery regexQuery2 = new RegexQuery(new Term("contact.lastName", searchText));
				query = qb.bool().should(regexQuery1).should(regexQuery2).createQuery();
			}
		}
		if (status != null && !status.isEmpty()) {
			statusQuery = qb.phrase().onField("status").sentence(status).createQuery();
		}
		if (query != null) {
			if (statusQuery != null) {
				query = qb.bool().must(query).must(statusQuery).createQuery();
			}
		} else if (statusQuery != null) {
			query = statusQuery;
		}
		if (query != null) {

			// regexQuery.setRegexImplementation(new
			// JavaUtilRegexCapabilities());
			org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Student.class);

			// execute search
			@SuppressWarnings("unchecked")
			List<Student> result = hibQuery.list();
			return result;
		} else {
			return findByCriteria();
		}
	}

	public List<Student> searchStudentsByNameNIdWithNdisNumber(String searchText, String status) {
		Session session = getCurrentSession();

		FullTextSession fullTextSession = Search.getFullTextSession(session);

		/**********************************/

		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Student.class).get();
		Query query = null, statusQuery = null;
		if (searchText != null && !searchText.isEmpty()) {
			/*
			 * query = qb .keyword() .onFields("id", "contact.title",
			 * "contact.firstName", "contact.lastName").matching(searchText)
			 * .createQuery();
			 */
			searchText = searchText.toLowerCase();
			if (searchText.matches("[0-9]+")) {
				query = qb.keyword().onFields("id").matching(searchText).createQuery();
			} else {
				searchText += ".*";
				RegexQuery regexQuery1 = new RegexQuery(new Term("contact.firstName", searchText));
				RegexQuery regexQuery2 = new RegexQuery(new Term("contact.lastName", searchText));
				query = qb.bool().should(regexQuery1).should(regexQuery2).createQuery();
			}
		}
		if (status != null && !status.isEmpty()) {
			statusQuery = qb.phrase().onField("status").sentence(status).createQuery();
		}
		if (query != null) {
			if (statusQuery != null) {
				query = qb.bool().must(query).must(statusQuery).createQuery();
			}
		} else if (statusQuery != null) {
			query = statusQuery;
		}
		if (query != null) {

			// regexQuery.setRegexImplementation(new
			// JavaUtilRegexCapabilities());
			org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Student.class);

			// execute search
			@SuppressWarnings("unchecked")
			List<Student> allStudents = hibQuery.list();
			List<Student> result = new ArrayList<Student>();
			for (Student student : allStudents) {
				if (student.getNdisNumber() != null || student.getNdisNumber() != "") {
					result.add(student);
				} else {
					continue;
				}
			}
			return result;
		} else {
			return findByCriteria();
		}
	}

	public List<Student> searchStudents360(String searchText, String status) {
		Session session = getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Student.class).get();
		/*String[] searchFields = { "id", "cisid", "mdsid", "gender", "groups.group.name", "contact.title",
				"contact.firstName", "groups.group.program.name", "groups.group.program.status",
				"groups.group.program.coordinator.contact.firstName",
				"groups.group.program.coordinator.contact.lastName", "groups.group.program.vehicle.name",
				"groups.group.program.vehicle.registrationId", "groups.pickup.name", "groups.pickup.locationCode",
				"groups.dropoff.name", "groups.dropoff.locationCode", "contact.lastName", "contact.streetAddress",
				"contact.city", "contact.state", "contact.postCode", "contact.country",
				"guardians.relationship.itemValue", "guardians.contact.firstName", "guardians.contact.lastName",
				"guardians.contact.city", "guardians.contact.state", "guardians.contact.postCode",
				"guardians.contact.country", "specialNeeds.name" };*/
		//String[] searchFields = { "contact.firstName", "contact.lastName"};
		org.apache.lucene.search.Query query = null/*qb.keyword().onFields(searchFields).matching(searchText).createQuery()*/;
		
		if(searchText != null && !searchText.isEmpty()){
			searchText = searchText.toLowerCase();
			searchText += ".*";
			RegexQuery regexQuery1 = new RegexQuery(new Term("contact.firstName", searchText));
			RegexQuery regexQuery2 = new RegexQuery(new Term("contact.lastName", searchText));
			query = qb.bool().should(regexQuery1).should(regexQuery2).createQuery();
		}
		if(query != null){
			org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Student.class);
			@SuppressWarnings("unchecked")
			List<Student> result = hibQuery.list();
			return result;
		}else{
			return findByCriteria();
		}
	}

	@SuppressWarnings("unchecked")
	protected List<Student> findByCriteria(Criterion... criterion) {
		// getCurrentSession().beginTransaction();
		Criteria crit = getCurrentSession().createCriteria(Student.class);
		for (Criterion c : criterion) {
			crit.add(c);
		}

		List<Student> list = crit.list();
		// getCurrentSession().getTransaction().commit();
		return list;
	}

	@SuppressWarnings("unchecked")
	protected List<StaffMember> findByCriteriaStaff(Criterion... criterion) {
		// getCurrentSession().beginTransaction();
		Criteria crit = getCurrentSession().createCriteria(StaffMember.class);
		for (Criterion c : criterion) {
			crit.add(c);
		}
		List<StaffMember> list = crit.list();
		// getCurrentSession().getTransaction().commit();
		return list;
	}

}
