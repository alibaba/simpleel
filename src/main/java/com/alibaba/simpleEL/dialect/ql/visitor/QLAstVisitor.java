package com.alibaba.simpleEL.dialect.ql.visitor;

import com.alibaba.simpleEL.dialect.ql.ast.QLAstNode;
import com.alibaba.simpleEL.dialect.ql.ast.QLCaseExpr;

public interface QLAstVisitor {
    void postVisit(QLAstNode astNode);

    void preVisit(QLAstNode astNode);
    
    void endVisit(QLCaseExpr.Item x);

    boolean visit(QLCaseExpr.Item x);
}
