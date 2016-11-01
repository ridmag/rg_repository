package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.FundingSource;
import com.itelasoft.pso.beans.Outlet;

@ManagedBean(name = "fundingManagerModel")
@SessionScoped
public class FundingManagerModel extends UIModel {
	private List<FundingSource> sources;
	private FundingSource source, tmpSource;
	private Outlet outlet, tmpOutlet;
	private boolean visibleOutlet;

	public void init() {
		sources = serviceLocator.getFundingSourceService().findAll();
		source = tmpSource = null;
		outlet = null;
		visibleOutlet = false;
	}

	public void addNew() {
		if (tmpSource != null)
			tmpSource.setUi_selected(false);
		source = new FundingSource();
	}

	public void selectSource(ClickActionEvent re) {
		if (tmpSource != null)
			tmpSource.setUi_selected(false);
		tmpSource = (FundingSource) re.getComponent().getAttributes()
				.get("source");
		tmpSource.setUi_selected(true);
		source = serviceLocator.getFundingSourceService().retrieve(
				tmpSource.getId());
		source.setOutlets(serviceLocator.getOutletService()
				.listOutletsByFundingSource(source.getId()));
		if (source.getOutlets() == null)
			source.setOutlets(new ArrayList<Outlet>());
	}

	public void saveSource() {
		if (validateSource()) {
			if (source.getId() == null) {
				source = serviceLocator.getFundingSourceService()
						.create(source);
				tmpSource = source;
				source = serviceLocator.getFundingSourceService().retrieve(
						source.getId()); // for not to update table data if user
											// have done any changes to Funding
											// Source
				tmpSource.setUi_selected(true);
				sources.add(tmpSource);
				showInfo("Funding Source added successfully..");
			} else {
				source = serviceLocator.getFundingSourceService()
						.update(source);
				source.setUi_selected(true);
				sources.set(sources.indexOf(tmpSource), source);
				tmpSource = source;
				source = serviceLocator.getFundingSourceService().retrieve(
						source.getId()); // for not to update table data if
											// user
											// have done any changes to
											// Funding
											// Source
				showInfo("Funding Source successfully updated..");
			}
			source.setOutlets(serviceLocator.getOutletService()
					.listOutletsByFundingSource(source.getId()));
		}
	}

	public void deleteSource(ActionEvent ae) {
		try {
			FundingSource fs = (FundingSource) ae.getComponent()
					.getAttributes().get("fus");
			serviceLocator.getFundingSourceService().delete(fs.getId());
			sources.remove(fs);
			if (tmpSource != null)
				tmpSource.setUi_selected(false);
			source = tmpSource = null;
		} catch (DataIntegrityViolationException e) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void deleteSource() {
		try {
			serviceLocator.getFundingSourceService().delete(source.getId());
			sources.remove(tmpSource);
			source = tmpSource = null;
			showInfo("Funding source deleted successfully..");
		} catch (DataIntegrityViolationException e) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public boolean validateSource() {
		String code = source.getFundingCode();
		String type = source.getFundingType();
		if (validateString(code)) {
			if (serviceLocator.getFundingSourceService().validateFundingId(
					source.getId(), code)) {
				if (validateString(type)) {
					return true;
				} else {
					showError("Funding type can not be empty..");
					return false;
				}
			} else {
				showError("Service ID is already exists..");
				return false;
			}
		} else {
			showError("Service ID can not be empty..");
			return false;
		}
	}

	/*
	 * 
	 * Outlet Methods
	 */

	public void addOutlet() {
		outlet = new Outlet();
		outlet.setFundingSource(source);
		outletPopup();
	}

	public void saveOutlet() {
		if (validateOutlet()) {
			if (outlet.getId() == null) {
				outlet = serviceLocator.getOutletService().create(outlet);
				if (source.getOutlets() == null)
					source.setOutlets(new ArrayList<Outlet>());
				source.getOutlets().add(outlet);
				showInfo("Outlet added successfilly..");
			} else {
				outlet = serviceLocator.getOutletService().update(outlet);
				source.getOutlets().set(source.getOutlets().indexOf(tmpOutlet),
						outlet);
				showInfo("Outlet updated successfilly..");
			}
			outletPopup();
		}
	}

	private boolean validateOutlet() {
		if (validateString(outlet.getServiceId())) {
			if (validateString(outlet.getName())) {
				if (validateString(outlet.getMdsid())) {
					return true;
				} else {
					showError("MDSID can not be empty..");
					return false;
				}
			} else {
				showError("Name can not be empty..");
				return false;
			}
		} else {
			showError("Service ID can not be empty..");
			return false;
		}
	}

	public void deleteOutlet(ActionEvent ae) {
		try {
			tmpOutlet = (Outlet) ae.getComponent().getAttributes().get("out");
			serviceLocator.getOutletService().delete(tmpOutlet.getId());
			source.getOutlets().remove(tmpOutlet);
			showInfo("Outlet deleted successfully..");
		} catch (DataIntegrityViolationException e) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public void editOutlet(ActionEvent ae) {
		tmpOutlet = (Outlet) ae.getComponent().getAttributes().get("out");
		outlet = serviceLocator.getOutletService().retrieve(tmpOutlet.getId());
		outletPopup();
	}

	public void outletPopup() {
		if (visibleOutlet) {
			visibleOutlet = false;
		} else {
			visibleOutlet = true;
		}
	}

	private boolean validateString(String string) {
		if (string != null && !string.isEmpty())
			return true;
		else
			return false;
	}

	/*
	 * 
	 * getters and setters
	 */

	public List<FundingSource> getSources() {
		return sources;
	}

	public void setSource(FundingSource source) {
		this.source = source;
	}

	public FundingSource getSource() {
		return source;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setVisibleOutlet(boolean visibleOutlet) {
		this.visibleOutlet = visibleOutlet;
	}

	public boolean isVisibleOutlet() {
		return visibleOutlet;
	}
}
