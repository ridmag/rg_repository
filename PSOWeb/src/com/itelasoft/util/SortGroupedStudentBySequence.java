package com.itelasoft.util;

import java.util.Comparator;

import com.itelasoft.pso.beans.GroupedStudent;

public class SortGroupedStudentBySequence implements Comparator<GroupedStudent> {

	@Override
	public int compare(GroupedStudent arg0, GroupedStudent arg1) {
		return arg0.getSequence() - arg1.getSequence();
	}

}
