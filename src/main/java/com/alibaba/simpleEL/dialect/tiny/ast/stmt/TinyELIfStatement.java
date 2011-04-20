package com.alibaba.simpleEL.dialect.tiny.ast.stmt;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELAstNode;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELOutputVisitor;

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

	public static class ElseIf extends TinyELAstNode {
		private final List<TinyELStatement> statementList = new ArrayList<TinyELStatement>();

		public ElseIf() {

		}

		public List<TinyELStatement> getStatementList() {
			return this.statementList;
		}

		@Override
		protected void accept0(TinyELAstVisitor visitor) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void output(StringBuffer buf) {
			StringWriter out = new StringWriter();
			TinyELOutputVisitor visitor = new TinyELOutputVisitor(new PrintWriter(out));
			this.accept(visitor);
			buf.append(out.toString());
		}
	}

	public static class Else extends TinyELAstNode {
		private final List<TinyELStatement> statementList = new ArrayList<TinyELStatement>();

		public Else() {

		}

		public List<TinyELStatement> getStatementList() {
			return this.statementList;
		}
		
		@Override
		public void output(StringBuffer buf) {
			StringWriter out = new StringWriter();
			TinyELOutputVisitor visitor = new TinyELOutputVisitor(new PrintWriter(out));
			this.accept(visitor);
			buf.append(out.toString());
		}

		@Override
		protected void accept0(TinyELAstVisitor visitor) {
			if (visitor.visit(this)) {
				acceptChild(visitor, statementList);
			}
			visitor.endVisit(this);
		}
	}

}
