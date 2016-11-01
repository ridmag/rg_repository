package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.NdisSupportItem;

public interface INdisSupportItemDao extends IGenericDao<NdisSupportItem, Long> {

	// public List<NdisSupportItem> listByServiceArea(Long serviceAreaId);

	public List<NdisSupportItem> listByName(String itemName);

	public NdisSupportItem listById(Long ndisitemid);

	public NdisSupportItem searchItemByRefNo(Long refNo);

	public List<NdisSupportItem> findAllByType(String type);
	
	public List<NdisSupportItem> listByType(String groupType);
}
