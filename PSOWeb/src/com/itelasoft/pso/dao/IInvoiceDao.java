package com.itelasoft.pso.dao;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.Invoice;

/**
 * Dao class for Invoice.
 */
public interface IInvoiceDao extends IGenericDao<Invoice, Long> {

	public Invoice retrieve(Long studentId, int month, int year);

	public List<Invoice> listByStudentName(String studentName);

	public Invoice getLastInvoice(Date currentInvoiceDate, Long studentId);

	public List<Invoice> listByStudentId(Long studentId, int year);

	public List<Invoice> listByPreviousMonth(int months);

	public List<Invoice> searchByAll(int months, Long studentId, String studentName);

	public List<Invoice> searchByYM(int year, int month);

	public boolean isInvoiceAvailableByStudent(Long studentId, int invoiceYear, int invoiceMonth);
}
/*
 * <bean id="invoiceDao" class="com.itelasoft.pso.dao.InvoiceDao"> <property
 * name="sessionFactory" ref="mySessionFactory" /> </bean>
 */