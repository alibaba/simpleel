package com.alibaba.simpleEL.dialect.ql.ast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

}
