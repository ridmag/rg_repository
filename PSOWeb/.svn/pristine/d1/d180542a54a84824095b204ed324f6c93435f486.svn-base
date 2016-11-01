package com.itelasoft.pso.web;

import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.itelasoft.pso.beans.Configuration;
import com.itelasoft.pso.services.ServiceLocator;

@ApplicationScoped
@ManagedBean
public class ConfigModel {
	HashMap<String, String> configMap;

	private void init() {
		List<Configuration> list = ServiceLocator.getServiceLocator()
				.getConfigurationService().listAll();
		configMap = new HashMap<String, String>();
		for (Configuration configuration : list) {
			configMap.put(configuration.getParameter(),
					configuration.getContents());
		}
	}

	public String getValue(String key) {
		if (configMap == null)
			init();
		if(!configMap.containsKey(key))
			return null;
		return configMap.get(key);
	}
	
	
}
