package com.alibaba.simpleEL.dialect.ql.ast;

import java.io.Serializable;

public class QLBetweenExpr extends QLExpr implements Serializable {
    private static final long serialVersionUID = 1L;
    public QLExpr testExpr;
    private boolean not;
    public QLExpr beginExpr;
    public QLExpr endExpr;

    public QLBetweenExpr() {

    }

    public QLBetweenExpr(QLExpr testExpr, QLExpr beginExpr, QLExpr endExpr) {
        this.testExpr = testExpr;
        this.beginExpr = beginExpr;
        this.endExpr = endExpr;
    }

    public QLBetweenExpr(QLExpr testExpr, boolean not, QLExpr beginExpr, QLExpr endExpr) {
        this.testExpr = testExpr;
        this.not = not;
        this.beginExpr = beginExpr;
        this.endExpr = endExpr;
    }

    public QLExpr getTestExpr() {
        return this.testExpr;
    }

    public void setTestExpr(QLExpr testExpr) {
        this.testExpr = testExpr;
    }

    public boolean isNot() {
        return this.not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public QLExpr getBeginExpr() {
        return this.beginExpr;
    }

    public void setBeginExpr(QLExpr beginExpr) {
        this.beginExpr = beginExpr;
    }

    public QLExpr getEndExpr() {
        return this.endExpr;
    }

    public void setEndExpr(QLExpr endExpr) {
        this.endExpr = endExpr;
    }
}
