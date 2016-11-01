package com.itelasoft.pso.beans;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class GroupedStaff extends BaseEntity {

	private Long id;
	private StudentGroup group;
	private StaffMember staffMember;
	private List<GroupedStaffWeekday> assignedDays = new ArrayList<GroupedStaffWeekday>();
	private String status;
	private String remarks;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "staffId")
	public StaffMember getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setGroup(StudentGroup group) {
		this.group = group;
	}

	@ManyToOne
	@JoinColumn(name = "groupId")
	public StudentGroup getGroup() {
		return group;
	}

	@OneToMany(mappedBy = "groupedstaff")
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@Cascade(value = { org.hibernate.annotations.CascadeType.ALL,
			org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE})
	public List<GroupedStaffWeekday> getAssignedDays() {
		return assignedDays;
	}
	public void setAssignedDays(List<GroupedStaffWeekday> assignedDays) {
		this.assignedDays = assignedDays;
	}
}
