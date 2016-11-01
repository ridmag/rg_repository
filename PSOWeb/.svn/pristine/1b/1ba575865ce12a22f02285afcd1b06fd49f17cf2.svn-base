package com.itelasoft.pso.beans;

public enum EnumSkillLevel {
	QUALIFIED_PROBATIONARY("QUALIFIED_PROBATIONARY") {
		public String toString() {
			return "Qualified - Probationary";
		}
	},

	QUALIFIED_INDEPENDENT("QUALIFIED_INDEPENDENT") {
		public String toString() {
			return "Qualified - Independent";
		}
	};

	private String id;
	private String code;

	private EnumSkillLevel(String id) {
		this.id = id;
		if (id.equals("QUALIFIED_PROBATIONARY"))
			code = "QP";
		if (id.equals("QUALIFIED_INDEPENDENT"))
			code = "QI";
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return EnumSkillLevel.valueOf(id).toString();
	}

	public String getCode() {
		return code;
	}

}
