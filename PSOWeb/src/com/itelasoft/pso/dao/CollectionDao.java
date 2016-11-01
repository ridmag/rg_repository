package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.Collection;

public class CollectionDao extends GenericDao<Collection, Long> implements
		ICollectionDao {

	public List<Collection> searchByName(String collectionName) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Collection.class);
		criteria.add(Restrictions.like("name", collectionName,MatchMode.ANYWHERE).ignoreCase());
		@SuppressWarnings("unchecked")
		List<Collection> collections = criteria.list();
		return collections;
	}
	public List<Collection> listActiveCollections(){
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Collection.class);
		criteria.add(Restrictions.like("status", "Active"));
		@SuppressWarnings("unchecked")
		List<Collection> collections = criteria.list();
		return collections;
		
	}
}
