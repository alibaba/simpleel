package com.alibaba.simpleEL.dialect.tiny;

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
}
