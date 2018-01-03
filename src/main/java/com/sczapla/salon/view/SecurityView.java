package com.sczapla.salon.view;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component("securityView")
@Scope(value = "session")
public class SecurityView {

	private User user;

	@PostConstruct
	public void init() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() instanceof User) {
			user = (User) auth.getPrincipal();
		}
	}

	public String getUsername() {
		return user == null ? "anonimowy" : user.getUsername();
	}

	public boolean hasPermission(String permission) {
		if (user != null) {
			Collection<GrantedAuthority> authorities = user.getAuthorities();
			for (GrantedAuthority auth : authorities) {
				if (auth.getAuthority().equals(permission)) {
					return true;
				}
			}
		}
		return false;
	}
}
