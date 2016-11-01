package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.NdisCommittedEvent;
import com.itelasoft.pso.beans.ProgramType;

public class NdisCommittedEventDao extends GenericDao<NdisCommittedEvent, Long>
		implements INdisCommittedEventDao {

	@Override
	public int deleteCommittedEvents(long studentId, Date startDate,
			Date endDate) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery(
						"delete from NdisCommittedEvent as nce where nce.participantId = ? and nce.eventDate > ? and nce.eventDate > ? ")
				.setParameter(0, studentId).setParameter(1, startDate)
				.setParameter(2, endDate);
		int row = query.executeUpdate();
		return row;
	}

	@SuppressWarnings("unchecked")
	public List<NdisCommittedEvent> getCommittedEvents(Long studentID,
			Long groupId, Date eventDate, Date endDate) {
		Session session1 = getCurrentSession();
		Criteria criteria = session1.createCriteria(NdisCommittedEvent.class)
				.createAlias("participant", "participant")
				.createAlias("studentGroup", "studentGroup")
				.add(Restrictions.eq("participant.id", studentID))
				.add(Restrictions.eq("studentGroup.id", groupId))
				.add(Restrictions.ge("eventDate", eventDate))
				.add(Restrictions.le("eventDate", endDate))
				.add(Restrictions.eq("claimed",false));
		// .addOrder(Order.asc("eventDate")).createCriteria("group");

		List<NdisCommittedEvent> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public int deleteSelectedEvents(Long studentId, Long groupId,
			Date fromDate, Date toDate) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery(
						"delete from NdisCommittedEvent as eve where eve.participant.id = ? and eve.studentGroup.id = ? and eve.eventDate >= ? and eve.eventDate <= ? and eve.claimed is false ")
				.setParameter(0, studentId).setParameter(1, groupId)
				.setParameter(2, fromDate).setParameter(3, toDate);

		int row = query.executeUpdate();

		return row;
		// session.createQuery(query).executeUpdate();
	}

	@Override
	public List<NdisCommittedEvent> ndisCommittedEventsListByGroup(Long groupId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from NdisCommittedEvent as eve where eve.studentGroup.id = ? and eve.claimed is false")
				.setParameter(0, groupId);
		@SuppressWarnings("unchecked")
		List<NdisCommittedEvent> list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<NdisCommittedEvent> retrieveClaimedCommittedEvents(Long studentID,
			Long groupId, Date eventDate, Date endDate) {
		Session session1 = getCurrentSession();
		Criteria criteria = session1.createCriteria(NdisCommittedEvent.class)
				.createAlias("participant", "participant")
				.createAlias("studentGroup", "studentGroup")
				.add(Restrictions.eq("participant.id", studentID))
				.add(Restrictions.eq("studentGroup.id", groupId))
				.add(Restrictions.ge("eventDate", eventDate))
				.add(Restrictions.le("eventDate", endDate))
				.add(Restrictions.eq("claimed",true));
		// .addOrder(Order.asc("eventDate")).createCriteria("group");

		List<NdisCommittedEvent> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	public int deleteSelectedStudentEvents(Long studentId,
			Date fromDate,Long studentGroupid) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery(
						"delete from NdisCommittedEvent as eve where eve.participant.id = ?  and eve.eventDate >= ? and eve.studentGroup.id = ? and eve.claimed is false")
				.setParameter(0, studentId)
				.setParameter(1, fromDate)
				.setParameter(2, studentGroupid);

		int row = query.executeUpdate();

		return row;
		
	}
	
	public List<NdisCommittedEvent> SelectedStudentEvents(Long studentId,
			Date fromDate,Long studentGroupid) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery(
						"from NdisCommittedEvent as eve where eve.participant.id = ?  and eve.eventDate >= ? and eve.studentGroup.id = ? and eve.claimed is false")
				.setParameter(0, studentId)
				.setParameter(1, fromDate)
                .setParameter(2, studentGroupid);
		

		@SuppressWarnings("unchecked")
		List<NdisCommittedEvent> list = query.list();
		return list;
		
	}
	@SuppressWarnings("unchecked")
	public List<NdisCommittedEvent> allCommittedAmountForStudent(Long studentID, ProgramType proType) {
		Session session1 = getCurrentSession();
		Criteria criteria = session1.createCriteria(NdisCommittedEvent.class)
				.createAlias("participant", "participant").createAlias("studentGroup.program", "program").createAlias("program.type", "type")
				.add(Restrictions.eq("participant.id", studentID)).add(Restrictions.eq("type.id", proType.getId()));
		List<NdisCommittedEvent> list = criteria.list();
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<NdisCommittedEvent> getCommitedEventsOfInvoiceByStudentId(Long invoiceId,Long studentId){
		Session session1 = getCurrentSession();
		Criteria criteria = session1.createCriteria(NdisCommittedEvent.class)
				.createAlias("participant", "participant")
				.add(Restrictions.eq("participant.id", studentId)).add(Restrictions.eq("invoiceId", invoiceId));
		List<NdisCommittedEvent> list = criteria.list();
		return list;
	}
}