package com.itelasoft.pso.beans;

public class TransportStaffEventTableRow {

	private StaffEvent mainEvent;
	private StaffEvent returnEvent;

	/**
	 * Constructor which creates TransportEventTableRow with a initial
	 * StaffEvent
	 * 
	 * @param mainEvent
	 */
	public TransportStaffEventTableRow(StaffEvent event, boolean main) {
		if (main)
			mainEvent = event;
		else
			returnEvent = event;
	}

	public StaffMember getStaff() {
		if (mainEvent != null)
			return mainEvent.getStaffMember();
		else if (returnEvent != null)
			return returnEvent.getStaffMember();
		return null;
	}

	public void setMainEvent(StaffEvent mainEvent) {
		this.mainEvent = mainEvent;
	}

	public StaffEvent getMainEvent() {
		return mainEvent;
	}

	public void setReturnEvent(StaffEvent returnEvent) {
		this.returnEvent = returnEvent;
	}

	public StaffEvent getReturnEvent() {
		return returnEvent;
	}

}
