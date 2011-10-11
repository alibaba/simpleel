package com.alibaba.simpleEL.dialect.ql.bvt.parser;

import java.io.PrintWriter;
import java.io.StringWriter;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.ql.ast.QLExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelect;
import com.alibaba.simpleEL.dialect.ql.parser.QLExprParser;
import com.alibaba.simpleEL.dialect.ql.parser.QLSelectParser;
import com.alibaba.simpleEL.dialect.ql.visitor.QLOutputAstVisitor;

public class TestParser0 extends TestCase {

    public void test_0() throws Exception {
        QLExprParser parser = new QLExprParser("CASE F WHEN T THEN 1 ELSE 0 END");
        QLExpr expr = parser.expr();
        
        Assert.assertEquals("CASE F WHEN T THEN 1 ELSE 0 END", expr.toString());
        Assert.assertEquals("CASE F WHEN T THEN 1 ELSE 0 END", toString(expr));
        
    }
    
    public void test_1() throws Exception {
        QLExprParser parser = new QLExprParser("CASE WHEN T > 0 THEN 1 ELSE 0 END");
        QLExpr expr = parser.expr();
        
        Assert.assertEquals("CASE WHEN T > 0 THEN 1 ELSE 0 END", expr.toString());
        Assert.assertEquals("CASE WHEN T > 0 THEN 1 ELSE 0 END", toString(expr));
    }
    
    public void test_between() throws Exception {
        QLExprParser parser = new QLExprParser("F1 BETWEEN @min AND @max");
        QLExpr expr = parser.expr();
        
        Assert.assertEquals("F1 BETWEEN @min AND @max", expr.toString());
        Assert.assertEquals("F1 BETWEEN @min AND @max", toString(expr));
    }
    
    public void test_between_not() throws Exception {
        QLExprParser parser = new QLExprParser("F1 NOT BETWEEN @min AND @max");
        QLExpr expr = parser.expr();
        
        Assert.assertEquals("F1 NOT BETWEEN @min AND @max", expr.toString());
        Assert.assertEquals("F1 NOT BETWEEN @min AND @max", toString(expr));
    }
    
    public void test_method() throws Exception {
        QLExprParser parser = new QLExprParser("LEN(F1) > 10");
        QLExpr expr = parser.expr();
        
        Assert.assertEquals("LEN(F1) > 10", expr.toString());
        Assert.assertEquals("LEN(F1) > 10", toString(expr));
    }
    
    public void test_method_2() throws Exception {
        QLExprParser parser = new QLExprParser("u.name.length() > 10");
        QLExpr expr = parser.expr();
        
        Assert.assertEquals("u.name.length() > 10", expr.toString());
        Assert.assertEquals("u.name.length() > 10", toString(expr));
    }
    
    public void test_aggregate() throws Exception {
        QLExprParser parser = new QLExprParser("MAX(AGE) > 10");
        QLExpr expr = parser.expr();
        
        Assert.assertEquals("MAX(AGE) > 10", expr.toString());
        Assert.assertEquals("MAX(AGE) > 10", toString(expr));
    }
    
    public void test_select() throws Exception {
        QLSelectParser parser = new QLSelectParser("SELECT MAX(AGE) WHERE ID = 0 ORDER BY AGE");
        QLSelect select = parser.select();
        
        Assert.assertEquals("SELECT MAX(AGE)\nWHERE ID = 0 ORDER BY AGE ASC", select.toString());
        Assert.assertEquals("SELECT MAX(AGE)\nWHERE ID = 0 ORDER BY AGE ASC", toString(select));
    }
    
    public static String toString(QLSelect select) {
        StringWriter out = new StringWriter();
        QLOutputAstVisitor visitor = new QLOutputAstVisitor(new PrintWriter(out));
        select.accept(visitor);
        return out.toString();
    }
    
    public static String toString(QLExpr expr) {
        StringWriter out = new StringWriter();
        QLOutputAstVisitor visitor = new QLOutputAstVisitor(new PrintWriter(out));
        expr.accept(visitor);
        return out.toString();
    }
}
