package com.alibaba.simpleEL.bvt;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.preprocess.ExpressUtils;
import com.alibaba.simpleEL.preprocess.VariantResolver;

public class ExpressUtilsTest extends TestCase {
	public void test_0 () throws Exception {
		String text = "@_last_loginid_2 * 10";
		String result = ExpressUtils.resolve(text, new VariantResolver() {

			@Override
			public String resolve(String variant) {
				return "ctx.get(\"" + variant + "\")";
			}
			
		});
		
		Assert.assertEquals("ctx.get(\"_last_loginid_2\") * 10", result);
	}
}
