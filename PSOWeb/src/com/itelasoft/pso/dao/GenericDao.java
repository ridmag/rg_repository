package com.itelasoft.pso.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class GenericDao<T, ID extends Serializable> extends DataAccessObject
		implements IGenericDao<T, ID> {

	final private Class<T> persistentClass;

	public GenericDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T retrive(ID id) {
		// Session session = getCurrentSession();
		// //session.getTransaction().begin();
		T entity;
		try {
			entity = (T) getCurrentSession().get(getPersistentClass(), id);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// ////session.getTransaction().commit();
		return entity;
	}

	public T save(T o) {
		Session session = getCurrentSession();
		// //session.getTransaction().begin();
		ID ret = (ID) session.save(o);
		// ////session.getTransaction().commit();
		return retrive(ret);
		// return o;
	}

	@SuppressWarnings("unchecked")
	public T update(T o) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		session.update(o);
		return o;
		// //session.getTransaction().commit();

	}

	public void delete(ID id) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		session.delete(id);
		// //session.getTransaction().commit();

	}

	public T delete(T entity) {
		Session session = getCurrentSession();
		// session.getTransaction().begin();
		session.delete(entity);
		return entity;
		// //session.getTransaction().commit();

	}

	public void flush() {
		getCurrentSession().flush();
	}

	public void clear() {
		getCurrentSession().clear();
	}

	public List<T> findAll() {

		return findByCriteria();

	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		// getCurrentSession().beginTransaction();
		Criteria crit = getCurrentSession()
				.createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}

		List<T> list = crit.addOrder(Order.asc("id")).list();
		// getCurrentSession().getTransaction().commit();
		return list;
	}

}
