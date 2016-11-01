package com.itelasoft.pso.beans;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "authorisedstaff")
public class AuthorisedStaff extends BaseEntity {
	private Long id;
	private StaffMember staffMember;
	private String authorisingCode;
	private InternalOrganization internalOrganization;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "Internalorgid")
	public InternalOrganization getInternalOrganization() {
		return internalOrganization;
	}

	public void setInternalOrganization(InternalOrganization internalOrganization) {
		this.internalOrganization = internalOrganization;
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "staffId")
	public StaffMember getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}

	public String getAuthorisingCode() {
		return authorisingCode;
	}

	public void setAuthorisingCode(String authorisingCode) {
		this.authorisingCode = authorisingCode;
	}
}
