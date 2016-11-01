package com.itelasoft.pso.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;

import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.StaffEvent;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.StaffSkill;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.Vehicle;
import com.itelasoft.pso.beans.WeekDay;
import com.itelasoft.pso.dao.IProgramDao;
import com.itelasoft.pso.dao.IStaffMemberDao;
import com.itelasoft.util.SortStaffEventsByMemberName;

public class ProgramService extends GenericService<Program, Long> implements IProgramService {

	private IStaffMemberDao staffMemberDao = null;

	private SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("h:mma");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	public static HashMap<Long, Long> staffRosterTimeMap;
	double fortnightHours;

	public void setStaffMemberDao(IStaffMemberDao staffMemberDao) {
		this.staffMemberDao = staffMemberDao;
	}

	public void deleteWithDependencies(Long programId) {
		((IProgramDao) dao).deleteWithDependencies(programId);
	}

	public List<Program> listActivePrograms() {
		return ((IProgramDao) dao).listActivePrograms();
	}

	public List<Program> listActiveByCoordinator(Long staffId) {
		return ((IProgramDao) dao).listActiveByCoordinator(staffId);
	}

	public List<Program> listByCriteria(String programName, List<ProgramType> programTypes, String status,
			boolean withGroups) {
		return ((IProgramDao) dao).listByCriteria(programName, programTypes, status, withGroups);
	}

	public List<Student> listAvailableStudents(Long progrmId, String name) {
		return ((IProgramDao) dao).listAvailableStudents(progrmId, name);
	}

	public Student retrieveStudent(Long programId, Long studentId) {
		return ((IProgramDao) dao).retrieveStudent(programId, studentId);
	}

	public List<Student> listAvailableTransportStudents(Long progrmId, String name) {
		return ((IProgramDao) dao).listAvailableTransportStudents(progrmId, name);
	}

	public Program retrieveById(Long programId, List<ProgramType> programTypes) {
		return ((IProgramDao) dao).retrieveById(programId, programTypes);
	}

	public List<Program> listActiveProgramsWithGroups() {
		return ((IProgramDao) dao).listActiveProgramsWithGroups();
	}

	public List<Program> createRoster(Date startDate, Date endDate, String staffId) {
		List<Program> programs = ((IProgramDao) dao).listWithData(startDate, endDate, staffId, null);
		if (programs != null) {
			refreshRoster(programs);
		}
		return programs;
	}

	public List<Program> refreshRoster(List<Program> programs) {
		staffRosterTimeMap = new HashMap<Long, Long>();
		long startTime, endTime;
		for (Program program : programs) {
			for (ProgramEvent programevent : program.getProgramEvents()) {
				for (StaffEvent staffevent : programevent.getStaffEvents()) {

					startTime = (staffevent.getRosterStartTime() == null) ? programevent.getStartTime().getTime()
							: staffevent.getRosterStartTime().getTime();
					endTime = (staffevent.getRosterEndTime() == null) ? programevent.getEndTime().getTime()
							: staffevent.getRosterEndTime().getTime();
					Long timeDiff = endTime - startTime;
					Long staffId = staffevent.getStaffMember().getId();
					if (staffRosterTimeMap.containsKey(staffId)) {
						timeDiff = staffRosterTimeMap.get(staffId) + timeDiff;
						staffRosterTimeMap.put(staffId, timeDiff);
						timeDiff = new Long(0);
					} else {
						staffRosterTimeMap.put(staffId, timeDiff);
						timeDiff = new Long(0);
					}
				}
			}
		}

		for (Program program : programs) {
			for (ProgramEvent event : program.getProgramEvents()) {
				event.setStaffAvailabilityMap(new HashMap<Long, String>());
				if (event.getStaffEvents() != null && !event.getStaffEvents().isEmpty()) {
					sortStaffList(event);
					for (StaffEvent se : event.getStaffEvents()) {
						setStaffAvailabilityNSkills(se, event);
						if (se.getStaffStatus().equals("Available"))
							setStaffStatusByEvents(programs, se, event);
					}
					for (StaffEvent se : event.getStaffEvents()) {
						if (event.getStaffAvailabilityMap().containsKey(se.getStaffMember().getId())) {
							fortnightHours = 0.0;
							fortnightHours = se.getStaffMember().getHoursperFortnight();
							if (staffRosterTimeMap.get(se.getStaffMember().getId()) != 0) {
								if (staffRosterTimeMap.get(se.getStaffMember().getId()) / 3600000.0 > fortnightHours) {
									if (!se.getStaffColor().equals("red")) {
										se.setStaffColor("orange");
									}
								}
							}
						}
					}
					if (event.getStaffAvailabilityMap().containsValue("Available"))
						event.setEventColor("green");
					else
						event.setEventColor("red");
				} else {
					event.setEventColor("orange");
				}

				if (event.getProgram().getType().getName().equals("Transport")) {
					if (event.getVehicle() != null) {
						setVehicleStatusByEvents(programs, event.getVehicle(), event);
					} else if (!event.getStaffAvailabilityMap().containsValue("Available"))
						event.setEventColor("red");
					else
						event.setEventColor("orange");
				}
				if (event.getStatus().equals("finished")) {
					event.setEventColor("blue");
				}
				String title = "Event Time: " + timeFormat.format(event.getStartTime()) + " - "
						+ timeFormat.format(event.getEndTime()) + "\nStatus: " + (event.getStatus().equals("finished")
								? "Completed" : WordUtils.capitalize(event.getStatus()));
				title = title + "\nCoordinator(s):";
				if (event.getStaffEvents() != null) {
					for (StaffEvent se : event.getStaffEvents()) {
						double fnightHours = 0.0;
						fnightHours = se.getStaffMember().getHoursperFortnight();
						double staffHours = staffRosterTimeMap.get(se.getStaffMember().getId()) / 3600000.0;

						title = title + "\n   " + se.getStaffMember().getContact().getName() + " - "
								+ timeFormat.format(se.getRosterStartTime()) + " to "
								+ timeFormat.format(se.getRosterEndTime()) + " - "
								+ event.getStaffAvailabilityMap().get(se.getStaffMember().getId()) + " - " + staffHours
								+ " Hrs";
						if (staffHours > fnightHours) {
							title = title + " - Limit is Exceeded. ";
						}
					}
				} else
					title = title + "Not Available";
				event.setEventTitle(title);
			}
		}
		return programs;
	}

	private void sortStaffList(ProgramEvent event) {
		if (event != null && event.getStaffEvents() != null) {
			Collections.sort(event.getStaffEvents(), new SortStaffEventsByMemberName());
		}
	}

	public void setStaffAvailabilityNSkills(StaffEvent staffEvent, ProgramEvent programEvent) {
		String skills = "";
		StaffMember staff = staffEvent.getStaffMember();
		if (staff.getSkills() != null && !staff.getSkills().isEmpty()) {
			for (StaffSkill skill : staff.getSkills()) {
				skills = skills + skill.getSkill().getItemValue() + " (" + skill.getLevel().toString() + ")\n";
			}
		} else {
			skills = "No Skills Available..";
		}
		staffEvent.setStaffSkills(skills);
		if (programEvent.getStatus().equals("finished")) {
			staffEvent.setStaffStatus("Not Applicable");
			staffEvent.setStaffColor("black");
			return;
		}
		if (!staff.getStatus().equals("Current") && staff.getServiceEndDate() != null
				&& (staff.getServiceEndDate().before(programEvent.getEndTime())
						|| staff.getServiceEndDate().equals(programEvent.getEndTime()))) {
			staffEvent.setStaffStatus(staffEvent.getStaffMember().getStatus());
			programEvent.getStaffAvailabilityMap().put(staffEvent.getStaffMember().getId(), "Not Available");
			staffEvent.setStaffColor("red");
			return;
		}
		for (WeekDay day : staffEvent.getStaffMember().getUnAvailableDays()) {
			if (day.getName().equals(dayFormat.format(programEvent.getEndTime()))) {
				programEvent.getStaffAvailabilityMap().put(staffEvent.getStaffMember().getId(), "Not Available");
				staffEvent.setStaffStatus("Not Available");
				staffEvent.setStaffColor("red");
				return;
			}
		}
		if (!staffMemberDao.checkAvailabilityByDate(staffEvent.getStaffMember().getId(), programEvent.getEndTime())) {
			programEvent.getStaffAvailabilityMap().put(staffEvent.getStaffMember().getId(), "On Leave");
			staffEvent.setStaffStatus("On Leave");
			staffEvent.setStaffColor("red");
			return;
		}

		programEvent.getStaffAvailabilityMap().put(staffEvent.getStaffMember().getId(), "Available");
		staffEvent.setStaffStatus("Available");
		staffEvent.setStaffColor("green");
	}

	public void setStaffStatusByEvents(List<Program> programs, StaffEvent staffEvent, ProgramEvent currentEvent) {

		List<ProgramEvent> conflictingEvents = new ArrayList<ProgramEvent>();
		Map<Long, Date> conflictStartTimeMap = new HashMap<Long, Date>();
		Map<Long, Date> conflictEndTimeMap = new HashMap<Long, Date>();

		if (currentEvent.getStatus().equals("finished"))
			return;

		Long currentEventId = currentEvent.getId();
		Date currentEventDate = currentEvent.getEndTime();
		long currentStartTime = (staffEvent.getRosterStartTime() == null) ? currentEvent.getStartTime().getTime()
				: staffEvent.getRosterStartTime().getTime(); // currentEvent.getStartTime().getTime();
		long currentEndTime = (staffEvent.getRosterEndTime() == null) ? currentEvent.getEndTime().getTime()
				: staffEvent.getRosterEndTime().getTime(); // currentEvent.getEndTime().getTime();
		Long currentStaffId = staffEvent.getStaffMember().getId();

		for (Program program : programs) {
			for (ProgramEvent event : program.getProgramEvents()) {
				if (event.getId().equals(currentEventId)) {
					// Ignores the current program event since the same staff
					// can not be repeated
					continue;
				}
				if (!dateFormat.format(event.getEndTime()).equals(dateFormat.format(currentEventDate))) {
					// Ignores events which has event date other than the
					// current event's date
					continue;
				}
				for (StaffEvent se : event.getStaffEvents()) {
					long eventStartTime = (se.getRosterStartTime() == null) ? event.getStartTime().getTime()
							: se.getRosterStartTime().getTime(); // event.getStartTime().getTime();
					long eventEndTime = (se.getRosterEndTime() == null) ? event.getEndTime().getTime()
							: se.getRosterEndTime().getTime(); // event.getEndTime().getTime();
					// Consider events only conflict with the current event's
					// times
					if (se.getStaffMember().getId().equals(currentStaffId)) {
						if ((eventStartTime == currentStartTime && eventEndTime == currentEndTime)
								|| (((eventStartTime < currentStartTime && eventEndTime > currentStartTime)
										|| (eventStartTime < currentEndTime && eventEndTime > currentEndTime))
										|| ((eventStartTime > currentStartTime && eventStartTime < currentEndTime)
												|| (eventEndTime > currentStartTime
														&& eventEndTime < currentEndTime)))) {
							// Event conflicts with current event's
							// date/time/staffMember
							conflictingEvents.add(event);
							Date rosterStart = (se.getRosterStartTime() == null) ? event.getStartTime()
									: se.getRosterStartTime();
							Date rosterEnd = (se.getRosterEndTime() == null) ? event.getEndTime()
									: se.getRosterEndTime();
							conflictStartTimeMap.put(event.getId(), rosterStart);
							conflictEndTimeMap.put(event.getId(), rosterEnd);
						}
					}
				}
			}
		}
		if (!conflictingEvents.isEmpty()) {
			String assignedEventTitle = "Assigned Events, \n";
			for (ProgramEvent pe : conflictingEvents) {
				assignedEventTitle = assignedEventTitle
						.concat("   " + pe.getName() + " (" + timeFormat.format(conflictStartTimeMap.get(pe.getId()))
								+ " - " + timeFormat.format(conflictEndTimeMap.get(pe.getId())) + ")\n");
			}
			currentEvent.getStaffAvailabilityMap().put(staffEvent.getStaffMember().getId(), "Busy");
			staffEvent.setStaffStatus("Busy");
			staffEvent.setStaffColor("red");
			staffEvent.setConflictingEvents(assignedEventTitle);
		}
	}

	public void setVehicleStatusByEvents(List<Program> programs, Vehicle vehicle, ProgramEvent currentEvent) {
		List<ProgramEvent> conflictingEvents = new ArrayList<ProgramEvent>();

		if (currentEvent.getStatus().equals("finished")) {
			currentEvent.setVehicleColor("black");
			vehicle.setAvailability("Not Applicable");
			return;
		}

		if (vehicle.getStatus().equals("Inactive")) {
			currentEvent.setVehicleColor("red");
			vehicle.setAvailability("Inactive");
			return;
		}
		Long currentEventId = currentEvent.getId();
		Date currentEventDate = currentEvent.getEndTime();
		long currentStartTime = currentEvent.getStartTime().getTime();
		long currentEndTime = currentEvent.getEndTime().getTime();

		for (Program program : programs) {
			for (ProgramEvent event : program.getProgramEvents()) {
				if (event.getId().equals(currentEventId)) {
					// Ignores the current program event since the same vehicle
					// can not be repeated
					continue;
				}
				if (!dateFormat.format(event.getEndTime()).equals(dateFormat.format(currentEventDate))) {
					// Ignores events which has event date other than the
					// current event's date
					continue;
				}
				long eventStartTime = event.getStartTime().getTime();
				long eventEndTime = event.getEndTime().getTime();
				if ((eventStartTime == currentStartTime && eventEndTime == currentEndTime)
						|| (((eventStartTime < currentStartTime && eventEndTime > currentStartTime)
								|| (eventStartTime < currentEndTime && eventEndTime > currentEndTime))
								|| ((eventStartTime > currentStartTime && eventStartTime < currentEndTime)
										|| (eventEndTime > currentStartTime && eventEndTime < currentEndTime)))) {
					// Consider events only conflict with the current event's
					// times
					if (event.getVehicle() != null && event.getVehicle().getId().equals(vehicle.getId())) {
						// Event conflicts with current event's
						// date/time/vehicle
						conflictingEvents.add(event);
					}
				}
			}
			if (!conflictingEvents.isEmpty()) {
				String assignedEventTitle = "Assigned Events, \n";
				for (ProgramEvent pe : conflictingEvents) {
					assignedEventTitle = assignedEventTitle
							.concat("   " + pe.getName() + " (" + timeFormat.format(pe.getStartTime()) + " - "
									+ timeFormat.format(pe.getEndTime()) + ")\n");
				}
				currentEvent.setVehicleColor("red");
				vehicle.setAvailability("Busy");
				vehicle.setConflictingEvents(assignedEventTitle);
			} else {
				currentEvent.setVehicleColor("green");
				vehicle.setAvailability("Available");
			}
		}
	}

	public static HashMap<Long, Long> getStaffRosterTimeMap() {
		return staffRosterTimeMap;
	}

	public static void setStaffRosterTimeMap(HashMap<Long, Long> staffRosterTimeMap) {
		ProgramService.staffRosterTimeMap = staffRosterTimeMap;
	}

	public List<Program> getProgramsbyDate(Date currentDate) {
		return ((IProgramDao) dao).getProgramsbyDate(currentDate);
	}
}
