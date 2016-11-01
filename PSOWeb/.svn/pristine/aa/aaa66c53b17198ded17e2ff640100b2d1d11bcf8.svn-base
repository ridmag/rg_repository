package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.ExternalOrganization;
import com.itelasoft.pso.dao.IExternalOrganizationDao;

public class ExternalOrganizationService extends
		GenericService<ExternalOrganization, Long> implements
		IExternalOrganizationService {
	private IExternalOrganizationDao externalOrganizationDao;

	public List<ExternalOrganization> listByServiceArea(Long serviceAreaId) {
		return this.externalOrganizationDao.listByServiceArea(serviceAreaId);
	}

	public void setExternalOrganizationDao(
			IExternalOrganizationDao externalOrganizationDao) {
		this.externalOrganizationDao = externalOrganizationDao;
	}

	public IExternalOrganizationDao getExternalOrganizationDao() {
		return externalOrganizationDao;
	}

	public List<ExternalOrganization> listByName(String name) {
		return this.externalOrganizationDao.listByName(name);
	}

}
