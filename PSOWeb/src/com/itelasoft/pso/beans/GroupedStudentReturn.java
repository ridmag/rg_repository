package com.itelasoft.pso.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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


public class GroupedStudentReturn extends BaseEntity {
	private GroupedStudent mainStd;
	private GroupedStudent  returnStd;
	public GroupedStudent getMainStd() {
		return mainStd;
	}
	public void setMainStd(GroupedStudent mainStd) {
		this.mainStd = mainStd;
	}
	public GroupedStudent getReturnStd() {
		return returnStd;
	}
	public void setReturnStd(GroupedStudent returnStd) {
		this.returnStd = returnStd;
	}
	
	

	
	
	
	
	
}

