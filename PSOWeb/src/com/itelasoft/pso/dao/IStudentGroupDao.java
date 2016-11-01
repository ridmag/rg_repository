package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.StudentGroup;

public interface IStudentGroupDao extends IGenericDao<StudentGroup, Long> {

	public List<StudentGroup> listByProgram(Long programId);

	public List<StudentGroup> listMainTranportGroups(Long programId);

	public boolean validateGroupName(StudentGroup studentGroup);

	public List<StudentGroup> listAvailableByStudentNProgram(Long studentId,
			String programName, String programTypeName);
	public List<StudentGroup> listAvailableByProgram(String programName, String programTypeName);
	public List<StudentGroup> retrieveAvailableByStudentNProgramId(
			Long studentId, Long programId, String programType);

	public StudentGroup retrieveWithStaffMembers(Long groupdId);

	/**
	 * Returns with staffMembers if withStaff is set to true.
	 * 
	 * @param mainGroupdId
	 * @param withStaff
	 * @return {@link StudentGroup}
	 */
	public StudentGroup getReturnGroup(Long mainGroupdId, boolean withStaff);
	
	public StudentGroup retrieveWithStaffMembersWithSkills(Long groupdId);
	
	public List<StudentGroup> listAvailableByGroup(String groupName);
	
	public List<StudentGroup> listByCriteria(Long studentGroupId,Date startDate,Date endDate);
	
	public StudentGroup retrieveWithGroupData(Long groupdId);
}
