package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.LeavePolicy;
import com.itelasoft.pso.beans.LeavePolicyDetail;
import com.itelasoft.pso.beans.StaffMember;

public class LeavePolicyDao extends GenericDao<LeavePolicy, Long> implements
		ILeavePolicyDao {

	public List<LeavePolicyDetail> listByPolicy(Long policyId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(LeavePolicyDetail.class)
				.createCriteria("policy").add(Restrictions.eq("id", policyId));
		@SuppressWarnings("unchecked")
		List<LeavePolicyDetail> list = criteria.list();
		return list;
	}

	public List<LeavePolicy> listActivePolicies() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(LeavePolicy.class)
				.add(Restrictions.eq("status", "Active"))
				.addOrder(Order.asc("id"));
		@SuppressWarnings("unchecked")
		List<LeavePolicy> policies = criteria.list();
		return policies;
	}

	public boolean isHavingCurrentStaff(Long policyID) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StaffMember.class)
				.add(Restrictions.eq("status", "Current"))
				.createCriteria("leavePolicy")
				.add(Restrictions.eq("id", policyID));
		@SuppressWarnings("unchecked")
		List<StaffMember> members = criteria.list();
		if ((members != null) && (!members.isEmpty())) {
			return true;
		}
		return false;
	}

}
