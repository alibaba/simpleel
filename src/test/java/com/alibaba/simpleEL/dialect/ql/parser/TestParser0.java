package com.alibaba.simpleEL.dialect.ql.parser;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.ql.ast.QLExpr;

public class TestParser0 extends TestCase {

    public void test_0() throws Exception {
        QLExprParser parser = new QLExprParser("CASE F WHEN T THEN 1 ELSE 0 END");
        QLExpr expr = parser.expr();
        
        Assert.assertEquals("CASE F WHEN T THEN 1 ELSE 0 END", expr.toString());
    }
    
    public void test_1() throws Exception {
        QLExprParser parser = new QLExprParser("CASE WHEN T > 0 THEN 1 ELSE 0 END");
        QLExpr expr = parser.expr();
        
        Assert.assertEquals("CASE WHEN T > 0 THEN 1 ELSE 0 END", expr.toString());
    }
}
