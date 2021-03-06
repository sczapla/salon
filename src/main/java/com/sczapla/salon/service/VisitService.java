package com.sczapla.salon.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

	public List<Visit> findAllByUserPersonel(Long userFromId, Long userToId, LocalDateTime dateFrom,
			LocalDateTime dateTo) {
		if (userFromId != null) {
			return visitRepository.findAllByUserPersonel(userFromId, userToId, dateFrom, dateTo);
		} else {
			return visitRepository.findAllByPersonel(userToId, dateFrom, dateTo);
		}
	}

	public List<Visit> findAllUserVisit(@Param("userFromId") Long userFromId) {
		return visitRepository.findAllUserVisit(userFromId);
	}

}
