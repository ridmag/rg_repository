package com.itelasoft.pso.web.jsf;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;

public class TimeDateConverter extends DateTimeConverter {

	private int minutes=0;
	private int seconds=0;
	private int hours=0;
	
	public TimeDateConverter() {
		super();
		setTimeZone(TimeZone.getDefault());
		// here you can set your custom date pattern for your project
		// setPattern("M/d/yy");
	}
	
	
	
	/* (non-Javadoc)
	 * @see javax.faces.convert.DateTimeConverter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		Date date = (Date) super.getAsObject(arg0, arg1, arg2);
		if(date != null)
			return get12Noon(date);
		else
			return date;
		
	}
	private Date get12Noon(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		//gregorianCalendar.setTimeZone(getTimeZone());
		gregorianCalendar.setTime(date);
		gregorianCalendar.set(GregorianCalendar.HOUR_OF_DAY, hours);
		gregorianCalendar.set(GregorianCalendar.MINUTE, minutes);
		gregorianCalendar.set(GregorianCalendar.SECOND, seconds);
		gregorianCalendar.set(GregorianCalendar.MILLISECOND, 00);
		return gregorianCalendar.getTime();
	}



	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}



	public int getMinutes() {
		return minutes;
	}



	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}



	public int getSeconds() {
		return seconds;
	}



	public void setHours(int hours) {
		this.hours = hours;
	}



	public int getHours() {
		return hours;
	}

}