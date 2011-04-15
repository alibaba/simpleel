package com.alibaba.simpleEL.dialect.ql.parser;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.dialect.ql.ast.QLExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLLimit;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderBy;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderByItem;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderByMode;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelect;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelectItem;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelectList;

public class QLSelectParser extends AbstractQLParser {
	private final QLExprParser exprParser;

	public QLSelectParser(String input) {
		this(new QLLexer(input));
		this.lexer.nextToken();
	}

	public QLSelectParser(QLLexer lexer) {
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

			QLSelectList selectList = new QLSelectList();
			for (;;) {
				QLSelectItem item = new QLSelectItem();
				item.setExpr(this.exprParser.expr());
				item.setAlias(as());
				selectList.getItems().add(item);
				
				if (lexer.token() == QLToken.COMMA) {
					lexer.nextToken();
					continue;
				} else {
					break;
				}
			}
			select.setSelectList(selectList);
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
					item.setMode(QLOrderByMode.ASC);
				} else if (lexer.token() == QLToken.DESC) {
					lexer.nextToken();
					item.setMode(QLOrderByMode.DESC);
				}
				orderBy.getItems().add(item);
				
				if (lexer.token() == QLToken.COMMA) {
					lexer.nextToken();
					continue;
				} else {
					break;
				}
			}
			select.setOrderBy(orderBy);
		}
		
		if (lexer.token() == QLToken.LIMIT) {
			lexer.nextToken();
			
			QLLimit limit = new QLLimit();
			
			QLExpr expr = exprParser.expr();
			
			if (lexer.token() == QLToken.COMMA){
				lexer.nextToken();
				QLExpr rowCount = exprParser.expr();
				limit.setOffset(expr);
				limit.setRowCount(rowCount);
			} else {
				limit.setRowCount(expr);
			}
			
			select.setLimit(limit);
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
