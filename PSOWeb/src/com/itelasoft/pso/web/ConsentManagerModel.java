package com.itelasoft.pso.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.Consent;

@ManagedBean(name = "consentManagerModel")
@SessionScoped
public class ConsentManagerModel extends UIModel {

	private List<Consent> consents;
	private Consent consent;
	private Consent tmpConsent;

	public void init() {
		consents = serviceLocator.getConsentService()
				.listWithoutFixedConsents();
		if (consents == null)
			consents = new ArrayList<Consent>();
		consent = null;
	}

	public void selectConsent(ClickActionEvent re) {
		if (tmpConsent != null)
			tmpConsent.setUi_selected(false);
		tmpConsent = (Consent) re.getComponent().getAttributes().get("con");
		tmpConsent.setUi_selected(true);
		consent = serviceLocator.getConsentService().retrieve(
				tmpConsent.getId());
	}

	public void addNewConsent() {
		if (tmpConsent != null)
			tmpConsent.setUi_selected(false);
		consent = new Consent();
	}

	public void saveConsent() {
		if(consent.getName().isEmpty()){
			showError("Consent name cannot be empty..");
			return;
		}
		if (consent.getId() == null) {
			consent = serviceLocator.getConsentService().create(consent);
			tmpConsent = consent;
			tmpConsent.setUi_selected(true);
			consents.add(tmpConsent);
			consent = serviceLocator.getConsentService().retrieve(
					tmpConsent.getId());
			showInfo("Consent added successfully..");
		} else {
			consent = serviceLocator.getConsentService().update(consent);
			consent.setUi_selected(true);
			consents.set(consents.indexOf(tmpConsent), consent);
			tmpConsent = consent;
			consent = serviceLocator.getConsentService().retrieve(
					tmpConsent.getId());
			showInfo("Consent updated successfully..");
		}
	}

	public void deleteConsent() {
		try {
			serviceLocator.getConsentService().delete(consent.getId());
			consents.remove(tmpConsent);
			consent = tmpConsent = null;
			showInfo("Consent deleted succesfully.");
		} catch (DataIntegrityViolationException d) {
			showError(Util.getMessage("error.integrity"));
		}
	}

	public List<Consent> getConsents() {
		return consents;
	}

	public void setConsents(List<Consent> consents) {
		this.consents = consents;
	}

	public void setConsent(Consent consent) {
		this.consent = consent;
	}

	public Consent getConsent() {
		return consent;
	}

}
