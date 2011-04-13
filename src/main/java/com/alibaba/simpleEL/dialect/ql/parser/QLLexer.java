package com.alibaba.simpleEL.dialect.ql.parser;

public class QLLexer {
	private char[] input;
	private int pos;
	
	public char[] getInput() {
		return this.input;
	}
	
	public int getPosition() {
		return this.pos;
	}
	
	public QLToken next() {
		throw new UnsupportedOperationException();
	}
}
