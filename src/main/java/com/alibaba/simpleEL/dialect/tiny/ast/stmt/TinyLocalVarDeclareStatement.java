package com.alibaba.simpleEL.dialect.tiny.ast.stmt;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyLocalVarDeclareStatement extends TinyELStatement {
	private String type;

	private final List<TinyELVariantDeclareItem> variants = new ArrayList<TinyELVariantDeclareItem>();

	public TinyLocalVarDeclareStatement() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<TinyELVariantDeclareItem> getVariants() {
		return variants;
	}

	@Override
	protected void accept0(TinyELAstVisitor visitor) {
		visitor.visit(this);
		visitor.endVisit(this);
	}
}
