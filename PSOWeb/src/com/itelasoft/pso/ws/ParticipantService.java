package com.itelasoft.pso.ws;

import java.util.ArrayList;
import java.util.List;

import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.services.IStudentService;
import com.itelasoft.pso.services.ServiceLocator;

public class ParticipantService {

	private IStudentService studentService;

	public ParticipantService() {
		studentService = ServiceLocator.getServiceLocator().getStudentService();
	}

	public Participant[] listByName(String studentName) {
	//	System.out.println("#######" + studentName + "------");
		List<Student> students;
		if (studentName != null) {
			studentName = studentName.replace("*", "%");
			if (studentName.equals("%%"))
				studentName = null;
		}
		//System.out.println("#######" + studentName + "------");
		if (studentName != null && !studentName.isEmpty()) {
			students = studentService.listByName(studentName,false);

		} else
			students = studentService.findAll();
		if (students != null) {
			Participant[] participants = new Participant[students.size()];
			int i = 0;
			for (Student s : students) {
				participants[i++] = new Participant(s);
			}
			return participants;
		} else
			return new Participant[0];

	}

	public Participant retrieve(Integer id) {
		return new Participant(studentService.retrieve(id.longValue()));
	}

}
