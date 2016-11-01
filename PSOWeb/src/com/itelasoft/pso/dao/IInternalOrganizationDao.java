package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.InternalOrganization;



public interface IInternalOrganizationDao extends
		IGenericDao<InternalOrganization, Long> {

	public List<InternalOrganization> listByServiceArea(Long serviceAreaId);

	public List<InternalOrganization> listByName(String name);
	
	public InternalOrganization getOrganization(String orgName);
}