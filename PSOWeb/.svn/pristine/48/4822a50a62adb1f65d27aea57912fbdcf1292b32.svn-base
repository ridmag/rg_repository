package com.itelasoft.pso.web.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("truncateConverter")
public class TruncateConverter implements Converter {
	private int length = 3;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public TruncateConverter() {
	}

	public Object getAsObject(FacesContext facesContext,
			UIComponent uiComponent, String param) {
		return param;

	}

	public String getAsString(FacesContext facesContext,
			UIComponent uiComponent, Object obj) {
		String string = (String) obj;
		if (string != null && string.length() > length)
			return string.substring(0, length);
		return string;
	}
}