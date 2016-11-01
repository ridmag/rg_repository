package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.NdisSupportItem;

public interface INdisSupportItemService extends
		IGenericService<NdisSupportItem, Long> {


	public List<NdisSupportItem> listByName(String itemName);

	public NdisSupportItem listById(Long ndisitemid);

	public NdisSupportItem searchItemByRefNo(Long refNo);
	
	public List<NdisSupportItem> findAllByType(String type);
	
	public List<NdisSupportItem> listByType(String groupType);

}