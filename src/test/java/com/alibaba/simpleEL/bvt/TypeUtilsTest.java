package com.alibaba.simpleEL.bvt;

import junit.framework.Assert;

import org.junit.Test;

import com.alibaba.simpleEL.TypeUtils;

public class TypeUtilsTest {

    @Test
    public void test_0() throws Exception {
        Assert.assertEquals(null, TypeUtils._bool(null));
        Assert.assertEquals(null, TypeUtils._byte(null));
        Assert.assertEquals(null, TypeUtils._short(null));
        Assert.assertEquals(null, TypeUtils._int(null));
        Assert.assertEquals(null, TypeUtils._long(null));
        Assert.assertEquals(null, TypeUtils._float(null));
        Assert.assertEquals(null, TypeUtils._double(null));

        Assert.assertEquals(Boolean.TRUE, TypeUtils._bool(Boolean.TRUE));
        Assert.assertEquals(Boolean.TRUE, TypeUtils._bool(1));
        Assert.assertEquals("1", TypeUtils._string((byte) 1));
        Assert.assertEquals(new Byte((byte) 1), TypeUtils._byte(1));
        Assert.assertEquals(new Short((short) 1), TypeUtils._short(1));
        Assert.assertEquals(new Short((short) 1), TypeUtils._short((short) 1));
    }

    @Test(expected = Exception.class)
    public void test_error() throws Exception {
        TypeUtils._bool(new Object());
    }
}
