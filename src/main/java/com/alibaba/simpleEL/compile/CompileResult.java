package com.alibaba.simpleEL.compile;

import com.alibaba.simpleEL.Expr;

public class CompileResult {

    private Class<? extends Expr> clazz;
    private JavaFileObjectImpl    source;

    public JavaFileObjectImpl getSource() {
        return source;
    }

    public void setSource(JavaFileObjectImpl source) {
        this.source = source;
    }

    public Class<? extends Expr> getExprClass() {
        return clazz;
    }

    public void setExprClass(Class<? extends Expr> clazz) {
        this.clazz = clazz;
    }

    public byte[] getBytecode() {
        if (source == null) {
            return null;
        }
        return source.getByteCode();
    }


}
