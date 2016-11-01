package com.itelasoft.pso.services;

import java.util.Collection;
import java.util.List;

import com.itelasoft.pso.beans.User;

public interface IUserService extends IGenericService<User, Long> {
	public User newUser();

	public User save(User user);

	public void delete(User user);

	public User getUserByUserName(String username);

	public Collection<User> getUsers();

	public List<User> getUsersByType(String type);

	public List<User> findByName(String name);

	public void assignAuthority(User user, String authorityCode);

	public void removeAuthority(User user, String authorityCode);

	public boolean validateUserName(Long userId, String userName);

}
