package com.itelasoft.pso.beans;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "StaffSkills")
public class StaffSkill {
	private Long id;
	private ReferenceItem skill;
	private StaffMember staffMember;
	private EnumSkillLevel level;

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
	@JoinColumn(name = "referenceItemId")
	public ReferenceItem getSkill() {
		return skill;
	}

	public void setSkill(ReferenceItem skill) {
		this.skill = skill;
	}

	@ManyToOne
	@JoinColumn(name = "staffId")
	public StaffMember getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
	}

	public void setLevel(EnumSkillLevel level) {
		this.level = level;
	}

	@Enumerated(EnumType.STRING)
	public EnumSkillLevel getLevel() {
		return level;
	}

}
