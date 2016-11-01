package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.EnumLeaveType;
import com.itelasoft.pso.beans.LeaveCategory;

public interface ILeaveCategoryDao extends IGenericDao<LeaveCategory, Long> {

	public List<LeaveCategory> listByStaffId(Long staffId);

	public LeaveCategory getByStaffIdNLeaveType(String staffId,
			EnumLeaveType leaveType);

}
