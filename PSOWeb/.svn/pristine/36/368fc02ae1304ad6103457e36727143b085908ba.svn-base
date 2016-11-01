package com.itelasoft.sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.beanfiles.translators.PropertyTranslator;
import com.itelasoft.pso.beans.EnumItemCategory;
import com.itelasoft.pso.beans.ReferenceItem;
import com.itelasoft.pso.beans.StaffCheckRecord;
import com.itelasoft.pso.services.ServiceLocator;

public class StaffTypeTranslator extends PropertyTranslator {

	private List<ReferenceItem> skills;

	public StaffTypeTranslator() {
		skills = ServiceLocator.getServiceLocator().getReferenceItemService()
				.findItemsByCategory(EnumItemCategory.STAFF_SKILL, null);
	}

	@Override
	public Object read(Object i, Class<?> clazz) {
		List<Object> list = Arrays.asList(i);
		ArrayList<StaffCheckRecord> arrayList = new ArrayList<StaffCheckRecord>();
		return null;
	}

	@Override
	public Object write(Object t) {
		// TODO Auto-generated method stub
		return null;
	}
}