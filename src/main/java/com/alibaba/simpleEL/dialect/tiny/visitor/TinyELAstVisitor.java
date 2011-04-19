package com.alibaba.simpleEL.dialect.tiny.visitor;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELAstNode;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOpExpr;

public interface TinyELAstVisitor {
    void postVisit(TinyELAstNode astNode);

    void preVisit(TinyELAstNode astNode);
    
    void endVisit(TinyELBinaryOpExpr x);
    
    boolean visit(TinyELBinaryOpExpr x);
}
