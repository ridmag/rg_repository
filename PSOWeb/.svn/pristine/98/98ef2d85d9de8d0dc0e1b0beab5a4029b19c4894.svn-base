package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.GroupedStaff;
import com.itelasoft.pso.beans.StudentGroup;

public class GroupedStaffDao extends GenericDao<GroupedStaff, Long> implements
		IGroupedStaffDao {

	public List<GroupedStaff> listByStaff(Long staffId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(GroupedStaff.class)
				.createAlias("group", "g").createAlias("g.program", "pro")
				.add(Restrictions.eq("staffMember.id", staffId))
				.addOrder(Order.asc("pro.status"))
				.addOrder(Order.asc("g.name"));
		@SuppressWarnings("unchecked")
		List<GroupedStaff> list = criteria.list();
		return list;
	}
	public List<GroupedStaff> listByStaffID(String staffId,Date startDate,Date endDate) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from GroupedStaff as gro where gro.staffMember.staffId = ? and gro.group.program.type.name='Staff' and gro.group.startDate<=? and gro.group.endDate>=? and gro.group.program.status='Active'");
		query.setParameter(0, staffId);
		query.setParameter(1, startDate);
		query.setParameter(2, endDate);
		@SuppressWarnings("unchecked")
		List<GroupedStaff> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
		
	}
	
	public List<GroupedStaff> listByStaffID1(String staffId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from GroupedStaff as gro where gro.staffMember.staffId = ? and gro.group.program.type.name='Staff'");
		query.setParameter(0, staffId);
		@SuppressWarnings("unchecked")
		List<GroupedStaff> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
		
	}
	
	public List<GroupedStaff> listByStaffIdRoster(Date startDate,Date endDate) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from GroupedStaff as gro where gro.group.program.type.name='Staff' and gro.group.startDate<=? and gro.group.endDate>=?");
		query.setParameter(0, startDate);
		query.setParameter(1, endDate);
		@SuppressWarnings("unchecked")
		List<GroupedStaff> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}
	
	public List<GroupedStaff> listByGroup(Long groupId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentGroup.class)
				.add(Restrictions.eq("id", groupId))
				.setFetchMode("staffs", FetchMode.JOIN);
		StudentGroup group = (StudentGroup) criteria.uniqueResult();
		return group.getStaffMembers();
	}
	
	public GroupedStaff retrieveByGroupNStaffMember(Long groupid,
			Long staffid) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from GroupedStaff as gro where gro.group.id = ? and gro.staffMember.id = ?");
		query.setParameter(0, groupid);
		query.setParameter(1, staffid);
		return (GroupedStaff) query.uniqueResult();
	}
}

