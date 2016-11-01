package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.LeavePolicy;
import com.itelasoft.pso.beans.LeavePolicyDetail;

public interface ILeavePolicyDao extends IGenericDao<LeavePolicy, Long> {

	public List<LeavePolicyDetail> listByPolicy(Long policyId);

	public List<LeavePolicy> listActivePolicies();

	public boolean isHavingCurrentStaff(Long policyID);

}
