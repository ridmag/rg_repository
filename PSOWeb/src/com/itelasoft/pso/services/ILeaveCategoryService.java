package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.EnumLeaveType;
import com.itelasoft.pso.beans.LeaveCategory;

public interface ILeaveCategoryService extends
		IGenericService<LeaveCategory, Long> {

	public List<LeaveCategory> listByStaffId(Long staffId);

	public LeaveCategory getByStaffIdNLeaveType(String staffId,
			EnumLeaveType leaveType);

}
