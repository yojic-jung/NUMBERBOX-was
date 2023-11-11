package com.numberbox.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.numberbox.members.entity.Members;
import com.numberbox.security.dto.CustomSecurityUser;

public class StaticSecurityUtil {

	public static Members getMembers() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomSecurityUser customUser = (CustomSecurityUser) authentication.getPrincipal();
		Members members = customUser.getMembers();
		return members;
	}

}
