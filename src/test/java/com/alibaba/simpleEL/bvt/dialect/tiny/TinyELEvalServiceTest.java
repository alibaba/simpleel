package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

public class TinyELEvalServiceTest extends TestCase {
	public void test_0 () throws Exception {
		TinyELEvalService service = new TinyELEvalService();
		
		service.regsiterVariant(int.class, "a", "b");

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", 3);
        ctx.put("b", 4);

        Assert.assertEquals(7, service.eval(ctx, "a + b"));
        Assert.assertEquals(true, service.eval(ctx, "a < b"));
	}
	
	public void test_1 () throws Exception {
		TinyELEvalService service = new TinyELEvalService();
		
		service.regsiterVariant(int.class, "a", "b");

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", 3);
        ctx.put("b", 4);

        Assert.assertEquals(7, service.eval(ctx, "a + b"));
        Assert.assertEquals(true, service.eval(ctx, "a < b"));
	}
}
