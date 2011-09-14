package com.alibaba.simpleEL.bvt.dialect.tiny.ast;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELStringExpr;


public class TinyELStringExprTest extends TestCase {
    public void test_string_null() throws Exception {
        TinyELStringExpr expr = new TinyELStringExpr();
        Assert.assertEquals("null", expr.toString());
    }
    
    public void test_string() throws Exception {
        TinyELStringExpr expr = new TinyELStringExpr();
        expr.setValue("text");
        Assert.assertEquals("\"text\"", expr.toString());
    }
}
