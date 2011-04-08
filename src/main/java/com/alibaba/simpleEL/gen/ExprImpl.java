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
package com.alibaba.simpleEL.gen;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.alibaba.simpleEL.Expr;

/**
 * @author wenshao<szujobs@hotmail.com>
 * 
 */
public abstract class ExprImpl implements Expr {
	public static boolean _bool(Object val) {
		if (val == null) {
			return false;
		}
		
		return (Boolean) val;
	}

	public static String _string(Object val) {
		Object value = val;

		if (value == null) {
			return null;
		}

		return value.toString();
	}
	
	public static int _byte(Object val) {
		if (val == null) {
			return 0;
		}
		
		return ((Number) val).byteValue();
	}
	
	public static int _short(Object val) {
		if (val == null) {
			return 0;
		}
		
		return ((Number) val).shortValue();
	}
	
	public static int _int(Object val) {
		if (val == null) {
			return 0;
		}
		
		return ((Number) val).intValue();
	}

	public static long _long(Object val) {
		if (val == null) {
			return 0L;
		}
		
		return ((Number) val).longValue();
	}
	

	public static double _float(Object val) {
		if (val == null) {
			return 0F;
		}
		
		return ((Number) val).floatValue();
	}

	public static double _double(Object val) {
		if (val == null) {
			return 0D;
		}
		
		return ((Number) val).doubleValue();
	}

	public static BigInteger _bigInt(Object val) {
		if (val == null) {
			return null;
		}
		
		if (val instanceof BigInteger) {
			return (BigInteger) val; 
		}
		
		if (val instanceof String) {
			return new BigInteger((String) val); 
		}
		
		return BigInteger.valueOf(((Number) val).longValue());
	}

	public static BigDecimal _decimal(Object val) {
		if (val == null) {
			return null;
		}
		
		if (val instanceof BigDecimal) {
			return (BigDecimal) val; 
		}
		
		if (val instanceof String) {
			return new BigDecimal((String) val); 
		}
		
		return BigDecimal.valueOf(((Number) val).longValue());
	}
}
