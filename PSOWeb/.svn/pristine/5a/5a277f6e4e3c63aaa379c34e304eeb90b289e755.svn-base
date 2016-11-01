package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.EnumLeaveType;
import com.itelasoft.pso.beans.Leave;

public interface ILeaveService extends IGenericService<Leave, Long> {

	public List<Leave> listByStaffId(Long staffId);

	public boolean isFinalized(Leave leave);

	public int getTotalNumberOfLeaveDays(Long staffId, EnumLeaveType type);

	public Leave getByStaffIdAndDate(Long staffId, Date eventDate);

}
