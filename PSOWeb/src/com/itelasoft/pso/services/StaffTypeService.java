package com.itelasoft.pso.services;

import com.itelasoft.pso.beans.StaffType;
import com.itelasoft.pso.dao.IStaffTypeDao;

public class StaffTypeService extends GenericService<StaffType, Long> implements
		IStaffTypeService {
	@Override
	public StaffType getStaffType(String name) {
		return ((IStaffTypeDao) dao).findByName(name);
	}
}
