package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.NdisAncillaryCost;

public interface INdisAncillaryCostService extends
		IGenericService<NdisAncillaryCost, Long> {
	
	List<NdisAncillaryCost> listNdisAncyCostByStudent(Long id);
	
	public List<NdisAncillaryCost> getAncillaryCostOfInvoiceByStudentId(Long invoiceId,Long studentId);
	
	public Double getTotalAncProgramFund(Long studentId);
}
