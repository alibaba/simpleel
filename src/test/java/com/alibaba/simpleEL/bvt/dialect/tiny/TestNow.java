package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.util.Collections;
import java.util.Date;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

public class TestNow extends TestCase {

    public void test_now() throws Exception {
        TinyELEvalService service = new TinyELEvalService();
        Date now = (Date) service.eval(Collections.<String, Object> emptyMap(), "now()");
        Assert.assertNotNull(now);
    }

    public void test_now_1() throws Exception {
        TinyELEvalService service = new TinyELEvalService();
        Long now = (Long) service.eval(Collections.<String, Object> emptyMap(), "now().getTime()");
        Assert.assertNotNull(now);
    }

    public void test_currentTimeMillis() throws Exception {
        TinyELEvalService service = new TinyELEvalService();
        Long now = (Long) service.eval(Collections.<String, Object> emptyMap(), "currentTimeMillis()");
        Assert.assertNotNull(now);
    }
    
    
}
