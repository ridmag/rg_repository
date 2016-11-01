package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.EnumLeaveType;
import com.itelasoft.pso.beans.LeaveCategory;
import com.itelasoft.pso.dao.ILeaveCategoryDao;

public class LeaveCategoryService extends GenericService<LeaveCategory, Long>
		implements ILeaveCategoryService {

	public List<LeaveCategory> listByStaffId(Long staffId) {
		return ((ILeaveCategoryDao) dao).listByStaffId(staffId);
	}

	public LeaveCategory getByStaffIdNLeaveType(String staffId,
			EnumLeaveType leaveType) {
		return ((ILeaveCategoryDao) dao).getByStaffIdNLeaveType(staffId,
				leaveType);
	}
}
