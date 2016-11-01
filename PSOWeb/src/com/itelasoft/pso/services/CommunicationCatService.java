package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.CommunicationCat;
import com.itelasoft.pso.dao.ICommunicationCatDao;

public class CommunicationCatService extends
		GenericService<CommunicationCat, Long> implements
		ICommunicationCatService {

	private ICommunicationCatDao comCatDao;

	public List<CommunicationCat> getSubCategories(Long parentId) {
		return this.comCatDao.getSubCategories(parentId);
	}

	public List<CommunicationCat> getRootCategories() {
		return this.comCatDao.getRootCategories();
	}

	public ICommunicationCatDao getComCatDao() {
		return comCatDao;
	}

	public void setComCatDao(ICommunicationCatDao comCatDao) {
		this.comCatDao = comCatDao;
	}

}
