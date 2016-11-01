package com.itelasoft.pso.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.itelasoft.pso.beans.Guardian;

public interface IGuardianService {

	public List<Guardian> listByStudent(Long studentId);

	public Guardian retrive(Long studentId, Long contactId);

	/**
	 * This will create student or contact first if either of them doesn't exist
	 * or otherwise will update them and create a new guardian. If the
	 * combination of student and contact is already exists, this will throw
	 * DataIntegrityViolationException
	 * 
	 * @param Guardian
	 * @return Guardian
	 */
	public Guardian create(Guardian guardian)
			throws DataIntegrityViolationException;

	/**
	 * This will update the guardian along with the contact, but not student.
	 * 
	 * @param Guardian
	 * @return Guardian
	 */
	public Guardian update(Guardian guardian);

	public int delete(Guardian guardian);

}
