package com.abdelaziz.validator;

import java.util.List;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import org.omnifaces.validator.MultiFieldValidator;

public class LoginValidator implements MultiFieldValidator {

	@Override
	public boolean validateValues(FacesContext context,
			List<UIInput> components, List<Object> values) {
		if (values.get(0) == null || values.get(1) == null)
			return false;

		return values.get(0).toString().matches("^[a-zA-Z0-9]*$")
				&& values.get(0).toString().length() > 4
				&& values.get(0).toString().length() < 20
				&& values.get(1).toString().matches("^[a-zA-Z0-9]*$")
				&& values.get(1).toString().length() > 4
				&& values.get(1).toString().length() < 20;
	}
}