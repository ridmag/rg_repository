package com.itelasoft.pso.beans;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LeaveCategory {

	private Long id;
	private EnumLeaveType leaveType;
	private double earnedHours;
	private double usedHours;
	private StaffMember staffMember;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getEarnedHours() {
		return earnedHours;
	}

	public void setEarnedHours(double earnedHours) {
		this.earnedHours = earnedHours;
	}

	public void setUsedHours(double usedHours) {
		this.usedHours = usedHours;
	}

	public double getUsedHours() {
		return usedHours;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}

	@ManyToOne
	@JoinColumn(name = "staffId")
	public StaffMember getStaffMember() {
		return staffMember;
	}

	public void setLeaveType(EnumLeaveType leaveType) {
		this.leaveType = leaveType;
	}

	@Enumerated(EnumType.STRING)
	public EnumLeaveType getLeaveType() {
		return leaveType;
	}

}
