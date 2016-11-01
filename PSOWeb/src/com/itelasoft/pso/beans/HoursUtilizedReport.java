package com.itelasoft.pso.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "HoursUtilizedReport")
public class HoursUtilizedReport extends BaseEntity {

	private Long id;
	private Date generatedDate;
	private Date fromDate;
	private Date toDate;
	private List<HoursUtilizedReportItem> reportItems = new ArrayList<HoursUtilizedReportItem>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGeneratedDate() {
		return generatedDate;
	}

	public void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setReportItems(List<HoursUtilizedReportItem> reportItems) {
		this.reportItems = reportItems;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "reportId", insertable = true, updatable = true)
	@LazyCollection(value = LazyCollectionOption.FALSE)
	public List<HoursUtilizedReportItem> getReportItems() {
		return reportItems;
	}

}
