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
package com.alibaba.simpleEL.eval;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.alibaba.simpleEL.Expr;
import com.alibaba.simpleEL.ExprCacheProvider;

/**
 * @author shaojin.wensj
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
