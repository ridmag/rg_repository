package com.itelasoft.pso.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.EnumReminderType;
import com.itelasoft.pso.beans.Reminder;

@ManagedBean(name = "reminderManagerModel")
@SessionScoped
public class ReminderManagerModel extends UIModel {

	private List<Reminder> reminders;
	private Reminder reminder;
	private boolean visibleReminder;
	private SimpleDateFormat mydateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private int unreadCount;

	public ReminderManagerModel() {
		// refreshReminders();
	}

	public void refreshReminders() {
		// reminders = serviceLocator.getReminderService().listNewReminders();
		unreadCount = 0;
		reminders = serviceLocator.getReminderService().listCommByKeyPerson(
				sessionContext.getUser().getId());
		for (Reminder r : reminders) {
			if (r.getStatus().equals("New"))
				unreadCount++;
		}
	}

	public void selectReminder(ClickActionEvent event) {
		if (reminder != null)
			reminder.setUi_selected(false);
		reminder = (Reminder) event.getComponent().getAttributes()
				.get("reminder");
		reminder.setUi_selected(true);
	}

	public String openReminder() {
		if (reminder.getType().equals(EnumReminderType.COMMUNICATION)) {
			reminderPopup();
			CommunicationManagerModel model = (CommunicationManagerModel) Util
					.getManagedBean("communicationManagerModel");
			model.init();
			model.openReminder(reminder);
			return "communication";
		}
		return null;
	}

	public String getHighlight(Date reminderDate, String status) {
		if (mydateFormat.format(reminderDate).equals(
				mydateFormat.format(new Date()))) {
			if (status.equals("New"))
				return "spclNewRow";
			else
				return "spclOldRow";
		}
		if (status.equals("New"))
			return "newRow";
		else
			return "oldRow";
	}

	public void reminderPopup() {
		if (visibleReminder)
			visibleReminder = false;
		else {
			if (reminder != null) {
				reminder.setUi_selected(false);
				reminder = null;
			}
			visibleReminder = true;
		}
	}

	public List<Reminder> getReminders() {
		// refreshReminders();
		return reminders;
	}

	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}

	public Reminder getReminder() {
		return reminder;
	}

	public boolean isVisibleReminder() {
		return visibleReminder;
	}

	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}

	public int getUnreadCount() {
		return unreadCount;
	}

}
