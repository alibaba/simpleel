package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;


public class Test_Conditional extends TestCase {
    public void test_conditional() throws Exception {
        
        TinyELEvalService service = new TinyELEvalService();
        
        service.registerFunction(Test_Conditional.class.getMethod("last", Object.class));
        service.regsiterVariant(Long.class, "bandwidth", "in_byte_total", "in_byte_total", "@@INTERVAL");

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("A", 123L);
        ctx.put("B", 456L);
        ctx.put("C", 789L);
        ctx.put("bandwidth", 0);

        Assert.assertEquals(0, service.eval(ctx, "@bandwidth != 0 ? ((@in_byte_total - last(@in_byte_total)) * 8 / (@@INTERVAL + 0.0)) / (@bandwidth * 1048576) : 0L"));
    }
    
    public static Object last(Object o) {
        return o;
    }
}
