package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.itelasoft.pso.beans.StaffCheckRecord;

public class StaffCheckRecordDao extends GenericDao<StaffCheckRecord, Long>
		implements IStaffCheckRecordDao {

	public List<StaffCheckRecord> getRecordsFromStaff(Long staffId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StaffCheckRecord.class);
		criteria = criteria.createCriteria("staffMember").add(
				Restrictions.eq("id", staffId));
		@SuppressWarnings("unchecked")
		List<StaffCheckRecord> list = criteria.list();
		return list;
	}

	public boolean checkStaffRecordAvailability(String staffId, Long checkId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StaffCheckRecord.class)
				.createAlias("staffMember", "staffMember")
				.createAlias("checkRecord", "checkRecord");
		criteria = criteria
				.add(Restrictions.eq("staffMember.staffId", staffId)).add(
						Restrictions.eq("checkRecord.id", checkId));
		@SuppressWarnings("unchecked")
		List<StaffCheckRecord> list = criteria.list();
		if (list == null || list.isEmpty())
			return true;
		return false;
	}
}
