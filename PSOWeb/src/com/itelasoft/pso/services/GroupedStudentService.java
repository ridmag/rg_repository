package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentGroup;
import com.itelasoft.pso.dao.IGroupedStudentDao;

public class GroupedStudentService extends GenericService<GroupedStudent, Long>
		implements IGroupedStudentService {

	private IGroupedStudentDao groupedStudentDao;

	public List<GroupedStudent> listByGroup(Long groupId) {
		return this.groupedStudentDao.listByGroup(groupId);
	}

	public List<GroupedStudent> listByStudent(Long studentId) {
		return this.groupedStudentDao.listByStudent(studentId);
	}

	public void setGroupedStudentDao(IGroupedStudentDao groupedStudentDao) {
		this.groupedStudentDao = groupedStudentDao;
	}

	public GroupedStudent retrieveByGroupNStudent(StudentGroup group,
			Student student) {
		return groupedStudentDao.retrieveByGroupNStudent(group, student);
	}

	public GroupedStudent retrieveGroupedStudent(Long groupId, Long studentId) {
		return this.groupedStudentDao
				.retrieveGroupedStudent(groupId, studentId);
	}

}
