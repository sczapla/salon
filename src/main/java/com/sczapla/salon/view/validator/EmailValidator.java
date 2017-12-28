package com.sczapla.salon.view.validator;

import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sczapla.salon.service.SystemUserService;
import com.sczapla.salon.view.utils.Constans;

@Component
@Scope("request")
public class EmailValidator implements Validator {

	@Autowired
	private SystemUserService userService;

	@SuppressWarnings("unused")
	private ResourceBundle messagesBundle;

	private Pattern pattern;

	public EmailValidator() {
		pattern = Pattern.compile(Constans.EMAIL_PATTERN);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Application application = context.getApplication();
		ResourceBundle messagesBundle = application.getResourceBundle(context, "msg");

		if (!pattern.matcher(value.toString()).matches()) {
			FacesMessage msg = new FacesMessage(null, messagesBundle.getString("message.error.incorrectEmail"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

		if (userService.existEmail((String) value)) {
			FacesMessage msg = new FacesMessage(null,
					messagesBundle.getString("message.error.emailConstraintViolation"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

}
