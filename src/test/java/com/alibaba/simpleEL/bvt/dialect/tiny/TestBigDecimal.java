package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

public class TestBigDecimal extends TestCase {

    public void test_decimal() throws Exception {
        TinyELEvalService service = new TinyELEvalService();
        service.setProfileEnable(true);
        
        service.regsiterVariant(BigDecimal.class, "a", "b", "c");
        
        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", new BigDecimal("123"));
        ctx.put("b", new BigDecimal("456"));
        ctx.put("c", new BigDecimal("100"));
        
        Assert.assertEquals(Boolean.FALSE, service.eval(ctx, "a > b"));
        Assert.assertEquals(Boolean.FALSE, service.eval(ctx, "a >= b"));
        Assert.assertEquals(Boolean.TRUE, service.eval(ctx, "a < b"));
        Assert.assertEquals(Boolean.TRUE, service.eval(ctx, "a <= b"));
        Assert.assertEquals(new BigDecimal("579"), service.eval(ctx, "a + b"));
        Assert.assertEquals(new BigDecimal("-333"), service.eval(ctx, "a - b"));
        Assert.assertEquals(new BigDecimal("333"), service.eval(ctx, "-(a - b)"));
        Assert.assertEquals(new BigDecimal("101"), service.eval(ctx, "++c"));
        Assert.assertEquals(new BigDecimal("100"), service.eval(ctx, "--c"));
        Assert.assertEquals(new BigDecimal("100"), service.eval(ctx, "c++"));
        Assert.assertEquals(new BigDecimal("101"), service.eval(ctx, "c--"));
        Assert.assertEquals(new BigDecimal("100"), service.eval(ctx, "c"));
        Assert.assertEquals(new BigDecimal("100"), service.eval(ctx, "+c"));
        
        Assert.assertEquals(Boolean.FALSE, service.eval(ctx, "@a > @b"));
        Assert.assertEquals(Boolean.FALSE, service.eval(ctx, "@a >= @b"));
        Assert.assertEquals(Boolean.TRUE, service.eval(ctx, "@a < @b"));
        Assert.assertEquals(Boolean.TRUE, service.eval(ctx, "@a <= @b"));
        Assert.assertEquals(new BigDecimal("579"), service.eval(ctx, "@a + @b"));
        Assert.assertEquals(new BigDecimal("-333"), service.eval(ctx, "@a - @b"));
        Assert.assertEquals(new BigDecimal("333"), service.eval(ctx, "-(@a - @b)"));
        Assert.assertEquals(new BigDecimal("101"), service.eval(ctx, "++@c"));
        Assert.assertEquals(new BigDecimal("100"), service.eval(ctx, "--@c"));
        Assert.assertEquals(new BigDecimal("100"), service.eval(ctx, "@c++"));
        Assert.assertEquals(new BigDecimal("101"), service.eval(ctx, "@c--"));
        Assert.assertEquals(new BigDecimal("100"), service.eval(ctx, "@c"));
    }

    
}
