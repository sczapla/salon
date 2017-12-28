package com.sczapla.salon.view.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sczapla.salon.model.GenericEntity;

@Component
@Scope("request")
public class GenericEntityConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			byte[] data = Base64.getDecoder().decode(arg2);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Object o = ois.readObject();
			ois.close();
			return o;
		} catch (Exception ex) {

		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof GenericEntity) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(arg2);
				oos.close();
				return Base64.getEncoder().encodeToString(baos.toByteArray());
			} catch (Exception ex) {

			}
		}
		return null;
	}

}
