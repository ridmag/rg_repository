/**
 * 
 */
package com.itelasoft.pso.web;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author vajira
 * 
 */

@ManagedBean(name = "timeBean")
@SessionScoped
public class TimeBean {
	private int hours = -1;
	private int minutes = -1;
	private int session = -1;
	private boolean sessionFixedToPM = false;

	public static TimeBean valueOf(java.util.Date time){
		return new TimeBean();
	}
	public TimeBean() {

	}

	public TimeBean(Date date) {
		setDateTime(date);
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		if (hours != -1)
			this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getSession() {
		return session;
	}

	public void setSession(int session) {
		this.session = session;
	}

	public String getTimeAsString(){
		return String.format("%02d:%02d %s", hours,minutes,session==0?"AM":"PM");
	}
	public Date getDateTime(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (date != null)
			calendar.setTime(date);
		else
			calendar.setTimeInMillis(0);
		if (hours == 0) {
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.AM_PM, Calendar.AM);
		} else if (hours == 12) {
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.AM_PM, Calendar.PM);
		} else {
			calendar.set(Calendar.HOUR, hours);
			calendar.set(Calendar.AM_PM, session);
		}
		calendar.set(Calendar.MINUTE, minutes);

		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public void setDateTime(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			minutes = calendar.get(Calendar.MINUTE);
			if ((minutes % 5) > 2.5)
				minutes = ((minutes / 5) * 5) + 5;
			else
				minutes = (minutes / 5) * 5;
			session = calendar.get(Calendar.AM_PM);
			if (calendar.get(Calendar.HOUR) == 0
					&& calendar.get(Calendar.AM_PM) == Calendar.PM) {
				hours = 12;
			} else
				hours = calendar.get(Calendar.HOUR);
		} else {
			hours = minutes = session = -1;
			sessionFixedToPM = false;
		}
	}

	public void setSessionFixedToPM(boolean sessionFixedToPM) {
		this.sessionFixedToPM = sessionFixedToPM;
	}

	public boolean isSessionFixedToPM() {
		return sessionFixedToPM;
	}

	public boolean isValid() {
		if (hours == -1 || minutes == -1 || session == -1)
			return false;
		return true;
	}

}
