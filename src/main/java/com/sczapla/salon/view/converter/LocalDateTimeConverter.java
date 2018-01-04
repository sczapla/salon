package com.sczapla.salon.view.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class LocalDateTimeConverter implements Converter {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			return LocalDateTime.parse(arg2, formatter);
		} catch (Exception ex) {

		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof LocalDateTime) {
			try {
				return ((LocalDateTime) arg2).format(formatter);
			} catch (Exception ex) {

			}
		}
		return null;
	}

}
