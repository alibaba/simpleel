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


import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

/**
 * @author shaojin.wensj
 *
 */
public class CharSequenceCompilerException extends Exception {
	private static final long serialVersionUID = 1L;

	private Set<String> classNames;
	private transient DiagnosticCollector<JavaFileObject> diagnostics;
	
	private String source;

	public CharSequenceCompilerException(String message, Set<String> qualifiedClassNames, Throwable cause, DiagnosticCollector<JavaFileObject> diagnostics) {
		super(message, cause);
		setClassNames(qualifiedClassNames);
		setDiagnostics(diagnostics);
	}

	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	public CharSequenceCompilerException(String message, Set<String> qualifiedClassNames, DiagnosticCollector<JavaFileObject> diagnostics) {
		super(message);
		setClassNames(qualifiedClassNames);
		setDiagnostics(diagnostics);
	}

	public CharSequenceCompilerException(Set<String> qualifiedClassNames, Throwable cause, DiagnosticCollector<JavaFileObject> diagnostics) {
		super(cause);
		setClassNames(qualifiedClassNames);
		setDiagnostics(diagnostics);
	}

	private void setClassNames(Set<String> qualifiedClassNames) {
		// create a new HashSet because the set passed in may not
		// be Serializable. For example, Map.keySet() returns a non-Serializable
		// set.
		classNames = new HashSet<String>(qualifiedClassNames);
	}

	private void setDiagnostics(DiagnosticCollector<JavaFileObject> diagnostics) {
		this.diagnostics = diagnostics;
	}

	/**
	 * Gets the diagnostics collected by this exception.
	 * 
	 * @return this exception's diagnostics
	 */
	public DiagnosticCollector<JavaFileObject> getDiagnostics() {
		return diagnostics;
	}
	
	/**
	 * @return The name of the classes whose compilation caused the compile
	 *         exception
	 */
	public Collection<String> getClassNames() {
		return Collections.unmodifiableSet(classNames);
	}
}
