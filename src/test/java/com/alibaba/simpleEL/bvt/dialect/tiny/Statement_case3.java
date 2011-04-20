package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

public class Statement_case3 extends TestCase {
	public void test_0() throws Exception {
		TinyELEvalService service = new TinyELEvalService();
		service.setAllowMultiStatement(true);
		service.regsiterVariant(int.class, "a", "b");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("a", 3);
		ctx.put("b", 4);

		Assert.assertEquals(null, service.eval(ctx, "int temp; temp = a; a = b; b = temp; return null;"));
		
		Assert.assertEquals(4, ctx.get("a"));
		Assert.assertEquals(3, ctx.get("b"));
	}

}
