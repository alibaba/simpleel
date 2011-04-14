package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public abstract class QLExpr extends QLAstNode {

	@Override
	protected void accept0(QLAstVisitor visitor) {
		
	}

    public String toString() {
        StringBuffer buf = new StringBuffer();
        output(buf);
        return buf.toString();
    }
}
