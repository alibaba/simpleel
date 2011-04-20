package com.alibaba.simpleEL.dialect.tiny.ast;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;

public class TinyELNewExpr extends TinyELExpr {
	   private static final long serialVersionUID = 1L;
	    private String type;
	    private TinyELExpr owner;
	    private final List<TinyELExpr> parameters = new ArrayList<TinyELExpr>();

	    public TinyELNewExpr() {

	    }

	    public TinyELNewExpr(String type) {
	        this.type = type;
	    }

	    public TinyELNewExpr(String type, TinyELExpr owner) {
	        this.type = type;
	        this.owner = owner;
	    }

	    public String getType() {
	        return this.type;
	    }

	    public void setType(String type) {
	        this.type = type;
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
	        
	        buf.append("new ");

	        buf.append(this.type);
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
