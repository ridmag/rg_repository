package com.itelasoft.util;

import java.util.Comparator;

import com.itelasoft.pso.beans.StudentEvent;

public class SortEventsBySequence implements Comparator<StudentEvent> {

	@Override
	public int compare(StudentEvent arg0, StudentEvent arg1) {
		if (arg0.getProEvent().getProgram().getType().getName()
				.equals("Transport")) {
			return arg0.getGroupedStudent().getSequence()
					- arg1.getGroupedStudent().getSequence();
		}
		return (int) (arg0.getGroupedStudent().getStudent().getId() - arg1
				.getGroupedStudent().getStudent().getId());
	}

}