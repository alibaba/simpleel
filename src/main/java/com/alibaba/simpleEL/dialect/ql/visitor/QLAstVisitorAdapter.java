package com.alibaba.simpleEL.dialect.ql.visitor;

import com.alibaba.simpleEL.dialect.ql.ast.QLAggregateExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLAllColumnExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLAstNode;
import com.alibaba.simpleEL.dialect.ql.ast.QLBetweenExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLBinaryOpExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLCaseExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLCaseExpr.Item;
import com.alibaba.simpleEL.dialect.ql.ast.QLCharExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLIdentifierExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLLimit;
import com.alibaba.simpleEL.dialect.ql.ast.QLMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLNullExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLNumberLiteralExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderBy;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderByItem;
import com.alibaba.simpleEL.dialect.ql.ast.QLPropertyExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelect;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelectItem;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelectList;
import com.alibaba.simpleEL.dialect.ql.ast.QLVariantRefExpr;

public class QLAstVisitorAdapter implements QLAstVisitor {

	@Override
	public void postVisit(QLAstNode astNode) {
		
	}

	@Override
	public void preVisit(QLAstNode astNode) {
		
	}

	@Override
	public void endVisit(Item x) {
		
	}

	@Override
	public boolean visit(Item x) {
		return true;
	}

	@Override
	public void endVisit(QLCaseExpr x) {
		
	}

	@Override
	public boolean visit(QLCaseExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLAggregateExpr x) {
		
	}

	@Override
	public boolean visit(QLAggregateExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLAllColumnExpr x) {
		
	}

	@Override
	public boolean visit(QLAllColumnExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLBetweenExpr x) {
		
	}

	@Override
	public boolean visit(QLBetweenExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLBinaryOpExpr x) {
		
	}

	@Override
	public boolean visit(QLBinaryOpExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLIdentifierExpr x) {
		
	}

	@Override
	public boolean visit(QLIdentifierExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLCharExpr x) {
		
	}

	@Override
	public boolean visit(QLCharExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLMethodInvokeExpr x) {
		
	}

	@Override
	public boolean visit(QLMethodInvokeExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLNullExpr x) {
		
	}

	@Override
	public boolean visit(QLNullExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLNumberLiteralExpr x) {
		
	}

	@Override
	public boolean visit(QLNumberLiteralExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLPropertyExpr x) {
		
	}

	@Override
	public boolean visit(QLPropertyExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLVariantRefExpr x) {
		
	}

	@Override
	public boolean visit(QLVariantRefExpr x) {
		return true;
	}

	@Override
	public void endVisit(QLSelectList x) {
		
	}

	@Override
	public boolean visit(QLSelectList x) {
		return true;
	}

	@Override
	public void endVisit(QLSelect x) {
		
	}

	@Override
	public boolean visit(QLSelect x) {
		return true;
	}

	@Override
	public void endVisit(QLOrderBy x) {
		
	}

	@Override
	public boolean visit(QLOrderBy x) {
		return true;
	}

	@Override
	public void endVisit(QLOrderByItem x) {
		
	}

	@Override
	public boolean visit(QLOrderByItem x) {
		return true;
	}

	@Override
	public void endVisit(QLLimit x) {
		
	}

	@Override
	public boolean visit(QLLimit x) {
		return true;
	}

    @Override
    public void endVisit(QLSelectItem x) {
        
    }

    @Override
    public boolean visit(QLSelectItem x) {
        return true;
    }

}
