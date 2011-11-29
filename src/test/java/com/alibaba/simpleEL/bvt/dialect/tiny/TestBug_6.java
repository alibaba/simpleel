package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.util.HashMap;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

import junit.framework.Assert;
import junit.framework.TestCase;


public class TestBug_6 extends TestCase {
    public void test_0 () throws Exception {
        String expr = "(!@LastThrowMessage.contains(\"Unable to find resource\") || !@LastThrowMessage.contains(\"Error rendering Velocity template\"))  && @Count > 5";
        
        TinyELEvalService service = new TinyELEvalService();
        service.regsiterVariant(String.class, "LastThrowMessage");
        service.regsiterVariant(long.class, "Count");
        
        HashMap<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("LastThrowMessage", "xxx");
        ctx.put("Count", 3);
        Object result = service.eval(ctx, expr);
        
        Assert.assertFalse(((Boolean)result).booleanValue());
    }
}
