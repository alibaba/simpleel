package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.math.BigDecimal;
import java.util.HashMap;

import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;


public class TestBug_5 extends TestCase {
    public void test_bug() throws Exception {
        TinyELEvalService service = new TinyELEvalService();
        service.registerFunction("LAST", TestBug_5.class.getMethod("last", Object.class));
        service.regsiterVariant(long.class, "status");
        
        
        service.eval(new HashMap<String, Object>(), "@status != LAST(@status)");
    }
    
    public static Object last(Object obj) {
        return null;
    }
}
