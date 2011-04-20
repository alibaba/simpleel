package com.alibaba.simpleEL.dialect.tiny.visitor;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELArrayAccessExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELAstNode;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBooleanExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNewExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNullExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNumberLiteralExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELPropertyExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELStringExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELVariantRefExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELIfStatement.Else;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELReturnStatement;

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
    
    void endVisit(TinyELStringExpr x);
    
    boolean visit(TinyELStringExpr x);
    
    void endVisit(TinyELVariantRefExpr x);
    
    boolean visit(TinyELVariantRefExpr x);
    
    void endVisit(TinyELBooleanExpr x);
    
    boolean visit(TinyELBooleanExpr x);
    
    void endVisit(TinyELArrayAccessExpr x);
    
    boolean visit(TinyELArrayAccessExpr x);
    
    void endVisit(TinyELNewExpr x);
    
    boolean visit(TinyELNewExpr x);
    
    void endVisit(TinyELReturnStatement x);
    
    boolean visit(TinyELReturnStatement x);
    
    void endVisit(Else x);
    
    boolean visit(Else x);
}
