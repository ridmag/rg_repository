package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Vehicle;

public interface IVehicleService extends IGenericService<Vehicle, Long> {

	public List<Vehicle> listByType(String vehicleType);

	public List<Vehicle> listActiveVehicles();

	public boolean isHavingActivePrograms(Long vehicleId);

	public boolean isHavingPendingEvents(Long vehicleId);

	public Vehicle retrieveEager(Long id);

}
