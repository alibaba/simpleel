package com.alibaba.simpleEL.dialect.ql;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.Preprocessor;
import com.alibaba.simpleEL.dialect.ql.ast.QLBinaryOpExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLBinaryOperator;
import com.alibaba.simpleEL.dialect.ql.ast.QLExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLIdentifierExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLLimit;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderBy;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderByItem;
import com.alibaba.simpleEL.dialect.ql.ast.QLOrderByMode;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelect;
import com.alibaba.simpleEL.dialect.ql.ast.QLVariantRefExpr;
import com.alibaba.simpleEL.dialect.ql.parser.QLSelectParser;
import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitor;
import com.alibaba.simpleEL.dialect.ql.visitor.QLAstVisitorAdapter;
import com.alibaba.simpleEL.dialect.ql.visitor.QLOutputAstVisitor;

public class QLPreprocessor implements Preprocessor {
	public static final String ATTR_TYPE = "_TYPE_";

	private String packageName = "com.alibaba.simpleEL.dialect.ql.gen";
	private final AtomicLong classIdSeed = new AtomicLong(10000L);

	private String srcCollectionName = "_src_";
	private String destCollectionName = "_dest_";

	@Override
	public JavaSource handle(Map<String, Object> context, String ql) {
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);

		QLSelectParser parser = new QLSelectParser(ql);

		QLSelect select = parser.select();

		final String className = "GenClass_" + classIdSeed.getAndIncrement();

		gen_package(out);
		gen_import(out);
		gen_class_start(out, className);
		gen_eval_start(out);
		gen_eval_body(context, select, out);
		gen_eval_end(out);
		gen_class_end(out);

		String text = writer.toString();

		return new JavaSource(packageName, className, text);
	}

	public void gen_package(PrintWriter out) {
		out.println("package " + packageName + ";");
		out.println();
	}

	public void gen_import(PrintWriter out) {
		out.println("import java.util.*;");
		out.println("import static java.lang.Math.*;");
		out.println("import com.alibaba.simpleEL.Expr;");
		out.println("import static com.alibaba.simpleEL.TypeUtils.*;");
		out.println();
	}

	public void gen_class_start(PrintWriter out, String className) {
		out.println("public class " + className + " implements Expr {");
		out.println();
	}

	public void gen_class_end(PrintWriter out) {
		out.println();
		out.println("}");
	}

	public void gen_eval_start(PrintWriter out) {
		out.println("	public Object eval(Map<String, Object> ctx) {");
	}

	public void gen_eval_end(PrintWriter out) {
		out.println();
		out.println("		return null;");
		out.println("	}");
	}

	public void gen_eval_body(Map<String, Object> context, QLSelect select, PrintWriter out) {
		Class<?> clazz = (Class<?>) context.get("class");

		if (clazz == null) {
			throw new IllegalArgumentException("context not contains 'class'");
		}

		String className = clazz.getName();
		className = className.replaceAll("\\$", ".");

		String entryVarName = "item";

		out.println("		List<" + className + "> " + srcCollectionName + " = (List<" + className + ">) ctx.get(\"_src_\");");
		out.println("		List<" + className + "> " + destCollectionName + " = (List<" + className + ">) ctx.get(\"_dest_\");");

		final QLLimit limit = select.getLimit();
		if (limit != null) {
			if (limit.getOffset() != null) {
				out.println("		final int offset = " + limit.getOffset() + ";");	
			}
			out.println("		final int rowCount = " + limit.getRowCount() + ";");
			out.println();
			
			if (limit.getOffset() != null) {
				out.println("		int rowIndex = 0;");	
			}
		}
		
		out.println("		for (" + className + " " + entryVarName + " : " + srcCollectionName + ") {");
		out.println();

		gen_where(context, select, out);

		out.println();
		out.println("		}");

		gen_orderBy(context, select, out);
		gen_limit(context, select, out);
	}
	
	public void gen_limit(final Map<String, Object> context, final QLSelect select, final PrintWriter out) {
		QLLimit limit = select.getLimit();
		
		if (limit == null) {
			return;
		}
		
		out.println();
		
		if (limit.getOffset() != null) {
			out.println("		for (int i = 0; i < offset && i < _dest_.size(); ++i) {");
			out.println("			_dest_.remove(0);");
			out.println("		}");
		}
		
		out.println("		while(_dest_.size() > rowCount) {");
		out.println("			_dest_.remove(_dest_.size() - 1);");
		out.println("		}");
	}

	public void gen_orderBy(final Map<String, Object> context, final QLSelect select, final PrintWriter out) {
		final Class<?> clazz = (Class<?>) context.get("class");

		if (clazz == null) {
			throw new IllegalArgumentException("context not contains 'class'");
		}

		final QLOrderBy orderBy = select.getOrderBy();
		if (orderBy == null || orderBy.getItems().size() == 0) {
			return;
		}

		String className = clazz.getName();
		className = className.replaceAll("\\$", ".");

		for (QLOrderByItem item : orderBy.getItems()) {
			out.println("		{");
			out.println("			Comparator<" + className + "> comparator = new Comparator<" + className + ">() {");
			out.println("				public int compare(" + className + " a, " + className + " b) {");

			if (item.getMode() == QLOrderByMode.DESC) {
				out.println("					if (" + gen_orderByItem(context, "a", item.getExpr()) + " > " + gen_orderByItem(context, "b", item.getExpr()) + ") {");
				out.println("						return -1;");
				out.println("					}");

				out.println("					if (" + gen_orderByItem(context, "a", item.getExpr()) + " < " + gen_orderByItem(context, "b", item.getExpr()) + ") {");
				out.println("						return 1;");
				out.println("					}");
			} else {
				out.println("					if (" + gen_orderByItem(context, "a", item.getExpr()) + " > " + gen_orderByItem(context, "b", item.getExpr()) + ") {");
				out.println("						return 1;");
				out.println("					}");

				out.println("					if (" + gen_orderByItem(context, "a", item.getExpr()) + " < " + gen_orderByItem(context, "b", item.getExpr()) + ") {");
				out.println("						return -1;");
				out.println("					}");
			}

			out.println("					return 0;");
			out.println("				}");
			out.println("			};");
			out.println("			Collections.sort(" + destCollectionName + ", comparator);");
			out.println("		}");
		}
	}

	public String gen_orderByItem(final Map<String, Object> context, final String varName, final QLExpr expr) {
		final Class<?> clazz = (Class<?>) context.get("class");

		if (clazz == null) {
			throw new IllegalArgumentException("context not contains 'class'");
		}

		final StringWriter out = new StringWriter();

		QLAstVisitor visitor = new QLOutputAstVisitor(new PrintWriter(out)) {
			@Override
			public boolean visit(QLIdentifierExpr x) {
				String name = x.getName();

				Method method = getMethod(clazz, name);

				if (method == null) {
					throw new ELException("getter not found. " + x.getName());
				}

				out.print(varName + "." + method.getName() + "()");

				return true;
			}
		};
		expr.accept(visitor);
		return out.toString();
	}

	public void gen_where(final Map<String, Object> context, final QLSelect select, final PrintWriter out) {
		final Class<?> clazz = (Class<?>) context.get("class");

		if (clazz == null) {
			throw new IllegalArgumentException("context not contains 'class'");
		}

		final QLExpr where = select.getWhere();
		if (where == null) {
			return;
		}
		
		final QLLimit limit = select.getLimit();

		QLAstVisitorAdapter setTypeVisitor = new SetTypeVisitor(clazz);
		where.accept(setTypeVisitor);

		out.print("			if(");
		QLAstVisitor visitor = new WhereGenOutputVisitor(out, clazz);
		where.accept(visitor);

		out.println(") {");
		
		if (limit != null && select.getOrderBy() == null) {
			out.println("				if (_dest_.size() >= rowCount) {");
			out.println("					break;");
			out.println("				}");
		}
		
		out.println("				" + destCollectionName + ".add(item);");
		out.println("			}");
		out.println();
	}

	private static Method getMethod(Class<?> clazz, String name) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		Method method = null;
		try {
			method = clazz.getMethod("get" + name);
		} catch (NoSuchMethodException e) {
			// skip
		} catch (Exception e) {
			throw new ELException("handle ql where error");
		}

		if (method == null) {
			try {
				method = clazz.getMethod("is" + name);
			} catch (NoSuchMethodException e) {
				// skip
			} catch (Exception e) {
				throw new ELException("handle ql where error");
			}
		}

		return method;
	}

	private final class WhereGenOutputVisitor extends QLOutputAstVisitor {
		private final Class<?> clazz;

		private WhereGenOutputVisitor(PrintWriter out, Class<?> clazz) {
			super(out);
			this.clazz = clazz;
		}

		@Override
		public boolean visit(QLIdentifierExpr x) {
			String name = x.getName();

			Method method = getMethod(clazz, name);

			if (method == null) {
				throw new ELException("getter not found. " + x.getName());
			}

			out.print("item." + method.getName() + "()");

			return true;
		}

		@Override
		public boolean visit(QLVariantRefExpr x) {
			Class<?> type = (Class<?>) x.getAttributes().get(ATTR_TYPE);

			String variant = x.getName();

			if (variant.startsWith("@")) {
				variant = variant.substring(1);
			}

			if (type != null) {
				if (boolean.class == type) {
					out.print("_bool(ctx.get(\"" + variant + "\"))");
				} else if (String.class == type) {
					out.print("_string(ctx.get(\"" + variant + "\"))");
				} else if (byte.class == type) {
					out.print("_byte(ctx.get(\"" + variant + "\"))");
				} else if (short.class == type) {
					out.print("_short(ctx.get(\"" + variant + "\"))");
				} else if (int.class == type) {
					out.print("_int(ctx.get(\"" + variant + "\"))");
				} else if (long.class == type) {
					out.print("_long(ctx.get(\"" + variant + "\"))");
				} else if (float.class == type) {
					out.print("_float(ctx.get(\"" + variant + "\"))");
				} else if (double.class == type) {
					out.print("_double(ctx.get(\"" + variant + "\"))");
				} else if (BigInteger.class == type) {
					out.print("_bigInt(ctx.get(\"" + variant + "\"))");
				} else if (BigDecimal.class == type) {
					out.print("_decimal(ctx.get(\"" + variant + "\"))");
				} else if (java.util.Date.class == type) {
					out.print("_date(ctx.get(\"" + variant + "\"))");
				} else if (Object.class == type) {
					out.print("ctx.get(\"" + variant + "\")");
				} else {
					String className = type.getName();
					className = className.replaceAll("\\$", "."); // inner
																	// class
					out.print("((" + className + ")" + "ctx.get(\"" + variant + "\"))");
				}
			} else {
				out.print(x.getName());
			}

			return false;
		}
		
		   public boolean visit(QLBinaryOpExpr x) {
		        if (x.getLeft() instanceof QLBinaryOpExpr) {
		            QLBinaryOpExpr left = (QLBinaryOpExpr) x.getLeft();
		            if (left.getOperator().priority > x.getOperator().priority) {
		                out.print('(');
		                left.accept(this);
		                out.print(')');
		            } else {
		                left.accept(this);
		            }
		        } else {
		            x.getLeft().accept(this);
		        }

		        out.print(" ");
		        if (QLBinaryOperator.BooleanAnd == x.getOperator()) {
		        	out.print("&&");
		        } else if (QLBinaryOperator.BooleanOr == x.getOperator()) {
		        	out.print("||");
		        } else {
		        	out.print(x.getOperator().name);
		        }
		        out.print(" ");

		        if (x.getRight() instanceof QLBinaryOpExpr) {
		            QLBinaryOpExpr right = (QLBinaryOpExpr) x.getRight();
		            if (right.getOperator().priority >= x.getOperator().priority) {
		                out.print('(');
		                right.accept(this);
		                out.print(')');
		            } else {
		                right.accept(this);
		            }
		        } else {
		            x.getRight().accept(this);
		        }

		        return false;
		    }
	}

	private final class SetTypeVisitor extends QLAstVisitorAdapter {
		private final Class<?> clazz;

		private SetTypeVisitor(Class<?> clazz) {
			this.clazz = clazz;
		}

		@Override
		public boolean visit(QLIdentifierExpr x) {
			String name = x.getName();

			Method method = getMethod(clazz, name);

			if (method == null) {
				throw new ELException("getter not found. " + x.getName());
			}

			x.getAttributes().put(ATTR_TYPE, method.getReturnType());

			return false;
		}

		@Override
		public boolean visit(QLBinaryOpExpr x) {
			x.getLeft().accept(this);
			x.getRight().accept(this);

			Class<?> leftType = (Class<?>) x.getLeft().getAttributes().get(ATTR_TYPE);
			Class<?> rightType = (Class<?>) x.getRight().getAttributes().get(ATTR_TYPE);

			if (leftType == null && rightType != null) {
				x.getLeft().getAttributes().put(ATTR_TYPE, rightType);
			}

			if (rightType == null && leftType != null) {
				x.getRight().getAttributes().put(ATTR_TYPE, leftType);
			}

			return false;
		}
	}
}
