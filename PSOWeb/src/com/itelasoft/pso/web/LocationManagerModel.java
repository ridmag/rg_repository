package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.beans.Location;

@ManagedBean(name = "locationManagerModel")
@SessionScoped
public class LocationManagerModel extends UIModel {

	private List<Location> locations;
	private Location location, tmpLocation;
	private String searchValue, searchText;

	public LocationManagerModel() {
		init();
	}

	public void init() {
		searchText = "";
		searchValue = "name";
		location = null;
		locations = serviceLocator.getLocationService().listByName(searchText);
		if (locations == null)
			locations = new ArrayList<Location>();
	}

	public void searchLocations() {
		if (searchValue.equals("code")) {
			Location loc = serviceLocator.getLocationService().searchByCode(searchText);
			if (loc == null)
				showError("No result available for this code.");
			else {
				locations.clear();
				locations.add(loc);
				location = tmpLocation = null;
			}
		} else {
			List<Location> locations = serviceLocator.getLocationService().listByName(searchText);
			if (locations == null || locations.isEmpty())
				showError("No results for the given search text.");
			else {
				this.locations = locations;
				location = tmpLocation = null;
			}
		}
	}

	public void selectLocation(ClickActionEvent re) {
		if (tmpLocation != null)
			tmpLocation.setUi_selected(false);
		tmpLocation = (Location) re.getComponent().getAttributes().get("loc");
		tmpLocation.setUi_selected(true);
		location = serviceLocator.getLocationService().retrieve(tmpLocation.getId());
	}

	public void addNewLocation() {
		if (tmpLocation != null)
			tmpLocation.setUi_selected(false);
		location = new Location();
		location.setContact(new Contact());

	}

	public void saveLocation() {
		if (validateFields()) {
			if (location.getId() == null) {
				location = serviceLocator.getLocationService().create(location);
				tmpLocation = location;
				tmpLocation.setUi_selected(true);
				locations.add(tmpLocation);
				location = serviceLocator.getLocationService().retrieve(tmpLocation.getId());
				showInfo("Location added successfully..");
			} else {
				location = serviceLocator.getLocationService().update(location);
				location.setUi_selected(true);
				locations.set(locations.indexOf(tmpLocation), location);
				tmpLocation = location;
				location = serviceLocator.getLocationService().retrieve(tmpLocation.getId());
				showInfo("Location updated successfully..");
			}
		}
	}

	public void deleteLocation() {
		try {
			serviceLocator.getLocationService().delete(location.getId());
			locations.remove(tmpLocation);
			location = tmpLocation = null;
			showInfo("Location deleted succesfully..");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	private boolean validateFields() {
		String name = location.getName();
		String code = location.getLocationCode();
		String address = location.getContact().getStreetAddress();
		String city = location.getContact().getCity();
		String state = location.getContact().getState();
		String postCode = location.getContact().getPostCode();
		String email = location.getContact().getEmail();
		String contact1=location.getContact().getOfficePhone();
		String contact2=location.getContact().getHomePhone();
		List<String> componentIds = new ArrayList<String>();
		boolean errorFound = false;
		clearInputs();
		if (!validateString(name)) {
			showError("Location name can not be empty.");
			componentIds.add("input_locName");
			errorFound = true;
		}
		if (!validateString(code)) {
			showError("Location code can not be empty.");
			componentIds.add("input_locCode");
			errorFound = true;
		}
		// email validation
		if (!validateString(email)) {
			showError("Not an valid Email");
			componentIds.add("input_locEmail");
			errorFound = true;
		}
		if (!serviceLocator.getLocationService().validateLocationCode(location.getId(), code)) {
			showError("Location code is already exists.");
			componentIds.add("input_locCode");
			errorFound = true;
		}
		if (!validateString(address) || !validateString(city) || !validateString(state) || !validateString(postCode)) {
			showError("Address fields can not be empty.");
			if (!validateString(address))
				componentIds.add("input_locStreet");
			if (!validateString(city))
				componentIds.add("input_locCity");
			if (!validateString(state))
				componentIds.add("select_locState");
			if (!validateString(postCode))
				componentIds.add("input_locPostCode");
			errorFound = true;
		}
		if(!contact1.equals("")){
			if(!Util.validatePhoneNumberAndFax(contact1, "Contact ")){
				return false;
			}
		}
		if(!contact2.equals("")){
			if(!Util.validatePhoneNumberAndFax(contact2, "Contact ")){
				return false;
			}
		}
		if (errorFound) {
			highlightInputs(componentIds);
			return false;
		}
		return true;
	}

	private boolean validateString(String string) {
		if (string != null && !string.isEmpty()) {
			// email validation
			if (string == location.getContact().getEmail()) {
				Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
				Matcher mat = pattern.matcher(string);
				if (mat.matches()) {
					return true;
				} else {
					return false;
				}
			}
			return true;
		} else
			return false;
	}

	/*
	 * getters and setters
	 */

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return location;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSearchValue() {
		return searchValue;
	}
}
