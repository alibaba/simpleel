package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLNullExpr extends QLExpr {
    public void output(StringBuffer buf) {
        buf.append("NULL");
    }

    protected void accept0(QLAstVisitor visitor) {
        visitor.visit(this);

        visitor.endVisit(this);
    }
}
