package com.sczapla.salon.service;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.springframework.stereotype.Service;

@Service
public class Redirector {

	public void redirect(String uri) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(uri);
	}

}
