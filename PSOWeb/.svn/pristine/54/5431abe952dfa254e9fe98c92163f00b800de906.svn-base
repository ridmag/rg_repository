package com.itelasoft.pso.services.test;

import java.util.GregorianCalendar;

import com.itelasoft.pso.beans.BlobFileData;
import com.itelasoft.pso.beans.FileData;
import com.itelasoft.pso.services.IFileDataService;
import com.itelasoft.pso.services.ServiceLocator;

import junit.framework.TestCase;

public class IFileDataServiceTest extends TestCase {

	IFileDataService fileDataService;
	
	@Override
	protected void setUp() throws Exception {
		fileDataService = ServiceLocator.getServiceLocator().getFileDataService();
		super.setUp();
	}
	
	public void testCreate() {
		String str = "hello hello hello hello hello hello hello hello hello ";
		
		FileData data = new FileData();
		data.getBlobFileData().setData(str.getBytes());
		data.getBlobFileData().setThumbnail(str.getBytes());
		data.setStudentId(1L);
		data.setFileName("my file name");
		data.setDateCreated(GregorianCalendar.getInstance().getTime());
		data = fileDataService.create(data);
		
		FileData data2 = fileDataService.retrieve(data.getId());
		
		String str2 = new String(data2.getBlobFileData().getData());
		assertEquals(str, str2);
	}

	public void testCreateEager() {
		String str = "hello hello hello hello hello hello hello hello hello ";
		
		FileData data = new FileData();
		
		//data.setStudentId(1L);
		data.setFileName("my file name");
		data.setDateCreated(GregorianCalendar.getInstance().getTime());
		
		BlobFileData blobFileData = new BlobFileData();
		//blobFileData.setFileData(data);
		//blobFileData.setId(1L);
		data.setBlobFileData(blobFileData);
		data.getBlobFileData().setData(str.getBytes());
		data.getBlobFileData().setThumbnail(str.getBytes());
		//fileDataService.update(data);
		data = fileDataService.create(data);
		FileData data2 = fileDataService.retrieveEager(data.getId());
		
		String str2 = new String(data2.getBlobFileData().getData());
		assertEquals(str, str2);
	}

	

}
