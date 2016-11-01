package com.itelasoft.pso.beans;

public enum EnumEmploymentType {

	FULLTIME("Full-Time") {
		public String toString() {
			return "Full-Time";
		}
	},
	PARTTIME("Part-Time") {
		public String toString() {
			return "Part-Time";
		}
	},
	CASUAL("Casual") {
		public String toString() {
			return "Casual";
		}
	};

	private String id;

	private EnumEmploymentType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
