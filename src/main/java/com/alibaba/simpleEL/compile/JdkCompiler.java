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
 * @author wenshao<szujobs@hotmail.com>
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
