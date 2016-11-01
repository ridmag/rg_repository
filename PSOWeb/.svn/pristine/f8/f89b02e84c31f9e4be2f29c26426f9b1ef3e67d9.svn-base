package com.itelasoft.pso.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reminders")
public class Reminder extends BaseEntity {

	private Long id;
	private Long referenceId;
	private Date createdDate;
	private Date remindOn;
	private EnumReminderType type;
	private String note;
	private int no_of_reminders;
	private String status;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getRemindOn() {
		return remindOn;
	}

	public void setRemindOn(Date remindOn) {
		this.remindOn = remindOn;
	}

	@Enumerated(EnumType.STRING)
	public EnumReminderType getType() {
		return type;
	}

	public void setType(EnumReminderType type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getNo_of_reminders() {
		return no_of_reminders;
	}

	public void setNo_of_reminders(int no_of_reminders) {
		this.no_of_reminders = no_of_reminders;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
