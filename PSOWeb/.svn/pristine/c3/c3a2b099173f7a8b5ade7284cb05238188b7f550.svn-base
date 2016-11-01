package com.itelasoft.sample;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.beanfiles.translators.PatternTranslator;
import com.itelasoft.pso.beans.EnumItemCategory;
import com.itelasoft.pso.beans.EnumSkillLevel;
import com.itelasoft.pso.beans.ReferenceItem;
import com.itelasoft.pso.beans.StaffSkill;
import com.itelasoft.pso.services.ServiceLocator;

public class StaffSkillTranslator extends PatternTranslator {

	private List<ReferenceItem> skills;

	public StaffSkillTranslator() {
		skills = ServiceLocator.getServiceLocator().getReferenceItemService()
				.findItemsByCategory(EnumItemCategory.STAFF_SKILL, null);
	}

	@Override
	public Object read(Object i, Class<?> clazz) {
		@SuppressWarnings("rawtypes")
		List list = (List) i;
		// @SuppressWarnings("rawtypes")
		// List list2 = (List) list.get(0);
		ArrayList<StaffSkill> arrayList = new ArrayList<StaffSkill>();
		for (String key : this.matches.keySet()) {
			StaffSkill staffSkill = new StaffSkill();
			for (ReferenceItem skill : skills) {
				if (skill.getItemValue().equals(key)) {
					staffSkill.setSkill(skill);
					String value = (String) list.get(this.matches.get(key).get(
							0));
					if (value != null && !value.equals("n")) {
						staffSkill.setLevel(EnumSkillLevel
								.valueOf((String) list.get(this.matches
										.get(key).get(0))));
						arrayList.add(staffSkill);
					}

					break;
				}

			}
		}

		return arrayList;
	}
}