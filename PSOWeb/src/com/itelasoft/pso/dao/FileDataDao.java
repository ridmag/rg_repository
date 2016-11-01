package com.itelasoft.pso.dao;

import org.hibernate.Hibernate;

import com.itelasoft.pso.beans.FileData;

/**
 * Dao class for FileData.
 */
public class FileDataDao extends GenericDao<FileData, Long> implements
		IFileDataDao {
	public FileData retrieveEager(Long id){
		FileData data = retrive(id);
		if(data.getBlobFileData() != null)
			Hibernate.initialize(data.getBlobFileData());
		return data;
	}
	
	@Override
	public FileData save(FileData o) {
		// TODO Auto-generated method stub
		//getCurrentSession().save(o.getBlobFileData());
		return super.save(o);
	}
}
