package com.alibaba.simpleEL.dialect.ql.parser;

import com.alibaba.simpleEL.ELException;

public class AbstractQLParser {
	protected final QLLexer lexer;

	public AbstractQLParser(QLLexer lexer) {
		super();
		this.lexer = lexer;
	}

	public QLLexer getLexer() {
		return lexer;
	}

	public void accept(QLToken token) {
		if (lexer.token() == token) {
			lexer.nextToken();
		} else {
			throw new ELException("syntax error, expect " + token + ", actual "
					+ lexer.token());
		}
	}
}
