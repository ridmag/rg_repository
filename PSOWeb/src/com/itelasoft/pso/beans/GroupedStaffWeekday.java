package com.itelasoft.pso.beans;

import java.util.Date;

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
@Table(name = "groupedstaffweekdays")
public class GroupedStaffWeekday extends BaseEntity{
	private Long id;
	private GroupedStaff groupedstaff;
	private WeekDay weekday;
	private Date startTime;
	private Date endTime;
	private Integer lunch;
	private TimeBean groupStaffStartTime;
	private TimeBean groupStaffEndTime;
	
	@Id
	@GenericGenerator(name = "mygen", strategy = "increment")
	@GeneratedValue(generator = "mygen")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name = "groupedstaffid")
	public GroupedStaff getGroupedstaff() {
		return groupedstaff;
	}
	public void setGroupedstaff(GroupedStaff groupedstaff) {
		this.groupedstaff = groupedstaff;
	}
	@ManyToOne
	@JoinColumn(name = "weekdayid")
	public WeekDay getWeekday() {
		return weekday;
	}
	public void setWeekday(WeekDay weekday) {
		this.weekday = weekday;
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
	public Integer getLunch() {
		return lunch;
	}
	public void setLunch(Integer lunch) {
		this.lunch = lunch;
	}
	@Transient
	public TimeBean getGroupStaffStartTime() {
		return groupStaffStartTime;
	}
	public void setGroupStaffStartTime(TimeBean groupStaffStartTime) {
		this.groupStaffStartTime = groupStaffStartTime;
	}
	@Transient
	public TimeBean getGroupStaffEndTime() {
		return groupStaffEndTime;
	}
	public void setGroupStaffEndTime(TimeBean groupStaffEndTime) {
		this.groupStaffEndTime = groupStaffEndTime;
	}
	
	

}
