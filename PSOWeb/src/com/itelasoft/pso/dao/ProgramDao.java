package com.itelasoft.pso.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.Student;

public class ProgramDao extends GenericDao<Program, Long> implements IProgramDao {

	public void deleteWithDependencies(Long programId) {
		if (programId != null && programId != 0) {
			String pid = programId.toString();
			Session session = getCurrentSession();
			String queryString = "delete from programeventcoordinatorxref where programeventid in (select id from programevents where programid ="
					+ pid
					+ ");delete from transactions where studenteventid in (select id from studentevents where eventid in (select id from programevents where programid="
					+ pid
					+ "));delete from studentevents where eventid in (select id from programevents where programid="
					+ pid
					+ ");delete from transactions where programeventid in (select id from programevents where programid ="
					+ pid + ");delete from programevents where programid =" + pid
					+ ";delete from groupedstaffweekdaysxref where groupedstaffid in	(select gs.id from groupedstaff gs, studentgroups g where gs.groupid = g.id and g.programid ="
					+ pid
					+ ");delete from groupedstaffweekdays where groupedstaffid in (select id from groupedstaff where groupid in (select id from studentgroups where programid ="+ pid +"));delete from groupedstaff where groupid in (select id from studentgroups where programid ="
					+ pid
					+ ");delete from groupedstudents where groupid in (select id from studentgroups where programid ="
					+ pid
					+ ");delete from groupsweekdaysxref where groupid in (select id from studentgroups where programid ="
					+ pid
					+ ");delete from groupstaffskillsxref where groupid in (select id from studentgroups where programid ="
					+ pid + ");delete from studentgroups where programid =" + pid + ";delete from programs where id ="
					+ pid + ";";
			session.createSQLQuery(queryString).executeUpdate();
		}
	}

	public List<Program> listActivePrograms() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Program.class).createAlias("type", "programType")
				.add(Restrictions.ne("programType.name", "Staff")).add(Restrictions.eq("status", "Active"))
				.addOrder(Order.asc("name"));
		@SuppressWarnings("unchecked")
		List<Program> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<Program> listActiveByCoordinator(Long staffId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Program.class).createAlias("coordinator", "coordinator")
				.add(Restrictions.eq("coordinator.id", staffId)).addOrder(Order.asc("status"))
				.addOrder(Order.asc("name"));
		@SuppressWarnings("unchecked")
		List<Program> list = criteria.list();
		return list;
	}

	public List<Program> listActiveProgramsWithGroups() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Program.class)
				// .createAlias("type", "programType")
				// .add(Restrictions.ne("programType.name", "Staff"))
				.add(Restrictions.eq("status", "Active")).addOrder(Order.asc("name"))
				.setFetchMode("studentGroups", FetchMode.JOIN)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<Program> list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Program> listWithData(Date startDate, Date endDate, String staffId, ProgramType programType) {
		List<Program> programs = new ArrayList<Program>();
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).createAlias("program", "pro")
				.createAlias("group", "group").add(Restrictions.ge("eventDate", startDate))
				.add(Restrictions.le("eventDate", endDate))
				// .setFetchMode("staffEvents", FetchMode.JOIN)
				.addOrder(Order.asc("pro.id")).addOrder(Order.asc("eventDate"));
		if (staffId != null && !staffId.isEmpty()) {
			criteria.createAlias("staffEvents", "se").createAlias("se.staffMember", "staff")
					.add(Restrictions.eq("staff.staffId", staffId));
		}
		if (programType != null) {
			criteria.add(Restrictions.eq("pro.type", programType));
		}
		criteria.addOrder(Order.asc("group.name")).addOrder(Order.asc("location")).addOrder(Order.asc("vehicle"))
				.addOrder(Order.asc("startTime"));
		List<ProgramEvent> events = criteria.list();
		if (events != null && !events.isEmpty()) {
			Program program = null;
			for (ProgramEvent event : events) {
				if (program != null) {
					if (program.getId() == event.getProgram().getId()) {
						program.getProgramEvents().add(event);
					} else {
						programs.add(program);
						program = event.getProgram();
						program.setProgramEvents(new ArrayList<ProgramEvent>());
						program.getProgramEvents().add(event);
					}
				} else {
					program = event.getProgram();
					program.setProgramEvents(new ArrayList<ProgramEvent>());
					program.getProgramEvents().add(event);
				}
			}
			programs.add(program);
		}
		return programs;
	}

	@SuppressWarnings("unchecked")
	public List<Program> listByCriteria(String programName, List<ProgramType> programTypes, String status,
			boolean withGroups) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Program.class);
		if (programTypes != null && !programTypes.isEmpty()) {
			criteria.add(Restrictions.in("type", programTypes));
		}
		if (programName != null && !programName.isEmpty())
			criteria.add(Restrictions.like("name", programName, MatchMode.ANYWHERE).ignoreCase());
		if (status != null && !status.isEmpty())
			criteria.add(Restrictions.eq("status", status));
		criteria.addOrder(Order.asc("status")).addOrder(Order.asc("name"));
		if (withGroups)
			criteria.setFetchMode("studentGroups", FetchMode.JOIN)
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public Program retrieveById(Long programId, List<ProgramType> programTypes) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Program.class).add(Restrictions.eq("id", programId));
		if (programTypes != null && !programTypes.isEmpty())
			criteria.add(Restrictions.in("type", programTypes));
		Program program = (Program) criteria.uniqueResult();
		return program;
	}

	public List<Student> listAvailableStudents(Long progrmId, String name) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"from Student as stu where (stu not in(select prostu.student from ProgramStudent prostu where prostu.program.id = ?)) and (lower(stu.contact.firstName) like ? or lower(stu.contact.lastName) like ?) order by stu.contact.firstName, stu.contact.lastName");
		query.setParameter(0, progrmId);
		query.setParameter(1, "%" + name.toLowerCase() + "%");
		query.setParameter(2, "%" + name.toLowerCase() + "%");
		@SuppressWarnings("unchecked")
		List<Student> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<GroupedStudent> listAvailableStudentsByProgramAndGroup(Long progrmId, Long groupId) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"from ProgramStudent as stu, StudentGroup as sg, Program as pro where ((stu.program.id = ? and stu.group.id = ?) and (sg in(stu.group) and pro in(stu.program)))");
		query.setParameter(0, progrmId);
		query.setParameter(1, groupId);
		@SuppressWarnings("unchecked")
		List<GroupedStudent> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<Student> listAvailableTransportStudents(Long progrmId, String name) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"from Student as stu where (stu not in(select prostu.student from ProgramStudent prostu where prostu.program.id = ?)) and ((lower(stu.contact.firstName) like ? or lower(stu.contact.lastName) like ?) and stu.transport = true) order by stu.contact.firstName, stu.contact.lastName");
		query.setParameter(0, progrmId);
		query.setParameter(1, "%" + name.toLowerCase() + "%");
		query.setParameter(2, "%" + name.toLowerCase() + "%");
		@SuppressWarnings("unchecked")
		List<Student> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public Student retrieveStudent(Long programId, Long studentId) {
		Session session = getCurrentSession();
		Query query = session.createQuery(
				"from Student as stu where (stu not in(select prostu.student from ProgramStudent prostu where prostu.program.id = ?)) and stu.id = ?");
		query.setParameter(0, programId);
		query.setParameter(1, studentId);
		return (Student) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Program> getProgramsbyDate(Date currentDate) {
		List<Program> programs = new ArrayList<Program>();
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class).createAlias("program", "pro")
				.createAlias("group", "group").add(Restrictions.eq("eventDate", currentDate))
				.addOrder(Order.asc("pro.id")).addOrder(Order.asc("eventDate"));
		criteria.addOrder(Order.asc("group.name")).addOrder(Order.asc("location")).addOrder(Order.asc("vehicle"))
				.addOrder(Order.asc("startTime"));
		List<ProgramEvent> events = criteria.list();
		if (events != null && !events.isEmpty()) {
			Program program = null;
			for (ProgramEvent event : events) {
				if (program != null) {
					if (program.getId() == event.getProgram().getId()) {
						program.getProgramEvents().add(event);
					} else {
						programs.add(program);
						program = event.getProgram();
						program.setProgramEvents(new ArrayList<ProgramEvent>());
						program.getProgramEvents().add(event);
					}
				} else {
					program = event.getProgram();
					program.setProgramEvents(new ArrayList<ProgramEvent>());
					program.getProgramEvents().add(event);
				}
			}
			programs.add(program);
		}
		return programs;
	}

}