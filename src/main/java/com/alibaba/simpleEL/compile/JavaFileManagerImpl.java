/**
 * Project: simple-el
 * 
 * File Created at 2010-12-2
 * $Id$
 * 
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.alibaba.simpleEL.compile;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;

/**
 * @author shaojin.wensj
 *
 */
final class JavaFileManagerImpl extends ForwardingJavaFileManager<JavaFileManager> {
	private final ClassLoaderImpl classLoader;

	private final Map<URI, JavaFileObject> fileObjects = new HashMap<URI, JavaFileObject>();

	public JavaFileManagerImpl(JavaFileManager fileManager, ClassLoaderImpl classLoader) {
		super(fileManager);
		this.classLoader = classLoader;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	@Override
	public FileObject getFileForInput(Location location, String packageName, String relativeName) throws IOException {
		FileObject o = fileObjects.get(uri(location, packageName, relativeName));
		if (o != null)
			return o;
		return super.getFileForInput(location, packageName, relativeName);
	}

	public void putFileForInput(StandardLocation location, String packageName, String relativeName, JavaFileObject file) {
		fileObjects.put(uri(location, packageName, relativeName), file);
	}

	private URI uri(Location location, String packageName, String relativeName) {
		return CharSequenceCompiler.toURI(location.getName() + '/' + packageName + '/' + relativeName);
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String qualifiedName, Kind kind, FileObject outputFile)
			throws IOException {
		JavaFileObject file = new JavaFileObjectImpl(qualifiedName, kind);
		classLoader.add(qualifiedName, file);
		return file;
	}

	@Override
	public ClassLoader getClassLoader(JavaFileManager.Location location) {
		return classLoader;
	}

	@Override
	public String inferBinaryName(Location loc, JavaFileObject file) {
		String result;
		// For our JavaFileImpl instances, return the file's name, else
		// simply run the default implementation
		if (file instanceof JavaFileObjectImpl)
			result = file.getName();
		else
			result = super.inferBinaryName(loc, file);
		return result;
	}

	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName, Set<Kind> kinds, boolean recurse)
			throws IOException {
		Iterable<JavaFileObject> result = super.list(location, packageName, kinds, recurse);

		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		List<URL> urlList = new ArrayList<URL>();
		Enumeration<URL> e = contextClassLoader.getResources("com");
		while (e.hasMoreElements()) {
			urlList.add(e.nextElement());
		}

		ArrayList<JavaFileObject> files = new ArrayList<JavaFileObject>();

		if (location == StandardLocation.CLASS_PATH && kinds.contains(JavaFileObject.Kind.CLASS)) {
			for (JavaFileObject file : fileObjects.values()) {
				if (file.getKind() == Kind.CLASS && file.getName().startsWith(packageName)) {
					files.add(file);
				}
			}

			files.addAll(classLoader.files());
		} else if (location == StandardLocation.SOURCE_PATH && kinds.contains(JavaFileObject.Kind.SOURCE)) {
			for (JavaFileObject file : fileObjects.values()) {
				if (file.getKind() == Kind.SOURCE && file.getName().startsWith(packageName)) {
					files.add(file);
				}
			}
		}

		for (JavaFileObject file : result) {
			files.add(file);
		}

		return files;
	}
}