package com.alibaba.simpleEL.dialect.ql.ast;

public class QLNumberLiteralExpr extends QLLiteralExpr {
	private Number value;
	
	public QLNumberLiteralExpr() {
		
	}
	
	public QLNumberLiteralExpr(Number value) {
		this.value = value;
	}
	
	public Number getValue() {
		return value;
	}
}
