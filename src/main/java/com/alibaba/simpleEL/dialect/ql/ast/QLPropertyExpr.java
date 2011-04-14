package com.alibaba.simpleEL.dialect.ql.ast;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;


public class QLPropertyExpr extends QLExpr implements QLName {
    private static final long serialVersionUID = 1L;

    private QLExpr owner;
    private String name;

    public QLPropertyExpr(QLExpr owner, String name) {

        this.owner = owner;
        this.name = name;
    }

    public QLPropertyExpr() {

    }

    public QLExpr getOwner() {
        return this.owner;
    }

    public void setOwner(QLExpr owner) {
        this.owner = owner;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void output(StringBuffer buf) {
        this.owner.output(buf);
        buf.append(".");
        buf.append(this.name);
    }

    protected void accept0(QLAstVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.owner);
        }

        visitor.endVisit(this);
    }
}
