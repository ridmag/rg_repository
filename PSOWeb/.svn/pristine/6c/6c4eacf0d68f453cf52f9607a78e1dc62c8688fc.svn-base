package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.AuthorisedStaff;
import com.itelasoft.pso.dao.IAuthorisedStaffDao;

public class AuthorisedStaffService extends GenericService<AuthorisedStaff, Long> implements IAuthorisedStaffService {
    
	private IAuthorisedStaffDao authorisedStaffDao;

	public IAuthorisedStaffDao getAuthorisedStaffDao() {
		return authorisedStaffDao;
	}

	public void setAuthorisedStaffDao(IAuthorisedStaffDao authorisedStaffDao) {
		this.authorisedStaffDao = authorisedStaffDao;
	}

	@Override
	public List<AuthorisedStaff> listAuthorisedStaffToOrg(Long orgId) {
		return authorisedStaffDao.listAuthorisedStaffToOrg(orgId);
	}

	
}
