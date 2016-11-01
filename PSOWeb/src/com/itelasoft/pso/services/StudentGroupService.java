package com.itelasoft.pso.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;

import com.itelasoft.pso.beans.StudentGroup;
import com.itelasoft.pso.dao.IGroupedStudentDao;
import com.itelasoft.pso.dao.IStudentGroupDao;
import com.itelasoft.util.SortObjectByName;

public class StudentGroupService extends GenericService<StudentGroup, Long> implements IStudentGroupService {

	private IGroupedStudentDao groupedStudentDao;

	public void updateGroups(StudentGroup mainGroup, StudentGroup returnGroup) throws Exception {
		if (mainGroup != null)
			((IStudentGroupDao) dao).update(mainGroup);
		if (returnGroup != null)
			((IStudentGroupDao) dao).update(returnGroup);
	}

	public List<StudentGroup> listByProgram(Long programId) {
		return ((IStudentGroupDao) dao).listByProgram(programId);
	}

	public List<StudentGroup> listMainTranportGroups(Long programId) {
		return ((IStudentGroupDao) dao).listMainTranportGroups(programId);
	}

	public boolean validateGroupName(StudentGroup studentGroup) {
		return ((IStudentGroupDao) dao).validateGroupName(studentGroup);
	}

	public List<StudentGroup> listAvailableByStudentNProgram(Long studentId, String programName,
			String programTypeName) {
		return ((IStudentGroupDao) dao).listAvailableByStudentNProgram(studentId, programName, programTypeName);
	}

	public List<StudentGroup> listAvailableByProgram(String programName, String programTypeName) {
		return ((IStudentGroupDao) dao).listAvailableByProgram(programName, programTypeName);
	}

	public List<StudentGroup> listAvailableByGroup(String groupName) {
		return ((IStudentGroupDao) dao).listAvailableByGroup(groupName);
	}

	public List<StudentGroup> retrieveAvailableByStudentNProgramId(Long studentId, Long programId, String programType) {
		return ((IStudentGroupDao) dao).retrieveAvailableByStudentNProgramId(studentId, programId, programType);
	}

	public StudentGroup retrieveWithStaffMembers(Long groupId) {
		return ((IStudentGroupDao) dao).retrieveWithStaffMembers(groupId);
	}

	public StudentGroup retrieveWithData(Long groupId, boolean withImage) {
		StudentGroup group = ((IStudentGroupDao) dao).retrieveWithStaffMembersWithSkills(groupId);
		if (group != null) {
			if (withImage && group.getPhoto() != null)
				Hibernate.initialize(group.getPhoto().getBlobFileData());
			group.setStudents(groupedStudentDao.listByGroup(groupId));
		}
		Collections.sort(group.getStudents(), new SortObjectByName());
		Collections.sort(group.getStaffMembers(), new SortObjectByName());
		return group;
	}

	public StudentGroup getReturnGroup(Long mainGroupdId, boolean withData) {
		StudentGroup group = ((IStudentGroupDao) dao).getReturnGroup(mainGroupdId, withData);
		if (withData && group != null) {
			group.setStudents(groupedStudentDao.listByGroup(group.getId()));
		}
		return group;
	}

	public void setGroupedStudentDao(IGroupedStudentDao groupedStudentDao) {
		this.groupedStudentDao = groupedStudentDao;
	}

	public List<StudentGroup> listByCriteria(Long studentGroupId, Date startDate, Date endDate) {
		return ((IStudentGroupDao) dao).listByCriteria(studentGroupId, startDate, endDate);
	}

	public StudentGroup retrieveWithGroupData(Long groupId) {
		return ((IStudentGroupDao) dao).retrieveWithGroupData(groupId);
	}

	public StudentGroup retrieveEager(Long studentGroupId) {
		StudentGroup studentGroup = dao.retrive(studentGroupId);
		Hibernate.initialize(studentGroup.getStaffSkills());
		return studentGroup;
	}

}
