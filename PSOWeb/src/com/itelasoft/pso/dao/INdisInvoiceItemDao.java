package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.NdisCommittedEvent;
import com.itelasoft.pso.beans.NdisInvoiceItem;
import com.itelasoft.pso.beans.NdisAncillaryCost;

public interface INdisInvoiceItemDao extends IGenericDao<NdisInvoiceItem, Long> {

	public NdisInvoiceItem getInvoice(String name);

	public List<NdisCommittedEvent> retrieveInvoiceItem(Date startDate,
			Date endDate, Long studentId);
	
	public List<NdisCommittedEvent> retriveCommitedEventsByInvoiceId(Long invoiceId);
	
	public List<NdisAncillaryCost> retrieveNdisAncillaryCostItems(Date startDate,
			Date endDate, Long studentId,Boolean claimed);
	
	public List<NdisAncillaryCost> allretrieveNdisAncillaryCostItems(Long studentId);
	
	public List<NdisInvoiceItem> retriveInvoiceItemsByInvoiceId(Long invoiceId);
	
	public List<NdisAncillaryCost> retriveNdisAncillaryCostByInvoiceId(Long invoiceId);
}