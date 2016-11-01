package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.NdisSupportItem;
import com.itelasoft.pso.dao.INdisSupportItemDao;

public class NdisSupportItemService extends
		GenericService<NdisSupportItem, Long> implements
		INdisSupportItemService {
	private INdisSupportItemDao ndisSupportItemDao;

	// public List<NdisSupportItem> listByServiceArea(Long serviceAreaId) {
	// return this.externalOrganizationDao.listByServiceArea(serviceAreaId);
	// }

	public void setNdisSupportItemDao(INdisSupportItemDao ndisSupportItemDao) {
		this.ndisSupportItemDao = ndisSupportItemDao;
	}

	public INdisSupportItemDao getNdisSupportItemDao() {
		return ndisSupportItemDao;
	}

	public List<NdisSupportItem> listByName(String itemName) {
		return ((INdisSupportItemDao) dao).listByName(itemName);
	}

	public NdisSupportItem listById(Long ndisitemid) {
		return ((INdisSupportItemDao) dao).listById(ndisitemid);
	}

	public NdisSupportItem searchItemByRefNo(Long refNo) {
		return ((INdisSupportItemDao) dao).searchItemByRefNo(refNo);
	}
	
	public List<NdisSupportItem> findAllByType(String type) {
		return ((INdisSupportItemDao) dao).findAllByType(type);
	}
	
	public List<NdisSupportItem> listByType(String groupType) {
		return ((INdisSupportItemDao) dao).listByType(groupType);
	}


}
