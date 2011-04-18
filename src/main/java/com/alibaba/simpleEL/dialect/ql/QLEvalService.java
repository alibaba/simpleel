package com.alibaba.simpleEL.dialect.ql;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.simpleEL.Expr;
import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.JavaSourceCompiler;
import com.alibaba.simpleEL.compile.JdkCompiler;

public class QLEvalService {
	private JavaSourceCompiler compiler = new JdkCompiler();
	private QLPreprocessor preprocessor = new QLPreprocessor();

	public JavaSourceCompiler getCompiler() {
		return compiler;
	}

	public void setCompiler(JavaSourceCompiler compiler) {
		this.compiler = compiler;
	}

	public QLPreprocessor getPreprocessor() {
		return preprocessor;
	}

	public void setPreprocessor(QLPreprocessor preprocessor) {
		this.preprocessor = preprocessor;
	}

	public <T> void select(Class<T> clazz, Collection<T> srcCollection, Collection<T> destCollection, String ql, Map<String, Object> context) throws Exception {
		Map<String, Object> compileContext = new HashMap<String, Object>();
		compileContext.put("class", clazz);

		JavaSource source = preprocessor.handle(compileContext, ql);

		System.out.println(source.getSource());

		Class<? extends Expr> exprClass = compiler.compile(source);

		Expr compiledExpr = exprClass.newInstance();

		Map<String, Object> evalContext = new HashMap<String, Object>();
		evalContext.put("_src_", srcCollection);
		evalContext.put("_dest_", destCollection);
		evalContext.putAll(context);

		compiledExpr.eval(evalContext);
	}
}
