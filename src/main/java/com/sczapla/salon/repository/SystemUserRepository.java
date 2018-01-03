package com.sczapla.salon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sczapla.salon.model.SystemUser;

public interface SystemUserRepository extends CrudRepository<SystemUser, Long> {

	@Query(value = "SELECT user FROM SystemUser user LEFT JOIN FETCH user.roles role "
			+ "LEFT JOIN FETCH role.permissions perm WHERE user.emailAddress = :emailAddress ")
	SystemUser findByEmailAddress(@Param("emailAddress") String email);

	@Query(value = "SELECT count(*) FROM SystemUser user WHERE user.emailAddress = :emailAddress")
	Integer existEmail(@Param("emailAddress") String email);

	List<SystemUser> findByPosition(String position);
}
