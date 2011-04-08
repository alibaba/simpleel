package com.alibaba.simpleEL;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TypeUtils {
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
