package com.alibaba.simpleEL.dialect.tiny.parser;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELIfStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELIfStatement.Else;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELIfStatement.ElseIf;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELReturnStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELStatement;

public class TinyStatementParser {
	protected final TinyELLexer lexer;
	protected final TinyELExprParser exprParser;

	public TinyStatementParser(TinyELLexer lexer) {
		super();
		this.lexer = lexer;
		this.exprParser = new TinyELExprParser(lexer);
	}

	public TinyStatementParser(String input) {
		this(new TinyELLexer(input));
		this.lexer.nextToken();
	}

	public List<TinyELStatement> statementList() {
		List<TinyELStatement> statements = new ArrayList<TinyELStatement>();

		statementList(statements);

		return statements;
	}

	public void statementList(List<TinyELStatement> statements) {
		for (;;) {
			if (lexer.token() == TinyELToken.IF) {
				statements.add(parseIf());
				continue;
			}

			if (lexer.token() == TinyELToken.RETURN) {
				statements.add(parseIf());
				continue;
			}
			
			if (lexer.token() == TinyELToken.SEMI) {
				lexer.nextToken();
				continue;
			}
			
			break;
		}
	}
	
	public TinyELStatement statement() {
		if (lexer.token() == TinyELToken.IF) {
			return parseIf();
		}

		if (lexer.token() == TinyELToken.RETURN) {
			return parseReturn();
		}
		
		if (lexer.token() == TinyELToken.SEMI) {
			lexer.nextToken();
			return null;
		}
		
		throw new ELException("parse error, TODO : " + lexer.token());
	}
	
	public void statementListBody(List<TinyELStatement> statements) {
		if (lexer.token() == TinyELToken.LBRACE) {
			lexer.nextToken();
			
			for (;;) {
				TinyELStatement stmt = statement();
				if (stmt != null) {
					statements.add(stmt);
				}
				
				if (lexer.token() == TinyELToken.RBRACE) {
					break;
				}
			}
			
			accept(TinyELToken.RBRACE);
		} else {
			TinyELStatement stmt = statement();
			if (stmt != null) {
				statements.add(stmt);
			}
		}
	}

	public TinyELReturnStatement parseReturn() {
		accept(TinyELToken.RETURN);

		TinyELReturnStatement stmt = new TinyELReturnStatement();
		stmt.setExpr(exprParser.expr());
		return stmt;
	}

	public TinyELIfStatement parseIf() {
		accept(TinyELToken.IF);

		TinyELIfStatement stmt = new TinyELIfStatement();
		
		accept(TinyELToken.LPAREN);
		stmt.setCondition(exprParser.expr());
		accept(TinyELToken.RPAREN);
		
		statementListBody(stmt.getStatementList());
		
		for (;;) {
			if (lexer.token() == TinyELToken.ELSE) {
				lexer.nextToken();
				
				if (lexer.token() == TinyELToken.IF) {
					lexer.nextToken();
					ElseIf elseIf = new ElseIf();
					
					accept(TinyELToken.LPAREN);
					elseIf.setCondition(exprParser.expr());
					accept(TinyELToken.RPAREN);
					
					statementListBody(elseIf.getStatementList());
					
					stmt.getElseIfList().add(elseIf);
				} else {
					Else elseItem = new Else();
					statementListBody(elseItem.getStatementList());
					stmt.setElse(elseItem);
				}
			}
			
			break;
		}

		return stmt;
	}

	public void accept(TinyELToken token) {
		if (lexer.token() == token) {
			lexer.nextToken();
		} else {
			throw new ELException("syntax error, expect " + token + ", actual " + lexer.token());
		}
	}
}
