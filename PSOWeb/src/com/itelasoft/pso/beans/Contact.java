package com.itelasoft.pso.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "contacts")
public class Contact extends BaseEntity {
	@DocumentId
	private Long id;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String title;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String firstName;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String lastName;
	private String middleNames;
	private String businessName;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String streetAddress;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String city;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String state;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String postCode;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String country = "Australia";
	private String geoCode;
	private String mobilePhone;
	private String homePhone;
	private String officePhone;
	private String fax;
	private String email;
	private String businessType;
	private Boolean sendNewsletter;
	private Boolean sendInvoice;

	@Transient
	public String getContactNumber() {
		if (mobilePhone != null && !mobilePhone.isEmpty())
			return mobilePhone;
		else if (homePhone != null && !homePhone.isEmpty())
			return homePhone;
		else if (officePhone != null && !officePhone.isEmpty())
			return officePhone;
		return "";

	}

	@Transient
	public String getName() {
		return (getTitle() != null ? getTitle() + " " : "") + getFirstName()
				+ " " + getLastName();
	}

	@Transient
	public String getAddress() {
		return getStreetAddress() + ", " + getCity() + ", " + getState() + ", "
				+ getPostCode();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGeoCode() {
		return geoCode;
	}

	public void setGeoCode(String geoCode) {
		this.geoCode = geoCode;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Boolean getSendNewsletter() {
		return sendNewsletter;
	}

	public void setSendNewsletter(Boolean sendNewsletter) {
		this.sendNewsletter = sendNewsletter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((businessName == null) ? 0 : businessName.hashCode());
		result = prime * result
				+ ((businessType == null) ? 0 : businessType.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((geoCode == null) ? 0 : geoCode.hashCode());
		result = prime * result
				+ ((homePhone == null) ? 0 : homePhone.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((middleNames == null) ? 0 : middleNames.hashCode());
		result = prime * result
				+ ((mobilePhone == null) ? 0 : mobilePhone.hashCode());
		result = prime * result
				+ ((officePhone == null) ? 0 : officePhone.hashCode());
		result = prime * result
				+ ((postCode == null) ? 0 : postCode.hashCode());
		result = prime * result
				+ ((sendNewsletter == null) ? 0 : sendNewsletter.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((streetAddress == null) ? 0 : streetAddress.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (businessName == null) {
			if (other.businessName != null)
				return false;
		} else if (!businessName.equals(other.businessName))
			return false;
		if (businessType == null) {
			if (other.businessType != null)
				return false;
		} else if (!businessType.equals(other.businessType))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (geoCode == null) {
			if (other.geoCode != null)
				return false;
		} else if (!geoCode.equals(other.geoCode))
			return false;
		if (homePhone == null) {
			if (other.homePhone != null)
				return false;
		} else if (!homePhone.equals(other.homePhone))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (middleNames == null) {
			if (other.middleNames != null)
				return false;
		} else if (!middleNames.equals(other.middleNames))
			return false;
		if (mobilePhone == null) {
			if (other.mobilePhone != null)
				return false;
		} else if (!mobilePhone.equals(other.mobilePhone))
			return false;
		if (officePhone == null) {
			if (other.officePhone != null)
				return false;
		} else if (!officePhone.equals(other.officePhone))
			return false;
		if (postCode == null) {
			if (other.postCode != null)
				return false;
		} else if (!postCode.equals(other.postCode))
			return false;
		if (sendNewsletter == null) {
			if (other.sendNewsletter != null)
				return false;
		} else if (!sendNewsletter.equals(other.sendNewsletter))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (streetAddress == null) {
			if (other.streetAddress != null)
				return false;
		} else if (!streetAddress.equals(other.streetAddress))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFax() {
		return fax;
	}

	public void setSendInvoice(Boolean sendInvoice) {
		this.sendInvoice = sendInvoice;
	}

	public Boolean getSendInvoice() {
		return sendInvoice;
	}

}
