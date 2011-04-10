package com.alibaba.simpleEL;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

public class SimpleScriptEngineFactory implements ScriptEngineFactory {

	public static final java.lang.String ENGINE_NAME = "SimpleScriptEngine";

	public static final java.lang.String ENGINE_VERSION = "1.0";

	public static final java.lang.String ENGINE_SHORT_NAME = "SimpleScriptEngine";

	public static final java.lang.String LANGUAGE = "SimpleScript";

	@Override
	public String getEngineName() {
		return ENGINE_NAME;
	}

	@Override
	public String getEngineVersion() {
		return ENGINE_VERSION;
	}

	@Override
	public List<String> getExtensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getMimeTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLanguageName() {
		return LANGUAGE;
	}

	@Override
	public String getLanguageVersion() {
		return null;
	}

	@Override
	public Object getParameter(String key) {
		return null;
	}

	@Override
	public String getMethodCallSyntax(String obj, String m, String... args) {
		return null;
	}

	@Override
	public String getOutputStatement(String toDisplay) {
		return null;
	}

	@Override
	public String getProgram(String... statements) {
		return null;
	}

	@Override
	public ScriptEngine getScriptEngine() {
		return new SimpleScriptEngine(this);
	}

}
