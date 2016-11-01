package com.itelasoft.pso.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import com.icesoft.faces.component.ext.ClickActionEvent;
import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.itelasoft.pso.beans.ActivityStatement;
import com.itelasoft.pso.beans.Collection;
import com.itelasoft.pso.beans.EnumPaymentType;
import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.Invoice;
import com.itelasoft.pso.beans.InvoiceItem;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentEvent;
import com.itelasoft.pso.beans.Transaction;
import com.itelasoft.pso.beans.Transaction.EnumChargeType;
import com.itelasoft.pso.beans.Transaction.EnumTransactionType;
import com.itelasoft.util.SortObjectByName;

@ManagedBean(name = "invoiceManagerModel")
@SessionScoped
public class InvoiceManagerModel extends UIModel {

	private List<Invoice> invoices;
	private Invoice invoice;
	private StudentEvent studentEvent;
	private double totalCharge;
	private double totalPayment;
	private double previousPayments;
	private double eftPayments, otherCharge;
	private double transportCharge;
	private Invoice tmpInvoice;
	private String searchText;
	private List<InvoiceItem> invoiceItems;
	private Date invoiceDate;
	private List<Student> students;
	private Student student;
	private List<StudentEvent> outstandingEvents;
	private double totalOutstanding;
	private boolean visiblePayment, visibleCharge, visibleAdhocPayment, visibleAdhocCharge, selectAll;
	private Transaction paymentTx;

	private Long collectionId;
	private List<Collection> collection;
	private List<SelectItem> paymentTypes;
	private Transaction chargeTx;
	private List<Transaction> unbilledTxs, unbilledAdHocTxs;
	private List<Transaction> adHocTxs;
	private double adhocAmount;
	private double adhocAmountPayable;
	private List<Transaction> tempTxs;
	private List<Transaction> deleteTxs;
	private List<Transaction> tempCollTxs;
	private GroupedStudent groupstudent;
	private ActivityStatement groupList;

	private int selectedYear, selectedMonth, startYear, startMonth, tabIndex;
	private HashMap<Integer, String> monthClasses;
	private int[] monthsList = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	private Calendar currentDate, cal;
	private List<Long> notBankedStdIDs, missingStmntsStdIDs;
	private SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
	private SimpleDateFormat monthFormatMM = new SimpleDateFormat("MM");

	private SimpleDateFormat yearFormatYYYY = new SimpleDateFormat("YYYY");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private HashMap<Long, Invoice> selectedInvMap;
	private boolean includeInactive;

	public InvoiceManagerModel() {
		cal = new GregorianCalendar();
		cal.setTime(serviceLocator.getBusinessRulesService().getActStmtStartDate());
		startYear = cal.get(Calendar.YEAR);
		startMonth = cal.get(Calendar.MONTH) + 1;
		monthClasses = new HashMap<Integer, String>();
		for (int i : monthsList) {
			monthClasses.put(i, "");
		}
		students = serviceLocator.getStudentService().findAll();
		currentDate = GregorianCalendar.getInstance();
		selectedYear = currentDate.get(Calendar.YEAR);
		selectedMonth = 0;
		searchText = "";
		refreshMonths();
	}

	public void initStatements() {
		currentDate = GregorianCalendar.getInstance();
		selectedYear = currentDate.get(Calendar.YEAR);
		selectedMonth = 0;
		searchText = "";
		invoice = null;
		invoices = null;
		refreshMonths();
	}

	public void initPayments() {
		tabIndex = 0;
		searchText = "";
		includeInactive = false;
		students = serviceLocator.getStudentService().findAll();
		if(!students.isEmpty()){
			Collections.sort(students, new SortObjectByName());
		}
		student = null;
		invoice = null;
		currentDate = GregorianCalendar.getInstance();
		selectedYear = currentDate.get(Calendar.YEAR);
	}

	public void yearChanged(ValueChangeEvent event) {
		String source = (String) event.getComponent().getAttributes().get("from");
		selectedYear = (Integer) event.getNewValue();
		if (source != null && source.equals("payment")) {
			refreshInvoicesByStudent();
		} else {
			selectedMonth = 0;
			invoices = null;
			refreshMonths();
		}
	}

	public void searchInvoice() {
		if (searchText.isEmpty()) {
			showError("Please enter a valid Statement Id.");
			return;
		}
		try {
			// See if the user has entered an ID instead name
			Long id = Long.parseLong(searchText);
			tmpInvoice = serviceLocator.getInvoiceService().retrieveWithItems(id);
			if (tmpInvoice == null) {
				invoice = null;
				showError("No result available for this Id.");
			} else {
				invoice = setEventItems(tmpInvoice);
			}

		} catch (NumberFormatException nFE) {
			showError("Input error : Statement id should be a number..");
		}
	}

	public void selectInvoice(ClickActionEvent re) {
		invoice = (Invoice) re.getComponent().getAttributes().get("invoice");
		invoice.setUi_selected(false);
	}

	public void openInvoice(ActionEvent ae) {
		tmpInvoice = (Invoice) ae.getComponent().getAttributes().get("invoice");
		selectedMonth = Integer.parseInt((String) ae.getComponent().getAttributes().get("sMonth"));
		tmpInvoice = serviceLocator.getInvoiceService().retrieveWithItems(tmpInvoice.getId());
		invoice = setEventItems(tmpInvoice);
		/*
		 * List<InvoiceItem> withOutEmpty = new ArrayList<InvoiceItem>(); for
		 * (InvoiceItem invoiceItem : invoice.getTmpItems()) { if
		 * (invoiceItem.getChargeAmount() > 0 || invoiceItem.getPaidAmount() > 0
		 * || invoiceItem.getTransportCharge() > 0 || (invoiceItem.isAttended()
		 * == false && invoiceItem .getGroupname() != null)
		 * 
		 * invoice = setEventItems(tmpInvoice); /* List<InvoiceItem>
		 * withOutEmpty = new ArrayList<InvoiceItem>(); for (InvoiceItem
		 * invoiceItem : invoice.getTmpItems()) { if
		 * (invoiceItem.getChargeAmount() > 0 || invoiceItem.getPaidAmount() > 0
		 * || invoiceItem.getTransportCharge() > 0 || (invoiceItem.isAttended()
		 * == false && invoiceItem .getGroupname() != null)
		 * 
		 * ) {
		 * 
		 * withOutEmpty.add(invoiceItem); } } invoice.setTmpItems(withOutEmpty);
		 */
		tmpInvoice = null;

	}

	public void closePreview() {
		invoice = null;
	}

	public void selectMonth(ActionEvent ae) {
		selectedMonth = (Integer) ae.getComponent().getAttributes().get("month");
		for (int key : monthClasses.keySet()) {
			String str = monthClasses.get(key);
			if (str.endsWith(" current")) {
				str = str.replace(" current", "");
				monthClasses.put(key, str);
				break;
			}
		}
		monthClasses.put(selectedMonth, monthClasses.get(selectedMonth).concat(" current"));
		refreshInvoices(true);

	}

	public void generateInvoice(ActionEvent ae) {

		Invoice invoice = (Invoice) ae.getComponent().getAttributes().get("invoice");
		String from = (String) ae.getComponent().getAttributes().get("from");
		int tableIndex = (Integer) ae.getComponent().getAttributes().get("index");

		if (!invoice.getStmntStatus().isEmpty()) {
			showError("Statement cannot be generated. Please check the status.");
			return;
		}
		if (from != null && from.equals("paymentMgr")) {
			selectedMonth = tableIndex + 1;
		}
		generateInvoice(invoice.getStudent());
		if (tmpInvoice != null) {
			if (from != null && from.equals("invoiceMgr")) {
				refreshMonths();
				if (invoice.isUi_selected()) {
					tmpInvoice.setUi_selected(true);
					selectedInvMap.put(invoice.getStudent().getId(), tmpInvoice);
				}
				invoices.set(tableIndex, tmpInvoice);
			}
			if (from != null && from.equals("paymentMgr")) {
				refreshInvoicesByStudent();
			}
			showInfo("Statement generated successfully.");
		} else {
			showError("No eligible transactions found..");
		}
	}

	public void generateInvoices(ActionEvent ae) {
		int generated = 0, alreadyGenerated = 0, notGenerated = 0;
		// List<Invoice> generatedList = new ArrayList<Invoice>();
		for (Invoice inv : selectedInvMap.values()) {
			if (inv.getId() != null) {
				alreadyGenerated++;
				continue;
			}
			if (!inv.getStmntStatus().isEmpty()) {
				notGenerated++;
				continue;
			}
			generateInvoice(inv.getStudent());
			if (tmpInvoice != null) {
				generated++;
				for (Invoice extInv : invoices) {
					if (tmpInvoice.getStudent().getId().equals(extInv.getStudent().getId())) {
						tmpInvoice.setUi_selected(true);
						invoices.set(invoices.indexOf(extInv), tmpInvoice);
						selectedInvMap.put(tmpInvoice.getStudent().getId(), tmpInvoice);
						break;
					}
				}
			} else
				notGenerated++;
		}
		if (alreadyGenerated > 0)
			showInfo(String.valueOf(alreadyGenerated) + " Statement(s) found already generated.");
		if (generated > 0) {
			refreshMonths();
			showInfo(String.valueOf(generated) + " Statement(s) successfully generated.");
		}
		if (notGenerated > 0)
			showError(String.valueOf(notGenerated) + " Statement(s) was not generated.");
	}

	public void deleteInvoice(ActionEvent ae) {
		Invoice invoice = (Invoice) ae.getComponent().getAttributes().get("invoice");
		// int tableIndex = (Integer) ae.getComponent().getAttributes()
		// .get("index");
		String from = (String) ae.getComponent().getAttributes().get("from");
		serviceLocator.getProgramEventService().unmarkEventsInvoiced(selectedYear, selectedMonth, invoice);
		serviceLocator.getInvoiceService().delete(invoice.getId());
		if (from != null && from.equals("invoiceMgr")) {
			refreshMonths();
			refreshInvoices(false);
		}
		if (from != null && from.equals("paymentMgr")) {
			refreshInvoicesByStudent();
		}
	}

	public void deleteInvoices() {
		int deleted = 0, notDeleted = 0;
		for (Invoice inv : selectedInvMap.values()) {
			if (inv.getId() == null || inv.isNxtStmntFound()) {
				notDeleted++;
				continue;
			}
			serviceLocator.getProgramEventService().unmarkEventsInvoiced(selectedYear, selectedMonth, inv);
			serviceLocator.getInvoiceService().delete(inv.getId());
			deleted++;
		}
		if (notDeleted > 0) {
			showError(notDeleted + " Statement(s) not deleted.");
		}
		if (deleted > 0) {
			showInfo(deleted + " Statement(s) deleted successfully.");
			refreshMonths();
			refreshInvoices(false);
		}
	}

	public void selectInvoice(ValueChangeEvent event) {
		Invoice invoice = (Invoice) event.getComponent().getAttributes().get("invoice");
		Boolean selected = (Boolean) event.getNewValue();
		invoice.setUi_selected(selected);
		if (selected) {
			selectedInvMap.put(invoice.getStudent().getId(), invoice);
			if (selectedInvMap.values().containsAll(invoices))
				selectAll = true;
		} else {
			selectAll = false;
			selectedInvMap.remove(invoice.getStudent().getId());
		}
	}

	public void selectAllInvoices(ValueChangeEvent event) {
		selectAll = (Boolean) event.getNewValue();
		for (Invoice inv : invoices) {
			inv.setUi_selected(selectAll);
			if (selectAll)
				selectedInvMap.put(inv.getStudent().getId(), inv);
			else
				selectedInvMap.remove(inv.getStudent().getId());
		}
	}

	private void refreshInvoices(boolean withSelectedMap) {
		invoices = new ArrayList<Invoice>();
		if (withSelectedMap || selectedInvMap == null) {
			selectAll = false;
			selectedInvMap = new HashMap<Long, Invoice>();
		}
		missingStmntsStdIDs = new ArrayList<Long>();

		List<Invoice> existingInvoices = serviceLocator.getInvoiceService().searchByYM(selectedYear, selectedMonth);
		List<Student> availableStds = serviceLocator.getStudentService().listStudentsWithTxs(selectedMonth,
				selectedYear);
		notBankedStdIDs = serviceLocator.getStudentService().listStudentsWithNotBankedEvents(selectedMonth,
				selectedYear);
		if (!(selectedYear == startYear && selectedMonth == startMonth)) {
			missingStmntsStdIDs = serviceLocator.getStudentService().listStudentsMissingPreviousMonthsInvoice(
					selectedMonth, selectedYear, serviceLocator.getBusinessRulesService().getActStmtStartDate());
		}
		for (Student std : availableStds) {
			Invoice invoice = null;
			for (Invoice inv : existingInvoices) {
				if (inv.getStudent().getId().equals(std.getId())) {
					invoice = inv;
					break;
				}
			}
			if (invoice == null) {
				invoice = new Invoice();
				invoice.setStudent(std);
			}
			String initial = "Following conflict(s) have been found.";
			if (notBankedStdIDs.contains(std.getId())) {
				invoice.setStmntStatus(invoice.getStmntStatus().isEmpty() ? (initial + "\n  - Not banked events")
						: (invoice.getStmntStatus() + "\n  - Not banked events"));
			}
			if (missingStmntsStdIDs.contains(std.getId())) {
				invoice.setStmntStatus(
						invoice.getStmntStatus().isEmpty() ? (initial + "\n  - Missing previous month's statement")
								: (invoice.getStmntStatus() + "\n  - Missing previous month's statement"));
			}
			if (selectedInvMap.containsKey(invoice.getStudent().getId())) {
				invoice.setUi_selected(true);
				selectedInvMap.put(invoice.getStudent().getId(), invoice);
			}
			invoices.add(invoice);
		}
		if(!invoices.isEmpty()){
			Collections.sort(invoices, new SortObjectByName());
		}
	}
	
	private void generateInvoice(Student student) {
		invoiceDate = null;
		tmpInvoice = null;
		unbilledTxs = serviceLocator.getTransactionService().listUnbilledTxsByStudent(selectedMonth, selectedYear,
				student.getId());
		unbilledAdHocTxs = serviceLocator.getTransactionService().listUnbilledAdHocTxsByStudent(selectedMonth,
				selectedYear, student.getId());
		/*
		 * if (unbilledTxs.isEmpty() && unbilledAdHocTxs.isEmpty()) { return; }
		 */
		tmpInvoice = new Invoice();
		tmpInvoice.setStudent(student);
		tmpInvoice.setType("INVOICE");

		invoiceDate = serviceLocator.getStudentEventService().findMaxEventDateOfMonth(selectedMonth, selectedYear,
				student.getId());

		for (Transaction txn : unbilledTxs) {
			if (invoiceDate == null || invoiceDate.before(txn.getProgramEvent().getEventDate()))
				invoiceDate = txn.getProgramEvent().getEventDate();
		}
		if (invoiceDate == null) {
			for (Transaction txn : unbilledAdHocTxs) {
				if (invoiceDate == null || invoiceDate.before(txn.getTransactionDate()))
					invoiceDate = txn.getTransactionDate();
			}
		}
		tmpInvoice.getTransactions().addAll(unbilledTxs);
		tmpInvoice.getTransactions().addAll(unbilledAdHocTxs);
		createInvoice(student);
	}

	private void createInvoice(Student student) {
		double subTotal = 0;
		for (Transaction tx : tmpInvoice.getTransactions()) {
			if (tx.getTransactionType().equals(EnumTransactionType.CREDIT))
				subTotal = subTotal + tx.getAmount();
			else
				subTotal = subTotal - tx.getAmount();
		}
		if (!(selectedYear == startYear && selectedMonth == startMonth)) {
			Invoice previous = serviceLocator.getInvoiceService().getLastInvoice(invoiceDate, student.getId());
			if (previous != null) {
				tmpInvoice.setPreviousOutstanding(previous.getTotal());
			} else
				tmpInvoice.setPreviousOutstanding(0);
		}
		tmpInvoice.setInvoiceDate(invoiceDate);
		tmpInvoice.setSubTotal(subTotal);
		tmpInvoice.setTotal(tmpInvoice.getPreviousOutstanding() + subTotal);
		tmpInvoice = serviceLocator.getInvoiceService().create(tmpInvoice);
		try {
			createInvoiceItems();
			serviceLocator.getProgramEventService().markEventsInvoiced(tmpInvoice);
			tmpInvoice.setTotalCharge(totalCharge);
			tmpInvoice.setOtherCharge(otherCharge);
			tmpInvoice.setPreviousPayments(previousPayments);
			tmpInvoice.setCurrentPayments(totalPayment);
			tmpInvoice.setTransportCharge(transportCharge);
			BigDecimal taxBigDecimal = new BigDecimal(transportCharge / 11).setScale(2, RoundingMode.HALF_UP);
			tmpInvoice.setTax(tmpInvoice.getTax() + taxBigDecimal.doubleValue());
			tmpInvoice.setEftCharge(eftPayments);
			double otherPayments = totalPayment + previousPayments - eftPayments;
			tmpInvoice.setOtherPayment(otherPayments);
			tmpInvoice.setInvoiceItems(invoiceItems);

			tmpInvoice = serviceLocator.getInvoiceService().update(tmpInvoice);
			tmpInvoice.setTransactions(null);
			tmpInvoice.setInvoiceItems(null);
			tmpInvoice.setAdditionalInvoiceItems(null);
		} catch (Exception e) {
			showExceptionAsError(e);
			e.printStackTrace();
			serviceLocator.getInvoiceService().delete(tmpInvoice.getId());
			tmpInvoice = null;
		}
		invoiceDate = null;
	}

	private void createInvoiceItems() {
		totalCharge = 0;
		totalPayment = previousPayments = 0;
		transportCharge = eftPayments = otherCharge = 0;
		double balance = tmpInvoice.getPreviousOutstanding();
		double taxableCharges = 0;
		invoiceItems = new ArrayList<InvoiceItem>();

		// List<String> groups = new ArrayList<String>();
		// Calendar cal = new GregorianCalendar(selectedYear, selectedMonth - 1,
		// 1, 0, 0, 0);
		for (Transaction tx : unbilledTxs) {
			if (tx.getTransactionType().equals(EnumTransactionType.CREDIT)) {
				InvoiceItem item = createInvoiceItem(tx);
				item.setChargeAmount(tx.getAmount());
				item.setAttended(true);
				item.setBalance(balance = balance + item.getChargeAmount());
				invoiceItems.add(item);
				if (tx.getProgramEvent().getProgram().getType().getName().equals("Transport")) {
					transportCharge = transportCharge + item.getChargeAmount();
				}
				totalCharge = totalCharge + item.getChargeAmount();
			}
			if (tx.getTransactionType().equals(EnumTransactionType.DEBIT)) {
				totalPayment = totalPayment + tx.getAmount();
				if (tx.getPaymentMethod() != null && tx.getPaymentMethod().equals("EFT")) {
					eftPayments = eftPayments + tx.getAmount();
				}
				InvoiceItem item = createInvoiceItem(tx);
				item.setPaidAmount(tx.getAmount());
				item.setPaymentMethod(tx.getPaymentMethod());

				item.setRemarks(tx.getRemarks());
				item.setAttended(true);
				if (balance >= 0)
					item.setBalance(balance = balance - item.getPaidAmount());
				else
					item.setBalance(balance = balance + item.getPaidAmount());
				invoiceItems.add(item);
			}
		}

		/*
		 * for (Transaction tx : unbilledTxs) { while
		 * (tx.getProgramEvent().getEventDate().after(cal.getTime())) { if (ii
		 * != null) { invoiceItems.add(ii); cal.add(Calendar.DAY_OF_MONTH, 1);
		 * groups.clear(); } ii = new InvoiceItem();
		 * ii.setEventDate(cal.getTime());
		 * ii.setChargeType(EnumChargeType.EVENT); } if (ii == null ||
		 * !dateFormat.format(ii.getEventDate()).equals(
		 * dateFormat.format(tx.getProgramEvent() .getEventDate()))) { if (ii !=
		 * null) { invoiceItems.add(ii); cal.add(Calendar.DAY_OF_MONTH, 1);
		 * groups.clear(); } ii = new InvoiceItem();
		 * ii.setEventDate(cal.getTime());
		 * ii.setChargeType(EnumChargeType.EVENT);
		 * groups.add(tx.getProgramEvent().getGroup().getName());
		 * ii.setProgramName(tx.getProgramEvent().getGroup().getName()); } else
		 * { if (!groups.contains(tx.getProgramEvent().getGroup().getName())) {
		 * groups.add(tx.getProgramEvent().getGroup().getName());
		 * ii.setProgramName((ii.getProgramName() != null &&
		 * !ii.getProgramName().isEmpty() ? (ii .getProgramName() + ", ") : "")
		 * + tx.getProgramEvent().getGroup().getName()); } } setTxValues(tx,
		 * ii); } if (ii != null) invoiceItems.add(ii);
		 */
		for (Transaction tx : unbilledAdHocTxs) {
			if (tx.getTransactionType().equals(EnumTransactionType.DEBIT)) {
				totalPayment = totalPayment + tx.getAmount();
				if (tx.getPaymentMethod().equals("EFT")) {
					eftPayments = eftPayments + tx.getAmount();
				}
				InvoiceItem item = new InvoiceItem();
				item.setPaymentType(tx.getPaymentType());
				item.setTransactionDate(tx.getTransactionDate());
				item.setPaidAmount(tx.getAmount());
				item.setPaymentMethod(tx.getPaymentMethod());
				item.setAttended(true);
				if (tx.getPaymentType().equals(EnumPaymentType.COLLECTION)) {
					item.setChargeAmount(tx.getAmount());
					item.setRemarks(tx.getPaymentType().getId() + " : Payment for '" + tx.getCollection().getName()
							+ "'"
							+ (tx.getRemarks() != null && !tx.getRemarks().isEmpty() ? " - " + tx.getRemarks() : ""));
				} else if (tx.getPaymentType().equals(EnumPaymentType.PERSONAL))
					item.setRemarks(tx.getPaymentType().getId()
							+ (tx.getRemarks() != null && !tx.getRemarks().isEmpty() ? " : " + tx.getRemarks() : ""));
				if (tx.getPaymentType().equals(EnumPaymentType.OUTSTANDING))
					item.setRemarks(tx.getPaymentType().getId()
							+ (tx.getRemarks() != null && !tx.getRemarks().isEmpty() ? " : " + tx.getRemarks() : ""));
				if (balance >= 0)
					item.setBalance(balance = balance - item.getPaidAmount());
				else
					item.setBalance(balance = balance + item.getPaidAmount());

				tmpInvoice.getAdditionalInvoiceItems().add(item);
			} else {
				if (tx.getChargeType() != null && !tx.getChargeType().equals(EnumChargeType.COLLECTION)) {
					if (tx.isGstIncluded())
						taxableCharges = taxableCharges + tx.getAmount();
					InvoiceItem item = new InvoiceItem();
					item.setChargeType(tx.getChargeType());
					item.setTransactionDate(tx.getTransactionDate());
					item.setChargeAmount(tx.getAmount());
					// if(!tx.getPaymentType().equals(EnumPaymentType.OUTSTANDING)){
					item.setRemarks(tx.getChargeType().getId()
							+ (tx.getRemarks() != null && !tx.getRemarks().isEmpty() ? " : " + tx.getRemarks() : ""));
					item.setAttended(true);
					/*
					 * } else{ item.setRemarks(tx.getPaymentType().getId() +
					 * (tx.getRemarks() != null && !tx.getRemarks().isEmpty() ?
					 * " : " + tx.getRemarks() : "")); }
					 */

					tmpInvoice.getAdditionalInvoiceItems().add(item);
				}

				otherCharge = otherCharge + tx.getAmount();
				totalCharge = totalCharge + tx.getAmount();
			}
		}

		if (taxableCharges != 0) {
			BigDecimal taxBigDecimal = new BigDecimal(taxableCharges / 11).setScale(2, RoundingMode.HALF_UP);
			tmpInvoice.setTax(tmpInvoice.getTax() + taxBigDecimal.doubleValue());
		}

	}

	private InvoiceItem createInvoiceItem(Transaction tx) {
		InvoiceItem item = new InvoiceItem();
		item.setTransactionDate(tx.getTransactionDate());
		item.setChargeType(tx.getChargeType());
		item.setPaymentType(tx.getPaymentType());
		item.setEventDate(tx.getProgramEvent().getEventDate());
		item.setStudentGrp(tx.getProgramEvent().getGroup().getName());
		item.setProgramName(tx.getProgramEvent().getProgram().getName());
		item.setProgramType(tx.getProgramEvent().getProgram().getType().getName());
		return item;
	}

	public Invoice setEventItems(Invoice invoice) {
		invoice.setTmpItems(new ArrayList<InvoiceItem>());
		// if (invoice.getInvoiceItems().isEmpty())
		// return invoice;
		List<String> groups = new ArrayList<String>();

		// Calculate first and last dates of month
		Calendar cal = new GregorianCalendar(selectedYear, selectedMonth - 1, 1, 0, 0, 0);
		Date startDate = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date endDate = cal.getTime();

		// reset calendar date to the invoice start date
		cal = new GregorianCalendar(selectedYear, selectedMonth - 1, 1, 0, 0, 0);

		/*
		 * tmpStudent = serviceLocator.getStudentEventService()
		 * .retrieveStudentEvents(tmpInvoice.setStudent().getId(), startDate,
		 * endDate);
		 */

		List<StudentEvent> tmpStdEvents = serviceLocator.getStudentEventService()
				.retrieveStudentEvents(invoice.getStudent().getId(), startDate, endDate);
		// Map<Date,Boolean> tempItemsAdded=new HashMap<Date, Boolean>();
		InvoiceItem ii = null;
		for (InvoiceItem item : invoice.getInvoiceItems()) {
			// to add new record when there is a new group found
			if (ii != null && !(item.getStudentGrp().equals(ii.getGroups()))) {
				invoice.getTmpItems().add(ii);
				ii = new InvoiceItem();
				ii.setEventDate(cal.getTime());
			}

			// adding empty recodes till the item date
			while (item.getEventDate().after(cal.getTime())) {
				if (ii != null) {
					invoice.getTmpItems().add(ii);
					cal.add(Calendar.DAY_OF_MONTH, 1);
					groups.clear();
				}
				ii = new InvoiceItem();
				ii.setEventDate(cal.getTime());
			}
			/*
			 * if(!item.getEventDate().after(cal.getTime())){
			 * if(!tempItemsAdded.containsKey(item.getEventDate())){
			 * tempItemsAdded.put(item.getEventDate(), false); }
			 * if(!tempItemsAdded.get(item.getEventDate())){
			 * invoice.getTmpItems().add(ii);
			 * tempItemsAdded.put(item.getEventDate(), true); } }
			 **/

			if (ii == null || !dateFormat.format(ii.getEventDate()).equals(dateFormat.format(item.getEventDate()))) {
				if (ii != null) {
					invoice.getTmpItems().add(ii);
					cal.add(Calendar.DAY_OF_MONTH, 1);
					groups.clear();
				}
				ii = new InvoiceItem();
				ii.setEventDate(cal.getTime());
				groups.add(item.getStudentGrp());
				ii.setGroups(item.getStudentGrp());
				ii.setAttended(true);
			} else {
				if (!groups.contains(item.getStudentGrp())) {
					groups.add(item.getStudentGrp());
					ii.setGroups(item.getStudentGrp());
					ii.setAttended(true);
				}
			}
			if (item.getChargeAmount() != 0 && item.getPaidAmount() == 0) {
				if (item.getProgramType().equals("Transport")) {
					ii.setTransportCharge(item.getChargeAmount());
				} else {
					ii.setChargeAmount(item.getChargeAmount());
				}
			}
			if (item.getChargeAmount() == 0 && item.getPaidAmount() != 0) {
				ii.setPaidAmount(item.getPaidAmount());
				// if (item.getProgramType().equals("Transport")) {
				// ii.setPaidAmount(item.getPaidAmount());
				// } else {
				// ii.setPaidAmount(item.getPaidAmount());
				// }
			}
		}
		do {
			if (ii != null) {
				invoice.getTmpItems().add(ii);
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			ii = new InvoiceItem();
			ii.setEventDate(cal.getTime());
		} while (cal.getTime().before(endDate) || dateFormat.format(cal.getTime()).equals(dateFormat.format(endDate)));

		String itemDate = null;
		List<InvoiceItem> finalList = new ArrayList<InvoiceItem>();
		for (InvoiceItem item : invoice.getTmpItems()) {
			if (itemDate != null && itemDate.equals(dateFormat.format(item.getEventDate()))) {
				finalList.add(item);
				if (invoice.getTmpItems().indexOf(item) == invoice.getTmpItems().size() - 1) {
					for (StudentEvent event : tmpStdEvents) {
						if (itemDate.equals(dateFormat.format(event.getProEvent().getEventDate()))) {
							ii = new InvoiceItem();
							ii.setGroupname(event.getGroupedStudent().getGroup().getName());
							ii.setAttended(event.isAttended());
							cal.setTime(event.getProEvent().getEventDate());
							ii.setEventDate(cal.getTime());
							finalList.add(ii);
						}
					}
				}
				continue;
			} else {
				if (itemDate != null) {
					for (StudentEvent event : tmpStdEvents) {
						if (itemDate.equals(dateFormat.format(event.getProEvent().getEventDate()))) {
							ii = new InvoiceItem();
							ii.setGroupname(event.getGroupedStudent().getGroup().getName());
							ii.setAttended(event.isAttended());
							cal.setTime(event.getProEvent().getEventDate());
							ii.setEventDate(cal.getTime());
							finalList.add(ii);
						}
					}
				}
				finalList.add(item);
				itemDate = dateFormat.format(item.getEventDate());
			}
		}
		// add rows with values and remove empty rows.
		List<InvoiceItem> withOutEmpty = new ArrayList<InvoiceItem>();
		for (InvoiceItem invoiceItem : finalList) {
			if (invoiceItem.getChargeAmount() > 0 || invoiceItem.getPaidAmount() > 0
					|| invoiceItem.getTransportCharge() > 0
					|| (!invoiceItem.isAttended() && invoiceItem.getGroupname() != null) || (invoiceItem.isAttended()
							&& invoiceItem.getChargeAmount() == 0 && invoiceItem.getPaidAmount() == 0)) {
				withOutEmpty.add(invoiceItem);
			}
		}
		invoice.setTmpItems(withOutEmpty);
		return invoice;
	}

	/*
	 * private void setTxValues(Transaction tx, InvoiceItem ii) { if
	 * (tx.getTransactionType().equals(EnumTransactionType.CREDIT) &&
	 * tx.getChargeType().equals(EnumChargeType.EVENT)) {
	 * ii.setChargeAmount(ii.getChargeAmount() + tx.getAmount()); if
	 * (tx.getProgramEvent().getProgram().getType().getName()
	 * .equals("Transport")) transportCharge += tx.getAmount(); totalCharge +=
	 * tx.getAmount(); } if
	 * (tx.getTransactionType().equals(EnumTransactionType.DEBIT) &&
	 * tx.getPaymentType().equals(EnumPaymentType.EVENT)) { if
	 * (tx.getProgramEvent().getProgram().getType().getName()
	 * .equals("Transport")) { ii.setTransportCharge(ii.getTransportCharge() +
	 * tx.getAmount()); } else { ii.setPaidAmount(ii.getPaidAmount() +
	 * tx.getAmount()); } if (tx.getPaymentMethod() != null &&
	 * tx.getPaymentMethod().equals("EFT")) eftPayments += tx.getAmount();
	 * totalPayment += tx.getAmount(); } }
	 */

	public GroupedStudent getGroupstudent() {
		return groupstudent;
	}

	public void setGroupstudent(GroupedStudent groupstudent) {
		this.groupstudent = groupstudent;
	}

	public void searchStudents() {
		try {
			// See if the user has entered an ID instead name
			Long id = Long.parseLong(searchText);
			Student student = serviceLocator.getStudentService().retrieve(id);
			if (student == null || (!includeInactive && student.getStatus().equals("Inactive")))
				showError("No result available for this id.");
			else {
				students.clear();
				students.add(student);
				this.student = null;
			}
		} catch (NumberFormatException nFE) {
			List<Student> students = serviceLocator.getStudentService().listByName(searchText, includeInactive);
			if (students == null || students.isEmpty())
				showError("No results found for the given search text.");
			else {
				this.students = students;
				student = null;
			}
		}
	}

	public void selectStudent(ClickActionEvent re) {
		if (student != null)
			student.setUi_selected(false);
		adhocAmount = 0;
		student = (Student) re.getComponent().getAttributes().get("student");
		selectedInvMap = new HashMap<Long, Invoice>();
		refreshInvoicesByStudent();
		adHocTxs = serviceLocator.getTransactionService().listAdHocTxsByStudent(student.getId());
		if (!adHocTxs.isEmpty()) {
			for (Transaction tx : adHocTxs) {
				if (tx.getTransactionType().equals(EnumTransactionType.CREDIT) && tx.getChargeType() != null
						&& !tx.getChargeType().equals(EnumChargeType.COLLECTION)) {
					adhocAmount = adhocAmount + tx.getAmount();
				}
				if (tx.getTransactionType().equals(EnumTransactionType.DEBIT) && tx.getPaymentType() != null
						&& !tx.getPaymentType().equals(EnumPaymentType.COLLECTION)) {
					adhocAmount = adhocAmount - tx.getAmount();
				}
			}
		}
		student.setUi_selected(true);
		sessionContext.setActiveString(student.getContact().getName());
		outstandingEvents = serviceLocator.getStudentEventService().listOutstandingByStudent(student.getId());
		refreshOutstandingAmount();
		this.refreshAdhocTransaction();
		this.closePreview();
	}

	public void tabChanged(TabChangeEvent event) {
		tabIndex = event.getNewTabIndex();
		if (student != null && tabIndex == 0)
			refreshInvoicesByStudent();
	}

	private void refreshInvoicesByStudent() {
		selectAll = false;
		invoices = new ArrayList<Invoice>();
		List<Invoice> existingInvoices = serviceLocator.getInvoiceService().listByStudentId(student.getId(),
				selectedYear);

		for (int month : monthsList) {
			Invoice invoice = null;
			for (Invoice inv : existingInvoices) {
				cal.setTime(inv.getInvoiceDate());
				if (month == cal.get(Calendar.MONTH) + 1) {
					invoice = inv;
					break;
				}
			}
			if (invoice == null) {
				cal.set(selectedYear, month - 1, 01);
				invoice = new Invoice();
				invoice.setStudent(student);
				invoice.setInvoiceDate(cal.getTime());
			}
			missingStmntsStdIDs = new ArrayList<Long>();
			notBankedStdIDs = new ArrayList<Long>();
			List<Transaction> unbilledTxs = serviceLocator.getTransactionService().listUnbilledTxsByStudent(month,
					selectedYear, student.getId());
			if (!unbilledTxs.isEmpty()) {
				notBankedStdIDs = serviceLocator.getStudentService().listStudentsWithNotBankedEvents(month,
						selectedYear);
			} else {
				unbilledTxs = serviceLocator.getTransactionService().listUnbilledAdHocTxsByStudent(month, selectedYear,
						student.getId());
			}
			if (!unbilledTxs.isEmpty()) {
				if (!(selectedYear == startYear && month == startMonth)) {
					missingStmntsStdIDs = serviceLocator.getStudentService().listStudentsMissingPreviousMonthsInvoice(
							month, selectedYear, serviceLocator.getBusinessRulesService().getActStmtStartDate());
				}
				String initial = "Following conflict(s) have been found.";
				if (notBankedStdIDs.contains(student.getId())) {
					invoice.setStmntStatus(invoice.getStmntStatus().isEmpty() ? (initial + "\n  - Not banked events")
							: (invoice.getStmntStatus() + "\n  - Not banked events"));
				}
				if (missingStmntsStdIDs.contains(student.getId())) {
					invoice.setStmntStatus(
							invoice.getStmntStatus().isEmpty() ? (initial + "\n  - Missing previous month's statement")
									: (invoice.getStmntStatus() + "\n  - Missing previous month's statement"));
				}
			} else if (invoice.getId() == null) {
				invoice.setNoRecords(true);
			}
			invoices.add(invoice);
		}

	}

	private void refreshOutstandingAmount() {
		totalOutstanding = 0;
		if (outstandingEvents != null && !outstandingEvents.isEmpty()) {
			for (StudentEvent se : outstandingEvents) {
				totalOutstanding = totalOutstanding + (se.getTotalCharges() - se.getAmountPaid());
			}
		}
	}

	public void saveOutstandingEvents() {
		for (StudentEvent studentEvent : outstandingEvents) {
			Transaction paymentTx = null;
			double tmp = 0;
			if (studentEvent.getPaymentTxs().isEmpty()) {
				if (studentEvent.getAmountPaid() > 0) {
					if (studentEvent.getAmountPaid() <= studentEvent.getEventChargeTx().getAmount()) {
						paymentTx = new Transaction();
						paymentTx.setTransactionType(EnumTransactionType.DEBIT);
						paymentTx.setPaymentType(EnumPaymentType.EVENT);
						paymentTx.setPaymentMethod(studentEvent.getPaymentMethod());
						paymentTx.setStudentEvent(studentEvent);
						paymentTx.setStudentId(studentEvent.getGroupedStudent().getStudent().getId());
						paymentTx.setProgramEvent(studentEvent.getProEvent());
						paymentTx.setAmount(studentEvent.getAmountPaid());
						paymentTx.setCreatedUserId(sessionContext.getUser().getId());
						paymentTx.setTransactionDate(new Date());
						studentEvent.getPaymentTxs().add(paymentTx);
					} else {
						tmp = studentEvent.getAmountPaid();
						showError("[" + Util.getMessage("student_label") + " ID: "
								+ studentEvent.getGroupedStudent().getStudent().getId()
								+ "] Amount cannot exceed the charge amount");
						studentEvent.setAmountPaid(0);
					}
				} else if (studentEvent.getAmountPaid() < 0) {
					tmp = studentEvent.getAmountPaid();
					showError("[" + Util.getMessage("student_label") + " ID: "
							+ studentEvent.getGroupedStudent().getStudent().getId()
							+ "] Initial payment can not be a negative value.");
					studentEvent.setAmountPaid(0);
				}
			}
			serviceLocator.getStudentEventService().update(studentEvent);
			if (tmp != 0)
				studentEvent.setAmountPaid(tmp);
		}
		showInfo("Outstanding events updated successfully.");
		outstandingEvents = serviceLocator.getStudentEventService().listOutstandingByStudent(student.getId());
		refreshOutstandingAmount();
	}

	public void createNewPayment(ActionEvent ae) {
		studentEvent = (StudentEvent) ae.getComponent().getAttributes().get("stdEvent");
		paymentTypes = new ArrayList<SelectItem>();
		paymentTypes.add(new SelectItem(EnumPaymentType.EVENT, EnumPaymentType.EVENT.getId()));
		if (!studentEvent.getOtherChargeTxs().isEmpty()) {
			for (Transaction tx : studentEvent.getOtherChargeTxs()) {
				if (tx.getChargeType().equals(EnumChargeType.PERSONAL)) {
					paymentTypes.add(new SelectItem(EnumPaymentType.PERSONAL, EnumPaymentType.PERSONAL.getId()));
					break;
				}
			}
		}
		paymentTypes.add(new SelectItem(EnumPaymentType.COLLECTION, EnumPaymentType.COLLECTION.getId()));
		initPayment();
		paymentPopup();
	}

	private void initPayment() {
		double paid = 0;
		for (Transaction tx : studentEvent.getPaymentTxs()) {
			if (tx.getPaymentType().equals(EnumPaymentType.EVENT)) {
				paid = paid + tx.getAmount();
			}
		}
		studentEvent.setTmpAmount(studentEvent.getEventChargeTx().getAmount() - paid);
		// payType = EnumPaymentType.EVENT;
		collectionId = new Long(0);
		paymentTx = new Transaction();
		paymentTx.setTransactionType(EnumTransactionType.DEBIT);
		paymentTx.setPaymentType(EnumPaymentType.EVENT);
		paymentTx.setStudentEvent(studentEvent);
		paymentTx.setStudentId(studentEvent.getGroupedStudent().getStudent().getId());
		paymentTx.setProgramEvent(studentEvent.getEventChargeTx().getProgramEvent());
		paymentTx.setAmount(studentEvent.getEventChargeTx().getAmount() - paid);
		paymentTx.setCreatedUserId(sessionContext.getUser().getId());
		paymentTx.setTransactionDate(new Date());
	}

	public void addPayment(ActionEvent ae) {
		EnumPaymentType payType = paymentTx.getPaymentType();
		String addfor = (String) ae.getComponent().getAttributes().get("add");
		if (paymentTx.getTransactionDate() == null) {
			showError("Transaction date cannot be empty.");
			return;
		}
		cal.setTime(paymentTx.getTransactionDate());
		if (serviceLocator.getInvoiceService().retrieve(student.getId(), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.YEAR)) != null) {
			// showError("Activity statement is already generated for the given
			// transaction date's month.");
			showError("You may need to regenarate activity statements again");
			// return;
		}
		if (paymentTx.getAmount() == 0) {
			showError("Amount cannot be zero(0).");
			return;
		}
		if (payType == EnumPaymentType.COLLECTION && collectionId == 0) {
			showError("Please select a collection.");
			return;
		}
		if (addfor.equals("outstanding")) {
			if (paymentTx.getAmount() < 0 && paymentTx.getRemarks().isEmpty()) {
				showError("Please add a remark..");
				return;

			}
			if (payType == EnumPaymentType.EVENT) {
				if (paymentTx.getAmount() > 0 && (paymentTx.getAmount() > (studentEvent.getTmpAmount()))) {
					showError("New amount should not exceed the amount payable..");
					return;
				}
				if (paymentTx.getAmount() < -(studentEvent.getEventChargeTx().getProgramEvent().getChargeAmount()
						- studentEvent.getTmpAmount())) {
					showError("Amount can not exceeds the total amount paid as " + payType.getId() + " charges..");
					return;
				}
			}
			if (payType == EnumPaymentType.PERSONAL) {
				if (paymentTx.getAmount() > 0 && (paymentTx.getAmount() > (studentEvent.getTmpAmount()))) {
					showError("New amount should not exceed the amount payable..");
					return;
				}
				double total = 0;
				for (Transaction tx : studentEvent.getOtherChargeTxs()) {
					total = total + tx.getAmount();
				}
				if (paymentTx.getAmount() < -(total - studentEvent.getTmpAmount())) {
					showError("Amount can not exceeds the total amount paid for additional charges..");
					return;
				}
			}
			if (payType == EnumPaymentType.COLLECTION) {
				if (paymentTx.getAmount() < -(studentEvent.getTmpAmount())) {
					showError("Amount can not exceeds the total amount paid for the " + payType.getId() + "..");
					return;
				}
				/* adding charge of type collection */
				Transaction t = new Transaction();
				t.setTransactionType(EnumTransactionType.CREDIT);
				t.setStudentEvent(studentEvent);
				t.setStudentId(studentEvent.getGroupedStudent().getStudent().getId());
				t.setProgramEvent(studentEvent.getEventChargeTx().getProgramEvent());
				t.setCollection(paymentTx.getCollection());
				t.setChargeType(EnumChargeType.COLLECTION);
				t.setAmount(paymentTx.getAmount());
				t.setCreatedUserId(sessionContext.getUser().getId());
				t.setTransactionDate(new Date());
				t.setRemarks("Charge for " + " '" + paymentTx.getCollection().getName() + "'");
				studentEvent.getOtherChargeTxs().add(t);

			}
			studentEvent.getPaymentTxs().add(paymentTx);
			initPayment();
		}
		if (addfor.equals("adhoc")) {
			if (paymentTx.getAmount() < 0 && paymentTx.getRemarks().isEmpty()) {
				showError("Please add a remark.");
				return;
			}
			if (payType == EnumPaymentType.PERSONAL) {
				if (paymentTx.getAmount() > 0 && (paymentTx.getAmount() > (adhocAmountPayable))) {
					showError("New amount should not exceed the amount payable..");
					return;
				}
				double total = 0;
				for (Transaction tx : adHocTxs) {
					total = total + tx.getAmount();
				}
				if (paymentTx.getAmount() < -(total)) {
					showError("Amount can not exceeds the total amount paid for additional charges..");
					return;
				}
			}
			if (payType == EnumPaymentType.COLLECTION) {
				if (paymentTx.getAmount() < -(paymentTx.getCollection().getAmount() - adhocAmountPayable)) {
					showError("Amount can not exceeds the total amount paid for the " + payType.getId() + "..");
					return;
				}
				/* for adding charge for collection */
				Transaction t = new Transaction();
				t.setTransactionType(EnumTransactionType.CREDIT);
				t.setStudentId(student.getId());
				t.setCollection(paymentTx.getCollection());
				t.setChargeType(EnumChargeType.COLLECTION);
				t.setAmount(paymentTx.getAmount());
				t.setCreatedUserId(sessionContext.getUser().getId());
				t.setTransactionDate(paymentTx.getTransactionDate());
				t.setRemarks("Charge for " + " '" + paymentTx.getCollection().getName() + "'");
				tempCollTxs.add(t);
			}
			adhocAmountPayable = adhocAmountPayable - paymentTx.getAmount();
			tempTxs.add(paymentTx);
			adhocAmountPayable = 0;
			initAdhocPaymentx();
			double charge = 0;
			double paid = 0;
			for (Transaction tx : tempTxs) {
				if (tx.getChargeType() != null) {
					if (tx.getChargeType().equals(EnumChargeType.PERSONAL)) {
						charge = charge + tx.getAmount();
					}
				}
				if (tx.getPaymentType() != null && tx.getId() == null) {
					if (tx.getPaymentType().equals(EnumPaymentType.PERSONAL)) {
						paid = paid + tx.getAmount();
					}
				}
			}
			adhocAmountPayable = charge + adhocAmount - paid;
			if (adhocAmountPayable == 0) {
				paymentTx.setPaymentType(EnumPaymentType.COLLECTION);
			}
		}
	}

	public void savePayment(ActionEvent ae) {
		String savefor = (String) ae.getComponent().getAttributes().get("save");
		// if (savefor.equals("outstanding")) {
		// double tmp = 0;
		// for (Transaction tx : studentEvent.getPaymentTxs()) {
		// tmp = tmp + tx.getAmount();
		// }
		// studentEvent.setAmountPaid(tmp);
		// serviceLocator.getStudentEventService().update(studentEvent);
		//
		// ProgramEvent programEvent = serviceLocator.getProgramEventService()
		// .retrieveWithData(studentEvent.getProEvent().getId(), true,
		// false);
		// if (programEvent != null && programEvent.getStudentEvents() != null)
		// {
		// double totalMoney = 0;
		// double totalEFT = 0;
		// for (StudentEvent se : programEvent.getStudentEvents()) {
		// for (Transaction tx : se.getPaymentTxs()) {
		// if (tx.getPaymentMethod().equals("EFT"))
		// totalEFT = totalEFT + tx.getAmount();
		// else
		// totalMoney = totalMoney + tx.getAmount();
		// }
		// }
		// programEvent.setTotalMoneyCollected(totalMoney);
		// programEvent.setTotalEFTCollected(totalEFT);
		// serviceLocator.getProgramEventService().update(programEvent);
		// }
		// refreshOutstandingAmount();
		// visiblePayment = false;
		// }
		if (savefor.equals("adhoc")) {
			if (tempTxs != null && !tempTxs.isEmpty()) {
				List<Transaction> tmpTxs = new ArrayList<Transaction>();
				for (Transaction tx : tempTxs) {
					if (tx.getId() == null) {
						tx = serviceLocator.getTransactionService().create(tx);
						adHocTxs.add(tx);
					}
				}
				if (!tmpTxs.isEmpty())
					tempTxs.removeAll(tmpTxs);
			}
			for (Transaction tx : deleteTxs) {
				adHocTxs.remove(tx);
				serviceLocator.getTransactionService().delete(tx.getId());
			}
			if (tempCollTxs != null && !tempCollTxs.isEmpty()) {
				for (Transaction tx : tempCollTxs) {
					tx = serviceLocator.getTransactionService().create(tx);
					adHocTxs.add(tx);
				}
			}
			for (Transaction tx : adHocTxs) {
				if (tx.getTransactionType().equals(EnumTransactionType.CREDIT) && tx.getChargeType() != null
						&& !tx.getChargeType().equals(EnumChargeType.COLLECTION))
					adhocAmount = adhocAmount + tx.getAmount();
				if (tx.getTransactionType().equals(EnumTransactionType.DEBIT) && tx.getPaymentType() != null
						&& !tx.getPaymentType().equals(EnumPaymentType.COLLECTION))
					adhocAmount = adhocAmount - tx.getAmount();
			}
			refreshAdhocTransaction();
			visibleAdhocPayment = false;
		}
	}

	public void deletePayment(ActionEvent ae) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String txType = (String) ae.getComponent().getAttributes().get("txType");
		Transaction tx = (Transaction) ae.getComponent().getAttributes().get("tx");
		if (txType.equals("event")) {
			if (tx.getCollection() != null) {
				for (Transaction charge : studentEvent.getOtherChargeTxs()) {
					if (charge.getCollection() != null && tx.getCollection().equals(charge.getCollection())
							&& tx.getAmount() == charge.getAmount() && dateFormat.format(tx.getTransactionDate())
									.equals(dateFormat.format(charge.getTransactionDate()))) {
						studentEvent.getTmpTxList().add(charge);
						studentEvent.getOtherChargeTxs().remove(charge);
						break;
					}
				}
			}
			if (tx.getId() != null) {
				studentEvent.getTmpTxList().add(tx);
			}
			studentEvent.getPaymentTxs().remove(tx);
		}
		if (txType.equals("adhoc")) {
			if (tx.getCollection() != null) {
				if (tx.getId() != null) {
					for (Transaction charge : adHocTxs) {
						if (charge.getCollection() != null && tx.getCollection().equals(charge.getCollection())
								&& tx.getAmount() == charge.getAmount() && dateFormat.format(tx.getTransactionDate())
										.equals(dateFormat.format(charge.getTransactionDate()))) {
							deleteTxs.add(charge);
							break;
						}
					}
				} else {
					for (Transaction charge : tempCollTxs) {
						if (charge.getCollection() != null && tx.getCollection().equals(charge.getCollection())
								&& tx.getAmount() == charge.getAmount() && dateFormat.format(tx.getTransactionDate())
										.equals(dateFormat.format(charge.getTransactionDate()))) {
							tempCollTxs.remove(charge);
							break;
						}
					}
				}
			}
			tempTxs.remove(tx);
			if (tx.getId() != null) {
				deleteTxs.add(tx);
				adhocAmount = adhocAmount + tx.getAmount();
				adhocAmountPayable = adhocAmountPayable + tx.getAmount();
			}
		}
	}

	public void createNewCharge(ActionEvent ae) {
		studentEvent = (StudentEvent) ae.getComponent().getAttributes().get("stdEvent");
		initChargeTx();
		chargePopup();
	}

	private void initChargeTx() {
		chargeTx = new Transaction();
		chargeTx.setChargeType(EnumChargeType.PERSONAL);
		chargeTx.setTransactionType(EnumTransactionType.CREDIT);
		chargeTx.setStudentEvent(studentEvent);
		chargeTx.setStudentId(studentEvent.getGroupedStudent().getStudent().getId());
		chargeTx.setProgramEvent(studentEvent.getEventChargeTx().getProgramEvent());
		chargeTx.setCreatedUserId(sessionContext.getUser().getId());
		chargeTx.setTransactionDate(new Date());
	}

	public void addCharge(ActionEvent ae) {
		String addfor = (String) ae.getComponent().getAttributes().get("add");
		if (chargeTx.getTransactionDate() == null) {
			showError("Transaction date cannot be empty.");
			return;
		}
		cal.setTime(chargeTx.getTransactionDate());
		if (serviceLocator.getInvoiceService().retrieve(student.getId(), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.YEAR)) != null) {
			showError("Activity statement is already generated for the given transaction date's month.");
			return;
		}
		if (chargeTx.getAmount() == 0) {
			showError("Amount can not be zero(0).");
			return;
		}
		if (chargeTx.getRemarks().isEmpty()) {
			showError("Please add a remark.");
			return;
		}
		if (addfor.equals("outstanding")) {
			if (chargeTx.getAmount() < 0) {
				double total = 0;
				for (Transaction tx : studentEvent.getOtherChargeTxs()) {
					total = total + tx.getAmount();
				}
				if (chargeTx.getAmount() < -total) {
					showError("Amount can not exceeds the total amount charged as additional charges.");
					return;
				}
			}
			studentEvent.getOtherChargeTxs().add(chargeTx);
			initChargeTx();
		}
		if (addfor.equals("adhoc")) {
			if (chargeTx.getAmount() < 0) {
				double total = 0;
				for (Transaction tx : tempTxs) {
					total = total + tx.getAmount();
				}
				if (chargeTx.getAmount() < -total) {
					showError("Amount can not exceeds the total amount charged as additional charges.");
					return;
				}
			}
			tempTxs.add(chargeTx);
			initAdhocChargeTx();
		}
	}

	public void saveCharges(ActionEvent ae) {
		String savefor = (String) ae.getComponent().getAttributes().get("save");
		if (savefor.equals("outstanding")) {
			serviceLocator.getStudentEventService().update(studentEvent);
			refreshOutstandingAmount();
			visibleCharge = false;
		}
		if (savefor.equals("adhoc")) {
			if (!tempTxs.isEmpty() && tempTxs != null) {
				for (Transaction tx : tempTxs) {
					if (tx.getId() == null) {
						tx = serviceLocator.getTransactionService().create(tx);
						adHocTxs.add(tx);
					}
				}
			}
			refreshAdhocTransaction();
			visibleAdhocCharge = false;
		}
	}

	public void chargePopup() {
		if (visibleCharge) {
			List<Transaction> otherChrgTxs = new ArrayList<Transaction>();
			for (Transaction tx : studentEvent.getOtherChargeTxs()) {
				if (tx.getId() == null)
					otherChrgTxs.add(tx);
			}
			if (!otherChrgTxs.isEmpty())
				studentEvent.getOtherChargeTxs().removeAll(otherChrgTxs);
			visibleCharge = false;
		} else {
			visibleCharge = true;
		}
	}

	public void changePaymentType(ValueChangeEvent ve) {
		EnumPaymentType type = (EnumPaymentType) ve.getNewValue();
		String paymentFor = (String) ve.getComponent().getAttributes().get("payment");
		if (paymentFor.equals("outstanding")) {
			if (type != EnumPaymentType.COLLECTION) {
				double paid = 0;
				for (Transaction tx : studentEvent.getPaymentTxs()) {
					if (tx.getPaymentType().equals(type)) {
						paid = paid + tx.getAmount();
					}
				}
				if (type.equals(EnumPaymentType.EVENT)) {
					studentEvent
							.setTmpAmount(studentEvent.getEventChargeTx().getProgramEvent().getChargeAmount() - paid);
				}
				if (type.equals(EnumPaymentType.PERSONAL)) {
					double charge = 0;
					for (Transaction tx : studentEvent.getOtherChargeTxs()) {
						if (tx.getChargeType().equals(EnumChargeType.PERSONAL)) {
							charge = charge + tx.getAmount();
						}
					}
					studentEvent.setTmpAmount(charge - paid);
				}
			} else if (collectionId != 0) {
				double paid = 0;
				for (Transaction tx : studentEvent.getPaymentTxs()) {
					if (tx.getPaymentType().equals(EnumPaymentType.COLLECTION)
							&& tx.getCollection().getId().equals(collectionId))
						paid = paid + tx.getAmount();
				}
				studentEvent.setTmpAmount(paid);
			} else {
				studentEvent.setTmpAmount(0);
			}
			paymentTx.setPaymentType(type);
			if (!type.equals(EnumPaymentType.COLLECTION)) {
				paymentTx.setAmount(studentEvent.getTmpAmount());
			} else {
				paymentTx.setAmount(0);
			}
			if (type != null && type.equals(EnumPaymentType.COLLECTION)) {
				collection = serviceLocator.getCollectionService().listActiveCollections();
			}
		}
		if (paymentFor.equals("adhoc")) {
			if (type != EnumPaymentType.COLLECTION) {
				double paid = 0;
				for (Transaction tx : tempTxs) {
					if (tx.getPaymentType() != null && tx.getId() == null && tx.getPaymentType().equals(type)) {
						paid = paid + tx.getAmount();
					}
				}
				if (type.equals(EnumPaymentType.PERSONAL)) {
					double charge = 0;
					for (Transaction tx : tempTxs) {
						if (tx.getChargeType() != null && tx.getChargeType().equals(EnumChargeType.PERSONAL)) {
							charge = charge + tx.getAmount();
						}
					}
					adhocAmountPayable = adhocAmount + charge - paid;
					paymentTx.setAmount(adhocAmountPayable);
				}
			} else if (collectionId != 0) {
				double paid = 0;
				for (Transaction tx : tempTxs) {
					if (tx.getPaymentType().equals(EnumPaymentType.COLLECTION)
							&& tx.getCollection().getId().equals(collectionId))
						paid = paid + tx.getAmount();
				}
				adhocAmountPayable = paid;
			} else if (collectionId == 0) {
				adhocAmountPayable = 0;
			}
			paymentTx.setPaymentType(type);
		}
	}

	public void collectionChanged(ValueChangeEvent ve) {
		collectionId = (Long) ve.getNewValue();
		String collectionfor = (String) ve.getComponent().getAttributes().get("collection");
		if (collectionfor.equals("outstanding")) {
			double paid = 0;
			for (Transaction tx : studentEvent.getPaymentTxs()) {
				if (tx.getPaymentType().equals(EnumPaymentType.COLLECTION) && tx.getCollection() != null
						&& tx.getCollection().getId().equals(collectionId))
					paid = paid + tx.getAmount();
			}
			if (collectionId != null && collectionId != 0) {
				paymentTx.setCollection(serviceLocator.getCollectionService().retrieve(collectionId));
				studentEvent.setTmpAmount(paid);
			}
		}
		if (collectionfor.equals("adhoc")) {
			double paid = 0;
			for (Transaction tx : tempTxs) {
				if (tx.getPaymentType().equals(EnumPaymentType.COLLECTION) && tx.getCollection() != null
						&& tx.getCollection().getId().equals(collectionId)) {
					paid = paid + tx.getAmount();
				}
			}
			if (collectionId != null && collectionId != 0) {
				paymentTx.setCollection(serviceLocator.getCollectionService().retrieve(collectionId));
				adhocAmountPayable = paid;
			}
		}
	}

	public void paymentPopup() {
		if (visiblePayment) {
			List<Transaction> paymentTxs = new ArrayList<Transaction>();
			List<Transaction> otherChrgTxs = new ArrayList<Transaction>();
			for (Transaction tx : studentEvent.getPaymentTxs()) {
				if (tx.getId() == null)
					paymentTxs.add(tx);
			}
			for (Transaction tx : studentEvent.getOtherChargeTxs()) {
				if (tx.getId() == null)
					otherChrgTxs.add(tx);
			}
			if (!paymentTxs.isEmpty())
				studentEvent.getPaymentTxs().removeAll(paymentTxs);
			if (!otherChrgTxs.isEmpty())
				studentEvent.getOtherChargeTxs().removeAll(otherChrgTxs);
			if (!studentEvent.getTmpTxList().isEmpty()) {
				for (Transaction tx : studentEvent.getTmpTxList()) {
					if (tx.getTransactionType().equals(EnumTransactionType.CREDIT)) {
						studentEvent.getOtherChargeTxs().add(tx);
					} else {
						studentEvent.getPaymentTxs().add(tx);
					}
				}
				studentEvent.setTmpTxList(new ArrayList<Transaction>());
			}
			visiblePayment = false;
		} else
			visiblePayment = true;
	}

	public void adhocCharge() {
		tempTxs = new ArrayList<Transaction>();
		if (adHocTxs != null && !adHocTxs.isEmpty()) {
			for (Transaction tx : adHocTxs) {
				if (tx.getTransactionType().equals(EnumTransactionType.CREDIT)) {
					tempTxs.add(tx);
				}
			}
		}
		initAdhocChargeTx();
		adhocChargePopup();
	}

	public void adhocChargePopup() {
		if (visibleAdhocCharge) {
			visibleAdhocCharge = false;
		} else {
			visibleAdhocCharge = true;
		}
	}

	public void adhocPayment() {
		tempCollTxs = new ArrayList<Transaction>();
		collection = serviceLocator.getCollectionService().listActiveCollections();
		adhocAmountPayable = 0;
		tempTxs = new ArrayList<Transaction>();
		if (adHocTxs != null && !adHocTxs.isEmpty()) {
			for (Transaction tx : adHocTxs) {
				if (tx.getTransactionType().equals(EnumTransactionType.DEBIT)) {
					tempTxs.add(tx);
				}
			}
		}
		paymentTypes = new ArrayList<SelectItem>();
		this.refreshOutstandingAmount();
		adhocAmountPayable = this.totalOutstanding;
		if (adHocTxs != null && !adHocTxs.isEmpty()) {
			for (Transaction tx : adHocTxs) {
				if (tx.getChargeType() != null) {
					if (tx.getChargeType().equals(EnumChargeType.PERSONAL)) {
						adhocAmountPayable = adhocAmountPayable + tx.getAmount();
					}
				}
				if (tx.getPaymentType() != null) {
					if (tx.getPaymentType().equals(EnumPaymentType.PERSONAL)) {
						adhocAmountPayable = adhocAmountPayable - tx.getAmount();
					}
					if (tx.getPaymentType().equals(EnumPaymentType.OUTSTANDING)) {
						adhocAmountPayable = adhocAmountPayable - tx.getAmount();
					}
				}
			}

			if (adhocAmountPayable != 0) {
				paymentTypes.add(new SelectItem(EnumPaymentType.PERSONAL, EnumPaymentType.PERSONAL.getId()));
			}
		}
		if (this.totalOutstanding != 0) {
			paymentTypes.add(new SelectItem(EnumPaymentType.OUTSTANDING, EnumPaymentType.OUTSTANDING.getId()));
		}
		paymentTypes.add(new SelectItem(EnumPaymentType.COLLECTION, EnumPaymentType.COLLECTION.getId()));
		initAdhocPaymentx();
		if (adhocAmountPayable == 0) {
			paymentTx.setPaymentType(EnumPaymentType.COLLECTION);
		} else {
			paymentTx.setAmount(adhocAmountPayable);
		}
		deleteTxs = new ArrayList<Transaction>();
		adhocPaymentPopup();
	}

	public void adhocPaymentPopup() {
		if (visibleAdhocPayment) {
			visibleAdhocPayment = false;
			refreshAdhocTransaction();
		} else {
			visibleAdhocPayment = true;
		}
	}

	private void initAdhocChargeTx() {
		chargeTx = new Transaction();
		chargeTx.setChargeType(EnumChargeType.PERSONAL);
		chargeTx.setTransactionType(EnumTransactionType.CREDIT);
		chargeTx.setStudentId(student.getId());
		chargeTx.setCreatedUserId(sessionContext.getUser().getId());
		chargeTx.setTransactionDate(new Date());
	}

	public void initAdhocPaymentx() {
		collectionId = new Long(0);
		paymentTx = new Transaction();
		paymentTx.setTransactionType(EnumTransactionType.DEBIT);
		paymentTx.setPaymentType(EnumPaymentType.PERSONAL);
		paymentTx.setStudentId(student.getId());
		paymentTx.setCreatedUserId(sessionContext.getUser().getId());
		paymentTx.setTransactionDate(new Date());
	}

	private void refreshAdhocTransaction() {
		adhocAmount = 0;
		this.totalOutstanding = 0;
		if (adHocTxs != null && !adHocTxs.isEmpty()) {
			for (Transaction tx : adHocTxs) {
				if (tx.getTransactionType().equals(EnumTransactionType.CREDIT) && tx.getChargeType() != null
						&& !tx.getChargeType().equals(EnumChargeType.COLLECTION)) {
					adhocAmount = adhocAmount + tx.getAmount();
				}
				if (tx.getTransactionType().equals(EnumTransactionType.DEBIT) && tx.getPaymentType() != null
						&& !tx.getPaymentType().equals(EnumPaymentType.COLLECTION)) {
					adhocAmount = adhocAmount - tx.getAmount();
				}
			}
		}
		this.refreshOutstandingAmount();
		this.adhocAmount += this.totalOutstanding;
	}

	private void refreshMonths() {
		serviceLocator.getTransactionService().refreshMonthClasses(selectedYear, monthsList, monthClasses);
	}

	/*
	 * getters and setters
	 */

	public Invoice getInvoice() {
		return invoice;
	}

	public double getPreviousPayments() {
		return previousPayments;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return student;
	}

	public List<StudentEvent> getOutstandingEvents() {
		return outstandingEvents;
	}

	public double getTotalOutstanding() {
		return totalOutstanding;
	}

	public boolean isVisiblePayment() {
		return visiblePayment;
	}

	public boolean isVisibleCharge() {
		return visibleCharge;
	}

	public void setVisibleCharge(boolean visibleCharge) {
		this.visibleCharge = visibleCharge;
	}

	public boolean isVisibleAdhocPayment() {
		return visibleAdhocPayment;
	}

	public void setVisibleAdhocPayment(boolean visibleAdhocPayment) {
		this.visibleAdhocPayment = visibleAdhocPayment;
	}

	public boolean isVisibleAdhocCharge() {
		return visibleAdhocCharge;
	}

	public void setVisibleAdhocCharge(boolean visibleAdhocCharge) {
		this.visibleAdhocCharge = visibleAdhocCharge;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public ActivityStatement getGroupList() {
		return groupList;
	}

	public void setGroupList(ActivityStatement groupList) {
		this.groupList = groupList;
	}

	public void setSelectedMonth(int selectedMonth) {
		this.selectedMonth = selectedMonth;
	}

	public int getSelectedMonth() {
		return selectedMonth;
	}

	public double setOtherCharge(double otherCharge) {
		this.otherCharge = otherCharge;
		return otherCharge;
	}

	public double getOtherCharge() {
		return otherCharge;
	}

	/*
	 * public void setPayType(EnumPaymentType payType) { this.payType = payType;
	 * }
	 * 
	 * public EnumPaymentType getPayType() { return payType; }
	 */

	public void setCollectionId(Long collectionId) {
		this.collectionId = collectionId;
	}

	public Long getCollectionId() {
		return collectionId;
	}

	public void setCollection(List<Collection> collection) {
		this.collection = collection;
	}

	public List<Collection> getCollection() {
		return collection;
	}

	public void setPaymentTypes(List<SelectItem> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	public List<SelectItem> getPaymentTypes() {
		return paymentTypes;
	}

	public Transaction getPaymentTx() {
		return paymentTx;
	}

	public void setPaymentTx(Transaction paymentTx) {
		this.paymentTx = paymentTx;
	}

	public Transaction getChargeTx() {
		return chargeTx;
	}

	public void setChargeTx(Transaction chargeTx) {
		this.chargeTx = chargeTx;
	}

	public void setAdHocTxs(List<Transaction> adHocTxs) {
		this.adHocTxs = adHocTxs;
	}

	public List<Transaction> getAdHocTxs() {
		return adHocTxs;
	}

	public void setAdhocAmount(double adhocAmount) {
		this.adhocAmount = adhocAmount;
	}

	public double getAdhocAmount() {
		return adhocAmount;
	}

	public void setTempTxs(List<Transaction> tempTxs) {
		this.tempTxs = tempTxs;
	}

	public List<Transaction> getTempTxs() {
		return tempTxs;
	}

	public void setAdhocAmountPayable(double adhocAmountPayable) {
		this.adhocAmountPayable = adhocAmountPayable;
	}

	public double getAdhocAmountPayable() {
		return adhocAmountPayable;
	}

	public void setTempCollTxs(List<Transaction> tempCollTxs) {
		this.tempCollTxs = tempCollTxs;
	}

	public List<Transaction> getTempCollTxs() {
		return tempCollTxs;
	}

	public void setSelectedYear(int selectedYear) {
		this.selectedYear = selectedYear;
	}

	public int getSelectedYear() {
		return selectedYear;
	}

	public HashMap<Integer, String> getMonthClasses() {
		return monthClasses;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public SimpleDateFormat getMonthFormat() {
		return monthFormat;
	}

	public HashMap<Long, Invoice> getSelectedInvMap() {
		return selectedInvMap;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public void setIncludeInactive(boolean includeInactive) {
		this.includeInactive = includeInactive;
	}

	public boolean isIncludeInactive() {
		return includeInactive;
	}

	public void setMonthFormatMM(SimpleDateFormat monthFormatMM) {
		this.monthFormatMM = monthFormatMM;
	}

	public SimpleDateFormat getMonthFormatMM() {
		return monthFormatMM;
	}

	public SimpleDateFormat getYearFormatYYYY() {
		return yearFormatYYYY;
	}

	public void setYearFormatYYYY(SimpleDateFormat yearFormatYYYY) {
		this.yearFormatYYYY = yearFormatYYYY;
	}

	public StudentEvent getStudentEvent() {
		return studentEvent;
	}

	public void setStudentEvent(StudentEvent studentEvent) {
		this.studentEvent = studentEvent;
	}
}//
