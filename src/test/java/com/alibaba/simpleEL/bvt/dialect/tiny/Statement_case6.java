package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

public class Statement_case6 extends TestCase {
	public void test_0() throws Exception {
		TinyELEvalService service = new TinyELEvalService();
		service.setAllowMultiStatement(true);
		service.regsiterVariant(int[].class, "a");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("a", new int[] {1, 2, 3, 4, 5});

		Assert.assertEquals(15, service.eval(ctx, "int sum = 0; for (int i : a) { sum += i; } return sum;"));
	}

}
