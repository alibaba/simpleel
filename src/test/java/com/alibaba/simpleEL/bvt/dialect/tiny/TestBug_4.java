package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;


public class TestBug_4 extends TestCase {
    public void test_0 () throws Exception {
        
        TinyELEvalService service = new TinyELEvalService();

        service.regsiterVariant(BigDecimal.class, "MemoryHeapUsed", "MemoryHeapCommitted", "OldGenUsed");

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("MemoryHeapUsed", 907594520);
        ctx.put("MemoryHeapCommitted", 2067005440);
        ctx.put("OldGenUsed", 1000);

        Assert.assertEquals(false, service.eval(ctx, "(@MemoryHeapUsed / (@MemoryHeapCommitted + 0.0)) > 0.9"));
        Assert.assertEquals(false, service.eval(ctx, "@MemoryHeapUsed * 100 / @MemoryHeapCommitted > 90"));
        Assert.assertEquals(false, service.eval(ctx, "(@OldGenUsed/ (96468992.0)) > 0.8"));
    }
}
