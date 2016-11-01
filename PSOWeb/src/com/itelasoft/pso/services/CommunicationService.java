package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Communication;
import com.itelasoft.pso.beans.CommunicationCat;
import com.itelasoft.pso.beans.CommunicationNote;
import com.itelasoft.pso.beans.ServiceArea;
import com.itelasoft.pso.dao.ICommunicationDao;

public class CommunicationService extends GenericService<Communication, Long>
		implements ICommunicationService {

	private ICommunicationDao comDao;

	public void setComDao(ICommunicationDao comDao) {
		this.comDao = comDao;
	}

	public ICommunicationDao getComDao() {
		return comDao;
	}

	public List<Communication> listByCategory(Long catId) {
		return this.comDao.listByCategory(catId);
	}

	public List<Communication> listBySubCategories(List<Long> subCatIds) {
		return this.comDao.listBySubCategories(subCatIds);
	}

	public List<Communication> listByStudents(Long categoryId) {
		return this.comDao.listByStudents(categoryId);
	}

	public List<Communication> listByStaffType_Status_Category(
			Long staffTypeId, String staffStatus, CommunicationCat category) {
		return this.comDao.listByStaffType_Status_Category(staffTypeId,
				staffStatus, category);
	}

	public List<CommunicationNote> listNotesByCommunication(Long communicationId) {
		return this.comDao.listNotesByCommunication(communicationId);
	}

	public List<Communication> listByCategoryNServiceArea(
			CommunicationCat category, ServiceArea area) {
		return this.comDao.listByCategoryNServiceArea(category, area);
	}

	public List<Communication> listByVehicleType(String vehicleType) {
		return comDao.listByVehicleType(vehicleType);
	}
}
