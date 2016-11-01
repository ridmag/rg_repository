package com.itelasoft.pso.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.NdisCommittedEvent;
import com.itelasoft.pso.beans.NdisPrice;
import com.itelasoft.pso.beans.NdisSupportItem;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.dao.IHolidayDao;
import com.itelasoft.pso.dao.INdisCommittedEventDao;
import com.itelasoft.pso.dao.INdisSupportItemDao;

public class NdisCommittedEventService extends GenericService<NdisCommittedEvent, Long>
		implements INdisCommittedEventService {

	private IHolidayDao holidayDao;
	private INdisSupportItemDao ndisSupportItemDao;

	public void setHolidayDao(IHolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}

	public void setNdisSupportItemDao(INdisSupportItemDao ndisSupportItemDao) {
		this.ndisSupportItemDao = ndisSupportItemDao;
	}

	@Override
	public int deleteCommittedEvents(long studentId, Date startDate, Date endDate) {
		return ((INdisCommittedEventDao) dao).deleteCommittedEvents(studentId, startDate, endDate);
	}

	public List<NdisCommittedEvent> getCommittedEvents(Long studentID, Long groupId, Date eventDate, Date endDate) {
		return ((INdisCommittedEventDao) dao).getCommittedEvents(studentID, groupId, eventDate, endDate);
	}

	public void saveAllEvents(List<NdisCommittedEvent> value) {
		for (NdisCommittedEvent eve : value) {
			create(eve);
		}
	}

	public int deleteSelectedEvents(Long studentId, Long groupId, Date fromDate, Date toDate) {
		return ((INdisCommittedEventDao) dao).deleteSelectedEvents(studentId, groupId, fromDate, toDate);
	}

	@Override
	public List<NdisCommittedEvent> ndisCommittedEventsListByGroup(Long groupId) {
		return ((INdisCommittedEventDao) dao).ndisCommittedEventsListByGroup(groupId);
	}

	public List<NdisCommittedEvent> retrieveClaimedCommittedEvents(Long studentID, Long groupId, Date eventDate,
			Date endDate) {
		return ((INdisCommittedEventDao) dao).retrieveClaimedCommittedEvents(studentID, groupId, eventDate, endDate);
	}

	@Override
	public int deleteSelectedStudentEvents(Long studentId, Date fromDate, Long studentGroupid) {
		return ((INdisCommittedEventDao) dao).deleteSelectedStudentEvents(studentId, fromDate, studentGroupid);
	}

	@Override
	public List<NdisCommittedEvent> SelectedStudentEvents(Long studentId, Date fromDate, Long studentGroupid) {
		return ((INdisCommittedEventDao) dao).SelectedStudentEvents(studentId, fromDate, studentGroupid);
	}

	public List<NdisCommittedEvent> allCommittedAmountForStudent(Long studentID, ProgramType proType) {
		return ((INdisCommittedEventDao) dao).allCommittedAmountForStudent(studentID, proType);
	}

	public List<NdisCommittedEvent> getCommitedEventsOfInvoiceByStudentId(Long invoiceId,Long studentId){
		return ((INdisCommittedEventDao)dao).getCommitedEventsOfInvoiceByStudentId(invoiceId, studentId);
	}
	
	public void calculateCommittedEventPrice(List<NdisCommittedEvent> committedEvents, boolean save) {
		long timeLong12 = 0, timeLong1 = 0, timeLong6 = 0, timeLong8 = 0, timeLong1159 = 0;
		DateFormat formattertime = new SimpleDateFormat("hh:mm:ss a");
		try {
			String timeString12 = "12:00:00 AM";
			Date timeDate12 = formattertime.parse(timeString12);
			timeLong12 = timeDate12.getTime();

			String timeString1 = "01:00:00 AM";
			Date timeDate1 = formattertime.parse(timeString1);
			timeLong1 = timeDate1.getTime();

			String timeString6 = "06:00:00 AM";
			Date timeDate6 = formattertime.parse(timeString6);
			timeLong6 = timeDate6.getTime();

			String timeString8 = "08:00:00 PM";
			Date timeDate8 = formattertime.parse(timeString8);
			timeLong8 = timeDate8.getTime();

			String timeString1159 = "11:59:00 PM";
			Date timeDate1159 = formattertime.parse(timeString1159);
			timeLong1159 = timeDate1159.getTime();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		for (NdisCommittedEvent committedEvent : committedEvents) {
			String ndisTime = "";
			List<NdisPrice> ndisprices = new ArrayList<NdisPrice>();
			NdisPrice selectedNdisPrice = null;
			NdisSupportItem supportCluster = ndisSupportItemDao.retrive(committedEvent.getClusterOverride() == null
					? committedEvent.getNdisSupportCluster().getId() : committedEvent.getClusterOverride().getId());
			if (!committedEvent.getStudentGroup().getProgram().getType().getName().equals("Transport")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(committedEvent.getEventDate());
				int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
				Date endTime = committedEvent.getEndTime();
				DateFormat time = new SimpleDateFormat("hh:mm:ss a");
				String endTimeString = time.format(endTime);
				DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
				Date endTime2 = null;
				try {
					endTime2 = formatter.parse(endTimeString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Long currentEndTimeLong = endTime2.getTime();
				Calendar calendr = Calendar.getInstance();
				calendr.setTime(committedEvent.getEventDate());
				if (holidayDao.isHoliday(new Long(1), committedEvent.getEventDate())) {
					ndisTime = "Public Holiday";
				} else if (dayOfWeek == 1) {
					ndisTime = "Sunday";
				} else if (dayOfWeek == 7) {
					ndisTime = "Saturday";
				} else if (timeLong12 <= currentEndTimeLong && currentEndTimeLong <= timeLong1) {
					ndisTime = "Weekday Overnight Active";
				} else if (timeLong1 < currentEndTimeLong && currentEndTimeLong < timeLong6) {
					ndisTime = "Weekday Overnight Active";
				} else if (timeLong6 <= currentEndTimeLong && currentEndTimeLong <= timeLong8) {
					ndisTime = "Weekday Daytime";
				} else if (timeLong8 < currentEndTimeLong && currentEndTimeLong <= timeLong1159
						&& committedEvent.getStudentGroup().isInactiveovernight()) {
					ndisTime = "Overnight Inactive";
				} else if (timeLong8 < currentEndTimeLong && currentEndTimeLong <= timeLong1159
						&& !committedEvent.getStudentGroup().isInactiveovernight()) {
					ndisTime = "Weekday Evening";
				}
			} else {
				ndisTime = "Ancillary";
			}
			for (NdisPrice price : supportCluster.getNdisPrice()) {
				if (price.getNdisTime().equals(ndisTime)) {
					ndisprices.add(price);
				}
			}
			if (!ndisprices.isEmpty()) {
				long minDiff = -1, eventDate = committedEvent.getEventDate().getTime();
				for (NdisPrice ndisprice : ndisprices) {
					long diff = Math.abs(eventDate - ndisprice.getStartDate().getTime());
					if ((minDiff == -1) || (diff < minDiff)) {
						minDiff = diff;
						selectedNdisPrice = ndisprice;
					}
				}
				if (selectedNdisPrice != null) {
					committedEvent.setEventPrice(selectedNdisPrice.getPrice() * (ndisTime.equals("Ancillary")
							? committedEvent.getKmsPerDay() : committedEvent.getHours()));
					committedEvent.setPrice(selectedNdisPrice);
				} else {
					continue;
				}
				if (save) {
					if (committedEvent.getId() == null)
						create(committedEvent);
					else
						update(committedEvent);
				}
			} else {
				continue;
			}
		}
	}

}
