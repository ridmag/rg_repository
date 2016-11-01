package com.itelasoft.pso.dao;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.itelasoft.pso.beans.Guardian;

public interface IGuardianDao {

	public List<Guardian> listByStudent(Long studentId);

	public Guardian retrive(Long studentId, Long contactId);
	
	public Guardian save(Guardian guardian)
			throws DataIntegrityViolationException;

	public Guardian update(Guardian guardian);

	public int delete(Guardian guardian);

}
