package com.alibaba.simpleEL.dialect.ql.parser;

public enum QLToken {
	SELECT("SELECT"),
    FROM("FROM"),
    WHERE("WHERE"),
    ORDER("ORDER"),
    BY("BY"),
    GROUP("GROUP"),
    HAVING("HAVING"),
    NULL("NULL"),
    NOT("NOT"),
    AS("AS"),
	;
	
    public final String name;

    QLToken() {
        this(null);
    }

    QLToken(String name) {
        this.name = name;
    }
}
