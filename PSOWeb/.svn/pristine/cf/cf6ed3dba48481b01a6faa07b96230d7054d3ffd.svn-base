package com.itelasoft.pso.beans;

import javax.persistence.CascadeType;
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
@Table(name = "ndisstafftimes")
public class NDISStaffTime {

	private Long id;
	private EnumEmploymentType employmentType;
	private double hours;
	private InternalOrganization organization;

	public NDISStaffTime() {

	}

	public NDISStaffTime(EnumEmploymentType type,
			InternalOrganization organization) {
		this.setEmploymentType(type);
		this.setOrganization(organization);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	public EnumEmploymentType getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(EnumEmploymentType employmentType) {
		this.employmentType = employmentType;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "organizationid")
	public InternalOrganization getOrganization() {
		return organization;
	}

	public void setOrganization(InternalOrganization organization) {
		this.organization = organization;
	}
}
