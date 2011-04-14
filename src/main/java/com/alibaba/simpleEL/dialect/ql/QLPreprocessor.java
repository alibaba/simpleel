package com.alibaba.simpleEL.dialect.ql;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.Preprocessor;
import com.alibaba.simpleEL.dialect.ql.ast.QLExpr;
import com.alibaba.simpleEL.dialect.ql.ast.QLSelect;
import com.alibaba.simpleEL.dialect.ql.parser.QLSelectParser;

public class QLPreprocessor implements Preprocessor {
	private String packageName = "com.alibaba.simpleEL.dialect.ql.gen";
	private final AtomicLong classIdSeed = new AtomicLong(10000L);
	
	@Override
	public JavaSource handle(Map<String, Object> context, String ql) {
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		
		QLSelectParser parser = new QLSelectParser(ql);

		QLSelect select = parser.select();
		
		final String className = "GenClass_" + classIdSeed.getAndIncrement();
		
		_package(out);
		_import(out);
		_class_start(out, className);
		_eval_start(out);
		gen(context, select, out);
		_eval_end(out);
		_class_end(out);

		String text = writer.toString();
		
		
		
		return new JavaSource(packageName, className, text);
	}
	
	public void _package(PrintWriter out) {
		out.println("package " + packageName);
		out.println();
	}
	
	public void _import(PrintWriter out) {
		out.println("import java.util.*;");
		out.println("import static java.lang.Math.*;");
		out.println("import com.alibaba.simpleEL.Expr;");
		out.println();
	}
	
	public void _class_start(PrintWriter out, String className) {
		out.println("public class " + className + " implements Expr {");
		out.println();
	}
	
	public void _class_end(PrintWriter out) {
		out.println();
		out.println("}");
	}
	
	public void _eval_start(PrintWriter out) {
		out.println("	public Object eval(Map<String, Object> ctx) {");
	}
	
	public void _eval_end(PrintWriter out) {
		out.println();
		out.println("	}");
	}
	
	public void gen(Map<String, Object> context, QLSelect select, PrintWriter out) {
		Class<?> clazz = (Class<?>) context.get("class");
		
		if (clazz == null) {
			throw new IllegalArgumentException("context not contains 'class'");
		}
		
		String className = clazz.getName();
		className = className.replaceAll("\\$", ".");
		
		String entryVarName = "item";
		
		String srcCollectionName = "items";
		out.println("		List<" + className + "> " + srcCollectionName + " = (List<" + className + ">) context.get(\"_src_\");");
		
		out.println("		for (" + className + " " + entryVarName + " : " + srcCollectionName + ") {");
		out.println();
		
		gen_where(context, select, out);
		
		out.println("		}");
		
	}
	
	public void gen_where(Map<String, Object> context, QLSelect select, PrintWriter out) {
		Class<?> clazz = (Class<?>) context.get("class");
		
		if (clazz == null) {
			throw new IllegalArgumentException("context not contains 'class'");
		}
		
		QLExpr where = select.getWhere();
		if (where == null) {
			return;
		}
		
	}
}
