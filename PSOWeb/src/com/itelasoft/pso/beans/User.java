package com.itelasoft.pso.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User extends BaseEntity {
	@Id @DocumentId
	private Long id;
	@IndexedEmbedded
	private Contact contact;
	private Contact mailingAddress;
	private String userName = "";
	private String password = "";
	private String userType = "";
	private boolean enabled = true;
	private boolean systemUser;
	private Date createdOn;
	private List<Authority> authorities = new ArrayList<Authority>();

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user")
	@Cascade(value = { org.hibernate.annotations.CascadeType.DELETE_ORPHAN, org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Column(name = "user_type")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "contactId")
	public Contact getContact() {
		return contact;
	}

	public void setSystemUser(boolean systemUser) {
		this.systemUser = systemUser;
	}

	public boolean isSystemUser() {
		return systemUser;
	}

	public void setMailingAddress(Contact mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "mailingAddressId")
	public Contact getMailingAddress() {
		return mailingAddress;
	}

}
