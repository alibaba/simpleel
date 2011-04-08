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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;

import com.alibaba.simpleEL.Expr;
import com.alibaba.simpleEL.JavaSourceCompiler;
import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.gen.ExprImpl;

/**
 * @author shaojin.wensj
 *
 */
public class JdkCompiler implements JavaSourceCompiler {
	private final AtomicLong compileCount = new AtomicLong();
	private final AtomicLong compileTimeNano = new AtomicLong();

	private List<String> options = new ArrayList<String>();

	public JdkCompiler() {
		options.add("-target");
		options.add("1.5");
	}

	public synchronized Class<? extends Expr> compile(JavaSource javaSource) {
		compileCount.incrementAndGet();
		long startTimeMillis = System.nanoTime();

		try {
			final DiagnosticCollector<JavaFileObject> errs = new DiagnosticCollector<JavaFileObject>();

			ClassLoader contextClassLoader = ExprImpl.class.getClassLoader();

			CharSequenceCompiler<Expr> compiler = new CharSequenceCompiler<Expr>(contextClassLoader, options);

			String qName = javaSource.getPackageName() + "." + javaSource.getClassName();
			
			return (Class<? extends Expr>) compiler.compile(qName, javaSource.getSource(), errs);
		} catch (CharSequenceCompilerException ex) {
			DiagnosticCollector<JavaFileObject> diagnostics = ex.getDiagnostics();

			throw new CompileExprException("compile error, source : \n" + javaSource + ", "
					+ diagnostics.getDiagnostics(), ex);
		} catch (Exception ex) {
			throw new CompileExprException("compile error, source : \n" + javaSource, ex);
		} finally {
			// 编译时间统计
			compileTimeNano.addAndGet(System.nanoTime() - startTimeMillis);
		}
	}
}
