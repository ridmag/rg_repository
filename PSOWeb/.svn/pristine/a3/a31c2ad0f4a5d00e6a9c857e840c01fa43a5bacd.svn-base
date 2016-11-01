package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Configuration;
import com.itelasoft.pso.dao.IConfigurationDao;

public class ConfigurationService implements IConfigurationService {

	private IConfigurationDao configurationDao;

	public void setConfigurationDao(IConfigurationDao configurationDao) {
		this.configurationDao = configurationDao;
	}

	// Basic Services

	public List<Configuration> listAll() {
		return configurationDao.listAll();
	}

	public Configuration retrieve(String parameter) {
		return configurationDao.retrieve(parameter);
	}

	public String retrieveContents(String parameter) {
		Configuration configuration = configurationDao.retrieve(parameter);
		if (configuration != null) {
			return configuration.getContents();
		} else {
			return "";
		}
	}

	public void save(Configuration configuration) {
		configurationDao.update(configuration);
	}

}
