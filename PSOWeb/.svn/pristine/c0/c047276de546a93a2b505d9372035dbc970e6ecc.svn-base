package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Consent;
import com.itelasoft.pso.dao.IConsentDao;

public class ConsentService extends GenericService<Consent, Long> implements
		IConsentService {

	private IConsentDao consentDao;

	public IConsentDao getConsentDao() {
		return consentDao;
	}

	public void setConsentDao(IConsentDao consentDao) {
		this.consentDao = consentDao;
	}

	public List<Consent> listWithoutFixedConsents() {
		return this.consentDao.listWithoutFixedConsents();
	}
}
