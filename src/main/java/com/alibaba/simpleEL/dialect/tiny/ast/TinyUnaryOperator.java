package com.alibaba.simpleEL.dialect.tiny.ast;

public enum TinyUnaryOperator {
	Plus,
	Minus,
	PreIncrement,
	PostIncrement,
	PreDecrement,
	PostDecrement
	;
	
    public final String name;
    public final int priority;

    TinyUnaryOperator() {
        this(null, 0);
    }

    TinyUnaryOperator(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
}
