package com.itelasoft.pso.beans;

public enum EnumPaymentType {
	EVENT("Event") {
		@Override
		public String toString() {
			return "Event";
		}
	},
	PERSONAL("Personal") {
		@Override
		public String toString() {
			return "Personal";
		}
	},
	COLLECTION("Collection") {
		@Override
		public String toString() {
			return "Collection";
		}
	},
	OUTSTANDING("Outstanding") {
		@Override
		public String toString() {
			return "Outstanding";
		}
	};
	
	

	private String id;

	private EnumPaymentType(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}