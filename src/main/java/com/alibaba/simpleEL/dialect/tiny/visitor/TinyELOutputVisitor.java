package com.alibaba.simpleEL.dialect.tiny.visitor;

import java.io.PrintWriter;
import java.util.List;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELAstNode;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNullExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNumberLiteralExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELPropertyExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELStringExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELVariantRefExpr;

public class TinyELOutputVisitor extends TinyELAstVisitorAdapter {
	protected PrintWriter out;

	public TinyELOutputVisitor(PrintWriter out) {
		this.out = out;
	}
	
	@Override
	public boolean visit(TinyELBinaryOpExpr x) {
	       if (x.getLeft() instanceof TinyELBinaryOpExpr) {
	    	   TinyELBinaryOpExpr left = (TinyELBinaryOpExpr) x.getLeft();
	            if (left.getOperator().priority > x.getOperator().priority) {
	                out.print('(');
	                left.accept(this);
	                out.print(')');
	            } else {
	                left.accept(this);
	            }
	        } else {
	            x.getLeft().accept(this);
	        }

	        out.print(" ");
	        out.print(x.getOperator().name);
	        out.print(" ");

	        if (x.getRight() instanceof TinyELBinaryOpExpr) {
	        	TinyELBinaryOpExpr right = (TinyELBinaryOpExpr) x.getRight();
	            if (right.getOperator().priority >= x.getOperator().priority) {
	                out.print('(');
	                right.accept(this);
	                out.print(')');
	            } else {
	                right.accept(this);
	            }
	        } else {
	            x.getRight().accept(this);
	        }

	        return false;
	}

	@Override
	public boolean visit(TinyELIdentifierExpr x) {
        out.print(x.getName());
        return false;
	}

	@Override
	public boolean visit(TinyELNullExpr x) {
		out.print("null");
		return false;
	}

	@Override
	public boolean visit(TinyELPropertyExpr x) {
        x.getOwner().accept(this);
        out.print(".");
        out.print(x.getName());
        return false;
	}

	@Override
	public boolean visit(TinyELMethodInvokeExpr x) {
        if (x.getOwner() != null) {
            x.getOwner().accept(this);
            out.print(".");
        }
        out.print(x.getMethodName());
        out.print("(");
        printAndAccept(x.getParameters(), ", ");
        out.print(")");
        return false;
	}

	@Override
	public boolean visit(TinyELNumberLiteralExpr x) {
		Number value = x.getValue();
		
		if (value == null) {
			out.print("null");
			return false;
		}
		
        out.print(x.getValue().toString());
        return false;
	}

	@Override
	public boolean visit(TinyELStringExpr x) {
		String value = x.getValue();
        if (value == null) {
            out.print("null");
        } else {
            out.print("'");
            for (char ch : value.toCharArray()) {
            	switch (ch) {
            	case '\t':
            		out.print("\\t");
            		break;
            	case '\n':
            		out.print("\\n");
            		break;
            	case '\r':
            		out.print("\\r");
            		break;
            	case '\f':
            		out.print("\\f");
            		break;
            	case '\b':
            		out.print("\\b");
            		break;
            	default:
            		out.print(ch);
            	}
        	}
            out.print("'");
        }

        return false;
	}

	@Override
	public boolean visit(TinyELVariantRefExpr x) {
    	out.print(x.getName());
		return false;
	}
	
    protected void printAndAccept(List<? extends TinyELAstNode> nodes, String seperator) {
        int i = 0;
        for (int size = nodes.size(); i < size; ++i) {
            if (i != 0) {
                out.print(seperator);
            }
            nodes.get(i).accept(this);
        }
    }
}
