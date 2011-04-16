package com.alibaba.simpleEL;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

import javax.management.ObjectName;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.compile.JdkCompiler;
import com.alibaba.simpleEL.eval.DefaultExpressEvalService;

public class EvalServiceTest extends TestCase {
	public void test_0 () throws Exception {
		DefaultExpressEvalService service = new DefaultExpressEvalService();
		service.regsiterVariant(int.class, "a", "b", "c");
		
		TestManager manager = new TestManager(service);
		
		ManagementFactory.getPlatformMBeanServer().registerMBean(manager, new ObjectName("alibaba.simpleEL:type=TestManager"));
		
		Thread.sleep(1000 * 1000 * 10);
	}
	
	public static class TestManager implements TestManagerMBean {
		private final DefaultExpressEvalService service;
		private int evalCount = 0;

		public TestManager(DefaultExpressEvalService service) {
			super();
			this.service = service;
		}
		
		public void eval() {
			Map<String, Object> context = new HashMap<String, Object>();
			context.put("a", 10000);
			context.put("b", 20000);
			context.put("c", 30000);
			
			for (int i = 0; i < 1000; ++i) {
				String text = "@a + " + i;
				Object result = service.eval(context, text);
				evalCount++;
				Assert.assertEquals(10000 + i, ((Number) result).intValue());
			}
		}
		
		public void clearClache() {
			service.getCacheProvider().clear();
			JdkCompiler compiler = (JdkCompiler) service.getCompiler();
			compiler.resetClassLoader();
		}
		
		public int getEvalCount() {
			return evalCount;
		}

		@Override
		public void evalAndClear100() {
			for (int i = 0; i < 100; ++i) {
				this.eval();
				this.clearClache();
			}
		}
	}
	
	public static interface TestManagerMBean {
		void eval();
		
		void clearClache();
		
		void evalAndClear100();
		
		int getEvalCount();
	}
}
