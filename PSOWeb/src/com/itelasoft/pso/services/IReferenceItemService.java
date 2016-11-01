package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.EnumItemCategory;
import com.itelasoft.pso.beans.ReferenceItem;

public interface IReferenceItemService extends
		IGenericService<ReferenceItem, Long> {

	public List<ReferenceItem> findItemsByCategory(EnumItemCategory category,
			String status);

	public List<ReferenceItem> listAvailableByStaff(Long staffId);

}
