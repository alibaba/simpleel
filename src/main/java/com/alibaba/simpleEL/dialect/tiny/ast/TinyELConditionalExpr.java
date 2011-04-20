package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELConditionalExpr extends TinyELExpr {
	private TinyELExpr condition;
	private TinyELExpr trueExpr;
	private TinyELExpr falseExpr;

	public TinyELConditionalExpr() {

	}

	public TinyELConditionalExpr(TinyELExpr condition, TinyELExpr trueExpr, TinyELExpr falseExpr) {
		super();
		this.condition = condition;
		this.trueExpr = trueExpr;
		this.falseExpr = falseExpr;
	}

	public TinyELExpr getCondition() {
		return condition;
	}

	public void setCondition(TinyELExpr condition) {
		this.condition = condition;
	}

	public TinyELExpr getTrueExpr() {
		return trueExpr;
	}

	public void setTrueExpr(TinyELExpr trueExpr) {
		this.trueExpr = trueExpr;
	}

	public TinyELExpr getFalseExpr() {
		return falseExpr;
	}

	public void setFalseExpr(TinyELExpr falseExpr) {
		this.falseExpr = falseExpr;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		if (visitor.visit(this)) {
			acceptChild(visitor, condition);
			acceptChild(visitor, trueExpr);
			acceptChild(visitor, falseExpr);
		}
		visitor.endVisit(this);
	}

}
