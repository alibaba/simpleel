package com.alibaba.simpleEL.dialect.tiny.parser;

public enum TinyELToken {
	   LPAREN("("),
	    RPAREN(")"),
	    LBRACE("{"),
	    RBRACE("}"),
	    LBRACKET("["),
	    RBRACKET("]"),
	    SEMI(";"),
	    COMMA(","),
	    DOT("."),
	    EQ("="),
	    GT(">"),
	    LT("<"),
	    BANG("!"),
	    TILDE("~"),
	    QUES("?"),
	    COLON(":"),
	    COLONEQ(":="),
	    EQEQ("=="),
	    LTEQ("<="),
	    LTEQGT("<=>"),
	    LTGT("<>"),
	    GTEQ(">="),
	    BANGEQ("!="),
	    BANGGT("!>"),
	    BANGLT("!<"),
	    AMPAMP("&&"),
	    BARBAR("||"),
	    PLUS("+"),
	    SUB("-"),
	    STAR("*"),
	    SLASH("/"),
	    AMP("&"),
	    BAR("|"),
	    CARET("^"),
	    PERCENT("%"),
	    LTLT("<<"),
	    GTGT(">>"),
	    MONKEYS_AT("@"),
	    
	    EOF,
	    ERROR,
	    IDENTIFIER,
	    
	    ;
		
	    public final String name;

	    TinyELToken() {
	        this(null);
	    }

	    TinyELToken(String name) {
	        this.name = name;
	    }
}
