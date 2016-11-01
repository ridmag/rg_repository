package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.GroupedStaff;
import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.beans.StaffMember;

public interface IStaffMemberService extends IGenericService<StaffMember, Long> {

	public boolean validateStaffId(Long userId, String staffId);

	public List<StaffMember> listByName(String staffName);

	public List<StaffMember> listAvailableByName(String searchText);

	public StaffMember searchByStaffId(String staffId);

	public List<StaffMember> listByStaffType(Long staffTypeId);

	public List<StaffMember> listByStaffTypeNStatus(Long staffTypeId,
			String staffStatus);

	/**
	 * This will duplicate the root entities for the staff members who have morethan one staff skill
	 * @return
	 */
	public List<StaffMember> listActiveStaffMembers();

	public boolean isHavingActivePrograms(Long staffId);

	public boolean isHavingPendingEvents(Long staffId);

	public boolean checkAvailabilityByDate(Long staffId, Date eventDate);

	public double getTotalWorkHours(Long staffId, Date startDate, Date endDate);

	public List<StaffMember> listAvailableByNameNGroup(Long groupId,
			String staffName);

	public List<StaffMember> listAvailableByStaffIdNGroup(Long groupId, String staffId);

	/**
	 * This will remove staff as coordinator from programs/proEvents, delete
	 * groupedStaffs and staffEvents. Only selected items of the list are
	 * processed.
	 * 
	 * @param programs
	 * @param proEvents
	 * @param groupedStaffs
	 * @param staffEvents
	 */
	public void updateActiveRecords(List<Program> programs,
			List<ProgramEvent> proEvents, List<GroupedStaff> groupedStaffs,
			List<StaffEvent> staffEvents) throws Exception;
	

	public StaffMember retrieveEager(Long id);
	
	public List<StaffMember> listAll();
}
