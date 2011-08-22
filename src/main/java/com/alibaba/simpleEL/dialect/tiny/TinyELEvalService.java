package com.alibaba.simpleEL.dialect.tiny;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.alibaba.simpleEL.eval.DefaultExpressEvalService;

public class TinyELEvalService extends DefaultExpressEvalService {
	public TinyELEvalService() {
		super (new TinyELPreprocessor());
	}
	
	public TinyELPreprocessor getTinyPreprocessor() {
		return (TinyELPreprocessor)preprocessor;
	}
	
	@Override
    public TinyELPreprocessor getPreprocessor() {
		return (TinyELPreprocessor) preprocessor;
	}
	
	public void registerFunction(Method method) {
		registerFunction(method.getName(), method);
	}
	
	public void registerFunction(String methodName, Method method) {
		if (method == null) {
			throw new IllegalArgumentException("method is null");
		}
		
		if (!Modifier.isStatic(method.getModifiers())) {
			throw new IllegalArgumentException("method is not static : " + method);
		}
		
		getTinyPreprocessor().getFunctions().put(methodName, method);
	}
}
