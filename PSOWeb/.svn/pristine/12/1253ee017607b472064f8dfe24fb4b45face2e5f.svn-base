package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentGroup;

public interface IGroupedStudentDao extends IGenericDao<GroupedStudent, Long> {

	public List<GroupedStudent> listByGroup(Long groupId);

	public List<GroupedStudent> listByStudent(Long studentId);

	public GroupedStudent retrieveByGroupNStudent(StudentGroup group,
			Student student);

	public GroupedStudent retrieveGroupedStudent(Long groupId, Long studentId);

}
