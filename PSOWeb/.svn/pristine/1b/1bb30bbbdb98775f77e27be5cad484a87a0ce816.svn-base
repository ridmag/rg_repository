package com.itelasoft.pso.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Indexed
@Table(name = "groupedstudents")
public class GroupedStudent {

	private Long id;
	private int sequence;
	@IndexedEmbedded
	private StudentGroup group;
	private Student student;
	private String status;
	private String remarks;
	@IndexedEmbedded
	private Location pickup;
	@IndexedEmbedded
	private Location dropoff;
	private boolean ui_selected;
	private Double chargeAmount;

	private String tmpString;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "studentId")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@OneToOne
	@JoinColumn(name = "pickup")
	public Location getPickup() {
		return pickup;
	}

	public void setPickup(Location pickup) {
		this.pickup = pickup;
	}

	@OneToOne
	@JoinColumn(name = "dropoff")
	public Location getDropoff() {
		return dropoff;
	}

	public void setDropoff(Location dropoff) {
		this.dropoff = dropoff;
	}

	public void setGroup(StudentGroup group) {
		this.group = group;
	}

	@ManyToOne
	@JoinColumn(name = "groupId")
	public StudentGroup getGroup() {
		return group;
	}

	public void setUi_selected(boolean ui_selected) {
		this.ui_selected = ui_selected;
	}

	@Transient
	public boolean isUi_selected() {
		return ui_selected;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getSequence() {
		return sequence;
	}

	public void setTmpString(String tmpString) {
		this.tmpString = tmpString;
	}

	@Transient
	public String getTmpString() {
		return tmpString;
	}

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

}
