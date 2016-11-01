package com.itelasoft.pso.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.Vehicle;

@ManagedBean(name = "vehicleManagerModel")
@SessionScoped
public class VehicleManagerModel extends UIModel {
	private List<Vehicle> allVehicles;
	private Vehicle vehicle, tmpVehicle;
	private String searchType;
	private int photoH, photoW;

	public void init() {
		searchType = "All";
		allVehicles = serviceLocator.getVehicleService().findAll();
		vehicle = tmpVehicle = null;
	}

	public void searchVehicles(ValueChangeEvent event) {
		if (event.getOldValue() != null) {
			searchType = (String) event.getNewValue();
			allVehicles = serviceLocator.getVehicleService().listByType(
					searchType);
			vehicle = tmpVehicle = null;
		}
	}

	public void newVehicle() {
		if (tmpVehicle != null)
			tmpVehicle.setUi_selected(false);
		vehicle = new Vehicle();
	}

	public void selectVehicle(ClickActionEvent re) {
		if (tmpVehicle != null)
			tmpVehicle.setUi_selected(false);
		tmpVehicle = (Vehicle) re.getComponent().getAttributes().get("vehicle");
		tmpVehicle.setUi_selected(true);
		vehicle = serviceLocator.getVehicleService().retrieveEager(
				tmpVehicle.getId());
		initPhotoImage();
	}

	public void saveVehicle() {
		if (validate()) {
			if (vehicle.getId() == null) {
				vehicle = serviceLocator.getVehicleService().create(vehicle);
				tmpVehicle = vehicle;
				vehicle = serviceLocator.getVehicleService().retrieveEager(
						vehicle.getId()); // for not to update table data if
											// user have done any changes to
											// vehicle
				tmpVehicle.setUi_selected(true);
				allVehicles.add(tmpVehicle);
				showInfo("Vehicle added successfully..");
			} else {
				vehicle = serviceLocator.getVehicleService().update(vehicle);
				vehicle.setUi_selected(true);
				allVehicles.set(allVehicles.indexOf(tmpVehicle), vehicle);
				tmpVehicle = vehicle;
				vehicle = serviceLocator.getVehicleService().retrieveEager(
						vehicle.getId()); // for not to update table data if
											// user have done any changes to
											// vehicle
				showInfo("Vehicle updated successfully..");
			}
		}
	}

	public void deleteVehicle() {
		try {
			serviceLocator.getVehicleService().delete(vehicle.getId());
			allVehicles.remove(tmpVehicle);
			vehicle = tmpVehicle = null;
			showInfo("Vehicle deleted succesfully..");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void initPhotoImage() {
		byte[] tmpData = null;
		if (vehicle.getPhoto() != null) {
			tmpData = vehicle.getPhoto().getBlobFileData().getData();
			if (tmpData != null) {
				BufferedImage loadImg;
				try {
					loadImg = ImageIO.read(new ByteArrayInputStream(tmpData));
					int w = loadImg.getWidth();
					int h = loadImg.getHeight();
					if (w > h) {
						photoW = 230;
						photoH = photoW * h / w;
					} else {
						photoH = 180;
						photoW = photoH * w / h;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean validate() {
		String name = vehicle.getName();
		String regNo = vehicle.getRegistrationId();
		int pasengers = vehicle.getPassengers();
		if (name != null && !name.isEmpty()) {
			if (regNo != null && !regNo.isEmpty()) {
				if (pasengers != 0) {
					if (!vehicle.getStatus().equals("Active")) {
						String message = "";
						if (serviceLocator.getVehicleService()
								.isHavingActivePrograms(vehicle.getId())) {
							message = "This vehicle is assigned to active program(s)";
						}
						if (serviceLocator.getVehicleService()
								.isHavingPendingEvents(vehicle.getId())) {
							if (message.isEmpty()) {
								message = "This vehicle has pending event(s)..";
							} else {
								message = message
										+ " and also has pending event(s)..";
							}
						}
						if (!message.isEmpty()) {
							showError(message);
							showError("Status can not be 'Inactive'.");
							return false;
						}
					}
					return true;
				} else {
					showError("Number of passengers can not be a zero value..");
					return false;
				}
			} else {
				showError("Registration number can not be empty..");
				return false;
			}
		} else {
			showError("Vehicle name can not be empty..");
			return false;
		}

	}

	/*
	 * getters and setters
	 */

	public List<Vehicle> getAllVehicles() {
		return allVehicles;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchType() {
		return searchType;
	}

	public int getPhotoH() {
		return photoH;
	}

	public int getPhotoW() {
		return photoW;
	}

}
