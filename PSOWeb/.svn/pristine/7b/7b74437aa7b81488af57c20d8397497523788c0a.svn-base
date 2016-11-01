package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.StaffSkill;
import com.itelasoft.pso.dao.IStaffSkillDao;

public class StaffSkillService extends GenericService<StaffSkill, Long>
		implements IStaffSkillService {

	private IStaffSkillDao skillDao;

	public List<StaffSkill> getSkillbyStaff(Long staffId) {
		return this.skillDao.getSkillbyStaff(staffId);
	}

	public void setSkillDao(IStaffSkillDao skillDao) {
		this.skillDao = skillDao;
	}

}
