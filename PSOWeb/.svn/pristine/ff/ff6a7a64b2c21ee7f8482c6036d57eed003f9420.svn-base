package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Location;
import com.itelasoft.pso.dao.ILocationDao;

public class LocationService extends GenericService<Location, Long> implements
		ILocationService {

	public ILocationDao locationDao;

	public List<Location> listByName(String locationName) {
		return locationDao.listByName(locationName);
	}

	public Location searchByCode(String locationCode) {
		return locationDao.searchByCode(locationCode);
	}

	public boolean validateLocationCode(Long locationId, String locationCode) {
		return locationDao.validateLocationCode(locationId, locationCode);
	}

	public void setLocationDao(ILocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public List<Location> listAvailableByEvent(Long eventId) {
		return this.locationDao.listAvailableByEvent(eventId);
	}

}
