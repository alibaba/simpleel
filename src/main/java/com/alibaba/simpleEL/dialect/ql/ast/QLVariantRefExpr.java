package com.alibaba.simpleEL.dialect.ql.ast;

public class QLVariantRefExpr extends QLExpr {
	private String name;

	public QLVariantRefExpr() {

	}

	public QLVariantRefExpr(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
