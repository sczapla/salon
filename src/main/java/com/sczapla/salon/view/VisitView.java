package com.sczapla.salon.view;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sczapla.salon.model.Position;
import com.sczapla.salon.model.SystemUser;
import com.sczapla.salon.model.Visit;
import com.sczapla.salon.service.SystemUserService;
import com.sczapla.salon.service.VisitService;
import com.sczapla.salon.view.model.ScheduleLazyModel;

@Component("visitView")
@Scope("view")
public class VisitView implements Serializable {

	private static final long serialVersionUID = 1L;
	private final ResourceBundle messagesBundle;

	private Visit newEntity;
	private Boolean hair;
	private Boolean beautician;

	@Autowired
	private ScheduleLazyModel eventModel;

	@Autowired
	private VisitService visitService;

	@Autowired
	private SystemUserService userSerivice;

	public VisitView() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		this.messagesBundle = application.getResourceBundle(context, "msg");
	}

	@PostConstruct
	public void init() {
		newEntity = new Visit();
		hair = true;
		// eventModel.addEvent(new DefaultScheduleEvent("Birthday Party",
		// today1Pm(),
		// today6Pm()));
	}

	public void onEventSelect(SelectEvent selectEvent) {
		int a = 0;
		a++;
	}

	public void changeOfferHair() {
		beautician = !hair;
	}

	public void changeOfferBeauty() {
		hair = !beautician;
	}

	public void add() {
		newEntity = new Visit();
	}

	public void edit(Visit entity) {
		newEntity = entity;
	}

	public String onFlowProcess(FlowEvent event) {
		if (event.getNewStep().equals(WizzardStep.person.name())) {
			List<SystemUser> personel = userSerivice
					.findByPosition(hair ? Position.FRYZJER.toString() : Position.KOSMETYCZKA.toString());
		}
		return event.getNewStep();
	}

	public ScheduleLazyModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleLazyModel eventModel) {
		this.eventModel = eventModel;
	}

	public Boolean getHair() {
		return hair;
	}

	public void setHair(Boolean hair) {
		this.hair = hair;
	}

	public Boolean getBeautician() {
		return beautician;
	}

	public void setBeautician(Boolean beautician) {
		this.beautician = beautician;
	}

}
