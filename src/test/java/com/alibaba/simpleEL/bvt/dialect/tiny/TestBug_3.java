package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;


public class TestBug_3 extends TestCase {
    public void test_0 () throws Exception {
        
        TinyELEvalService service = new TinyELEvalService();

        service.regsiterVariant(String.class, "description");
        service.regsiterVariant(BigDecimal.class, "in_bps_pct");
        service.regsiterVariant(BigDecimal.class, "out_bps_pct");

        Map<String, Object> ctx = new HashMap<String, Object>();
        //ctx.put("description", "");
        //ctx.put("in_bps_pct", 101344160L);
        //ctx.put("out_bps_pct", 101344160L);

        Assert.assertEquals(false, service.eval(ctx, "\"Ethernet15/18\".equals(@description) && (@in_bps_pct > 0.01 || @out_bps_pct > 0.01)"));
    }
}
