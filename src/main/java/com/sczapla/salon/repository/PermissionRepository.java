package com.sczapla.salon.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sczapla.salon.model.Permission;

public interface PermissionRepository extends CrudRepository<Permission, Long> {

	@Query(value = "FROM Permission per ORDER BY per.name ASC")
	List<Permission> findAllPermission();

}
