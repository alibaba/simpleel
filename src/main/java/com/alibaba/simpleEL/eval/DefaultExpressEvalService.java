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
package com.alibaba.simpleEL.eval;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.management.JMException;
import javax.management.openmbean.TabularData;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.Expr;
import com.alibaba.simpleEL.ExprCacheProvider;
import com.alibaba.simpleEL.ExpressEvalService;
import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.JavaSourceCompiler;
import com.alibaba.simpleEL.Preprocessor;
import com.alibaba.simpleEL.compile.JdkCompiler;
import com.alibaba.simpleEL.preprocess.DefaultVariantResolver;
import com.alibaba.simpleEL.preprocess.TemplatePreProcessor;

/**
 * @author wenshao<szujobs@hotmail.com>
 * 
 */
public class DefaultExpressEvalService implements ExpressEvalService,
		DefaultExpressEvalServiceMBean {
	private Preprocessor preprocessor = new TemplatePreProcessor();
	private JavaSourceCompiler compiler = new JdkCompiler();
	private ExprCacheProvider cacheProvider = new DefaultExprCacheProvider();

	// 计数器
	private boolean profileEnable = false;
	private final AtomicLong evalCount = new AtomicLong();
	private final AtomicLong evalNano = new AtomicLong();
	private final AtomicLong evalErrorCount = new AtomicLong();

	public boolean isProfileEnable() {
		return profileEnable;
	}

	public void setProfileEnable(boolean profileEnable) {
		this.profileEnable = profileEnable;
	}

	public ExprCacheProvider getCacheProvider() {
		return cacheProvider;
	}

	public void setCacheProvider(ExprCacheProvider cacheProvider) {
		this.cacheProvider = cacheProvider;
	}
	
	public void regsiterVariant(Class<?> clazz, String... variants) {
		DefaultVariantResolver variantResolver = (DefaultVariantResolver) getTemplatePreprocessor().getVariantResolver();
		variantResolver.registerVariant(clazz, variants);
	}
	
	public void setAllowMultiStatement(boolean value) {
		this.getTemplatePreprocessor().setAllowMultiStatement(value);
	}

	public JavaSourceCompiler getCompiler() {
		return compiler;
	}

	public void setCompiler(JavaSourceCompiler compiler) {
		this.compiler = compiler;
	}

	@Override
	public Preprocessor getPreprocessor() {
		return preprocessor;
	}
	
	public TemplatePreProcessor getTemplatePreprocessor() {
		return (TemplatePreProcessor)preprocessor;
	}

	public void setPreprocessor(Preprocessor preprocessor) {
		this.preprocessor = preprocessor;
	}

	@Override
	public Object eval(Map<String, Object> ctx, String expr) throws ELException {
		if (profileEnable) {
			return evalProfile(ctx, expr);
		} else {
			return evalNoProfile(ctx, expr);
		}
	}

	private Object evalNoProfile(Map<String, Object> ctx, String expr)
			throws ELException {
		try {
			Expr compiledExpr = getExpr(ctx, expr);
			return compiledExpr.eval(ctx);
		} catch (ELException ex) {
			evalErrorCount.incrementAndGet();
			throw ex;
		} catch (Throwable ex) {
			evalErrorCount.incrementAndGet();
			throw new ELException("eval error, expr : " + expr, ex);
		}
	}

	private Object evalProfile(Map<String, Object> ctx, String expr)
			throws ELException {
		long startNano = System.nanoTime();
		try {
			evalCount.incrementAndGet();
			Expr compiledExpr = getExpr(ctx, expr);

			return compiledExpr.eval(ctx);
		} catch (ELException ex) {
			evalErrorCount.incrementAndGet();
			throw ex;
		} catch (Throwable ex) {
			evalErrorCount.incrementAndGet();
			throw new ELException("eval error, expr : " + expr, ex);
		} finally {
			long nanoDelta = System.nanoTime() - startNano;
			evalNano.addAndGet(nanoDelta);
		}
	}

	public Expr getExpr(Map<String, Object> ctx, String expr)
			throws InstantiationException, IllegalAccessException {
		Expr cachedExpr = null;

		if (cacheProvider != null) {
			cachedExpr = cacheProvider.get(ctx, expr);
		}

		if (cachedExpr != null) {
			return cachedExpr;
		}

		JavaSource javaSource = preprocessor.handle(ctx, expr);

		Class<? extends Expr> exprClass = compiler.compile(javaSource);

		Expr compiledExpr = exprClass.newInstance();

		if (cacheProvider != null) {
			if (profileEnable) {
				cacheProvider.putIfAbsent(ctx, expr, new CompiledExprWrapper(
						expr, compiledExpr));
			} else {
				cacheProvider.putIfAbsent(ctx, expr, compiledExpr);
			}

			cachedExpr = cacheProvider.get(ctx, expr);

			return cachedExpr;
		}

		return compiledExpr;
	}

	@Override
	public boolean reset() {
		if (cacheProvider == null) {
			return false;
		}

		cacheProvider.clear();
		
		if (compiler != null) {
			compiler.resetClassLoader();
		}
		
		return true;
	}

	@Override
	public long getEvalCount() {
		return evalCount.get();
	}

	@Override
	public long getEvalErrorCount() {
		return evalErrorCount.get();
	}

	@SuppressWarnings({ "unchecked" })
	public TabularData getCacheExpressions() throws JMException {
		if (cacheProvider == null) {
			return null;
		}

		if (!profileEnable) {
			return null;
		}

		List<CompiledExprWrapper> list = (List<CompiledExprWrapper>) cacheProvider
				.getCacheExpressions();

		return CompiledExprWrapper.toTabularData(list);
	}
}
