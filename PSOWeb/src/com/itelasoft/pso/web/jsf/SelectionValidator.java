/*
 * PhoneNumberValidator.java
 *
 * Created on March 28, 2007, 2:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.itelasoft.pso.web.jsf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author dnorthcott
 */
public class SelectionValidator implements Validator {

    /** Creates a new instance of PhoneNumberValidator */
    public SelectionValidator() {
    }

    /** phone number*/
    private static final String PHONE_NUM= "[0-9]{3}[-]{1}[0-9]{4}";

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException{
    	
    	FacesMessage message = new FacesMessage();
        message.setDetail("Value must be selected");
        message.setSummary("Value must be selected");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
    	if(value.equals("notselected") || value.equals(-1L))
    	   throw new ValidatorException(message);

    }
}
