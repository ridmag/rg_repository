package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.InternalOrganization;



public interface IInternalOrganizationService extends
		IGenericService<InternalOrganization, Long> {

	public List<InternalOrganization> listByServiceArea(Long serviceAreaId);

	public List<InternalOrganization> listByName(String name);
	
	public InternalOrganization getOrganization(String orgName);
}