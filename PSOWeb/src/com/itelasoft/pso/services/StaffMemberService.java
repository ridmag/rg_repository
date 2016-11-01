package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;

import com.itelasoft.pso.beans.GroupedStaff;
import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.dao.IStaffMemberDao;

public class StaffMemberService extends GenericService<StaffMember, Long>
		implements IStaffMemberService {

	private IStaffMemberDao staffMemberDao = null;

	public boolean validateStaffId(Long userId, String staffId) {
		return this.staffMemberDao.validateStaffId(userId, staffId);
	}

	public List<StaffMember> listByName(String staffName) {
		return this.staffMemberDao.listByName(staffName);
	}

	public List<StaffMember> listAvailableByName(String searchText) {
		return this.staffMemberDao.listAvailableByName(searchText);
	}

	public StaffMember searchByStaffId(String staffId) {
		return this.staffMemberDao.searchByStaffId(staffId);
	}

	public List<StaffMember> listByStaffType(Long staffTypeId) {
		return this.staffMemberDao.listByStaffType(staffTypeId);
	}

	public List<StaffMember> listByStaffTypeNStatus(Long staffTypeId,
			String staffStatus) {
		return this.staffMemberDao.listByStaffTypeNStatus(staffTypeId,
				staffStatus);
	}

	public List<StaffMember> listActiveStaffMembers() {
		return this.staffMemberDao.listActiveStaffMembers();
	}

	public boolean isHavingActivePrograms(Long staffId) {
		return this.staffMemberDao.isHavingActivePrograms(staffId);
	}

	public boolean isHavingPendingEvents(Long staffId) {
		return this.staffMemberDao.isHavingPendingEvents(staffId);
	}

	public boolean checkAvailabilityByDate(Long staffId, Date eventDate) {
		return this.staffMemberDao.checkAvailabilityByDate(staffId, eventDate);
	}

	public double getTotalWorkHours(Long staffId, Date startDate, Date endDate) {
		return staffMemberDao.getTotalWorkHours(staffId, startDate,endDate);
	}

	public List<StaffMember> listAvailableByNameNGroup(Long groupId,
			String staffName) {
		return this.staffMemberDao
				.listAvailableByNameNGroup(groupId, staffName);
	}

	public List<StaffMember> listAvailableByStaffIdNGroup(Long groupId, String staffId) {
		return this.staffMemberDao.listAvailableByStaffIdNGroup(groupId,
				staffId);
	}

	public void updateActiveRecords(List<Program> programs,
			List<ProgramEvent> proEvents, List<GroupedStaff> groupedStaffs,
			List<StaffEvent> staffEvents) {
		this.staffMemberDao.updateActiveRecords(programs, proEvents,
				groupedStaffs, staffEvents);
	}

	public void setStaffMemberDao(IStaffMemberDao staffMemberDao) {
		this.staffMemberDao = staffMemberDao;
	}

	@Override
	public StaffMember retrieveEager(Long id) {
		StaffMember member = super.retrieve(id);
		if (member.getPhoto() != null)
			Hibernate.initialize(member.getPhoto().getBlobFileData());
		Hibernate.initialize(member.getLeaveCategories());
		return member;
	}
	public List<StaffMember> listAll(){
		return this.staffMemberDao.listAll();
	}

}
