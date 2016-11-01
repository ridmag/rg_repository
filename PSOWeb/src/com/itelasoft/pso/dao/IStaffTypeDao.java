package com.itelasoft.pso.dao;

import com.itelasoft.pso.beans.StaffType;

public interface IStaffTypeDao extends IGenericDao<StaffType, Long> {

	StaffType findByName(String name);

}
