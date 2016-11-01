package com.itelasoft.pso.web.jsf;

import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "dateConverter")
public class DateConverter extends DateTimeConverter implements
		Converter {

	public DateConverter() {
		this.setDateStyle("short");
		this.setTimeZone(java.util.TimeZone.getDefault());
		this.setPattern("dd/MM/yyyy");
	}
}