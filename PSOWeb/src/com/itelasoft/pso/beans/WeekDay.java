package com.itelasoft.pso.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "weekdays")
public class WeekDay extends BaseEntity {
	private Long id;
	private String name;

	public WeekDay() {

	}

	public WeekDay(Long weekdayId) {
		id = weekdayId;
	}

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

	@Transient
	public boolean equals(Object weekDay) {
		if (id != null && id.equals(((WeekDay) weekDay).getId()))
			return true;
		return false;
	}
}
