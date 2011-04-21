package com.alibaba.simpleEL.dialect.tiny.visitor;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELArrayAccessExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELAstNode;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBooleanExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELConditionalExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNewExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNullExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNumberLiteralExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELPropertyExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELStringExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELVariantRefExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyUnaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELExprStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELForEachStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELForStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELIfStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELIfStatement.Else;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELIfStatement.ElseIf;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELReturnStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELVariantDeclareItem;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELWhileStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyLocalVarDeclareStatement;

public class TinyELAstVisitorAdapter implements TinyELAstVisitor {

	@Override
	public void postVisit(TinyELAstNode astNode) {
		
	}

	@Override
	public void preVisit(TinyELAstNode astNode) {
		
	}

	@Override
	public void endVisit(TinyELBinaryOpExpr x) {
		
	}

	@Override
	public boolean visit(TinyELBinaryOpExpr x) {
		return true;
	}

	@Override
	public void endVisit(TinyELIdentifierExpr x) {
		
	}

	@Override
	public boolean visit(TinyELIdentifierExpr x) {
		return true;
	}

	@Override
	public void endVisit(TinyELNullExpr x) {
		
	}

	@Override
	public boolean visit(TinyELNullExpr x) {
		return true;
	}

	@Override
	public void endVisit(TinyELPropertyExpr x) {
		
	}

	@Override
	public boolean visit(TinyELPropertyExpr x) {
		return true;
	}

	@Override
	public void endVisit(TinyELMethodInvokeExpr x) {
		
	}

	@Override
	public boolean visit(TinyELMethodInvokeExpr x) {
		return true;
	}

	@Override
	public void endVisit(TinyELNumberLiteralExpr x) {
		
	}

	@Override
	public boolean visit(TinyELNumberLiteralExpr x) {
		return true;
	}

	@Override
	public void endVisit(TinyELStringExpr x) {
		
	}

	@Override
	public boolean visit(TinyELStringExpr x) {
		return true;
	}

	@Override
	public void endVisit(TinyELVariantRefExpr x) {
		
	}

	@Override
	public boolean visit(TinyELVariantRefExpr x) {
		return true;
	}

	@Override
	public void endVisit(TinyELBooleanExpr x) {
		
	}

	@Override
	public boolean visit(TinyELBooleanExpr x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyELArrayAccessExpr x) {
		
	}
	
	@Override
	public boolean visit(TinyELArrayAccessExpr x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyELNewExpr x) {
		
	}
	
	@Override
	public boolean visit(TinyELNewExpr x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyELReturnStatement x) {
		
	}
	
	@Override
	public boolean visit(TinyELReturnStatement x) {
		return true;
	}
	
	@Override
	public void endVisit(ElseIf x) {
		
	}
	
	@Override
	public boolean visit(ElseIf x) {
		return true;
	}
	
	@Override
	public void endVisit(Else x) {
		
	}
	
	@Override
	public boolean visit(Else x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyELIfStatement x) {
		
	}
	
	@Override
	public boolean visit(TinyELIfStatement x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyELVariantDeclareItem x) {
		
	}
	
	@Override
	public boolean visit(TinyELVariantDeclareItem x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyLocalVarDeclareStatement x) {
		
	}
	
	@Override
	public boolean visit(TinyLocalVarDeclareStatement x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyELConditionalExpr x) {
		
	}
	
	@Override
	public boolean visit(TinyELConditionalExpr x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyELExprStatement x) {
		
	}
	
	@Override
	public boolean visit(TinyELExprStatement x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyELWhileStatement x) {
		
	}
	
	@Override
	public boolean visit(TinyELWhileStatement x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyUnaryOpExpr x) {
		
	}
	
	@Override
	public boolean visit(TinyUnaryOpExpr x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyELForEachStatement x) {
		
	}
	
	@Override
	public boolean visit(TinyELForEachStatement x) {
		return true;
	}
	
	@Override
	public void endVisit(TinyELForStatement x) {
		
	}
	
	@Override
	public boolean visit(TinyELForStatement x) {
		return true;
	}

}
