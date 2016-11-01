package com.itelasoft.pso.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.imageio.ImageIO;

import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentEvent;

@ManagedBean(name = "studentViewModel")
@SessionScoped
public class Student360ViewModel extends UIModel {

	private Student student;
	private Calendar calendar;
	private String timePeriod;
	private Date selectedDate;
	private List<StudentEvent> scheduledEvents;
	private int photoH, photoW;

	public void init(Long studentId) {
		if (studentId != null) {
			student = serviceLocator.getStudentService().retrieveEager(studentId);
			timePeriod = "1W";
			calendar = GregorianCalendar.getInstance();
			selectedDate = calendar.getTime();
			initPhotoImage();
			searchEvents();
		}
	}

	public void initPhotoImage() {
		byte[] tmpData = null;
		if (student.getPhoto() != null) {
			tmpData = student.getPhoto().getBlobFileData().getData();
			if (tmpData != null) {
				BufferedImage loadImg;
				try {
					loadImg = ImageIO.read(new ByteArrayInputStream(tmpData));
					int w = loadImg.getWidth();
					int h = loadImg.getHeight();
					if (w > h) {
						photoW = 230;
						photoH = photoW * h / w;
					} else {
						photoH = 180;
						photoW = photoH * w / h;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Date getFromDate() {
		calendar.setTime(selectedDate);
		if (timePeriod.equals("1W")) {
			calendar.add(Calendar.DATE, -7);
		} else if (timePeriod.equals("2W")) {
			calendar.add(Calendar.DATE, -14);
		} else if (timePeriod.equals("1M")) {
			calendar.add(Calendar.MONTH, -1);
		} else if (timePeriod.equals("3M")) {
			calendar.add(Calendar.MONTH, -3);
		} else if (timePeriod.equals("6M")) {
			calendar.add(Calendar.MONTH, -6);
		}
		return calendar.getTime();
	}

	public void searchEvents() {
		if (student != null) {
			scheduledEvents = serviceLocator.getStudentEventService()
					.listByCriteria(null, getFromDate(), selectedDate,
							student.getId());
			// scheduledEvents = serviceLocator.getProgramEventService()
			// .listByCriteria(null, null, null, fromDate, toDate, null,
			// student.getId());
			if (scheduledEvents == null || scheduledEvents.isEmpty())
				showError("No events found for the given period.");
		} else
			showError("Invalid " + Util.getMessage("student_label").toLowerCase() + " selected.");
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return student;
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public List<StudentEvent> getScheduledEvents() {
		return scheduledEvents;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public int getPhotoH() {
		return photoH;
	}

	public int getPhotoW() {
		return photoW;
	}

}
