package com.alibaba.simpleEL.bvt.dialect.tiny;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.parser.TinyELExprParser;

public class TinyELExprParserTest extends TestCase {
	public void test_0 () throws Exception {
		String text = "a + b";
		TinyELExprParser parser = new TinyELExprParser(text);
		TinyELExpr expr = parser.expr();
		
		TinyELBinaryOpExpr binaryExpr = (TinyELBinaryOpExpr) expr;
		
		TinyELIdentifierExpr left = (TinyELIdentifierExpr) binaryExpr.getLeft();
		TinyELIdentifierExpr right = (TinyELIdentifierExpr) binaryExpr.getRight();
		
		Assert.assertEquals("a", left.getName());
		Assert.assertEquals("b", right.getName());
		
	}
}
