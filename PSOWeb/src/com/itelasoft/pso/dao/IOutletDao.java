package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.Outlet;

public interface IOutletDao extends IGenericDao<Outlet, Long> {

	public List<Outlet> listOutletsByFundingSource(Long sourceId);

}
