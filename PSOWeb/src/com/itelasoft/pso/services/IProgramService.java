package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.Vehicle;

public interface IProgramService extends IGenericService<Program, Long> {

	public void deleteWithDependencies(Long programId);

	public List<Program> listActivePrograms();

	public List<Program> listActiveByCoordinator(Long staffId);

	public List<Program> listByCriteria(String programName, List<ProgramType> programTypes, String status,
			boolean withGroups);

	public Program retrieveById(Long programId, List<ProgramType> programTypes);

	public List<Student> listAvailableStudents(Long progrmId, String name);

	public Student retrieveStudent(Long programId, Long studentId);

	public List<Student> listAvailableTransportStudents(Long progrmId, String name);

	public List<Program> listActiveProgramsWithGroups();

	/**
	 * This will list all the active programs with program events and their
	 * staff events pre-loaded if the staffId is provided. Otherwise will have
	 * only list of program events loaded.
	 * 
	 * @param startDate
	 * @param endDate
	 * @param staffId
	 * @return List<Program>
	 */
	public List<Program> createRoster(Date startDate, Date endDate, String staffId);

	/**
	 * This will refresh and return the provided list of program's program
	 * events with staff/vehicle/event status with minimum DB queries
	 * 
	 * @param List
	 *            <Program>
	 */
	public List<Program> refreshRoster(List<Program> programs);

	/**
	 * This will set the availability of staffMember for the passed event's date
	 * by available days of the week and leaves. While setting the available
	 * status, this sets the staff skills and staff color as well. All the
	 * values will be set to the temporary variables in StaffEvent for roster
	 * use
	 * 
	 * @param staffEvent
	 * @param programEvent
	 */
	public void setStaffAvailabilityNSkills(StaffEvent staffEvent, ProgramEvent programEvent);

	/**
	 * Checks the conflicting program events of provided programs with passed
	 * program/staff events and set the staffStatus, staffColor and conflicting
	 * events into given staffEvent. All the values will be set to the temporary
	 * variables in StaffEvent for roster use
	 * 
	 * @param List
	 *            <Program> programs
	 * @param staffEvent
	 * @param currentEvent
	 */
	public void setStaffStatusByEvents(List<Program> programs, StaffEvent staffEvent, ProgramEvent currentEvent);

	/**
	 * Checks the conflicting program events of provided programs with passed
	 * program event, vehicle and set the vehicleStatus, vehicleColor and
	 * conflicting events into given vehicle. All the values will be set to the
	 * temporary variables in vehicle for roster use
	 * 
	 * @param List
	 *            <Program> programs
	 * @param vehicle
	 * @param currentEvent
	 */
	public void setVehicleStatusByEvents(List<Program> programs, Vehicle vehicle, ProgramEvent currentEvent);
	
	public List<Program> getProgramsbyDate(Date currentDate);
}
