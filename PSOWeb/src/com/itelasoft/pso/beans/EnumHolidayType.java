package com.itelasoft.pso.beans;

public enum EnumHolidayType {

	PUBLIC("Public") {
		public String toString() {
			return "Public";
		}
	},
	NON_PROGRAM("Non-Program Day") {
		public String toString() {
			return "Non-Program Day";
		}
	},
	VACATION("Vacation") {
		public String toString() {
			return "Vacation";
		}
	};

	private String id;

	private EnumHolidayType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
