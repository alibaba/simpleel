package com.alibaba.simpleEL.bvt.dialect.tiny;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.alibaba.simpleEL.TypeUtils;
import com.alibaba.simpleEL.dialect.tiny.TinyELEvalService;
import com.alibaba.simpleEL.dialect.tiny.TinyELPreprocessor;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELStringExpr;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELOutputVisitor;

public class LastTest extends TestCase {
    private final static ThreadLocal<List<Map<String, Object>>> dataListLocal = new ThreadLocal<List<Map<String, Object>>>();
    
    public void test_0() throws Exception {
        TinyELEvalService service = new TinyELEvalService();
        service.setPreprocessor(new MyTinyELPrerocessor());

        service.regsiterVariant(BigDecimal.class, "a", "b");
        service.regsiterVariant(long.class, "@@INTERVAL");
        service.registerFunction("SUM", LastTest.class.getMethod("sum", String.class));

        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("a", 3);
            data.put("b", 4);
            dataList.add(data);
        }
        {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("a", 5);
            data.put("b", 6);
            dataList.add(data);
        }


        dataListLocal.set(dataList);
        for (Map<String, Object> ctx : dataList) {
            ctx.put("@@INTERVAL", 2);
            Object result = service.eval(ctx, "SUM(a) / @@INTERVAL");
            Assert.assertEquals(4, ((Number) result).intValue());
        }
        dataListLocal.set(null);
    }
    
    public static Number sum(String propertyName) {
        List<Map<String, Object>> dataList = dataListLocal.get();
        if (dataList == null) {
            return 0;
        }
        
        BigDecimal sum = BigDecimal.ZERO;
        for (Map<String, Object> data : dataList) {
            Object value = data.get(propertyName);
            if (value == null) {
                continue;
            }
            BigDecimal decimalValue = TypeUtils._decimal(value);
            sum = sum.add(decimalValue);
        }
        
        return sum;
    }

    static class MyTinyELPrerocessor extends TinyELPreprocessor {

        private final List<String> aggregateMethods = new ArrayList<String>();

        public MyTinyELPrerocessor(){
            aggregateMethods.add("SUM");
            aggregateMethods.add("MIN");
            aggregateMethods.add("MAX");
            aggregateMethods.add("AVG");
            aggregateMethods.add("COUNT");
            aggregateMethods.add("LAST");
            aggregateMethods.add("FIRST");
        }

        protected TinyELOutputVisitor createVisitor(StringWriter out) {
            JavaSourceGenVisitor visitor = new MyJavaSourceGenVisitor(new PrintWriter(out));
            return visitor;
        }

        private boolean isAggregateFunction(String name) {
            name = name.toUpperCase();
            return aggregateMethods.contains(name);
        }

        protected class MyJavaSourceGenVisitor extends JavaSourceGenVisitor {

            public MyJavaSourceGenVisitor(PrintWriter out){
                super(out);
            }

            @Override
            public boolean visit(TinyELMethodInvokeExpr x) {
                if (x.getParameters().size() == 1) {
                    TinyELExpr param = x.getParameters().get(0);
                    if (isAggregateFunction(x.getMethodName()) && param instanceof TinyELIdentifierExpr) {
                        x.setMethodName(x.getMethodName().toUpperCase());
                        TinyELExpr newParam = new TinyELStringExpr(((TinyELIdentifierExpr) param).getName());
                        x.getParameters().set(0, newParam);
                    }
                }
                return super.visit(x);
            }
        }
    }
}
