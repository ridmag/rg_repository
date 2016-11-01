package com.itelasoft.pso.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T, ID extends Serializable> {

	public T retrive(ID id);

	public T save(T o);

	public T update(T o);

	public T delete(T entity);

	public void flush();

	public void clear();

	List<T> findAll();

}
