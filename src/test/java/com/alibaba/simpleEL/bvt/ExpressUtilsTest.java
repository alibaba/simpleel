/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

			@Override
			public Class<?> getType(String variant) {
				return null;
			}
			
		});
		
		Assert.assertEquals("ctx.get(\"_last_loginid_2\") * 10", result);
	}
}
