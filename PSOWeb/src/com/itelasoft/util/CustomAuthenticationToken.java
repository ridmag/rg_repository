package com.itelasoft.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import waffle.servlet.WindowsPrincipal;

import com.itelasoft.pso.beans.Authority;
import com.itelasoft.pso.services.ServiceLocator;

/**
 * Custom Authentication Token to comply with custom Waffle Authentication
 * mechanism. Variation of Waffle's WindowsAuthenticationToken. See #Note below
 */
public class CustomAuthenticationToken implements Authentication {

	Logger logger = LoggerFactory.getLogger(CustomAuthenticationToken.class);

	private static final long serialVersionUID = 1L;
	private WindowsPrincipal windowsPrincipal = null;
	private org.springframework.security.core.userdetails.User principal = null;
	private Collection<GrantedAuthority> authorities = null;

	// Move this to a config
	public static final String MY_DOMAIN = "<your corporate domain>";

	/**
	 * Constructor that fully initializes the principal
	 * 
	 * @param windowsPrincipal
	 *            windows principal
	 */
	public CustomAuthenticationToken(WindowsPrincipal windowsPrincipal) {
		this.windowsPrincipal = windowsPrincipal;
		// Strip domainName in <domainName>\\username to get mapped username in
		// App
		String username = windowsPrincipal.getName();
		logger.debug("App Login Info derived from ActiveDirectory: " + username);

		// Load roles
		Collection<GrantedAuthority> authorities = loadAuthorities(username);

		// Create UserDetails object
		if (authorities == null)
			authorities = new ArrayList<GrantedAuthority>();
		this.principal = new org.springframework.security.core.userdetails.User(
				username, "", true, true, true, true, authorities);
	}

	/**
	 * Loads granted authorities by looking up User=>Roles. Throws
	 * UsernameNotFoundException if principal isn't mapped mapped to Db
	 * 
	 * @return
	 */
	private Collection<GrantedAuthority> loadAuthorities(String username) {

		// Match User Account from Db
		com.itelasoft.pso.beans.User account = null;
		account = ServiceLocator.getServiceLocator().getUserService()
				.getUserByUserName(username);
		if (account != null) {
			Collection<GrantedAuthority> grantedAuthorities = toGrantedAuthorities(account
					.getAuthorities());
			this.authorities = grantedAuthorities;
			return authorities;

		}
		return null;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Object getCredentials() {
		return null;
	}

	public Object getDetails() {
		return windowsPrincipal;
	}

	public Object getPrincipal() {
		return principal;
	}

	public boolean isAuthenticated() {
		return (principal != null);
	}

	public void setAuthenticated(boolean authenticated)
			throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	public String getName() {
		return principal.getUsername();
	}

	private static Collection<GrantedAuthority> toGrantedAuthorities(
			List<Authority> list) {
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();

		for (Authority role : list) {
			result.add(new GrantedAuthorityImpl(role.getAuthority()));
		}

		return result;
	}
}