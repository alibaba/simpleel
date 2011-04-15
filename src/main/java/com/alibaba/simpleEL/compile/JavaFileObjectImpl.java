/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.simpleEL.compile;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * @author wenshao<szujobs@hotmail.com>
 *
 */
public final class JavaFileObjectImpl extends SimpleJavaFileObject {
	// If kind == CLASS, this stores byte code from openOutputStream
	private ByteArrayOutputStream byteCode = new ByteArrayOutputStream();

	// if kind == SOURCE, this contains the source text
	private final CharSequence source;

	public JavaFileObjectImpl(final String baseName, final CharSequence source) {
		super(SimpleELCompiler.toURI(baseName + SimpleELCompiler.JAVA_EXTENSION), Kind.SOURCE);
		this.source = source;
	}

	JavaFileObjectImpl(final String name, final Kind kind) {
		super(SimpleELCompiler.toURI(name), kind);
		source = null;
	}
	
	 public JavaFileObjectImpl(URI uri, Kind kind) {
		 super (uri, kind);
		 source = null;
	 }

	@Override
	public CharSequence getCharContent(final boolean ignoreEncodingErrors) throws UnsupportedOperationException {
		if (source == null) {
			throw new UnsupportedOperationException();
		}
		
		return source;
	}

	@Override
	public InputStream openInputStream() {
		return new ByteArrayInputStream(getByteCode());
	}

	@Override
	public OutputStream openOutputStream() {
		return byteCode;
	}

	byte[] getByteCode() {
		return byteCode.toByteArray();
	}
}