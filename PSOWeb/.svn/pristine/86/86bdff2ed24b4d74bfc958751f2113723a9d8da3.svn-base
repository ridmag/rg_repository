package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.EnumLeaveType;
import com.itelasoft.pso.beans.Leave;
import com.itelasoft.pso.dao.ILeaveDao;

public class LeaveService extends GenericService<Leave, Long> implements
		ILeaveService {

	private ILeaveDao leaveDao;

	public List<Leave> listByStaffId(Long staffId) {
		return this.leaveDao.listByStaffId(staffId);
	}

	public boolean isFinalized(Leave leave) {
		return leaveDao.isFinalized(leave);
	}

	public void setLeaveDao(ILeaveDao leaveDao) {
		this.leaveDao = leaveDao;
	}

	public int getTotalNumberOfLeaveDays(Long staffId, EnumLeaveType type) {
		return leaveDao.getTotalNumberOfLeaveDays(staffId, type);
	}

	public Leave getByStaffIdAndDate(Long staffId, Date eventDate) {
		return leaveDao.getByStaffIdAndDate(staffId, eventDate);
	}

}
