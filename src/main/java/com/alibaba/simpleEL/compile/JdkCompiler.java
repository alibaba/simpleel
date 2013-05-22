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
import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.JavaSourceCompiler;

/**
 * @author wenshao<szujobs@hotmail.com>
 */
public class JdkCompiler implements JavaSourceCompiler, JdkCompilerMBean {

    private final AtomicLong       compileCount      = new AtomicLong();
    private final AtomicLong       compileTimeNano   = new AtomicLong();

    private final List<String>     options           = new ArrayList<String>();

    private JdkCompilerClassLoader classLoader;
    private ClassLoader            parentClassLoader = Thread.currentThread().getContextClassLoader();

    public JdkCompiler(){
        options.add("-target");
        options.add("1.6");

        try {
            parentClassLoader.loadClass("com.alibaba.simpleEL.compile.JdkCompiler");
        } catch (Exception e) {
            parentClassLoader = JdkCompiler.class.getClassLoader();
        }

        classLoader = new JdkCompilerClassLoader(parentClassLoader);
    }

    public ClassLoader getParentClassLoader() {
        return parentClassLoader;
    }

    public void setParentClassLoader(ClassLoader parentClassLoader) {
        this.parentClassLoader = parentClassLoader;
    }

    public void setClassLoader(JdkCompilerClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public JdkCompilerClassLoader getClassLoader() {
        return this.classLoader;
    }

    public void resetClassLoader() {
        classLoader.clearCache();
        classLoader = new JdkCompilerClassLoader(parentClassLoader);
    }

    public List<String> getOptions() {
        return this.options;
    }

    public long getCompileCount() {
        return compileCount.get();
    }

    public long getCompileTimeNano() {
        return compileTimeNano.get();
    }

    public synchronized Class<? extends Expr> compile(JavaSource javaSource) {
        return compileEx(javaSource).getExprClass();
    }

    @Override
    public CompileResult compileEx(JavaSource javaSource) {
        compileCount.incrementAndGet();
        long startTimeMillis = System.nanoTime();

        try {
            final DiagnosticCollector<JavaFileObject> errs = new DiagnosticCollector<JavaFileObject>();

            JdkCompileTask<Expr> compileTask = new JdkCompileTask<Expr>(classLoader, options);

            String fullName = javaSource.getPackageName() + "." + javaSource.getClassName();

            CompileResult result = compileTask.compile(fullName, javaSource.getSource(), errs);

            return result;
        } catch (JdkCompileException ex) {
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
