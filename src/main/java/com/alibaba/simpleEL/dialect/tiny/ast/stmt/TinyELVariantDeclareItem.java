package com.alibaba.simpleEL.dialect.tiny.ast.stmt;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELAstNode;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELOutputVisitor;

public class TinyELVariantDeclareItem extends TinyELAstNode {
	private String name;
	private TinyELExpr initValue;
	
	public TinyELVariantDeclareItem(String name) {
		this.name = name;
	}
	
	public TinyELVariantDeclareItem(String name, TinyELExpr initValue) {
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