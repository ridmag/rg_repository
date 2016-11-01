package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.AuthorisedStaff;
import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.beans.EnumEmploymentType;
import com.itelasoft.pso.beans.InternalOrganization;
import com.itelasoft.pso.beans.NDISStaffTime;
import com.itelasoft.pso.beans.ServiceArea;
import com.itelasoft.pso.beans.StaffMember;
import com.itelasoft.util.SortObjectByName;

@ManagedBean(name = "internalOrganizationModel")
@SessionScoped
public class InternalOrganizationModel extends UIModel {
	private List<InternalOrganization> allOrgs;
	private InternalOrganization org, tmpOrg;
	private List<SelectItem> serviceAreaSelectItems;
	private Long selectInternalOrganizationId;
	private String searchText;
	private List<StaffMember> staffList;
	private boolean visibleStaff;
	private StaffMember staffMember;
	private String searchValue, searchTxt;
	private List<AuthorisedStaff> authorisedstaffList;
	private List<SelectItem> gstCodeList;
	private int selectedTabIndex;
	private AuthorisedStaff authorisedStaff;
	private boolean gstChanged, hoursChanged;
	private String msg, staffName;

	public InternalOrganizationModel() {
		init();
	}

	public void init() {
		allOrgs = serviceLocator.getInternalOrganizationService().findAll();
		authorisedstaffList = new ArrayList<AuthorisedStaff>();
		serviceAreaSelectItems = new ArrayList<SelectItem>();
		List<ServiceArea> areas = serviceLocator.getServiceAreaService().findAll();
		if (areas != null && !areas.isEmpty()) {
			serviceAreaSelectItems.add(new SelectItem(0, "Select One"));
			for (ServiceArea area : areas) {
				serviceAreaSelectItems.add(new SelectItem(area.getId(), area.getName()));
			}
		} else {
			serviceAreaSelectItems.add(new SelectItem(0, "Not Available"));
		}
		org = tmpOrg = null;
		searchText = "";
		visibleStaff = gstChanged = hoursChanged = false;
		searchValue = "name";
		selectedTabIndex = 0;
		staffList = new ArrayList<StaffMember>();
		// staffList=serviceLocator.getStaffMemberService().findAll();

	}

	public void searchOrganizations() {
		try {
			Long id = Long.parseLong(searchText);
			InternalOrganization org = serviceLocator.getInternalOrganizationService().retrieve(id);
			if (org == null) {
				showError("No Internal organization available for this Id.");
			} else {
				allOrgs.clear();
				allOrgs.add(org);
				org = tmpOrg = null;
			}
		} catch (NumberFormatException nFE) {
			List<InternalOrganization> allOrgs = serviceLocator.getInternalOrganizationService().listByName(searchText);
			if (allOrgs == null || allOrgs.isEmpty())
				showError("No results for the given search text.");
			else {
				this.allOrgs = allOrgs;
				tmpOrg = org = null;
			}
		} catch (Exception exception) {
			showExceptionAsError(exception);
		}
	}

	public void newOrganization() {
		if (allOrgs != null && !allOrgs.isEmpty()) {
			showError("Currently the system allows only for one Internal organization");
			return;
		}
		clearInputs();
		if (tmpOrg != null)
			tmpOrg.setUi_selected(false);
		org = new InternalOrganization();
		org.setName("Disability Options Support");
		org.setContact(new Contact());

		// selectedServiceAreaId = new Long(0);
		selectedTabIndex = 0;
		org.setNdisStaffTimes(new ArrayList<NDISStaffTime>());
		for (EnumEmploymentType type : EnumEmploymentType.values()) {
			org.getNdisStaffTimes().add(new NDISStaffTime(type, org));
		}
	}

	public void selectOrganization(ClickActionEvent re) {
		if (tmpOrg != null)
			tmpOrg.setUi_selected(false);
		hoursChanged = gstChanged = false;
		tmpOrg = (InternalOrganization) re.getComponent().getAttributes().get("org");
		tmpOrg.setUi_selected(true);
		org = serviceLocator.getInternalOrganizationService().retrieve(tmpOrg.getId());
		authorisedstaffList = serviceLocator.getAuthorisedStaffService().listAuthorisedStaffToOrg(org.getId());
		for (EnumEmploymentType type : EnumEmploymentType.values()) {
			boolean found = false;
			for (NDISStaffTime time : org.getNdisStaffTimes()) {
				if (type.equals(time.getEmploymentType())) {
					found = true;
				}
			}
			if (!found) {
				org.getNdisStaffTimes().add(new NDISStaffTime(type, org));
			}
		}
		
		if(authorisedstaffList != null || !authorisedstaffList.isEmpty()){
			Collections.sort(authorisedstaffList, new SortObjectByName());
		}
		
		// selectedServiceAreaId = org.getServiceArea().getId();
		selectedTabIndex = 0;

	}

	public void saveOrganization() {
		if (validateFields()) {
			/*
			 * org.setServiceArea(serviceLocator.getServiceAreaService().
			 * retrieve( selectedServiceAreaId));
			 */
			if (org.getId() == null) {
				org = serviceLocator.getInternalOrganizationService().create(org);
				tmpOrg = org;
				tmpOrg.setUi_selected(true);
				allOrgs.add(tmpOrg);
				org = serviceLocator.getInternalOrganizationService().retrieve(tmpOrg.getId());
				showInfo("Internal organization added successfully..");
			} else {
				if (!org.getGst().equals(tmpOrg.getGst())) {
					gstChanged = true;
					// msg="Do you want to change GST code?";
				}
				if (tmpOrg.getNdisStaffTimes() != null || !tmpOrg.getNdisStaffTimes().isEmpty()) {
					for (NDISStaffTime tmpTime : tmpOrg.getNdisStaffTimes()) {
						for (NDISStaffTime orgTime : org.getNdisStaffTimes()) {
							if (tmpTime.getEmploymentType().equals(orgTime.getEmploymentType())
									&& tmpTime.getHours() != orgTime.getHours()) {
								hoursChanged = true;
								break;
								// msg="Do you want to change the hours?";
							}
						}
						if (hoursChanged)
							break;
					}
				}
				if (hoursChanged && !gstChanged)
					msg = "Staff hours changed. Are you sure you want to continue?";
				if (!hoursChanged && gstChanged)
					msg = "GST code changed. Are you sure you want to continue?";
				if (hoursChanged && gstChanged)
					msg = "GST code and Staff hours are changed. Are you sure you want to continue?";
				if (!gstChanged && !hoursChanged)
					updateOrganization();
			}
		}
	}

	public void updateOrganization() {
		org = serviceLocator.getInternalOrganizationService().update(org);
		org.setUi_selected(true);
		allOrgs.set(allOrgs.indexOf(tmpOrg), org);
		tmpOrg = org;
		org = serviceLocator.getInternalOrganizationService().retrieve(tmpOrg.getId());
		showInfo("Internal organization updated successfully..");
		gstChanged = hoursChanged = false;
	}

	public void deleteOrganization() {
		try {
			serviceLocator.getInternalOrganizationService().delete(org.getId());
			allOrgs.remove(tmpOrg);
			org = tmpOrg = null;
			showInfo("Internal organization deleted succesfully..");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
		authorisedstaffList = serviceLocator.getAuthorisedStaffService().findAll();
	}

	private boolean validateFields() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (validateString(org.getName())) {
			if (validateString(org.getContact().getStreetAddress()) && validateString(org.getContact().getCity())
					&& validateString(org.getContact().getPostCode())) {
				if (org.getNDISRegistrationCode() != null && !org.getNDISRegistrationCode().equals("")) {
					List<NDISStaffTime> times = new ArrayList<NDISStaffTime>();
					times = org.getNdisStaffTimes();
					for (NDISStaffTime ndisStaffTime : times) {
						if (ndisStaffTime.getHours() <= 0) {
							showError("Invalid staff default hours in " + ndisStaffTime.getEmploymentType().toString());
							return false;
						}
						if (ndisStaffTime.getHours() > 336) {
							showError("Invalid fortnight work hours for " + ndisStaffTime.getEmploymentType().toString()
									+ ".Max: 336");
							return false;
						}
					}

					if (!Util.validatePhoneNumberAndFax(org.getContact().getOfficePhone(), "Phone")) {
						return false;
					}
					if (!Util.validateEmail(org.getContact().getEmail())) {
						return false;
					}
					return true;

				} else {
					showError("Enter NDISRegistrationCode ");
					componentIds.add("input_Ndisregcode");
					highlightInputs(componentIds);
					return false;
				}

			} else {
				if (org.getContact().getStreetAddress().isEmpty() || org.getContact().getStreetAddress() == null) {
					showError("Address is required");
					componentIds.add("input_OrganizationAddress");
					highlightInputs(componentIds);
				}
				if (org.getContact().getCity().isEmpty() || org.getContact().getCity() == null) {
					showError("City is required");
					componentIds.add("input_OrganizationCity");
					highlightInputs(componentIds);
				}
				if (org.getContact().getPostCode().isEmpty() || org.getContact().getPostCode() == null) {
					showError("Postal code is required");
					componentIds.add("input_OrganizationPostCode");
					highlightInputs(componentIds);
				}
				return false;
			}
		} else {
			showError("Organization name is required");
			componentIds.add("input_OrganizationName");
			highlightInputs(componentIds);
			return false;
		}
	}

	public void closeConfirmation() {
		gstChanged = hoursChanged = false;
	}

	private boolean validateString(String string) {
		if (string != null && !string.isEmpty())
			return true;
		else
			return false;
	}

	public void addNewAStaff() {
		authorisedStaff = new AuthorisedStaff();
		staffMember = null;
		staffName = "";
		staffList = serviceLocator.getStaffMemberService().listActiveStaffMembers();
		showStaffPopup();
	}

	public void selectStaff(ClickActionEvent re) {
		staffMember = (StaffMember) re.getComponent().getAttributes().get("staff");
		authorisedStaff.setStaffMember(staffMember);
		staffName = staffMember.getContact().getName();
		staffMember.setUi_selected(true);
	}

	public void searchStaff() {
		staffList.clear();
		if (searchValue.equals("id")) {
			StaffMember staff = serviceLocator.getStaffMemberService().searchByStaffId(searchTxt);
			if (staff == null)
				showError("No result available for this Id.");
			else {
				staffList.add(staff);
			}
		} else {
			staffList = serviceLocator.getStaffMemberService().listAvailableByName(searchTxt);
			if (staffList == null || staffList.isEmpty())
				showError("No results for the given search text.");
		}
	}

	public boolean saveAuthorisingStaff() {
		if (validate()) {
			if (authorisedStaff.getId() == null) {
				authorisedStaff.setInternalOrganization(org);
				authorisedStaff.setStaffMember(staffMember);
				serviceLocator.getAuthorisedStaffService().create(authorisedStaff);
				showInfo("saved successfully..");
			} else {
				serviceLocator.getAuthorisedStaffService().update(authorisedStaff);
				showInfo("Updated successfully..");
			}
			authorisedstaffList = serviceLocator.getAuthorisedStaffService().listAuthorisedStaffToOrg(org.getId());
			if(authorisedstaffList != null || !authorisedstaffList.isEmpty()){
				Collections.sort(authorisedstaffList, new SortObjectByName());
			}
			return true;
		} else {
			return false;
		}

	}

	public void saveAndExit() {
		if (saveAuthorisingStaff()) {
			showStaffPopup();
		}
	}

	public void showStaffPopup() {
		visibleStaff = !visibleStaff;
	}

	public boolean validate() {
		if (staffMember == null) {
			showError("Select a Staff Member");
			return false;
		}
		if (authorisedStaff.getAuthorisingCode() == null || authorisedStaff.getAuthorisingCode().isEmpty()) {
			showError("Athorising Code is required");
			return false;
		}
		return true;
	}

	public void editStaff(ActionEvent ae) {
		authorisedStaff = (AuthorisedStaff) ae.getComponent().getAttributes().get("staff");
		staffMember = authorisedStaff.getStaffMember();
		staffName = staffMember.getContact().getName();
		showStaffPopup();
	}

	public void removeStaff(ActionEvent ae) {
		authorisedStaff = (AuthorisedStaff) ae.getComponent().getAttributes().get("staff");
		serviceLocator.getAuthorisedStaffService().delete(authorisedStaff.getId());
		authorisedstaffList = serviceLocator.getAuthorisedStaffService().findAll();
	}

	/*
	 * getters and setters
	 */

	public List<InternalOrganization> getAllOrgs() {
		return allOrgs;
	}

	public List<SelectItem> getServiceAreaSelectItems() {
		return serviceAreaSelectItems;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchText() {
		return searchText;
	}

	public InternalOrganization getOrg() {
		return org;
	}

	public void setOrg(InternalOrganization org) {
		this.org = org;
	}
	/*
	 * public Long getSelectedServiceAreaId() { return selectedServiceAreaId; }
	 * 
	 * public void setSelectedServiceAreaId(Long selectedServiceAreaId) {
	 * this.selectedServiceAreaId = selectedServiceAreaId; }
	 */

	public List<StaffMember> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<StaffMember> staffList) {
		this.staffList = staffList;
	}

	public boolean isVisibleStaff() {
		return visibleStaff;
	}

	public void setVisibleStaff(boolean visibleStaff) {
		this.visibleStaff = visibleStaff;
	}

	public StaffMember getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSearchTxt() {
		return searchTxt;
	}

	public void setSearchTxt(String searchTxt) {
		this.searchTxt = searchTxt;
	}

	public List<AuthorisedStaff> getAuthorisedstaffList() {
		return authorisedstaffList;
	}

	public void setAuthorisedstaffList(List<AuthorisedStaff> authorisedstaffList) {
		this.authorisedstaffList = authorisedstaffList;
	}

	public Long getSelectInternalOrganizationId() {
		return selectInternalOrganizationId;
	}

	public void setSelectInternalOrganizationId(Long selectInternalOrganizationId) {
		this.selectInternalOrganizationId = selectInternalOrganizationId;
	}

	public int getSelectedTabIndex() {
		return selectedTabIndex;
	}

	public void setSelectedTabIndex(int selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}

	public AuthorisedStaff getAuthorisedStaff() {
		return authorisedStaff;
	}

	public void setAuthorisedStaff(AuthorisedStaff authorisedStaff) {
		this.authorisedStaff = authorisedStaff;
	}

	public List<SelectItem> getGstCodeList() {
		return gstCodeList;
	}

	public void setGstCodeList(List<SelectItem> gstCodeList) {
		this.gstCodeList = gstCodeList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isGstChanged() {
		return gstChanged;
	}

	public boolean isHoursChanged() {
		return hoursChanged;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

}
