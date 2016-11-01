package com.itelasoft.pso.beans;

public enum EnumReminderType {

	COMMUNICATION("COMMUNICATION") {
		public String toString() {
			return "Communication";
		}
	};

	private String id;

	private EnumReminderType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
