package com.alibaba.simpleEL.dialect.tiny.ast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELMethodInvokeExpr extends TinyELExpr implements Serializable {
    private static final long serialVersionUID = 1L;
    private String methodName;
    private TinyELExpr owner;
    private final List<TinyELExpr> parameters = new ArrayList<TinyELExpr>();

    public TinyELMethodInvokeExpr() {

    }

    public TinyELMethodInvokeExpr(String methodName) {

        this.methodName = methodName;
    }

    public TinyELMethodInvokeExpr(String methodName, TinyELExpr owner) {

        this.methodName = methodName;
        this.owner = owner;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public TinyELExpr getOwner() {
        return this.owner;
    }

    public void setOwner(TinyELExpr owner) {
        this.owner = owner;
    }

    public List<TinyELExpr> getParameters() {
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
    protected void accept0(TinyELAstVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.owner);
            acceptChild(visitor, this.parameters);
        }

        visitor.endVisit(this);
    }
}
