package com.itelasoft.util;

import java.util.Comparator;

import com.itelasoft.pso.beans.StaffEvent;

public class SortStaffEventsByMemberName implements Comparator<StaffEvent> {

	@Override
	public int compare(StaffEvent arg0, StaffEvent arg1) {
		return arg0.getStaffMember().getContact().getFirstName()
				.compareTo(arg1.getStaffMember().getContact().getFirstName());
	}

}
