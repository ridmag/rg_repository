package com.itelasoft.sample;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.beanfiles.translators.PatternTranslator;
import com.itelasoft.pso.beans.Consent;
import com.itelasoft.pso.beans.StudentConsent;
import com.itelasoft.pso.services.ServiceLocator;

public class StudentConsentTranslator extends PatternTranslator {

	private List<Consent> consents;

	public StudentConsentTranslator() {
		consents = ServiceLocator.getServiceLocator().getConsentService()
				.findAll();
	}

	@Override
	public Object read(Object i, Class<?> clazz) {
		ArrayList<StudentConsent> arrayList = new ArrayList<StudentConsent>();
		for (String key : this.matches.keySet()) {
			StudentConsent studentConsent = new StudentConsent();
			for (Consent consent : consents) {
				if (consent.getName().equals(key)) {
					studentConsent.setConsent(consent);
					// studentConsent.setConsentGiven(true);
					arrayList.add(studentConsent);
					break;
				}

			}
		}

		return arrayList;
	}
}