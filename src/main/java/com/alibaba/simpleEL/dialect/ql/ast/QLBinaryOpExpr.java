package com.alibaba.simpleEL.dialect.ql.ast;


public class QLBinaryOpExpr extends QLExpr {
	  private static final long serialVersionUID = 1L;
	    public QLExpr left;
	    public QLExpr right;
	    public QLBinaryOperator operator;

	    public QLBinaryOpExpr() {

	    }

	    public QLBinaryOpExpr(QLExpr left, QLBinaryOperator operator, QLExpr right) {

	        this.left = left;
	        this.right = right;
	        this.operator = operator;
	    }

	    public QLBinaryOpExpr(QLExpr left, QLExpr right, QLBinaryOperator operator) {

	        this.left = left;
	        this.right = right;
	        this.operator = operator;
	    }

	    public QLExpr getLeft() {
	        return this.left;
	    }

	    public void setLeft(QLExpr left) {
	        this.left = left;
	    }

	    public QLExpr getRight() {
	        return this.right;
	    }

	    public void setRight(QLExpr right) {
	        this.right = right;
	    }

	    public QLBinaryOperator getOperator() {
	        return this.operator;
	    }

	    public void setOperator(QLBinaryOperator operator) {
	        this.operator = operator;
	    }
}
