package com.itelasoft.pso.services;

import java.util.List;

import com.itelasoft.pso.beans.Collection;
import com.itelasoft.pso.dao.ICollectionDao;

public class CollectionService extends GenericService<Collection, Long>
		implements ICollectionService {
	public List<Collection> searchByName(String collectionName) {
		return ((ICollectionDao) dao).searchByName(collectionName);
	}

	public List<Collection> listActiveCollections() {
		return ((ICollectionDao) dao).listActiveCollections();
	}
}
