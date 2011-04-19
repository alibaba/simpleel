package com.alibaba.simpleEL.dialect.tiny.ast;

import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELAstVisitor;


public class TinyELBinaryOpExpr extends TinyELExpr {
	  private static final long serialVersionUID = 1L;
	    public TinyELExpr left;
	    public TinyELExpr right;
	    public TinyELBinaryOperator operator;

	    public TinyELBinaryOpExpr() {

	    }

	    public TinyELBinaryOpExpr(TinyELExpr left, TinyELBinaryOperator operator, TinyELExpr right) {

	        this.left = left;
	        this.right = right;
	        this.operator = operator;
	    }

	    public TinyELBinaryOpExpr(TinyELExpr left, TinyELExpr right, TinyELBinaryOperator operator) {

	        this.left = left;
	        this.right = right;
	        this.operator = operator;
	    }

	    public TinyELExpr getLeft() {
	        return this.left;
	    }

	    public void setLeft(TinyELExpr left) {
	        this.left = left;
	    }

	    public TinyELExpr getRight() {
	        return this.right;
	    }

	    public void setRight(TinyELExpr right) {
	        this.right = right;
	    }

	    public TinyELBinaryOperator getOperator() {
	        return this.operator;
	    }

	    public void setOperator(TinyELBinaryOperator operator) {
	        this.operator = operator;
	    }

	    protected void accept0(TinyELAstVisitor visitor) {
	        if (visitor.visit(this)) {
	            acceptChild(visitor, this.left);
	            acceptChild(visitor, this.right);
	        }

	        visitor.endVisit(this);
	    }
	    
	    public void output(StringBuffer buf) {
	        this.left.output(buf);
	        buf.append(" ");
	        buf.append(this.operator.name);
	        buf.append(" ");
	        this.right.output(buf);
	    }
}
