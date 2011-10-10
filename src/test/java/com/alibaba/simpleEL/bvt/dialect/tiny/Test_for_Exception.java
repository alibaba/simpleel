package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

public class Test_for_Exception extends TestCase {

    public void test_ltgt() throws Exception {
        TinyELEvalService service = new TinyELEvalService();

        service.regsiterVariant(BigDecimal.class, "Count");
        service.regsiterVariant(String.class, "URI", "Method");

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("Count", 5);
        ctx.put("URI", "/commodity/clothingDna.htm");
        ctx.put("Method", "java.net.PlainSocketImpl.socketConnect");

        String text = "(@Count > 2 && !(\"/ims/cloth_pi.htm\".equals(@URI)&&\"java.net.PlainSocketImpl.socketConnect\".equals(@Method)))"
                      + "|| (@Count > 2 && !(\"/commodity/clothingDna.htm\".equals(@URI)&&\"java.net.PlainSocketImpl.socketConnect\".equals(@Method)))";
        Assert.assertEquals(Boolean.TRUE, service.eval(ctx, text));
    }
}
