package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.itelasoft.pso.beans.AuthorisedStaff;

public class AuthorisedStaffDao extends GenericDao<AuthorisedStaff, Long> implements IAuthorisedStaffDao {

	@Override
	public List<AuthorisedStaff> listAuthorisedStaffToOrg(Long orgId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from AuthorisedStaff as aStaff where aStaff.internalOrganization.id = ? ");
		query.setParameter(0, orgId);
		@SuppressWarnings("unchecked")
		List<AuthorisedStaff> staff = query.list();
		if (staff != null) {
			return staff;
		}
		return null;
	}

}
