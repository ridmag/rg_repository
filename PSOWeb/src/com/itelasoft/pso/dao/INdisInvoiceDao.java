package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.NdisInvoice;

public interface INdisInvoiceDao extends IGenericDao<NdisInvoice, Long>{
	public List<NdisInvoice> retriveInvoiceByStartEndDate(Date startdate,Date enddate);
}
