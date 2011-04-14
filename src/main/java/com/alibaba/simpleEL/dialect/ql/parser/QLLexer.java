package com.alibaba.simpleEL.dialect.ql.parser;

import java.math.BigDecimal;

public class QLLexer {
	private char[] input;
	private int pos;
	
	public char[] getInput() {
		return this.input;
	}
	
	public int getPosition() {
		return this.pos;
	}
	
	public QLToken nextToken() {
		throw new UnsupportedOperationException();
	}
	
	public QLToken token() {
		throw new UnsupportedOperationException();
	}
	
	public String hexString() {
		throw new UnsupportedOperationException();
	}
	
	public Number integerValue() {
		throw new UnsupportedOperationException();
	}
	
	public BigDecimal decimalValue() {
		throw new UnsupportedOperationException();
	}
	
	public String stringVal() {
		throw new UnsupportedOperationException();
	}
}
