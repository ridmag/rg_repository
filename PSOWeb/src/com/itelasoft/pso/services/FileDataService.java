package com.itelasoft.pso.services;

import com.itelasoft.pso.beans.FileData;
import com.itelasoft.pso.dao.IFileDataDao;

public class FileDataService extends GenericService<FileData, Long> implements
		IFileDataService {
	public FileData retrieveEager(Long id) {
		FileData data = ((IFileDataDao)dao).retrieveEager(id);
		return data;
	}

}
