package com.itelasoft.pso.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "internalOrganizations")
public class InternalOrganization extends BaseEntity {
	private Long id;
	private Contact contact;
	private String name;
	private ServiceArea serviceArea;
	private boolean preferedSupplier;
	private String NDISRegistrationCode;
	private List<AuthorisedStaff> authorisedStaff;
	private String gst;
	private Date fortnightStartDate;
	private List<NDISStaffTime> ndisStaffTimes;

	/*
	 * 
	 * getters and setters
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contactId")
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPreferedSupplier() {
		return preferedSupplier;
	}

	public void setPreferedSupplier(boolean preferedSupplier) {
		this.preferedSupplier = preferedSupplier;
	}

	public void setServiceArea(ServiceArea serviceArea) {
		this.serviceArea = serviceArea;
	}

	@ManyToOne
	@JoinColumn(name = "serviceareaid")
	public ServiceArea getServiceArea() {
		return serviceArea;
	}

	public String getNDISRegistrationCode() {
		return NDISRegistrationCode;
	}

	public void setNDISRegistrationCode(String nDISRegistrationCode) {
		NDISRegistrationCode = nDISRegistrationCode;
	}

	@OneToMany(mappedBy = "internalOrganization", cascade = CascadeType.ALL)
	public List<AuthorisedStaff> getAuthorisedStaff() {
		return authorisedStaff;
	}

	public void setAuthorisedStaff(List<AuthorisedStaff> authorisedStaff) {
		this.authorisedStaff = authorisedStaff;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public Date getFortnightStartDate() {
		return fortnightStartDate;
	}

	public void setFortnightStartDate(Date fortnightStartDate) {
		this.fortnightStartDate = fortnightStartDate;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "organization")
	@LazyCollection(value = LazyCollectionOption.FALSE)
	public List<NDISStaffTime> getNdisStaffTimes() {
		return ndisStaffTimes;
	}

	public void setNdisStaffTimes(List<NDISStaffTime> ndisStaffTimes) {
		this.ndisStaffTimes = ndisStaffTimes;
	}

}