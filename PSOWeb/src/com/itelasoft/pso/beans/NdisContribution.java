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
@Table(name = "ndiscontributions")
public class NdisContribution extends BaseEntity {

	private Long id;
	private Long clusterType;
	private Student student;
	private Double amount;
	private Date contributedDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClusterType() {
		return clusterType;
	}

	public void setClusterType(Long clusterType) {
		this.clusterType = clusterType;
	}

	@ManyToOne
	@JoinColumn(name = "studentid")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getContributedDate() {
		return contributedDate;
	}

	public void setContributedDate(Date contributedDate) {
		this.contributedDate = contributedDate;
	}

}