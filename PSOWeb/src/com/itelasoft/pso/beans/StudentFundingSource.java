package com.itelasoft.pso.beans;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : PSO
//  @ Author : Tharindu
//
//

@Entity
@Table(name = "StudentFundingSources")
public class StudentFundingSource {
	private Long id;
	private FundingSource fundingSrc;
	private Student student;
	private Outlet outlet;
	private String fundingLevel;
	private Date fundingStartDate;
	private int fundingHours;
	private boolean active;

	// private long fundingHoursUsed;

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GenericGenerator(name = "mygen", strategy = "increment")
	@GeneratedValue(generator = "mygen")
	public Long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "fundingsrcid")
	public FundingSource getFundingSrc() {
		return fundingSrc;
	}

	public void setFundingSrc(FundingSource fundingSrc) {
		this.fundingSrc = fundingSrc;
	}

	@ManyToOne
	@JoinColumn(name = "studentId")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getFundingLevel() {
		return fundingLevel;
	}

	public void setFundingLevel(String fundingLevel) {
		this.fundingLevel = fundingLevel;
	}

	public Date getFundingStartDate() {
		return fundingStartDate;
	}

	public void setFundingStartDate(Date fundingStartDate) {
		this.fundingStartDate = fundingStartDate;
	}

	public void setFundingHours(int fundingHours) {
		this.fundingHours = fundingHours;
	}

	public int getFundingHours() {
		return fundingHours;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	@OneToOne
	@JoinColumn(name = "outletId")
	public Outlet getOutlet() {
		return outlet;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Transient
	public boolean isActive() {
		return active;
	}

}