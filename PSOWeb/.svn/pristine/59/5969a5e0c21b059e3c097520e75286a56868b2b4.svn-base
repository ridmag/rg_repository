package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.StaffSkill;

public class StaffSkillDao extends GenericDao<StaffSkill, Long> implements
		IStaffSkillDao {

	public List<StaffSkill> getSkillbyStaff(Long staffId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StaffSkill.class)
				.createCriteria("staffMember")
				.add(Restrictions.eq("id", staffId));
		@SuppressWarnings("unchecked")
		List<StaffSkill> list = criteria.list();
		return list;
	}

}
