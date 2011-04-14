package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLCharExpr extends QLLiteralExpr {
	private String value;

	public QLCharExpr() {

	}

	public QLCharExpr(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

    @Override
    public void output(StringBuffer buf) {
        if ((this.value == null) || (this.value.length() == 0)) {
            buf.append("NULL");
        } else {
            buf.append("'");
            buf.append(this.value.replaceAll("'", "''"));
            buf.append("'");
        }
    }

    protected void accept0(QLAstVisitor visitor) {
        visitor.visit(this);
        visitor.endVisit(this);
    }
}
