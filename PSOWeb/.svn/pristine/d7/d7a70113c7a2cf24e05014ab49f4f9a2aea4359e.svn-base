package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import com.itelasoft.pso.beans.NdisPrice;

public class NdisPriceDao extends GenericDao<NdisPrice, Long> implements
		INdisPriceDao {

	@Override
	public List<NdisPrice> listNdisPrices(Long supportClusterId) {
		Session session = getCurrentSession();
		Query query = session
				.createQuery("from NdisPrice as np where np.supportItem.id = ? ");
		query.setParameter(0, supportClusterId);
		@SuppressWarnings("unchecked")
		List<NdisPrice> ndisPrice = query.list();
		return ndisPrice;
	}
/*
	@Override
	public boolean checkNdisPriceNo(Long num) {

		Session session = getCurrentSession();
		Query query = session
				.createQuery("from NdisPrice as np where np.itemNumber = ? ");
		query.setParameter(0, num);
		@SuppressWarnings("unchecked")
		List<NdisPrice> ndisPrice = query.list();
		if (ndisPrice.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}*/



}