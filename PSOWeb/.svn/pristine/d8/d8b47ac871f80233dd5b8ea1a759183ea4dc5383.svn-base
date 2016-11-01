package com.itelasoft.pso.ws;

import java.util.List;

import javax.jws.WebService;

import com.itelasoft.pso.beans.StudentGroup;
import com.itelasoft.pso.services.IProgramEventService;
import com.itelasoft.pso.services.IProgramService;
import com.itelasoft.pso.services.IStudentGroupService;
import com.itelasoft.pso.services.ServiceLocator;
import com.itelasoft.pso.ws.value.Program;


public class ProgramService {

	private IProgramService programService;
	private IProgramEventService programEventService;
	private IStudentGroupService studentGroupService;

	public ProgramService() {
		programService = ServiceLocator.getServiceLocator().getProgramService();
		programEventService = ServiceLocator.getServiceLocator()
				.getProgramEventService();
		studentGroupService = ServiceLocator.getServiceLocator()
				.getStudentGroupService();
	}

	public Program create(Program o) {
		return new Program(programService.create(o.program));
	}

	public Program update(Program o) {
		return new Program(programService.update(o.program));
	}

	public Program[] listByName(String name) {
		if (name != null) {
			name = name.replace("*", "%");
			if (name.equals("%%"))
				name = null;
		}
		List<com.itelasoft.pso.beans.Program> programs;
		if (name != null && !name.isEmpty())
			programs = programService.listByCriteria(name, null, null, false);
		else
			programs = programService.listActivePrograms();
		Program[] programs2 = new Program[programs.size()];
		int i = 0;
		for (com.itelasoft.pso.beans.Program p : programs) {
			programs2[i++] = new Program(p);
		}
		return programs2;
	}

	public Program retrieve(Integer id) {
		return new Program(programService.retrieve(id.longValue()));
	}

	public com.itelasoft.pso.ws.value.StudentGroup[] listProgStudentGroups(
			String name) {
		List<StudentGroup> list = null;
		if (name != null) {
			name = name.replace("*", "%");
			if (name.equals("%%"))
				name = null;
		}
		if (name != null && !name.isEmpty())
			list = studentGroupService.listAvailableByGroup(name);
		else
			list = studentGroupService.listAvailableByGroup(null);
		com.itelasoft.pso.ws.value.StudentGroup[] list2 = new com.itelasoft.pso.ws.value.StudentGroup[list
				.size()];
		int i = 0;
		for (com.itelasoft.pso.beans.StudentGroup s : list) {
			list2[i++] = new com.itelasoft.pso.ws.value.StudentGroup(s);
		}
		return list2;

	}

	public com.itelasoft.pso.ws.value.StudentGroup retrieveGroup(Integer id) {
		return new com.itelasoft.pso.ws.value.StudentGroup(
				studentGroupService.retrieve(id.longValue()));
	}

}
