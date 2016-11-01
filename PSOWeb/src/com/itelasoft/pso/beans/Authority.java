package com.itelasoft.pso.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "authorities")
public class Authority {
	private Long id;
	private String authority;
	private String userName;
	private User user;

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GenericGenerator(name = "mygen1", strategy = "increment")
	@GeneratedValue(generator = "mygen1")
	public Long getId() {
		return id;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}
}
