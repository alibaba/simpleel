package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

import junit.framework.Assert;
import junit.framework.TestCase;


public class TestProperty extends TestCase {
    public void test_property() throws Exception {
        TinyELEvalService service = new TinyELEvalService();
        service.regsiterVariant(BigDecimal.class, "a", "b", "c");
        service.regsiterVariant(Date.class, "d");
        
        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", 1);
        ctx.put("b", 2);
        Assert.assertEquals(BigDecimal.ONE, service.eval(ctx, "(a + b).ONE"));
        Assert.assertEquals(BigDecimal.ONE, service.eval(ctx, "a.ONE"));
    }
}
