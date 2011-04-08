package com.alibaba.simpleEL.bvt;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.eval.DefaultExpressEvalService;
import com.alibaba.simpleEL.preprocess.DefaultVariantResolver;
import com.alibaba.simpleEL.preprocess.DefaultVariantResolver.Type;

public class Case0 extends TestCase {
	public void test_0 () throws Exception {
		DefaultExpressEvalService service = new DefaultExpressEvalService();
		DefaultVariantResolver variantResolver = (DefaultVariantResolver) service.getTemplatePreprocessor().getVariantResolver();
		variantResolver.registerVariant("a", Type.Integer);
		variantResolver.registerVariant("b", Type.Integer);

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", 3);
        ctx.put("b", 4);

        Assert.assertEquals(7, service.eval(ctx, "@a + @b"));

     
	}
}
