package com.itelasoft.pso.services;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import com.itelasoft.pso.beans.Authority;
import com.itelasoft.pso.beans.Contact;
import com.itelasoft.pso.beans.User;
import com.itelasoft.pso.dao.IAuthorityDao;
import com.itelasoft.pso.dao.IUserDao;

public class UserServiceImpl extends GenericService<User, Long> implements
		IUserService {

	// private IUserDao userDao = null;
	private IAuthorityDao authorityDao = null;

	public IAuthorityDao getAuthorityDao() {
		return authorityDao;
	}

	public void setAuthorityDao(IAuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}

	/*
	 * public void setUserDao(IUserDao userDao) { this.userDao = userDao; }
	 */

	public User newUser() {
		User user = new User();
		Contact contact = new Contact();
		user.setContact(contact);
		user.setCreatedOn(GregorianCalendar.getInstance().getTime());
		user.setEnabled(true);
		return user;
	}

	public void delete(User user) {

	}

	public Collection<User> getUsers() {
		return ((IUserDao) dao).listAll();
	}

	public List<User> getUsersByType(String type) {
		return ((IUserDao) dao).listByType(type);
	}

	public User getUserByUserName(String userName) {
		return ((IUserDao) dao).getUserByUserName(userName);
	}

	public List<User> findByName(String name) {
		return ((IUserDao) dao).listByName(name);
	}

	public User save(User user) {
		// If no authorities are assigned to this user, set base authorities
		if (user.isSystemUser()) {
			assignDefualtAuthority(user);
			if (user.getCreatedOn() == null)
				user.setCreatedOn(GregorianCalendar.getInstance().getTime());
		}
		// Depending on the state of user save or update
		if (user.getId() != null) {
			return ((IUserDao) dao).update(user);
		} else {
			return ((IUserDao) dao).save(user);
		}
	}

	// Assign a new authority in to a user
	private Authority newAuthority(User user, String userName,
			String authorityCode) {
		Authority authority = new Authority();
		authority.setUser(user);
		authority.setUserName(userName);
		authority.setAuthority(authorityCode);
		return authority;
	}

	// If no authorities are assigned to a user, assign basic authority levels
	// based on the type of the user entity
	private void assignDefualtAuthority(User user) {
		if (user.getAuthorities().isEmpty()) {
			user.getAuthorities().add(
					newAuthority(user, user.getUserName(), "ROLE_USER"));
			if (user.getUserType().compareTo("STF") == 0)
				user.getAuthorities().add(
						newAuthority(user, user.getUserName(), "ROLE_STAFF"));
			if (user.getUserType().compareTo("SUP") == 0)
				user.getAuthorities().add(
						newAuthority(user, user.getUserName(),
								"ROLE_SUPERVISOR"));
			if (user.getUserType().compareTo("FIN") == 0)
				user.getAuthorities().add(
						newAuthority(user, user.getUserName(), "ROLE_FINANCE"));
			if (user.getUserType().compareTo("MGR") == 0)
				user.getAuthorities().add(
						newAuthority(user, user.getUserName(), "ROLE_ADMIN"));
		} else {
			boolean authorityFound = false;
			for (Authority authority : user.getAuthorities()) {
				authority.setUserName(user.getUserName());
				if (authority.getAuthority().compareTo("ROLE_USER") == 0) {
					authorityFound = true;
				}
			}
			if (!authorityFound) {
				user.getAuthorities().add(
						newAuthority(user, user.getUserName(), "ROLE_USER"));
			}
		}
	}

	// Assign an authority to a user
	public void assignAuthority(User user, String authorityCode) {
		// check if the given authority is already assigned
		boolean authorityFound = false;
		for (Authority authority : user.getAuthorities()) {
			if (authority.getAuthority().compareTo(authorityCode) == 0)
				authorityFound = true;
		}
		// If not found assign the new authority
		if (!authorityFound) {
			user.getAuthorities().add(
					newAuthority(user, user.getUserName(), authorityCode));
		}
	}

	// Remove an authority from a user
	public void removeAuthority(User user, String authorityCode) {
		for (Authority authority : user.getAuthorities()) {
			if (authority.getAuthority().compareTo(authorityCode) == 0) {
				//authorityDao.delete(authority);
				user.getAuthorities().remove(authority);
			}
		}
	}

	public boolean validateUserName(Long userId, String userName) {
		return ((IUserDao) dao).validateUserName(userId, userName);
	}

}
