package com.alibaba.simpleEL.dialect.ql.parser;

public class QLExprParser {
	private final QLLexer lexer;
	
	public QLExprParser(QLLexer lexer) {
		this.lexer = lexer;
	}
	
	public QLLexer getLexer() {
		return lexer;
	}
}
