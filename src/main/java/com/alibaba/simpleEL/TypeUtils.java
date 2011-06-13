package com.alibaba.simpleEL;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

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

	public static Byte _byte(Object val) {
		if (val == null) {
			return null;
		}
		
		if (val instanceof Byte) {
			return (Byte) val;
		}

		return ((Number) val).byteValue();
	}

	public static Short _short(Object val) {
		if (val == null) {
			return null;
		}

		if (val instanceof Short) {
			return (Short) val;
		}

		return ((Number) val).shortValue();
	}

	public static Integer _int(Object val) {
		if (val == null) {
			return null;
		}

		if (val instanceof Integer) {
			return (Integer) val;
		}

		return ((Number) val).intValue();
	}

	public static Long _long(Object val) {
		if (val == null) {
			return null;
		}

		if (val instanceof Long) {
			return (Long) val;
		}

		return ((Number) val).longValue();
	}

	public static Float _float(Object val) {
		if (val == null) {
			return null;
		}

		if (val instanceof Float) {
			return (Float) val;
		}

		return ((Number) val).floatValue();
	}

	public static Double _double(Object val) {
		if (val == null) {
			return null;
		}
		
		if (val instanceof Double) {
			return (Double) val;
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

	public static Date _date(Object val) {
		if (val == null) {
			return null;
		}

		if (val instanceof Date) {
			return (Date) val;
		}

		if (val instanceof Number) {
			return new Date(((Number) val).longValue());
		}

		throw new ELException("");
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

	public static String getClassName(Class<?> clazz) {
		if (clazz.isArray()) {
			return getClassName(clazz.getComponentType()) + "[]";
		}

		String className = clazz.getName();
		className = className.replaceAll("\\$", "."); // inner class
		return className;
	}

	public static Object putAndGet(Map<String, Object> ctx, String name, Object value) {
		ctx.put(name, value);
		return value;
	}
}
