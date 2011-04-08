package com.alibaba.simpleEL.bvt;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.eval.DefaultExpressEvalService;

public class Case1 extends TestCase {
	public void test_0 () throws Exception {
		DefaultExpressEvalService service = new DefaultExpressEvalService();
		service.regsiterVariant(int.class, "a", "b");
		service.setAllowMultiStatement(true);

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", 3);
        ctx.put("b", 4);

        Assert.assertEquals(1, service.eval(ctx, "if (@a > @b) { return @a - @b; } else {return @b - @a; }"));
	}
}


