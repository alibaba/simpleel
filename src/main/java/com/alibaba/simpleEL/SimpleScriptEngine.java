package com.alibaba.simpleEL;

import java.io.Reader;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import com.alibaba.simpleEL.eval.DefaultExpressEvalService;

public class SimpleScriptEngine extends AbstractScriptEngine {
	private DefaultExpressEvalService service = new DefaultExpressEvalService();
	private SimpleScriptEngineFactory factory;
	
	public SimpleScriptEngine() {
		
	}
	
	public SimpleScriptEngine(DefaultExpressEvalService service) {
		this.service = service;
	}
	
	public SimpleScriptEngine(SimpleScriptEngineFactory factory) {
		this.factory = factory;
	}

	public DefaultExpressEvalService getService() {
		return service;
	}

	public void setService(DefaultExpressEvalService service) {
		this.service = service;
	}

	@Override
	public Object eval(String script, ScriptContext context)
			throws ScriptException {
		Bindings bindings = context.getBindings(ScriptContext.ENGINE_SCOPE);
		
		return service.eval(bindings, script);
	}

	@Override
	public Object eval(Reader reader, ScriptContext context)
			throws ScriptException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Bindings createBindings() {
		return new SimpleBindings();
	}

	@Override
	public ScriptEngineFactory getFactory() {
		return factory;
	}
}
