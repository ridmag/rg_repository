package com.itelasoft.pso.services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.itelasoft.pso.beans.EnumEmploymentType;
import com.itelasoft.pso.beans.EnumLeaveType;
import com.itelasoft.pso.beans.StaffMember;

public class BusinessRulesService implements IBusinessRulesService {

	private int fullTimeLunchMinutes = 24;
	private int partTimeLunchMinutes = 24;
	private int casualTimeLunchMinutes = 24;
	private int fullTimeRegularRosterMinutes = 360;
	private int partTimeRegularRosterMinutes = 360;
	private int casualTimeRegularRosterMinutes = 0;
	private double sickLeavePercentage = 3.8462;
	private double annualLeavePercentage = 7.6923;

	private Date actStmtStartDate;

	public BusinessRulesService() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(2014, 00, 01);
		actStmtStartDate = calendar.getTime();
	}

	public int getLunchTimeMinutes(StaffMember staffMember) {
		if (staffMember.getEmploymentType().equals(EnumEmploymentType.FULLTIME))
			return fullTimeLunchMinutes;
		if (staffMember.getEmploymentType().equals(EnumEmploymentType.PARTTIME))
			return partTimeLunchMinutes;
		if (staffMember.getEmploymentType().equals(EnumEmploymentType.CASUAL))
			return casualTimeLunchMinutes;
		return 0;
	}

	public int getRegularRosterMinutes(StaffMember staffMember) {
		if (staffMember.getEmploymentType().equals(EnumEmploymentType.FULLTIME))
			return fullTimeRegularRosterMinutes;
		if (staffMember.getEmploymentType().equals(EnumEmploymentType.PARTTIME))
			return partTimeRegularRosterMinutes;
		if (staffMember.getEmploymentType().equals(EnumEmploymentType.CASUAL))
			return casualTimeRegularRosterMinutes;
		return 0;
	}

	public double getLeavePercentage(EnumLeaveType leaveType) {
		if (leaveType.equals(EnumLeaveType.ANNUAL))
			return annualLeavePercentage / 100;
		if (leaveType.equals(EnumLeaveType.PERSONAL))
			return sickLeavePercentage / 100;
		return 0;
	}

	public int getFullTimeLunchMinutes() {
		return fullTimeLunchMinutes;
	}

	public void setFullTimeLunchMinutes(int fullTimeLunchMinutes) {
		this.fullTimeLunchMinutes = fullTimeLunchMinutes;
	}

	public int getPartTimeLunchMinutes() {
		return partTimeLunchMinutes;
	}

	public void setPartTimeLunchMinutes(int partTimeLunchMinutes) {
		this.partTimeLunchMinutes = partTimeLunchMinutes;
	}

	public int getCasualTimeLunchMinutes() {
		return casualTimeLunchMinutes;
	}

	public void setCasualTimeLunchMinutes(int casualTimeLunchMinutes) {
		this.casualTimeLunchMinutes = casualTimeLunchMinutes;
	}

	public int getFullTimeRegularRosterMinutes() {
		return fullTimeRegularRosterMinutes;
	}

	public void setFullTimeRegularRosterMinutes(int fullTimeRegularRosterMinutes) {
		this.fullTimeRegularRosterMinutes = fullTimeRegularRosterMinutes;
	}

	public int getPartTimeRegularRosterMinutes() {
		return partTimeRegularRosterMinutes;
	}

	public void setPartTimeRegularRosterMinutes(int partTimeRegularRosterMinutes) {
		this.partTimeRegularRosterMinutes = partTimeRegularRosterMinutes;
	}

	public int getCasualTimeRegularRosterMinutes() {
		return casualTimeRegularRosterMinutes;
	}

	public void setCasualTimeRegularRosterMinutes(
			int casualTimeRegularRosterMinutes) {
		this.casualTimeRegularRosterMinutes = casualTimeRegularRosterMinutes;
	}

	public double getSickLeavePercentage() {
		return sickLeavePercentage;
	}

	public void setSickLeavePercentage(double sickLeavePercentage) {
		this.sickLeavePercentage = sickLeavePercentage;
	}

	public double getAnnualLeavePercentage() {
		return annualLeavePercentage;
	}

	public void setAnnualLeavePercentage(double annualLeavePercentage) {
		this.annualLeavePercentage = annualLeavePercentage;
	}

	public void setActStmtStartDate(Date actStmtStartDate) {
		this.actStmtStartDate = actStmtStartDate;
	}

	public Date getActStmtStartDate() {
		return actStmtStartDate;
	}
//
}
