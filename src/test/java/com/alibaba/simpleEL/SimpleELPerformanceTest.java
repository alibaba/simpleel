package com.alibaba.simpleEL;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.alibaba.simpleEL.eval.DefaultExpressEvalService;
import com.alibaba.simpleEL.preprocess.DefaultVariantResolver.Type;

public class SimpleELPerformanceTest extends TestCase {
	public void test_perf() throws Exception {
		DefaultExpressEvalService service = new DefaultExpressEvalService();
		service.regsiterVariant(Type.Integer, "a", "b", "c");

        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", 3);
        ctx.put("b", 4);
        ctx.put("c", 5);

        
        for (int i = 0; i < 10; ++i) {
        	perf(service, ctx);
        }
	}

	private void perf(DefaultExpressEvalService service, Map<String, Object> ctx) {
		long startMillis = System.currentTimeMillis();
		
		final int COUNT = 1000 * 1000;
        for (int i = 0; i < COUNT; ++i) {
        	service.eval(ctx, "(@a + @b) * @c");
        }
        
        long millis = System.currentTimeMillis() - startMillis;
        
        System.out.println("time : " + NumberFormat.getInstance().format(millis));
	}
}
