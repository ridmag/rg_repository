package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.LeavePolicy;
import com.itelasoft.pso.beans.LeavePolicyDetail;
import com.itelasoft.pso.dao.ILeavePolicyDao;

public class LeavePolicyService extends GenericService<LeavePolicy, Long>
		implements ILeavePolicyService {

	private ILeavePolicyDao leavePolicyDao;

	public List<LeavePolicyDetail> listByPolicy(Long policyId) {
		return this.leavePolicyDao.listByPolicy(policyId);
	}

	public void setLeavePolicyDao(ILeavePolicyDao leavePolicyDao) {
		this.leavePolicyDao = leavePolicyDao;
	}

	public ILeavePolicyDao getLeavePolicyDao() {
		return leavePolicyDao;
	}

	public List<LeavePolicy> listActivePolicies() {
		return this.leavePolicyDao.listActivePolicies();
	}

	public boolean isHavingCurrentStaff(Long policyID) {
		return this.leavePolicyDao.isHavingCurrentStaff(policyID);
	}

}
