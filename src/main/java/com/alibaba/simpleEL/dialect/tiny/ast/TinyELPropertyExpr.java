package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;


public class TinyELPropertyExpr extends TinyELExpr implements TinyELName {

    private TinyELExpr owner;
    private String name;
    
    public TinyELPropertyExpr(TinyELExpr owner, String name) {
        this.owner = owner;
        this.name = name;
    }
    
    public boolean isTinyELName() {
    	if (this.owner instanceof TinyELIdentifierExpr) {
    		return true;
    	}
    	
    	if (this.owner instanceof TinyELPropertyExpr) {
    		return ((TinyELPropertyExpr) this.owner).isTinyELName();
    	}
    	
    	return false;
    }

    public TinyELPropertyExpr() {

    }

    public TinyELExpr getOwner() {
        return this.owner;
    }

    public void setOwner(TinyELExpr owner) {
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

    protected void accept0(TinyELAstVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.owner);
        }

        visitor.endVisit(this);
    }
}
