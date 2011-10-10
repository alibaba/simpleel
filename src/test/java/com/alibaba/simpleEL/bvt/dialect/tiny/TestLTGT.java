package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.math.BigDecimal;
import java.util.Collections;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

public class TestLTGT extends TestCase {

    public void test_ltgt() throws Exception {
        TinyELEvalService service = new TinyELEvalService();

        service.regsiterVariant(BigDecimal.class, "Count");

        Assert.assertEquals(Boolean.TRUE, service.eval(Collections.<String, Object> singletonMap("Count", 2), "@Count >= 1"));
    }
}
