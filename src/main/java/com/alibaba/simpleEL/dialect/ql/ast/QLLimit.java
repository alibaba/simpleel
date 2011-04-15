package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLLimit extends QLAstNode {
	private QLExpr offset;
	private QLExpr rowCount;

	public QLLimit() {

	}

	public QLLimit(QLExpr offset, QLExpr rowCount) {
		this.offset = offset;
		this.rowCount = rowCount;
	}

	public QLExpr getOffset() {
		return offset;
	}

	public void setOffset(QLExpr offset) {
		this.offset = offset;
	}

	public QLExpr getRowCount() {
		return rowCount;
	}

	public void setRowCount(QLExpr rowCount) {
		this.rowCount = rowCount;
	}

	@Override
	protected void accept0(QLAstVisitor visitor) {
		if (visitor.visit(this)) {
			acceptChild(visitor, offset);
			acceptChild(visitor, rowCount);
		}
		
		visitor.endVisit(this);
	}

	@Override
	public void output(StringBuffer buf) {
		buf.append("LIMIT ");
		
		if (this.offset != null) {
			this.offset.output(buf);
			buf.append(", ");
		}

		this.rowCount.output(buf);
	}

}
