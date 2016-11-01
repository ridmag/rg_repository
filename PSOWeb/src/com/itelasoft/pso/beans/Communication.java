package com.itelasoft.pso.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "Communications")
public class Communication extends BaseEntity {

	private Long id;
	private String method;
	private Date createdDate;
	private Date createdTime;
	private User createdBy;
	private StaffMember keyPersonStaff;
	private Student keyPersonStudent;
	private ExternalOrganization keyPersonOrg;
	private Vehicle keyPersonVehicle;
	private FundingSource keyPersonFunding;
	private String summary;
	private CommunicationCat category;
	private List<CommunicationNote> notes;
	private Reminder reminder;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date eventDate) {
		this.createdDate = eventDate;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date eventTime) {
		this.createdTime = eventTime;
	}

	@ManyToOne
	@JoinColumn(name = "keyPersonStaff")
	public StaffMember getKeyPersonStaff() {
		return keyPersonStaff;
	}

	public void setKeyPersonStaff(StaffMember keyPersonStaff) {
		this.keyPersonStaff = keyPersonStaff;
	}

	@ManyToOne
	@JoinColumn(name = "category")
	public CommunicationCat getCategory() {
		return category;
	}

	public void setCategory(CommunicationCat category) {
		this.category = category;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSummary() {
		return summary;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@ManyToOne
	@JoinColumn(name = "createdBy")
	public User getCreatedBy() {
		return createdBy;
	}

	@ManyToOne
	@JoinColumn(name = "keyPersonStudent")
	public Student getKeyPersonStudent() {
		return keyPersonStudent;
	}

	public void setKeyPersonStudent(Student keyPersonStudent) {
		this.keyPersonStudent = keyPersonStudent;
	}

	@ManyToOne
	@JoinColumn(name = "keyPersonOrg")
	public ExternalOrganization getKeyPersonOrg() {
		return keyPersonOrg;
	}

	public void setKeyPersonOrg(ExternalOrganization keyPersonOrg) {
		this.keyPersonOrg = keyPersonOrg;
	}

	public void setNotes(List<CommunicationNote> notes) {
		this.notes = notes;
	}

	@OneToMany(mappedBy = "communication")
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@Cascade(value = { org.hibernate.annotations.CascadeType.DELETE_ORPHAN,
			org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	public List<CommunicationNote> getNotes() {
		return notes;
	}

	public void setKeyPersonVehicle(Vehicle keyPersonVehicle) {
		this.keyPersonVehicle = keyPersonVehicle;
	}

	@ManyToOne
	@JoinColumn(name = "keyPersonVehicle")
	public Vehicle getKeyPersonVehicle() {
		return keyPersonVehicle;
	}

	public void setKeyPersonFunding(FundingSource keyPersonFunding) {
		this.keyPersonFunding = keyPersonFunding;
	}

	@ManyToOne
	@JoinColumn(name = "keyPersonFunding")
	public FundingSource getKeyPersonFunding() {
		return keyPersonFunding;
	}

	@ManyToOne
	@Cascade(value = { org.hibernate.annotations.CascadeType.DELETE_ORPHAN,
			org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "reminderId")
	public Reminder getReminder() {
		return reminder;
	}

	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}

	@Transient
	public String getFollowUps() {
		if (notes == null || notes.isEmpty())
			return "No follow-ups found.";
		String message = "";
		for (CommunicationNote note : notes) {
			if (note.getNotify() != null && !note.getNotify().isEmpty())
				message += note.getNotify() + "\n";
		}
		return message;
	}

}
