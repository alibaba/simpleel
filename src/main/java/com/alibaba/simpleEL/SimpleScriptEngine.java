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

	public DefaultExpressEvalService getService() {
		return service;
	}

	public void setService(DefaultExpressEvalService service) {
		this.service = service;
	}

	@Override
	public Object eval(String script, ScriptContext context)
			throws ScriptException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object eval(Reader reader, ScriptContext context)
			throws ScriptException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bindings createBindings() {
		return new SimpleBindings();
	}

	@Override
	public ScriptEngineFactory getFactory() {
		// TODO Auto-generated method stub
		return null;
	}


}
