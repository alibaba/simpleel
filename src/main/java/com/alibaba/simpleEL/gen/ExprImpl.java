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
package com.alibaba.simpleEL.gen;

import com.alibaba.simpleEL.Expr;

/**
 * @author shaojin.wensj
 * 
 */
public abstract class ExprImpl implements Expr {

	public static int _int(Object val) {
		return ((Number) val).intValue();
	}

	public static long _long(Object val) {
		return ((Number) val).longValue();
	}

	public static double _double(Object val) {
		return ((Number) val).doubleValue();
	}

	public static double _float(Object val) {
		return ((Number) val).floatValue();
	}

	public static boolean _bool(Object val) {
		return (Boolean) val;
	}

	public static String _string(Object val) {
		Object value = val;

		if (value == null) {
			return null;
		}

		return value.toString();
	}
}
