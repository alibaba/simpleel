package com.alibaba.simpleEL.dialect.ql;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.eval.DefaultExpressEvalService;

public class QLService {
	private DefaultExpressEvalService evalService = new DefaultExpressEvalService();
	private QLPreprocessor preprocessor = new QLPreprocessor();
	
	public <T> void select(Class<T> clazz, Collection<T> srcCollection, Collection<T> destCollection, String ql) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("class", clazz);
		
		JavaSource source = preprocessor.handle(context, ql);
		
		System.out.println(source.getSource());
		
		throw new ELException("TODO");
	}
}
