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

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.eval.DefaultExpressEvalService;
import com.alibaba.simpleEL.preprocess.TemplatePreProcessor;
import com.alibaba.simpleEL.preprocess.VariantResolver;

public class ExpressEvalServiceTest extends TestCase {
	public void test_0() throws Exception {
		DefaultExpressEvalService service = new DefaultExpressEvalService();
		
		TemplatePreProcessor preprocessor = (TemplatePreProcessor) service.getPreprocessor();
        preprocessor.setVariantResolver(new VariantResolver() {
            @Override
            public String resolve(String variant) {
                return "_int(ctx.get(\"" + variant + "\"))";
            }

			@Override
			public Class<?> getType(String variant) {
				// TODO Auto-generated method stub
				return null;
			}
            
        });
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("a", 3);
		context.put("b", 4);
		
		Assert.assertEquals(7, service.eval(context, "@a + @b"));
	}
}
