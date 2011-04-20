package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELBooleanExpr extends TinyELExpr {
	private boolean value;

	public TinyELBooleanExpr(boolean value) {
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		visitor.visit(this);
		visitor.endVisit(this);
	}

	@Override
	void output(StringBuffer buf) {
		buf.append(value ? "true" : "false");
	}

}
