package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELCharExpr extends TinyELLiteralExpr {
	private String value;

	public TinyELCharExpr() {

	}

	public TinyELCharExpr(String value) {
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

    protected void accept0(TinyELAstVisitor visitor) {
        visitor.visit(this);
        visitor.endVisit(this);
    }
}
