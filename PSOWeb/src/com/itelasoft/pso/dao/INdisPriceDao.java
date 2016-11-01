package com.itelasoft.pso.dao;

import java.util.List;
import com.itelasoft.pso.beans.NdisPrice;

public interface INdisPriceDao extends IGenericDao<NdisPrice, Long> {

	public List<NdisPrice> listNdisPrices(Long supportClusterId);

	// public boolean checkNdisPriceNo(Long num);

}
