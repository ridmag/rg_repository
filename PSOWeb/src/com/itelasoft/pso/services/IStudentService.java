package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.Student;

public interface IStudentService extends IGenericService<Student, Long> {

	public List<Student> listByName(String studentName, boolean includeInactive);

	public List<Student> listAvailableByGroup(Long groupId, String studentName);

	public Student retrieveAvailableByGroup(Long groupId, Long studentId);

	public List<Student> listByCommunication(Long communicationId);

	public List<Student> listNotInCommunication(Long communicationId);

	public Student retrieveEager(Long studentId);

	public List<Student> listStudentsWithTxs(int month, int year);

	public List<Long> listStudentsWithNotBankedEvents(int month, int year);

	public List<Long> listStudentsMissingPreviousMonthsInvoice(int month, int year, Date actStatementStartDate);

	public List<Student> listByStatus(String status);

	public List<Student> listActiveStudents();
	
	public List<Student> listAll();

	public List<Student> listActiveStudentswithNdisnumber();
	
	public List<Student> listStudentByInvoice(Long invoiceId);
	
	public List<Student> retrieveAvailableByTwoGroups(Long groupId1, Long groupId2);
}
