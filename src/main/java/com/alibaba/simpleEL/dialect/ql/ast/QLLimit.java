package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLLimit extends QLAstNode {
	private QLExpr offset;
	private QLExpr limit;
	
	public QLLimit() {
		
	}
	
	public QLLimit(QLExpr offset, QLExpr limit) {
		this.offset = offset;
		this.limit = limit;
	}

	public QLExpr getOffset() {
		return offset;
	}

	public void setOffset(QLExpr offset) {
		this.offset = offset;
	}

	public QLExpr getLimit() {
		return limit;
	}

	public void setLimit(QLExpr limit) {
		this.limit = limit;
	}

	@Override
	protected void accept0(QLAstVisitor visitor) {
		
	}

	@Override
	public void output(StringBuffer buf) {
		
	}

}
