package com.itelasoft.pso.beans;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Table(name = "StudentGuardiansXRef")
public class Guardian {

	@EmbeddedId
	private GuardianId guardianId = new GuardianId();

	@SuppressWarnings("unused")
	@ManyToOne
	@JoinColumn(name = "studentid", insertable = false, updatable = false)
	private Student student;

	@SuppressWarnings("unused")
	@ManyToOne
	@JoinColumn(name = "contactid", insertable = false, updatable = false)
	@IndexedEmbedded
	private Contact contact;

	@ManyToOne
	@JoinColumn(name = "relationId")
	@IndexedEmbedded
	private ReferenceItem relationship;

	public Guardian() {

	}

	public Guardian(Student student, Contact contact) {
		setGuardianId(new GuardianId(student, contact));
	}

	public void setGuardianId(GuardianId guardianId) {
		this.guardianId = guardianId;
	}

	public GuardianId getGuardianId() {
		return guardianId;
	}

	public Student getStudent() {
		return guardianId.getStudent();
	}

	public void setStudent(Student student) {
		this.guardianId.setStudent(student);
	}

	public Contact getContact() {
		return guardianId.getContact();
	}

	public void setContact(Contact contact) {
		this.guardianId.setContact(contact);
	}

	public ReferenceItem getRelationship() {
		return relationship;
	}

	public void setRelationship(ReferenceItem relationship) {
		this.relationship = relationship;
	}

	@Embeddable
	public static class GuardianId implements Serializable {

		private static final long serialVersionUID = 1L;

		@ManyToOne
		@JoinColumn(name = "studentid")
		private Student student;

		@ManyToOne
		@JoinColumn(name = "contactid")
		private Contact contact;

		// required no arg constructor
		public GuardianId() {

		}

		public GuardianId(Student student, Contact contact) {
			this.student = student;
			this.contact = contact;
		}

		public Student getStudent() {
			return student;
		}

		public void setStudent(Student student) {
			this.student = student;
		}

		public Contact getContact() {
			return contact;
		}

		public void setContact(Contact contact) {
			this.contact = contact;
		}

		@Override
		public boolean equals(Object instance) {
			if (instance == null)
				return false;

			if (!(instance instanceof GuardianId))
				return false;

			final GuardianId other = (GuardianId) instance;
			if (!(student.getId().equals(other.getStudent().getId())))
				return false;

			if (!(contact.getId().equals(other.getContact().getId())))
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 47 * hash
					+ (this.student != null ? this.student.hashCode() : 0);
			hash = 47 * hash
					+ (this.contact != null ? this.contact.hashCode() : 0);
			return hash;
		}

	}

}
