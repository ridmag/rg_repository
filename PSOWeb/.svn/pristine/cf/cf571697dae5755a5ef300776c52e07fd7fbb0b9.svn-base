package com.itelasoft.pso.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.itelasoft.pso.beans.Transaction.EnumChargeType;

@Entity
@Table(name = "invoiceitems")
public class InvoiceItem {

	private Long id;
	private String programName;
	private String programType;
	private String studentGrp;
	private Date eventDate;
	private Date transactionDate;
	private double chargeAmount;
	private double paidAmount;
	private String remarks;
	private double balance;
	private Long invoiceId;
	private double transportCharge;
	private double programCharge;
	private EnumChargeType chargeType;
	private EnumPaymentType paymentType;
	private String paymentMethod;
	private String groupname,groups;
	private boolean attended;

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public boolean isAttended() {
		return attended;
	}

	public void setAttended(boolean attended) {
		this.attended = attended;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getStudentGrp() {
		return studentGrp;
	}

	public void setStudentGrp(String studentGrp) {
		this.studentGrp = studentGrp;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public String getProgramType() {
		return programType;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setTransportCharge(double transportCharge) {
		this.transportCharge = transportCharge;
	}

	public double getTransportCharge() {
		return transportCharge;
	}

	public void setProgramCharge(double programCharge) {
		this.programCharge = programCharge;
	}

	public double getProgramCharge() {
		return programCharge;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	@Enumerated(EnumType.STRING)
	public EnumChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(EnumChargeType chargeType) {
		this.chargeType = chargeType;
	}

	@Enumerated(EnumType.STRING)
	public EnumPaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(EnumPaymentType paymentType) {
		this.paymentType = paymentType;
	}
}
