package com.alibaba.simpleEL.dialect.tiny;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELVariantRefExpr;
import com.alibaba.simpleEL.dialect.tiny.parser.TinyELExprParser;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELOutputVisitor;
import com.alibaba.simpleEL.preprocess.TemplatePreProcessor;

public class TinyELPreprocessor extends TemplatePreProcessor {
	public final static String DEFAULT_PACKAGE_NAME = "com.alibaba.simpleEL.dialect.tiny.gen";

	private final Map<String, Method> functions = new HashMap<String, Method>();

	public TinyELPreprocessor() {
		this.packageName = DEFAULT_PACKAGE_NAME;
	}

	public Map<String, Method> getFunctions() {
		return this.functions;
	}

	@Override
	public JavaSource handle(Map<String, Object> context, String text) {
		if (variantResolver == null) {
			throw new IllegalStateException("variantResolver is null");
		}

		TinyELExprParser parser = new TinyELExprParser(text);
		TinyELExpr expr = parser.expr();

		StringWriter out = new StringWriter();
		JavaSourceGenVisitor visitor = new JavaSourceGenVisitor(new PrintWriter(out));
		expr.accept(visitor);

		String resolvedResult = out.toString();
		
		System.out.println(resolvedResult);

		if (!allowMultiStatement) {
			resolvedResult = "return " + resolvedResult + ";";
		}

		final String className = "GenClass_" + digits();

		String source = template.replace("$packageName", packageName)//
				.replace("$className", className)//
				.replace("$expression", resolvedResult);

		JavaSource javaSource = new JavaSource(packageName, className, source);

		return javaSource;
	}

	public class JavaSourceGenVisitor extends TinyELOutputVisitor {
		public JavaSourceGenVisitor(PrintWriter out) {
			super(out);
		}

		@Override
		public boolean visit(TinyELIdentifierExpr x) {
			String ident = x.getName();
			String result = variantResolver.resolve(ident);
			out.print(result);
			return false;
		}

		@Override
		public boolean visit(TinyELVariantRefExpr x) {
			String ident = x.getName();
			String result = variantResolver.resolve(ident);
			out.print(result);
			return false;
		}

		@Override
		public boolean visit(TinyELMethodInvokeExpr x) {
			if (x.getOwner() == null) {
				String methodName = x.getMethodName();
				Method method = functions.get(methodName);

				if (method == null) {
					return super.visit(x);
				}

				Class<?>[] types = method.getParameterTypes();
				if (!method.isVarArgs()) {
					if (types.length != x.getParameters().size()) {
						return super.visit(x);
					}
					
					String className = method.getDeclaringClass().getName();
					className = className.replaceAll("\\$", "."); // inner class
					out.print(className);
					out.print(".");
					out.print(method.getName());
					
			        out.print("(");
			        for (int i =0, size = x.getParameters().size(); i < size; ++i) {
			            if (i != 0) {
			                out.print(",");
			            }
			            visitMethodParameter(types[i], x.getParameters().get(i));
			        }
			        
			        out.print(")");
			        return false;
				} else {
					if (types.length > x.getParameters().size()) {
						return super.visit(x);
					}
					
					String className = method.getDeclaringClass().getName();
					className = className.replaceAll("\\$", "."); // inner class
					out.print(className);
					out.print(".");
					out.print(method.getName());
					
			        out.print("(");
			        for (int i =0, size = x.getParameters().size(); i < size; ++i) {
			            if (i != 0) {
			                out.print(",");
			            }
			            
			            Class<?> type;
			            if (i >= types.length) {
			            	type = types[types.length - 1];
			            } else {
			            	type = types[i];
			            }
			            visitMethodParameter(type, x.getParameters().get(i));
			        }
			        
			        out.print(")");
				}
			}

			return super.visit(x);
		}
		
		public void visitMethodParameter(Class<?> type, TinyELExpr exp) {
			if (boolean.class == type) {
				out.print("_bool(");
				exp.accept(this);
				out.print(")");
			} else if (String.class == type) {
				out.print("_string(");
				exp.accept(this);
				out.print(")");
			} else if (byte.class == type) {
				out.print("_byte(");
				exp.accept(this);
				out.print(")");
			} else if (short.class == type) {
				out.print("_short(");
				exp.accept(this);
				out.print(")");
			} else if (int.class == type) {
				out.print("_int(");
				exp.accept(this);
				out.print(")");
			} else if (long.class == type) {
				out.print("_long(");
				exp.accept(this);
				out.print(")");
			} else if (float.class == type) {
				out.print("_float(");
				exp.accept(this);
				out.print(")");
			} else if (double.class == type) {
				out.print("_double(");
				exp.accept(this);
				out.print(")");
			} else if (BigInteger.class == type) {
				out.print("_bigInt(");
				exp.accept(this);
				out.print(")");
			} else if (BigDecimal.class == type) {
				out.print("_decimal(");
				exp.accept(this);
				out.print(")");
			} else if (java.util.Date.class == type) {
				out.print("_date(");
				exp.accept(this);
				out.print(")");
			} else if (Object.class == type) {
				exp.accept(this);
			} else {
				String className = type.getName();
				className = className.replaceAll("\\$", "."); // inner class
				out.print("(" + className + ")");
				exp.accept(this);
			}
			
		}
	}
}
