package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

public class TinyELEvalServiceTest extends TestCase {
	public void test_0() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(int.class, "a", "b");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("a", 3);
		ctx.put("b", 4);

		Assert.assertEquals(7, service.eval(ctx, "a + b"));
		Assert.assertEquals(true, service.eval(ctx, "a < b"));
	}

	public void test_1() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(int.class, "a", "b");
		service.registerFunction("max", Math.class.getMethod("max", new Class<?>[] { int.class, int.class }));

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("a", 3);
		ctx.put("b", 4);

		Assert.assertEquals(7, service.eval(ctx, "a + b"));
		Assert.assertEquals(true, service.eval(ctx, "a < b"));
		Assert.assertEquals(4, service.eval(ctx, "max(a, b)"));
	}

	public void test_2() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(String.class, "name");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("name", "abc");

		Assert.assertEquals(3, service.eval(ctx, "name.length()"));
	}

	public void test_3() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(String.class, "name");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("name", "abc");

		Assert.assertEquals(5, service.eval(ctx, "('aa' + name).length()"));
	}

	public void test_4() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(String.class, "name");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("name", "abc");

		Assert.assertEquals(5, service.eval(ctx, "(\"aa\" + name).length()"));
	}

	public void test_5() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(int.class, "a", "b");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("a", 3);
		ctx.put("b", 4);

		Assert.assertEquals(4, service.eval(ctx, "java.lang.Math.max(a, b)"));
	}

	public void test_6() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(int.class, "a", "b");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("a", 3);
		ctx.put("b", 4);

		Assert.assertEquals(4, service.eval(ctx, "Math.max(a, b)"));
	}

	public void test_7() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(int.class, "a", "b");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("a", 3);
		ctx.put("b", 4);

		Assert.assertEquals(true, service.eval(ctx, "Math.max(a, b) > a == true"));
	}

	public void test_8() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(int.class, "a", "b");
		service.regsiterVariant(int[].class, "c");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("a", 3);
		ctx.put("b", 4);
		ctx.put("c", new int[] { 1, 2 });

		Assert.assertEquals(true, service.eval(ctx, "Math.max(c[0], c[1]) == 2"));
	}

	public void test_9() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(long.class, "millis");

		long millis = System.currentTimeMillis();
		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("millis", millis);

		Assert.assertEquals(new java.util.Date(millis), service.eval(ctx, "new java.util.Date(millis)"));
	}

	public void test_10() throws Exception {
		TinyELEvalService service = new TinyELEvalService();

		service.regsiterVariant(int.class, "a", "b");
		service.regsiterVariant(int[].class, "c");

		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("a", 3);
		ctx.put("b", 4);
		ctx.put("c", new int[] { 1, 2 });

		Assert.assertEquals(4, service.eval(ctx, "Math.max(c[0], c[1]) + 2"));
	}
	
	public void test_11() throws Exception {
		TinyELEvalService service = new TinyELEvalService();
		
		service.regsiterVariant(int.class, "a", "b");
		service.regsiterVariant(int[].class, "c");
		
		Map<String, Object> ctx = new HashMap<String, Object>();
		ctx.put("a", 3);
		ctx.put("b", 4);
		ctx.put("c", new int[] { 1, 2 });
		
		Assert.assertEquals(4, service.eval(ctx, "Math.max(c[0], c[1]) + 2"));
	}
}
