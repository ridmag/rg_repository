package com.itelasoft.pso.beans;

import java.util.Date;

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
@Table(name = "Leaves")
public class Leave extends BaseEntity {

	private Long id;
	private String reason;
	private Date startDate;
	private Date endDate;
	private EnumLeaveType leaveType;
	private int days;
	private double nopayHours;
	private StaffMember staffMember;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@ManyToOne
	@JoinColumn(name = "staffId")
	public StaffMember getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public void setLeaveType(EnumLeaveType leaveType) {
		this.leaveType = leaveType;
	}

	@Enumerated(EnumType.STRING)
	public EnumLeaveType getLeaveType() {
		return leaveType;
	}

	public void setNopayHours(double nopayHours) {
		this.nopayHours = nopayHours;
	}

	public double getNopayHours() {
		return nopayHours;
	}

}
