package com.sczapla.salon.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("linkView")
@Scope("view")
public class LinkView implements Serializable {

	private static final long serialVersionUID = 1L;

	private final ResourceBundle messagesBundle;

	public LinkView() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		this.messagesBundle = application.getResourceBundle(context, "msg");
	}

	public void goToHistory() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/history.xhtml");
	}

	public void goToVisit() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/visit.xhtml");
	}

	public void goToAdmin() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/system-user.xhtml");
	}
}
