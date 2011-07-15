package com.alibaba.simpleEL.dialect.tiny.ast;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELOutputVisitor;

public abstract class TinyELExpr extends TinyELAstNode {

    private TinyELExpr parentExpr;

    public TinyELExpr getParentExpr() {
        return parentExpr;
    }

    public void setParentExpr(TinyELExpr parentExpr) {
        this.parentExpr = parentExpr;
    }

    @Override
    public void output(StringBuffer buf) {
        StringWriter out = new StringWriter();
        TinyELOutputVisitor visitor = new TinyELOutputVisitor(new PrintWriter(out));
        this.accept(visitor);
        buf.append(out.toString());
    }
}
