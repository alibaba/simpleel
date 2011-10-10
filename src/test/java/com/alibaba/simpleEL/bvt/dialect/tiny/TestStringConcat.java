package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

public class TestStringConcat extends TestCase {

    public void test_now() throws Exception {
        TinyELEvalService service = new TinyELEvalService();
        
        service.regsiterVariant(String.class, "a", "b");
        
        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", "123");
        ctx.put("b", "cde");
        Assert.assertEquals(new Integer(6), service.eval(ctx, "(a + b).length()"));
    }

    
}
