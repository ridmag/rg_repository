package com.itelasoft.pso.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import com.itelasoft.pso.beans.Authority;
import com.itelasoft.pso.beans.User;

@ManagedBean(name = "userManagerModel")
@SessionScoped
public class UserManagerModel extends UIModel {

	private User user;
	private String userSearchText;
	private List<SelectItem> userSearchResults;
	private String selectedUserName;
	private List<SelectItem> userAuthorities;
	private List<SelectItem> authorities;
	private String selectedAuthority1;
	private String selectedAuthority2;

	public void init() {
		userSearchResults = new ArrayList<SelectItem>();
		userAuthorities = new ArrayList<SelectItem>();
		loadUser(getCurrentUserName());
	}

	public void loadUser(String userName) {
		try {
			this.user = serviceLocator.getUserService().getUserByUserName(
					userName);
			refreshAuthorities();
		} catch (Exception exception) {
			showExceptionAsError(exception);
		}
	}

	// Search a set of users matching a criteria based on the name
	public void searchUsers(ActionEvent event) {
		userSearchResults.clear();
		List<User> list = serviceLocator.getUserService().findByName(
				userSearchText);
		if (list != null && !list.isEmpty()) {
			for (User user : list) {
				this.userSearchResults.add(new SelectItem(user.getUserName(),
						user.getContact().getFirstName() + " "
								+ user.getContact().getLastName()));
			}
		} else {
			showError("No results found for the given search criteria.");
		}
	}

	// Create a new user and assign to the model
	public void newUser(ActionEvent event) {
		this.user = serviceLocator.getUserService().newUser();
		refreshAuthorities();
	}

	// Select a user from the search results
	public void selectUser(ActionEvent event) {
		if (!selectedUserName.isEmpty()) {
			this.user = serviceLocator.getUserService().getUserByUserName(
					selectedUserName);
			refreshAuthorities();
		}
	}

	// Save current user
	public void saveUser(ActionEvent event) {
		try {
			user.setSystemUser(true);
			serviceLocator.getUserService().save(user);
			showInfo("User Details Saved.");
		} catch (Exception exception) {
			showExceptionAsError(exception);
		}
		refreshAuthorities();
	}

	// Delete the selected user and assign a new user in to the model
	public void deleteUser(ActionEvent event) {
		serviceLocator.getUserService().delete(user);
		this.user = serviceLocator.getUserService().newUser();
		refreshAuthorities();
	}

	private void refreshAuthorities() {
		userAuthorities.clear();
		if (user.getAuthorities() != null) {
			for (Authority authority : user.getAuthorities()) {
				userAuthorities.add(new SelectItem(authority.getAuthority(),
						authority.getAuthority()));
			}
		}
	}

	// Add a new authority in to this user
	public void addAuthority(ActionEvent event) {
		try {
			this.serviceLocator.getUserService().assignAuthority(this.user,
					selectedAuthority1);
		} catch (Exception exception) {
			showExceptionAsError(exception);
		}
		refreshAuthorities();
	}

	// Remove an authority already assigned in to the user
	public void removeAuthority(ActionEvent event) {
		try {
			this.serviceLocator.getUserService().removeAuthority(this.user,
					selectedAuthority2);
		} catch (Exception exception) {
			showExceptionAsError(exception);
		}
		refreshAuthorities();
	}

	// Fetch currently logged in user name
	private String getCurrentUserName() {
		Principal principal = ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getUserPrincipal();
		if (principal != null) {
			return principal.getName();
		} else {
			return "";
		}
	}

	public List<SelectItem> getAuthorities() {
		if (authorities == null) {
			authorities = new ArrayList<SelectItem>();
			authorities.add(new SelectItem("ROLE_USER", "ROLE_USER"));
			authorities.add(new SelectItem("ROLE_ADMIN", "ROLE_ADMIN"));
			authorities.add(new SelectItem("ROLE_STAFF", "ROLE_STAFF"));
			authorities.add(new SelectItem("ROLE_FINANCE", "ROLE_FINANCE"));
			authorities
					.add(new SelectItem("ROLE_SUPERVISOR", "ROLE_SUPERVISOR"));
		}
		return authorities;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUserSearchText(String userSearchText) {
		this.userSearchText = userSearchText;
	}

	public String getUserSearchText() {
		return userSearchText;
	}

	public void setSelectedUserName(String selectedUserName) {
		this.selectedUserName = selectedUserName;
	}

	public String getSelectedUserName() {
		return selectedUserName;
	}

	public List<SelectItem> getUserSearchResults() {
		return userSearchResults;
	}

	public Boolean getUserEditable() {
		return true;
	}

	public List<SelectItem> getUserAuthorities() {
		return userAuthorities;
	}

	public void setSelectedAuthority1(String selectedAuthority1) {
		this.selectedAuthority1 = selectedAuthority1;
	}

	public String getSelectedAuthority1() {
		return selectedAuthority1;
	}

	public void setSelectedAuthority2(String selectedAuthority2) {
		this.selectedAuthority2 = selectedAuthority2;
	}

	public String getSelectedAuthority2() {
		return selectedAuthority2;
	}

}
