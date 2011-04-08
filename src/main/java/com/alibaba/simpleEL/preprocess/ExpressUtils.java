/**
 * Project: simple-el
 * 
 * File Created at 2010-12-2
 * $Id$
 * 
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.alibaba.simpleEL.preprocess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shaojin.wensj
 *
 */
public class ExpressUtils {
	public static String resolve(String text, VariantResolver resolver) {
		StringBuffer buf = new StringBuffer();
		
		List<Token> tokList = parse(text);
		for (Token tok : tokList) {
			if (TokenType.Variant.equals(tok.getType())) {
				buf.append(resolver.resolve(tok.getText()));
			} else {
				buf.append(tok.getText());
			}
		}
		
		return buf.toString();
	}
	
	public static List<Token> parse(String expr) {

		List<Token> tokenList = new ArrayList<Token>();

		TokenType type = TokenType.Text;

		StringBuffer buf = new StringBuffer();
		char[] charArray = expr.toCharArray();
		for (int i = 0; i < charArray.length; ++i) {
			char ch = charArray[i];

			if (ch == '@') {
				if (buf.length() > 0) {
					tokenList.add(new Token(type, buf.toString()));
				}
				type = TokenType.Variant;
				buf = new StringBuffer();

				continue;
			}
			
			if (ch == '/' && i < charArray.length - 1 && charArray[i + 1] == '*') {
				if (buf.length() > 0) {
					tokenList.add(new Token(type, buf.toString()));
				}
				buf.append("/*");
				++i;
				type = TokenType.MultiLineComment;
				continue;
			}
			
			if (ch == '/' && i < charArray.length - 1 && charArray[i + 1] == '/') {
				if (buf.length() > 0) {
					tokenList.add(new Token(type, buf.toString()));
				}
				buf.append("//");
				++i;
				type = TokenType.LineComment;
				continue;
			}

			if (type == TokenType.Variant) {
				if (Character.isLetter(ch) || Character.isDigit(ch)) {
					buf.append(ch);
				} else if (ch == '_') {
					buf.append(ch);
				} else {
					tokenList.add(new Token(type, buf.toString()));
					type = TokenType.Text;
					buf = new StringBuffer();
					--i;
				}
				continue;
			}
			
			if (type == TokenType.Text) {
				buf.append(ch);
				continue;
			}
			
			if (type == TokenType.MultiLineComment) {
				if (ch == '*' && i < charArray.length - 1 && charArray[i + 1] == '/') {
					buf.append("*/");
					++i;
					tokenList.add(new Token(type, buf.toString()));
					type = TokenType.Text;
					buf = new StringBuffer();
					continue;
				} else {
					buf.append(ch);
				}
			}
			
			if (type == TokenType.LineComment) {
				if (ch == '\n') {
					buf.append(ch);
					tokenList.add(new Token(type, buf.toString()));
					type = TokenType.Text;
					buf = new StringBuffer();
				} else if (ch == '\r') {
					buf.append(ch);
					if (i < charArray.length - 1 && charArray[i + 1] == '\n') {
						buf.append(charArray[i + 1]);
						++i;
					}
					
					tokenList.add(new Token(type, buf.toString()));
					type = TokenType.Text;
					buf = new StringBuffer();
				} else {
					buf.append(ch);
				}
				continue;
			}
		}
		
		if (buf.length() > 0) {
			tokenList.add(new Token(type, buf.toString()));
		}
		
		return tokenList;
	}

	public static class Token {

		public Token(TokenType type, String text) {
			this.type = type;
			this.text = text;
		}

		public Token() {
		}

		private TokenType type;
		private String text;

		public TokenType getType() {
			return type;
		}

		public void setType(TokenType type) {
			this.type = type;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}

	public static enum TokenType {
		Text, LineComment, MultiLineComment, Variant
	}

}
