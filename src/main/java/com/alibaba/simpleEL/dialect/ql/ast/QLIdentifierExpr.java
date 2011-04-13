package com.alibaba.simpleEL.dialect.ql.ast;

public class QLIdentifierExpr extends QLExpr {
	private String name;
	
	public QLIdentifierExpr() {
	}
	
	public QLIdentifierExpr(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
