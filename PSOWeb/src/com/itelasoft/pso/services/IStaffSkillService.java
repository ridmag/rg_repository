package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.StaffSkill;

public interface IStaffSkillService extends IGenericService<StaffSkill, Long> {

	public List<StaffSkill> getSkillbyStaff(Long staffId);
}
