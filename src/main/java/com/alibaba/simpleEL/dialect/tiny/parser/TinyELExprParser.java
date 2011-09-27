package com.alibaba.simpleEL.dialect.tiny.parser;

import java.math.BigInteger;
import java.util.Collection;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELArrayAccessExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOperator;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBooleanExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELConditionalExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNewExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNullExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELNumberLiteralExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELPropertyExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELStringExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELVariantRefExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyUnaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyUnaryOperator;

public class TinyELExprParser {

    protected final TinyELLexer lexer;

    public TinyELExprParser(TinyELLexer lexer){
        super();
        this.lexer = lexer;
    }

    public TinyELExprParser(String input){
        this(new TinyELLexer(input));
        this.lexer.nextToken();
    }

    public TinyELLexer getLexer() {
        return lexer;
    }

    public void accept(TinyELToken token) {
        if (lexer.token() == token) {
            lexer.nextToken();
        } else {
            throw new ELException("syntax error, expect " + token + ", actual " + lexer.token());
        }
    }

    public TinyELExpr expr() {
        TinyELExpr expr = primary();

        if (lexer.token() == TinyELToken.COMMA) {
            return expr;
        }

        return exprRest(expr);
    }

    public TinyELExpr exprRest(TinyELExpr expr) {

        expr = multiplicativeRest(expr);
        expr = additiveRest(expr);
        expr = shiftRest(expr);
        expr = relationalRest(expr);
        expr = equalityRest(expr);
        expr = bitAndRest(expr);
        expr = bitXorRest(expr);
        expr = bitOrRest(expr);
        expr = andRest(expr);
        expr = orRest(expr);
        expr = conditionalRest(expr);
        expr = assignRest(expr);

        return expr;
    }

    // ^
    public final TinyELExpr bitXor() {
        TinyELExpr expr = bitAnd();
        return bitXorRest(expr);
    }

    // ^
    public TinyELExpr bitXorRest(TinyELExpr expr) {
        if (lexer.token() == TinyELToken.CARET) {
            lexer.nextToken();
            TinyELExpr rightExp = bitAnd();
            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.BitwiseXor, rightExp);
            expr = bitXorRest(expr);
        }

        return expr;
    }

    public final TinyELExpr multiplicative() {
        TinyELExpr expr = primary();
        return multiplicativeRest(expr);
    }

    public TinyELExpr multiplicativeRest(TinyELExpr expr) {
        if (lexer.token() == TinyELToken.STAR) {
            lexer.nextToken();
            TinyELExpr rightExp = primary();
            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.Multiply, rightExp);
            expr = multiplicativeRest(expr);
        } else if (lexer.token() == TinyELToken.SLASH) {
            lexer.nextToken();
            TinyELExpr rightExp = primary();
            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.Divide, rightExp);
            expr = multiplicativeRest(expr);
        } else if (lexer.token() == TinyELToken.PERCENT) {
            lexer.nextToken();
            TinyELExpr rightExp = primary();
            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.Modulus, rightExp);
            expr = multiplicativeRest(expr);
        }
        return expr;
    }

    // &
    public final TinyELExpr bitAnd() {
        TinyELExpr expr = equality();
        return orRest(expr);
    }

    public final TinyELExpr bitAndRest(TinyELExpr expr) {
        while (lexer.token() == TinyELToken.AMP) {
            lexer.nextToken();
            TinyELExpr rightExp = equality();
            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.BitwiseAnd, rightExp);
        }
        return expr;
    }

    // |
    public final TinyELExpr bitOr() {
        TinyELExpr expr = bitXor();
        return orRest(expr);
    }

    // |
    public final TinyELExpr bitOrRest(TinyELExpr expr) {
        while (lexer.token() == TinyELToken.BAR) {
            lexer.nextToken();
            TinyELExpr rightExp = bitXor();
            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.BitwiseOr, rightExp);
        }
        return expr;
    }

    public final TinyELExpr equality() {
        TinyELExpr expr = relational();
        return equalityRest(expr);
    }

    public final TinyELExpr equalityRest(TinyELExpr expr) {
        TinyELExpr rightExp;
        if (lexer.token() == TinyELToken.EQEQ) {
            lexer.nextToken();
            rightExp = relational();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.Equality, rightExp);
        } else if (lexer.token() == TinyELToken.BANGEQ) {
            lexer.nextToken();
            rightExp = relational();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.NotEqual, rightExp);
        }

        return expr;
    }

    public final TinyELExpr additive() {
        TinyELExpr expr = multiplicative();
        return additiveRest(expr);
    }

    // + -
    public TinyELExpr additiveRest(TinyELExpr expr) {
        if (lexer.token() == TinyELToken.PLUS) {
            lexer.nextToken();
            TinyELExpr rightExp = multiplicative();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.Add, rightExp);
            expr = additiveRest(expr);
        } else if (lexer.token() == TinyELToken.SUB) {
            lexer.nextToken();
            TinyELExpr rightExp = multiplicative();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.Subtract, rightExp);
            expr = additiveRest(expr);
        }

        return expr;
    }

    public final TinyELExpr shift() {
        TinyELExpr expr = additive();
        return shiftRest(expr);
    }

    public TinyELExpr shiftRest(TinyELExpr expr) {
        if (lexer.token() == TinyELToken.LTLT) {
            lexer.nextToken();
            TinyELExpr rightExp = additive();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.LeftShift, rightExp);
            expr = shiftRest(expr);
        } else if (lexer.token() == TinyELToken.GTGT) {
            lexer.nextToken();
            TinyELExpr rightExp = additive();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.RightShift, rightExp);
            expr = shiftRest(expr);
        }

        return expr;
    }

    public final TinyELExpr and() {
        TinyELExpr expr = bitOr();
        return andRest(expr);
    }

    // &&
    public final TinyELExpr andRest(TinyELExpr expr) {
        if (lexer.token() == TinyELToken.AMPAMP) {
            lexer.nextToken();
            TinyELExpr rightExp = bitOr();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.BooleanAnd, rightExp);
            expr = andRest(expr);
        }

        return expr;
    }

    public final TinyELExpr or() {
        TinyELExpr expr = and();
        return orRest(expr);
    }

    // ||
    public final TinyELExpr orRest(TinyELExpr expr) {
        if (lexer.token() == TinyELToken.BARBAR) {
            lexer.nextToken();
            TinyELExpr rightExp = and();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.BooleanOr, rightExp);
            expr = orRest(expr);
        }

        return expr;
    }

    public final TinyELExpr conditional() {
        TinyELExpr expr = or();
        return conditionalRest(expr);
    }

    // ?:
    public final TinyELExpr conditionalRest(TinyELExpr expr) {
        if (lexer.token() == TinyELToken.QUES) {
            lexer.nextToken();
            TinyELExpr trueExpr = expr();
            accept(TinyELToken.COLON);
            TinyELExpr falseExpr = expr();
            expr = new TinyELConditionalExpr(expr, trueExpr, falseExpr);

            expr = conditionalRest(expr);
        }
        return expr;
    }

    public final TinyELExpr assign() {
        TinyELExpr expr = conditional();
        return assignRest(expr);
    }

    // = += -= *= /= %= &= |= ^= ~= <<= >>= >>>=
    public final TinyELExpr assignRest(TinyELExpr expr) {
        if (lexer.token() == TinyELToken.EQ) {
            lexer.nextToken();
            TinyELExpr rightExp = conditional();
            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.Assignment, rightExp);
            expr = assignRest(expr);
        }

        if (lexer.token() == TinyELToken.PLUSEQ) {
            lexer.nextToken();
            TinyELExpr rightExp = conditional();
            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.AddAndAssignment, rightExp);

            expr = assignRest(expr);
        }

        return expr;
    }

    public final TinyELExpr relational() {
        TinyELExpr expr = shift();

        return relationalRest(expr);
    }

    public TinyELExpr relationalRest(TinyELExpr expr) {
        TinyELExpr rightExp;
        if (lexer.token() == TinyELToken.LT) {
            lexer.nextToken();
            rightExp = shift();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.LessThan, rightExp);
        } else if (lexer.token() == TinyELToken.LTEQ) {
            lexer.nextToken();
            rightExp = shift();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.LessThanOrEqual, rightExp);
        } else if (lexer.token() == TinyELToken.GT) {
            lexer.nextToken();
            rightExp = shift();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.GreaterThan, rightExp);
        } else if (lexer.token() == TinyELToken.GTEQ) {
            lexer.nextToken();
            rightExp = shift();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.GreaterThanOrEqual, rightExp);
        } else if (lexer.token() == TinyELToken.INSTNACEOF) {
            lexer.nextToken();
            rightExp = shift();

            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.InstanceOf, rightExp);
        }

        return expr;
    }

    public final TinyELExpr name() throws ELException {
        if (lexer.token() != TinyELToken.IDENTIFIER) {
            throw new ELException("error : " + lexer.token());
        }

        String identName = lexer.stringVal();

        lexer.nextToken();

        TinyELExpr name = new TinyELIdentifierExpr(identName);

        while (lexer.token() == TinyELToken.DOT) {
            lexer.nextToken();

            if (lexer.token() != TinyELToken.IDENTIFIER) {
                throw new ELException("error : " + lexer.token());
            }

            name = new TinyELPropertyExpr(name, lexer.stringVal());
            lexer.nextToken();
        }

        return name;
    }

    public String type() {
        final TinyELToken tok = lexer.token();
        switch (tok) {
            case BYTE:
            case SHORT:
            case INT:
            case LONG:
            case FLOAT:
            case DOUBLE:
                lexer.nextToken();
                return tok.name;
            default:
                return name().toString();
        }
    }

    public TinyELExpr primary() {
        TinyELExpr primaryExpr = null;

        final TinyELToken tok = lexer.token();

        switch (tok) {
            case LPAREN:
                lexer.nextToken();
                primaryExpr = expr();
                accept(TinyELToken.RPAREN);
                break;
            case IDENTIFIER:
                primaryExpr = new TinyELIdentifierExpr(lexer.stringVal());
                lexer.nextToken();
                break;
            case PLUSPLUS:
                lexer.nextToken();
                primaryExpr = expr();
                primaryExpr = new TinyUnaryOpExpr(primaryExpr, TinyUnaryOperator.PreIncrement);
                break;
            case SUBSUB:
                lexer.nextToken();
                primaryExpr = expr();
                primaryExpr = new TinyUnaryOpExpr(primaryExpr, TinyUnaryOperator.PreDecrement);
                break;
            case BANG:
                lexer.nextToken();
                primaryExpr = expr();
                primaryExpr = new TinyUnaryOpExpr(primaryExpr, TinyUnaryOperator.Not);
                break;
            case NEW:
                lexer.nextToken();

                TinyELExpr name = name();
                String typeName = name.toString();

                if (lexer.token() == TinyELToken.LPAREN) {
                    lexer.nextToken();

                    TinyELNewExpr newExpr = new TinyELNewExpr(typeName);
                    if (lexer.token() != TinyELToken.RPAREN) {
                        exprList(newExpr.getParameters());
                    }

                    accept(TinyELToken.RPAREN);

                    return primaryRest(newExpr);
                }
                throw new ELException("TODO " + lexer.token());
            case TRUE:
                primaryExpr = new TinyELBooleanExpr(true);
                lexer.nextToken();
                break;
            case FALSE:
                primaryExpr = new TinyELBooleanExpr(false);
                lexer.nextToken();
                break;
            case LITERAL_INT:
                primaryExpr = new TinyELNumberLiteralExpr(lexer.integerValue());
                lexer.nextToken();
                break;
            case LITERAL_FLOAT:
                primaryExpr = new TinyELNumberLiteralExpr(lexer.decimalValue());
                lexer.nextToken();
                break;
            case LITERAL_STRING:
                primaryExpr = new TinyELStringExpr(lexer.stringVal());
                lexer.nextToken();
                break;
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
                        primaryExpr = new TinyELNumberLiteralExpr(integerValue);
                        lexer.nextToken();
                        break;
                    case LITERAL_FLOAT:
                        primaryExpr = new TinyELNumberLiteralExpr(lexer.decimalValue().negate());
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
                        primaryExpr = new TinyELNumberLiteralExpr(lexer.integerValue());
                        lexer.nextToken();
                        break;
                    case LITERAL_FLOAT:
                        primaryExpr = new TinyELNumberLiteralExpr(lexer.decimalValue());
                        lexer.nextToken();
                        break;
                    default:
                        throw new ELException("TODO");
                }
                break;
            case QUES:
                lexer.nextToken();
                primaryExpr = new TinyELVariantRefExpr("?");
                break;
            case NULL:
                primaryExpr = new TinyELNullExpr();
                lexer.nextToken();
                break;
            case VARIANT:
                String varName = lexer.stringVal();
                primaryExpr = new TinyELVariantRefExpr(varName);
                lexer.nextToken();
                break;
            default:
                throw new ELException("ERROR. token : " + tok);
        }

        return primaryRest(primaryExpr);
    }

    public TinyELExpr primaryRest(TinyELExpr expr) throws ELException {
        if (expr == null) {
            throw new IllegalArgumentException("expr");
        }

        if (lexer.token() == TinyELToken.DOT) {
            lexer.nextToken();

            if (lexer.token() == TinyELToken.STAR) {
                lexer.nextToken();
                expr = new TinyELPropertyExpr(expr, "*");
            } else {
                if (lexer.token() != TinyELToken.IDENTIFIER) {
                    throw new ELException("error");
                }

                String name = lexer.stringVal();
                lexer.nextToken();

                if (lexer.token() == TinyELToken.LPAREN) {
                    lexer.nextToken();

                    TinyELMethodInvokeExpr methodInvokeExpr = new TinyELMethodInvokeExpr(name, expr);
                    if (lexer.token() == TinyELToken.RPAREN) {
                        lexer.nextToken();
                    } else {
                        exprList(methodInvokeExpr.getParameters());
                        accept(TinyELToken.RPAREN);
                    }
                    expr = methodInvokeExpr;
                } else {
                    expr = new TinyELPropertyExpr(expr, name);
                }
            }

            expr = primaryRest(expr);
        } else if (lexer.token() == TinyELToken.PLUSPLUS) {
            lexer.nextToken();
            expr = new TinyUnaryOpExpr(expr, TinyUnaryOperator.PostIncrement);
            expr = primaryRest(expr);
        } else if (lexer.token() == TinyELToken.SUBSUB) {
            lexer.nextToken();
            expr = new TinyUnaryOpExpr(expr, TinyUnaryOperator.PostDecrement);
            expr = primaryRest(expr);
        } else if (lexer.token() == TinyELToken.LBRACKET) {
            lexer.nextToken();
            TinyELExpr indexExpr = expr();
            accept(TinyELToken.RBRACKET);
            expr = new TinyELArrayAccessExpr(expr, indexExpr);
            expr = primaryRest(expr);
        } else if (lexer.token() == TinyELToken.COLONEQ) {
            lexer.nextToken();
            TinyELExpr rightExp = primary();
            expr = new TinyELBinaryOpExpr(expr, TinyELBinaryOperator.Assignment, rightExp);
        } else {
            if (lexer.token() == TinyELToken.LPAREN) {
                if (expr instanceof TinyELIdentifierExpr) {
                    TinyELIdentifierExpr identExpr = (TinyELIdentifierExpr) expr;
                    String method_name = identExpr.getName();
                    lexer.nextToken();

                    TinyELMethodInvokeExpr methodInvokeExpr = new TinyELMethodInvokeExpr(method_name);
                    if (lexer.token() != TinyELToken.RPAREN) {
                        exprList(methodInvokeExpr.getParameters());
                    }

                    accept(TinyELToken.RPAREN);

                    return primaryRest(methodInvokeExpr);
                }

                throw new ELException("not support token:");
            }
        }

        return expr;
    }

    public final void exprList(Collection<TinyELExpr> exprCol) throws ELException {
        if (lexer.token() == TinyELToken.RPAREN) {
            return;
        }

        if (lexer.token() == TinyELToken.EOF) {
            return;
        }

        TinyELExpr expr = expr();
        exprCol.add(expr);

        while (lexer.token() == TinyELToken.COMMA) {
            lexer.nextToken();
            expr = expr();
            exprCol.add(expr);
        }
    }

}
