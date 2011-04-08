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
        });
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("a", 3);
		context.put("b", 4);
		
		Assert.assertEquals(7, service.eval(context, "@a + @b"));
	}
}
