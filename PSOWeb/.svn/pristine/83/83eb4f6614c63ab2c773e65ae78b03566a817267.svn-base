package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.EnumItemCategory;
import com.itelasoft.pso.beans.ReferenceItem;
import com.itelasoft.pso.dao.IReferenceItemDao;

public class ReferenceItemService extends GenericService<ReferenceItem, Long>
		implements IReferenceItemService {

	private IReferenceItemDao itemDao;

	public void setItemDao(IReferenceItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public IReferenceItemDao getItemDao() {
		return itemDao;
	}

	public List<ReferenceItem> findItemsByCategory(EnumItemCategory category,
			String status) {
		return this.itemDao.findItemsByCategory(category, status);
	}

	public List<ReferenceItem> listAvailableByStaff(Long staffId) {
		return this.itemDao.listAvailableByStaff(staffId);
	}

}
