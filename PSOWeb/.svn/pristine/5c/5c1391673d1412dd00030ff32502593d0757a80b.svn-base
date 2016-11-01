package com.itelasoft.pso.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "checkrecords")
public class CheckRecord extends BaseEntity {

	private Long id;
	private String name;
	private Boolean needRemarks = false;
	private Boolean needCompleted = false;
	private Boolean needExpiry = false;
	private String remarks;
	private Date dateCompleted;
	private Date expiryDate;

	@Transient
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Transient
	public Date getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	@Transient
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

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

	public Boolean getNeedRemarks() {
		return needRemarks;
	}

	public void setNeedRemarks(Boolean needRemarks) {
		this.needRemarks = needRemarks;
	}

	public Boolean getNeedCompleted() {
		return needCompleted;
	}

	public void setNeedCompleted(Boolean needCompleted) {
		this.needCompleted = needCompleted;
	}

	public Boolean getNeedExpiry() {
		return needExpiry;
	}

	public void setNeedExpiry(Boolean needExpiry) {
		this.needExpiry = needExpiry;
	}

}
