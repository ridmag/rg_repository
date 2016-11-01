package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.Consent;

public class ConsentDao extends GenericDao<Consent, Long> implements
		IConsentDao {

	public List<Consent> listWithoutFixedConsents() {
		Session session = getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Consent> consents = (List<Consent>) session
				.createCriteria(Consent.class)
				.add(Restrictions.gt("id", new Long(4))).list();
		if (consents != null && !consents.isEmpty()) {
			return consents;
		}
		return null;
	}

}
