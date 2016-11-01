package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.ExternalOrganization;

;

public interface IExternalOrganizationDao extends
		IGenericDao<ExternalOrganization, Long> {

	public List<ExternalOrganization> listByServiceArea(Long serviceAreaId);

	public List<ExternalOrganization> listByName(String name);
}
