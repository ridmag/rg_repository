package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.Vehicle;

public interface IVehicleDao extends IGenericDao<Vehicle, Long> {

	public List<Vehicle> listByType(String vehicleType);

	public List<Vehicle> listActiveVehicles();

	public boolean isHavingActivePrograms(Long vehicleId);

	public boolean isHavingPendingEvents(Long vehicleId);

}
