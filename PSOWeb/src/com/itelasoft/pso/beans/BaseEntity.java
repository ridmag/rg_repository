package com.itelasoft.pso.beans;

import java.util.Date;

import javax.persistence.Transient;

public class BaseEntity {
	private boolean ui_selected; // Used in UI specific purposes like marking an
									// item selected in a list
	private String lastUpdatedBy;
	private Date lastUpdatedOn;
	private Date createOn;

	@Transient
	public boolean isUi_selected() {
		return ui_selected;
	}

	public void setUi_selected(boolean uiSelected) {
		ui_selected = uiSelected;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Date getCreateOn() {
		return createOn;
	}

}
