package com.itelasoft.pso.beans;

public class TransportEventTableRow {

	private StudentEvent mainEvent;
	private StudentEvent returnEvent;

	/**
	 * Constructor which creates TransportEventTableRow with a initial
	 * StudentEvent
	 * 
	 * @param mainEvent
	 */
	public TransportEventTableRow(StudentEvent event, boolean main) {
		if (main)
			mainEvent = event;
		else
			returnEvent = event;
	}

	public Student getStudent() {
		if (mainEvent != null)
			return mainEvent.getGroupedStudent().getStudent();
		else if (returnEvent != null)
			return returnEvent.getGroupedStudent().getStudent();
		return null;
	}

	public double getRemainingHours() {
		if (mainEvent != null)
			return mainEvent.getRemainingHours();
		else if (returnEvent != null)
			return returnEvent.getRemainingHours();
		return 0;
	}

	public String getFundingStatus() {
		if (mainEvent != null)
			return mainEvent.getFundingStatus();
		else if (returnEvent != null)
			return returnEvent.getFundingStatus();
		return "";
	}

	public void setMainEvent(StudentEvent mainEvent) {
		this.mainEvent = mainEvent;
	}

	public StudentEvent getMainEvent() {
		return mainEvent;
	}

	public void setReturnEvent(StudentEvent returnEvent) {
		this.returnEvent = returnEvent;
	}

	public StudentEvent getReturnEvent() {
		return returnEvent;
	}

}