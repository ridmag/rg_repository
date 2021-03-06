package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.StudentGroup;

public interface IStudentGroupService extends
		IGenericService<StudentGroup, Long> {

	public void updateGroups(StudentGroup mainGroup, StudentGroup returnGroup)
			throws Exception;

	public List<StudentGroup> listByProgram(Long programId);

	public List<StudentGroup> listMainTranportGroups(Long programId);

	public boolean validateGroupName(StudentGroup studentGroup);

	public List<StudentGroup> listAvailableByStudentNProgram(Long studentId,
			String programName, String programTypeName);

	public List<StudentGroup> listAvailableByProgram(String programName,
			String programTypeName);

	public List<StudentGroup> retrieveAvailableByStudentNProgramId(
			Long studentId, Long programId, String programType);

	public StudentGroup retrieveWithStaffMembers(Long groupId);

	public StudentGroup retrieveWithData(Long groupId, boolean withImage);

	public StudentGroup retrieveWithGroupData(Long groupId);

	public List<StudentGroup> listByCriteria(Long studentGroupId,
			Date startDate, Date endDate);

	/**
	 * Returns with staffMembers and students if withData is true.
	 * 
	 * @param mainGroupdId
	 * @param withData
	 * @return {@link StudentGroup}
	 */
	public StudentGroup getReturnGroup(Long mainGroupdId, boolean withData);

	public List<StudentGroup> listAvailableByGroup(String groupName);

	public StudentGroup retrieveEager(Long studentGroupId);
}
