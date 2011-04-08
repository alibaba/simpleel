package com.alibaba.simpleEL;

import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.Bindings;
import javax.script.ScriptContext;

public class ScriptContextMap implements ScriptContext, Map<String, Object> {
	private final ScriptContext context;
	
	public ScriptContextMap(ScriptContext context) {
		this.context = context;
	}

	public ScriptContext getContext() {
		return context;
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(Object key) {
		return context.getAttribute((String) key);
	}

	@Override
	public Object put(String key, Object value) {
		context.setAttribute(key, value, ScriptContext.ENGINE_SCOPE);
		return null;
	}

	@Override
	public Object remove(Object key) {
		context.removeAttribute((String) key, ScriptContext.ENGINE_SCOPE);
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		for (Map.Entry<? extends String, ? extends Object> entry : m.entrySet()) {
			context.setAttribute(entry.getKey(), entry.getValue(), ScriptContext.ENGINE_SCOPE);
		}
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Object> values() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBindings(Bindings bindings, int scope) {
		context.setBindings(bindings, scope);
	}

	@Override
	public Bindings getBindings(int scope) {
		return context.getBindings(scope);
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		context.setAttribute(name, value, scope);
	}

	@Override
	public Object getAttribute(String name, int scope) {
		return context.getAttribute(name, scope);
	}

	@Override
	public Object removeAttribute(String name, int scope) {
		return context.removeAttribute(name, scope);
	}

	@Override
	public Object getAttribute(String name) {
		return context.getAttribute(name);
	}

	@Override
	public int getAttributesScope(String name) {
		return context.getAttributesScope(name);
	}

	@Override
	public Writer getWriter() {
		return context.getWriter();
	}

	@Override
	public Writer getErrorWriter() {
		return context.getErrorWriter();
	}

	@Override
	public void setWriter(Writer writer) {
		context.setWriter(writer);
	}

	@Override
	public void setErrorWriter(Writer writer) {
		context.setErrorWriter(writer);
	}

	@Override
	public Reader getReader() {
		return context.getReader();
	}

	@Override
	public void setReader(Reader reader) {
		context.setReader(reader);
	}

	@Override
	public List<Integer> getScopes() {
		return context.getScopes();
	}

	
}
