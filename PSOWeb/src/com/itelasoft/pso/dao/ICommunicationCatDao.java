package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.CommunicationCat;

/**
 * Dao class for Category.
 */
public interface ICommunicationCatDao extends
		IGenericDao<CommunicationCat, Long> {

	public List<CommunicationCat> getRootCategories();

	public List<CommunicationCat> getSubCategories(Long parentId);
}
/*
 * <bean id="categoryDao" class="com.itelasoft.vet.dao.CategoryDao"> <property
 * name="sessionFactory" ref="mySessionFactory" /> </bean>
 */