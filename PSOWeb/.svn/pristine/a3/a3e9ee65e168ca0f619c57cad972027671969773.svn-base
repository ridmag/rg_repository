package com.itelasoft.pso.beans;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "specialneeds")
public class SpecialNeed extends BaseEntity {
	private Long id;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String name;
	private String fileName;
	private FileData icon;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setIcon(FileData icon) {
		this.icon = icon;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "photoId")
	public FileData getIcon() {
		return icon;
	}

}
