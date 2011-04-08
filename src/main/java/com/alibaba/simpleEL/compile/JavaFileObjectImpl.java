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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * @author shaojin.wensj
 *
 */
public final class JavaFileObjectImpl extends SimpleJavaFileObject {
	// If kind == CLASS, this stores byte code from openOutputStream
	private ByteArrayOutputStream byteCode;

	// if kind == SOURCE, this contains the source text
	private final CharSequence source;

	public JavaFileObjectImpl(final String baseName, final CharSequence source) {
		super(CharSequenceCompiler.toURI(baseName + CharSequenceCompiler.JAVA_EXTENSION), Kind.SOURCE);
		this.source = source;
	}

	JavaFileObjectImpl(final String name, final Kind kind) {
		super(CharSequenceCompiler.toURI(name), kind);
		source = null;
	}
	
	 public JavaFileObjectImpl(URI uri, Kind kind) {
		 super (uri, kind);
		 source = null;
	 }

	@Override
	public CharSequence getCharContent(final boolean ignoreEncodingErrors) throws UnsupportedOperationException {
		if (source == null)
			throw new UnsupportedOperationException("getCharContent()");
		return source;
	}

	@Override
	public InputStream openInputStream() {
		return new ByteArrayInputStream(getByteCode());
	}

	@Override
	public OutputStream openOutputStream() {
		byteCode = new ByteArrayOutputStream();
		return byteCode;
	}

	byte[] getByteCode() {
		return byteCode.toByteArray();
	}
}