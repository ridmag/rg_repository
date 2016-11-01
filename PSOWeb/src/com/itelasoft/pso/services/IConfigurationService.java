package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Configuration;

public interface IConfigurationService {

	public Configuration retrieve(String parameter);

	public String retrieveContents(String parameter);

	public void save(Configuration configuration);

	public List<Configuration> listAll();

}
