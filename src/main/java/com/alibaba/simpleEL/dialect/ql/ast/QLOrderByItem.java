package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLOrderByItem extends QLAstNode {
	private QLExpr expr;
	private OrderByMode mode = OrderByMode.ASC;

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

	public OrderByMode getMode() {
		return mode;
	}

	public void setMode(OrderByMode mode) {
		this.mode = mode;
	}

	@Override
	protected void accept0(QLAstVisitor visitor) {
		if (visitor.visit(this)) {
			acceptChild(visitor, this.expr);
		}

		visitor.endVisit(this);
	}

	@Override
	public void output(StringBuffer buf) {
		expr.output(buf);
		if (mode != null) {
			buf.append(mode.name());
		}
	}
}
