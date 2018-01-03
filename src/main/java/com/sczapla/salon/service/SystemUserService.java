package com.sczapla.salon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sczapla.salon.model.Position;
import com.sczapla.salon.model.SystemUser;
import com.sczapla.salon.repository.SystemUserRepository;

@Component
@Transactional
public class SystemUserService {

	@Autowired
	private SystemUserRepository userRepository;

	public List<SystemUser> findByPosition(Position roleName) {
		return userRepository.findByPosition(roleName);
	}

	public boolean existEmail(String emial) {
		return userRepository.existEmail(emial) > 0;
	}

	public SystemUser save(SystemUser entity) {
		return userRepository.save(entity);
	}

	public void delete(SystemUser entity) {
		userRepository.delete(entity);
	}

	public Iterable<SystemUser> findAll() {
		return userRepository.findAll();
	}

}
