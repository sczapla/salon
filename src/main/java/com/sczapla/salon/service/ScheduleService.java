package com.sczapla.salon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sczapla.salon.repository.VisitRepository;

@Service
@Transactional
public class ScheduleService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VisitRepository visitRepository;

	@Scheduled(cron = "0 5/30 * * * *")
	public void updateVisitStatus() {
		logger.info("Visit status update start.");
		visitRepository.updateStatusOldVisit();
	}
}
