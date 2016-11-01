package com.itelasoft.pso.dao;

import java.util.List;

import com.itelasoft.pso.beans.Location;

public interface ILocationDao extends IGenericDao<Location, Long> {

	public List<Location> listByName(String locationName);

	public Location searchByCode(String locationCode);

	public boolean validateLocationCode(Long locationId, String locationCode);

	public List<Location> listAvailableByEvent(Long eventId);

}
