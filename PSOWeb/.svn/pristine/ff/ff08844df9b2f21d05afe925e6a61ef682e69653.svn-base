/**
 * 
 */
package com.itelasoft.pso.beans;

public enum EnumItemCategory {
	STAFF_SKILL("STAFF_SKILL") {
		public String toString() {
			return "Staff Skill";
		}
	},
	RELATIONSHIP("RELATIONSHIP") {
		public String toString() {
			return "Relationship";
		}
	},
	GST_CODE("GST_CODE") {
		public String toString() {
			return "GSTCode";
		}
	};

	private String id;

	private EnumItemCategory(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return EnumItemCategory.valueOf(id).toString();
	}

}
