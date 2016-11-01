package com.itelasoft.pso.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.itelasoft.pso.beans.AuthorisedStaff;
import com.itelasoft.pso.beans.InternalOrganization;
import com.itelasoft.pso.beans.NdisAncillaryCost;
import com.itelasoft.pso.beans.NdisCommittedEvent;
import com.itelasoft.pso.beans.NdisInvoice;
import com.itelasoft.pso.beans.NdisInvoiceItem;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentEvent;
import com.itelasoft.pso.beans.StudentGroup;
import com.itelasoft.util.SortObjectByName;

import edu.emory.mathcs.backport.java.util.Collections;

@ManagedBean(name = "ndisInvoiceManagerModel")
@SessionScoped
public class NdisInvoiceManagerModel extends UIModel {

	private List<SelectItem> studentSelectItemList;
	private InternalOrganization internalOrganization;
	private List<SelectItem> staffList;
	private List<AuthorisedStaff> authorizedStaffs;
	private List<InternalOrganization> registrationCodes;
	private List<NdisInvoice> invoiceList;
	private List<NdisCommittedEvent> commitedEventList;
	private List<NdisAncillaryCost> ancillaryCostList;
	private Date weekDate;
	private String timePeriod;
	private Long studentId;
	private Long staffId;
	private String registrationCode;
	private List<Student> studentList, slectedInvoiceStudentList, selectedStudentList;
	// private Calendar calendar = Calendar.getInstance();
	private Date startDate;
	private Date endDate;
	private Date filterStartDate, filterEndDate;
	private String uom;
	private Double quantity;
	private Long ancillaryCount;
	private String reportDate;
	// private Student student;
	private boolean showEvents, showAncillary, selectStudent, showCreateNewInvoice;
	// private HashMap<Long, Student> studentMap;
	private HashMap<Long, AuthorisedStaff> staffMap;
	private HashMap<Long, InternalOrganization> registrationCodeMap;
	private String commaDelimiter = ",";// Delimiter used in CSV file
	private String newLineSeparater = "\n";
	private String generetedDate, dateRange, authorizedStaffName;
	private int invoiceId, numberOfStudent;
	private String searchNdisStudentText, rosterTimeperiod;
	private static final String fileHeader = "RegistrationNumber,NDISNumber,SupportsDeliveredFrom,SupportsDeliveredTo,SupportNumber,ClaimReference,Quantity,Hours,UnitPrice,GSTCode,AuthorisedBy,ParticipantApproved";
	FileWriter fileWriter = null;
	Long count = new Long(1);
	AuthorisedStaff staff;
	String invoiceName;

	public NdisInvoiceManagerModel() {
		init();
	}

	public void init() {
		invoiceList = serviceLocator.getNdisInvoiceService().findAll();
		dateRange = null;
		authorizedStaffName = null;
		generetedDate = null;
		numberOfStudent = 0;
		invoiceId = 0;
		slectedInvoiceStudentList = null;
		weekDate = null;
	}

	private void initCreateNewInvoice() {
		internalOrganization = null;
		List<InternalOrganization> orgs = new ArrayList<InternalOrganization>();
		orgs = serviceLocator.getInternalOrganizationService().findAll();
		if (orgs.isEmpty() || orgs == null)
			showError("There is no Internal Organization exists.");
		else {
			searchNdisStudentText = "";
			registrationCode = "";
			timePeriod = "1W";
			staffList = new ArrayList<SelectItem>();
			staffId = new Long(0);
			staff = null;
			if (weekDate == null)
				weekDate = new Date();
			studentId = new Long(0);
			uom = null;
			studentList = null;
			selectedStudentList = new ArrayList<Student>();
			internalOrganization = orgs.get(0);
			studentList = serviceLocator.getStudentService().listActiveStudentswithNdisnumber();
			registrationCodeMap = new HashMap<Long, InternalOrganization>();
			staffMap = new HashMap<Long, AuthorisedStaff>();
			authorizedStaffs = serviceLocator.getAuthorisedStaffService()
					.listAuthorisedStaffToOrg(internalOrganization.getId());
			if (authorizedStaffs.size() != 0) {
				for (AuthorisedStaff staff : authorizedStaffs) {
					staffMap.put(staff.getId(), staff);
				}
			}
			registrationCodes = serviceLocator.getInternalOrganizationService().findAll();
			if (registrationCodes.size() != 0) {
				for (InternalOrganization intOrg : registrationCodes) {
					registrationCodeMap.put(intOrg.getId(), intOrg);
				}
			}
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			reportDate = df.format(date);
			selectStudent = false;
			dateChange(null);
		}
	}

	public void selectAllStudents(ActionEvent ae) {
		if (studentList.size() > 0) {
			selectedStudentList.addAll(studentList);
			studentList.clear();
		}
	}

	public void removeAllStudents(ActionEvent ae) {
		if (selectedStudentList.size() > 0) {
			selectedStudentList.clear();
			dateChange(null);
		}
	}

	public void openEvents(ActionEvent ae) {
		Student stu = (Student) ae.getComponent().getAttributes().get("student");
		commitedEventList = serviceLocator.getNdisCommittedEventService()
				.getCommitedEventsOfInvoiceByStudentId(new Long(invoiceId), stu.getId());
		showEventPopup();
	}

	public void openAncillaryCosts(ActionEvent ae) {
		Student stu = (Student) ae.getComponent().getAttributes().get("student");
		ancillaryCostList = serviceLocator.getNdisAncillaryCostService()
				.getAncillaryCostOfInvoiceByStudentId(new Long(invoiceId), stu.getId());
		showAncillaryPopup();
	}

	public void selectStudent(ActionEvent ae) {
		Student student = (Student) ae.getComponent().getAttributes().get("student");
		if (!selectedStudentList.contains(student)) {
			selectedStudentList.add(student);
			studentList.remove(student);
		}
	}

	public void removeStudent(ActionEvent ae) {
		Student student = (Student) ae.getComponent().getAttributes().get("student");
		if (!studentList.contains(student)) {
			studentList.add(student);
			selectedStudentList.remove(student);
		}
	}

	public void selectInvoice(ClickActionEvent ae) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		NdisInvoice selectedInvoice = (NdisInvoice) ae.getComponent().getAttributes().get("invo");
		invoiceId = selectedInvoice.getId().intValue();
		generetedDate = formatter.format(selectedInvoice.getGenerateDate());
		dateRange = formatter.format(selectedInvoice.getStartDay()) + " - "
				+ formatter.format(selectedInvoice.getEndDay());
		authorizedStaffName = selectedInvoice.getAuthorisedstaff().getStaffMember().getContact().getName();
		slectedInvoiceStudentList = serviceLocator.getStudentService().listStudentByInvoice(selectedInvoice.getId());
		numberOfStudent = slectedInvoiceStudentList.size();
		Collections.sort(slectedInvoiceStudentList, new SortObjectByName());

	}

	private void invoiceNameValidation(int i) {
		List<NdisInvoice> nameCheckInvoiceList = new ArrayList<NdisInvoice>();
		nameCheckInvoiceList = serviceLocator.getNdisInvoiceService().retriveInvoiceByStartEndDate(startDate, endDate);
		boolean found = false;
		for (NdisInvoice invo : nameCheckInvoiceList) {
			if (invo.getFilePath().equals(Util.getMessage("ndis_invoice_path") + invoiceName + ".csv")) {
				invoiceName = new SimpleDateFormat("ddMMMyy").format(startDate) + "-"
						+ new SimpleDateFormat("ddMMMyy").format(endDate);
				invoiceName = invoiceName + "(" + i + ")";
				i++;
				found = true;
			}
		}
		if (found) {
			invoiceNameValidation(i);
		}
	}

	public void createInvoice() {
		calculateRosterDates();
		invoiceNameValidation(1);

		staff = serviceLocator.getAuthorisedStaffService().retrieve(staffId);
		NdisInvoice ndisinvoice = new NdisInvoice();
		ndisinvoice.setAuthorisedstaff(staff);
		ndisinvoice.setRegistrationCode(registrationCode);
		ndisinvoice.setStartDay(startDate);
		ndisinvoice.setEndDay(endDate);
		ndisinvoice.setGenerateDate(new Date());
		ndisinvoice.setFilePath(Util.getMessage("ndis_invoice_path") + invoiceName + ".csv");
		ndisinvoice = serviceLocator.getNdisInvoiceService().create(ndisinvoice);
		try {
			fileWriter = new FileWriter(ndisinvoice.getFilePath());
			fileWriter.append(fileHeader.toString());
			fileWriter.append(newLineSeparater);
			for (Student std : selectedStudentList) {
				if (std.getId() != null) {
					createInvoiceData(std, ndisinvoice.getId());
				}
			}
			showInfo("CSV file was created successfully and Saved ");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (Exception e) {
				showError("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}
	}

	public void downloadCvs() {
		try {
			downloadNDISInvoice();
		} catch (IOException ex) {
			showError("Error");
		}
	}

	public void deleteInvoice() {
		Long invoId = new Long(invoiceId);
		List<NdisCommittedEvent> listEventToUnclaim = serviceLocator.getNdisAncillaryService()
				.retriveCommitedEventsByInvoiceId(invoId);
		List<NdisAncillaryCost> listAncillarytoUnclaim = serviceLocator.getNdisAncillaryService()
				.retriveNdisAncillaryCostByInvoiceId(invoId);
		if (listEventToUnclaim != null && listEventToUnclaim.size() != 0) {
			for (NdisCommittedEvent event : listEventToUnclaim) {
				event.setClaimed(false);
				event.setInvoiceId(null);
				serviceLocator.getNdisCommittedEventService().update(event);
			}
		}
		if (listAncillarytoUnclaim != null && listAncillarytoUnclaim.size() != 0) {
			for (NdisAncillaryCost ancillaryCost : listAncillarytoUnclaim) {
				ancillaryCost.setClaimed(false);
				ancillaryCost.setInvoiceId(null);
				serviceLocator.getNdisAncillaryCostService().update(ancillaryCost);
			}
		}
		List<NdisInvoiceItem> invoiceItems = serviceLocator.getNdisInvoiceItemService()
				.retriveInvoiceItemsByInvoiceId(invoId);
		if (invoiceItems != null && invoiceItems.size() != 0) {
			for (NdisInvoiceItem item : invoiceItems) {
				serviceLocator.getNdisInvoiceItemService().delete(item.getId());
			}
		}
		NdisInvoice invo = serviceLocator.getNdisInvoiceService().retrieve(invoId);
		File f = new File(invo.getFilePath());
		f.delete();
		serviceLocator.getNdisInvoiceService().delete(invoId);
		init();
		invoiceId = 0;
		generetedDate = null;
		dateRange = null;
		authorizedStaffName = null;
	}

	public void searchNdisStudent() {
		if (searchNdisStudentText != null && searchNdisStudentText != "") {
			Pattern name = Pattern.compile(searchNdisStudentText.toLowerCase());
			List<Student> temp = new ArrayList<Student>();
			for (Student std : studentList) {
				Matcher match = name.matcher(std.getContact().getName().toLowerCase());
				if (match.find()) {
					temp.add(std);
				}
			}
			studentList.clear();
			studentList.addAll(temp);
			if (studentList.size() == 0) {
				showError(" No Students for the current search");
			}
		} else {
			dateChange(null);
			if (selectedStudentList.size() > 0) {
				List<Student> Remove = new ArrayList<Student>();
				for (Student std : selectedStudentList) {
					for (Student std2 : studentList) {
						if (std.getId().equals(std2.getId())) {
							Remove.add(std2);
							break;
						}
					}
				}
				studentList.removeAll(Remove);
			}
		}
	}

	private void createInvoiceData(Student student, Long invoiceId) {
		String supportStart, supportEnd, referenceNumber, hours = null;
		long timeLong12 = 0, timeLong1 = 0, timeLong6 = 0;
		Double price = null;
		quantity = null;
		String gstCode;
		uom = null;
		Date eventEndDate = null;
		DecimalFormat formatDecimal = new DecimalFormat("00");
		List<NdisCommittedEvent> ndisCommittedEventItems = serviceLocator.getNdisAncillaryService()
				.retrieveInvoiceItem(startDate, endDate, student.getId());
		try {
			if (ndisCommittedEventItems.size() != 0) {
				DateFormat formattertime = new SimpleDateFormat("hh:mm:ss a");
				try {
					String timeString12 = "12:00:00 AM";
					Date timeDate12 = formattertime.parse(timeString12);
					timeLong12 = timeDate12.getTime();
					String timeString1 = "01:00:00 AM";
					Date timeDate1 = formattertime.parse(timeString1);
					timeLong1 = timeDate1.getTime();
					String timeString6 = "06:00:00 AM";
					Date timeDate6 = formattertime.parse(timeString6);
					timeLong6 = timeDate6.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				for (NdisCommittedEvent item : ndisCommittedEventItems) {
					supportStart = null;
					supportEnd = null;
					eventEndDate = null;

					String refNumber = String.format("%04d", count);
					referenceNumber = reportDate + refNumber;
					gstCode = item.getPrice().getSupportItem().getGstCode();
					if (!item.getStudentGroup().getProgram().getType().getName().equals("Transport")) {
						Date endTime = item.getEndTime();
						DateFormat time = new SimpleDateFormat("hh:mm:ss a");
						String endTimeString = time.format(endTime);
						Date endTime2 = null;
						try {
							endTime2 = formattertime.parse(endTimeString);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						Long currentEndTimeLong = endTime2.getTime();
						Calendar cal = Calendar.getInstance();
						cal.setTime(item.getEventDate());
						if (timeLong12 <= currentEndTimeLong && currentEndTimeLong <= timeLong1) {
							Calendar c = Calendar.getInstance();
							c.setTime(item.getEventDate());
							c.add(Calendar.DATE, 1);
							eventEndDate = c.getTime();
						} else if (timeLong1 < currentEndTimeLong && currentEndTimeLong < timeLong6) {
							Calendar c = Calendar.getInstance();
							c.setTime(item.getEventDate());
							c.add(Calendar.DATE, 1);
							eventEndDate = c.getTime();
						}
						uom = item.getPrice().getUom();
						if (uom != null) {
							if (uom.equals("Hour")) {
								quantity = null;
								if (item.getHours() != null) {
									String startTime = "00:00";
									double minutes = item.getHours() * 60.0;
									int i = (int) (minutes + 0.5d);
									int h = i / 60 + Integer.valueOf(startTime.substring(0, 1));
									int m = i % 60 + Integer.valueOf(startTime.substring(3, 4));
									hours = h + ":" + formatDecimal.format(m);
								}
							} else if (uom.equals("Each")) {
								hours = null;
								quantity = 1.00;
							}
						}
					} else if (item.getStudentGroup().getProgram().getType().getName().equals("Transport")) {
						hours = null;
						quantity = item.getKmsPerDay();

					}
					price = item.getPrice().getPrice();
					DateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
					supportStart = d1.format(item.getEventDate());
					if (eventEndDate != null) {
						supportEnd = d1.format(eventEndDate);
					} else {
						supportEnd = d1.format(item.getEventDate());
					}
					fileWriter.append(registrationCode);
					fileWriter.append(commaDelimiter);
					fileWriter.append(item.getParticipant().getNdisNumber());
					fileWriter.append(commaDelimiter);
					fileWriter.append(supportStart);
					fileWriter.append(commaDelimiter);
					fileWriter.append(supportEnd);
					fileWriter.append(commaDelimiter);
					fileWriter.append(item.getPrice().getItemNumber());
					fileWriter.append(commaDelimiter);
					fileWriter.append(referenceNumber);
					fileWriter.append(commaDelimiter);
					fileWriter.append(((quantity == null) ? "" : String.valueOf(quantity)));// quantity
					fileWriter.append(commaDelimiter);
					fileWriter.append(((hours == null) ? "" : hours));
					fileWriter.append(commaDelimiter);
					fileWriter.append(String.valueOf(price));
					fileWriter.append(commaDelimiter);
					fileWriter.append(((gstCode == null) ? "" : gstCode));
					fileWriter.append(commaDelimiter);
					fileWriter.append(staff.getAuthorisingCode());// Authorized
																	// By
					fileWriter.append(commaDelimiter);
					fileWriter.append("true");
					fileWriter.append(newLineSeparater);
					item.setClaimed(true);
					item.setInvoiceId(invoiceId);
					serviceLocator.getNdisCommittedEventService().update(item);
					count++;
					NdisInvoiceItem invoiceItem = new NdisInvoiceItem();
					invoiceItem.setInvoiceId(invoiceId);
					invoiceItem.setRegistrationNumber(registrationCode);
					invoiceItem.setNDISNumber(item.getParticipant().getNdisNumber());
					invoiceItem.setSupportsDeliveredFrom(supportStart);
					invoiceItem.setSupportsDeliveredTo(supportEnd);
					invoiceItem.setSupportNumber(item.getPrice().getItemNumber());
					invoiceItem.setClaimReference(referenceNumber);
					invoiceItem.setQuantity(quantity);
					invoiceItem.setHours(hours);
					invoiceItem.setUnitPrice(price);
					invoiceItem.setGSTCode(gstCode);
					invoiceItem.setAuthorisedBy(staff.getAuthorisingCode());
					serviceLocator.getNdisInvoiceItemService().create(invoiceItem);
				}
			}
			createAncillaryCostInvoice(startDate, endDate, student.getId(), invoiceId);
		} catch (Exception e) {
			showInfo("Error in CsvFileWriter");
			e.printStackTrace();
		}
	}

	public void createAncillaryCostInvoice(Date startDate, Date endDate, Long studentId, Long invoiceId) {
		ancillaryCount = new Long(0);
		Boolean claimed = false;
		List<NdisAncillaryCost> ancillaryCostItems = serviceLocator.getNdisAncillaryService()
				.retrieveNdisAncillaryCostItems(startDate, endDate, studentId, claimed);
		ancillaryCount = count;
		try {
			if (ancillaryCostItems != null && ancillaryCostItems.size() != 0) {
				for (NdisAncillaryCost ancillaryCost : ancillaryCostItems) {
					DateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
					String supportStart = d1.format(ancillaryCost.getDate());
					String supportEnd = d1.format(ancillaryCost.getDate());
					String ndisSupportCluster = ancillaryCost.getNdisPrice().getItemNumber();
					String refNumber = String.format("%04d", ancillaryCount);
					String referenceNumber = reportDate + refNumber;
					Double hours = null;
					int quantity = ancillaryCost.getNoofunit();
					Double price = ancillaryCost.getNdisPrice().getPrice();
					fileWriter.append(registrationCode);
					fileWriter.append(commaDelimiter);
					fileWriter.append(ancillaryCost.getStudent().getNdisNumber());
					fileWriter.append(commaDelimiter);
					fileWriter.append(supportStart);
					fileWriter.append(commaDelimiter);
					fileWriter.append(supportEnd);
					fileWriter.append(commaDelimiter);
					fileWriter.append(ndisSupportCluster);
					fileWriter.append(commaDelimiter);
					fileWriter.append(referenceNumber);
					fileWriter.append(commaDelimiter);
					fileWriter.append(String.valueOf(quantity));
					fileWriter.append(commaDelimiter);
					fileWriter.append(((hours == null) ? "" : String.valueOf(hours)));
					fileWriter.append(commaDelimiter);
					fileWriter.append(String.valueOf(price));
					fileWriter.append(commaDelimiter);
					fileWriter.append(ancillaryCost.getNdisSupportItem().getGstCode());
					fileWriter.append(commaDelimiter);
					fileWriter.append(staff.getAuthorisingCode());
					fileWriter.append(commaDelimiter);
					fileWriter.append("true");
					fileWriter.append(newLineSeparater);
					ancillaryCost.setClaimed(true);
					ancillaryCost.setInvoiceId(invoiceId);
					serviceLocator.getNdisAncillaryCostService().update(ancillaryCost);
					ancillaryCount++;

					NdisInvoiceItem invoiceItem = new NdisInvoiceItem();
					invoiceItem.setInvoiceId(invoiceId);
					invoiceItem.setRegistrationNumber(registrationCode);
					invoiceItem.setNDISNumber(ancillaryCost.getStudent().getNdisNumber());
					invoiceItem.setSupportsDeliveredFrom(supportStart);
					invoiceItem.setSupportsDeliveredTo(supportEnd);
					invoiceItem.setSupportNumber(ndisSupportCluster);
					invoiceItem.setClaimReference(referenceNumber);
					invoiceItem.setQuantity(new Double(quantity));
					invoiceItem.setHours(String.valueOf(hours));
					invoiceItem.setUnitPrice(price);
					invoiceItem.setGSTCode(ancillaryCost.getNdisSupportItem().getGstCode());
					invoiceItem.setAuthorisedBy(staff.getAuthorisingCode());
					serviceLocator.getNdisInvoiceItemService().create(invoiceItem);
				}
			}
		} catch (Exception e) {
			showInfo("Error in CsvFileWriter");
			e.printStackTrace();
		}
	}

	private void calculateRosterDates() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(weekDate);
		if (calendar.get(Calendar.DAY_OF_WEEK) != 7) {
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			calendar.add(Calendar.DATE, -1);
		}
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate = calendar.getTime();
		// if (timePeriod == null || timePeriod.equals("1W")) {
		calendar.add(Calendar.DATE, 6);
		// } else if (timePeriod.equals("2W")) {
		// calendar.add(Calendar.DATE, 13);
		// }
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		endDate = calendar.getTime();
		rosterTimeperiod = new SimpleDateFormat("dd/MM/yyyy").format(startDate) + " - "
				+ new SimpleDateFormat("dd/MM/yyyy").format(endDate);
	}

	public void weekChange(ValueChangeEvent ve) {
		timePeriod = (String) ve.getNewValue();
		dateChange(null);
	}

	public void dateChange(ValueChangeEvent ve) {
		if (ve != null) {
			weekDate = (Date) ve.getNewValue();
		}
		calculateRosterDates();
		List<Student> allStudents = new ArrayList<Student>();
		List<Student> tempStudentL = new ArrayList<Student>();
		allStudents = serviceLocator.getStudentService().listActiveStudentswithNdisnumber();
		for (Student student : allStudents) {
			List<NdisCommittedEvent> ndisCommittedEventItems = serviceLocator.getNdisAncillaryService()
					.retrieveInvoiceItem(startDate, endDate, student.getId());
			List<NdisCommittedEvent> tempCommittedEvents = new ArrayList<NdisCommittedEvent>();
			for (NdisCommittedEvent item : ndisCommittedEventItems) {
				if (item.getStudentGroup().getProgram().getType().getName().equals("Individual")) {
					List<StudentEvent> StudentEvents = new ArrayList<StudentEvent>();
					StudentEvents = serviceLocator.getStudentEventService().retrieveStudentEventsByDate(
							item.getParticipant().getId(), item.getStudentGroup().getId(), item.getEventDate());
					if (StudentEvents == null || StudentEvents.isEmpty()) {
						continue;
					}else{
						tempCommittedEvents.add(item);
					}
				} else if (item.getStudentGroup().getProgram().getType().getName().equals("Transport")) {
					List<StudentEvent> StudentEvents = new ArrayList<StudentEvent>();
					List<StudentEvent> returnStudentEvents = new ArrayList<StudentEvent>();
					StudentGroup returnStdGroup = null;
					returnStdGroup = serviceLocator.getStudentGroupService()
							.getReturnGroup(item.getStudentGroup().getId(), false);
					if (returnStdGroup != null) {
						returnStudentEvents = serviceLocator.getStudentEventService().retrieveStudentEventsByDate(
								item.getParticipant().getId(), returnStdGroup.getId(), item.getEventDate());
					}
					StudentEvents = serviceLocator.getStudentEventService().retrieveStudentEventsByDate(
							item.getParticipant().getId(), item.getStudentGroup().getId(), item.getEventDate());
					if (StudentEvents.isEmpty() && returnStudentEvents.isEmpty()) {
						continue;
					}else{
						tempCommittedEvents.add(item);
					}
				}else{
						tempCommittedEvents.add(item);
				}

			}
			List<NdisAncillaryCost> ancillaryCostItems = serviceLocator.getNdisAncillaryService()
					.retrieveNdisAncillaryCostItems(startDate, endDate, student.getId(), false);
			if (tempCommittedEvents.size() > 0 || ancillaryCostItems.size() > 0) {
				tempStudentL.add(student);
			}
		}
		studentList.clear();
		studentList.addAll(tempStudentL);
	}

	public void generateNdisClaim() {
		if (validateDetails()) {
			invoiceName = new SimpleDateFormat("ddMMMyy").format(startDate) + "-"
					+ new SimpleDateFormat("ddMMMyy").format(endDate);
			createInvoice();
			init();
			initCreateNewInvoice();
		}
	}

	private void downloadNDISInvoice() throws IOException {
		Long invoId = new Long(invoiceId);
		NdisInvoice downloadInvo = serviceLocator.getNdisInvoiceService().retrieve(invoId);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
		File file = new File(downloadInvo.getFilePath());
		String fileName = file.getName();
		ExternalContext ec = fc.getExternalContext();
		String contentType = ec.getMimeType(fileName); // JSF
														// 1.x:((ServletContext)ec.getContext()).getMimeType(fileName);
		int contentLength = (int) file.length();

		response.reset(); // Some JSF component library or some Filter might
							// have set some headers in the buffer
							// beforehand.
							// We want to get rid of them, else it may
							// collide.
		response.setContentType(contentType); // Check
												// http://www.iana.org/assignments/media-types
												// for all types. Use if
												// necessary
												// ServletContext#getMimeType()
												// for auto-detection based
												// on
												// filename.
		response.setContentLength(contentLength); // Set it with the file
													// size.
													// This header is
													// optional.
													// It will work if it's
													// omitted, but the
													// download
													// progress will be
													// unknown.
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		// The Save As popup magic is done here. You can give it any file
		// name you want, this only won't work
		// in MSIE it will use current request URL as file name instead.

		OutputStream output = response.getOutputStream();
		// Now you can write the InputStream of the file to the above
		// OutputStream the usual way.
		// ...
		try {
			Files.copy(file.toPath(), output);

			fc.responseComplete(); // Important! Otherwise JSF will attempt
									// to
									// render the response which obviously
									// will
									// fail
									// since it's already written with a
									// file
									// and
									// closed.
		} catch (NoSuchFileException nfe) {
			createMissingInvoice();
			downloadNDISInvoice();
		}

	}

	private void createMissingInvoice() {
		Long invoiceIdl = new Long(invoiceId);
		List<NdisInvoiceItem> invoiceItems = serviceLocator.getNdisInvoiceItemService()
				.retriveInvoiceItemsByInvoiceId(invoiceIdl);
		NdisInvoice ndisinvoice = serviceLocator.getNdisInvoiceService().retrieve(invoiceIdl);
		try {
			FileWriter fileWriterl = new FileWriter(ndisinvoice.getFilePath());
			fileWriterl.append(fileHeader.toString());
			fileWriterl.append(newLineSeparater);

			for (NdisInvoiceItem item : invoiceItems) {

				fileWriterl.append(item.getRegistrationNumber());
				fileWriterl.append(commaDelimiter);
				fileWriterl.append(item.getNDISNumber());
				fileWriterl.append(commaDelimiter);
				fileWriterl.append(item.getSupportsDeliveredFrom());
				fileWriterl.append(commaDelimiter);
				fileWriterl.append(item.getSupportsDeliveredTo());
				fileWriterl.append(commaDelimiter);
				fileWriterl.append(item.getSupportNumber());
				fileWriterl.append(commaDelimiter);
				fileWriterl.append(item.getClaimReference());
				fileWriterl.append(commaDelimiter);
				fileWriterl.append(((item.getQuantity() == null) ? "" : String.valueOf(item.getQuantity())));// quantity
				fileWriterl.append(commaDelimiter);
				fileWriterl.append(((item.getHours() == null) ? "" : item.getHours()));
				fileWriterl.append(commaDelimiter);
				fileWriterl.append(String.valueOf(item.getUnitPrice()));
				fileWriterl.append(commaDelimiter);
				fileWriterl.append(((item.getGSTCode() == null) ? "" : item.getGSTCode()));
				fileWriterl.append(commaDelimiter);
				fileWriterl.append(item.getAuthorisedBy());// Authorized
															// By
				fileWriterl.append(commaDelimiter);
				fileWriterl.append("true");
				fileWriterl.append(newLineSeparater);
			}
			fileWriterl.flush();
			fileWriterl.close();
		} catch (Exception e) {
			showError("Error , Please contact Admin");
		}
	}

	private boolean validateDetails() {

		if (registrationCode.equals(0) || registrationCode.isEmpty()) {
			showError("NDIS Registration Code should not be empty.");
			return false;
		}
		if (staffId == null || staffId == 0) {
			showError("Please select an authorizing staff.");
			return false;
		}
		if (selectedStudentList == null || selectedStudentList.size() == 0) {
			showError("At least one student must be selected.");
			return false;
		}
		return true;
	}

	public void createNewInvoice() {
		initCreateNewInvoice();
		createNewInvoicePopup();
	}

	public void filterInvoice() {
		init();
		if (filterStartDate != null && filterEndDate != null && filterStartDate.before(filterEndDate)) {
			List<NdisInvoice> filterList = new ArrayList<NdisInvoice>();
			Date filterstartd = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(filterStartDate);
			cal.add(Calendar.DATE, -1);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			filterstartd = cal.getTime();

			cal.setTime(filterEndDate);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			filterEndDate = cal.getTime();

			for (NdisInvoice invoice : invoiceList) {
				if ((invoice.getStartDay().after(filterstartd) || invoice.getStartDay().equals(filterstartd))
						&& (invoice.getEndDay().before(filterEndDate) || invoice.getEndDay().equals(filterEndDate))) {
					filterList.add(invoice);
				}
			}
			invoiceList.clear();
			invoiceList.addAll(filterList);
			if (invoiceList.size() == 0) {
				showError("No Invoice found");
			}
		} else {
			showError("Please Enter valid Start/End dates");
		}
	}

	public void createNewInvoicePopup() {
		if (showCreateNewInvoice) {
			showCreateNewInvoice = false;
		} else {
			showCreateNewInvoice = true;
		}
	}

	public void showEventPopup() {
		if (showEvents) {
			showEvents = false;
		} else {
			showEvents = true;
		}
	}

	public void showAncillaryPopup() {
		if (showAncillary) {
			showAncillary = false;
		} else {
			showAncillary = true;
		}
	}

	public String getCommaDelimiter() {
		return commaDelimiter;
	}

	public void setCommaDelimiter(String commaDelimiter) {
		this.commaDelimiter = commaDelimiter;
	}

	public String getNewLineSeparater() {
		return newLineSeparater;
	}

	public void setNewLineSeparater(String newLineSeparater) {
		this.newLineSeparater = newLineSeparater;
	}

	public static String getFileheader() {
		return fileHeader;
	}

	public List<SelectItem> getStudentSelectItemList() {
		return studentSelectItemList;
	}

	public void setStudentSelectItemList(List<SelectItem> studentSelectItemList) {
		this.studentSelectItemList = studentSelectItemList;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public Date getWeekDate() {
		return weekDate;
	}

	public void setWeekDate(Date weekDate) {
		this.weekDate = weekDate;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public HashMap<Long, AuthorisedStaff> getStaffMap() {
		return staffMap;
	}

	public void setStaffMap(HashMap<Long, AuthorisedStaff> staffMap) {
		this.staffMap = staffMap;
	}

	public Collection<AuthorisedStaff> getStaffs() {
		return (Collection<AuthorisedStaff>) staffMap.values();
	}

	public Collection<InternalOrganization> getRegCodes() {
		return (Collection<InternalOrganization>) registrationCodeMap.values();
	}

	public List<SelectItem> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<SelectItem> staffList) {
		this.staffList = staffList;
	}

	public List<AuthorisedStaff> getAuthorizedStaffs() {
		return authorizedStaffs;
	}

	public void setAuthorizedStaffs(List<AuthorisedStaff> authorizedStaffs) {
		this.authorizedStaffs = authorizedStaffs;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getRegistrationCode() {
		return registrationCode;
	}

	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}

	public HashMap<Long, InternalOrganization> getRegistrationCodeMap() {
		return registrationCodeMap;
	}

	public void setRegistrationCodeMap(HashMap<Long, InternalOrganization> registrationCodeMap) {
		this.registrationCodeMap = registrationCodeMap;
	}

	public List<InternalOrganization> getRegistrationCodes() {
		return registrationCodes;
	}

	public void setRegistrationCodes(List<InternalOrganization> registrationCodes) {
		this.registrationCodes = registrationCodes;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getAncillaryCount() {
		return ancillaryCount;
	}

	public void setAncillaryCount(Long ancillaryCount) {
		this.ancillaryCount = ancillaryCount;
	}
	//
	// public boolean isSelectStudents() {
	// return selectStudents;
	// }
	//
	// public void setSelectStudents(boolean selectStudents) {
	// this.selectStudents = selectStudents;
	// }

	// public boolean isSelectAllStudents() {
	// return selectAllStudents;
	// }
	//
	// public void setSelectAllStudents(boolean selectAllStudents) {
	// this.selectAllStudents = selectAllStudents;
	// }

	public boolean isSelectStudent() {
		return selectStudent;
	}

	public void setSelectStudent(boolean selectStudent) {
		this.selectStudent = selectStudent;
	}

	public AuthorisedStaff getStaff() {
		return staff;
	}

	public void setStaff(AuthorisedStaff staff) {
		this.staff = staff;
	}

	public InternalOrganization getInternalOrganization() {
		return internalOrganization;
	}

	public boolean isShowCreateNewInvoice() {
		return showCreateNewInvoice;
	}

	public void setShowCreateNewInvoice(boolean showCreateNewInvoice) {
		this.showCreateNewInvoice = showCreateNewInvoice;
	}

	public List<NdisInvoice> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<NdisInvoice> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public String getGeneretedDate() {
		return generetedDate;
	}

	public void setGeneretedDate(String generetedDate) {
		this.generetedDate = generetedDate;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public String getAuthorizedStaffName() {
		return authorizedStaffName;
	}

	public void setAuthorizedStaffName(String authorizedStaffName) {
		this.authorizedStaffName = authorizedStaffName;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public int getNumberOfStudent() {
		return numberOfStudent;
	}

	public void setNumberOfStudent(int numberOfStudent) {
		this.numberOfStudent = numberOfStudent;
	}

	public Date getFilterStartDate() {
		return filterStartDate;
	}

	public void setFilterStartDate(Date filterStartDate) {
		this.filterStartDate = filterStartDate;
	}

	public Date getFilterEndDate() {
		return filterEndDate;
	}

	public void setFilterEndDate(Date filterEndDate) {
		this.filterEndDate = filterEndDate;
	}

	public List<Student> getSlectedInvoiceStudentList() {
		return slectedInvoiceStudentList;
	}

	public void setSlectedInvoiceStudentList(List<Student> slectedInvoiceStudentList) {
		this.slectedInvoiceStudentList = slectedInvoiceStudentList;
	}

	public String getSearchNdisStudentText() {
		return searchNdisStudentText;
	}

	public void setSearchNdisStudentText(String searchNdisStudentText) {
		this.searchNdisStudentText = searchNdisStudentText;
	}

	public List<Student> getSelectedStudentList() {
		return selectedStudentList;
	}

	public void setSelectedStudentList(List<Student> selectedStudentList) {
		this.selectedStudentList = selectedStudentList;
	}

	public List<NdisCommittedEvent> getCommitedEventList() {
		return commitedEventList;
	}

	public void setCommitedEventList(List<NdisCommittedEvent> commitedEventList) {
		this.commitedEventList = commitedEventList;
	}

	public boolean isShowEvents() {
		return showEvents;
	}

	public void setShowEvents(boolean showEvents) {
		this.showEvents = showEvents;
	}

	public String getRosterTimeperiod() {
		return rosterTimeperiod;
	}

	public void setRosterTimeperiod(String rosterTimeperiod) {
		this.rosterTimeperiod = rosterTimeperiod;
	}

	public List<NdisAncillaryCost> getAncillaryCostList() {
		return ancillaryCostList;
	}

	public void setAncillaryCostList(List<NdisAncillaryCost> ancillaryCostList) {
		this.ancillaryCostList = ancillaryCostList;
	}

	public boolean isShowAncillary() {
		return showAncillary;
	}

	public void setShowAncillary(boolean showAncillary) {
		this.showAncillary = showAncillary;
	}

}