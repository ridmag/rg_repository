package com.itelasoft.pso.services;

import com.itelasoft.pso.beans.StaffType;

public interface IStaffTypeService extends IGenericService<StaffType, Long> {
	public StaffType getStaffType(String name);
}
