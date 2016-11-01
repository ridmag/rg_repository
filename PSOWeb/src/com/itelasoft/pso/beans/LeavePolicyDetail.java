package com.itelasoft.pso.beans;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LeavePolicyDetails")
public class LeavePolicyDetail {

	private Long id;
	private EnumLeaveType leaveType;
	private int daysEntitled;
	private LeavePolicy policy;
	private String remarks;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDaysEntitled() {
		return daysEntitled;
	}

	public void setDaysEntitled(int daysEntitled) {
		this.daysEntitled = daysEntitled;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setPolicy(LeavePolicy policy) {
		this.policy = policy;
	}

	@ManyToOne
	@JoinColumn(name = "leavePolicyId", insertable = true, updatable = true, unique = true)
	public LeavePolicy getPolicy() {
		return policy;
	}

	public void setLeaveType(EnumLeaveType leaveType) {
		this.leaveType = leaveType;
	}

	@Enumerated(EnumType.STRING)
	public EnumLeaveType getLeaveType() {
		return leaveType;
	}

}
