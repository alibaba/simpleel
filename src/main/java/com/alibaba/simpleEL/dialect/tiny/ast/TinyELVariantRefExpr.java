package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELVariantRefExpr extends TinyELExpr {
	private String name;

	public TinyELVariantRefExpr() {

	}

	public TinyELVariantRefExpr(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public void output(StringBuffer buf) {
        buf.append(this.name);
    }

    @Override
    protected void accept0(TinyELAstVisitor visitor) {
        visitor.visit(this);

        visitor.endVisit(this);
    }
}
