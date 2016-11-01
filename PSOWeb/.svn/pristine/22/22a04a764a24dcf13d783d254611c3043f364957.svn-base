package com.itelasoft.pso.services;

import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.dao.IProgramTypeDao;

public class ProgramTypeService extends GenericService<ProgramType, Long>
		implements IProgramTypeService {

	public ProgramType retrieveByName(String programTypeName) {
		return ((IProgramTypeDao) dao).retrieveByName(programTypeName);
	}

}
