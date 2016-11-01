package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.beans.ExternalOrganization;
import com.itelasoft.pso.beans.ServiceArea;

@ManagedBean(name = "externalOrganizationManagerModel")
@SessionScoped
public class ExternalOrganizationManagerModel extends UIModel {
	private List<ExternalOrganization> allOrgs;
	private ExternalOrganization org, tmpOrg;
	private List<SelectItem> serviceAreaSelectItems;
	private Long selectedServiceAreaId;
	private String searchText;

	public void init() {
		allOrgs = serviceLocator.getExternalOrganizationService().findAll();
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
	}

	public void searchOrganizations() {
		try {
			Long id = Long.parseLong(searchText);
			ExternalOrganization org = serviceLocator.getExternalOrganizationService().retrieve(id);
			if (org == null) {
				showError("No external organization available for this Id.");
			} else {
				allOrgs.clear();
				allOrgs.add(org);
				org = tmpOrg = null;
			}
		} catch (NumberFormatException nFE) {
			List<ExternalOrganization> allOrgs = serviceLocator.getExternalOrganizationService().listByName(searchText);
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
		if (tmpOrg != null)
			tmpOrg.setUi_selected(false);
		org = new ExternalOrganization();
		org.setContact(new Contact());
		org.setContactPerson(new Contact());
		selectedServiceAreaId = new Long(0);
	}

	public void selectOrganization(ClickActionEvent re) {
		if (tmpOrg != null)
			tmpOrg.setUi_selected(false);
		tmpOrg = (ExternalOrganization) re.getComponent().getAttributes().get("org");
		tmpOrg.setUi_selected(true);
		org = serviceLocator.getExternalOrganizationService().retrieve(tmpOrg.getId());
		selectedServiceAreaId = org.getServiceArea().getId();
	}

	public void saveOrganization() {
		if (validateFields()) {
			if (validatePerson()) {
				org.setServiceArea(serviceLocator.getServiceAreaService().retrieve(selectedServiceAreaId));
				if (org.getId() == null) {
					org = serviceLocator.getExternalOrganizationService().create(org);
					tmpOrg = org;
					tmpOrg.setUi_selected(true);
					allOrgs.add(tmpOrg);
					org = serviceLocator.getExternalOrganizationService().retrieve(tmpOrg.getId());
					showInfo("External organization added successfully..");
				} else {
					org = serviceLocator.getExternalOrganizationService().update(org);
					org.setUi_selected(true);
					allOrgs.set(allOrgs.indexOf(tmpOrg), org);
					tmpOrg = org;
					org = serviceLocator.getExternalOrganizationService().retrieve(tmpOrg.getId());
					showInfo("External organization updated successfully..");
				}
			}
		}
	}

	private boolean validatePerson() {
		String firstName = org.getContactPerson().getFirstName();
		String address = org.getContactPerson().getStreetAddress();
		String city = org.getContactPerson().getCity();
		String postCode = org.getContactPerson().getPostCode();
		if (validateString(firstName)) {
			if (validateString(address) && validateString(city) && validateString(postCode)) {
				return true;
			} else {
				showError("Contact person's address fields can not be empty..");
				return false;
			}
		} else {
			showError("Contact person's name can not be empty..");
			return false;
		}
	}

	public void deleteOrganization() {
		try {
			serviceLocator.getExternalOrganizationService().delete(org.getId());
			allOrgs.remove(tmpOrg);
			org = tmpOrg = null;
			showInfo("External organization deleted succesfully..");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	private boolean validateFields() {
		String name = org.getName();
		String option = org.getPaymentOption();
		String address = org.getContact().getStreetAddress();
		String city = org.getContact().getCity();
		String postCode = org.getContact().getPostCode();
		if (validateString(name)) {
			if (selectedServiceAreaId != null && selectedServiceAreaId != 0) {
				if (validateString(option)) {
					if (validateString(address) && validateString(city) && validateString(postCode)) {

						if (!Util.validatePhoneNumberAndFax(org.getContact().getOfficePhone(), "Phone")) {
							return false;
						}
						if (!Util.validateEmail(org.getContact().getEmail())) {
							return false;
						}

						if (!Util.validatePhoneNumberAndFax(org.getContactPerson().getMobilePhone(), "Contact person Phone")) {
							return false;
						}
						if (!Util.validateEmail(org.getContactPerson().getEmail())) {
							return false;
						}
						if (!Util.validatePhoneNumberAndFax(org.getContactPerson().getHomePhone(), "Fax")) {
							return false;
						}
						return true;
					} else {
						showError("Address fields can not be empty..");
						return false;
					}
				} else {
					showError("Please provide a payment Option..");
					return false;
				}
			} else {
				showError("Please select a service area..");
				return false;
			}
		} else {
			showError("Organization name can not be empty..");
			return false;
		}

	}

	private boolean validateString(String string) {
		if (string != null && !string.isEmpty())
			return true;
		else
			return false;
	}

	/*
	 * getters and setters
	 */

	public List<ExternalOrganization> getAllOrgs() {
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

	public ExternalOrganization getOrg() {
		return org;
	}

	public void setOrg(ExternalOrganization org) {
		this.org = org;
	}

	public Long getSelectedServiceAreaId() {
		return selectedServiceAreaId;
	}

	public void setSelectedServiceAreaId(Long selectedServiceAreaId) {
		this.selectedServiceAreaId = selectedServiceAreaId;
	}

}
