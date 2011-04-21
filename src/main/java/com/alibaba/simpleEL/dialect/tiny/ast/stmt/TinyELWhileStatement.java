package com.alibaba.simpleEL.dialect.tiny.ast.stmt;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELWhileStatement extends TinyELStatement {
	private final List<TinyELStatement> statementList = new ArrayList<TinyELStatement>();
	private TinyELExpr condition;
	
	public TinyELWhileStatement() {
		
	}

	public TinyELExpr getCondition() {
		return condition;
	}

	public void setCondition(TinyELExpr condition) {
		this.condition = condition;
	}

	public List<TinyELStatement> getStatementList() {
		return statementList;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		if (visitor.visit(this)) {
			acceptChild(visitor, condition);
			acceptChild(visitor, statementList);
		}
		visitor.endVisit(this);
	}

}
