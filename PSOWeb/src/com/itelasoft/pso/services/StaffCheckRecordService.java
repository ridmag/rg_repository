package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.StaffCheckRecord;
import com.itelasoft.pso.dao.IStaffCheckRecordDao;

public class StaffCheckRecordService extends
		GenericService<StaffCheckRecord, Long> implements
		IStaffCheckRecordService {

	private IStaffCheckRecordDao recordDao;

	public void setRecordDao(IStaffCheckRecordDao recordDao) {
		this.recordDao = recordDao;
	}

	public IStaffCheckRecordDao getRecordDao() {
		return recordDao;
	}

	public List<StaffCheckRecord> getRecordsFromStaff(Long staffId) {
		return this.recordDao.getRecordsFromStaff(staffId);
	}

	public boolean checkStaffRecordAvailability(String staffId, Long checkId) {
		return this.recordDao.checkStaffRecordAvailability(staffId, checkId);
	}

}
