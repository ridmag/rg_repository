package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Reminder;
import com.itelasoft.pso.dao.IReminderDao;

public class ReminderService extends GenericService<Reminder, Long> implements
		IReminderService {

	public List<Reminder> listNewReminders() {
		return ((IReminderDao) dao).listNewReminders();
	}

	public List<Reminder> listCommByKeyPerson(Long staffId) {
		return ((IReminderDao) dao).listCommByKeyPerson(staffId);
	}

}
