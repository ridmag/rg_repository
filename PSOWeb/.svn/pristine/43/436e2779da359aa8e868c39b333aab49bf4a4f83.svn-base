package com.itelasoft.pso.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "ndissupportitems")
public class NdisSupportItem extends BaseEntity {
	
	private Long id;
	private String itemName;
	private String ndisClusterType;
	private int numerator;
	private int denominator;
	private boolean skills;
	private List<NdisPrice> ndisPrice;
	private String gstCode;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getNumerator() {
		return numerator;
	}
	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}
	public int getDenominator() {
		return denominator;
	}
	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}
	public boolean isSkills() {
		return skills;
	}
	public void setSkills(boolean skills) {
		this.skills = skills;
	}

	@OneToMany(mappedBy = "supportItem")
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@OrderBy("priceName")
	public List<NdisPrice> getNdisPrice() {
		return ndisPrice;
	}
	public void setNdisPrice(List<NdisPrice> ndisPrice) {
		this.ndisPrice = ndisPrice;
	}
	public String getNdisClusterType() {
		return ndisClusterType;
	}
	public void setNdisClusterType(String ndisClusterType) {
		this.ndisClusterType = ndisClusterType;
	}

	public String getGstCode() {
		return gstCode;
	}

	public void setGstCode(String gstCode) {
		this.gstCode = gstCode;
	}

}