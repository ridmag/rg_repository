package com.itelasoft.pso.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity {

	private Long id;
	private Student student;
	private String type;
	private Contact invoicee;
	private String description;
	private double totalCharge;
	private double previousPayments;
	private double currentPayments;
	private double previousOutstanding;
	private double transportCharge;
	private double eftCharge;
	private double otherPayment;
	private double otherCharge;
	private double subTotal;
	private double discount;
	private double discountRate;
	private String discountType;
	private double tax;
	private double total;
	private Date invoiceDate, dueDate;
	private String followupNote;
	private Boolean billedToThirdParty;
	private List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
	private List<InvoiceItem> additionalInvoiceItems = new ArrayList<InvoiceItem>();
	private List<Transaction> transactions = new ArrayList<Transaction>();
	private String stmntStatus = "";
	private boolean nxtStmntFound = false, noRecords = false;

	// Temp list for event related transactions
	private List<InvoiceItem> tmpItems = new ArrayList<InvoiceItem>();

	@Transient
	public String getInvoiceInfo() {
		return (id + " | " + student.getContact().getName() + " | $" + total);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "studentId")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "invoiceeId")
	public Contact getInvoicee() {
		return invoicee;
	}

	public void setInvoicee(Contact invoicee) {
		this.invoicee = invoicee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "invoiceId", insertable = true)
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public double getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(double totalCharge) {
		this.totalCharge = totalCharge;
	}

	public double getPreviousPayments() {
		return previousPayments;
	}

	public void setPreviousPayments(double previousPayments) {
		this.previousPayments = previousPayments;
	}

	public double getCurrentPayments() {
		return currentPayments;
	}

	public void setCurrentPayments(double currentPayments) {
		this.currentPayments = currentPayments;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	@Transient
	public double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}

	@Transient
	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getFollowupNote() {
		return followupNote;
	}

	public void setFollowupNote(String followupNote) {
		this.followupNote = followupNote;
	}

	public Boolean getBilledToThirdParty() {
		return billedToThirdParty;
	}

	public void setBilledToThirdParty(Boolean billedToThirdParty) {
		this.billedToThirdParty = billedToThirdParty;
	}

	public void setPreviousOutstanding(double previousOutstanding) {
		this.previousOutstanding = previousOutstanding;
	}

	public double getPreviousOutstanding() {
		return previousOutstanding;
	}

	public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Where(clause = "chargeType = 'EVENT' OR paymentType = 'EVENT'")
	@OrderBy(clause = "eventDate")
	@JoinColumn(name = "invoiceId", insertable = true)
	public List<InvoiceItem> getInvoiceItems() {
		return invoiceItems;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Where(clause = "(chargeType IS NOT null AND chargeType != 'EVENT') OR (paymentType IS NOT null AND paymentType != 'EVENT')")
	@JoinColumn(name = "invoiceId", insertable = true)
	public List<InvoiceItem> getAdditionalInvoiceItems() {
		return additionalInvoiceItems;
	}

	public void setAdditionalInvoiceItems(
			List<InvoiceItem> additionalInvoiceItems) {
		this.additionalInvoiceItems = additionalInvoiceItems;
	}

	public void setTransportCharge(double transportCharge) {
		this.transportCharge = transportCharge;
	}

	public double getTransportCharge() {
		return transportCharge;
	}

	public void setEftCharge(double eftCharge) {
		this.eftCharge = eftCharge;
	}

	public double getEftCharge() {
		return eftCharge;
	}

	public void setOtherPayment(double otherPayment) {
		this.otherPayment = otherPayment;
	}

	public double getOtherPayment() {
		return otherPayment;
	}

	public void setOtherCharge(double otherCharge) {
		this.otherCharge = otherCharge;
	}

	public double getOtherCharge() {
		return otherCharge;
	}

	@Transient
	public String getStmntStatus() {
		return stmntStatus;
	}

	public void setStmntStatus(String stmntStatus) {
		this.stmntStatus = stmntStatus;
	}

	public void setNxtStmntFound(boolean nxtStmntFound) {
		this.nxtStmntFound = nxtStmntFound;
	}

	@Transient
	public boolean isNxtStmntFound() {
		return nxtStmntFound;
	}

	public void setNoRecords(boolean noRecords) {
		this.noRecords = noRecords;
	}

	@Transient
	public boolean isNoRecords() {
		return noRecords;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Transient
	public Date getDueDate() {
		return dueDate;
	}

	public void setTmpItems(List<InvoiceItem> tmpItems) {
		this.tmpItems = tmpItems;
	}

	@Transient
	public List<InvoiceItem> getTmpItems() {
		return tmpItems;
	}

}
