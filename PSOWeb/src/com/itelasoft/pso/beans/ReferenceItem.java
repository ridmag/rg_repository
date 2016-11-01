package com.itelasoft.pso.beans;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "referenceItems")
public class ReferenceItem extends BaseEntity {

	private Long id;
	private EnumItemCategory category;
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String itemValue;
	private String status;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setCategory(EnumItemCategory category) {
		this.category = category;
	}

	@Enumerated(EnumType.STRING)
	public EnumItemCategory getCategory() {
		return category;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
