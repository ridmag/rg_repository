package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.InternalOrganization;
import com.itelasoft.pso.dao.IInternalOrganizationDao;

public class InternalOrganizationService extends
		GenericService<InternalOrganization, Long> implements
		IInternalOrganizationService {
	private IInternalOrganizationDao internalOrganizationDao;

	public List<InternalOrganization> listByServiceArea(Long serviceAreaId) {
		return this.internalOrganizationDao.listByServiceArea(serviceAreaId);
	}

	public void setInternalOrganizationDao(
			IInternalOrganizationDao internalOrganizationDao) {
		this.internalOrganizationDao = internalOrganizationDao;
	}

	public IInternalOrganizationDao getExternalOrganizationDao() {
		return internalOrganizationDao;
	}

	public List<InternalOrganization> listByName(String name) {
		return this.internalOrganizationDao.listByName(name);
	}

	@Override
	public InternalOrganization getOrganization(String orgName) {
		return this.internalOrganizationDao.getOrganization(orgName);
	}

}