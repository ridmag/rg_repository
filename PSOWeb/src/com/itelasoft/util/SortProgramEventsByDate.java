package com.itelasoft.util;

import java.util.Comparator;

import com.itelasoft.pso.beans.ProgramEvent;

public class SortProgramEventsByDate implements Comparator<ProgramEvent> {

	@Override
	public int compare(ProgramEvent arg0, ProgramEvent arg1) {
		int i;
		i = arg0.getEventDate().compareTo(arg1.getEventDate());
		if (i == 0)
			i = arg0.getProgram().getName()
					.compareTo(arg1.getProgram().getName());
		if (i == 0)
			i = arg0.getGroup().getName()
					.compareTo(arg1.getGroup().getName());
		return i;
	}

}
