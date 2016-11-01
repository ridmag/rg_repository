package com.itelasoft.pso.ws;

import java.util.List;

import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.services.IStudentService;
import com.itelasoft.pso.services.ServiceLocator;

public class ParticipantService2 {

	private IStudentService studentService;

	public ParticipantService2() {
		studentService = ServiceLocator.getServiceLocator().getStudentService();
	}

	public Participant create(Participant o) {
		return new Participant(studentService.create(o.student));
	}

	public Participant update(Participant o) {
		return new Participant(studentService.update(o.student));
	}

	public Participant[] listByName(String studentName) {
		List<Student>  students;
		if(studentName != null && !studentName.isEmpty())
			students =studentService.listByName(studentName, true);
		else
			students = studentService.findAll();
		Participant[] participants = new Participant[students.size()];
		int i = 0;
		for(Student s:students){
			participants[i++]=new Participant(s);
		}
		return participants;
	}

	public Participant retrieve(Integer id) {
		return new Participant(studentService.retrieve(id.longValue()));
	}

}
