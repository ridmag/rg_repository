package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.Student;

public interface IProgramDao extends IGenericDao<Program, Long> {

	public void deleteWithDependencies(Long programId);

	/**
	 * This will return a list of programs along with it's programEvents
	 * 
	 * @param startDate
	 *            - This will search for programEvents starting from this date
	 * @param endDate
	 *            - This will search for programEvents till this date
	 * @param staffId
	 *            - If provided, only the programEvents will be returned which
	 *            has this staffMember assigned
	 * @param proType
	 *            - If provided, programEvents will be filtered for this
	 *            programType
	 * @return
	 */
	public List<Program> listWithData(Date startDate, Date endDate, String staffId, ProgramType programType);

	public List<Program> listActivePrograms();

	public List<Program> listActiveByCoordinator(Long staffId);

	public List<Program> listByCriteria(String programName, List<ProgramType> programTypes, String status,
			boolean withGroups);

	public Program retrieveById(Long programId, List<ProgramType> programTypes);

	public List<Student> listAvailableStudents(Long progrmId, String name);

	public Student retrieveStudent(Long programId, Long studentId);

	public List<Student> listAvailableTransportStudents(Long progrmId, String name);

	public List<GroupedStudent> listAvailableStudentsByProgramAndGroup(Long progrmId, Long groupId);

	public List<Program> listActiveProgramsWithGroups();

	public List<Program> getProgramsbyDate(Date currentDate);

}
