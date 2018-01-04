package com.sczapla.salon.view.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sczapla.salon.model.Status;
import com.sczapla.salon.model.Visit;
import com.sczapla.salon.service.VisitService;

@Component
public class ScheduleLazyModel extends LazyScheduleModel {

	private static final long serialVersionUID = 1L;

	private Long userFrom;
	private Long userTo;

	@Autowired
	private VisitService visitService;

	@Override
	public void loadEvents(Date start, Date end) {
		LocalDateTime startDateDayTime = LocalDateTime.of(start.getYear() + 1900, start.getMonth() + 1, start.getDate(),
				10, 0);
		LocalDateTime endDateDayTime = LocalDateTime.of(start.getYear() + 1900, start.getMonth() + 1, start.getDate(),
				17, 30);
		LocalDateTime endDateTime = LocalDateTime.of(end.getYear() + 1900, end.getMonth() + 1, end.getDate(), 17, 30);
		List<Visit> reservation = visitService.findAllByUserPersonel(userFrom, userTo, startDateDayTime, endDateTime);
		for (LocalDateTime d = startDateDayTime; !d.isAfter(endDateTime); d = d.plusDays(1)) {
			if (d.getDayOfWeek() != DayOfWeek.SUNDAY && d.getDayOfWeek() != DayOfWeek.SATURDAY) {
				addDayEvents(d, endDateDayTime, reservation);
			}
			d = d.withHour(10).withMinute(0);
			endDateDayTime = endDateDayTime.withHour(17).withMinute(30).plusDays(1);
		}
	}

	private void addDayEvents(LocalDateTime startDateTime, LocalDateTime endDateTime, List<Visit> reservation) {
		for (LocalDateTime d = startDateTime; !d.isAfter(endDateTime); d = d.plusMinutes(30)) {
			Date from = Date.from(d.atZone(ZoneId.systemDefault()).toInstant());
			Date to = Date.from(d.plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
			ScheduleEvent event = new DefaultScheduleEvent(
					dateReservationStatus(d, d.plusMinutes(30), reservation).name(), from, to);
			addEvent(event);
		}
	}

	private Status dateReservationStatus(LocalDateTime from, LocalDateTime to, List<Visit> reservation) {
		for (Visit visit : reservation) {
			if ((visit.getVisitFrom().compareTo(from) == 0) && (visit.getVisitTo().compareTo(to) == 0)) {
				return visit.getStatus();
			}
		}
		return Status.WOLNE;
	}

	public void setUserFrom(Long userFrom) {
		this.userFrom = userFrom;
	}

	public void setUserTo(Long userTo) {
		this.userTo = userTo;
	}

}
