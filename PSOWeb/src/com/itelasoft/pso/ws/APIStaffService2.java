package com.itelasoft.pso.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.services.IStaffMemberService;
import com.itelasoft.pso.services.ServiceLocator;
import com.itelasoft.pso.ws.value.Staff;

@WebService(name = "StaffService2")
public class APIStaffService2 {

	private IStaffMemberService staffMemberService;

	public APIStaffService2() {
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
