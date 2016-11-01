/**
 * 
 */
package com.itelasoft.pso.beans;

/**
 * @author vajira
 * 
 */
public enum EnumFileType {
	SPECIALNEED_ICON("SPECIAL_NEED_ICON") {
		public String toString() {
			return "SpecialNeed Icon";
		}
	},
	VEHICLE_IMAGE("VEHICLE_IMAGE") {
		public String toString() {
			return "Vehicle Image";
		}
	},

	STUDENT_IMAGE("STUDENT_IMAGE") {
		public String toString() {
			return "Student Image";
		}
	},

	STAFF_IMAGE("STAFF_IMAGE") {
		public String toString() {
			return "Staff Image";
		}
	},
	
	STUDENT_GROUP_IMAGE("STUDENT_GROUP_IMAGE") {
		public String toString() {
			return "Student Group Image";
		}
	};

	private String id;

	private EnumFileType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
