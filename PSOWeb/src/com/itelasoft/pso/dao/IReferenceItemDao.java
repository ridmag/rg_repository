package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.EnumItemCategory;
import com.itelasoft.pso.beans.ReferenceItem;

public interface IReferenceItemDao extends IGenericDao<ReferenceItem, Long> {

	public List<ReferenceItem> findItemsByCategory(EnumItemCategory category,
			String status);

	public List<ReferenceItem> listAvailableByStaff(Long staffId);
}
