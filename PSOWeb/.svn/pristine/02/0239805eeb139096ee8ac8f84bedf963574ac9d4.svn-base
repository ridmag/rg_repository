package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.GroupedStaff;

public interface IGroupedStaffService extends
		IGenericService<GroupedStaff, Long> {

	public List<GroupedStaff> listByStaff(Long staffId);
	
	public List<GroupedStaff> listByStaffID(String staffId,Date startDate,Date endDate);
	
	public List<GroupedStaff> listByStaffID1(String staffId);
	
	public List<GroupedStaff> listByStaffIdRoster(Date startDate,Date endDate);

	public List<GroupedStaff> listByGroup(Long groupId);
	
	public GroupedStaff retrieveByGroupNStaffMember(Long groupid,
			Long staffid);

}
