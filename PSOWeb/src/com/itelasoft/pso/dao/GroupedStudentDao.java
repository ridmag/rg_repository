package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentGroup;

public class GroupedStudentDao extends GenericDao<GroupedStudent, Long>
		implements IGroupedStudentDao {

	public List<GroupedStudent> listByGroup(Long groupId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(StudentGroup.class)
				.add(Restrictions.eq("id", groupId))
				.setFetchMode("students", FetchMode.JOIN);
		StudentGroup group = (StudentGroup) criteria.uniqueResult();
		return group.getStudents();
	}

	public List<GroupedStudent> listByStudent(Long studentId) {
		Session session = getCurrentSession();
		Criteria c = session.createCriteria(Student.class)
				.add(Restrictions.eq("id", studentId))
				.setFetchMode("groups", FetchMode.JOIN);
		Student student = (Student) c.uniqueResult();
		return student.getGroups();
	}

	public GroupedStudent retrieveByGroupNStudent(StudentGroup group,
			Student student) {
		Session session = getCurrentSession();
		Criteria c = session.createCriteria(GroupedStudent.class)
				.add(Restrictions.eq("group", group))
				.add(Restrictions.eq("student", student));
		GroupedStudent std = (GroupedStudent) c.uniqueResult();
		return std;
	}

	public GroupedStudent retrieveGroupedStudent(Long groupId, Long studentId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from GroupedStudent as gro where gro.group.id = ? and gro.student.id = ?");
		query.setParameter(0, groupId);
		query.setParameter(1, studentId);
		return (GroupedStudent) query.uniqueResult();
	}

}
