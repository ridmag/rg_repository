package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.GroupedStaff;
import com.itelasoft.pso.dao.IGroupedStaffDao;

public class GroupedStaffService extends GenericService<GroupedStaff, Long>
		implements IGroupedStaffService {

	public List<GroupedStaff> listByStaff(Long staffId) {
		return ((IGroupedStaffDao) dao).listByStaff(staffId);
	}
	
	public List<GroupedStaff> listByStaffID(String staffId,Date startDate,Date endDate) {
		return ((IGroupedStaffDao) dao).listByStaffID(staffId,startDate,endDate);
	}
	
	public List<GroupedStaff> listByStaffID1(String staffId) {
		return ((IGroupedStaffDao) dao).listByStaffID1(staffId);
	}

	public List<GroupedStaff> listByStaffIdRoster(Date startDate,Date endDate) {
		return ((IGroupedStaffDao) dao).listByStaffIdRoster(startDate,endDate);
	}
	
	public List<GroupedStaff> listByGroup(Long groupId) {
		return ((IGroupedStaffDao) dao).listByGroup(groupId);
	}

	public GroupedStaff retrieveByGroupNStaffMember(Long groupid,
			Long staffid) {
		return ((IGroupedStaffDao) dao).retrieveByGroupNStaffMember(groupid, staffid);
	}

}
