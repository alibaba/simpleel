package com.alibaba.simpleEL.bvt.dialect.tiny.ast;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.ast.TinyELMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNumberLiteralExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELStringExpr;


public class TinyELMethodInvokeExprTest extends TestCase {
    public void test_method () throws Exception {
        TinyELMethodInvokeExpr expr = new TinyELMethodInvokeExpr();
        expr.setOwner(new TinyELStringExpr("abc"));
        expr.setMethodName("length");
        
        Assert.assertEquals("\"abc\".length()", expr.toString());
    }
    
    public void test_method_2 () throws Exception {
        TinyELMethodInvokeExpr expr = new TinyELMethodInvokeExpr();
        expr.setOwner(new TinyELStringExpr("abc"));
        expr.setMethodName("substring");
        expr.getParameters().add(new TinyELNumberLiteralExpr(3));
        
        Assert.assertEquals("\"abc\".substring(3)", expr.toString());
    }
}
