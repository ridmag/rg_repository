package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.Configuration;

public interface IConfigurationDao {
	public Configuration retrieve(String parameter);

	public void update(Configuration configuration);

	public List<Configuration> listAll();
}
