package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.NdisPrice;
import com.itelasoft.pso.dao.INdisPriceDao;

public class NdisPriceService extends GenericService<NdisPrice, Long> implements
		INdisPriceService {

	@Override
	public List<NdisPrice> listNdisPrices(Long supportClusterId) {
		return ((INdisPriceDao)dao).listNdisPrices(supportClusterId);
	}

	/*@Override
	public boolean checkNdisPriceNo(Long num) {
		return ndisPriceDao.checkNdisPriceNo(num);
	}*/
}