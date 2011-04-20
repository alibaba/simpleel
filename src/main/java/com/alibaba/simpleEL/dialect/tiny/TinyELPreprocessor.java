package com.alibaba.simpleEL.dialect.tiny;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.TypeUtils;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELAssignExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOperator;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELPropertyExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELVariantRefExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyLocalVarDeclareStatement;
import com.alibaba.simpleEL.dialect.tiny.parser.TinyELExprParser;
import com.alibaba.simpleEL.dialect.tiny.parser.TinyStatementParser;
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

		String resolvedResult;
		if (!allowMultiStatement) {
			TinyELExprParser parser = new TinyELExprParser(text);
			TinyELExpr expr = parser.expr();
	
			StringWriter out = new StringWriter();
			JavaSourceGenVisitor visitor = new JavaSourceGenVisitor(new PrintWriter(out));
			expr.accept(visitor);
	
			resolvedResult = out.toString();
		} else {
			TinyStatementParser parser = new TinyStatementParser(text);
			List<TinyELStatement> statements = parser.statementList();
	
			StringWriter out = new StringWriter();
			JavaSourceGenVisitor visitor = new JavaSourceGenVisitor(new PrintWriter(out));
			visitor.incrementIndent();
			for (TinyELStatement statement : statements) {
				visitor.println();
				statement.accept(visitor);
			}
			visitor.decrementIndent();
	
			resolvedResult = out.toString();
		}
		
		//System.out.println(resolvedResult);

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
		private Map<String, Object> localVariants = new HashMap<String, Object>();
		
		public JavaSourceGenVisitor(PrintWriter out) {
			super(out);
		}

		@Override
		public boolean visit(TinyELIdentifierExpr x) {
			String ident = x.getName();
			
			if (localVariants.containsKey(ident)) {
				out.print(ident);
				return false;
			}
			
			{
				String fullName = "java.lang." + ident;
				if (isClassName(fullName)) {
					out.print(ident);
					return false;
				}
			}
			{
				String fullName = "java.util." + ident;
				if (isClassName(fullName)) {
					out.print(fullName);
					return false;
				}
			}
			
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
		
		public boolean isClassName(String fullName) {
			try {
				Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(fullName);
				
				if (clazz != null) {
					return true;
				}
			} catch (ClassNotFoundException e) {
				return false;
			}
			
			return false;
		}
		
		@Override
		public boolean visit(TinyELPropertyExpr x) {
			if (x.getOwner() instanceof TinyELBinaryOpExpr) {
				out.print('(');
				x.getOwner().accept(this);
				out.print(')');
				out.print(".");
		        out.print(x.getName());
		        
		        return false;
			}
			
			String fullName = x.toString();
			if (isClassName(fullName)) {
				out.print(fullName);
				return false;
			}
			
			x.getOwner().accept(this);
			
	        out.print(".");
	        out.print(x.getName());
	        return false;
		}
		
		private Class<?> getType(TinyELExpr expr) {
			if (expr instanceof TinyELIdentifierExpr) {
				TinyELIdentifierExpr ident = (TinyELIdentifierExpr) expr;
				String name = ident.getName();
				
				Class<?> type = variantResolver.getType(name);
				if (type != null) {
					return type;
				}
				
			}
			return null;
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
			            
			            TinyELExpr param = x.getParameters().get(i);
			            if (types[i] == getType(param)) {
			            	param.accept(this);
			            } else {
			            	visitMethodParameter(types[i], param);
			            }
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
		
		@Override
		public boolean visit(TinyELAssignExpr x) {
			if (x.getTarget() instanceof TinyELIdentifierExpr) {
				TinyELIdentifierExpr target = (TinyELIdentifierExpr) x.getTarget();
				String varName = target.getName();
				if (localVariants.containsKey(varName)) {
					x.getTarget().accept(this);
				} else {
					print("ctx.put(\"");
					print(varName);
					print("\", ");
					x.getValue().accept(this);
					print(")");
					return false;
				}
			} else {
				x.getTarget().accept(this);
			}
			print(" = ");
			x.getValue().accept(this);
			return false;
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
				String className = TypeUtils.getClassName(type);
				
				out.print("(" + className + ")");
				exp.accept(this);
			}
			
		}
		
		@Override
		public boolean visit(TinyELBinaryOpExpr x) {
			if (x.getOperator() == TinyELBinaryOperator.InstanceOf) {
				if (x.getLeft() instanceof TinyELIdentifierExpr) {
					TinyELIdentifierExpr ident = (TinyELIdentifierExpr) x.getLeft();
					String varName = ident.getName();
					if (!localVariants.containsKey(varName)) {
						print("ctx.get(\"");
						print(varName);
						print("\") instanceof ");
					}
					x.getRight().accept(this);
					return false;
				}
			}
			return super.visit(x);
		}
		
		@Override
		public boolean visit(TinyLocalVarDeclareStatement x) {
			for (int i = 0, size = x.getVariants().size(); i < size; ++i) {
				String varName = x.getVariants().get(i).getName();
				localVariants.put(varName, x.getType());
			}
			
			return super.visit(x);
		}
	}
}
