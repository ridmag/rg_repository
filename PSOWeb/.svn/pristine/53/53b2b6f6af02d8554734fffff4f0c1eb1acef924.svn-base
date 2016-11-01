package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.Program;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.Vehicle;

public class VehicleDao extends GenericDao<Vehicle, Long> implements
		IVehicleDao {

	public List<Vehicle> listByType(String vehicleType) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Vehicle.class);
		if (vehicleType != null && !vehicleType.isEmpty()
				&& !vehicleType.equals("All"))
			criteria.add(Restrictions.eq("type", vehicleType));
		@SuppressWarnings("unchecked")
		List<Vehicle> list = criteria.addOrder(Order.asc("id")).list();
		return list;
	}

	public List<Vehicle> listActiveVehicles() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Vehicle.class).add(
				Restrictions.eq("status", "Active"));
		@SuppressWarnings("unchecked")
		List<Vehicle> vehicles = criteria.list();
		return vehicles;
	}

	public boolean isHavingActivePrograms(Long vehicleId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Program.class)
				.add(Restrictions.eq("status", "Active"))
				.createCriteria("vehicle")
				.add(Restrictions.eq("id", vehicleId));
		@SuppressWarnings("unchecked")
		List<Program> pros = criteria.list();
		if (pros != null && !pros.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean isHavingPendingEvents(Long vehicleId) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ProgramEvent.class)
				.add(Restrictions.ne("status", "finished"))
				.createCriteria("vehicle")
				.add(Restrictions.eq("id", vehicleId));
		@SuppressWarnings("unchecked")
		List<ProgramEvent> pros = criteria.list();
		if (pros != null && !pros.isEmpty()) {
			return true;
		}
		return false;
	}

}
