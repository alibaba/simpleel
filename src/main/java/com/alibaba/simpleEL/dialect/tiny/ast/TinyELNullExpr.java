package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELNullExpr extends TinyELExpr {
    public void output(StringBuffer buf) {
        buf.append("NULL");
    }

    protected void accept0(TinyELAstVisitor visitor) {
        visitor.visit(this);

        visitor.endVisit(this);
    }
}
