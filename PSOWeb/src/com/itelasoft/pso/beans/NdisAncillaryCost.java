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
@Table(name = "ndisancillarycost")
public class NdisAncillaryCost {

	private Long id;
	private NdisSupportItem ndisSupportItem;
	private Date date;
	private String uom;
	private int noofunit;
	private Student student;
	private NdisPrice ndisPrice;
	private Boolean claimed;
	private Boolean authorized;
	private Long invoiceId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "ndisitemid")
	public NdisSupportItem getNdisSupportItem() {
		return ndisSupportItem;
	}

	public void setNdisSupportItem(NdisSupportItem ndisSupportItem) {
		this.ndisSupportItem = ndisSupportItem;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public int getNoofunit() {
		return noofunit;
	}

	public void setNoofunit(int noofunit) {
		this.noofunit = noofunit;
	}

	@ManyToOne
	@JoinColumn(name = "studentid")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne
	@JoinColumn(name = "ndispriceid")
	public NdisPrice getNdisPrice() {
		return ndisPrice;
	}

	public void setNdisPrice(NdisPrice ndisPrice) {
		this.ndisPrice = ndisPrice;
	}

	public Boolean isClaimed() {
		return claimed;
	}

	public void setClaimed(Boolean claimed) {
		this.claimed = claimed;
	}

	public Boolean getAuthorized() {
		return authorized;
	}

	public void setAuthorized(Boolean authorized) {
		this.authorized = authorized;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}


}