package com.itelasoft.pso.ws.value;

import com.itelasoft.pso.beans.WeekDay;

public class StudentGroup {
	public StudentGroup(com.itelasoft.pso.beans.StudentGroup studentGroup) {
		this.studentGroup = studentGroup;
		// TODO Auto-generated constructor stub
	}

	public com.itelasoft.pso.beans.StudentGroup studentGroup;

	public Integer getId(){
		return studentGroup.getId().intValue();
	}
	public String getName() {
		return studentGroup.getName();
	}

	public String getStatus() {
		return studentGroup.getProgram().getStatus();
	}

	

	public String getProgram() {
		return studentGroup.getProgram().getName();
	}

	public String getLocation() {
		if( studentGroup.getLocation()!=null)
		return studentGroup.getLocation().getName();
		else
			return "";
	}

	

	public String getWeekDays() {
		String string ="";
		for(WeekDay weekDay:studentGroup.getWeekDays()){
			if(!string.isEmpty())
				string+=", ";
			string+= weekDay.getName();
		}
		return string;
	}

	

}
