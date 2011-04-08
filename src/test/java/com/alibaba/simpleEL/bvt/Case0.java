package com.alibaba.simpleEL.bvt;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.eval.DefaultExpressEvalService;
import com.alibaba.simpleEL.preprocess.DefaultVariantResolver.Type;

public class Case0 extends TestCase {
	public void test_0 () throws Exception {
		DefaultExpressEvalService service = new DefaultExpressEvalService();
		service.regsiterVariant(Type.Integer, "a", "b");

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", 3);
        ctx.put("b", 4);

        Assert.assertEquals(7, service.eval(ctx, "@a + @b"));
        Assert.assertEquals(true, service.eval(ctx, "@a < @b"));
	}
}
