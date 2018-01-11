package com.sczapla.salon.view;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sczapla.salon.model.Position;
import com.sczapla.salon.model.Status;
import com.sczapla.salon.model.SystemUser;
import com.sczapla.salon.model.Visit;
import com.sczapla.salon.service.SecurityService;
import com.sczapla.salon.service.SystemUserService;
import com.sczapla.salon.service.VisitService;
import com.sczapla.salon.view.model.ScheduleLazyModel;

@Component("visitView")
@Scope("view")
public class VisitView implements Serializable {

	private static final long serialVersionUID = 1L;
	private final ResourceBundle messagesBundle;

	private List<SystemUser> personel;
	private Long selectedPersonel;
	private Position selectedOffer;
	private DefaultScheduleEvent selectedEvent;

	@Autowired
	private ScheduleLazyModel eventModel;

	@Autowired
	private VisitService visitService;

	@Autowired
	private SystemUserService userSerivice;

	@Autowired
	private SecurityService securityService;

	public VisitView() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		this.messagesBundle = application.getResourceBundle(context, "msg");
	}

	@PostConstruct
	public void init() {
		personel = new ArrayList<SystemUser>();
		if (securityService.hasRole("KIEROWNIK")) {
			selectedOffer = Position.FRYZJER;
			personel = userSerivice.findByPosition(selectedOffer);
			eventModel.setUserFrom(null);
		} else if (securityService.hasRole("PRACOWNIK")) {
			selectedOffer = securityService.getLoggedUser().getPosition();
			personel.add(securityService.getLoggedUser());
			eventModel.setUserFrom(null);
		} else {
			selectedOffer = Position.FRYZJER;
			personel = userSerivice.findByPosition(selectedOffer);
			eventModel.setUserFrom(securityService.getLoggedUser().getId());
		}
		selectedPersonel = personel.get(0).getId();
		eventModel.setClient(securityService.hasRole("KLIENT"));
		eventModel.setUserTo(selectedPersonel);
	}

	public void onEventSelect(SelectEvent selectEvent) {
		DefaultScheduleEvent event = (DefaultScheduleEvent) selectEvent.getObject();
		if (event.getTitle().equals(Status.WOLNE.name())) {
			selectedEvent = event;
		} else {
			selectedEvent = null;
		}
	}

	public void saveVisit() {
		Visit visit = new Visit();
		visit.setStatus(Status.ZAREZEROWANE);
		visit.setVisitFrom(LocalDateTime.ofInstant(selectedEvent.getStartDate().toInstant(), ZoneId.systemDefault()));
		visit.setVisitTo(LocalDateTime.ofInstant(selectedEvent.getEndDate().toInstant(), ZoneId.systemDefault()));
		visit.setUserTo(getSelectedUser());
		visit.setUserFrom(securityService.getLoggedUser());
		visitService.save(visit);
	}

	public void changeOffer() {
		personel = userSerivice.findByPosition(selectedOffer);
		selectedPersonel = personel.get(0).getId();
		eventModel.setUserTo(selectedPersonel);
	}

	public String getConfirmInfo1() {
		if (selectedEvent == null) {
			return messagesBundle.getString("visit.confirm.info.busy");
		} else {
			String msg = messagesBundle.getString("visit.confirm.info.free2");
			return MessageFormat.format(msg, selectedEvent.getStartDate(), selectedEvent.getEndDate());
		}
	}

	public String getConfirmInfo2() {
		if (selectedEvent == null) {
			return messagesBundle.getString("visit.confirm.info.busy");
		} else {
			String msg = messagesBundle.getString("visit.confirm.info.free3");
			return MessageFormat.format(msg, selectedOffer.name());
		}
	}

	public String getConfirmInfo3() {
		if (selectedEvent == null) {
			return messagesBundle.getString("visit.confirm.info.busy");
		} else {
			String msg = messagesBundle.getString("visit.confirm.info.free4");
			return MessageFormat.format(msg, getSelectedUser().getName());
		}
	}

	public void changePersonel() {
		eventModel.setUserTo(selectedPersonel);
	}

	private SystemUser getSelectedUser() {
		for (SystemUser user : personel) {
			if (user.getId() == selectedPersonel) {
				return user;
			}
		}
		return null;
	}

	public ScheduleLazyModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleLazyModel eventModel) {
		this.eventModel = eventModel;
	}

	public List<SystemUser> getPersonel() {
		return personel;
	}

	public void setPersonel(List<SystemUser> personel) {
		this.personel = personel;
	}

	public Long getSelectedPersonel() {
		return selectedPersonel;
	}

	public void setSelectedPersonel(Long selectedPerson) {
		this.selectedPersonel = selectedPerson;
	}

	public Position[] getOffer() {
		if (securityService.hasRole("PRACOWNIK")) {
			Position[] pos = { securityService.getLoggedUser().getPosition() };
			return pos;
		}
		return Position.values();
	}

	public Position getSelectedOffer() {
		return selectedOffer;
	}

	public void setSelectedOffer(Position selectedOffer) {
		this.selectedOffer = selectedOffer;
	}

	public DefaultScheduleEvent getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(DefaultScheduleEvent selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

}
