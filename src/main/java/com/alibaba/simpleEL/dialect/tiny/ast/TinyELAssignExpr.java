package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELAssignExpr extends TinyELExpr {
	private TinyELExpr target;
	private TinyELExpr value;

	public TinyELAssignExpr() {
	}

	public TinyELExpr getTarget() {
		return target;
	}

	public void setTarget(TinyELExpr target) {
		this.target = target;
	}

	public TinyELExpr getValue() {
		return value;
	}

	public void setValue(TinyELExpr value) {
		this.value = value;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		if (visitor.visit(this)) {
			acceptChild(visitor, target);
			acceptChild(visitor, value);
		}
		visitor.endVisit(this);
	}

}
