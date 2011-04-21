package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyUnaryOpExpr extends TinyELExpr {
	private TinyELExpr expr;
	private TinyUnaryOperator operator;

	public TinyUnaryOpExpr() {

	}

	public TinyUnaryOpExpr(TinyELExpr expr, TinyUnaryOperator operator) {
		super();
		this.expr = expr;
		this.operator = operator;
	}

	public TinyELExpr getExpr() {
		return expr;
	}

	public void setExpr(TinyELExpr expr) {
		this.expr = expr;
	}

	public TinyUnaryOperator getOperator() {
		return operator;
	}

	public void setOperator(TinyUnaryOperator operator) {
		this.operator = operator;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		if (visitor.visit(this)) {
			acceptChild(visitor, expr);
		}
		visitor.endVisit(this);
	}

}
