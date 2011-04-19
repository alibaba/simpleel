package com.alibaba.simpleEL.dialect.ql.parser;

import java.math.BigInteger;
import java.util.Collection;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.dialect.ql.ast.QLAggregateExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLAllColumnExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLBetweenExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLBinaryOpExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLBinaryOperator;
import com.alibaba.simpleEL.dialect.ql.ast.QLCaseExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLCharExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLIdentifierExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLNullExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLNumberLiteralExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLPropertyExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLVariantRefExpr;

public class QLExprParser extends AbstractQLParser {
	
	public QLExprParser(String input) {
		this(new QLLexer(input));
		this.lexer.nextToken();
	}
	
	public QLExprParser(QLLexer lexer) {
		super (lexer);
	}
	
	public QLLexer getLexer() {
		return lexer;
	}
	

    public QLExpr expr() {
        if (lexer.token() == QLToken.STAR) {
            lexer.nextToken();

            return new QLAllColumnExpr();
        }

        QLExpr expr = primary();

        if (lexer.token() == QLToken.COMMA) {
            return expr;
        }

        return exprRest(expr);
    }

    public QLExpr exprRest(QLExpr expr) {
        expr = bitXorRest(expr);
        expr = multiplicativeRest(expr);
        expr = additiveRest(expr);
        expr = shiftRest(expr);
        expr = bitAndRest(expr);
        expr = bitOrRest(expr);
        expr = inRest(expr);
        expr = relationalRest(expr);
        expr = equalityRest(expr);
        expr = andRest(expr);
        expr = xorRest(expr);
        expr = orRest(expr);

        return expr;
    }

    public final QLExpr bitXor() {
        QLExpr expr = primary();
        return bitXorRest(expr);
    }

    public QLExpr bitXorRest(QLExpr expr) {
        if (lexer.token() == QLToken.CARET) {
            lexer.nextToken();
            QLExpr rightExp = primary();
            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.BitwiseXor, rightExp);
            expr = bitXorRest(expr);
        }

        return expr;
    }

    public final QLExpr multiplicative() {
        QLExpr expr = bitXor();
        return multiplicativeRest(expr);
    }

    public QLExpr multiplicativeRest(QLExpr expr) {
        if (lexer.token() == QLToken.STAR) {
            lexer.nextToken();
            QLExpr rightExp = primary();
            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Multiply, rightExp);
            expr = multiplicativeRest(expr);
        } else if (lexer.token() == QLToken.SLASH) {
            lexer.nextToken();
            QLExpr rightExp = primary();
            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Divide, rightExp);
            expr = multiplicativeRest(expr);
        } else if (lexer.token() == QLToken.PERCENT) {
            lexer.nextToken();
            QLExpr rightExp = primary();
            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Modulus, rightExp);
            expr = multiplicativeRest(expr);
        }
        return expr;
    }
    
    public final QLExpr bitAnd() {
        QLExpr expr = shift();
        return orRest(expr);
    }

    public final QLExpr bitAndRest(QLExpr expr) {
        while (lexer.token() == QLToken.AMP) {
            lexer.nextToken();
            QLExpr rightExp = shift();
            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.BitwiseAnd, rightExp);
        }
        return expr;
    }

    public final QLExpr bitOr() {
        QLExpr expr = bitAnd();
        return orRest(expr);
    }

    public final QLExpr bitOrRest(QLExpr expr) {
        while (lexer.token() == QLToken.BAR) {
            lexer.nextToken();
            QLExpr rightExp = bitAnd();
            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.BitwiseOr, rightExp);
        }
        return expr;
    }

    public final QLExpr equality() {
        QLExpr expr = relational();
        return equalityRest(expr);
    }

    public final QLExpr equalityRest(QLExpr expr) {
        QLExpr rightExp;
        if (lexer.token() == QLToken.EQ) {
            lexer.nextToken();
            rightExp = or();

            rightExp = equalityRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Equality, rightExp);
        } else if (lexer.token() == QLToken.BANGEQ) {
            lexer.nextToken();
            rightExp = or();

            rightExp = equalityRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.NotEqual, rightExp);
        }

        return expr;
    }

    public final QLExpr inRest(QLExpr expr) {
        if (lexer.token() == QLToken.IN) {
            throw new ELException("TODO");
        }

        expr = andRest(expr);
        expr = orRest(expr);
        return expr;
    }
    
    public final QLExpr additive() {
        QLExpr expr = multiplicative();
        return additiveRest(expr);
    }
    
    public QLExpr additiveRest(QLExpr expr) {
        if (lexer.token() == QLToken.PLUS) {
            lexer.nextToken();
            QLExpr rightExp = multiplicative();

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Add, rightExp);
            expr = additiveRest(expr);
        } else if (lexer.token() == QLToken.BARBAR) {
            lexer.nextToken();
            QLExpr rightExp = multiplicative();
            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Concat, rightExp);
            expr = additiveRest(expr);
        } else if (lexer.token() == QLToken.SUB) {
            lexer.nextToken();
            QLExpr rightExp = multiplicative();

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Subtract, rightExp);
            expr = additiveRest(expr);
        }

        return expr;
    }
    

    public final QLExpr shift() {
        QLExpr expr = additive();
        return shiftRest(expr);
    }

    public QLExpr shiftRest(QLExpr expr) {
        if (lexer.token() == QLToken.LTLT) {
            lexer.nextToken();
            QLExpr rightExp = multiplicative();

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.LeftShift, rightExp);
            expr = shiftRest(expr);
        } else if (lexer.token() == QLToken.GTGT) {
            lexer.nextToken();
            QLExpr rightExp = multiplicative();

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.RightShift, rightExp);
            expr = shiftRest(expr);
        }

        return expr;
    }

    public final QLExpr and() {
        QLExpr expr = equality();
        return andRest(expr);
    }

    public final QLExpr andRest(QLExpr expr) {
        while (lexer.token() == QLToken.AND || lexer.token() == QLToken.AMPAMP) {
            lexer.nextToken();
            QLExpr rightExp = equality();
            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.BooleanAnd, rightExp);
        }
        return expr;
    }

    public final QLExpr xor() {
        QLExpr expr = and();
        return xorRest(expr);
    }

    public final QLExpr xorRest(QLExpr expr) {
        while (lexer.token() == QLToken.XOR) {
            lexer.nextToken();
            QLExpr rightExp = and();
            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.BooleanXor, rightExp);
        }
        return expr;
    }

    public final QLExpr or() {
        QLExpr expr = xor();
        return orRest(expr);
    }

    public final QLExpr orRest(QLExpr expr) {
        while (lexer.token() == QLToken.OR) {
            lexer.nextToken();
            QLExpr rightExp = xor();
            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.BooleanOr, rightExp);
        }
        return expr;
    }

    public final QLExpr relational() {
        QLExpr expr = bitOr();

        return relationalRest(expr);
    }

    public QLExpr relationalRest(QLExpr expr) {
        QLExpr rightExp;
        if (lexer.token() == QLToken.LT) {
            lexer.nextToken();
            rightExp = bitOr();

            rightExp = relationalRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.LessThan, rightExp);
        } else if (lexer.token() == QLToken.LTEQ) {
            lexer.nextToken();
            rightExp = shift();

            rightExp = relationalRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.LessThanOrEqual, rightExp);
        } else if (lexer.token() == QLToken.LTEQGT) {
            lexer.nextToken();
            rightExp = shift();

            rightExp = relationalRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.LessThanOrEqualOrGreaterThan, rightExp);
        } else if (lexer.token() == QLToken.GT) {
            lexer.nextToken();
            rightExp = shift();

            rightExp = relationalRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.GreaterThan, rightExp);
        } else if (lexer.token() == QLToken.GTEQ) {
            lexer.nextToken();
            rightExp = shift();

            rightExp = relationalRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.GreaterThanOrEqual, rightExp);
        } else if (lexer.token() == QLToken.BANGLT) {
            lexer.nextToken();
            rightExp = shift();

            rightExp = relationalRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.NotLessThan, rightExp);
        } else if (lexer.token() == QLToken.BANGGT) {
            lexer.nextToken();
            rightExp = shift();

            rightExp = relationalRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.NotGreaterThan, rightExp);
        } else if (lexer.token() == QLToken.LTGT) {
            lexer.nextToken();
            rightExp = shift();

            rightExp = relationalRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.LessThanOrGreater, rightExp);
        } else if (lexer.token() == QLToken.LIKE) {
            lexer.nextToken();
            rightExp = shift();

            rightExp = relationalRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Like, rightExp);

            if (lexer.token() == QLToken.ESCAPE) {
                lexer.nextToken();
                rightExp = expr();
                expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Escape, rightExp);
            }
        } else if (lexer.token() == (QLToken.NOT)) {
            lexer.nextToken();
            expr = notRationalRest(expr);
        } else if (lexer.token() == (QLToken.BETWEEN)) {
            lexer.nextToken();
            QLExpr beginExpr = primary();
            accept(QLToken.AND);
            QLExpr endExpr = primary();
            expr = new QLBetweenExpr(expr, beginExpr, endExpr);
        } else if (lexer.token() == (QLToken.IS)) {
            lexer.nextToken();

            if (lexer.token() == (QLToken.NOT)) {
                lexer.nextToken();
                QLExpr rightExpr = primary();
                expr = new QLBinaryOpExpr(expr, QLBinaryOperator.IsNot, rightExpr);
            } else {
                QLExpr rightExpr = primary();
                expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Is, rightExpr);
            }
        } else if (lexer.token() == QLToken.IN) {
            expr = inRest(expr);
        }

        return expr;
    }

    public QLExpr notRationalRest(QLExpr expr) {
        if (lexer.token() == (QLToken.LIKE)) {
            lexer.nextToken();
            QLExpr rightExp = shift();

            rightExp = relationalRest(rightExp);

            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.NotLike, rightExp);

            if (lexer.token() == QLToken.ESCAPE) {
                lexer.nextToken();
                rightExp = expr();
                expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Escape, rightExp);
            }
        } else if (lexer.token() == (QLToken.BETWEEN)) {
            lexer.nextToken();
            QLExpr beginExpr = primary();
            accept(QLToken.AND);
            QLExpr endExpr = primary();

            expr = new QLBetweenExpr(expr, true, beginExpr, endExpr);

            return expr;
        } else {
            throw new ELException("TODO");
        }
        return expr;
    }

    public QLExpr primary() {
        QLExpr sqlExpr = null;

        final QLToken tok = lexer.token();

        switch (tok) {
        case LPAREN:
            lexer.nextToken();
            sqlExpr = expr();
            accept(QLToken.RPAREN);
            break;
        case INSERT:
            lexer.nextToken();
            if (lexer.token() != QLToken.LPAREN) {
                throw new ELException("syntax error");
            }
            sqlExpr = new QLIdentifierExpr("INSERT");
            break;
        case IDENTIFIER:
            sqlExpr = new QLIdentifierExpr(lexer.stringVal());
            lexer.nextToken();
            break;
        case NEW:
            throw new ELException("TODO");
        case LITERAL_INT:
            sqlExpr = new QLNumberLiteralExpr(lexer.integerValue());
            lexer.nextToken();
            break;
        case LITERAL_FLOAT:
            sqlExpr = new QLNumberLiteralExpr(lexer.decimalValue());
            lexer.nextToken();
            break;
        case LITERAL_CHARS:
            sqlExpr = new QLCharExpr(lexer.stringVal());
            lexer.nextToken();
            break;
        case CASE:
            QLCaseExpr caseExpr = new QLCaseExpr();
            lexer.nextToken();
            if (lexer.token() != QLToken.WHEN) {
                caseExpr.setValueExpr(expr());
            }

            accept(QLToken.WHEN);
            QLExpr testExpr = expr();
            accept(QLToken.THEN);
            QLExpr valueExpr = expr();
            QLCaseExpr.Item caseItem = new QLCaseExpr.Item(testExpr, valueExpr);
            caseExpr.getItems().add(caseItem);

            while (lexer.token() == QLToken.WHEN) {
                lexer.nextToken();
                testExpr = expr();
                accept(QLToken.THEN);
                valueExpr = expr();
                caseItem = new QLCaseExpr.Item(testExpr, valueExpr);
                caseExpr.getItems().add(caseItem);
            }

            if (lexer.token() == QLToken.ELSE) {
                lexer.nextToken();
                caseExpr.setElseExpr(expr());
            }

            accept(QLToken.END);

            sqlExpr = caseExpr;
            break;
        case NOT:
        	throw new ELException("TODO");
        case SUB:
            lexer.nextToken();
            switch (lexer.token()) {
            case LITERAL_INT:
                Number integerValue = lexer.integerValue();
                if (integerValue instanceof Integer) {
                    int intVal = ((Integer) integerValue).intValue();
                    if (intVal == Integer.MIN_VALUE) {
                        integerValue = Long.valueOf(((long) intVal) * -1);
                    } else {
                        integerValue = Integer.valueOf(intVal * -1);
                    }
                } else if (integerValue instanceof Long) {
                    long longVal = ((Long) integerValue).longValue();
                    if (longVal == 2147483648L) {
                        integerValue = Integer.valueOf((int) (((long) longVal) * -1));
                    } else {
                        integerValue = Long.valueOf(longVal * -1);
                    }
                } else {
                    integerValue = ((BigInteger) integerValue).negate();
                }
                sqlExpr = new QLNumberLiteralExpr(integerValue);
                lexer.nextToken();
                break;
            case LITERAL_FLOAT:
                sqlExpr = new QLNumberLiteralExpr(lexer.decimalValue().negate());
                lexer.nextToken();
                break;
            default:
                throw new ELException("TODO");
            }
            break;
        case PLUS:
            lexer.nextToken();
            switch (lexer.token()) {
            case LITERAL_INT:
                sqlExpr = new QLNumberLiteralExpr(lexer.integerValue());
                lexer.nextToken();
                break;
            case LITERAL_FLOAT:
                sqlExpr = new QLNumberLiteralExpr(lexer.decimalValue());
                lexer.nextToken();
                break;
            default:
                throw new ELException("TODO");
            }
            break;
        case QUES:
            lexer.nextToken();
            sqlExpr = new QLVariantRefExpr("?");
            break;
        case NULL:
            sqlExpr = new QLNullExpr();
            lexer.nextToken();
            break;
        case VARIANT:
        	String varName = lexer.stringVal();
        	sqlExpr = new QLVariantRefExpr(varName);
        	lexer.nextToken();
        	break;
        default:
            throw new ELException("ERROR. token : " + tok);
        }

        return primaryRest(sqlExpr);
    }
    
    public QLExpr primaryRest(QLExpr expr) throws ELException {
        if (expr == null) {
            throw new IllegalArgumentException("expr");
        }

        if (lexer.token() == QLToken.DOT) {
            lexer.nextToken();

            if (lexer.token() == QLToken.STAR) {
                lexer.nextToken();
                expr = new QLPropertyExpr(expr, "*");
            } else {
                if (lexer.token() != QLToken.IDENTIFIER) {
                    throw new ELException("error");
                }

                String name = lexer.stringVal();
                lexer.nextToken();

                if (lexer.token() == QLToken.LPAREN) {
                    lexer.nextToken();

                    QLMethodInvokeExpr methodInvokeExpr = new QLMethodInvokeExpr(name);
                    if (lexer.token() == QLToken.RPAREN) {
                        lexer.nextToken();
                    } else {
                        exprList(methodInvokeExpr.getParameters());
                        accept(QLToken.RPAREN);
                    }
                    expr = methodInvokeExpr;
                } else {
                    expr = new QLPropertyExpr(expr, name);
                }
            }

            expr = primaryRest(expr);
        } else if (lexer.token() == QLToken.COLONEQ) {
            lexer.nextToken();
            QLExpr rightExp = primary();
            expr = new QLBinaryOpExpr(expr, QLBinaryOperator.Assignment, rightExp);
        } else {
            if (lexer.token() == QLToken.LPAREN) {
                if (expr instanceof QLIdentifierExpr) {
                    QLIdentifierExpr identExpr = (QLIdentifierExpr) expr;
                    String method_name = identExpr.getName();
                    lexer.nextToken();

                    if (isAggreateFunction(method_name)) {
                        QLAggregateExpr aggregateExpr = parseAggregateExpr(method_name);

                        return aggregateExpr;
                    }

                    QLMethodInvokeExpr methodInvokeExpr = new QLMethodInvokeExpr(method_name);
                    if (lexer.token() != QLToken.RPAREN) {
                        exprList(methodInvokeExpr.getParameters());
                    }

                    accept(QLToken.RPAREN);

                    return primaryRest(methodInvokeExpr);
                }

                throw new ELException("not support token:");
            }
        }

        return expr;
    }
    
    protected QLAggregateExpr parseAggregateExpr(String method_name) throws ELException {
        QLAggregateExpr aggregateExpr;
        if (lexer.token() == QLToken.ALL) {
            aggregateExpr = new QLAggregateExpr(method_name, 1);
            lexer.nextToken();
        } else if (lexer.token() == QLToken.DISTINCT) {
            aggregateExpr = new QLAggregateExpr(method_name, 0);
            lexer.nextToken();
        } else {
            aggregateExpr = new QLAggregateExpr(method_name, 1);
        }
        exprList(aggregateExpr.getArguments());

        accept(QLToken.RPAREN);

        return aggregateExpr;
    }
    
    public boolean isAggreateFunction(String word) {
        String[] aggregateFunctions = { "AVG", "COUNT", "MAX", "MIN", "STDDEV", "SUM" };

        for (int i = 0; i < aggregateFunctions.length; ++i) {
            if (aggregateFunctions[i].compareToIgnoreCase(word) == 0) {
                return true;
            }
        }

        return false;
    }
    
    public final void exprList(Collection<QLExpr> exprCol) throws ELException {
        if (lexer.token() == QLToken.RPAREN) {
            return;
        }

        if (lexer.token() == QLToken.EOF) {
            return;
        }

        QLExpr expr = expr();
        exprCol.add(expr);

        while (lexer.token() == QLToken.COMMA) {
            lexer.nextToken();
            expr = expr();
            exprCol.add(expr);
        }
    }


}
