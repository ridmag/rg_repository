package com.itelasoft.pso.ws;

import java.util.ArrayList;
import java.util.List;

import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.services.IStaffMemberService;
import com.itelasoft.pso.services.ServiceLocator;
import com.itelasoft.pso.ws.value.Staff;

public class APIStaffService {

	private IStaffMemberService staffMemberService;

	public APIStaffService() {
		staffMemberService = ServiceLocator.getServiceLocator()
				.getStaffMemberService();
	}

	/*
	 * public Staff create(Staff o) { return new
	 * Staff(staffMemberService.create(o.getStaff())); }
	 * 
	 * public Staff update(Staff o) { return new
	 * Staff(staffMemberService.update(o.getStaff())); }
	 */

	public Staff[] listByName(String studentName) {
		if (studentName != null) {
			studentName = studentName.replace("*", "%");
			if (studentName.equals("%%"))
				studentName = null;
		}
		List<StaffMember> staffMembers;
		if (studentName != null && !studentName.isEmpty())
			staffMembers = staffMemberService.listByName(studentName);
		else
			staffMembers = staffMemberService.findAll();
		ArrayList<Staff> staffList = new ArrayList<Staff>();
		int i = 0;
		for (StaffMember s : staffMembers) {
			staffList.add(new Staff(s));
		}
		return staffList.toArray(new Staff[staffList.size()]);
	}

	public Staff retrieve(Integer id) {
		return new Staff(staffMemberService.retrieve(id.longValue()));
	}

}
