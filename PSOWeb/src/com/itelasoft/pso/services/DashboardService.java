package com.itelasoft.pso.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;

import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.dao.IProgramDao;
import com.itelasoft.util.SortObjectByName;

public class DashboardService implements IDashboardService {

	private IProgramDao programDao;

	public void setProgramDao(IProgramDao programDao) {
		this.programDao = programDao;
	}

	public List<Program> listProgramsWithData(Date fromDate, Date toDate, ProgramType programType) {
		List<Program> programs = programDao.listWithData(fromDate, toDate, null, programType);
		for (Program program : programs) {
			for (ProgramEvent pe : program.getProgramEvents()) {
				Hibernate.initialize(pe.getStaffEvents());
				Collections.sort(pe.getStaffEvents(), new SortObjectByName());
//				if (programType.getId() != 3) {
				Hibernate.initialize(pe.getStudentEvents());
				Collections.sort(pe.getStudentEvents(), new SortObjectByName());
//				}
			}
		}
		return programs;
	}
}