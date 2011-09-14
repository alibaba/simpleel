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
        QLExprParser parser = new QLExprParser("age >= 30.23");
        QLExpr expr = parser.expr();
        Assert.assertEquals("age >= 30.23", expr.toString());
    }
    
    
    public void test_8() throws Exception {
        QLExprParser parser = new QLExprParser("a <= b and c >= d");
        QLExpr expr = parser.expr();
        Assert.assertEquals("a <= b AND c >= d", expr.toString());
    }
    
    public void test_9() throws Exception {
        QLExprParser parser = new QLExprParser("a <= -1 and b >= +1 and c < +1.2 and D > -7.1");
        QLExpr expr = parser.expr();
        Assert.assertEquals("a <= -1 AND b >= 1 AND c < 1.2 AND D > -7.1", expr.toString());
    }
    
    public void test_10() throws Exception {
        QLExprParser parser = new QLExprParser("a IS NULL");
        QLExpr expr = parser.expr();
        Assert.assertEquals("a IS NULL", expr.toString());
    }
    
    public void test_11() throws Exception {
        QLExprParser parser = new QLExprParser("a like 'aaa'");
        QLExpr expr = parser.expr();
        Assert.assertEquals("a LIKE 'aaa'", expr.toString());
    }
    
    public void test_12() throws Exception {
        QLExprParser parser = new QLExprParser("a not like 'aaa'");
        QLExpr expr = parser.expr();
        Assert.assertEquals("a NOT LIKE 'aaa'", expr.toString());
    }
    
    public void test_13() throws Exception {
        QLExprParser parser = new QLExprParser("a <= b or c >= d");
        QLExpr expr = parser.expr();
        Assert.assertEquals("a <= b OR c >= d", expr.toString());
    }
    
    public void test_14() throws Exception {
        QLExprParser parser = new QLExprParser("a <= b xor c >= d");
        QLExpr expr = parser.expr();
        Assert.assertEquals("a <= b XOR c >= d", expr.toString());
    }
    
    public void test_15() throws Exception {
        QLExprParser parser = new QLExprParser("(a + b) / c");
        QLExpr expr = parser.expr();
        Assert.assertEquals("(a + b) / c", expr.toString());
    }
}
