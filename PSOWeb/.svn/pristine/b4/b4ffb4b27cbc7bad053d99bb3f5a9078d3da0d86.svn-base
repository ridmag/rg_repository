package com.itelasoft.pso.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "HoursUtilizedReportItem")
public class HoursUtilizedReportItem extends BaseEntity {

	private Long id;
	private Student student;
	private String fundingType;
	private double totalMinutes;
	private double programMinsUsed;
	private double transportMinsUsed;
	private double balanceMins;
	private String remarks;

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "studentId")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getFundingType() {
		return fundingType;
	}

	public void setFundingType(String fundingType) {
		this.fundingType = fundingType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public double getTotalMinutes() {
		return totalMinutes;
	}

	@Transient
	public String getTotalHours() {
		double totalMinutes = getTotalMinutes();
		return convertTime(totalMinutes);
	}

	public void setTotalMinutes(double totalMinutes) {
		this.totalMinutes = totalMinutes;
	}

	public double getProgramMinsUsed() {
		return programMinsUsed;
	}

	@Transient
	public String getProgramHrsUsed() {
		double programMinsUsed = getProgramMinsUsed();
		return convertTime(programMinsUsed);
	}

	public void setProgramMinsUsed(double programMinsUsed) {
		this.programMinsUsed = programMinsUsed;
	}

	public double getTransportMinsUsed() {
		return transportMinsUsed;
	}

	@Transient
	public String getTransportHrsUsed() {
		double transportMinsUsed = getTransportMinsUsed();
		return convertTime(transportMinsUsed);
	}

	public void setTransportMinsUsed(double transportMinsUsed) {
		this.transportMinsUsed = transportMinsUsed;
	}

	public double getBalanceMins() {
		return balanceMins;
	}

	@Transient
	public String getBalanceHrs() {
		double balanceMins = getBalanceMins();
		return convertTime(balanceMins);
	}

	private String convertTime(double time) {
		int numericMins = (int) time;
		int hours = numericMins / 60;
		int minutes = numericMins % 60;
		String convertedTime = "";
		convertedTime = (hours < 10) ? convertedTime = "0" + hours
				: convertedTime + hours;
		convertedTime = convertedTime + ":";
		convertedTime = (minutes < 10) ? convertedTime + "0" + minutes
				: convertedTime + minutes;
		return convertedTime;
	}

	public void setBalanceMins(double balanceMins) {
		this.balanceMins = balanceMins;
	}

}
