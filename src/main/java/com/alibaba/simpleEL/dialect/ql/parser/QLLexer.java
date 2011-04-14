package com.alibaba.simpleEL.dialect.ql.parser;

import static com.alibaba.simpleEL.dialect.ql.parser.CharTypes.isFirstIdentifierChar;
import static com.alibaba.simpleEL.dialect.ql.parser.CharTypes.isIdentifierChar;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.COLON;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.COLONEQ;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.COMMA;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.EOF;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.ERROR;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.LBRACE;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.LBRACKET;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.LITERAL_CHARS;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.LPAREN;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.RBRACE;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.RBRACKET;
import static com.alibaba.simpleEL.dialect.ql.parser.QLToken.RPAREN;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.alibaba.simpleEL.ELException;

/**
 * 
 * @author shaojin.wensj
 * 
 */
public class QLLexer {
	public final static byte EOI = 0x1A;
	
	protected final char[] buf;
	protected int bp;
	protected int buflen;
	protected int eofPos;

	/**
	 * The current character.
	 */
	protected char ch;

	/**
	 * The token's position, 0-based offset from beginning of text.
	 */
	protected int pos;

	/**
	 * A character buffer for literals.
	 */
	protected char[] sbuf;
	protected int sp;

	protected int np;

	protected SymbolTable symbolTable = new SymbolTable();

	/**
	 * The token, set by nextToken().
	 */
	protected QLToken token;

	protected Keywords keywods = Keywords.DEFAULT_KEYWORDS;

	protected final static ThreadLocal<char[]> sbufRef = new ThreadLocal<char[]>();

	protected String stringVal;

	public QLLexer(String input) {
		this(input.toCharArray(), input.length());
	}

	public QLLexer(char[] input, int inputLength) {
		sbuf = sbufRef.get(); // new char[1024];
		if (sbuf == null) {
			sbuf = new char[1024];
			sbufRef.set(sbuf);
		}

		eofPos = inputLength;

		if (inputLength == input.length) {
			if (input.length > 0 && isWhitespace(input[input.length - 1])) {
				inputLength--;
			} else {
				char[] newInput = new char[inputLength + 1];
				System.arraycopy(input, 0, newInput, 0, input.length);
				input = newInput;
			}
		}
		buf = input;
		buflen = inputLength;
		buf[buflen] = EOI;
		bp = -1;

		scanChar();
	}

	protected final void scanChar() {
		ch = buf[++bp];
	}

	/**
	 * Report an error at the given position using the provided arguments.
	 */
	protected void lexError(int pos, String key, Object... args) {
		token = ERROR;
	}

	/**
	 * Report an error at the current token position using the provided
	 * arguments.
	 */
	private void lexError(String key, Object... args) {
		lexError(pos, key, args);
	}

	/**
	 * Return the current token, set by nextToken().
	 */
	public final QLToken token() {
		return token;
	}

	public final void nextToken() {
		sp = 0;

		for (;;) {
			pos = bp;

			if (isWhitespace(ch)) {
				scanChar();
				continue;
			}

			if (isFirstIdentifierChar(ch)) {
				if (ch == 'N') {
					if (buf[bp + 1] == '\'') {
						bp++;
						ch = '\'';
						scanString();
						token = QLToken.LITERAL_NCHARS;
						return;
					}
				}

				scanIdent();
				return;
			}

			if (ch == ',') {
				scanChar();
				token = COMMA;
				return;
			}

			switch (ch) {
			case '0':
				if (buf[bp + 1] == 'x') {
					scanChar();
					scanChar();
					scanHexNumber();
				} else {
					scanNumber();
				}
				return;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				scanNumber();
				return;
			case '(':
				scanChar();
				token = LPAREN;
				return;
			case ')':
				scanChar();
				token = RPAREN;
				return;
			case '[':
				scanChar();
				token = LBRACKET;
				return;
			case ']':
				scanChar();
				token = RBRACKET;
				return;
			case '{':
				scanChar();
				token = LBRACE;
				return;
			case '}':
				scanChar();
				token = RBRACE;
				return;
			case ':':
				scanChar();
				if (ch == '=') {
					scanChar();
					token = COLONEQ;
				} else {
					token = COLON;
				}
				return;
			case '.':
				scanChar();
				token = QLToken.DOT;
				return;
			case '\'':
				scanString();
				return;
			case '\"':
				scanAlias();
				return;
			case '*':
				scanChar();
				token = QLToken.STAR;
				return;
			case '?':
				scanChar();
				token = QLToken.QUES;
				return;
			case ';':
				scanChar();
				token = QLToken.SEMI;
				return;
			case '`':
				throw new ELException("TODO"); // TODO
			case '@':
				scanVariable();
				token = QLToken.Variant;
				return;
			default:
				if (Character.isLetter(ch)) {
					scanIdent();
					return;
				}

				if (isOperator(ch)) {
					scanOperator();
					return;
				}

				if (bp == buflen || ch == EOI && bp + 1 == buflen) { // JLS
					token = EOF;
					pos = bp = eofPos;
				} else {
					lexError("illegal.char", String.valueOf((int) ch));
					scanChar();
				}

				return;
			}
		}

	}
	
	public static final boolean isWhitespace(char ch) {
		// 专门调整了判断顺序
		return ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t' || ch == '\f' || ch == '\b';
	}

	private final void scanOperator() {
		switch (ch) {
		case '+':
			scanChar();
			token = QLToken.PLUS;
			break;
		case '-':
			scanChar();
			token = QLToken.SUB;
			break;
		case '*':
			scanChar();
			token = QLToken.STAR;
			break;
		case '/':
			scanChar();
			token = QLToken.SLASH;
			break;
		case '&':
			scanChar();
			if (ch == '&') {
				scanChar();
				token = QLToken.AMPAMP;
			} else {
				token = QLToken.AMP;
			}
			break;
		case '|':
			scanChar();
			if (ch == '|') {
				scanChar();
				token = QLToken.BARBAR;
			} else {
				token = QLToken.BAR;
			}
			break;
		case '^':
			scanChar();
			token = QLToken.CARET;
			break;
		case '%':
			scanChar();
			token = QLToken.PERCENT;
			break;
		case '=':
			scanChar();
			if (ch == '=') {
				scanChar();
				token = QLToken.EQEQ;
			} else {
				token = QLToken.EQ;
			}
			break;
		case '>':
			scanChar();
			if (ch == '=') {
				scanChar();
				token = QLToken.GTEQ;
			} else if (ch == '>') {
				scanChar();
				token = QLToken.GTGT;
			} else {
				token = QLToken.GT;
			}
			break;
		case '<':
			scanChar();
			if (ch == '=') {
				scanChar();
				if (ch == '>') {
					token = QLToken.LTEQGT;
					scanChar();
				} else {
					token = QLToken.LTEQ;
				}
			} else if (ch == '>') {
				scanChar();
				token = QLToken.LTGT;
			} else if (ch == '<') {
				scanChar();
				token = QLToken.LTLT;
			} else {
				token = QLToken.LT;
			}
			break;
		case '!':
			scanChar();
			if (ch == '=') {
				scanChar();
				token = QLToken.BANGEQ;
			} else if (ch == '>') {
				scanChar();
				token = QLToken.BANGGT;
			} else if (ch == '<') {
				scanChar();
				token = QLToken.BANGLT;
			} else {
				token = QLToken.BANG;
			}
			break;
		case '?':
			scanChar();
			token = QLToken.QUES;
			break;
		case '~':
			scanChar();
			token = QLToken.TILDE;
			break;
		default:
			throw new ELException("TODO");
		}
	}

	protected void scanString() {
		np = bp;
		boolean hasSpecial = false;

		for (;;) {
			if (bp >= buflen) {
				lexError(pos, "unclosed.str.lit");
				return;
			}

			ch = buf[++bp];

			if (ch == '\'') {
				scanChar();
				if (ch != '\'') {
					token = LITERAL_CHARS;
					break;
				} else {
					System.arraycopy(buf, np + 1, sbuf, 0, sp);
					hasSpecial = true;
					putChar('\'');
					continue;
				}
			} 

			if (!hasSpecial) {
				sp++;
				continue;
			}

			if (sp == sbuf.length) {
				putChar(ch);
			} else {
				sbuf[sp++] = ch;
			}
		}

		if (!hasSpecial) {
			stringVal = new String(buf, np + 1, sp);
		} else {
			stringVal = new String(sbuf, 0, sp);
		}
	}

	private final void scanAlias() {
		for (;;) {
			if (bp >= buflen) {
				lexError(pos, "unclosed.str.lit");
				return;
			}

			ch = buf[++bp];

			if (ch == '\"') {
				scanChar();
				token = QLToken.LITERAL_ALIAS;
				return;
			}

			if (sp == sbuf.length) {
				putChar(ch);
			} else {
				sbuf[sp++] = ch;
			}
		}
	}

	public void scanVariable() {
		final char first = ch;

		if (ch != '@' && ch != ':') {
			throw new ELException("illegal variable");
		}

		int hash = first;

		np = bp;
		sp = 1;
		char ch;
		for (;;) {
			ch = buf[++bp];

			if (!isIdentifierChar(ch)) {
				break;
			}

			hash = 31 * hash + ch;

			sp++;
			continue;
		}

		this.ch = buf[bp];

		stringVal = symbolTable.addSymbol(buf, np, sp, hash);
		QLToken tok = keywods.getKeyword(stringVal);
		if (tok != null) {
			token = tok;
		} else {
			token = QLToken.IDENTIFIER;
		}
	}

	public void scanIdent() {
		final char first = ch;

		final boolean firstFlag = isFirstIdentifierChar(first);
		if (!firstFlag) {
			throw new ELException("illegal identifier");
		}

		int hash = first;

		np = bp;
		sp = 1;
		char ch;
		for (;;) {
			ch = buf[++bp];

			if (!isIdentifierChar(ch)) {
				break;
			}

			hash = 31 * hash + ch;

			sp++;
			continue;
		}

		this.ch = buf[bp];

		stringVal = symbolTable.addSymbol(buf, np, sp, hash);
		QLToken tok = keywods.getKeyword(stringVal);
		if (tok != null) {
			token = tok;
		} else {
			token = QLToken.IDENTIFIER;
		}
	}

	public void scanNumber() {
		np = bp;

		if (ch == '-') {
			sp++;
			ch = buf[++bp];
		}

		for (;;) {
			if (ch >= '0' && ch <= '9') {
				sp++;
			} else {
				break;
			}
			ch = buf[++bp];
		}

		boolean isDouble = false;

		if (ch == '.') {
			sp++;
			ch = buf[++bp];
			isDouble = true;

			for (;;) {
				if (ch >= '0' && ch <= '9') {
					sp++;
				} else {
					break;
				}
				ch = buf[++bp];
			}
		}

		if (ch == 'e' || ch == 'E') {
			sp++;
			ch = buf[++bp];

			if (ch == '+' || ch == '-') {
				sp++;
				ch = buf[++bp];
			}

			for (;;) {
				if (ch >= '0' && ch <= '9') {
					sp++;
				} else {
					break;
				}
				ch = buf[++bp];
			}

			isDouble = true;
		}

		if (isDouble) {
			token = QLToken.LITERAL_NUM_MIX_DIGIT;
		} else {
			token = QLToken.LITERAL_NUM_PURE_DIGIT;
		}
	}

	public void scanHexNumber() {
		np = bp;

		if (ch == '-') {
			sp++;
			ch = buf[++bp];
		}

		for (;;) {
			if (CharTypes.isHex(ch)) {
				sp++;
			} else {
				break;
			}
			ch = buf[++bp];
		}

		token = QLToken.LITERAL_HEX;
	}

	public String hexString() throws NumberFormatException {
		return new String(buf, np, sp);
	}

	public final boolean isDigit(char ch) {
		return ch >= '0' && ch <= '9';
	}

	/**
	 * Append a character to sbuf.
	 */
	protected final void putChar(char ch) {
		if (sp == sbuf.length) {
			char[] newsbuf = new char[sbuf.length * 2];
			System.arraycopy(sbuf, 0, newsbuf, 0, sbuf.length);
			sbuf = newsbuf;
		}
		sbuf[sp++] = ch;
	}

	/**
	 * Return the current token's position: a 0-based offset from beginning of
	 * the raw input stream (before unicode translation)
	 */
	public final int pos() {
		return pos;
	}

	/**
	 * The value of a literal token, recorded as a string. For integers, leading
	 * 0x and 'l' suffixes are suppressed.
	 */
	public final String stringVal() {
		return stringVal;
	}

	private boolean isOperator(char ch) {
		switch (ch) {
		case '!':
		case '%':
		case '&':
		case '*':
		case '+':
		case '-':
		case '<':
		case '=':
		case '>':
		case '^':
		case '|':
		case '~':
		case '/':
		case ';':
			return true;
		default:
			return false;
		}
	}

	private static final long MULTMIN_RADIX_TEN = Long.MIN_VALUE / 10;
	private static final long N_MULTMAX_RADIX_TEN = -Long.MAX_VALUE / 10;

	private final static int[] digits = new int[(int) '9' + 1];

	static {
		for (int i = '0'; i <= '9'; ++i) {
			digits[i] = i - '0';
		}
	}

	public Number integerValue() throws NumberFormatException {
		long result = 0;
		boolean negative = false;
		int i = np, max = np + sp;
		long limit;
		long multmin;
		int digit;

		if (buf[np] == '-') {
			negative = true;
			limit = Long.MIN_VALUE;
			i++;
		} else {
			limit = -Long.MAX_VALUE;
		}
		multmin = negative ? MULTMIN_RADIX_TEN : N_MULTMAX_RADIX_TEN;
		if (i < max) {
			digit = digits[buf[i++]];
			result = -digit;
		}
		while (i < max) {
			// Accumulating negatively avoids surprises near MAX_VALUE
			digit = digits[buf[i++]];
			if (result < multmin) {
				return new BigInteger(numberString());
			}
			result *= 10;
			if (result < limit + digit) {
				return new BigInteger(numberString());
			}
			result -= digit;
		}

		if (negative) {
			if (i > np + 1) {
				if (result >= Integer.MIN_VALUE) {
					return (int) result;
				}
				return result;
			} else { /* Only got "-" */
				throw new NumberFormatException(numberString());
			}
		} else {
			result = -result;
			if (result <= Integer.MAX_VALUE) {
				return (int) result;
			}
			return result;
		}
	}

	public final String numberString() {
		return new String(buf, np, sp);
	}

	public BigDecimal decimalValue() {
		return new BigDecimal(buf, np, sp);
	}
}
