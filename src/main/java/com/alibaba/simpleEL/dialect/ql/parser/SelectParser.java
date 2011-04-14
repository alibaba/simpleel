package com.alibaba.simpleEL.dialect.ql.parser;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.dialect.ql.ast.OrderByMode;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderBy;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderByItem;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelect;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelectItem;

public class SelectParser extends AbstractQLParser {
	private final QLExprParser exprParser;

	public SelectParser(String input) {
		this(new QLLexer(input));
		this.lexer.nextToken();
	}

	public SelectParser(QLLexer lexer) {
		super(lexer);
		this.exprParser = new QLExprParser(lexer);
	}

	public QLLexer getLexer() {
		return lexer;
	}

	public QLSelect select() {
		QLSelect select = new QLSelect();

		if (lexer.token() == QLToken.SELECT) {
			lexer.nextToken();

			for (;;) {
				QLSelectItem item = new QLSelectItem();
				item.setExpr(this.exprParser.expr());
				item.setAlias(as());
				
				if (lexer.token() == QLToken.COMMA) {
					lexer.nextToken();
					continue;
				} else {
					break;
				}
			}
		}
		
		if (lexer.token() == QLToken.WHERE) {
			lexer.nextToken();
			select.setWhere(exprParser.expr());
		}
		
		if (lexer.token() == QLToken.ORDER) {
			lexer.nextToken();
			accept(QLToken.BY);
			
			QLOrderBy orderBy = new QLOrderBy();
			for (;;) {
				QLOrderByItem item = new QLOrderByItem();
				item.setExpr(this.exprParser.expr());
				
				if (lexer.token() == QLToken.ASC) {
					lexer.nextToken();
					item.setMode(OrderByMode.ASC);
				} else if (lexer.token() == QLToken.DESC) {
					lexer.nextToken();
					item.setMode(OrderByMode.DESC);
				}
				
				if (lexer.token() == QLToken.COMMA) {
					lexer.nextToken();
					continue;
				} else {
					break;
				}
			}
			select.setOrderBy(orderBy);
		}

		return select;
	}

	public String as() {
		if (lexer.token() == QLToken.AS) {
			lexer.nextToken();
			if (lexer.token() != QLToken.LITERAL_ALIAS) {
				throw new ELException("parse as error");
			}

			String alias = lexer.stringVal();
			lexer.nextToken();
			return alias;
		}

		if (lexer.token() == QLToken.LITERAL_ALIAS) {
			String alias = lexer.stringVal();
			lexer.nextToken();
			return alias;
		}
		
		return null;
	}
}
