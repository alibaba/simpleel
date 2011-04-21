package com.alibaba.simpleEL.dialect.tiny.ast;


public enum TinyELBinaryOperator {
    Add("+", 70),
    Subtract("-", 70),
    Multiply("*", 60),
    Divide("/", 60),
    Concat("||", 140),
    BitwiseAnd("&", 90),
    BitwiseNot("!", 130),
    BitwiseOr("|", 100),
    BitwiseXor("^", 50),
    BooleanAnd("AND", 140),
    BooleanOr("OR", 160),
    BooleanXor("XOR", 150),
    Equality("==", 170),
    Assignment("=", 169),
    AddAndAssignment("+=", 169),
    SubtractAndAssignment("-=", 169),
    GreaterThan(">", 110),
    GreaterThanOrEqual(">=", 110),
    Is("IS", 110),
    LessThan("<", 110),
    LessThanOrEqual("<=", 110),
    LessThanOrEqualOrGreaterThan("<=>", 110),
    InstanceOf("instanceof", 110),
    LessThanOrGreater("<>", 110),
    LeftShift("<<", 80),
    Like("LIKE", 110),
    RightShift(">>", 80),
    Modulus("%", 60),
    NotEqual("!=", 110),
    NotLessThan("!<", 110),
    NotGreaterThan("!>", 110);

    public static int getPriority(TinyELBinaryOperator operator) {
        return 0;
    }

  

    public final String name;
    public final int priority;

    TinyELBinaryOperator() {
        this(null, 0);
    }

    TinyELBinaryOperator(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
}
