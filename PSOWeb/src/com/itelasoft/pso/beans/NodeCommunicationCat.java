package com.itelasoft.pso.beans;

import javax.swing.tree.DefaultMutableTreeNode;

import com.icesoft.faces.component.tree.IceUserObject;

public class NodeCommunicationCat extends IceUserObject {

	private CommunicationCat category;
	private StaffType staffType;
	private ServiceArea serviceArea;
	private String staffStatus;
	private String vehicleType;

	public NodeCommunicationCat(DefaultMutableTreeNode wrapper) {
		super(wrapper);
	}

	public CommunicationCat getCategory() {
		return category;
	}

	public void setCategory(CommunicationCat category) {
		this.category = category;
	}

	public void setStaffType(StaffType staffType) {
		this.staffType = staffType;
	}

	public StaffType getStaffType() {
		return staffType;
	}

	public void setServiceArea(ServiceArea serviceArea) {
		this.serviceArea = serviceArea;
	}

	public ServiceArea getServiceArea() {
		return serviceArea;
	}

	public void setStaffStatus(String staffStatus) {
		this.staffStatus = staffStatus;
	}

	public String getStaffStatus() {
		return staffStatus;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getVehicleType() {
		return vehicleType;
	}
}
