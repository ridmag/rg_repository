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
@Table(name = "ndisprices")
public class NdisPrice extends BaseEntity {
	private Long Id;
	private String priceName;
	private String description;
	private String itemNumber;
	private Date startDate;
	private Double price;
	private String uom;
	private NdisSupportItem supportItem;
	private String ndisTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@ManyToOne
	@JoinColumn(name = "supportitem")
	public NdisSupportItem getSupportItem() {
		return supportItem;
	}

	public void setSupportItem(NdisSupportItem supportItem) {
		this.supportItem = supportItem;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getNdisTime() {
		return ndisTime;
	}

	public void setNdisTime(String ndisTime) {
		this.ndisTime = ndisTime;
	}
	
	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}