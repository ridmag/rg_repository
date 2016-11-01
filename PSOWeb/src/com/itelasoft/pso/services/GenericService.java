package com.itelasoft.pso.services;

import java.io.Serializable;
import java.util.List;

import com.itelasoft.pso.dao.IGenericDao;

public class GenericService<T, ID extends Serializable> implements
		IGenericService<T, ID> {
	protected IGenericDao<T, ID> dao;

	public IGenericDao<T, ID> getDao() {
		return dao;
	}

	public void setDao(IGenericDao<T, ID> dao) {
		this.dao = dao;
	}

	public T create(T o) {
		// ((GenericDao)dao).getCurrentSession().beginTransaction();
		o = dao.save(o);
		// ((GenericDao)dao).getCurrentSession().getTransaction().commit();
		return o;
	};

	public T update(T o) {
		return dao.update(o);
	}

	public T delete(ID id) {
		T entity = dao.retrive(id);
		dao.delete(entity);
		return entity;
	};

	public T retrieve(ID id) {
		// ((GenericDao)dao).getCurrentSession().beginTransaction();
		T o = dao.retrive(id);
		// ((GenericDao)dao).getCurrentSession().getTransaction().commit();
		return o;
	};

	public List<T> findAll() {
		return dao.findAll();
	};
}
