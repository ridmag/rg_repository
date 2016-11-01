package com.itelasoft.pso.beans;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "vehicles")
public class Vehicle extends BaseEntity {
	private Long id;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String name;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String registrationId;
	private int passengers;
	private int wheelChairs;
	private String status;
	private String type;
	private FileData photo;
	private boolean active;

	// Transient variables for roster use
	private String availability;
	private String conflictingEvents;

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setWheelChairs(int wheelChairs) {
		this.wheelChairs = wheelChairs;
	}

	public int getWheelChairs() {
		return wheelChairs;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setPhoto(FileData photo) {
		this.photo = photo;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "photoId")
	public FileData getPhoto() {
		return photo;
	}

	@Transient
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	@Transient
	public String getAvailability() {
		return availability;
	}

	public void setConflictingEvents(String conflictingEvents) {
		this.conflictingEvents = conflictingEvents;
	}

	@Transient
	public String getConflictingEvents() {
		return conflictingEvents;
	}

}
