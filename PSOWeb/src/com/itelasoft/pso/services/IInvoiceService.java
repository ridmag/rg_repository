package com.itelasoft.pso.services;

import java.util.Date;
import java.util.List;

import com.itelasoft.pso.beans.Invoice;

public interface IInvoiceService extends IGenericService<Invoice, Long> {

	public List<Invoice> setData(List<Invoice> invoices);

	public Invoice retrieveWithItems(Long invoiceId);

	public Invoice retrieve(Long studentId, int month, int year);

	public List<Invoice> listByStudentName(String studentName);

	public Invoice getLastInvoice(Date currentInvoiceDate, Long studentId);

	public List<Invoice> listByStudentId(Long studentId, int year);

	public List<Invoice> listByPreviousMonth(int months);

	public List<Invoice> searchByAll(int months, Long studentId,
			String studentName);

	/**
	 * Returns the list of invoices matching the given year/month
	 * 
	 * @param year
	 * @param month
	 * @return List<Invoice>
	 */
	public List<Invoice> searchByYM(int year, int month);
}
/*
 * <bean id="invoiceService" class="com.itelasoft.pso.services.InvoiceService">
 * <property name="dao" ref="invoiceDao" /> </bean> public IInvoiceService
 * getInvoiceService(){ return
 * (IInvoiceService)context.getBean("invoiceService"); }
 */