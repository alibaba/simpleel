package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;


public class TinyELNumberLiteralExpr extends TinyELLiteralExpr {
	private Number value;
	
	public TinyELNumberLiteralExpr() {
		
	}
	
	public TinyELNumberLiteralExpr(Number value) {
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

    protected void accept0(TinyELAstVisitor visitor) {
        visitor.visit(this);

        visitor.endVisit(this);
    }
}
