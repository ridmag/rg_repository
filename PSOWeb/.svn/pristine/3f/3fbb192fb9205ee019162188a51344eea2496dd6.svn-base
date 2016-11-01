package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.itelasoft.pso.beans.Configuration;

public class ConfigurationDao extends DataAccessObject implements
		IConfigurationDao {

	public Configuration retrieve(String parameter) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery(
						"from Configuration as configuration where configuration.parameter = ?")
				.setParameter(0, parameter);
		return (Configuration) query.uniqueResult();
	}

	public void update(Configuration configuration) {
		Session session = getCurrentSession();
		session.update(configuration);
	}

	public List<Configuration> listAll() {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from Configuration as configuration order by configuration.id");
		@SuppressWarnings("unchecked")
		List<Configuration> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

}
