package com.alibaba.simpleEL.dialect.tiny.ast.stmt;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELIfStatement extends TinyELStatement {
	private TinyELExpr condition;
	private final List<TinyELStatement> statementList = new ArrayList<TinyELStatement>();
	private final List<ElseIf> elseIfList = new ArrayList<ElseIf>();
	private Else elseItem;

	public TinyELIfStatement() {

	}

	public TinyELExpr getCondition() {
		return condition;
	}

	public void setCondition(TinyELExpr condition) {
		this.condition = condition;
	}

	public Else getElse() {
		return this.elseItem;
	}

	public void setElse(Else value) {
		this.elseItem = value;
	}

	public List<ElseIf> getElseIfList() {
		return this.elseIfList;
	}

	public List<TinyELStatement> getStatementList() {
		return this.statementList;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {

	}

	@Override
	public void output(StringBuffer buf) {
		buf.append("if(");
		this.condition.output(buf);
		buf.append(") {\n");
		buf.append("\n}");
	}

	public static class ElseIf {
		private final List<TinyELStatement> statementList = new ArrayList<TinyELStatement>();

		public ElseIf() {

		}

		public List<TinyELStatement> getStatementList() {
			return this.statementList;
		}
	}

	public static class Else {
		private final List<TinyELStatement> statementList = new ArrayList<TinyELStatement>();

		public Else() {

		}

		public List<TinyELStatement> getStatementList() {
			return this.statementList;
		}
	}

}
