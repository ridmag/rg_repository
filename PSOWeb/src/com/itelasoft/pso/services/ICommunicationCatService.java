package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.CommunicationCat;

public interface ICommunicationCatService extends
		IGenericService<CommunicationCat, Long> {
	public List<CommunicationCat> getRootCategories();

	public List<CommunicationCat> getSubCategories(Long parentId);
}
/*
 * <bean id="categoryService"
 * class="com.itelasoft.vet.services.CategoryService"> <property name="dao"
 * ref="categoryDao" /> </bean> public ICategoryService getCategoryService(){
 * return (ICategoryService)context.getBean("categoryService"); }
 */