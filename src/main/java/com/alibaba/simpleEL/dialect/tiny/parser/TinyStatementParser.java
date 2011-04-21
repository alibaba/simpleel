package com.alibaba.simpleEL.dialect.tiny.parser;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELExprStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELForEachStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELForStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELIfStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELIfStatement.Else;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELIfStatement.ElseIf;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELReturnStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELWhileStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyLocalVarDeclareStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELVariantDeclareItem;

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
			if (lexer.token() == TinyELToken.RBRACE) {
				break;
			}

			if (lexer.token() == TinyELToken.EOF) {
				break;
			}

			TinyELStatement stmt = statement();
			if (stmt != null) {
				statements.add(stmt);
			}
		}
	}

	public TinyELStatement statement() {
		if (lexer.token() == TinyELToken.IF) {
			return parseIf();
		}

		if (lexer.token() == TinyELToken.WHILE) {
			return parseWhile();
		}

		if (lexer.token() == TinyELToken.RETURN) {
			return parseReturn();
		}

		if (lexer.token() == TinyELToken.FOR) {
			return parseFor();
		}

		if (lexer.token() == TinyELToken.SEMI) {
			lexer.nextToken();
			return null;
		}

		switch (lexer.token()) {
		case BYTE:
		case SHORT:
		case INT:
		case LONG:
		case FLOAT:
		case DOUBLE:
			return parseVarDecl();
		default:
			break;
		}

		TinyELExpr expr = exprParser.expr();
		accept(TinyELToken.SEMI);
		return new TinyELExprStatement(expr);
	}

	public TinyELStatement parseFor() {
		accept(TinyELToken.FOR);

		accept(TinyELToken.LPAREN);
		String type = exprParser.type();
		String varName = exprParser.name().toString();

		if (lexer.token() == TinyELToken.COLON) {
			lexer.nextToken();
			TinyELForEachStatement stmt = new TinyELForEachStatement();
			stmt.setType(type);
			stmt.setVariant(varName);
			stmt.setTargetExpr(exprParser.expr());

			accept(TinyELToken.RPAREN);

			statementListBody(stmt.getStatementList());

			return stmt;
		}

		TinyELForStatement stmt = new TinyELForStatement();
		stmt.setType(type);
		accept(TinyELToken.EQ);
		{
			TinyELVariantDeclareItem var = new TinyELVariantDeclareItem(varName);
			var.setInitValue(exprParser.expr());
			stmt.getVariants().add(var);
		}

		while (lexer.token() == TinyELToken.COMMA) {
			lexer.nextToken();

			if (lexer.token() != TinyELToken.IDENTIFIER) {
				throw new ELException("parse error : " + lexer.token());
			}

			varName = lexer.stringVal();
			lexer.nextToken();

			TinyELVariantDeclareItem var = new TinyELVariantDeclareItem(varName);
			stmt.getVariants().add(var);

			if (lexer.token() == TinyELToken.EQ) {
				lexer.nextToken();
				var.setInitValue(exprParser.expr());
			}
		}

		accept(TinyELToken.SEMI);

		if (lexer.token() != TinyELToken.SEMI) {
			stmt.setCondition(exprParser.expr());
		}
		accept(TinyELToken.SEMI);

		if (lexer.token() != TinyELToken.RPAREN) {
			stmt.setPostExpr(exprParser.expr());
		}
		accept(TinyELToken.RPAREN);

		statementListBody(stmt.getStatementList());

		return stmt;
	}

	public TinyLocalVarDeclareStatement parseVarDecl() {
		TinyLocalVarDeclareStatement stmt = new TinyLocalVarDeclareStatement();

		switch (lexer.token()) {
		case BYTE:
		case SHORT:
		case INT:
		case LONG:
		case FLOAT:
		case DOUBLE:
			stmt.setType(lexer.token().name);
			lexer.nextToken();
			break;
		default:
			throw new ELException("parse error, TODO : " + lexer.token());
		}

		for (;;) {
			if (lexer.token() != TinyELToken.IDENTIFIER) {
				throw new ELException("parse error : " + lexer.token());
			}

			String varName = lexer.stringVal();
			lexer.nextToken();

			TinyELVariantDeclareItem var = new TinyELVariantDeclareItem(varName);
			stmt.getVariants().add(var);

			if (lexer.token() == TinyELToken.EQ) {
				lexer.nextToken();
				var.setInitValue(exprParser.expr());
			}

			if (lexer.token() == TinyELToken.COMMA) {
				lexer.nextToken();
				continue;
			}

			break;
		}

		accept(TinyELToken.SEMI);

		return stmt;
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

	public TinyELWhileStatement parseWhile() {
		accept(TinyELToken.WHILE);

		TinyELWhileStatement stmt = new TinyELWhileStatement();

		accept(TinyELToken.LPAREN);
		stmt.setCondition(exprParser.expr());
		accept(TinyELToken.RPAREN);

		statementListBody(stmt.getStatementList());

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
