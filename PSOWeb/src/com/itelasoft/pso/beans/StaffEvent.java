package com.itelasoft.pso.beans;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.itelasoft.pso.web.TimeBean;

@Entity
@Table(name = "ProgramEventCoordinatorXRef")
public class StaffEvent extends BaseEntity {

	private Long id;
	private ProgramEvent programEvent;
	private StaffMember staffMember;
	private boolean attended;
	private Date startTime;
	private Date endTime;
	private int lunchMinutes;
	private Date rosterStartTime;
	private Date rosterEndTime;
	
	private TimeBean startTimeBean, endTimeBean,rosterStatTimeBean,rosterEndTimeBean;

	// Roster related transient variables
	private String staffStatus;
	private String staffColor;
	private String staffSkills;
	private String conflictingEvents;
	private boolean isHolidayEvent;
	private GroupedStaff groupStaff;

	public StaffEvent() {
		
	}

	@Id
	@GenericGenerator(name = "mygen", strategy = "increment")
	@GeneratedValue(generator = "mygen")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "programEventId", insertable = true, nullable = false, updatable = true)
	public ProgramEvent getProgramEvent() {
		return programEvent;
	}

	public void setProgramEvent(ProgramEvent programEvent) {
		this.programEvent = programEvent;
	}

	@ManyToOne
	@JoinColumn(name = "coordinatorId")
	public StaffMember getStaffMember() {
		return staffMember;
	}

	public void setStaffMember(StaffMember staffMember) {
		this.staffMember = staffMember;
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

	public void setLunchMinutes(int lunchMinutes) {
		this.lunchMinutes = lunchMinutes;
	}

	public int getLunchMinutes() {
		return lunchMinutes;
	}

	public void setStaffStatus(String staffStatus) {
		this.staffStatus = staffStatus;
	}

	@Transient
	public String getStaffStatus() {
		return staffStatus;
	}

	public void setStaffColor(String staffColor) {
		this.staffColor = staffColor;
	}

	@Transient
	public String getStaffColor() {
		return staffColor;
	}

	public void setConflictingEvents(String conflictingEvents) {
		this.conflictingEvents = conflictingEvents;
	}

	@Transient
	public String getConflictingEvents() {
		return conflictingEvents;
	}

	public void setStaffSkills(String staffSkills) {
		this.staffSkills = staffSkills;
	}

	@Transient
	public String getStaffSkills() {
		return staffSkills;
	}

	public void setAttended(boolean attended) {
		this.attended = attended;
	}

	public boolean isAttended() {
		return attended;
	}

	@Transient
	public TimeBean getStartTimeBean() {
		if(startTimeBean==null){
			startTimeBean = new TimeBean(startTime);
		}
		return startTimeBean;
	}

	public void setStartTimeBean(TimeBean startTimeBean) {
		this.startTimeBean = startTimeBean;
	}

	@Transient
	public TimeBean getEndTimeBean() {
		if(endTimeBean == null){
			endTimeBean = new TimeBean(endTime);
		}
		return endTimeBean;
	}

	public void setEndTimeBean(TimeBean endTimeBean) {
		this.endTimeBean = endTimeBean;
	}

	@Transient
	public void setTimes() {
		startTime = startTimeBean.getDateTime(programEvent.getEventDate());
		endTime = endTimeBean.getDateTime(programEvent.getEventDate());
	}

	public Date getRosterStartTime() {
		return rosterStartTime;
	}

	public void setRosterStartTime(Date rosterStartTime) {
		this.rosterStartTime = rosterStartTime;
	}

	public Date getRosterEndTime() {
		return rosterEndTime;
	}

	public void setRosterEndTime(Date rosterEndTime) {
		this.rosterEndTime = rosterEndTime;
	}
	@Transient
	public TimeBean getRosterStatTimeBean() {
		if(rosterStatTimeBean== null){
			rosterStatTimeBean = new TimeBean(rosterStartTime);
		}
			return rosterStatTimeBean;
	}

	public void setRosterStatTimeBean(TimeBean rosterStatTimeBean) {
		this.rosterStatTimeBean = rosterStatTimeBean;
	}
	@Transient
	public TimeBean getRosterEndTimeBean() {
		if(rosterEndTimeBean == null){
		rosterEndTimeBean = new TimeBean(rosterEndTime);
		}
		return rosterEndTimeBean;
	}

	public void setRosterEndTimeBean(TimeBean rosterEndTimeBean) {
		this.rosterEndTimeBean = rosterEndTimeBean;
	}
	
	@Transient
	public boolean isHolidayEvent() {
		return isHolidayEvent;
	}

	public void setHolidayEvent(boolean isHolidayEvent) {
		this.isHolidayEvent = isHolidayEvent;
	}

	@Transient
	public GroupedStaff getGroupStaff() {
		return groupStaff;
	}

	public void setGroupStaff(GroupedStaff groupStaff) {
		this.groupStaff = groupStaff;
	}

}
