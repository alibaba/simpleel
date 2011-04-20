package com.alibaba.simpleEL.dialect.tiny.ast.stmt;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELAstNode;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELOutputVisitor;

public class TinyLocalVarDeclareStatement extends TinyELStatement {
	private String type;

	private final List<VariantDeclareItem> variants = new ArrayList<VariantDeclareItem>();

	public TinyLocalVarDeclareStatement() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<VariantDeclareItem> getVariants() {
		return variants;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		visitor.visit(this);
		visitor.endVisit(this);
	}

	public static class VariantDeclareItem extends TinyELAstNode {
		private String name;
		private TinyELExpr initValue;
		
		public VariantDeclareItem(String name) {
			this.name = name;
		}
		
		public VariantDeclareItem(String name, TinyELExpr initValue) {
			this.name = name;
			this.initValue = initValue;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public TinyELExpr getInitValue() {
			return initValue;
		}

		public void setInitValue(TinyELExpr initValue) {
			this.initValue = initValue;
		}

		@Override
		protected void accept0(TinyELAstVisitor visitor) {
			if (visitor.visit(this)) {
				acceptChild(visitor, initValue);
			}
			visitor.endVisit(this);
		}

		@Override
		public void output(StringBuffer buf) {
			StringWriter out = new StringWriter();
			TinyELOutputVisitor visitor = new TinyELOutputVisitor(new PrintWriter(out));
			this.accept(visitor);
			buf.append(out.toString());
		}

	}
}
