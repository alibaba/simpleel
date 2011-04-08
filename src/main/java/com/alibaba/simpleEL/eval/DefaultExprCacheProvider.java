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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.alibaba.simpleEL.Expr;
import com.alibaba.simpleEL.ExprCacheProvider;

/**
 * @author wenshao<szujobs@hotmail.com>
 *
 */
public class DefaultExprCacheProvider implements ExprCacheProvider {
	private ConcurrentMap<String, Expr> cache = new ConcurrentHashMap<String, Expr>();

	@Override
	public Expr get(Map<String, Object> ctx, String expr) {
		return cache.get(expr);
	}

	@Override
	public Expr putIfAbsent(Map<String, Object> ctx, String expr, Expr compiledExpr) {
		return cache.put(expr, compiledExpr);
	}

	@Override
	public void clear() {
		cache.clear();
	}
	
	@Override
	public List<Expr> getCacheExpressions() {
		return new ArrayList<Expr>(cache.values());
	}

}
