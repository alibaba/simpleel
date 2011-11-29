package com.alibaba.simpleEL.compile;

import com.alibaba.simpleEL.Expr;

public class CompileResult {

    private Class<? extends Expr> clazz;
    private JavaFileObjectImpl    source;
    private byte[]                bytecode;

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
        return bytecode;
    }

    public void setBytecode(byte[] bytecode) {
        this.bytecode = bytecode;
    }

}
