package com.itelasoft.pso.dao;

import java.util.List;
import com.itelasoft.pso.beans.NdisAncillaryCost;

public interface INdisAncillaryCostDao extends IGenericDao<NdisAncillaryCost, Long> {

	public List<NdisAncillaryCost> listNdisAncyCostByStudent(Long id);
	
	public List<NdisAncillaryCost> getAncillaryCostOfInvoiceByStudentId(Long invoiceId,Long studentId);
	
	public Double getTotalAncProgramFund(Long studentId);
}
