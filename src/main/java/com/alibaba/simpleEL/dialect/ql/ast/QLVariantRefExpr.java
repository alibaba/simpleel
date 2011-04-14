package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLVariantRefExpr extends QLExpr {
	private String name;

	public QLVariantRefExpr() {

	}

	public QLVariantRefExpr(String name) {
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
    protected void accept0(QLAstVisitor visitor) {
        visitor.visit(this);

        visitor.endVisit(this);
    }
}
