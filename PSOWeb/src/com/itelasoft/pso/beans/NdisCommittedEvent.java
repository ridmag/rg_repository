package com.itelasoft.pso.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "ndiscommittedevents")
public class NdisCommittedEvent extends BaseEntity {

	private Long Id;
	private StudentGroup studentGroup;
	private Student participant;
	private Date eventDate;
	private Date startTime;
	private Date endTime;
	private NdisSupportItem ndisSupportCluster;
	private NdisSupportItem clusterOverride;
	private Double hours;
	private Boolean claimed;
	private Double committedHours;
	private Double uncommittedhours;
	private Long clusterOverrideId;
	private Long invoiceId;
	private Integer noOfUnits;
	private Integer committedUnits;
	private NdisPrice price;
	private Integer uncommittedUnits;
	private Double kmsPerDay;
	private double eventPrice;
	private Number units;
	private String unitsType;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	@ManyToOne
	@JoinColumn(name = "studentgroup")
	public StudentGroup getStudentGroup() {
		return studentGroup;
	}

	public void setStudentGroup(StudentGroup studentGroup) {
		this.studentGroup = studentGroup;
	}

	@ManyToOne
	@JoinColumn(name = "participant")
	public Student getParticipant() {
		return participant;
	}

	public void setParticipant(Student participant) {
		this.participant = participant;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@ManyToOne
	@JoinColumn(name = "ndissupportcluster")
	public NdisSupportItem getNdisSupportCluster() {
		return ndisSupportCluster;
	}

	public void setNdisSupportCluster(NdisSupportItem ndisSupportCluster) {
		this.ndisSupportCluster = ndisSupportCluster;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public Boolean getClaimed() {
		return claimed;
	}

	public void setClaimed(Boolean claimed) {
		this.claimed = claimed;
	}

	@Transient
	public Double getCommittedHours() {
		return committedHours;
	}

	public void setCommittedHours(Double committedHours) {
		this.committedHours = committedHours;
	}

	@Transient
	public Double getUncommittedhours() {
		return uncommittedhours;
	}

	public void setUncommittedhours(Double uncommittedhours) {
		this.uncommittedhours = uncommittedhours;
	}

	public void setClusterOverride(NdisSupportItem clusterOverride) {
		this.clusterOverride = clusterOverride;
	}

	@ManyToOne
	@Cascade(value = org.hibernate.annotations.CascadeType.PERSIST)
	@JoinColumn(name = "clusteroverride")
	public NdisSupportItem getClusterOverride() {
		return clusterOverride;
	}

	public void setClusterOverrideId(Long clusterOverrideId) {
		this.clusterOverrideId = clusterOverrideId;
	}

	@Transient
	public Long getClusterOverrideId() {
		return clusterOverrideId;
	}

	public Integer getNoOfUnits() {
		return noOfUnits;
	}

	public void setNoOfUnits(Integer noOfUnits) {
		this.noOfUnits = noOfUnits;
	}

	@Transient
	public Integer getCommittedUnits() {
		return committedUnits;
	}

	public void setCommittedUnits(Integer committedUnits) {
		this.committedUnits = committedUnits;
	}

	@ManyToOne
	@JoinColumn(name = "priceid")
	public NdisPrice getPrice() {
		return price;
	}

	public void setPrice(NdisPrice price) {
		this.price = price;
	}

	@Transient
	public Integer getUncommittedUnits() {
		return uncommittedUnits;
	}

	public void setUncommittedUnits(Integer uncommittedUnits) {
		this.uncommittedUnits = uncommittedUnits;
	}

	public Double getKmsPerDay() {
		return kmsPerDay;
	}

	public void setKmsPerDay(Double kmsPerDay) {
		this.kmsPerDay = kmsPerDay;
	}

	public double getEventPrice() {
		return eventPrice;
	}

	public void setEventPrice(double eventPrice) {
		this.eventPrice = eventPrice;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	@Transient
	public Number getUnits() {
		String type = price.getSupportItem().getNdisClusterType();
		if (type.equals("DayProgram")) {
			return hours;
		} else if (type.equals("Transport")) {
			return kmsPerDay;
		} else if (type.equals("Ancillary")) {
			return noOfUnits;
		} else if (type.equals("Individual")) {
			if (price.getUom().equals("Each"))
				return noOfUnits;
			else
				return hours;
		}
		return 0;
	}

	@Transient
	public String getUnitsType() {
		String type = price.getSupportItem().getNdisClusterType();
		if (type.equals("DayProgram")) {
			return "Hours";
		} else if (type.equals("Transport")) {
			return "KMs per day";
		} else if (type.equals("Ancillary")) {
			return "Number of units";
		} else if (type.equals("Individual")) {
			if (price.getUom().equals("Each"))
				return "Number of units";
			else
				return "Hours";
		}
		return "";
	}

}