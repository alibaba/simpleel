package com.alibaba.simpleEL.dialect.ql.parser;

public enum QLToken {
	SELECT("SELECT"),
    FROM("FROM"),
    WHERE("WHERE"),
    ORDER("ORDER"),
    BY("BY"),
    GROUP("GROUP"),
    HAVING("HAVING"),
    NULL("NULL"),
    NOT("NOT"),
    AS("AS"),
    INSERT("INSERT"),
    ALL("ALL"),
    DISTINCT("DISTINCT"),
    
    AND("AND"),
    OR("OR"),
    XOR("XOR"),
    CASE("CASE"),
    WHEN("WHEN"),
    THEN("THEN"),
    ELSE("ELSE"),
    END("END"),
    EXISTS("EXISTS"),
    IN("IN"),
    LIMIT("LIMIT"),
    
    NEW("NEW"),
    ASC("ASC"),
    DESC("DESC"),
    IS("IS"),
    LIKE("LIKE"),
    ESCAPE("ESCAPE"),
    BETWEEN("BETWEEN"),
    VALUES("VALUES"),
    INTERVAL("INTERVAL"),
    
    VARIANT,

    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    LBRACKET("["),
    RBRACKET("]"),
    SEMI(";"),
    COMMA(","),
    DOT("."),
    EQ("="),
    GT(">"),
    LT("<"),
    BANG("!"),
    TILDE("~"),
    QUES("?"),
    COLON(":"),
    COLONEQ(":="),
    EQEQ("=="),
    LTEQ("<="),
    LTEQGT("<=>"),
    LTGT("<>"),
    GTEQ(">="),
    BANGEQ("!="),
    BANGGT("!>"),
    BANGLT("!<"),
    AMPAMP("&&"),
    BARBAR("||"),
    PLUS("+"),
    SUB("-"),
    STAR("*"),
    SLASH("/"),
    AMP("&"),
    BAR("|"),
    CARET("^"),
    PERCENT("%"),
    LTLT("<<"),
    GTGT(">>"),
    MONKEYS_AT("@"),
    
    EOF,
    ERROR,
    IDENTIFIER,
    HINT,
    //QS_TODO add support in Lexer
    SYS_VAR,
    USR_VAR,
    /** number composed purely of digit */
    LITERAL_NUM_PURE_DIGIT,
    /** number composed of digit mixed with <code>.</code> or <code>e</code> */
    LITERAL_NUM_MIX_DIGIT,
    LITERAL_HEX,
    //QS_TODO add syntax support
    LITERAL_BIT,
    LITERAL_CHARS,
    LITERAL_NCHARS,
    LITERAL_ALIAS,
    ;
	
    public final String name;

    QLToken() {
        this(null);
    }

    QLToken(String name) {
        this.name = name;
    }
}
