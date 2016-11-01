package com.itelasoft.pso.beans;

public enum EnumNdisTime {
     
	PROGRAM("Program") {
		public String toString() {
			return "Program";
		}
	},
	TRANSPORT("Transport") {
		public String toString() {
			return "Transport";
		}
	},
	ANCILLARY("Ancillary") {
		public String toString() {
			return "Ancillary";
		}
	};

	private String id;

	private EnumNdisTime(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
