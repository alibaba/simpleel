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
package com.alibaba.simpleEL;

import java.util.List;
import java.util.Map;

/**
 * @author shaojin.wensj
 *
 */
public interface ExprCacheProvider {
	Expr get(Map<String, Object> ctx, String expr);
	
	Expr putIfAbsent(Map<String, Object> ctx, String expr, Expr compiledExpr);
	
	void clear();
	
	List<? extends Expr> getCacheExpressions();
}
