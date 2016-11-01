package com.itelasoft.sample;

import org.apache.commons.beanutils.converters.AbstractConverter;

import com.itelasoft.pso.beans.StaffType;
import com.itelasoft.pso.services.ServiceLocator;

public class StaffTypeConvertor extends AbstractConverter {

	@Override
	protected Object convertToType(Class arg0, Object arg1) throws Throwable {
		// TODO Auto-generated method stub
		StaffType staffType = ServiceLocator.getServiceLocator().getStaffTypeService().getStaffType((String) arg1);
		return staffType;
	}

	@Override
	protected Class getDefaultType() {
		// TODO Auto-generated method stub
		return StaffType.class;
	}

}
