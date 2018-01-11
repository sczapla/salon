package com.sczapla.salon.service;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.sczapla.salon.model.Role;
import com.sczapla.salon.model.SystemUser;
import com.sczapla.salon.repository.SystemUserRepository;

@Component("securityService")
@Scope(value = "session")
public class SecurityService {

	@Autowired
	private SystemUserRepository systemUserRepository;

	private User user;

	private SystemUser systemUser;

	@PostConstruct
	public void init() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() instanceof User) {
			user = (User) auth.getPrincipal();
			systemUser = systemUserRepository.findByEmailAddress(user.getUsername());
		}
	}

	public SystemUser getLoggedUser() {
		return systemUser;
	}

	public String getUsername() {
		return systemUser == null ? "anonimowy" : systemUser.getName();
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

	public boolean hasRole(String role) {
		if (systemUser != null) {
			for (Role userRole : systemUser.getRoles()) {
				if (role.equals(userRole.getName())) {
					return true;
				}
			}
		}
		return false;
	}
}
