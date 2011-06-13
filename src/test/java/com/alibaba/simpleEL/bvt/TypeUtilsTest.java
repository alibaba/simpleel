package com.alibaba.simpleEL.bvt;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.TypeUtils;

public class TypeUtilsTest extends TestCase {
	public void test_0() throws Exception {
		Assert.assertEquals(null, TypeUtils._bool(null));
		Assert.assertEquals(null, TypeUtils._byte(null));
		Assert.assertEquals(null, TypeUtils._short(null));
		Assert.assertEquals(null, TypeUtils._int(null));
		Assert.assertEquals(null, TypeUtils._long(null));
		Assert.assertEquals(null, TypeUtils._float(null));
		Assert.assertEquals(null, TypeUtils._double(null));
	}
}
