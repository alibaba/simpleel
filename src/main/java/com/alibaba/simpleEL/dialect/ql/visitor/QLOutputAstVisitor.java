package com.alibaba.simpleEL.dialect.ql.visitor;

import java.io.PrintWriter;
import java.util.List;

import com.alibaba.simpleEL.dialect.ql.ast.QLAggregateExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLAllColumnExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLAstNode;
import com.alibaba.simpleEL.dialect.ql.ast.QLBetweenExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLBinaryOpExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLCaseExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLCharExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLIdentifierExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLNullExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLNumberLiteralExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderBy;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderByItem;
import com.alibaba.simpleEL.dialect.ql.ast.QLPropertyExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelect;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelectItem;
import com.alibaba.simpleEL.dialect.ql.ast.QLVariantRefExpr;

public class QLOutputAstVisitor extends QLAstVisitorAdapter {
	protected PrintWriter out;

	public QLOutputAstVisitor(PrintWriter out) {
		this.out = out;
	}
	
	public boolean visit(QLBetweenExpr x) {
        x.getTestExpr().accept(this);
        if (x.isNot()) {
        	out.print(" NOT BETWEEN ");
        } else {
            out.print(" BETWEEN ");
        }
        x.getBeginExpr().accept(this);
        out.print(" AND ");
        x.getEndExpr().accept(this);
        return false;
    }

    public boolean visit(QLBinaryOpExpr x) {
        if (x.getLeft() instanceof QLBinaryOpExpr) {
            QLBinaryOpExpr left = (QLBinaryOpExpr) x.getLeft();
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

        if (x.getRight() instanceof QLBinaryOpExpr) {
            QLBinaryOpExpr right = (QLBinaryOpExpr) x.getRight();
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

    public boolean visit(QLCaseExpr x) {
        out.print("CASE ");
        if (x.getValueExpr() != null) {
            x.getValueExpr().accept(this);
            out.print(" ");
        }

        printAndAccept(x.getItems(), " ");

        if (x.getElseExpr() != null) {
            out.print(" ELSE ");
            x.getElseExpr().accept(this);
        }

        out.print(" END");
        return false;
    }

    public boolean visit(QLCaseExpr.Item x) {
        out.print("WHEN ");
        x.getConditionExpr().accept(this);
        out.print(" THEN ");
        x.getValueExpr().accept(this);
        return false;
    }


    public boolean visit(QLCharExpr x) {
        if ((x.getValue() == null) || (x.getValue().length() == 0)) {
            out.print("NULL");
        } else {
            out.print("'");
            out.print(x.getValue().replaceAll("'", "''"));
            out.print("'");
        }

        return false;
    }


    public boolean visit(QLIdentifierExpr astNode) {
        out.print(astNode.getName());
        return false;
    }


    public boolean visit(QLNumberLiteralExpr x) {
        out.print(x.getValue().toString());
        return false;
    }

    public boolean visit(QLMethodInvokeExpr x) {
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

    public boolean visit(QLAggregateExpr x) {
        x.getMethodName().accept(this);
        out.print("(");
        printAndAccept(x.getArguments(), ", ");
        out.print(")");
        return false;
    }

    public boolean visit(QLAllColumnExpr x) {
        out.print("*");
        return true;
    }


    public boolean visit(QLNullExpr x) {
        out.print("NULL");
        return false;
    }

    public boolean visit(QLPropertyExpr x) {
        x.getOwner().accept(this);
        out.print(".");
        out.print(x.getName());
        return false;
    }


    public boolean visit(QLSelect select) {
        out.print("SELECT ");

        if (select.getSelectList() != null) {
            out.println();
            out.print("FROM ");
            select.getSelectList().accept(this);
        }

        if (select.getWhere() != null) {
            out.println();
            out.print("WHERE ");
            select.getWhere().accept(this);
        }

        if (select.getOrderBy() != null) {
            out.print(" ");
            select.getOrderBy().accept(this);
        }

        return false;
    }

    public boolean visit(QLSelectItem x) {
        x.getExpr().accept(this);

        if ((x.getAlias() != null) && (x.getAlias().length() > 0)) {
            out.print(" AS ");
            out.print(x.getAlias());
        }
        return false;
    }

    public boolean visit(QLOrderBy x) {
        if (x.getItems().size() > 0) {
            out.print("ORDER BY ");

            printAndAccept(x.getItems(), ", ");
        }
        return false;
    }

    public boolean visit(QLOrderByItem x) {
        x.getExpr().accept(this);
        if (x.getMode() != null) {
            out.print(" ");
            out.print(x.getMode().name().toUpperCase());
        }

        return false;
    }
    
    @Override
	public boolean visit(QLVariantRefExpr x) {
    	out.print(x.getName());
		return false;
	}
    
    public void incrementIndent() {
    	
    }
    public void decrementIndent() {
    	
    }
    
    protected void printAndAccept(List<? extends QLAstNode> nodes, String seperator) {
        int i = 0;
        for (int size = nodes.size(); i < size; ++i) {
            if (i != 0) {
                out.print(seperator);
            }
            nodes.get(i).accept(this);
        }
    }
}
