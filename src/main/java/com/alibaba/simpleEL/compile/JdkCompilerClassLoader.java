package com.alibaba.simpleEL.compile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaFileObject;

public final class JdkCompilerClassLoader extends ClassLoader {
	private final Map<String, JavaFileObject> classes = new HashMap<String, JavaFileObject>();

	public JdkCompilerClassLoader(ClassLoader parentClassLoader) {
		super(parentClassLoader);
	}

	public Collection<JavaFileObject> files() {
		return Collections.unmodifiableCollection(classes.values());
	}

	@Override
	protected synchronized Class<?> findClass(String qualifiedClassName) throws ClassNotFoundException {
		JavaFileObject file = classes.get(qualifiedClassName);
		if (file != null) {
			byte[] bytes = ((JavaFileObjectImpl) file).getByteCode();
			return defineClass(qualifiedClassName, bytes, 0, bytes.length);
		}
		
		try {
			return Class.forName(qualifiedClassName);
		} catch (ClassNotFoundException nf) {
			// Ignore and fall through
		}
		
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(qualifiedClassName);
		} catch (ClassNotFoundException nf) {
			// Ignore and fall through
		}
		
		return super.findClass(qualifiedClassName);
	}

	public void add(String qualifiedClassName, final JavaFileObject javaFile) {
		classes.put(qualifiedClassName, javaFile);
	}

	@Override
	protected synchronized Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
		return super.loadClass(name, resolve);
	}

	@Override
	public InputStream getResourceAsStream(final String name) {
		if (name.endsWith(".class")) {
			String qualifiedClassName = name.substring(0, name.length() - ".class".length()).replace('/', '.');
			JavaFileObjectImpl file = (JavaFileObjectImpl) classes.get(qualifiedClassName);
			
			if (file != null) {
				return new ByteArrayInputStream(file.getByteCode());
			}
		}
		
		return super.getResourceAsStream(name);
	}
}