package com.alibaba.simpleEL.dialect.ql.ast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;

public class QLMethodInvokeExpr extends QLExpr implements Serializable {
    private static final long serialVersionUID = 1L;
    private String methodName;
    private QLExpr owner;
    private final List<QLExpr> parameters = new ArrayList<QLExpr>();

    public QLMethodInvokeExpr() {

    }

    public QLMethodInvokeExpr(String methodName) {

        this.methodName = methodName;
    }

    public QLMethodInvokeExpr(String methodName, QLExpr owner) {

        this.methodName = methodName;
        this.owner = owner;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public QLExpr getOwner() {
        return this.owner;
    }

    public void setOwner(QLExpr owner) {
        this.owner = owner;
    }

    public List<QLExpr> getParameters() {
        return this.parameters;
    }
    
    public void output(StringBuffer buf) {
        if (this.owner != null) {
            this.owner.output(buf);
            buf.append(".");
        }

        buf.append(this.methodName);
        buf.append("(");
        for (int i = 0, size = this.parameters.size(); i < size; ++i) {
            if (i != 0) {
                buf.append(", ");
            }

            this.parameters.get(i).output(buf);
        }
        buf.append(")");
    }

    @Override
    protected void accept0(QLAstVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.owner);
            acceptChild(visitor, this.parameters);
        }

        visitor.endVisit(this);
    }
}
