package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLNumberLiteralExpr extends QLLiteralExpr {
	private Number value;
	
	public QLNumberLiteralExpr() {
		
	}
	
	public QLNumberLiteralExpr(Number value) {
		this.value = value;
	}
	
	public Number getValue() {
		return value;
	}
	
    public void output(StringBuffer buf) {
    	if (value == null) {
    		buf.append("NULL");
    	} else {
    		buf.append(value.toString());
    	}
    }

    protected void accept0(QLAstVisitor visitor) {
        visitor.visit(this);

        visitor.endVisit(this);
    }
}
