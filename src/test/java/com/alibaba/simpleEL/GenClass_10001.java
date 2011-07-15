package com.alibaba.simpleEL;

import static com.alibaba.simpleEL.TypeUtils._int;

import java.util.HashMap;
import java.util.Map;

public class GenClass_10001 implements Expr {

    public Object eval(Map<String, Object> ctx) {
        return _int(ctx.get("a")) > _int(ctx.get("b")) && _int(ctx.get("b")) > _int(ctx.get("a")) && (_int(ctx.get("a")) == _int(ctx.get("b")));
    }
    
    public static void main(String[] args) throws Exception {
        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("a", 3);
        ctx.put("b", 4);
        
        System.out.println(new GenClass_10001().eval(ctx));
    }
}
