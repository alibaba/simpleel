package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;


public class AbsTest extends TestCase {
    public void test_0() throws Exception {
        TinyELEvalService service = new TinyELEvalService();
        service.setAllowMultiStatement(false);
        service.regsiterVariant(BigDecimal.class, "a", "b");

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", -3);
        ctx.put("b", -4);

        Assert.assertEquals(true, service.eval(ctx, "abs(a) == 3"));
    }
}
