package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;


public class TestBug_1 extends TestCase {
    public void test_0 () throws Exception {
        
        TinyELEvalService service = new TinyELEvalService();

        service.regsiterVariant(BigDecimal.class, "MemoryHeapUsed", "MemoryHeapCommitted");

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("MemoryHeapUsed", 101344160L);
        ctx.put("MemoryHeapCommitted", 288489472);

        Assert.assertEquals(false, service.eval(ctx, "@MemoryHeapUsed * 100 / @MemoryHeapCommitted > 90"));
        Assert.assertEquals(false, service.eval(ctx, "(@MemoryHeapUsed * 100) / @MemoryHeapCommitted > 90"));
    }
}
