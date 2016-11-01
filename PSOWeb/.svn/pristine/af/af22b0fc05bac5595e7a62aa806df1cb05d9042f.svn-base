package com.itelasoft.pso.web.jsf;

import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "timeConverter")
public class TimeConverter extends DateTimeConverter implements Converter {

	public TimeConverter() {
		this.setTimeStyle("short");
		this.setTimeZone(java.util.TimeZone.getDefault());
		this.setPattern("hh:mm a");
	}
}