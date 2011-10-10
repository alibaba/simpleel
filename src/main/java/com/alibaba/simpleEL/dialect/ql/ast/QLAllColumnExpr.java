package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;


public class QLAllColumnExpr extends QLExpr {

    public QLAllColumnExpr() {

    }

    public void output(StringBuffer buf) {
        buf.append("*");
    }

    protected void accept0(QLAstVisitor visitor) {
        visitor.visit(this);
        visitor.endVisit(this);
    }
}
