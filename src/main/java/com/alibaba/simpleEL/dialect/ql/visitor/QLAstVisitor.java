package com.alibaba.simpleEL.dialect.ql.visitor;

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
import com.alibaba.simpleEL.dialect.ql.ast.QLPropertyExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLVariantRefExpr;

public interface QLAstVisitor {
    void postVisit(QLAstNode astNode);

    void preVisit(QLAstNode astNode);
    
    void endVisit(QLCaseExpr.Item x);

    boolean visit(QLCaseExpr.Item x);
    
    void endVisit(QLCaseExpr x);
    
    boolean visit(QLCaseExpr x);
    
    void endVisit(QLAggregateExpr x);
    
    boolean visit(QLAggregateExpr x);
    
    void endVisit(QLAllColumnExpr x);
    
    boolean visit(QLAllColumnExpr x);
    
    void endVisit(QLBetweenExpr x);
    
    boolean visit(QLBetweenExpr x);
    
    void endVisit(QLBinaryOpExpr x);
    
    boolean visit(QLBinaryOpExpr x);
    
    void endVisit(QLIdentifierExpr x);
    
    boolean visit(QLIdentifierExpr x);
    
    void endVisit(QLCharExpr x);
    
    boolean visit(QLCharExpr x);
    
    void endVisit(QLMethodInvokeExpr x);
    
    boolean visit(QLMethodInvokeExpr x);
    
    void endVisit(QLNullExpr x);
    
    boolean visit(QLNullExpr x);
    
    void endVisit(QLNumberLiteralExpr x);
    
    boolean visit(QLNumberLiteralExpr x);
    
    void endVisit(QLPropertyExpr x);
    
    boolean visit(QLPropertyExpr x);
    
    void endVisit(QLVariantRefExpr x);
    
    boolean visit(QLVariantRefExpr x);
}
