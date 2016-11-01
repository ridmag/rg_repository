package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.GroupedStaff;
import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.beans.StaffMember;

public interface IStaffMemberDao extends IGenericDao<StaffMember, Long> {

	public boolean validateStaffId(Long userId, String staffId);

	public List<StaffMember> listByName(String staffName);

	public List<StaffMember> listAvailableByName(String searchText);

	public StaffMember searchByStaffId(String staffId);

	public List<StaffMember> listByStaffType(Long staffTypeId);

	public List<StaffMember> listByStaffTypeNStatus(Long staffTypeId,
			String staffStatus);

	public List<StaffMember> listActiveStaffMembers();

	public boolean isHavingActivePrograms(Long staffId);

	public boolean isHavingPendingEvents(Long staffId);

	public boolean checkAvailabilityByDate(Long staffId, Date eventDate);

	public double getTotalWorkHours(Long staffId, Date startDate,Date endDate);

	public List<StaffMember> listAvailableByNameNGroup(Long groupId,
			String staffName);

	public List<StaffMember> listAvailableByStaffIdNGroup(Long groupId, String staffId);

	public void updateActiveRecords(List<Program> programs,
			List<ProgramEvent> proEvents, List<GroupedStaff> groupedStaffs,
			List<StaffEvent> staffEvents);
	
    public List<StaffMember> listAll();
}
