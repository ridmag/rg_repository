package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.LeavePolicy;
import com.itelasoft.pso.beans.LeavePolicyDetail;

@ManagedBean(name = "leavePolicyManager")
@SessionScoped
public class LeavePolicyManager extends UIModel {

	private List<LeavePolicy> policies;
	private LeavePolicy policy, tmpPolicy;
	private String selectedLeaveType;
	private boolean visibleConfirmation;

	public void init() {
		policies = serviceLocator.getLeavePolicyService().findAll();
		visibleConfirmation = false;
		policy = tmpPolicy = null;
	}

	public void selectPolicy(ClickActionEvent re) {
		if (tmpPolicy != null)
			tmpPolicy.setUi_selected(false);
		tmpPolicy = (LeavePolicy) re.getComponent().getAttributes()
				.get("policy");
		tmpPolicy.setUi_selected(true);
		policy = serviceLocator.getLeavePolicyService().retrieve(
				tmpPolicy.getId());
	}

	public void addNewPolicy() {
		if (tmpPolicy != null)
			tmpPolicy.setUi_selected(false);
		policy = new LeavePolicy();
		policy.setDetails(new ArrayList<LeavePolicyDetail>());
	}

	public void savePolicy(ActionEvent event) {
		if (validatePolicyFields()) {
			if (policy.getStatus().equals("Inactive")
					&& serviceLocator.getLeavePolicyService()
							.isHavingCurrentStaff(policy.getId())) {
				visibleConfirmation = true;
			} else {
				savePolicy();
			}
		}
	}

	public void savePolicy() {
		if (policy.getId() == null) {
			policy = serviceLocator.getLeavePolicyService().create(policy);
			tmpPolicy = policy;
			tmpPolicy.setUi_selected(true);
			policies.add(tmpPolicy);
			policy = serviceLocator.getLeavePolicyService().retrieve(
					tmpPolicy.getId());
			showInfo("Leave policy added successfully..");
		} else {
			policy = serviceLocator.getLeavePolicyService().update(policy);
			policy.setUi_selected(true);
			policies.set(policies.indexOf(tmpPolicy), policy);
			tmpPolicy = policy;
			policy = serviceLocator.getLeavePolicyService().retrieve(
					tmpPolicy.getId());
			showInfo("Leave policy updated successfully..");
		}
	}

	public void deletePolicy() {
		try {
			serviceLocator.getLeavePolicyService().delete(policy.getId());
			policies.remove(tmpPolicy);
			policy = tmpPolicy = null;
			showInfo("Leave policy deleted succesfully.");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	private boolean validatePolicyFields() {
		if (policy.getName() != null && !policy.getName().isEmpty()) {
			HashMap<String, String> leaveTypes = new HashMap<String, String>();
			for (LeavePolicyDetail detail : policy.getDetails()) {
				if (detail.getLeaveType() == null) {
					showError("Please select a leave type..");
					return false;
				}
				if (leaveTypes.containsKey(detail.getLeaveType().getId())) {
					showError("Same leave type can not be repeated..");
					return false;
				} else {
					leaveTypes.put(detail.getLeaveType().getId(), detail
							.getLeaveType().getId());
				}
			}
			return true;
		} else {
			showError("Policy name can not be empty..");
			return false;
		}
	}

	public void confirmationAction(ActionEvent ae) {
		visibleConfirmation = false;
		String action = (String) ae.getComponent().getAttributes()
				.get("confirmation");
		if (action.equals("Yes")) {
			savePolicy();
		}
	}

	public void addDetail() {
		LeavePolicyDetail detail = new LeavePolicyDetail();
		detail.setPolicy(policy);
		policy.getDetails().add(0, detail);
	}

	public void deleteDetail(ActionEvent ae) {
		LeavePolicyDetail detail = (LeavePolicyDetail) ae.getComponent()
				.getAttributes().get("detail");
		if (detail != null) {
			policy.getDetails().remove(detail);
		}
	}

	/*
	 * getters and setters
	 */

	public String getSelectedLeaveType() {
		return selectedLeaveType;
	}

	public void setSelectedLeaveType(String selectedLeaveType) {
		this.selectedLeaveType = selectedLeaveType;
	}

	public void setPolicies(List<LeavePolicy> policies) {
		this.policies = policies;
	}

	public List<LeavePolicy> getPolicies() {
		return policies;
	}

	public void setPolicy(LeavePolicy policy) {
		this.policy = policy;
	}

	public LeavePolicy getPolicy() {
		return policy;
	}

	public void setVisibleConfirmation(boolean visibleConfirmation) {
		this.visibleConfirmation = visibleConfirmation;
	}

	public boolean isVisibleConfirmation() {
		return visibleConfirmation;
	}

}
