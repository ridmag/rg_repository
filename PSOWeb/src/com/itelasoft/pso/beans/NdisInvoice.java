package com.itelasoft.pso.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ndisinvoice")
public class NdisInvoice extends BaseEntity {

	private Long Id;
	private AuthorisedStaff authorisedstaff;
	private String registrationCode;
	private Date startDay;
	private Date endDay;
	private Date GenerateDate;
	private String filePath;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	@ManyToOne
	@JoinColumn(name = "authorisedstaffid")
	public AuthorisedStaff getAuthorisedstaff() {
		return authorisedstaff;
	}

	public void setAuthorisedstaff(AuthorisedStaff authorisedstaff) {
		this.authorisedstaff = authorisedstaff;
	}

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public Date getStartDay() {
		return startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	public Date getEndDay() {
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public Date getGenerateDate() {
		return GenerateDate;
	}

	public void setGenerateDate(Date generateDate) {
		GenerateDate = generateDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}