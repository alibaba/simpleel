package com.alibaba.simpleEL.dialect.tiny;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELVariantRefExpr;
import com.alibaba.simpleEL.dialect.tiny.parser.TinyELExprParser;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELOutputVisitor;
import com.alibaba.simpleEL.preprocess.TemplatePreProcessor;

public class TinyELPreprocessor extends TemplatePreProcessor {
	public final static String DEFAULT_PACKAGE_NAME = "com.alibaba.simpleEL.dialect.tiny.gen";
	
	public TinyELPreprocessor() {
		this.packageName = DEFAULT_PACKAGE_NAME;
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
			super (out);
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
	}
}
