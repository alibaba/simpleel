package com.alibaba.simpleEL.dialect.tiny.ast.stmt;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELIfStatement extends TinyELStatement {
	private final List<TinyELStatement> statementList = new ArrayList<TinyELStatement>();

	public TinyELIfStatement() {

	}

	public List<TinyELStatement> getStatementList() {
		return this.statementList;
	}
	
	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		
	}

	@Override
	public void output(StringBuffer buf) {
		
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
