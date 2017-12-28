package com.sczapla.salon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sczapla.salon.model.Role;
import com.sczapla.salon.repository.RoleRepository;

@Component
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role save(Role entity) {
		return roleRepository.save(entity);
	}

	public void delete(Role entity) {
		roleRepository.delete(entity);
	}

	public Iterable<Role> findAll() {
		return roleRepository.findAll();
	}

}
