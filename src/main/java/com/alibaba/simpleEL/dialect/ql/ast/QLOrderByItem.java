package com.alibaba.simpleEL.dialect.ql.ast;

public class QLOrderByItem {
	private QLExpr expr;
	
	public QLOrderByItem() {
		
	}
	
	public QLOrderByItem(QLExpr expr) {
		this.expr = expr;
	}
	
	public QLExpr getExpr() {
		return this.expr;
	}
	
	public void setExpr(QLExpr expr) {
		this.expr = expr;
	}
}
