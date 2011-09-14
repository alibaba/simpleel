package com.alibaba.simpleEL.dialect.ql.bvt;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.simpleEL.dialect.ql.ast.QLExpr;
import com.alibaba.simpleEL.dialect.ql.parser.QLExprParser;

public class QLExprParserTest extends TestCase {
	public void test_0 () throws Exception {
		QLExprParser parser = new QLExprParser("age > 30");
		QLExpr expr = parser.expr();
		Assert.assertEquals("age > 30", expr.toString());
	}
}
