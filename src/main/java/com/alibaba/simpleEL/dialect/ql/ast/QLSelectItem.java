package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLSelectItem extends QLAstNode {
	private QLExpr expr;
	private String alias;
	
	public QLSelectItem() {
	}

	public QLSelectItem(QLExpr expr, String alias) {
		this.expr = expr;
		this.alias = alias;
	}

	public QLExpr getExpr() {
		return expr;
	}

	public void setExpr(QLExpr expr) {
		this.expr = expr;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	protected void accept0(QLAstVisitor visitor) {
	    if(visitor.visit(this)) {
	        acceptChild(visitor, expr);
	    }
	}

	@Override
	public void output(StringBuffer buf) {
	    
	}

}
