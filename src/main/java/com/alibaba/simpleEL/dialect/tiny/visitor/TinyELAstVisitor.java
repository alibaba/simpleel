package com.alibaba.simpleEL.dialect.tiny.visitor;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELAstNode;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELCharExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNullExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNumberLiteralExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELPropertyExpr;

public interface TinyELAstVisitor {
    void postVisit(TinyELAstNode astNode);

    void preVisit(TinyELAstNode astNode);
    
    void endVisit(TinyELBinaryOpExpr x);
    
    boolean visit(TinyELBinaryOpExpr x);
    
    void endVisit(TinyELIdentifierExpr x);
    
    boolean visit(TinyELIdentifierExpr x);
    
    void endVisit(TinyELNullExpr x);
    
    boolean visit(TinyELNullExpr x);
    
    void endVisit(TinyELPropertyExpr x);
    
    boolean visit(TinyELPropertyExpr x);
    
    void endVisit(TinyELMethodInvokeExpr x);
    
    boolean visit(TinyELMethodInvokeExpr x);
    
    void endVisit(TinyELNumberLiteralExpr x);
    
    boolean visit(TinyELNumberLiteralExpr x);
    
    void endVisit(TinyELCharExpr x);
    
    boolean visit(TinyELCharExpr x);
}
