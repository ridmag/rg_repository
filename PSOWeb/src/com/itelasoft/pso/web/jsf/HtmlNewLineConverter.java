package com.itelasoft.pso.web.jsf;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class HtmlNewLineConverter implements Converter {
	
	
	public HtmlNewLineConverter() {
	}

	public Object getAsObject(FacesContext facesContext,
			UIComponent uiComponent, String param) {

		return param;
	}

	public String getAsString(FacesContext facesContext,
			UIComponent uiComponent, Object obj) {
		try {
			if (obj == null)
				return null;
			String strObj = (String) obj;

			return strObj.replaceAll("\n", "<br/>");
		} catch (Exception exception) {
			throw new ConverterException(exception);

		}
	}

	
}