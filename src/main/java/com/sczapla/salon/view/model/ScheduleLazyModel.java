package com.sczapla.salon.view.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.springframework.stereotype.Component;

@Component
public class ScheduleLazyModel extends LazyScheduleModel{

	private static final long serialVersionUID = 1L;

	@Override
	public void loadEvents(Date start, Date end) {
		LocalDateTime startDateDayTime = LocalDateTime.of(start.getYear()+1900, start.getMonth()+1, start.getDate(), 10, 0);
		LocalDateTime endDateDayTime = LocalDateTime.of(start.getYear()+1900, start.getMonth()+1, start.getDate(), 17, 30);
		LocalDateTime endDateTime = LocalDateTime.of(end.getYear()+1900, end.getMonth()+1, end.getDate(), 17, 30);
		for (LocalDateTime d = startDateDayTime; !d.isAfter(endDateTime); d = d.plusDays(1)) {
			if(d.getDayOfWeek() != DayOfWeek.SUNDAY && d.getDayOfWeek() != DayOfWeek.SATURDAY) {
				addDayEvents(d, endDateDayTime);
			}
			d = d.withHour(10).withMinute(0);
			endDateDayTime = endDateDayTime.withHour(17).withMinute(30).plusDays(1);
		}
	}
	
	private void addDayEvents(LocalDateTime startDateTime, LocalDateTime endDateTime) {
		for (LocalDateTime d = startDateTime; !d.isAfter(endDateTime); d = d.plusMinutes(30)) {
			ScheduleEvent event = new DefaultScheduleEvent("Wolne", Date.from(d.atZone(ZoneId.systemDefault()).toInstant()), 
					Date.from(d.plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant()));
			addEvent(event);
		}
	}
}
