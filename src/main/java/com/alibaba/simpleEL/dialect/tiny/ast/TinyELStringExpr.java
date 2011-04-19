package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELStringExpr extends TinyELLiteralExpr {
	private String value;

	public TinyELStringExpr() {

	}

	public TinyELStringExpr(String value) {
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
            buf.append("null");
        } else {
            buf.append('"');
            buf.append(this.value);
            buf.append('"');
        }
    }

    protected void accept0(TinyELAstVisitor visitor) {
        visitor.visit(this);
        visitor.endVisit(this);
    }
}
