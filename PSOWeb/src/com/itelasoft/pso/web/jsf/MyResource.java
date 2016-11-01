package com.itelasoft.pso.web.jsf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import javax.faces.context.ExternalContext;

import com.icesoft.faces.context.Resource;
import com.itelasoft.pso.beans.FileData;

public class MyResource implements Resource, Serializable{
    private String resourceName;
    private InputStream inputStream;
    private final Date lastModified;
    private ExternalContext extContext;
    private FileData fileData;
    /**
	 * 
	 * @hibernate.property column = "fileData"
	 */
	public FileData getFileData() {
		return fileData;
	}

	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(FileData fileData) {
		this.fileData = fileData;
	}

	public MyResource(ExternalContext ec, String resourceName) {
        this.extContext = ec;
        this.resourceName = resourceName;
        this.lastModified = new Date();        
    }
    
    public MyResource(FileData fileData) {
           this.fileData = fileData;
           lastModified = new Date();
    }
    /**
     * This intermediate step of reading in the files from the JAR, into a
     * byte array, and then serving the Resource from the ByteArrayInputStream,
     * is not strictly necessary, but serves to illustrate that the Resource
     * content need not come from an actual file, but can come from any source,
     * and also be dynamically generated. In most cases, applications need not
     * provide their own concrete implementations of Resource, but can instead
     * simply make use of com.icesoft.faces.context.ByteArrayResource,
     * com.icesoft.faces.context.FileResource, com.icesoft.faces.context.JarResource.
     */
    public InputStream open() throws IOException {
        if (inputStream == null) {
            //InputStream stream = extContext.getResourceAsStream(OutputResourceBean.RESOURCE_PATH + resourceName);
            byte[] byteArray = fileData.getBlobFileData().getData();
            inputStream = new ByteArrayInputStream(byteArray);
        }
        return inputStream;
    }
    
    public String calculateDigest() {
        return resourceName;
    }

    public Date lastModified() {
        return lastModified;
    }

    public void withOptions(Options arg0) throws IOException {
    }

	
}   

