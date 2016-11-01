package com.itelasoft.pso.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "LeavePolicies")
public class LeavePolicy extends BaseEntity {
	private Long id;
	private String name;
	private String status;
	private List<LeavePolicyDetail> details;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setDetails(List<LeavePolicyDetail> details) {
		this.details = details;
	}

	@OneToMany(mappedBy = "policy")
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@Cascade(value = { org.hibernate.annotations.CascadeType.DELETE_ORPHAN,
			org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	public List<LeavePolicyDetail> getDetails() {
		return details;
	}

}
