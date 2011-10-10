package com.alibaba.simpleEL;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TypeUtils {

    public static Boolean _bool(Object val) {
        if (val == null) {
            return null;
        }
        
        if (val instanceof Boolean) {
            return (Boolean) val;
        }
        
        if (val instanceof Number) {
            return ((Number) val).intValue() == 1;
        }

        throw new IllegalArgumentException();
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
        
        if (val instanceof String) {
            return _date((String) val);
        }

        throw new ELException("");
    }

    public static Date _date(String text) {
        if (text == null || text.length() == 0) {
            return null;
        }

        String format;

        if (text.length() == "yyyy-MM-dd".length()) {
            format = "yyyy-MM-dd";
        } else {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        try {
            return new SimpleDateFormat(format).parse(text);
        } catch (ParseException e) {
            throw new ELException("format : " + format + ", value : " + text);
        }
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
        
        if (val instanceof Float) {
            return new BigDecimal((Float) val);
        }
        
        if (val instanceof Double) {
            return new BigDecimal((Double) val);
        }

        return BigDecimal.valueOf(((Number) val).longValue());
    }

    public static Object _sum(Object a, Object b) {
        if (a == null) {
            return b;
        }

        if (b == null) {
            return a;
        }

        if (a instanceof BigDecimal || b instanceof BigDecimal) {
            return _decimal(a).add(_decimal(b));
        }

        if (a instanceof BigInteger || b instanceof BigInteger) {
            return _bigInt(a).add(_bigInt(b));
        }

        if (a instanceof Long || b instanceof Long) {
            return _long(a) + _long(b);
        }

        if (a instanceof Integer || b instanceof Integer) {
            return _int(a) + _int(b);
        }

        if (a instanceof Short || b instanceof Short) {
            return _short(a) + _short(b);
        }

        if (a instanceof Byte || b instanceof Byte) {
            return _byte(a) + _byte(b);
        }

        throw new IllegalArgumentException();
    }
    
    public static Object _div(Object a, Object b) {
        if (a == null || b == null) {
            return null;
        }
        
        BigDecimal decimalA = _decimal(a);
        BigDecimal decimalB = _decimal(b);

        try {
            return decimalA.divide(decimalB);
        } catch (ArithmeticException ex) {
            return decimalA.divide(decimalB, 4, RoundingMode.CEILING);
        }
    }

    public static Object _div2(Object a, Object b) {
        if (a == null || b == null) {
            return null;
        }

        if (a instanceof BigDecimal || b instanceof BigDecimal) {
            return _decimal(a).divide(_decimal(b));
        }

        if (a instanceof BigInteger || b instanceof BigInteger) {
            return _bigInt(a).divide(_bigInt(b));
        }

        if (a instanceof Long || b instanceof Long) {
            return _long(a) / _long(b);
        }

        if (a instanceof Integer || b instanceof Integer) {
            return _int(a) / _int(b);
        }

        if (a instanceof Short || b instanceof Short) {
            return _short(a) / _short(b);
        }

        if (a instanceof Byte || b instanceof Byte) {
            return _byte(a) / _byte(b);
        }

        throw new IllegalArgumentException();
    }
    
    public static boolean _gt(Object a, Object b) {
        if (a == null) {
            return false;
        }
        
        if (b == null) {
            return true;
        }

        if (a instanceof BigDecimal || b instanceof BigDecimal) {
            return _decimal(a).compareTo(_decimal(b)) > 0;
        }

        if (a instanceof BigInteger || b instanceof BigInteger) {
            return _bigInt(a).compareTo(_bigInt(b)) > 0;
        }

        if (a instanceof Long || b instanceof Long) {
            return _long(a) > _long(b);
        }

        if (a instanceof Integer || b instanceof Integer) {
            return _int(a) > _int(b);
        }

        if (a instanceof Short || b instanceof Short) {
            return _short(a) > _short(b);
        }

        if (a instanceof Byte || b instanceof Byte) {
            return _byte(a) > _byte(b);
        }
        
        if (a instanceof Date || b instanceof Date) {
            Date d1 = _date(a);
            Date d2 = _date(b);
            
            if (d1 == d2) {
                return false;
            }
            
            if (d1 == null) {
                return false;
            }
            
            if (d2 == null) {
                return true;
            }
            
            return d1.compareTo(d2) > 0;
        }

        throw new IllegalArgumentException();
    }
    
    public static boolean _gteq(Object a, Object b) {
        if (_eq(a, b)) {
            return true;
        }
        
        return _gt(a, b);
    }
    
    public static boolean _lt(Object a, Object b) {
        if (a == null) {
            return true;
        }
        
        if (b == null) {
            return false;
        }

        if (a instanceof BigDecimal || b instanceof BigDecimal) {
            return _decimal(a).compareTo(_decimal(b)) < 0;
        }

        if (a instanceof BigInteger || b instanceof BigInteger) {
            return _bigInt(a).compareTo(_bigInt(b)) < 0;
        }

        if (a instanceof Long || b instanceof Long) {
            return _long(a) < _long(b);
        }

        if (a instanceof Integer || b instanceof Integer) {
            return _int(a) < _int(b);
        }

        if (a instanceof Short || b instanceof Short) {
            return _short(a) < _short(b);
        }

        if (a instanceof Byte || b instanceof Byte) {
            return _byte(a) < _byte(b);
        }
        
        if (a instanceof Date || b instanceof Date) {
            Date d1 = _date(a);
            Date d2 = _date(b);
            
            if (d1 == d2) {
                return false;
            }
            
            if (d1 == null) {
                return true;
            }
            
            if (d2 == null) {
                return false;
            }
            
            return d1.compareTo(d2) < 0;
        }

        throw new IllegalArgumentException();
    }

    public static boolean _lteq(Object a, Object b) {
        if (_eq(a, b)) {
            return true;
        }
        
        return _lt(a, b);
    }
    
    public static boolean _eq(Object a, Object b) {
        if (a == b) {
            return true;
        }
        
        if (a == null || b == null) {
            return false;
        }
        
        if (a.equals(b)) {
            return true;
        }

        if (a instanceof BigDecimal || b instanceof BigDecimal) {
            return _decimal(a).compareTo(_decimal(b)) == 0;
        }

        if (a instanceof BigInteger || b instanceof BigInteger) {
            return _bigInt(a).compareTo(_bigInt(b)) == 0;
        }

        if (a instanceof Long || b instanceof Long) {
            return _long(a) == _long(b);
        }

        if (a instanceof Integer || b instanceof Integer) {
            return _int(a) == _int(b);
        }

        if (a instanceof Short || b instanceof Short) {
            return _short(a) == _short(b);
        }

        if (a instanceof Byte || b instanceof Byte) {
            return _byte(a) == _byte(b);
        }
        
        if (a instanceof Date || b instanceof Date) {
            Date d1 = _date(a);
            Date d2 = _date(b);
            
            if (d1 == d2) {
                return true;
            }
            
            if (d1 == null || d2 == null) {
                return false;
            }
            
            return d1.equals(d2);
        }

        throw new IllegalArgumentException();
    }

    public static Object _add(Object a, Object b) {
        if (a == null) {
            return b;
        }

        if (b == null) {
            return a;
        }

        if (a instanceof BigDecimal || b instanceof BigDecimal) {
            return _decimal(a).add(_decimal(b));
        }

        if (a instanceof BigInteger || b instanceof BigInteger) {
            return _bigInt(a).add(_bigInt(b));
        }

        if (a instanceof Long || b instanceof Long) {
            return _long(a) + _long(b);
        }

        if (a instanceof Integer || b instanceof Integer) {
            return _int(a) + _int(b);
        }

        if (a instanceof Short || b instanceof Short) {
            return _short(a) + _short(b);
        }

        if (a instanceof Byte || b instanceof Byte) {
            return _byte(a) + _byte(b);
        }
        
        if (a instanceof String || b instanceof String) {
            return _string(a) + _string(b);
        }

        throw new IllegalArgumentException();
    }
    
    public static Object _sub(Object a, Object b) {
        if (a == null) {
            return null;
        }

        if (b == null) {
            return a;
        }

        if (a instanceof BigDecimal || b instanceof BigDecimal) {
            return _decimal(a).subtract(_decimal(b));
        }

        if (a instanceof BigInteger || b instanceof BigInteger) {
            return _bigInt(a).subtract(_bigInt(b));
        }

        if (a instanceof Long || b instanceof Long) {
            return _long(a) - _long(b);
        }

        if (a instanceof Integer || b instanceof Integer) {
            return _int(a) - _int(b);
        }

        if (a instanceof Short || b instanceof Short) {
            return _short(a) - _short(b);
        }

        if (a instanceof Byte || b instanceof Byte) {
            return _byte(a) - _byte(b);
        }

        throw new IllegalArgumentException();
    }
    
    public static Object _multi(Object a, Object b) {
        if (a == null || b == null) {
            return null;
        }

        if (a instanceof BigDecimal || b instanceof BigDecimal) {
            return _decimal(a).multiply(_decimal(b));
        }

        if (a instanceof BigInteger || b instanceof BigInteger) {
            return _bigInt(a).multiply(_bigInt(b));
        }

        if (a instanceof Long || b instanceof Long) {
            return _long(a) * _long(b);
        }

        if (a instanceof Integer || b instanceof Integer) {
            return _int(a) * _int(b);
        }

        if (a instanceof Short || b instanceof Short) {
            return _short(a) * _short(b);
        }

        if (a instanceof Byte || b instanceof Byte) {
            return _byte(a) * _byte(b);
        }

        throw new IllegalArgumentException();
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
