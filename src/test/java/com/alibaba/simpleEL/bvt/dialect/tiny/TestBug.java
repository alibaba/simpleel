package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestBug extends TestCase {

    public void test_bug() throws Exception {
        TinyELEvalService service = new TinyELEvalService();

        service.regsiterVariant(int.class, "a", "b");

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", 3);
        ctx.put("b", 4);

        Assert.assertEquals(false, service.eval(ctx, "a > b && b > a && a == b"));
        Assert.assertEquals(true, service.eval(ctx, "a != b"));
    }
}
