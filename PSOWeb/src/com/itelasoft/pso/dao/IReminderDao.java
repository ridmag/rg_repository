package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.Reminder;

public interface IReminderDao extends IGenericDao<Reminder, Long> {

	public List<Reminder> listNewReminders();

	public List<Reminder> listCommByKeyPerson(Long staffId);

}
