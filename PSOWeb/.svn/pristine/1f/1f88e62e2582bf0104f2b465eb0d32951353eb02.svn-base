package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Outlet;
import com.itelasoft.pso.dao.IOutletDao;

public class OutletService extends GenericService<Outlet, Long> implements
		IOutletService {
	private IOutletDao outletDao;

	public List<Outlet> listOutletsByFundingSource(Long sourceId) {
		return this.outletDao.listOutletsByFundingSource(sourceId);
	}

	public void setOutletDao(IOutletDao outletDao) {
		this.outletDao = outletDao;
	}

	public IOutletDao getOutletDao() {
		return outletDao;
	}

}
