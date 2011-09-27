package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;


public class TestBug_2 extends TestCase {
    public void test_0 () throws Exception {
        
        TinyELEvalService service = new TinyELEvalService();

        service.regsiterVariant(BigDecimal.class, "notifyExceptionCount");

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("notifyExceptionCount", 101344160L);

        Assert.assertEquals(true, service.eval(ctx, "@notifyExceptionCount !=null && !\"0\".equals(@notifyExceptionCount)"));
    }
}
