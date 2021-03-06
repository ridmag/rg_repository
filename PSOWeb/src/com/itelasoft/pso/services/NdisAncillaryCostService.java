package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.NdisAncillaryCost;
import com.itelasoft.pso.dao.INdisAncillaryCostDao;

public class NdisAncillaryCostService extends GenericService<NdisAncillaryCost, Long>
		implements INdisAncillaryCostService {
	private INdisAncillaryCostDao ndisAncillaryCostDao;

	public INdisAncillaryCostDao getNdisAncillaryCostDao() {
		return ndisAncillaryCostDao;
	}

	public void setNdisAncillaryCostDao(INdisAncillaryCostDao ndisAncillaryCostDao) {
		this.ndisAncillaryCostDao = ndisAncillaryCostDao;
	}

	@Override
	public List<NdisAncillaryCost> listNdisAncyCostByStudent(Long id) {
		return ndisAncillaryCostDao.listNdisAncyCostByStudent(id);
	}
	
	public List<NdisAncillaryCost> getAncillaryCostOfInvoiceByStudentId(Long invoiceId,Long studentId){
		return ndisAncillaryCostDao.getAncillaryCostOfInvoiceByStudentId(invoiceId, studentId);
	}
	
	public Double getTotalAncProgramFund(Long studentId) {
		return ndisAncillaryCostDao.getTotalAncProgramFund(studentId);
	}
}
