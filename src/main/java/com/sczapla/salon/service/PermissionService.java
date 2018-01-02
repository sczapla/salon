package com.sczapla.salon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sczapla.salon.model.Permission;
import com.sczapla.salon.repository.PermissionRepository;

@Component
@Transactional
public class PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	public List<Permission> findAllOrderByNameAsc() {
		return permissionRepository.findAllPermission();
	}

	public Permission save(Permission entity) {
		return permissionRepository.save(entity);
	}

	public void delete(Permission entity) {
		permissionRepository.delete(entity);
	}

	public Iterable<Permission> findAll() {
		return permissionRepository.findAll();
	}

}
