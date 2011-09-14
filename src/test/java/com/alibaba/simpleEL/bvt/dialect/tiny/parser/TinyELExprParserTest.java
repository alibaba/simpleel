package com.alibaba.simpleEL.bvt.dialect.tiny.parser;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNewExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNumberLiteralExpr;
import com.alibaba.simpleEL.dialect.tiny.parser.TinyELExprParser;

public class TinyELExprParserTest extends TestCase {

    public void test_add() throws Exception {
        String text = "a + b";
        TinyELExprParser parser = new TinyELExprParser(text);
        TinyELExpr expr = parser.expr();

        TinyELBinaryOpExpr binaryExpr = (TinyELBinaryOpExpr) expr;

        TinyELIdentifierExpr left = (TinyELIdentifierExpr) binaryExpr.getLeft();
        TinyELIdentifierExpr right = (TinyELIdentifierExpr) binaryExpr.getRight();

        Assert.assertEquals("a", left.getName());
        Assert.assertEquals("b", right.getName());

    }

    public void test_new() throws Exception {
        String text = "new Integer(3)";
        TinyELExprParser parser = new TinyELExprParser(text);
        TinyELExpr expr = parser.expr();

        TinyELNewExpr newExpr = (TinyELNewExpr) expr;

        Assert.assertEquals("Integer", newExpr.getType());
        Assert.assertEquals("3", newExpr.getParameters().get(0).toString());
        
        Assert.assertEquals(newExpr.toString(), text);
    }
    
    public void test_modulus() throws Exception {
        String text = "a / b";
        TinyELExprParser parser = new TinyELExprParser(text);
        TinyELExpr expr = parser.expr();

        TinyELBinaryOpExpr binaryExpr = (TinyELBinaryOpExpr) expr;

        TinyELIdentifierExpr left = (TinyELIdentifierExpr) binaryExpr.getLeft();
        TinyELIdentifierExpr right = (TinyELIdentifierExpr) binaryExpr.getRight();

        Assert.assertEquals("a", left.getName());
        Assert.assertEquals("b", right.getName());
        Assert.assertEquals(expr.toString(), text);
    }
    
    
    public void test_bitAnd() throws Exception {
        String text = "a & b";
        TinyELExprParser parser = new TinyELExprParser(text);
        TinyELExpr expr = parser.expr();

        TinyELBinaryOpExpr binaryExpr = (TinyELBinaryOpExpr) expr;

        TinyELIdentifierExpr left = (TinyELIdentifierExpr) binaryExpr.getLeft();
        TinyELIdentifierExpr right = (TinyELIdentifierExpr) binaryExpr.getRight();

        Assert.assertEquals("a", left.getName());
        Assert.assertEquals("b", right.getName());
        Assert.assertEquals(expr.toString(), text);
    }
    
    public void test_bitOr() throws Exception {
        String text = "a | b";
        TinyELExprParser parser = new TinyELExprParser(text);
        TinyELExpr expr = parser.expr();

        TinyELBinaryOpExpr binaryExpr = (TinyELBinaryOpExpr) expr;

        TinyELIdentifierExpr left = (TinyELIdentifierExpr) binaryExpr.getLeft();
        TinyELIdentifierExpr right = (TinyELIdentifierExpr) binaryExpr.getRight();

        Assert.assertEquals("a", left.getName());
        Assert.assertEquals("b", right.getName());
        Assert.assertEquals(expr.toString(), text);
    }
    
    public void test_bitXor() throws Exception {
        String text = "a ^ b";
        TinyELExprParser parser = new TinyELExprParser(text);
        TinyELExpr expr = parser.expr();
        
        TinyELBinaryOpExpr binaryExpr = (TinyELBinaryOpExpr) expr;
        
        TinyELIdentifierExpr left = (TinyELIdentifierExpr) binaryExpr.getLeft();
        TinyELIdentifierExpr right = (TinyELIdentifierExpr) binaryExpr.getRight();
        
        Assert.assertEquals("a", left.getName());
        Assert.assertEquals("b", right.getName());
        Assert.assertEquals(expr.toString(), text);
    }
    
    public void test_shift() throws Exception {
        String text = "a >> b";
        TinyELExprParser parser = new TinyELExprParser(text);
        TinyELExpr expr = parser.expr();
        
        TinyELBinaryOpExpr binaryExpr = (TinyELBinaryOpExpr) expr;
        
        TinyELIdentifierExpr left = (TinyELIdentifierExpr) binaryExpr.getLeft();
        TinyELIdentifierExpr right = (TinyELIdentifierExpr) binaryExpr.getRight();
        
        Assert.assertEquals("a", left.getName());
        Assert.assertEquals("b", right.getName());
        Assert.assertEquals(expr.toString(), text);
    }
    
    public void test_shift2() throws Exception {
        String text = "a << 3";
        TinyELExprParser parser = new TinyELExprParser(text);
        TinyELExpr expr = parser.expr();
        
        TinyELBinaryOpExpr binaryExpr = (TinyELBinaryOpExpr) expr;
        
        TinyELIdentifierExpr left = (TinyELIdentifierExpr) binaryExpr.getLeft();
        TinyELNumberLiteralExpr right = (TinyELNumberLiteralExpr) binaryExpr.getRight();
        
        Assert.assertEquals("a", left.getName());
        Assert.assertEquals(3, right.getValue());
        Assert.assertEquals(expr.toString(), text);
    }
    
    public void test_sub() throws Exception {
        String text = "1 + 2.0 + -1 + -1.3";
        TinyELExprParser parser = new TinyELExprParser(text);
        TinyELExpr expr = parser.expr();

        Assert.assertEquals(expr.toString(), text);

    }
}
