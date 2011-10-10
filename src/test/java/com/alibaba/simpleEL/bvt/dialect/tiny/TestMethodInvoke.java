package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;

import junit.framework.TestCase;

import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;

public class TestMethodInvoke extends TestCase {

    public void test_invoke() throws Exception {
        TinyELEvalService service = new TinyELEvalService();
        service.regsiterVariant(BigDecimal.class, "a", "b", "c");
        service.regsiterVariant(Date.class, "d");
        
        service.registerFunction(this.getClass().getMethod("f0", byte.class));
        service.registerFunction(this.getClass().getMethod("f1", short.class));
        service.registerFunction(this.getClass().getMethod("f2", int.class));
        service.registerFunction(this.getClass().getMethod("f3", long.class));
        service.registerFunction(this.getClass().getMethod("f4", float.class));
        service.registerFunction(this.getClass().getMethod("f5", double.class));

        service.registerFunction(this.getClass().getMethod("f6", Byte.class));
        service.registerFunction(this.getClass().getMethod("f7", Short.class));
        service.registerFunction(this.getClass().getMethod("f8", Integer.class));
        service.registerFunction(this.getClass().getMethod("f9", Long.class));
        service.registerFunction(this.getClass().getMethod("f10", Float.class));
        service.registerFunction(this.getClass().getMethod("f11", Double.class));

        service.registerFunction(this.getClass().getMethod("f12", boolean.class));
        service.registerFunction(this.getClass().getMethod("f13", Boolean.class));
        service.registerFunction(this.getClass().getMethod("f14", Date.class));
        service.registerFunction(this.getClass().getMethod("f15", String.class));
        service.registerFunction(this.getClass().getMethod("f16", BigInteger.class));
        service.registerFunction(this.getClass().getMethod("f17", BigDecimal.class));
        service.registerFunction(this.getClass().getMethod("fn", String.class, Object[].class));

        HashMap<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", 1);
        service.eval(ctx, "f0(a)");
        service.eval(ctx, "f1(a)");
        service.eval(ctx, "f2(a)");
        service.eval(ctx, "f3(a)");
        service.eval(ctx, "f4(a)");
        service.eval(ctx, "f5(a)");
        service.eval(ctx, "f6(a)");
        service.eval(ctx, "f7(a)");
        service.eval(ctx, "f8(a)");
        service.eval(ctx, "f9(a)");
        service.eval(ctx, "f10(a)");
        service.eval(ctx, "f11(a)");
        service.eval(ctx, "f12(a)");
        service.eval(ctx, "f13(a)");
        service.eval(ctx, "f14(a)");
        service.eval(ctx, "f15(d)");
        service.eval(ctx, "f16(a)");
        service.eval(ctx, "f17(a)");
        service.eval(ctx, "fn(d, a, b)");
    }

    public static Object f0(byte v) {
        return v;
    }

    public static Object f1(short v) {
        return v;
    }

    public static Object f2(int v) {
        return v;
    }

    public static Object f3(long v) {
        return v;
    }

    public static Object f4(float v) {
        return v;
    }

    public static Object f5(double v) {
        return v;
    }

    public static Object f6(Byte v) {
        return v;
    }

    public static Object f7(Short v) {
        return v;
    }

    public static Object f8(Integer v) {
        return v;
    }

    public static Object f9(Long v) {
        return v;
    }

    public static Object f10(Float v) {
        return v;
    }

    public static Object f11(Double v) {
        return v;
    }

    public static Object f12(boolean v) {
        return v;
    }

    public static Object f13(Boolean v) {
        return v;
    }

    public static Object f14(Date v) {
        return v;
    }

    public static Object f15(String v) {
        return v;
    }
    
    public static Object f16(BigInteger v) {
        return v;
    }
    
    public static Object f17(BigDecimal v) {
        return v;
    }

    public static Object fn(String v, Object... objects) {
        return v;
    }
}
