package com.alibaba.simpleEL.dialect.ql.ast;

public class QLCharExpr extends QLLiteralExpr {
	private String value;

	public QLCharExpr() {

	}

	public QLCharExpr(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
