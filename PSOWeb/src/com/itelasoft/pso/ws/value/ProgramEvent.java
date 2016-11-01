package com.itelasoft.pso.ws.value;

import java.util.Date;

import com.itelasoft.pso.beans.Location;
import com.itelasoft.pso.beans.Program;

public class ProgramEvent {
	public com.itelasoft.pso.beans.ProgramEvent programEvent;

	public Date getStartTime() {
		return programEvent.getStartTime();
	}

	public void setStartTime(Date startTime) {
		programEvent.setStartTime(startTime);
	}

	public Integer getId() {
		return programEvent.getId().intValue();
	}

	public void setId(Long id) {
		programEvent.setId(id);
	}

	public String getProgram() {
		return programEvent.getProgram().getName();
	}

	public void setProgram(Program program) {
		programEvent.setProgram(program);
	}

	public String getLocation() {
		return programEvent.getLocation().getName();
	}

	public void setLocation(Location location) {
		programEvent.setLocation(location);
	}

	public String getName() {
		return programEvent.getName();
	}

	

	public String getGroup() {
		return programEvent.getGroup().getName();
	}

	public ProgramEvent(com.itelasoft.pso.beans.ProgramEvent programEvent) {
		this.programEvent = programEvent;
	}
}
