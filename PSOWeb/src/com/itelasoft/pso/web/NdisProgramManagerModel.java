package com.itelasoft.pso.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.springframework.dao.DataIntegrityViolationException;

import com.icesoft.faces.component.ext.ClickActionEvent;
import com.icesoft.faces.component.paneltabset.TabChangeEvent;
import com.itelasoft.pso.beans.EnumItemCategory;
import com.itelasoft.pso.beans.GroupedStudent;
import com.itelasoft.pso.beans.NdisAncillaryCost;
import com.itelasoft.pso.beans.NdisCommittedEvent;
import com.itelasoft.pso.beans.NdisContribution;
import com.itelasoft.pso.beans.NdisContributionSummary;
import com.itelasoft.pso.beans.NdisPrice;
import com.itelasoft.pso.beans.NdisSupportItem;
import com.itelasoft.pso.beans.ProgramEvent;
import com.itelasoft.pso.beans.ProgramType;
import com.itelasoft.pso.beans.ReferenceItem;
import com.itelasoft.pso.beans.Student;
import com.itelasoft.pso.beans.StudentEvent;
import com.itelasoft.pso.beans.StudentGroup;
import com.itelasoft.pso.beans.WeekDay;
import com.itelasoft.util.SortObjectByName;

@ManagedBean(name = "ndisProgramManagerModel")
@SessionScoped
public class NdisProgramManagerModel extends UIModel {

	private List<Student> students;
	private Student student, tmpStudent;
	private boolean selectAllEvents;
	private String searchSNText;
	private String searchGroupText;
	private HashMap<Long, StudentGroup> selectedGroups;
	private boolean visibleGroups, visibleTransportGroups;
	private String searchStudentText;
	private int selectedTabIndex;
	private GroupedStudent groupedStudent = new GroupedStudent();
	private StudentGroup studentGroup;
	private List<ProgramEvent> events;
	private Date fromDate;
	private String jsonString;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE - dd/MM/yyyy");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");//
	private List<Object> oneRowTable;
	private List<SelectItem> relationships;
	private List<SelectItem> tmpRelationships;
	private Long ndisSupportItemId, ndisPriceId;
	private String selectedStudentId;
	private int wizardPage;
	private List<ProgramEvent> actProEvents;
	private List<GroupedStudent> actGroupedStudents;
	private HashMap<Long, List<NdisSupportItem>> clusterMap;
	private HashMap<Long, List<NdisPrice>> priceMap;//
	private Date startDate;//
	private Date endDate;//
	private Date sDate;
	private Date eDate;
	private List<StudentEvent> studentEvents;///
	private ArrayList<StudentGroup> programs = new ArrayList<StudentGroup>();//
	private String paymentMethod;//
	private boolean visibleAncillaryPopUp;
	private List<SelectItem> ndisSelectItemsList;
	private List<SelectItem> ndisSelectPriceItemsList;
	private NdisSupportItem ndisSupportItem;
	private NdisPrice ndisPrice;
	private NdisAncillaryCost ndisAncillaryCost, tempNdisAncillaryCost;
	private String uom;
	private List<NdisAncillaryCost> allNdisAncillaryCosts;
	private Map<Long, NdisPrice> ndisPricesMap;
	private Map<Long, NdisSupportItem> ndisItemMap;
	private String status = "Active";
	private boolean visibleContribution;
	private boolean visibleLOS;//
	private Long selectedGroupId;
	private HashMap<Long, StudentGroup> stdGrpMap;
	private Double hours;
	private Double committedHours, uncommittedHours, kmsPerDay;
	private Integer committedunits, uncommittedunits;
	private NdisCommittedEvent ndisEvent;
	private NdisSupportItem item;//
	HashMap<Long, Double> hoursMap = new HashMap<Long, Double>();//
	HashMap<Long, List<NdisCommittedEvent>> ndisEventsMap;
	private List<NdisCommittedEvent> ndisEvents;
	private NdisCommittedEvent eventNdis;
	private HashMap<Long, NdisCommittedEvent> selectedNdisEventsMap;
	private HashMap<Long, NdisCommittedEvent> selectedNdisEventsMapUncommit;
	private boolean selectEvent;
	private Long programTypeId;
	private Date date;
	private int noofunit;
	private Long selectedProgramTypeId;//
	private List<SelectItem> groupSelectItems;
	private List<String> committedList;
	long time1Lt, time2Lt, time3Lt, time4Lt, time5Lt, currentEndTime;
	private NdisContribution ndisContribution, tmpNdisContribution;
	private List<NdisContribution> allNdisContributions;
	private boolean visibleAddContribution;
	private NdisContribution contribution;
	private boolean isCommittedEventsAvailable;
	private List<NdisContributionSummary> ncsList;

	private void calculateContributionSummary() {
		ncsList = new ArrayList<NdisContributionSummary>();
		List<NdisContribution> contributions = serviceLocator.getNdisContributionService()
				.listNdisContributionByStudent(student.getId());
		for (Long id = new Long(1); id < 5; id++) {
			NdisContributionSummary ncs = new NdisContributionSummary(id);
			for (NdisContribution nc : contributions) {
				if (nc.getClusterType().equals(id)) {
					ncs.setNdisFund(ncs.getNdisFund() + nc.getAmount());
				}
			}
			List<NdisCommittedEvent> nceList = new ArrayList<NdisCommittedEvent>();
			List<NdisAncillaryCost> nacList = new ArrayList<NdisAncillaryCost>();
			if (id.equals(new Long(1)))
				nceList = serviceLocator.getNdisCommittedEventService().allCommittedAmountForStudent(student.getId(),
						new ProgramType(new Long(1)));
			else if (id.equals(new Long(2)))
				nceList = serviceLocator.getNdisCommittedEventService().allCommittedAmountForStudent(student.getId(),
						new ProgramType(new Long(4)));
			else if (id.equals(new Long(3)))
				nceList = serviceLocator.getNdisCommittedEventService().allCommittedAmountForStudent(student.getId(),
						new ProgramType(new Long(2)));
			else if (id.equals(new Long(4)))
				nacList = serviceLocator.getNdisAncillaryCostService().listNdisAncyCostByStudent(student.getId());
			for (NdisCommittedEvent nce : nceList) {
				ncs.setCommittedAmount(ncs.getCommittedAmount() + nce.getEventPrice());
				if (nce.getClaimed() != null && nce.getClaimed()) {
					ncs.setClaimedAmount(ncs.getClaimedAmount() + nce.getEventPrice());
				} else {
					ncs.setUnclaimedAmount(ncs.getUnclaimedAmount() + nce.getEventPrice());
				}
			}
			for (NdisAncillaryCost nac : nacList) {
				ncs.setCommittedAmount(ncs.getCommittedAmount() + (nac.getNoofunit() * nac.getNdisPrice().getPrice()));
				if (nac.isClaimed() != null && nac.isClaimed()) {
					ncs.setClaimedAmount(ncs.getClaimedAmount() + (nac.getNoofunit() * nac.getNdisPrice().getPrice()));
				} else {
					ncs.setUnclaimedAmount(
							ncs.getUnclaimedAmount() + (nac.getNoofunit() * nac.getNdisPrice().getPrice()));
				}
			}
			ncs.setUncommittedAmount(ncs.getNdisFund() - ncs.getCommittedAmount());
			ncsList.add(ncs);
		}
		NdisContributionSummary summary = new NdisContributionSummary(new Long(5));
		for (NdisContributionSummary ncs : ncsList) {
			summary.setNdisFund(summary.getNdisFund() + ncs.getNdisFund());
			summary.setCommittedAmount(summary.getCommittedAmount() + ncs.getCommittedAmount());
			summary.setUncommittedAmount(summary.getUncommittedAmount() + ncs.getUncommittedAmount());
			summary.setClaimedAmount(summary.getClaimedAmount() + ncs.getClaimedAmount());
			summary.setUnclaimedAmount(summary.getUnclaimedAmount() + ncs.getUnclaimedAmount());
		}
		ncsList.add(summary);
	}

	public NdisProgramManagerModel() {
		init();
	}

	public void init() {
		students = serviceLocator.getStudentService().listActiveStudentswithNdisnumber();
		// students = serviceLocator.getStudentService().listByName(null,
		// false);
		student = tmpStudent = null;
		initialise();
	}

	public void initStudent(ActionEvent ae) {
		StudentManagerModel sm = (StudentManagerModel) Util.getManagedBean("studentManagerModel");
		students = sm.getStudents();
		student = sm.getStudent();
		tmpStudent = sm.getTmpStudent();
		initialise();
		selectStudent();
	}

	private void initialise() {
		selectedTabIndex = 0;
		searchStudentText = "";
		oneRowTable = new ArrayList<Object>();//
		oneRowTable.add(new Object());//
		fromDate = new Date();//
		ndisEvents = new ArrayList<NdisCommittedEvent>();
		ndisEvent = new NdisCommittedEvent();
		tmpRelationships = new ArrayList<SelectItem>();//
		ndisItemMap = new HashMap<Long, NdisSupportItem>();
		List<ReferenceItem> refItems = serviceLocator.getReferenceItemService()
				.findItemsByCategory(EnumItemCategory.RELATIONSHIP, "Active");
		ndisAncillaryCost = tempNdisAncillaryCost = null;
		allNdisAncillaryCosts = serviceLocator.getNdisAncillaryCostService().findAll();
		ndisSupportItemId = new Long(0);
		if (!refItems.isEmpty()) {//
			tmpRelationships.add(new SelectItem(new Long(0), "Not Assigned"));
			for (ReferenceItem item : refItems) {
				tmpRelationships.add(new SelectItem(item.getId(), item.getItemValue()));
			}
		} else {
			tmpRelationships.add(new SelectItem(new Long(0), "Not Available"));
		} //
		sDate = null;
		eDate = null;
		startDate = new Date();//
		endDate = new Date();//
		date = new Date();
		uncommittedHours = 0.0;
		item = null;//
		hoursMap = new HashMap<Long, Double>();//
		ndisEventsMap = new HashMap<Long, List<NdisCommittedEvent>>();
		for (NdisSupportItem item : serviceLocator.getNdisSupportItemService().listByType("Ancillary"))
			ndisItemMap.put(item.getId(), item);
		ndisPricesMap = new HashMap<Long, NdisPrice>();
		selectAllEvents = false;
		programTypeId = new Long(0);
		stdGrpMap = new HashMap<Long, StudentGroup>();
		selectedProgramTypeId = new Long(0);//
		groupSelectItems = new ArrayList<SelectItem>();
		selectedNdisEventsMap = new HashMap<Long, NdisCommittedEvent>();
		selectedNdisEventsMapUncommit = new HashMap<Long, NdisCommittedEvent>();
		ndisEventsMap = new HashMap<Long, List<NdisCommittedEvent>>();
		ndisEvents = new ArrayList<NdisCommittedEvent>();
		ndisPriceId = new Long(0);
		kmsPerDay = new Double(0);
		visibleContribution = false;
		ndisContribution = new NdisContribution();
		tmpNdisContribution = new NdisContribution();
		visibleAddContribution = false;
	}

	public void initNdisItems() {
		selectedGroupId = new Long(0);
		ndisSelectItemsList = new ArrayList<SelectItem>();
		List<NdisSupportItem> supportitems = serviceLocator.getNdisSupportItemService().findAll();
		if (supportitems != null && !supportitems.isEmpty()) {
			ndisSelectItemsList.add(new SelectItem(0, "Not Assigned"));
			for (NdisSupportItem item : supportitems) {
				ndisSelectItemsList.add(new SelectItem(item.getId(), item.getItemName()));
			}
		} else {
			ndisSelectItemsList.add(new SelectItem(0, "Not Available"));
		}
	}

	public void initNdisPriceItems() {
		ndisSelectPriceItemsList = new ArrayList<SelectItem>();
		List<NdisPrice> price = serviceLocator.getNdisPriceService().listNdisPrices(ndisSupportItem.getId());
		if (price != null && !price.isEmpty()) {
			ndisSelectPriceItemsList.add(new SelectItem(0, "Not Assigned"));
			for (NdisPrice item : price) {
				ndisSelectPriceItemsList.add(new SelectItem(item.getId(), item.getItemNumber()));
			}
		} else {
			ndisSelectPriceItemsList.add(new SelectItem(0, "Not Available"));
		}
	}

	public void searchStudents() {
		List<Student> students = serviceLocator.getTextSearchService()
				.searchStudentsByNameNIdWithNdisNumber(searchStudentText, status.equals("all") ? null : status);
		if (students == null || students.isEmpty())
			showError("No results for the given search text.");
		else {
			this.students = students;
			Collections.sort(this.students, new SortObjectByName());
			student = tmpStudent = null;
			selectedTabIndex = 0;
		}
	}

	public void selectStudentsStatus() {
		List<Student> students = serviceLocator.getStudentService().listByStatus(status);
		if (students == null || students.isEmpty())
			showError("No results for the given search text.");
		else {
			this.students = students;
			student = tmpStudent = null;
			selectedTabIndex = 0;
		}
	}

	public void selectStudent(ClickActionEvent re) {
		if (tmpStudent != null)
			tmpStudent.setUi_selected(false);
		tmpStudent = (Student) re.getComponent().getAttributes().get("stu");
		tmpStudent.setUi_selected(true);
		student = serviceLocator.getStudentService().retrieveEager(tmpStudent.getId());
		selectStudent();
	}

	private void selectStudent() {
		clearInputs();
		ndisEventsMap = new HashMap<Long, List<NdisCommittedEvent>>();
		selectedNdisEventsMap = new HashMap<Long, NdisCommittedEvent>();
		selectedNdisEventsMapUncommit = new HashMap<Long, NdisCommittedEvent>();
		ndisEvents = new ArrayList<NdisCommittedEvent>();
		programTypeId = new Long(0);
		selectedGroupId = new Long(0);
		selectedGroupId = new Long(0);
		sDate = null;
		eDate = null;
		selectAllEvents = false;
		ndisAncillaryCost = new NdisAncillaryCost();
		ndisSupportItem = new NdisSupportItem();
		ndisPrice = new NdisPrice();
		selectedStudentId = "{studentId:" + student.getId() + "}";
		allNdisAncillaryCosts = serviceLocator.getNdisAncillaryCostService().listNdisAncyCostByStudent(student.getId());
		allNdisContributions = serviceLocator.getNdisContributionService()
				.listNdisContributionByStudent(student.getId());
		calculateContributionSummary();
	}

	public void selectStudentGroup(ValueChangeEvent event) {
		clearInputs();
		ndisEventsMap = new HashMap<Long, List<NdisCommittedEvent>>();
		selectedNdisEventsMap = new HashMap<Long, NdisCommittedEvent>();
		selectedNdisEventsMapUncommit = new HashMap<Long, NdisCommittedEvent>();
		stdGrpMap = new HashMap<Long, StudentGroup>();
		programTypeId = (Long) event.getNewValue();
		groupSelectItems.clear();
		ndisEvents = new ArrayList<NdisCommittedEvent>();
		List<StudentGroup> studentProgramList = new ArrayList<StudentGroup>();
		List<StudentGroup> transportProgramList = new ArrayList<StudentGroup>();
		List<StudentGroup> individualProgramList = new ArrayList<StudentGroup>();

		if (student.getGroups() != null && !student.getGroups().isEmpty()) {
			selectedGroupId = new Long(0);
			for (GroupedStudent gs : student.getGroups()) {
				if (!gs.getStatus().equals("Excluded")) {
					if (gs.getGroup().getProgram().getType().getName().equals("Student")
							&& !gs.getStatus().equals("Excluded")) {
						studentProgramList.add(gs.getGroup());
					} else if (gs.getGroup().getProgram().getType().getName().equals("Transport")) {
						if (gs.getGroup().getParentGroup() == null) {
							if (!gs.getStatus().equals("Excluded")) {
								transportProgramList.add(gs.getGroup());
							} else {
								continue;
							}
						} else {
							StudentGroup mainGroup = gs.getGroup().getParentGroup();
							for (GroupedStudent grpStd : student.getGroups()) {
								if (grpStd.getGroup() == mainGroup) {
									if (!grpStd.getStatus().equals("Excluded")) {
										break;
									} else {
										transportProgramList.add(gs.getGroup());
									}
								} else {
									continue;
								}
							}
						}
					} else if (gs.getGroup().getProgram().getType().getName().equals("Individual")
							&& !gs.getStatus().equals("Excluded")) {
						individualProgramList.add(gs.getGroup());
					}
				}
			}
			if (programTypeId == 0) {
				for (StudentGroup grp : studentProgramList) {
					stdGrpMap.put(grp.getId(), grp);
				}
				for (StudentGroup grp : transportProgramList) {
					stdGrpMap.put(grp.getId(), grp);
				}
				for (StudentGroup grp : studentProgramList) {
					stdGrpMap.put(grp.getId(), grp);
				}
			}
			if (programTypeId == 1) {
				for (StudentGroup grp : studentProgramList) {
					stdGrpMap.put(grp.getId(), grp);
				}
			}
			if (programTypeId == 2) {
				for (StudentGroup grp : transportProgramList) {
					stdGrpMap.put(grp.getId(), grp);
				}
			}
			if (programTypeId == 3) {
				for (StudentGroup grp : individualProgramList) {
					stdGrpMap.put(grp.getId(), grp);
				}
			}
		}
	}

	public void selectContribution(ClickActionEvent re) {
		clearInputs();
		if (tmpNdisContribution != null)
			tmpNdisContribution.setUi_selected(false);
		tmpNdisContribution = (NdisContribution) re.getComponent().getAttributes().get("nc");
		tmpNdisContribution.setUi_selected(true);
		ndisContribution = serviceLocator.getNdisContributionService().retrieve(tmpNdisContribution.getId());
	}

	public void tabChangeListner(TabChangeEvent event) {
		// if (event.getOldTabIndex() == 0) {
		// selectedTabIndex = event.getNewTabIndex();
		// }else if (selectedTabIndex == 2) {
		// calculateNdisSummery();
		// }
		// else
		selectedTabIndex = event.getNewTabIndex();
		if (selectedTabIndex == 0) {
			calculateContributionSummary();
		}
	}

	/*
	 * private void setActiveStudent() { if (student != null) { if
	 * (student.getServiceEndDate() != null) processActiveRecords(); else
	 * wizPages = new ArrayList<Integer>(); if (student.getGroups() != null) {
	 * for (GroupedStudent gs : student.getGroups()) { if
	 * (gs.getStatus().equals("Excluded")) gs.setUi_selected(true); } } if
	 * (student.getId() != null) {
	 * sessionContext.setActiveString(student.getContact().getName()); } else
	 * sessionContext.setActiveString("New-Student"); } else
	 * sessionContext.setActiveString(""); }
	 */
	/*
	 * public void tabChangeListner(TabChangeEvent event) { if
	 * (event.getOldTabIndex() == 0) { if (validateStudentFields())
	 * selectedTabIndex = event.getNewTabIndex(); else selectedTabIndex = 0; }
	 * else if (selectedTabIndex == 4) { programTypeId = new Long(0);
	 * selectedGroupId = new Long(0); sDate = null; eDate = null; }
	 * selectedTabIndex = event.getNewTabIndex(); }
	 */

	public void generateNDISEvents() {
		if (validateDetails()) {
			selectAllEvents = false;
			int notGenerated = 0;
			selectedNdisEventsMap = new HashMap<Long, NdisCommittedEvent>();
			selectedNdisEventsMapUncommit = new HashMap<Long, NdisCommittedEvent>();
			ndisEventsMap = new HashMap<Long, List<NdisCommittedEvent>>();
			ndisEvents = new ArrayList<NdisCommittedEvent>();
			clusterMap = new HashMap<Long, List<NdisSupportItem>>();
			// priceMap = new HashMap<Long, List<NdisPrice>>();
			notGenerated = refreshEventMap();
			if (notGenerated > 0)
				showError(notGenerated + " Programs are not processed. Missing NDIS Support Cluster.");
			else if (notGenerated == 1)
				showError(notGenerated + " Program is not processed. Missing NDIS Support Cluster.");
			/*
			 * else { showError(
			 * " No Programs are not processed. Missing NDIS Support Cluster."
			 * ); }
			 */
		
	}

	}

	private int refreshEventMap() {
		if (selectedGroupId > 0) {
			if (stdGrpMap.get(selectedGroupId).getNdis() != null) {
				generateGroupNDISEvents(stdGrpMap.get(selectedGroupId));
			} else {
				showError("Programs are not processed. Missing NDIS Support Cluster.");
			}
			return 0;
		} else {
			int notGenerated = 0;
			for (StudentGroup group : stdGrpMap.values()) {

				if (group.getNdis() != null) {
					generateGroupNDISEvents(group);
				} else {
					notGenerated++;
				}
			}
			return notGenerated;
		}
	}

	private void generateGroupNDISEvents(StudentGroup group) {
		hours = uncommittedHours = committedHours = new Double(0);
		uncommittedunits = committedunits = new Integer(0);
		isCommittedEventsAvailable = false;
		List<NdisCommittedEvent> claimedNdisCommittedEvents = new ArrayList<NdisCommittedEvent>();
		claimedNdisCommittedEvents = serviceLocator.getNdisCommittedEventService()
				.retrieveClaimedCommittedEvents(student.getId(), group.getId(), sDate, eDate);
		HashMap<String, WeekDay> weekDays = null;
		Calendar eventDate = Calendar.getInstance();
		eventDate.setTime(sDate);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(eDate);
		SimpleDateFormat formatDay = new SimpleDateFormat("EEEE");
		overrideCluster(group);
		if (!group.getWeekDays().isEmpty()) {
			weekDays = new HashMap<String, WeekDay>();
			for (WeekDay day : group.getWeekDays()) {
				weekDays.put(day.getName(), day);
			}
		}
		do {
			if (eventDate.getTime().compareTo(group.getEndDate()) > 0) {
				break;
			}
			if (eventDate.getTime().compareTo(group.getStartDate()) < 0) {
				eventDate.setTime(group.getStartDate());
				continue;
			}
			if (serviceLocator.getHolidayService().isHoliday(new Long(1), eventDate.getTime())
					&& !group.getProgram().getType().getName().equals("Individual")) {
				eventDate.add(Calendar.DATE, 1);
				continue;
			}
			if (!group.getWeekDays().isEmpty() && !weekDays.containsKey(formatDay.format(eventDate.getTime()))) {
				eventDate.add(Calendar.DATE, 1);
				continue;
			}
			if (claimedNdisCommittedEvents != null) {
				boolean flag = false;
				for (NdisCommittedEvent event : claimedNdisCommittedEvents) {
					Calendar eveDate = Calendar.getInstance();
					eveDate.setTime(event.getEventDate());
					if (eveDate.compareTo(eventDate) == 0) {
						flag = true;
						break;
					}
				}
				if (flag) {
					eventDate.add(Calendar.DATE, 1);
					continue;
				}
			}
			NdisCommittedEvent ndisEvent = createNdisCommittedEvent(student, eventDate.getTime(), group);
			if (ndisEventsMap.containsKey(group.getId())) {
				ndisEventsMap.get(group.getId()).add(ndisEvent);
			} else {
				List<NdisCommittedEvent> list = new ArrayList<NdisCommittedEvent>();
				list.add(ndisEvent);
				ndisEventsMap.put(group.getId(), list);
			}
			eventDate.add(Calendar.DATE, 1);
		} while (eventDate.getTime().compareTo(endDate.getTime()) <= 0);
		if (isCommittedEventsAvailable) {
			if (programTypeId != 2) {
				List<NdisCommittedEvent> committedevents = serviceLocator.getNdisCommittedEventService()
						.getCommittedEvents(student.getId(), group.getId(), sDate, eDate);
				if (committedevents != null) {
					for (NdisCommittedEvent eve : committedevents) {
						committedHours += eve.getHours();
					}
				} else {
					committedHours = 0.0;
				}
				ndisEvent.setUncommittedhours(uncommittedHours - committedHours);
				ndisEvent.setCommittedHours(committedHours);
			} else {
				List<NdisCommittedEvent> committedevents = serviceLocator.getNdisCommittedEventService()
						.getCommittedEvents(student.getId(), group.getId(), sDate, eDate);
				if (committedevents != null) {
					ndisEvent.setCommittedUnits(committedevents.size());
					committedunits = committedevents.size();
				} else {
					ndisEvent.setCommittedUnits(0);
					committedunits = 0;
				}
				ndisEvent.setUncommittedUnits(uncommittedunits - committedunits);
			}
			ndisEvents.add(ndisEvent);
		} else {
			ndisEvent = new NdisCommittedEvent();
			showError(group.getName() + " has no event(s) to commit for given Time Period.");
		}
	}

	private NdisCommittedEvent createNdisCommittedEvent(Student student, Date eventDate, StudentGroup studentGroup) {
		ndisEvent = new NdisCommittedEvent();
		ndisEvent.setParticipant(student);
		ndisEvent.setStudentGroup(studentGroup);
		ndisEvent.setEventDate(eventDate);
		ndisEvent.setNdisSupportCluster(studentGroup.getNdis());
		ndisEvent.setClaimed(false);
		ndisEvent.setStartTime(studentGroup.getStartTime());
		if (studentGroup.getProgram().getType().getName().equals("Student")) {
			try {
				double diff = studentGroup.getEndTime().getTime() - studentGroup.getStartTime().getTime();
				ndisEvent.setHours(diff / 3600000.0);
				uncommittedHours = uncommittedHours + (diff / 3600000.0);
				ndisEvent.setEndTime(studentGroup.getEndTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (studentGroup.getProgram().getType().getName().equals("Individual")) {
			Calendar start = new GregorianCalendar();
			start.setTime(studentGroup.getStartTime());
			Calendar end = new GregorianCalendar();
			end.setTime(studentGroup.getEndTime());
			if (start.get(Calendar.AM_PM) > end.get(Calendar.AM_PM)) {
				end.add(Calendar.DATE, 1);
				ndisEvent.setEndTime(end.getTime());
				double diff = end.getTimeInMillis() - start.getTimeInMillis();
				ndisEvent.setHours(diff / 3600000.0);
				uncommittedHours = uncommittedHours + (diff / 3600000.0);
			} else {
				ndisEvent.setEndTime(studentGroup.getEndTime());
				double diff = studentGroup.getEndTime().getTime() - studentGroup.getStartTime().getTime();
				ndisEvent.setHours(diff / 3600000.0);
				uncommittedHours = uncommittedHours + (diff / 3600000.0);
			}
		} else {
			ndisEvent.setEndTime(studentGroup.getEndTime());
			ndisEvent.setNoOfUnits(1);
			uncommittedunits = uncommittedunits + 1;
		}
		isCommittedEventsAvailable = true;
		return ndisEvent;
	}

	public void overrideCluster(StudentGroup group) {
		List<NdisSupportItem> clusterList = new ArrayList<NdisSupportItem>();
		String type = group.getNdis().getNdisClusterType();
		List<NdisSupportItem> items = serviceLocator.getNdisSupportItemService().findAllByType(type);
		if (items != null) {
			if (group.getProgram().getType().getName().equals("Student")
					|| group.getProgram().getType().getName().equals("Individual")) {
				double supportClusterOriginal = group.getNdis().getNumerator() / group.getNdis().getDenominator();
				for (NdisSupportItem item : items) {
					double supportClusterOverride = item.getNumerator() / item.getDenominator();
					if (supportClusterOriginal >= supportClusterOverride && isNdisPriceAvailable(group, item)) {
						clusterList.add(item);
						clusterMap.put(group.getId(), clusterList);
					}
				}
			} else {
				for (NdisSupportItem item : items) {
					clusterList.add(item);
					clusterMap.put(group.getId(), clusterList);
				}
			}
		} else {
			showError("There are no NDIS Support cluster(s) for given Cluster Type.");
		}
	}

	private boolean isNdisPriceAvailable(StudentGroup group, NdisSupportItem item) {
		String isSaturdayAvailable, isSundayAvailable, isPriceAvailable, isHolidayPriceAvailable;
		isSaturdayAvailable = isSundayAvailable = isPriceAvailable = isHolidayPriceAvailable = null;
		if (group.isAllowProgramtorunonaholiday()) {
			isHolidayPriceAvailable = "NotAvailable";
			for (NdisPrice price : item.getNdisPrice()) {
				if (price.getNdisTime().equals("Public Holiday")) {
					isHolidayPriceAvailable = "Available";
					break;
				}
			}
		}
		for (WeekDay weekday : group.getWeekDays()) {
			if (weekday.getId() == 6) {
				isSaturdayAvailable = "NotAvailable";
				for (NdisPrice price : item.getNdisPrice()) {
					if (price.getNdisTime().equals("Saturday")) {
						isSaturdayAvailable = "Available";
						break;
					}
				}
			}
			if (weekday.getId() == 7) {
				isSundayAvailable = "NotAvailable";
				for (NdisPrice price : item.getNdisPrice()) {
					if (price.getNdisTime().equals("Sunday")) {
						isSundayAvailable = "Available";
						break;
					}
				}
			}
		}
		Long currentEndTime, eTime1, eTime2, eTime3, eTime4;
		currentEndTime = eTime1 = eTime2 = eTime3 = eTime4 = null;
		Date endTime = group.getEndTime();
		DateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss a");
		String endTimeStr = timeFormatter.format(endTime);
		String ndisTime = null;

		try {
			currentEndTime = timeFormatter.parse(endTimeStr).getTime();
			Date time1Dt = timeFormatter.parse("08:00:00 PM");
			eTime1 = time1Dt.getTime();
			Date time2Dt = timeFormatter.parse("11:59:00 PM");
			eTime2 = time2Dt.getTime();
			Date time3Dt = timeFormatter.parse("12:00:00 AM");
			eTime3 = time3Dt.getTime();
			Date time4Dt = timeFormatter.parse("06:00:00 AM");
			eTime4 = time4Dt.getTime();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (eTime3 <= currentEndTime && currentEndTime <= eTime4) {
			ndisTime = "Weekday Overnight Active";
		} else if (eTime4 <= currentEndTime && currentEndTime <= eTime1) {
			ndisTime = "Weekday Daytime";
		} else if (eTime1 < currentEndTime && currentEndTime <= eTime2 && group.isInactiveovernight()) {
			ndisTime = "Overnight Inactive";
		} else if (eTime1 < currentEndTime && currentEndTime <= eTime2 && !group.isInactiveovernight()) {
			ndisTime = "Weekday Evening";
		}
		for (NdisPrice price : item.getNdisPrice()) {
			isPriceAvailable = "NotAvailable";
			if (price.getNdisTime().equals(ndisTime)) {
				isPriceAvailable = "Available";
				break;
			}
		}

		if (isSaturdayAvailable != null && isSaturdayAvailable.equals("NotAvailable")) {
			return false;
		}
		if (isSundayAvailable != null && isSundayAvailable.equals("NotAvailable")) {
			return false;
		}
		if (isPriceAvailable != null && isPriceAvailable.equals("NotAvailable")) {
			return false;
		}
		if (isHolidayPriceAvailable != null && isHolidayPriceAvailable.equals("NotAvailable")) {
			return false;
		}

		return true;
	}

	public void selectAllNdisEvents(ValueChangeEvent ve) {
		committedList = new ArrayList<String>();
		selectAllEvents = (Boolean) ve.getNewValue();
		for (NdisCommittedEvent event : ndisEvents) {
			event.setUi_selected(selectAllEvents);
			if (event.isUi_selected()) {
				if (programTypeId == 2) {
					if (event.getCommittedUnits() != null && event.getCommittedUnits() > 0) {
						selectedNdisEventsMapUncommit.put(event.getStudentGroup().getId(), event);
					} else {
						selectedNdisEventsMap.put(event.getStudentGroup().getId(), event);
					}
				} else {
					if (event.getCommittedHours() != null && event.getCommittedHours() > 0) {
						selectedNdisEventsMapUncommit.put(event.getStudentGroup().getId(), event);
					} else {
						selectedNdisEventsMap.put(event.getStudentGroup().getId(), event);
					}
				}
			} else {
				selectedNdisEventsMapUncommit.clear();
				selectedNdisEventsMap.clear();
			}
		}
	}

	public void selectNdisEvent(ValueChangeEvent ve) {
		selectEvent = ((Boolean) ve.getNewValue() != null ? true : false);
		eventNdis = (NdisCommittedEvent) ve.getComponent().getAttributes().get("event");
		// Long groupId = eventNdis.getStudentGroup().getId();
		// List<NdisCommittedEvent> list = ndisEventsMap.get(groupId);
		eventNdis.setUi_selected(selectEvent);
		if (eventNdis.isUi_selected()) {
			if (programTypeId == 2) {
				if (eventNdis.getCommittedUnits() != null && eventNdis.getCommittedUnits() > 0) {
					selectedNdisEventsMapUncommit.put(eventNdis.getStudentGroup().getId(), eventNdis);
				} else {
					selectedNdisEventsMap.put(eventNdis.getStudentGroup().getId(), eventNdis);
				}
			} else {
				if (eventNdis.getCommittedHours() != null && eventNdis.getCommittedHours() > 0) {
					selectedNdisEventsMapUncommit.put(eventNdis.getStudentGroup().getId(), eventNdis);
				} else {
					selectedNdisEventsMap.put(eventNdis.getStudentGroup().getId(), eventNdis);
				}
			}
		} else {
			if ((eventNdis.getCommittedHours() != null && eventNdis.getCommittedHours() > 0)
					|| (eventNdis.getCommittedUnits() != null && eventNdis.getCommittedUnits() > 0)) {
				selectedNdisEventsMapUncommit.remove(eventNdis.getStudentGroup().getId());
			} else {
				selectedNdisEventsMap.remove(eventNdis.getStudentGroup().getId());
			}
		}
		if (ndisEvents.size() == selectedNdisEventsMap.size()) {
			selectAllEvents = true;
		} else if (ndisEvents.size() == selectedNdisEventsMapUncommit.size()) {
			selectAllEvents = true;
		} else {
			selectAllEvents = false;
		}
	}

	public void commitAll(ActionEvent ae) {
		boolean save = true;
		List<NdisCommittedEvent> finalCommittedEventsList = new ArrayList<NdisCommittedEvent>();
		for (NdisCommittedEvent eve : selectedNdisEventsMap.values()) {
			Double currentcommittedHours = 0.0;
			int count = 0;
			for (NdisCommittedEvent event : ndisEventsMap.get(eve.getStudentGroup().getId())) {
				if (eve.getClusterOverrideId() != 0
						&& !eve.getNdisSupportCluster().getId().equals(eve.getClusterOverrideId())) {
					event.setClusterOverride(new NdisSupportItem());
					event.getClusterOverride().setId(eve.getClusterOverrideId());
				}
				if (programTypeId == 2) {
					// event.setPrice(eve.getPrice());
					event.setKmsPerDay(kmsPerDay);
					count++;
				} else {
					currentcommittedHours += event.getHours();
					committedHours += currentcommittedHours;
				}
			}
			eve.setCommittedHours(currentcommittedHours);
			eve.setUncommittedhours(0.0);
			eve.setCommittedUnits(count);
			eve.setUncommittedUnits(0);

			finalCommittedEventsList.addAll(ndisEventsMap.get(eve.getStudentGroup().getId()));
			eve.setUi_selected(false);
		}
		serviceLocator.getNdisCommittedEventService().calculateCommittedEventPrice(finalCommittedEventsList, save);
		selectedNdisEventsMap.clear();
		if (ndisEvents.size() == selectedNdisEventsMap.size()) {
			selectAllEvents = true;
		} else if (ndisEvents.size() == selectedNdisEventsMapUncommit.size()) {
			selectAllEvents = true;
		} else {
			selectAllEvents = false;
		}
		calculateContributionSummary();
		showInfo("Selected uncommitted events are committed successfully for " + student.getContact().getName());
	}

	public void uncommitEvents(ActionEvent ae) {
		for (NdisCommittedEvent eve : selectedNdisEventsMapUncommit.values()) {
			uncommitEvents(student.getId(), eve.getStudentGroup().getId(), sDate, eDate);
			selectedNdisEventsMap.put(eve.getStudentGroup().getId(), eve);
			Double currentcommittedHours = 0.0;
			int count = 0;
			for (NdisCommittedEvent event : ndisEventsMap.get(eve.getStudentGroup().getId())) {
				if (programTypeId == 2) {
					// event.setPrice(eve.getPrice());
					count++;
				} else {
					currentcommittedHours += event.getHours();
					// committedHours += currentcommittedHours;
				}
			}
			eve.setUncommittedhours(currentcommittedHours);
			eve.setCommittedHours(0.0);
			eve.setCommittedUnits(0);
			committedHours = 0.0;
			eve.setUncommittedUnits(count);
		}
		calculateContributionSummary();
		selectedNdisEventsMapUncommit.clear();
		showInfo("Selected committed events are uncommitted successfully for " + student.getContact().getName());
		selectAllEvents = false;
		selectedNdisEventsMap = new HashMap<Long, NdisCommittedEvent>();
		selectedNdisEventsMapUncommit = new HashMap<Long, NdisCommittedEvent>();
		ndisEventsMap = new HashMap<Long, List<NdisCommittedEvent>>();
		ndisEvents = new ArrayList<NdisCommittedEvent>();
		clusterMap = new HashMap<Long, List<NdisSupportItem>>();
		refreshEventMap();
	}

	private void uncommitEvents(Long studentId, Long groupId, Date fromDate, Date toDate) {
		serviceLocator.getNdisCommittedEventService().deleteSelectedEvents(studentId, groupId, fromDate, toDate);
	}

	private boolean validateDetails() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (programTypeId == 0) {
			showError("Program Type can not be empty..");
			componentIds.add("select_ProgramTypes");
			highlightInputs(componentIds);
			return false;
		}
		if (sDate == null) {
			showError("Start Date  can not be empty..");
			componentIds.add("input_EventFromDate");
			highlightInputs(componentIds);
			return false;
		}
		if (eDate == null) {
			showError("End Date  can not be empty..");
			componentIds.add("input_EventToDate");
			highlightInputs(componentIds);
			return false;
		}
		if (eDate.compareTo(sDate) < 0) {
			showError("Start date should be before the end date..");
			return false;
		}
		return true;
	}

	/*
	 * NdisAncillaryCost Methods
	 */

	public void showNDISAncillaryPopup() {
		clearInputs();
		visibleAncillaryPopUp = !visibleAncillaryPopUp;
		/*
		 * ndisSupportItem = new NdisSupportItem(); ndisPrice=new NdisPrice();
		 */
	}

	public void addNewNdisAncillaryCost() {
		clearInputs();
		ndisAncillaryCost = new NdisAncillaryCost();
		date = null;
		noofunit = 0;
		ndisSupportItemId = new Long(0);
		ndisPricesMap.clear();
		ndisPriceId = new Long(0);
		uom = null;
		showNDISAncillaryPopup();
	}

	public void addNewNdisAncillary() {
		clearInputs();
		ndisAncillaryCost = new NdisAncillaryCost();
		date = null;
		noofunit = 0;
		ndisSupportItemId = new Long(0);
		ndisPriceId = new Long(0);
		uom = "";
	}

	public boolean saveNdisAncillaryCost() {
		if (validateNdisAncyCost()) {
			ndisAncillaryCost.setNdisPrice(ndisPricesMap.get(ndisPriceId));
			if (ndisAncillaryCost.getId() == null) {
				ndisAncillaryCost.setNdisSupportItem(ndisSupportItem);
				ndisAncillaryCost.setStudent(student);
				ndisAncillaryCost.setUom(uom);
				ndisAncillaryCost.setDate(date);
				ndisAncillaryCost.setNoofunit(noofunit);
				ndisAncillaryCost.setClaimed(false);
				ndisAncillaryCost = serviceLocator.getNdisAncillaryCostService().create(ndisAncillaryCost);
				tempNdisAncillaryCost = ndisAncillaryCost;
				ndisAncillaryCost = serviceLocator.getNdisAncillaryCostService()
						.retrieve(tempNdisAncillaryCost.getId());
				showInfo(" added successfully..");

			} else {
				ndisAncillaryCost.setNdisSupportItem(ndisSupportItem);
				ndisAncillaryCost.setStudent(student);
				// ndisAncillaryCost.setNdisPrice(ndisPrice);
				ndisAncillaryCost.setUom(uom);
				ndisAncillaryCost.setDate(date);
				ndisAncillaryCost.setNoofunit(noofunit);
				ndisAncillaryCost.setClaimed(false);
				ndisAncillaryCost = serviceLocator.getNdisAncillaryCostService().update(ndisAncillaryCost);

				tempNdisAncillaryCost = ndisAncillaryCost;
				ndisAncillaryCost = serviceLocator.getNdisAncillaryCostService()
						.retrieve(tempNdisAncillaryCost.getId());
				showInfo(" updated successfully..");
			}
			allNdisAncillaryCosts = serviceLocator.getNdisAncillaryCostService()
					.listNdisAncyCostByStudent(student.getId());

			return true;

		}

		return false;
	}

	private boolean validateNdisAncyCost() {
		List<String> componentIds = new ArrayList<String>();
		clearInputs();
		if (date == null) {
			showError("Select a date");
			componentIds.add("input_NDISAncillaryCostDate");
			highlightInputs(componentIds);
			return false;
		}
		if (ndisSupportItemId == 0) {
			showError("Please select NDIS Support item");
			componentIds.add("select_NDISAncillaryItem");
			highlightInputs(componentIds);
			return false;
		}
		if (ndisPriceId == 0) {
			showError("Please select NDIS price item");
			componentIds.add("select_NDISprice");
			highlightInputs(componentIds);
			return false;
		}
		if (noofunit == 0) {
			showError("No of Unit can not be empty");
			componentIds.add("input_NoofUnit");
			highlightInputs(componentIds);
			return false;
		}
		return true;
	}

	public void saveAndExit() {
		if (saveNdisAncillaryCost())
			showNDISAncillaryPopup();
	}

	public void selectNdisItem(ValueChangeEvent event) {
		ndisSupportItemId = (Long) event.getNewValue();
		ndisSupportItem = ndisItemMap.get(ndisSupportItemId);
		ndisPricesMap = new HashMap<Long, NdisPrice>();
		for (NdisPrice price : serviceLocator.getNdisPriceService().listNdisPrices(ndisSupportItemId)) {
			ndisPricesMap.put(price.getId(), price);

		}
		ndisPriceId = new Long(0);
		uom = null;

	}

	public void selectNdisPriceItem(ValueChangeEvent event) {
		Long id = (Long) event.getNewValue();
		if (id > 0) {
			uom = serviceLocator.getNdisPriceService().retrieve(id).getUom();
		} else {
			uom = "";
		}
	}

	public void removeNdisAncyCost(ActionEvent ae) {
		ndisAncillaryCost = (NdisAncillaryCost) ae.getComponent().getAttributes().get("ndisAncyCost");
		serviceLocator.getNdisAncillaryCostService().delete(ndisAncillaryCost.getId());
		allNdisAncillaryCosts.remove(ndisAncillaryCost);
		allNdisAncillaryCosts = serviceLocator.getNdisAncillaryCostService().listNdisAncyCostByStudent(student.getId());
	}

	public void editNdisAncyCost(ActionEvent ae) {
		clearInputs();
		ndisAncillaryCost = (NdisAncillaryCost) ae.getComponent().getAttributes().get("ndisAncyCost");
		ndisSupportItem = ndisAncillaryCost.getNdisSupportItem();
		date = ndisAncillaryCost.getDate();
		noofunit = ndisAncillaryCost.getNoofunit();
		student = ndisAncillaryCost.getStudent();
		ndisSupportItemId = ndisAncillaryCost.getNdisSupportItem().getId();
		ndisSupportItem = ndisItemMap.get(ndisSupportItemId);
		ndisPricesMap = new HashMap<Long, NdisPrice>();
		for (NdisPrice price : serviceLocator.getNdisPriceService().listNdisPrices(ndisSupportItemId)) {
			ndisPricesMap.put(price.getId(), price);
		}
		ndisPrice = ndisAncillaryCost.getNdisPrice();
		uom = ndisAncillaryCost.getUom();
		if (ndisPrice != null) {
			ndisPriceId = ndisPrice.getId();
		}
		allNdisAncillaryCosts = serviceLocator.getNdisAncillaryCostService().listNdisAncyCostByStudent(student.getId());
		showNDISAncillaryPopup();
	}

	public void addnewcontribution() {
		clearInputs();
		ndisContribution = new NdisContribution();
		ndisContribution.setAmount(0.0);
		ndisContribution.setClusterType(new Long(0));
		ndisContribution.setContributedDate(null);
		addContributionPopup();
	}

	public void addContributionPopup() {
		visibleAddContribution = !visibleAddContribution;
	}

	public void savecontribution() {
		if (validateContributionFields()) {
			if (ndisContribution.getId() == null) {
				ndisContribution.setStudent(student);
				ndisContribution = serviceLocator.getNdisContributionService().create(ndisContribution);
				ndisContribution.setUi_selected(true);
				tmpNdisContribution = ndisContribution;
				allNdisContributions.add(tmpNdisContribution);
				ndisContribution = serviceLocator.getNdisContributionService().retrieve(tmpNdisContribution.getId());
				showInfo("NDIS Contribution created successfully.");
			} else {
				ndisContribution = serviceLocator.getNdisContributionService().update(ndisContribution);
				ndisContribution.setUi_selected(true);
				allNdisContributions.set(allNdisContributions.indexOf(tmpNdisContribution), ndisContribution);
				tmpNdisContribution = ndisContribution;
				ndisContribution = serviceLocator.getNdisContributionService().retrieve(tmpNdisContribution.getId());
				showInfo("NDIS Contribution updated successfully.");
			}
			visibleAddContribution = false;
			calculateContributionSummary();
		}
	}

	public void deletecontribution(ActionEvent ae) {
		ndisContribution = (NdisContribution) ae.getComponent().getAttributes().get("nc");
		if (ndisContribution != null) {
			try {
				serviceLocator.getNdisContributionService().delete(ndisContribution.getId());
				allNdisContributions.remove(ndisContribution);
				ndisContribution = tmpNdisContribution = null;
				showInfo("NDIS Contribution deleted succesfully..");
				calculateContributionSummary();
			} catch (DataIntegrityViolationException d) {
				showError(Util.getMessage("error.integrity"));
			}
		}
	}

	private boolean validateContributionFields() {
		Double amount = ndisContribution.getAmount();
		Date contributedDate = ndisContribution.getContributedDate();
		if (ndisContribution.getClusterType().equals(new Long(0))) {
			showError("Support cluster type is required");
			return false;
		}
		if (amount <= 0.0) {
			showError("Invalid Amount.");
			return false;
		}
		if (contributedDate == null) {
			showError("Contributed Date is required");
			return false;
		}
		return true;
	}
	/*
	 * public List<Student> getStudents() { return students; }
	 * 
	 * public void setStudents(List<Student> students) { this.students =
	 * students; }
	 * 
	 * public Student getStudent() { return student; }
	 * 
	 * public void setStudent(Student student) { this.student = student; }
	 * 
	 * public Student getTmpStudent() { return tmpStudent; }
	 * 
	 * public void setTmpStudent(Student tmpStudent) { this.tmpStudent =
	 * tmpStudent; }
	 * 
	 * public int getSelectedTabIndex() { return selectedTabIndex; }
	 * 
	 * public void setSelectedTabIndex(int selectedTabIndex) {
	 * this.selectedTabIndex = selectedTabIndex; }
	 * 
	 * public String getStatus() { return status; }
	 * 
	 * public void setStatus(String status) { this.status = status; }
	 * 
	 * public boolean isVisibleContribution() { return visibleContribution; }
	 * 
	 * public void setVisibleContribution(boolean visibleContribution) {
	 * this.visibleContribution = visibleContribution; }
	 * 
	 * public boolean isDefaultAddress() { return defaultAddress; }
	 * 
	 * public void setDefaultAddress(boolean defaultAddress) {
	 * this.defaultAddress = defaultAddress; }
	 * 
	 * public Student getNdisAncyCoststudent() { return ndisAncyCoststudent; }
	 * 
	 * public void setNdisAncyCoststudent(Student ndisAncyCoststudent) {
	 * this.ndisAncyCoststudent = ndisAncyCoststudent; }
	 * 
	 * public boolean isAllGroups() { return allGroups; }
	 * 
	 * public void setAllGroups(boolean allGroups) { this.allGroups = allGroups;
	 * }
	 */

	public List<Student> getStudents() {
		return students;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return student;
	}

	public void setSearchStudentText(String searchStudentText) {
		this.searchStudentText = searchStudentText;
	}

	public String getSearchStudentText() {
		return searchStudentText;
	}

	public void setSelectedTabIndex(int selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}

	public int getSelectedTabIndex() {
		return selectedTabIndex;
	}

	/*
	 * public boolean isSelectAllGroups() { return selectAllGroups; }
	 * 
	 * public void setSelectAllGroups(boolean selectAllGroups) {
	 * this.selectAllGroups = selectAllGroups; }
	 */

	public HashMap<Long, StudentGroup> getSelectedGroups() {
		return selectedGroups;
	}

	public boolean isVisibleGroups() {
		return visibleGroups;
	}

	public void setVisibleGroups(boolean visibleGroups) {
		this.visibleGroups = visibleGroups;
	}

	public void setSearchGroupText(String searchGroupText) {
		this.searchGroupText = searchGroupText;
	}

	public String getSearchGroupText() {
		return searchGroupText;
	}

	public void setSearchSNText(String searchSNText) {
		this.searchSNText = searchSNText;
	}

	public String getSearchSNText() {
		return searchSNText;
	}

	public boolean isVisibleTransportGroups() {
		return visibleTransportGroups;
	}

	public void setTmpStudent(Student tmpStudent) {
		this.tmpStudent = tmpStudent;
	}

	public Student getTmpStudent() {
		return tmpStudent;
	}

	public void setStudentGroup(StudentGroup studentGroup) {
		this.studentGroup = studentGroup;
	}

	public StudentGroup getStudentGroup() {
		return studentGroup;
	}

	public void setGroupedStudent(GroupedStudent groupedStudent) {
		this.groupedStudent = groupedStudent;
	}

	public GroupedStudent getGroupedStudent() {
		return groupedStudent;
	}
	/*
	 * public void setAllGroups(boolean allGroups) { this.allGroups = allGroups;
	 * }
	 * 
	 * public boolean isAllGroups() { return allGroups; }
	 */

	public List<ProgramEvent> getEvents() {
		return events;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/*
	 * public Date getToDate() { return toDate; }
	 * 
	 * public void setToDate(Date toDate) { this.toDate = toDate; }
	 */

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getJsonString() {
		return jsonString;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public SimpleDateFormat getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(SimpleDateFormat timeFormat) {
		this.timeFormat = timeFormat;
	}

	public void setOneRowTable(List<Object> oneRowTable) {
		this.oneRowTable = oneRowTable;
	}

	public List<Object> getOneRowTable() {
		return oneRowTable;
	}

	public String getSelectedStudentId() {
		return selectedStudentId;
	}

	public void setSelectedStudentId(String selectedStudentId) {
		this.selectedStudentId = selectedStudentId;
	}

	/*
	 * public void setSelectedRelationshipId(Long selectedRelationshipId) {
	 * this.selectedRelationshipId = selectedRelationshipId; }
	 * 
	 * public Long getSelectedRelationshipId() { return selectedRelationshipId;
	 * }
	 */

	public List<SelectItem> getRelationships() {
		return relationships;
	}

	public List<ProgramEvent> getActProEvents() {
		return actProEvents;
	}

	public int getWizardPage() {
		return wizardPage;
	}

	public List<GroupedStudent> getActGroupedStudents() {
		return actGroupedStudents;
	}

	public void setSelectAllEvents(boolean selectAllEvents) {
		this.selectAllEvents = selectAllEvents;
	}

	public boolean isSelectAllEvents() {
		return selectAllEvents;
	}

	/*
	 * public String getNxtBtnTxt() { return nxtBtnTxt; }
	 */

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public List<StudentEvent> getStudentEvents() {
		return studentEvents;
	}

	public void setStudentEvents(List<StudentEvent> studentEvents) {
		this.studentEvents = studentEvents;
	}

	public Date getsDate() {
		return sDate;
	}

	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}

	public Date geteDate() {
		return eDate;
	}

	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}

	public List<StudentGroup> getPrograms() {
		return programs;
	}

	public void setPrograms(ArrayList<StudentGroup> programs) {
		this.programs = programs;
	}

	public boolean isVisibleContribution() {
		return visibleContribution;
	}

	public void setVisibleContribution(boolean visibleContribution) {
		this.visibleContribution = visibleContribution;
	}

	public boolean isVisibleLOS() {
		return visibleLOS;
	}

	public void setVisibleLOS(boolean visibleLOS) {
		this.visibleLOS = visibleLOS;
	}

	public Long getSelectedGroupId() {
		return selectedGroupId;
	}

	public void setSelectedGroupId(Long selectedGroupId) {
		this.selectedGroupId = selectedGroupId;
	}

	/*
	 * public Collection<StudentGroup> getStdGroups() { return
	 * (Collection<StudentGroup>) stdGrpMap.values(); }
	 */

	public Collection<StudentGroup> getStdGroups() {
		return (Collection<StudentGroup>) stdGrpMap.values();
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public Double getUncommittedHours() {
		return uncommittedHours;
	}

	public void setUncommittedHours(Double uncommittedHours) {
		this.uncommittedHours = uncommittedHours;
	}

	public NdisCommittedEvent getNdisEvent() {
		return ndisEvent;
	}

	public void setNdisEvent(NdisCommittedEvent ndisEvent) {
		this.ndisEvent = ndisEvent;
	}

	public List<NdisSupportItem> getClusterOverrideList(Long clusterId) {
		return clusterMap.get(clusterId);
	}

	public List<NdisPrice> getNdisPriceList(Long priceId) {
		return priceMap.get(priceId);
	}

	public NdisSupportItem getItem() {
		return item;
	}

	public void setItem(NdisSupportItem item) {
		this.item = item;
	}

	public Double getCommittedHours() {
		return committedHours;
	}

	public void setCommittedHours(Double committedHours) {
		this.committedHours = committedHours;
	}

	public List<NdisCommittedEvent> getNdisEvents() {
		return ndisEvents;
	}

	public void setNdisEvents(List<NdisCommittedEvent> ndisEvents) {
		this.ndisEvents = ndisEvents;
	}

	public NdisCommittedEvent getEventNdis() {
		return eventNdis;
	}

	public void setEventNdis(NdisCommittedEvent eventNdis) {
		this.eventNdis = eventNdis;
	}

	public boolean isSelectEvent() {
		return selectEvent;
	}

	public void setSelectEvent(boolean selectEvent) {
		this.selectEvent = selectEvent;
	}

	public HashMap<Long, NdisCommittedEvent> getSelectedNdisEventsMap() {
		return selectedNdisEventsMap;
	}

	public void setSelectedNdisEventsMap(HashMap<Long, NdisCommittedEvent> selectedNdisEventsMap) {
		this.selectedNdisEventsMap = selectedNdisEventsMap;
	}

	public HashMap<Long, List<NdisCommittedEvent>> getNdisEventsMap() {
		return ndisEventsMap;
	}

	public void setNdisEventsMap(HashMap<Long, List<NdisCommittedEvent>> ndisEventsMap) {
		this.ndisEventsMap = ndisEventsMap;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public boolean isVisibleAncillaryPopUp() {
		return visibleAncillaryPopUp;
	}

	public void setVisibleAncillaryPopUp(boolean visibleAncillaryPopUp) {
		this.visibleAncillaryPopUp = visibleAncillaryPopUp;
	}

	public List<SelectItem> getNdisSelectItemsList() {
		return ndisSelectItemsList;
	}

	public void setNdisSelectItemsList(List<SelectItem> ndisSelectItemsList) {
		this.ndisSelectItemsList = ndisSelectItemsList;
	}

	public NdisSupportItem getNdisSupportItem() {
		return ndisSupportItem;
	}

	public void setNdisSupportItem(NdisSupportItem ndisSupportItem) {
		this.ndisSupportItem = ndisSupportItem;
	}

	public NdisAncillaryCost getTempNdisAncillaryCost() {
		return tempNdisAncillaryCost;
	}

	public void setTempNdisAncillaryCost(NdisAncillaryCost tempNdisAncillaryCost) {
		this.tempNdisAncillaryCost = tempNdisAncillaryCost;
	}

	public NdisAncillaryCost getNdisAncillaryCost() {
		return ndisAncillaryCost;
	}

	public void setNdisAncillaryCost(NdisAncillaryCost ndisAncillaryCost) {
		this.ndisAncillaryCost = ndisAncillaryCost;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public List<NdisAncillaryCost> getAllNdisAncillaryCosts() {
		return allNdisAncillaryCosts;
	}

	public void setAllNdisAncillaryCosts(List<NdisAncillaryCost> allNdisAncillaryCosts) {
		this.allNdisAncillaryCosts = allNdisAncillaryCosts;
	}

	public Collection<NdisPrice> getNdisPricesList() {
		return (Collection<NdisPrice>) ndisPricesMap.values();
	}

	public List<SelectItem> getNdisSelectPriceItemsList() {
		return ndisSelectPriceItemsList;
	}

	public void setNdisSelectPriceItemsList(List<SelectItem> ndisSelectPriceItemsList) {
		this.ndisSelectPriceItemsList = ndisSelectPriceItemsList;
	}

	public Collection<NdisSupportItem> getNdisItemList() {
		return (Collection<NdisSupportItem>) ndisItemMap.values();
	}

	public void setNdisSupportItemId(Long ndisSupportItemId) {
		this.ndisSupportItemId = ndisSupportItemId;
	}

	public Long getNdisSupportItemId() {
		return ndisSupportItemId;
	}

	public void setNdisPriceId(Long ndisPriceId) {
		this.ndisPriceId = ndisPriceId;
	}

	public Long getNdisPriceId() {
		return ndisPriceId;
	}

	public NdisPrice getNdisPrice() {
		return ndisPrice;
	}

	public void setNdisPrice(NdisPrice ndisPrice) {
		this.ndisPrice = ndisPrice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNoofunit() {
		return noofunit;
	}

	public void setNoofunit(int noofunit) {
		this.noofunit = noofunit;
	}

	public Long getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(Long programTypeId) {
		this.programTypeId = programTypeId;
	}

	public Long getSelectedProgramTypeId() {
		return selectedProgramTypeId;
	}

	public void setSelectedProgramTypeId(Long selectedProgramTypeId) {
		this.selectedProgramTypeId = selectedProgramTypeId;
	}

	public List<SelectItem> getGroupSelectItems() {
		return groupSelectItems;
	}

	public void setGroupSelectItems(List<SelectItem> groupSelectItems) {
		this.groupSelectItems = groupSelectItems;
	}

	/*
	 * public Integer getNoOfUnits() { return noOfUnits; }
	 * 
	 * public void setNoOfUnits(Integer noOfUnits) { this.noOfUnits = noOfUnits;
	 * }
	 */

	public List<String> getCommittedList() {
		return committedList;
	}

	public void setCommittedList(List<String> committedList) {
		this.committedList = committedList;
	}

	public HashMap<Long, NdisCommittedEvent> getSelectedNdisEventsMapUncommit() {
		return selectedNdisEventsMapUncommit;
	}

	public void setSelectedNdisEventsMapUncommit(HashMap<Long, NdisCommittedEvent> selectedNdisEventsMapUncommit) {
		this.selectedNdisEventsMapUncommit = selectedNdisEventsMapUncommit;
	}

	public Double getKmsPerDay() {
		return kmsPerDay;
	}

	public void setKmsPerDay(Double kmsPerDay) {
		this.kmsPerDay = kmsPerDay;
	}

	public Integer getUncommittedunits() {
		return uncommittedunits;
	}

	public void setUncommittedunits(Integer uncommittedunits) {
		this.uncommittedunits = uncommittedunits;
	}

	public NdisContribution getNdisContribution() {
		return ndisContribution;
	}

	public void setNdisContribution(NdisContribution ndisContribution) {
		this.ndisContribution = ndisContribution;
	}

	public NdisContribution getTmpNdisContribution() {
		return tmpNdisContribution;
	}

	public void setTmpNdisContribution(NdisContribution tmpNdisContribution) {
		this.tmpNdisContribution = tmpNdisContribution;
	}

	public List<NdisContribution> getAllNdisContributions() {
		return allNdisContributions;
	}

	public void setAllNdisContributions(List<NdisContribution> allNdisContributions) {
		this.allNdisContributions = allNdisContributions;
	}

	public boolean isVisibleAddContribution() {
		return visibleAddContribution;
	}

	public void setVisibleAddContribution(boolean visibleAddContribution) {
		this.visibleAddContribution = visibleAddContribution;
	}

	public List<NdisContributionSummary> getNcsList() {
		return ncsList;
	}

	public NdisContribution getContribution() {
		return contribution;
	}

	public void setContribution(NdisContribution contribution) {
		this.contribution = contribution;
	}
}