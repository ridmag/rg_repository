package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.EnumLeaveType;
import com.itelasoft.pso.beans.LeaveCategory;

public class LeaveCategoryDao extends GenericDao<LeaveCategory, Long> implements
		ILeaveCategoryDao {

	public List<LeaveCategory> listByStaffId(Long staffId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(LeaveCategory.class)
				.createCriteria("staffMember")
				.add(Restrictions.eq("id", staffId));
		@SuppressWarnings("unchecked")
		List<LeaveCategory> list = criteria.list();
		return list;
	}

	public LeaveCategory getByStaffIdNLeaveType(String staffId,
			EnumLeaveType leaveType) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(LeaveCategory.class)
				.add(Restrictions.eq("leaveType", leaveType))
				.createCriteria("staffMember")
				.add(Restrictions.eq("staffId", staffId));
		return (LeaveCategory) criteria.uniqueResult();
	}
}
