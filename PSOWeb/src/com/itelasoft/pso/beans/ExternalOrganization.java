package com.itelasoft.pso.beans;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "externalOrganizations")
public class ExternalOrganization extends BaseEntity {
	private Long id;
	private Contact contact;
	private Contact contactPerson;
	private String name;
	private String paymentOption;
	private ServiceArea serviceArea;
	private boolean preferedSupplier;

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

	public String getPaymentOption() {
		return paymentOption;
	}

	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
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

	public void setContactPerson(Contact contactPerson) {
		this.contactPerson = contactPerson;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contactPersonId")
	public Contact getContactPerson() {
		return contactPerson;
	}

}
