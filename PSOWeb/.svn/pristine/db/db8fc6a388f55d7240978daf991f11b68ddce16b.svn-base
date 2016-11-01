package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.NdisInvoice;
import com.itelasoft.pso.dao.INdisInvoiceDao;

public class NdisInvoiceService extends GenericService<NdisInvoice, Long> implements INdisInvoiceService {
	
	public List<NdisInvoice> retriveInvoiceByStartEndDate(Date startdate,Date enddate){
		return ((INdisInvoiceDao)dao).retriveInvoiceByStartEndDate(startdate, enddate);
	}
}
