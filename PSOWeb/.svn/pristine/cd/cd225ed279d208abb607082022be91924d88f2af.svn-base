package com.itelasoft.pso.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "transactions")
public class Transaction {

	private Long id;
	private Long contactId;
	private ProgramEvent programEvent;
	private StudentEvent studentEvent;
	private Long studentId;
	private Long invoiceId;
	private EnumTransactionType transactionType;
	private double amount;
	private String remarks;
	private Date transactionDate;
	private Long createdUserId;
	private EnumChargeType chargeType;
	private EnumPaymentType paymentType;
	private String paymentMethod;
	private boolean gstIncluded;
	private String flag = "";
	private Collection collection;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	@ManyToOne
	@JoinColumn(name = "studenteventid", insertable = true, updatable = true, unique = true)
	public StudentEvent getStudentEvent() {
		return studentEvent;
	}

	public void setStudentEvent(StudentEvent studentEvent) {
		this.studentEvent = studentEvent;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}

	public void setTransactionType(EnumTransactionType transactionType) {
		this.transactionType = transactionType;
	}

	@Enumerated(EnumType.STRING)
	public EnumTransactionType getTransactionType() {
		return transactionType;
	}

	public void setProgramEvent(ProgramEvent programEvent) {
		this.programEvent = programEvent;
	}

	@ManyToOne
	@JoinColumn(name = "programEventId")
	public ProgramEvent getProgramEvent() {
		return programEvent;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	@Enumerated(EnumType.STRING)
	public EnumChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(EnumChargeType chargeType) {
		this.chargeType = chargeType;
	}

	public void setPaymentType(EnumPaymentType paymentType) {
		this.paymentType = paymentType;
	}

	@Enumerated(EnumType.STRING)
	public EnumPaymentType getPaymentType() {
		return paymentType;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Transient
	public String getFlag() {
		return flag;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	@ManyToOne
	@JoinColumn(name = "collectionId")
	public Collection getCollection() {
		return collection;
	}

	public void setGstIncluded(boolean gstIncluded) {
		this.gstIncluded = gstIncluded;
	}

	public boolean isGstIncluded() {
		return gstIncluded;
	}

	public enum EnumTransactionType {
		CREDIT("CR") {
			@Override
			public String toString() {
				return "Credit";
			}
		},
		DEBIT("DR") {
			@Override
			public String toString() {
				return "Debit";
			}
		};

		private String id;

		private EnumTransactionType(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

	}

	public enum EnumChargeType {
		EVENT("Event") {
			@Override
			public String toString() {
				return "Event";
			}
		},
		PERSONAL("Personal") {
			@Override
			public String toString() {
				return "Personal";
			}
		},
		COLLECTION("Collection") {
			public String toString() {
				return "Collection";
			}
		};

		private String id;

		private EnumChargeType(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

	}

}
