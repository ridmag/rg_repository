package com.itelasoft.pso.ws.value;

import java.util.Date;

import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.pso.beans.StaffType;


public class Staff {
	private StaffMember staff;
	/*public StaffMember getStaff() {
		return staff;
	}

	public void setStaff(StaffMember staff) {
		this.staff = staff;
	}*/

	public void setStatus(String status) {
		staff.setStatus(status);
	}

	public String getStatus() {
		return staff.getStatus();
	}

	private Contact contact;

	public String getContactNumber() {
		return contact.getContactNumber();
	}

	public String getName() {
		return contact.getName();
	}

	public String getAddress() {
		return contact.getAddress();
	}

	public void setId(Integer id) {
		staff.setId(id.longValue());
	}

	public String getTitle() {
		return contact.getTitle();
	}

	public void setTitle(String title) {
		contact.setTitle(title);
	}

	public String getFirstName() {
		return contact.getFirstName();
	}

	public void setFirstName(String firstName) {
		contact.setFirstName(firstName);
	}

	public String getLastName() {
		return contact.getLastName();
	}

	public void setLastName(String lastName) {
		contact.setLastName(lastName);
	}

	public String getMiddleNames() {
		return contact.getMiddleNames();
	}

	public void setMiddleNames(String middleNames) {
		contact.setMiddleNames(middleNames);
	}

	public String getStreetAddress() {
		return contact.getStreetAddress();
	}

	public void setStreetAddress(String streetAddress) {
		contact.setStreetAddress(streetAddress);
	}

	public String getCity() {
		return contact.getCity();
	}

	public void setCity(String city) {
		contact.setCity(city);
	}

	public String getState() {
		return contact.getState();
	}

	public void setState(String state) {
		contact.setState(state);
	}

	public String getPostCode() {
		return contact.getPostCode();
	}

	public void setPostCode(String postCode) {
		contact.setPostCode(postCode);
	}

	public String getCountry() {
		return contact.getCountry();
	}

	public void setCountry(String country) {
		contact.setCountry(country);
	}

	public String getGeoCode() {
		return contact.getGeoCode();
	}

	public void setGeoCode(String geoCode) {
		contact.setGeoCode(geoCode);
	}

	public String getMobilePhone() {
		return contact.getMobilePhone();
	}

	public void setMobilePhone(String mobilePhone) {
		contact.setMobilePhone(mobilePhone);
	}

	public String getHomePhone() {
		return contact.getHomePhone();
	}

	public void setHomePhone(String homePhone) {
		contact.setHomePhone(homePhone);
	}

	public String getOfficePhone() {
		return contact.getOfficePhone();
	}

	public void setOfficePhone(String officePhone) {
		contact.setOfficePhone(officePhone);
	}

	public String getEmail() {
		return contact.getEmail();
	}

	public void setEmail(String email) {
		contact.setEmail(email);
	}

	public void setFax(String fax) {
		contact.setFax(fax);
	}

	public String getFax() {
		return contact.getFax();
	}

	/*
	 * public boolean isUi_selected() { return staff.isUi_selected(); }
	 * 
	 * public void setUi_selected(boolean uiSelected) {
	 * staff.setUi_selected(uiSelected); }
	 * 
	 * public void setLastUpdatedBy(String lastUpdatedBy) {
	 * staff.setLastUpdatedBy(lastUpdatedBy); }
	 * 
	 * public String getLastUpdatedBy() { return staff.getLastUpdatedBy(); }
	 * 
	 * public void setLastUpdatedOn(Date lastUpdatedOn) {
	 * staff.setLastUpdatedOn(lastUpdatedOn); }
	 * 
	 * public Date getLastUpdatedOn() { return staff.getLastUpdatedOn(); }
	 * 
	 * public void setCreateOn(Date createOn) { staff.setCreateOn(createOn); }
	 * 
	 * public Date getCreateOn() { return staff.getCreateOn(); }
	 */
	public int hashCode() {
		return staff.hashCode();
	}

	public Integer getId() {
		return staff.getId().intValue();
	}


	public Date getDob() {
		return staff.getDob();
	}

	public void setDob(Date dob) {
		staff.setDob(dob);
	}

	public String getGender() {
		return staff.getGender();
	}

	public void setGender(String gender) {
		staff.setGender(gender);
	}

	/*
	 * public Contact getContact() { return staff.getContact(); }
	 * 
	 * public void setContact(Contact contact) { staff.setContact(contact); }
	 */
	/*
	 * public FileData getPhoto() { return staff.getPhoto(); }
	 * 
	 * public void setPhoto(FileData photo) { staff.setPhoto(photo); }
	 */

	/*
	 * public SpecialNeed[] getSpecialNeeds() { SpecialNeed[] needs = new
	 * SpecialNeed
	 * [staff.getSpecialNeeds()!=null?staff.getSpecialNeeds().size():0];
	 * 
	 * for(int i=0;i < staff.getSpecialNeeds().size();i++){ needs[i]=
	 * staff.getSpecialNeeds().get(i); } return needs; }
	 * 
	 * public void setSpecialNeeds(SpecialNeed[] specialNeeds) {
	 * staff.setSpecialNeeds(Arrays.asList(specialNeeds)); }
	 */
	/*
	 * public void setFundingSrcs(List<staffFundingSource> fundingSrcs) {
	 * staff.setFundingSrcs(fundingSrcs); }
	 * 
	 * public void testMe(String str) { staff.testMe(str); }
	 * 
	 * public List<staffFundingSource> getFundingSrcs() { return
	 * staff.getFundingSrcs(); }
	 */
	/*
	 * public void setGuardians(List<Contact> guardians) {
	 * staff.setGuardians(guardians); }
	 * 
	 * public List<Contact> getGuardians() { return staff.getGuardians(); }
	 */
	/*
	 * public void setTransport(boolean transport) {
	 * staff.setTransport(transport); }
	 * 
	 * 
	 * 
	 * public boolean isTransport() { return staff.isTransport(); }
	 */
	/*
	 * public void setGroups(List<Groupedstaff> groups) {
	 * staff.setGroups(groups); } public List<Groupedstaff> getGroups() {
	 * return staff.getGroups(); }
	 */
	

	public void setMailingAddress(Contact mailingAddress) {
		staff.setMailingAddress(mailingAddress);
	}

	public Contact getMailingAddress() {
		return staff.getMailingAddress();
	}

	/*
	 * public void setstaffConsents(List<staffConsent> staffConsents) {
	 * staff.setstaffConsents(staffConsents); }
	 * 
	 * public List<staffConsent> getstaffConsents() { return
	 * staff.getstaffConsents(); }
	 */

	/*
	 * public void setMailAddressDefault(Boolean isMailAddressDefault) {
	 * staff.setMailAddressDefault(isMailAddressDefault); }
	 */

	

	public String toString() {
		return staff.toString();
	}

	public Staff() {
		// TODO Auto-generated constructor stub
	}

	public Staff(StaffMember sta) {
		staff = sta;
		contact = sta.getContact();
	}

}
