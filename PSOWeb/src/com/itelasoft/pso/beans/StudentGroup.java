package com.itelasoft.pso.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Indexed
@Table(name = "studentgroups")
public class StudentGroup extends BaseEntity {

	private Long id;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String name;
	private Date startDate;
	private Date endDate;
	private Date startTime;
	private Date endTime;
	private double chargeAmount;
	private boolean lunchIncluded;
	private String status;
	@IndexedEmbedded
	private Program program;
	private Location location;

	private Vehicle vehicle;
	private FileData photo;
	private List<WeekDay> weekDays = new ArrayList<WeekDay>();
	private List<GroupedStudent> students, availableStudents;
	private List<GroupedStaff> staffMembers;
	private StudentGroup parentGroup;
	private String lunchTime;
	private NdisSupportItem ndis;
	private boolean returnAvailable;
	private List<ProgramEvent> events;
	private SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm a");
	private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy - EEE : ");
	private List<ReferenceItem> staffSkills = new ArrayList<ReferenceItem>();
	private Double ndisUnitPrice;
	private boolean allowProgramtorunonaholiday;
	private boolean inactiveovernight;
	private String cAmount;
	// private String gstCode;
	private List<NdisCommittedEvent> ndisCommittedEvents;

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isLunchIncluded() {
		return lunchIncluded;
	}

	public void setLunchIncluded(boolean lunchIncluded) {
		this.lunchIncluded = lunchIncluded;
	}

	public String getStatus() {
		return status;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	@ManyToOne
	@JoinColumn(name = "programId")
	public Program getProgram() {
		return program;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "locationId")
	public Location getLocation() {
		return location;
	}

	public void setNdis(NdisSupportItem ndis) {
		this.ndis = ndis;
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "refno")
	public NdisSupportItem getNdis() {
		return ndis;
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "vehicleId")
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public void setPhoto(FileData photo) {
		this.photo = photo;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "photoId")
	public FileData getPhoto() {
		return photo;
	}

	public void setWeekDays(List<WeekDay> weekDays) {
		this.weekDays = weekDays;
	}

	@ManyToMany(cascade = CascadeType.PERSIST)
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@JoinTable(name = "GroupsWeekDaysXRef", joinColumns = @JoinColumn(name = "groupId") , inverseJoinColumns = @JoinColumn(name = "weekDayId") )
	public List<WeekDay> getWeekDays() {
		return weekDays;
	}

	public void setStudents(List<GroupedStudent> students) {
		this.students = students;
	}

	@OneToMany(mappedBy = "group")
	@Cascade(value = { org.hibernate.annotations.CascadeType.DELETE_ORPHAN,
			org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	public List<GroupedStudent> getStudents() {
		return students;
	}

	// @ManyToMany(cascade = CascadeType.PERSIST)
	// @JoinTable(name = "StudentGroupStaffXRef", joinColumns = @JoinColumn(name
	// = "groupId"), inverseJoinColumns = @JoinColumn(name = "staffId"))
	// public List<StaffMember> getStaffMembers() {

	@OneToMany(mappedBy = "group")
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@Cascade(value = { org.hibernate.annotations.CascadeType.DELETE_ORPHAN,
			org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	public List<GroupedStaff> getStaffMembers() {
		return staffMembers;
	}

	public void setStaffMembers(List<GroupedStaff> staffMembers) {
		this.staffMembers = staffMembers;
	}

	public void setEvents(List<ProgramEvent> events) {
		this.events = events;
	}

	@Transient
	public List<ProgramEvent> getEvents() {
		return events;
	}

	@Transient
	public String getEventInfo(Date eventDate) {
		return formatDate.format(eventDate) + getName() + ":" + formatTime.format(startTime) + "-"
				+ formatTime.format(endTime) + "|0";
	}

	public void setParentGroup(StudentGroup parentGroup) {
		this.parentGroup = parentGroup;
	}

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "parentId")
	public StudentGroup getParentGroup() {
		return parentGroup;
	}

	public void setReturnAvailable(boolean returnAvailable) {
		this.returnAvailable = returnAvailable;
	}

	@Transient
	public boolean isReturnAvailable() {
		return returnAvailable;
	}

	public void setStaffSkills(List<ReferenceItem> staffSkills) {
		this.staffSkills = staffSkills;
	}

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "groupstaffskillsxref", joinColumns = @JoinColumn(name = "groupId") , inverseJoinColumns = @JoinColumn(name = "referenceitemid") )
	public List<ReferenceItem> getStaffSkills() {
		return staffSkills;
	}

	public String getLunchTime() {
		return lunchTime;
	}

	public void setLunchTime(String lunchTime) {
		this.lunchTime = lunchTime;
	}

	public Double getNdisUnitPrice() {
		return ndisUnitPrice;
	}

	public void setNdisUnitPrice(Double ndisUnitPrice) {
		this.ndisUnitPrice = ndisUnitPrice;
	}

	public boolean isAllowProgramtorunonaholiday() {
		return allowProgramtorunonaholiday;
	}

	public void setAllowProgramtorunonaholiday(boolean allowProgramtorunonaholiday) {
		this.allowProgramtorunonaholiday = allowProgramtorunonaholiday;
	}
	/*
	 * public String getGstCode() { return gstCode; }
	 * 
	 * public void setGstCode(String gstCode) { this.gstCode = gstCode; }
	 */

	public boolean isInactiveovernight() {
		return inactiveovernight;
	}

	public void setInactiveovernight(boolean inactiveovernight) {
		this.inactiveovernight = inactiveovernight;
	}

	@ElementCollection
	@OneToMany(mappedBy = "studentGroup", cascade = CascadeType.ALL)
	public List<NdisCommittedEvent> getNdisCommittedEvents() {
		return ndisCommittedEvents;
	}

	public void setNdisCommittedEvents(List<NdisCommittedEvent> ndisCommittedEvents) {
		this.ndisCommittedEvents = ndisCommittedEvents;
	}

	@Transient
	public List<GroupedStudent> getAvailableStudents() {
		return availableStudents;
	}

	public void setAvailableStudents(List<GroupedStudent> availableStudents) {
		this.availableStudents = availableStudents;
	}
	@Transient
	public String getcAmount() {
		return cAmount;
	}

	public void setcAmount(String cAmount) {
		this.cAmount = cAmount;
	}

}