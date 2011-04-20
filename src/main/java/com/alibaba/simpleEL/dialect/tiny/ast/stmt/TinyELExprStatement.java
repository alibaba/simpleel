package com.alibaba.simpleEL.dialect.tiny.ast.stmt;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELExprStatement extends TinyELStatement {
	private TinyELExpr expr;

	public TinyELExprStatement() {

	}

	public TinyELExprStatement(TinyELExpr expr) {
		this.expr = expr;
	}

	public TinyELExpr getExpr() {
		return expr;
	}

	public void setExpr(TinyELExpr expr) {
		this.expr = expr;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		if (visitor.visit(this)) {
			acceptChild(visitor, expr);
		}
		visitor.endVisit(this);
	}

}
