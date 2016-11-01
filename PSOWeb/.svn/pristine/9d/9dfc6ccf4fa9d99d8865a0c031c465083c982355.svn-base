package com.itelasoft.sample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.beanfiles.translators.PatternTranslator;
import com.itelasoft.pso.beans.CheckRecord;
import com.itelasoft.pso.beans.StaffCheckRecord;
import com.itelasoft.pso.services.ServiceLocator;

public class StaffCheckTranslator extends PatternTranslator {

	private List<CheckRecord> checkRecords;

	public StaffCheckTranslator() {
		checkRecords = ServiceLocator.getServiceLocator()
				.getCheckRecordService().findAll();
	}

	@Override
	public Object read(Object i, Class<?> clazz) {
		List<?> list = (List<?>) i;
		ArrayList<StaffCheckRecord> arrayList = new ArrayList<StaffCheckRecord>();
		for (String key : this.matches.keySet()) {
			StaffCheckRecord staffCheckRecord = new StaffCheckRecord();
			for (CheckRecord checkRecord : checkRecords) {
				if (checkRecord.getName().equals(key)) {
					String value = (String) list.get(this.matches.get(key).get(
							0));
					if (value != null && !value.equals("n")) {
						try {
							staffCheckRecord
									.setDateCompleted(new SimpleDateFormat(
											"dd/MM/yyyy").parse(value));
							// staffCheckRecord.setChecked(true);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}// else
						// staffCheckRecord.setChecked(false);
					staffCheckRecord.setCheckRecord(checkRecord);
					arrayList.add(staffCheckRecord);
					break;
				}

			}
		}

		return arrayList;
	}
}