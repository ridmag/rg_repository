package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.Location;

public class LocationDao extends GenericDao<Location, Long> implements
		ILocationDao {

	public List<Location> listByName(String locationName) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Location.class).add(
				Restrictions.ne("name", "Home"));
		if (locationName != null && !locationName.isEmpty())
			criteria.add(Restrictions.like("name", locationName,
					MatchMode.ANYWHERE).ignoreCase()).addOrder(Order.asc("name"));
		@SuppressWarnings("unchecked")
		List<Location> locations = criteria.addOrder(Order.asc("name")).list();
		return locations;
	}

	public Location searchByCode(String locationCode) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Location.class).add(
				Restrictions.eq("locationCode", locationCode).ignoreCase());
		Location location = (Location) criteria.uniqueResult();
		return location;
	}

	public boolean validateLocationCode(Long locationId, String locationCode) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Location.class).add(
				Restrictions.eq("locationCode", locationCode).ignoreCase());
		if (locationId != null)
			criteria.add(Restrictions.ne("id", locationId));
		Location location = (Location) criteria.uniqueResult();
		if (location == null) {
			return true;
		} else {
			return false;
		}
	}

	public List<Location> listAvailableByEvent(Long eventId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("select loc from Location as loc, ProgramEvent as event where event.id = ? and loc not in(event.location)");
		query.setParameter(0, eventId);
		@SuppressWarnings("unchecked")
		List<Location> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

}
