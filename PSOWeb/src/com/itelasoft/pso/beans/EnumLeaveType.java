package com.itelasoft.pso.beans;

public enum EnumLeaveType {

	PERSONAL("Personal") {
		public String toString() {
			return "Personal";
		}
	},
	SERVICE("Service") {
		public String toString() {
			return "Service";
		}
	},
	ANNUAL("Annual") {
		public String toString() {
			return "Annual";
		}
	},
	PARENTAL("Parental") {
		public String toString() {
			return "Parental";
		}
	},
	NOPAY("No Pay") {
		public String toString() {
			return "No Pay";
		}
	};

	private String id;

	private EnumLeaveType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
