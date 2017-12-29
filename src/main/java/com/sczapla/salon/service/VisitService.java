package com.sczapla.salon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sczapla.salon.model.Visit;
import com.sczapla.salon.repository.VisitRepository;

@Component
@Transactional
public class VisitService {

	@Autowired
	private VisitRepository visitRepository;

	public Visit save(Visit entity) {
		return visitRepository.save(entity);
	}

	public void delete(Visit entity) {
		visitRepository.delete(entity);
	}

	public Iterable<Visit> findAll() {
		return visitRepository.findAll();
	}

}
