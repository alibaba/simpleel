package com.alibaba.simpleEL.dialect.ql.bvt;

import junit.framework.TestCase;

import org.junit.Assert;

import com.alibaba.simpleEL.dialect.ql.ast.QLExpr;
import com.alibaba.simpleEL.dialect.ql.parser.QLExprParser;

public class QLExprParserTest extends TestCase {

    public void test_0() throws Exception {
        QLExprParser parser = new QLExprParser("age > 30");
        QLExpr expr = parser.expr();
        Assert.assertEquals("age > 30", expr.toString());
    }

    public void test_1() throws Exception {
        QLExprParser parser = new QLExprParser("age * 30");
        QLExpr expr = parser.expr();
        Assert.assertEquals("age * 30", expr.toString());
    }
    
    public void test_2() throws Exception {
        QLExprParser parser = new QLExprParser("age | 30");
        QLExpr expr = parser.expr();
        Assert.assertEquals("age | 30", expr.toString());
    }
    
    public void test_3() throws Exception {
        QLExprParser parser = new QLExprParser("age & 30");
        QLExpr expr = parser.expr();
        Assert.assertEquals("age & 30", expr.toString());
    }
    
    public void test_4() throws Exception {
        QLExprParser parser = new QLExprParser("age ^ 30");
        QLExpr expr = parser.expr();
        Assert.assertEquals("age ^ 30", expr.toString());
    }
    
    public void test_5() throws Exception {
        QLExprParser parser = new QLExprParser("age = 30");
        QLExpr expr = parser.expr();
        Assert.assertEquals("age = 30", expr.toString());
    }
    
    public void test_6() throws Exception {
        QLExprParser parser = new QLExprParser("a + b - c > 5");
        QLExpr expr = parser.expr();
        Assert.assertEquals("a + b - c > 5", expr.toString());
    }
    
    
    public void test_7() throws Exception {
        QLExprParser parser = new QLExprParser("age >= 30");
        QLExpr expr = parser.expr();
        Assert.assertEquals("age >= 30", expr.toString());
    }
    
    
    public void test_8() throws Exception {
        QLExprParser parser = new QLExprParser("a <= b and c >= d");
        QLExpr expr = parser.expr();
        Assert.assertEquals("a <= b AND c >= d", expr.toString());
    }
}
