package com.itelasoft.pso.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : pso
//  @ Author : Tharindu
//
//

@Entity
@Table(name = "blobdata")
public class BlobFileData {

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}

	/*
	 * @OneToOne(mappedBy="blobFileData") public FileData getFileData() { return
	 * fileData; }
	 */
	private byte[] data;
	private byte[] thumbnail;
}
