package com.alibaba.simpleEL.dialect.tiny.ast.stmt;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELForEachStatement extends TinyELStatement {
	private String type;
	private String variant;
	private TinyELExpr targetExpr;
	private final List<TinyELStatement> statementList = new ArrayList<TinyELStatement>();

	public TinyELForEachStatement() {

	}

	public TinyELForEachStatement(String type, String variant, TinyELExpr targetExpr) {
		this.type = type;
		this.variant = variant;
		this.targetExpr = targetExpr;
	}

	public String getVariant() {
		return variant;
	}

	public List<TinyELStatement> getStatementList() {
		return statementList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public TinyELExpr getTargetExpr() {
		return targetExpr;
	}

	public void setTargetExpr(TinyELExpr targetExpr) {
		this.targetExpr = targetExpr;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		if (visitor.visit(this)) {
			acceptChild(visitor, targetExpr);
		}
		visitor.endVisit(this);
	}

}
