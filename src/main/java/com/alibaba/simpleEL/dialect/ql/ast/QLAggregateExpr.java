package com.alibaba.simpleEL.dialect.ql.ast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLAggregateExpr extends QLExpr implements Serializable {
    private static final long serialVersionUID = 1L;
    protected QLIdentifierExpr methodName;
    protected int option;
    protected final List<QLExpr> arguments = new ArrayList<QLExpr>();

    public QLAggregateExpr(String methodName) {
        this.methodName = new QLIdentifierExpr(methodName);
        this.option = 1;
    }

    public QLAggregateExpr(String methodName, int option) {

        this.methodName = new QLIdentifierExpr(methodName);
        this.option = option;
    }

    public QLIdentifierExpr getMethodName() {
        return this.methodName;
    }

    public void setMethodName(QLIdentifierExpr methodName) {
        this.methodName = methodName;
    }

    public int getOption() {
        return this.option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public List<QLExpr> getArguments() {
        return this.arguments;
    }

    public void output(StringBuffer buf) {
        buf.append(this.methodName);
        buf.append("(");
        int i = 0;
        for (int size = this.arguments.size(); i < size; ++i) {
            ((QLExpr) this.arguments.get(i)).output(buf);
        }
        buf.append(")");
    }

    @Override
    protected void accept0(QLAstVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.methodName);
            acceptChild(visitor, this.arguments);
        }

        visitor.endVisit(this);
    }
}
