package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.User;

public interface IUserDao extends IGenericDao<User, Long> {

	public User getUserByUserName(String userName);

	public List<User> listAll();

	public List<User> listByName(String name);

	public List<User> listByType(String type);

	public boolean validateUserName(Long userId, String userName);

}
