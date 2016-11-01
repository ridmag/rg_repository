package com.itelasoft.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UserUtils {

	public static String getCurrentUsername() {
		return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}

}
