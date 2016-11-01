package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.StudentGroup;

public class StudentGroupDao extends GenericDao<StudentGroup, Long> implements
		IStudentGroupDao {

	@SuppressWarnings("unchecked")
	public List<StudentGroup> listByProgram(Long programId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentGroup.class)
				.addOrder(Order.asc("name"));
		criteria = criteria.createCriteria("program").add(
				Restrictions.eq("id", programId));
		return criteria.list();
	}

	public List<StudentGroup> listMainTranportGroups(Long programId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentGroup.class)
				.add(Restrictions.eq("program.id", programId))
				.add(Restrictions.isNull("parentGroup"))
				.addOrder(Order.asc("name"));
		@SuppressWarnings("unchecked")
		List<StudentGroup> list = criteria.list();
		return list;
	}

	public StudentGroup retrieveWithStaffMembers(Long groupId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentGroup.class)
				.add(Restrictions.eq("id", groupId))
				.setFetchMode("staffMembers", FetchMode.JOIN);
		return (StudentGroup) criteria.uniqueResult();
	}
	
	public StudentGroup retrieveWithStaffMembersWithSkills(Long groupId) {
		StudentGroup withStaff=retrieveWithStaffMembers(groupId);
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentGroup.class)
				.add(Restrictions.eq("id", groupId))
				.setFetchMode("staffSkills", FetchMode.JOIN);
		StudentGroup onlystaff=(StudentGroup) criteria.uniqueResult();
		withStaff.setStaffSkills(onlystaff.getStaffSkills());
		return withStaff;
	}

	public StudentGroup getReturnGroup(Long mainGroupId, boolean withStaff) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentGroup.class).add(
				Restrictions.eq("parentGroup.id", mainGroupId));
		if (withStaff)
			criteria.setFetchMode("staffMembers", FetchMode.JOIN);
		return (StudentGroup) criteria.uniqueResult();
	}

	public boolean validateGroupName(StudentGroup studentGroup) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentGroup.class);
		if (studentGroup.getId() != null) {
			criteria.add(Restrictions.ne("id", studentGroup.getId()));
		}
		criteria.add(
				Restrictions.eq("name", studentGroup.getName()).ignoreCase())
				.createCriteria("program")
				.add(Restrictions.eq("id", studentGroup.getProgram().getId()));
		StudentGroup group = (StudentGroup) criteria.uniqueResult();
		if (group != null)
			return false;
		else
			return true;
	}

	public List<StudentGroup> listAvailableByStudentNProgram(Long studentId,
			String programName, String programTypeName) {
		Session session = getCurrentSession();
		Query query;
		query = session
				.createQuery("from StudentGroup as gro where gro not in(select grostu.group from GroupedStudent grostu where grostu.student.id = ?) and lower(gro.program.name) like ? and gro.program.type.name = ? order by gro.program.status, gro.name,gro.program.name");
		query.setParameter(0, studentId);
		query.setParameter(1, "%" + programName.toLowerCase() + "%");
		query.setParameter(2, programTypeName);
		@SuppressWarnings("unchecked")
		List<StudentGroup> list = query.list();
		return list;
	}

	public List<StudentGroup> listAvailableByProgram(String programName,
			String programTypeName) {
		Session session = getCurrentSession();
		Query query;
		if (programName != null) {
			query = session
					.createQuery("from StudentGroup as gro where lower(gro.program.name) like ? order by gro.program.status, gro.program.name, gro.name");
			query.setParameter(0, "%" + programName.toLowerCase() + "%");
		
		} else {

			query = session
					.createQuery("from StudentGroup as gro order by gro.program.status, gro.program.name, gro.name");
		
		}

		@SuppressWarnings("unchecked")
		List<StudentGroup> list = query.list();
		return list;
	}
	
	public List<StudentGroup> listAvailableByGroup(String groupName) {
		Session session = getCurrentSession();
		Query query;
		if (groupName != null) {
			query = session
					.createQuery("from StudentGroup as gro where lower(gro.name) like ? order by gro.program.status, gro.program.name, gro.name");
			query.setParameter(0, "%" + groupName.toLowerCase() + "%");
		
		} else {

			query = session
					.createQuery("from StudentGroup as gro order by gro.program.status, gro.program.name, gro.name");
		
		}

		@SuppressWarnings("unchecked")
		List<StudentGroup> list = query.list();
		return list;
	}


	public List<StudentGroup> retrieveAvailableByStudentNProgramId(
			Long studentId, Long programId, String programTypeName) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from StudentGroup as gro where gro not in(select grostu.group from GroupedStudent grostu where grostu.student.id = ?) and gro.program.id = ? and gro.program.type.name = ?");
		query.setParameter(0, studentId);
		query.setParameter(1, programId);
		query.setParameter(2, programTypeName);
		@SuppressWarnings("unchecked")
		List<StudentGroup> list = query.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<StudentGroup> listByCriteria(Long studentGroupId,Date startDate,Date endDate) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentGroup.class)
				.add(Restrictions.eq("id", studentGroupId));
		
	
		if (startDate != null) {
			criteria = criteria.add(Restrictions.gt("startDate", startDate));
		}
		if (endDate != null) {
			criteria = criteria.add(Restrictions.le("endDate", endDate));
		}

		return criteria.list();
		
	}
		public StudentGroup retrieveWithGroupData(Long groupId) {
			Session session = getCurrentSession();
			Criteria criteria = session.createCriteria(StudentGroup.class)
					.add(Restrictions.eq("id", groupId));
			return (StudentGroup) criteria.uniqueResult();
		}
	
	

}
