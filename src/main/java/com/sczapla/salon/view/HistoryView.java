package com.sczapla.salon.view;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sczapla.salon.model.Status;
import com.sczapla.salon.model.Visit;
import com.sczapla.salon.service.SecurityService;
import com.sczapla.salon.service.VisitService;
import com.sczapla.salon.view.utils.Utils;

@Component("historyView")
@Scope("view")
public class HistoryView implements Serializable {

	private static final long serialVersionUID = 1L;

	private final ResourceBundle messagesBundle;
	private List<Visit> visits;

	@Autowired
	private VisitService visitService;

	@Autowired
	private SecurityService securityService;

	public HistoryView() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		this.messagesBundle = application.getResourceBundle(context, "msg");
	}

	@PostConstruct
	public void init() {
		visits = visitService.findAllUserVisit(securityService.getLoggedUser().getId());
	}

	public void delete(Visit entity) {
		entity.setStatus(Status.ANULOWANE);
		visitService.save(entity);
		Utils.addDetailMessage(messagesBundle.getString("cancel.visit.msg"), FacesMessage.SEVERITY_INFO);
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

}
