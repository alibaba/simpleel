package com.alibaba.simpleEL.bvt;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.compile.JdkCompiler;
import com.alibaba.simpleEL.preprocess.TemplatePreProcessor;

public class TemplatePreProcessorTest extends TestCase {
	public void test_0 () throws Exception {
		TemplatePreProcessor preProcessor = new TemplatePreProcessor();
		
		Map<String, Object> context = new HashMap<String, Object>();
		
		JavaSource source = preProcessor.handle(context, "3");
		System.out.println(source);
		
		JdkCompiler compiler = new JdkCompiler();
		
		compiler.compile(source);
	}
}
