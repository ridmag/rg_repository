package com.itelasoft.pso.services;

import java.util.List;

import org.hibernate.Hibernate;

import com.itelasoft.pso.beans.Vehicle;
import com.itelasoft.pso.dao.IVehicleDao;

public class VehicleService extends GenericService<Vehicle, Long> implements
		IVehicleService {

	public IVehicleDao vehicleDao;

	public List<Vehicle> listByType(String vehicleType) {
		return vehicleDao.listByType(vehicleType);
	}

	public void setVehicleDao(IVehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	public List<Vehicle> listActiveVehicles() {
		return this.vehicleDao.listActiveVehicles();
	}

	public boolean isHavingActivePrograms(Long vehicleId) {
		return vehicleDao.isHavingActivePrograms(vehicleId);
	}

	public boolean isHavingPendingEvents(Long vehicleId) {
		return vehicleDao.isHavingPendingEvents(vehicleId);
	}

	@Override
	public Vehicle retrieveEager(Long id) {
		// TODO Auto-generated method stub
		Vehicle vehicle = super.retrieve(id);
		if(vehicle.getPhoto() != null)
			Hibernate.initialize(vehicle.getPhoto().getBlobFileData());
		return vehicle;
	}
}
